素晴らしい進捗ですね！🎉
以下に、**Kong + Konga + Quarkus + Keycloak** を統合するための手順を **順を追って丁寧に整理** しました。

---

# ✅ 多段認証構成：統合セットアップ手順

構成は以下の通りです：

* **Kong Gateway**：API ゲートウェイ
* **PostgreSQL**：Kong/Konga用データベース
* **Konga**：Kong管理GUI
* **Redis**：Quarkus認可用セッションストア
* **Keycloak**：OIDC 認証プロバイダー
* **Quarkus**：認可マイクロサービス（OIDC→Redis→Kong）

---

## 🧱 1. Docker Compose準備

`docker-compose.yaml` は以下のように構成：

```yaml
version: '3.8'

services:
  kong-database:
    image: postgres:10
    environment:
      POSTGRES_USER: kong
      POSTGRES_DB: kong
      POSTGRES_PASSWORD: kong
    volumes:
      - kong_data:/var/lib/postgresql/data

  redis:
    image: redis:7
    ports:
      - "6379:6379"

  kong:
    image: kong:3.6.0
    depends_on:
      - kong-database
    environment:
      KONG_DATABASE: postgres
      KONG_PG_HOST: kong-database
      KONG_PG_USER: kong
      KONG_PG_PASSWORD: kong
      KONG_PROXY_ACCESS_LOG: /dev/stdout
      KONG_ADMIN_ACCESS_LOG: /dev/stdout
      KONG_PROXY_ERROR_LOG: /dev/stderr
      KONG_ADMIN_ERROR_LOG: /dev/stderr
      KONG_ADMIN_LISTEN: 0.0.0.0:8001
      KONG_PLUGINS: bundled
    ports:
      - "8000:8000"
      - "8001:8001"

  konga:
    image: pantsel/konga:latest
    depends_on:
      - kong-database
    environment:
      - DB_ADAPTER=postgres
      - DB_HOST=kong-database
      - DB_USER=kong
      - DB_PASSWORD=kong
      - DB_DATABASE=konga
      - NODE_ENV=development
    ports:
      - "1337:1337"
    restart: always

  keycloak:
    image: quay.io/keycloak/keycloak:24.0
    command: start-dev
    environment:
      KEYCLOAK_ADMIN: admin
      KEYCLOAK_ADMIN_PASSWORD: admin
    ports:
      - "8080:8080"

  quarkus-authz:
    build:
      context: ./quarkus-authz
      dockerfile: Dockerfile
    depends_on:
      - redis
    environment:
      REDIS_HOST: redis
    ports:
      - "8081:8080"

volumes:
  kong_data:
```

---

## 🚀 2. コンテナ起動

```bash
docker compose up -d
```

確認：

```bash
docker ps
```

---

## 🖥️ 3. Konga 初期セットアップ

1. ブラウザで `http://localhost:1337` にアクセス
2. 管理者アカウントを作成（Username, Email, Password）
3. Konga ダッシュボードが表示される

---

## 🔗 4. Kong との接続 (Konga経由)

1. Kongaダッシュボードで「**+ Add Connection**」

2. 以下を入力：

   * **Name**: kong-local
   * **Kong Admin URL**: `http://kong:8001`
   * **Health Check**: ON

3. 「**Create**」→ 接続成功！

---

## 🔐 5. Keycloak 設定（OIDC）

ブラウザで `http://localhost:8080` へアクセスし、以下を実施：

1. ログイン：`admin / admin`

2. Realm 作成：例 `demo-realm`

3. クライアント追加：

   * `client-id`: `quarkus-client`
   * `client authentication`: OFF
   * `Access Type`: public
   * `Valid Redirect URIs`: `http://localhost:8081/*`

4. ユーザー作成：

   * username: `testuser`
   * password: `password`
   * 有効化・認証フロー設定を有効に

---

## ⚙️ 6. Quarkus AuthZ アプリ構成

`application.properties` 例：

```properties
quarkus.oidc.auth-server-url=http://keycloak:8080/realms/demo-realm
quarkus.oidc.client-id=quarkus-client
quarkus.oidc.tls.verification=none
quarkus.oidc.token.issuer=demo-realm

redis.hosts=redis://redis:6379
```

※ `/quarkus-authz/Dockerfile` が存在する必要あり。

---

## 🛡️ 7. Kong で Quarkus + Keycloak 認可構成

Kong 管理APIまたはKongaから：

### 1. Service追加（Quarkus）

```bash
curl -i -X POST http://localhost:8001/services \
  --data name=authz-service \
  --data url=http://quarkus-authz:8080
```

### 2. Route追加

```bash
curl -i -X POST http://localhost:8001/services/authz-service/routes \
  --data paths[]=/secure
```

### 3. Plugin追加（OIDCの場合）

KongでOIDCを直接使うにはOpenID Connect plugin（有償）または他の構成が必要ですが、今回はQuarkusでOIDC処理を行い、KongではAPIゲートウェイとして振る舞います。

---

## ✅ 動作確認

1. ブラウザで `http://localhost:8000/secure` にアクセス
2. 認証が必要 → Keycloakへリダイレクト
3. 認証後、Quarkusアプリが処理
4. Redisにセッション情報が保存される（ログなどで確認）

---

## 📦 オプション（データリセット時）

```bash
docker compose down -v
```

---

## 🎯 まとめ

| コンポーネント    | ポート       | 用途                      |
| ---------- | --------- | ----------------------- |
| Kong       | 8000/8001 | API Gateway / Admin API |
| Konga      | 1337      | Kong GUI管理              |
| Keycloak   | 8080      | OIDC Identity Provider  |
| Quarkus    | 8081      | AuthZサービス               |
| Redis      | 6379      | セッション管理                 |
| PostgreSQL | 内部        | Kong/Kongaデータストア        |

---
