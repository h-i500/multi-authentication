やったー！動いたところまで反映した、**更新版 README.md** を用意しました。
そのまま置き換えられるように、全文を下に載せます（Kong は idempotent な `PUT` を使って「作成 or 更新」できる手順に寄せ、競合ルートの掃除コマンドも入れてあります）。

---

# multi-authentication

Keycloak（OIDC）× Quarkus（BFF / Aggregator）× Kong（API Gateway）× React（Vite dev-server）で構成する、多段認証・BFF/マッシュアップのサンプルです。
**今回の更新点**として、`frontend`（Vite）を追加し、**Kong で `/app` をフロントに、`/api/*` を Quarkus BFF に**振り分ける形に変更しました。OIDC のコールバックは **`/login`** に固定し、BFF 側で `/app/` に戻す運用です。

---

## アーキテクチャ

```
[Browser]
   |
   |  http://localhost:8000
   v
+------------------+
|      Kong        |  (proxy:8000, admin:8001)
+------------------+
   | /app/*                        | /api/*, /login, /secure, /logout
   v                               v
+------------------+            +------------------+
|  frontend(Vite)  |            |  Quarkus BFF     |
|  http://frontend:5173         |  http://quarkus-authz:8080
+------------------+            +---------+--------+
                                           |
                              (Bearer)     |  /a/*, /b/*
                                           v
                                 +---------+--------+
                                 |  Service A / B   |
                                 +------------------+

Keycloak: http://keycloak:8080 (realm: demo-realm)
Redis:    セッション保持
Postgres: Kong DB
Konga:    Kong 管理UI（任意） http://localhost:1337
```

### ログイン・リダイレクトの流れ

1. ユーザが `http://localhost:8000/app/` を開く
2. フロントの「ログイン」→ `window.location.assign('/api/login')`
3. Kong → Quarkus BFF (`/api/login` → `LoginResource#login`)

   * 未ログインなら **`/secure` へ 302**（Kong 経由で BFF に届く）
   * BFF の `/secure` は `@Authenticated` なので、Quarkus OIDC のコードフローが走る
4. Keycloak ログイン → コールバックは **`/login`**（Kong → BFF）
5. BFF がセッション確立後、**`/app/` に 302** で戻す
6. 以降、`/api/me` / `/api/mashup` は BFF 経由で動作

---

## 前提

* Docker / Docker Compose が利用できること
* `jq` が入っていると便利（Kong の確認・掃除用）

---

## 起動

```bash
# クローン
git clone https://github.com/h-i500/multi-authentication.git
cd multi-authentication

# 起動（ビルド込み）
docker compose up -d
```

> 初回は Keycloak の Realm インポートや各サービスのビルドで数分かかります。

---

## Kong の設定（作成 or 更新：idempotent）

Kong の **Service** と **Route** を「名前指定の `PUT`」で作成/更新します。
（既に存在しても上書き更新されるので安全に再実行できます）

### 1) Services

```bash
# BFF(Service) → quarkus-authz:8080
curl -sS -X PUT http://localhost:8001/services/api-svc \
  -d url=http://quarkus-authz:8080

# Frontend(Service) → frontend:5173 (Vite dev)
curl -sS -X PUT http://localhost:8001/services/frontend-dev-svc \
  -d url=http://frontend:5173
```

### 2) Routes

#### API 側（BFF 行き）

```bash
# /api → BFF へ。/api を剥がす（/api/* → /*）
curl -sS -X PUT http://localhost:8001/routes/api-route \
  -d service.name=api-svc \
  -d paths[]=/api \
  -d strip_path=true \
  -d preserve_host=true \
  -d path_handling=v0

# OIDC コールバック (/login) は剥がさない（BFF の /login にそのまま渡す）
curl -sS -X PUT http://localhost:8001/routes/login-route \
  -d service.name=api-svc \
  -d paths[]=/login \
  -d strip_path=false \
  -d preserve_host=true \
  -d path_handling=v0

# OIDC 開始の踏み台 (/secure) も剥がさない
curl -sS -X PUT http://localhost:8001/routes/secure-route \
  -d service.name=api-svc \
  -d paths[]=/secure \
  -d strip_path=false \
  -d preserve_host=true \
  -d path_handling=v0

# ログアウト (/logout) も剥がさない
curl -sS -X PUT http://localhost:8001/routes/logout-route \
  -d service.name=api-svc \
  -d paths[]=/logout \
  -d strip_path=false \
  -d preserve_host=true \
  -d path_handling=v0

# （任意）/api/secure を個別に持ちたい場合
curl -sS -X PUT http://localhost:8001/routes/api-secure-route \
  -d service.name=api-svc \
  -d paths[]=/api/secure \
  -d strip_path=false \
  -d preserve_host=true \
  -d path_handling=v0
```

#### Frontend 側

```bash
# /app → Vite dev にそのまま渡す
# Vite の dev サーバとは相性の良い v1 を使用
curl -sS -X PUT http://localhost:8001/routes/frontend-dev-route \
  -d service.name=frontend-dev-svc \
  -d paths[]=/app \
  -d strip_path=false \
  -d preserve_host=true \
  -d path_handling=v1
```

### 3) 競合ルートの掃除（重要）

他サービスに向く `/secure`・`/login` 等が残っていると、**404 や State エラー**の原因になります。
`/secure` を例に、BFF 以外に向くルートを削除します。

```bash
SID_API=$(curl -s http://localhost:8001/services | jq -r '.data[] | select(.name=="api-svc") | .id')

# /secure が api-svc 以外に向いているルートを削除
for id in $(curl -s http://localhost:8001/routes \
  | jq -r ".data[] | select((.paths|index(\"/secure\")) and .service.id != \"$SID_API\") | .id"); do
  curl -sS -X DELETE http://localhost:8001/routes/$id
done
```

### 4) 設定の確認

```bash
curl -s http://localhost:8001/routes \
  | jq '.data[] | {name, paths, strip_path, preserve_host, path_handling, service: .service.id}'
```

**期待する最終形**

* `login-route` … `/login`（strip\_path\:false, preserve\_host\:true, v0）
* `secure-route` … `/secure`（strip\_path\:false, preserve\_host\:true, v0）
* `logout-route` … `/logout`（strip\_path\:false, preserve\_host\:true, v0）
* `api-route` … `/api`（strip\_path\:true, preserve\_host\:true, v0）
* `api-secure-route` … `/api/secure`（strip\_path\:false, preserve\_host\:true, v0）※任意
* `frontend-dev-route` … `/app`（strip\_path\:false, preserve\_host\:true, **v1**）

---

## アプリ固有のポイント（BFF / Frontend）

### Quarkus（BFF）設定の要点

`quarkus-authz/src/main/resources/application.properties`（抜粋）

```properties
# OIDC 基本設定
quarkus.oidc.auth-server-url=http://keycloak:8080/realms/demo-realm
quarkus.oidc.client-id=quarkus-client
quarkus.oidc.credentials.client-secret.value=VJEng5YQw6dBE5mf6x5R2tFvU0KMg3KB
quarkus.oidc.application-type=web-app
quarkus.http.proxy.proxy-address-forwarding=true

# OIDC コールバックを /login に固定（/app ではない）
quarkus.oidc.authentication.redirect-path=/login
# コールバック後は元URLへ戻さず、LoginResource 側で /app/ に送る
quarkus.oidc.authentication.restore-path-after-redirect=false

# PKCE & state 保護
quarkus.oidc.authentication.pkce-required=true
quarkus.oidc.authentication.state-secret=${STATE_SECRET:change-me-change-me-change-me-1234}

# ログアウト
quarkus.oidc.logout.path=/logout
quarkus.oidc.logout.post-logout-path=/app/

# そのほか（Redis セッション保持、トークン戦略など）はファイル参照
```

`LoginResource.java`（意図）

* `GET /login`（PermitAll）
  未ログインなら **`/secure` へ 302**、ログイン済みなら **`/app/` へ 302**
* `GET /secure`（Authenticated）
  OIDC のコードフローを開始 → 認証後は `/app/` に 302
* `GET /logout`
  セッション破棄 → `/app/` に 302

### Frontend（Vite / React）

`frontend/src/App.tsx`（抜粋）

```tsx
<button type="button" onClick={() => window.location.assign('/api/login')}>
  ログイン
</button>
<button type="button" onClick={() => window.location.assign('/api/logout')}>
  ログアウト
</button>

<button onClick={() => apiGet('/me')}>/api/me</button>
<button onClick={() => apiGet('/mashup')}>/api/mashup</button>
<a href="/app/" style={{ marginLeft: 8 }}>トップへ</a>
```

* \*\*ログイン/ログアウトはトップレベル遷移（`window.location.assign`）\*\*が必須
* API は **`/api/*`** で BFF に到達

---

## 動作確認

1. `http://localhost:8000/app/` をブラウザで開く
2. 「ログイン」を押下

   * Keycloak ログイン画面 → 認証
   * コールバック `/login` → BFF が `/app/` に戻す
3. `/api/me` と `/api/mashup` が 200 で結果を返すこと
4. 「ログアウト」→ `/app/` に戻り、`/api/me` が再びログインを促すこと

---

## よくあるハマりどころ

* **`/login` で 404（Resource not found）**
  → Kong の `login-route` が **`strip_path=false`** / `preserve_host=true` になっているか確認。
  誤って `strip_path=true` だと BFF の `/login` まで届かず 404 になります。

* **`State parameter can not be empty` のログ**
  → `/login` を直接叩いた場合に出ることがある正常なログ。
  ユーザの導線は `/api/login` → `/secure` → `/login` → `/app/` を想定。

* **認証後に `/app/` へ戻らない**
  → `quarkus.oidc.authentication.redirect-path=/login` と
  `quarkus.oidc.authentication.restore-path-after-redirect=false` が一致しているか、
  そして **`/login` が BFF へ到達**しているか（Kong ルート）を確認。

* **`/secure` に他サービス向きのルートが残っている**
  → 競合ルートの掃除スクリプトを実行して除去（上記参照）。

* **Keycloak のロール / groups が期待通り出ない**
  → `quarkus.oidc.roles.*` の設定や、Access Token に載るクレーム（`realm_access/roles` など）を確認。

* **HTTPS/TLS 終端を入れた**
  → Cookie の `Secure` / `SameSite` 設定を見直し。
  `quarkus.oidc.authentication.cookie-force-secure=true` などを検討。

---

## 付録：Kong 設定をまるっと再適用したいとき

何度でも実行可（作成 or 更新）。競合ルートの掃除も含めて再適用できます。

```bash
# Services
curl -sS -X PUT http://localhost:8001/services/api-svc -d url=http://quarkus-authz:8080
curl -sS -X PUT http://localhost:8001/services/frontend-dev-svc -d url=http://frontend:5173

# Routes
curl -sS -X PUT http://localhost:8001/routes/api-route \
  -d service.name=api-svc -d paths[]=/api -d strip_path=true -d preserve_host=true -d path_handling=v0
curl -sS -X PUT http://localhost:8001/routes/login-route \
  -d service.name=api-svc -d paths[]=/login -d strip_path=false -d preserve_host=true -d path_handling=v0
curl -sS -X PUT http://localhost:8001/routes/secure-route \
  -d service.name=api-svc -d paths[]=/secure -d strip_path=false -d preserve_host=true -d path_handling=v0
curl -sS -X PUT http://localhost:8001/routes/logout-route \
  -d service.name=api-svc -d paths[]=/logout -d strip_path=false -d preserve_host=true -d path_handling=v0
curl -sS -X PUT http://localhost:8001/routes/api-secure-route \
  -d service.name=api-svc -d paths[]=/api/secure -d strip_path=false -d preserve_host=true -d path_handling=v0
curl -sS -X PUT http://localhost:8001/routes/frontend-dev-route \
  -d service.name=frontend-dev-svc -d paths[]=/app -d strip_path=false -d preserve_host=true -d path_handling=v1

# 競合ルート掃除（/secure の例）
SID_API=$(curl -s http://localhost:8001/services | jq -r '.data[] | select(.name=="api-svc") | .id')
for id in $(curl -s http://localhost:8001/routes \
  | jq -r ".data[] | select((.paths|index(\"/secure\")) and .service.id != \"$SID_API\") | .id"); do
  curl -sS -X DELETE http://localhost:8001/routes/$id
done

# 確認
curl -s http://localhost:8001/routes \
  | jq '.data[] | {name, paths, strip_path, preserve_host, path_handling, service: .service.id}'
```

---

## 参考：主要エンドポイント

* フロント：`http://localhost:8000/app/`
* API(BFF)：`http://localhost:8000/api/*`

  * ログイン：`/api/login`（→ `/secure` → Keycloak → `/login` → `/app/`）
  * ログアウト：`/api/logout`
  * 認証ユーザ：`/api/me`
  * マッシュアップ：`/api/mashup`
* Keycloak：`http://localhost:8080/`（realm: `demo-realm`）
* Konga（任意）：`http://localhost:1337/`

---

以上です。
このまま `README.md` に貼り付ければ、frontend 追加後の構成・手順・Kong 設定まで一通り追従できます。必要なら、Kong 設定を `deck`/宣言的設定に移す版も作れますよ。
