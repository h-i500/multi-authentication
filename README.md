# 多段認証・多段認可デモ（Quarkus × Kong × Keycloak × Redis セッション）

ユーザー → **Kong **`` → **Quarkus(集約API)** が OIDC でログイン（セッションは **Redis**） → Quarkus が **ユーザーの Access Token を下流へ転送** → **Service A / B** が各自のポリシーで認可 → 結果をマージして返却するデモ構成です。

---

## アーキテクチャ

```
Browser ──▶ Kong(8000) ──▶ quarkus-authz(8080/8081)
                             │
                             ├──▶ Service A (9081)
                             └──▶ Service B (9082)

Keycloak(8080): OIDC IdP
Redis(6379):    Quarkus OIDC セッション保管（Token State）
```

- Quarkus(集約API) は **Authorization Code Flow**（`application-type=web-app`）
- セッションは `` によって **Redis に外だし**（Cookie は参照キーのみ）
- 下流A/Bは `` で **Bearer(JWT) 検証**

> **Quarkus バージョン**: 3.24+（推奨 3.25.x 以降）

---

## ディレクトリ構成

```
.
├─ docker-compose.yaml
├─ kong-nginx-http.conf
├─ init-konga-db.sql
├─ quarkus-authz/           # 集約API (web-app)
├─ service-a/
└─ service-b/
```

---

## 前提

- JDK 17
- Maven / Docker / Docker Compose
- （初回のみ）Kong の DB マイグレーションが必要

---

## ビルド（fast-jar）

各モジュールで:

```bash
cd quarkus-authz && mvn clean package && cd ..
cd service-a     && mvn clean package && cd ..
cd service-b     && mvn clean package && cd ..
```

> Dockerfile は `target/quarkus-app/...` と `quarkus-run.jar` を前提とします。

---

## 起動（Docker Compose）

```bash
docker compose up -d --build kong-database redis keycloak
# 数秒待機（Keycloak 起動待ち）

# 初回のみ Kong DB のマイグレーション
docker compose run --rm kong kong migrations bootstrap

# ゲートウェイ/UI
docker compose up -d --build kong konga

# アプリ群
docker compose up -d --build service-a service-b quarkus-authz
```

公開ポート: Kong 8000/8001, Keycloak 8080, quarkus-authz 8081, service-a 9081, service-b 9082

---

## Keycloak 設定（`demo-realm`）

管理UI: `http://localhost:8080/`（Admin: `admin` / `admin`）

### 1) Realm/ユーザー

- **Realm**: `demo-realm`
- **User**: `testuser`（Password=`password`, Temporary=OFF）

### 2) Clients（3つ）

- ``**（集約API）**

  - Client type: OpenID Connect
  - **Client authentication: ON（= Confidential）**
  - Standard Flow: ON（Authorization Code）
  - Direct Access Grants: 任意で ON（CLI 動作確認用）
  - **Valid Redirect URIs**: `http://localhost:8000/*`, `http://quarkus-authz:8000/*`, `http://localhost:8081/*`
  - Web Origins: `http://localhost:8000`, `http://localhost:8081`
  - **Credentials > Secret** をコピーして、後述の Quarkus 設定へ

- ``** / **``**（下流）**

  - Access Type: **Bearer-only**（ログイン画面なし）

### 3) Client Roles

- `service-a`: `read`, `user`
- `service-b`: `read`, `user`

### 4) ユーザーへロール付与

- `testuser` → Role Mapping → Client Roles → `service-a: read,user` / `service-b: read,user` を付与

### 5) Audience（aud）に A/B を含める

- `quarkus-client` → Client Scopes → Add Mapper → **Audience**
  - Included Client Audience: `service-a`, `service-b`
  - Add to access token: ON

> これで access token に `aud: ["service-a","service-b", ...]` と `resource_access.service-a.roles / service-b.roles` が入ります。

---

## Kong ルーティング（素通し）

認証は Quarkus に任せる。Kong はルートだけ作る。

Admin API: `http://localhost:8001`

```bash
# Quarkus 集約API（Docker内名に向ける）
curl -sS -X POST http://localhost:8001/services \
  -d name=mashup-svc \
  -d url=http://quarkus-authz:8080

# /mashup の Route（パスそのまま）
curl -sS -X POST http://localhost:8001/services/mashup-svc/routes \
  -d name=mashup-route \
  -d paths[]=/mashup \
  -d strip_path=false \
  -d preserve_host=true
```

> `strip_path=false` と `preserve_host=true` が重要。外向きホスト/パスを Quarkus に正しく伝える。

---

## Quarkus（集約API）設定

`quarkus-authz/src/main/resources/application.properties`（主要部）

```properties
# --- デフォルト：ブラウザログイン用（web-app）---
quarkus.oidc.auth-server-url=http://keycloak:8080/realms/demo-realm
quarkus.oidc.client-id=quarkus-client
quarkus.oidc.credentials.client-secret.value=<Keycloak の Secret>
quarkus.oidc.application-type=web-app

# /mashup を認証必須に
quarkus.http.auth.permission.mashup.paths=/mashup
quarkus.http.auth.permission.mashup.policy=authenticated

# 逆プロキシ配下
quarkus.http.proxy.proxy-address-forwarding=true

# --- Redis にセッション外だし ---
quarkus.redis.hosts=redis://redis:6379
quarkus.oidc.token-state-manager.strategy=id-refresh-tokens
# 名前付きクライアントで分離（任意）
quarkus.oidc.redis-token-state-manager.redis-client-name=session
quarkus.redis.session.hosts=redis://redis:6379

# セッション継続
quarkus.oidc.token.refresh-expired=true
quarkus.oidc.authentication.session-age-extension=15M
quarkus.oidc.token.refresh-token-time-skew=30S

# Cookie（開発時は Secure を有効化しないこと）
# 本番(HTTPS終端)では有効化推奨
# quarkus.oidc.authentication.cookie-force-secure=true
quarkus.oidc.authentication.cookie-same-site=Lax

# ロールは access token から
quarkus.oidc.roles.source=accesstoken

# ログ（確認が済んだら INFO へ）
quarkus.log.category."io.quarkus.oidc".level=TRACE
```

> **注意**: ローカルで `http://localhost:8000` にアクセスする間は `cookie-force-secure=true` を無効にしてください（HTTP ではクッキーが送信されず、ログインがループします）。

> `/mashup` を **Bearer 専用**にしたい場合は `quarkus.oidc."service".tenant-paths=/mashup` を有効にし、web-app 側のパーミッションから外してください（ブラウザは 401 になります）。

---

## 下流サービス（A/B）設定

`service-a` / `service-b` の `application.properties`:

```properties
quarkus.oidc.auth-server-url=http://keycloak:8080/realms/demo-realm
quarkus.oidc.client-id=service-a   # service-b 側は service-b
quarkus.oidc.application-type=service

# アクセストークン内ロールを使用
quarkus.oidc.roles.source=accesstoken
quarkus.oidc.roles.role-claim-path=resource_access["service-a"].roles  # B は ["service-b"]
```

必要に応じてエンドポイントに `@RolesAllowed("read")` を付けて厳格化します。

---

## 動作確認

### 1) ブラウザ（Kong 経由）

- `http://localhost:8000/mashup`
  - 初回は Keycloak ログイン → `/mashup` に戻って結果表示

### 2) CLI（直叩き）

```bash
# パスワードグラントでトークン取得（検証用）
TOKEN=$(curl -s -X POST \
  'http://localhost:8080/realms/demo-realm/protocol/openid-connect/token' \
  -d 'grant_type=password' \
  -d 'client_id=quarkus-client' \
  -d 'username=testuser' \
  -d 'password=password' | jq -r .access_token)

# 集約APIへ
curl -i http://localhost:8081/mashup -H "Authorization: Bearer $TOKEN"
```

期待レスポンス：

```json
{
  "fromServiceA": { "message": "ok-from-A", "detail": "helloA" },
  "fromServiceB": { "message": "ok-from-b", "detail": "helloB" }
}
```

### 3) Redis でセッション確認

```bash
docker exec -it <redis-container> redis-cli
127.0.0.1:6379> SCAN 0 MATCH * COUNT 100
127.0.0.1:6379> TTL "oidc:token:..."   # 残存時間
```

---

## トラブルシューティング & TIPS

- **401 unauthorized\_client**: Keycloak の `quarkus-client` を **Confidential** に、Quarkus の `client-secret.value` と一致させる。
- **リダイレクトURI不一致**: `http://localhost:8000/*`, `http://quarkus-authz:8000/*`, `http://localhost:8081/*` を Valid Redirect URIs に登録。
- **Cookie が返らない/ループ**: 開発時に `cookie-force-secure=true` 有効だと HTTP では送れない。
- **roles.source** は ``（ハイフン/アンダースコアなし）。
- **aud が足りない**: Audience マッパーで `service-a` / `service-b` を access token に付与。
- **大きいヘッダで 502**: `kong-nginx-http.conf` の `large_client_header_buffers` を調整。
- **セッション全消し**: 検証時は Redis で `FLUSHDB`（本番は慎重に）。

---

## ライセンス

このリポジトリのルールに従います（未設定の場合は適宜追記してください）。

