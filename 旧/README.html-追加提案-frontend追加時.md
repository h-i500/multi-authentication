<!DOCTYPE html>
<html>
<head>
<title>README.md-追加提案-frontend追加時.md</title>
<meta http-equiv="Content-type" content="text/html;charset=UTF-8">

<style>
/* https://github.com/microsoft/vscode/blob/master/extensions/markdown-language-features/media/markdown.css */
/*---------------------------------------------------------------------------------------------
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License. See License.txt in the project root for license information.
 *--------------------------------------------------------------------------------------------*/

body {
	font-family: var(--vscode-markdown-font-family, -apple-system, BlinkMacSystemFont, "Segoe WPC", "Segoe UI", "Ubuntu", "Droid Sans", sans-serif);
	font-size: var(--vscode-markdown-font-size, 14px);
	padding: 0 26px;
	line-height: var(--vscode-markdown-line-height, 22px);
	word-wrap: break-word;
}

#code-csp-warning {
	position: fixed;
	top: 0;
	right: 0;
	color: white;
	margin: 16px;
	text-align: center;
	font-size: 12px;
	font-family: sans-serif;
	background-color:#444444;
	cursor: pointer;
	padding: 6px;
	box-shadow: 1px 1px 1px rgba(0,0,0,.25);
}

#code-csp-warning:hover {
	text-decoration: none;
	background-color:#007acc;
	box-shadow: 2px 2px 2px rgba(0,0,0,.25);
}

body.scrollBeyondLastLine {
	margin-bottom: calc(100vh - 22px);
}

body.showEditorSelection .code-line {
	position: relative;
}

body.showEditorSelection .code-active-line:before,
body.showEditorSelection .code-line:hover:before {
	content: "";
	display: block;
	position: absolute;
	top: 0;
	left: -12px;
	height: 100%;
}

body.showEditorSelection li.code-active-line:before,
body.showEditorSelection li.code-line:hover:before {
	left: -30px;
}

.vscode-light.showEditorSelection .code-active-line:before {
	border-left: 3px solid rgba(0, 0, 0, 0.15);
}

.vscode-light.showEditorSelection .code-line:hover:before {
	border-left: 3px solid rgba(0, 0, 0, 0.40);
}

.vscode-light.showEditorSelection .code-line .code-line:hover:before {
	border-left: none;
}

.vscode-dark.showEditorSelection .code-active-line:before {
	border-left: 3px solid rgba(255, 255, 255, 0.4);
}

.vscode-dark.showEditorSelection .code-line:hover:before {
	border-left: 3px solid rgba(255, 255, 255, 0.60);
}

.vscode-dark.showEditorSelection .code-line .code-line:hover:before {
	border-left: none;
}

.vscode-high-contrast.showEditorSelection .code-active-line:before {
	border-left: 3px solid rgba(255, 160, 0, 0.7);
}

.vscode-high-contrast.showEditorSelection .code-line:hover:before {
	border-left: 3px solid rgba(255, 160, 0, 1);
}

.vscode-high-contrast.showEditorSelection .code-line .code-line:hover:before {
	border-left: none;
}

img {
	max-width: 100%;
	max-height: 100%;
}

a {
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}

a:focus,
input:focus,
select:focus,
textarea:focus {
	outline: 1px solid -webkit-focus-ring-color;
	outline-offset: -1px;
}

hr {
	border: 0;
	height: 2px;
	border-bottom: 2px solid;
}

h1 {
	padding-bottom: 0.3em;
	line-height: 1.2;
	border-bottom-width: 1px;
	border-bottom-style: solid;
}

h1, h2, h3 {
	font-weight: normal;
}

table {
	border-collapse: collapse;
}

table > thead > tr > th {
	text-align: left;
	border-bottom: 1px solid;
}

table > thead > tr > th,
table > thead > tr > td,
table > tbody > tr > th,
table > tbody > tr > td {
	padding: 5px 10px;
}

table > tbody > tr + tr > td {
	border-top: 1px solid;
}

blockquote {
	margin: 0 7px 0 5px;
	padding: 0 16px 0 10px;
	border-left-width: 5px;
	border-left-style: solid;
}

code {
	font-family: Menlo, Monaco, Consolas, "Droid Sans Mono", "Courier New", monospace, "Droid Sans Fallback";
	font-size: 1em;
	line-height: 1.357em;
}

body.wordWrap pre {
	white-space: pre-wrap;
}

pre:not(.hljs),
pre.hljs code > div {
	padding: 16px;
	border-radius: 3px;
	overflow: auto;
}

pre code {
	color: var(--vscode-editor-foreground);
	tab-size: 4;
}

/** Theming */

.vscode-light pre {
	background-color: rgba(220, 220, 220, 0.4);
}

.vscode-dark pre {
	background-color: rgba(10, 10, 10, 0.4);
}

.vscode-high-contrast pre {
	background-color: rgb(0, 0, 0);
}

.vscode-high-contrast h1 {
	border-color: rgb(0, 0, 0);
}

.vscode-light table > thead > tr > th {
	border-color: rgba(0, 0, 0, 0.69);
}

.vscode-dark table > thead > tr > th {
	border-color: rgba(255, 255, 255, 0.69);
}

.vscode-light h1,
.vscode-light hr,
.vscode-light table > tbody > tr + tr > td {
	border-color: rgba(0, 0, 0, 0.18);
}

.vscode-dark h1,
.vscode-dark hr,
.vscode-dark table > tbody > tr + tr > td {
	border-color: rgba(255, 255, 255, 0.18);
}

</style>

<style>
/* Tomorrow Theme */
/* http://jmblog.github.com/color-themes-for-google-code-highlightjs */
/* Original theme - https://github.com/chriskempson/tomorrow-theme */

/* Tomorrow Comment */
.hljs-comment,
.hljs-quote {
	color: #8e908c;
}

/* Tomorrow Red */
.hljs-variable,
.hljs-template-variable,
.hljs-tag,
.hljs-name,
.hljs-selector-id,
.hljs-selector-class,
.hljs-regexp,
.hljs-deletion {
	color: #c82829;
}

/* Tomorrow Orange */
.hljs-number,
.hljs-built_in,
.hljs-builtin-name,
.hljs-literal,
.hljs-type,
.hljs-params,
.hljs-meta,
.hljs-link {
	color: #f5871f;
}

/* Tomorrow Yellow */
.hljs-attribute {
	color: #eab700;
}

/* Tomorrow Green */
.hljs-string,
.hljs-symbol,
.hljs-bullet,
.hljs-addition {
	color: #718c00;
}

/* Tomorrow Blue */
.hljs-title,
.hljs-section {
	color: #4271ae;
}

/* Tomorrow Purple */
.hljs-keyword,
.hljs-selector-tag {
	color: #8959a8;
}

.hljs {
	display: block;
	overflow-x: auto;
	color: #4d4d4c;
	padding: 0.5em;
}

.hljs-emphasis {
	font-style: italic;
}

.hljs-strong {
	font-weight: bold;
}
</style>

<style>
/*
 * Markdown PDF CSS
 */

 body {
	font-family: -apple-system, BlinkMacSystemFont, "Segoe WPC", "Segoe UI", "Ubuntu", "Droid Sans", sans-serif, "Meiryo";
	padding: 0 12px;
}

pre {
	background-color: #f8f8f8;
	border: 1px solid #cccccc;
	border-radius: 3px;
	overflow-x: auto;
	white-space: pre-wrap;
	overflow-wrap: break-word;
}

pre:not(.hljs) {
	padding: 23px;
	line-height: 19px;
}

blockquote {
	background: rgba(127, 127, 127, 0.1);
	border-color: rgba(0, 122, 204, 0.5);
}

.emoji {
	height: 1.4em;
}

code {
	font-size: 14px;
	line-height: 19px;
}

/* for inline code */
:not(pre):not(.hljs) > code {
	color: #C9AE75; /* Change the old color so it seems less like an error */
	font-size: inherit;
}

/* Page Break : use <div class="page"/> to insert page break
-------------------------------------------------------- */
.page {
	page-break-after: always;
}

</style>

<script src="https://unpkg.com/mermaid/dist/mermaid.min.js"></script>
</head>
<body>
  <script>
    mermaid.initialize({
      startOnLoad: true,
      theme: document.body.classList.contains('vscode-dark') || document.body.classList.contains('vscode-high-contrast')
          ? 'dark'
          : 'default'
    });
  </script>
<p>やったー！動いたところまで反映した、<strong>更新版 README.md</strong> を用意しました。
そのまま置き換えられるように、全文を下に載せます（Kong は idempotent な <code>PUT</code> を使って「作成 or 更新」できる手順に寄せ、競合ルートの掃除コマンドも入れてあります）。</p>
<hr>
<h1 id="multi-authentication">multi-authentication</h1>
<p>Keycloak（OIDC）× Quarkus（BFF / Aggregator）× Kong（API Gateway）× React（Vite dev-server）で構成する、多段認証・BFF/マッシュアップのサンプルです。
<strong>今回の更新点</strong>として、<code>frontend</code>（Vite）を追加し、<strong>Kong で <code>/app</code> をフロントに、<code>/api/*</code> を Quarkus BFF に</strong>振り分ける形に変更しました。OIDC のコールバックは <strong><code>/login</code></strong> に固定し、BFF 側で <code>/app/</code> に戻す運用です。</p>
<hr>
<h2 id="%E3%82%A2%E3%83%BC%E3%82%AD%E3%83%86%E3%82%AF%E3%83%81%E3%83%A3">アーキテクチャ</h2>
<pre class="hljs"><code><div>[Browser]
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
</div></code></pre>
<h3 id="%E3%83%AD%E3%82%B0%E3%82%A4%E3%83%B3%E3%83%BB%E3%83%AA%E3%83%80%E3%82%A4%E3%83%AC%E3%82%AF%E3%83%88%E3%81%AE%E6%B5%81%E3%82%8C">ログイン・リダイレクトの流れ</h3>
<ol>
<li>
<p>ユーザが <code>http://localhost:8000/app/</code> を開く</p>
</li>
<li>
<p>フロントの「ログイン」→ <code>window.location.assign('/api/login')</code></p>
</li>
<li>
<p>Kong → Quarkus BFF (<code>/api/login</code> → <code>LoginResource#login</code>)</p>
<ul>
<li>未ログインなら <strong><code>/secure</code> へ 302</strong>（Kong 経由で BFF に届く）</li>
<li>BFF の <code>/secure</code> は <code>@Authenticated</code> なので、Quarkus OIDC のコードフローが走る</li>
</ul>
</li>
<li>
<p>Keycloak ログイン → コールバックは <strong><code>/login</code></strong>（Kong → BFF）</p>
</li>
<li>
<p>BFF がセッション確立後、<strong><code>/app/</code> に 302</strong> で戻す</p>
</li>
<li>
<p>以降、<code>/api/me</code> / <code>/api/mashup</code> は BFF 経由で動作</p>
</li>
</ol>
<hr>
<h2 id="%E5%89%8D%E6%8F%90">前提</h2>
<ul>
<li>Docker / Docker Compose が利用できること</li>
<li><code>jq</code> が入っていると便利（Kong の確認・掃除用）</li>
</ul>
<hr>
<h2 id="%E8%B5%B7%E5%8B%95">起動</h2>
<pre class="hljs"><code><div><span class="hljs-comment"># クローン</span>
git <span class="hljs-built_in">clone</span> https://github.com/h-i500/multi-authentication.git
<span class="hljs-built_in">cd</span> multi-authentication

<span class="hljs-comment"># 起動（ビルド込み）</span>
docker compose up -d
</div></code></pre>
<blockquote>
<p>初回は Keycloak の Realm インポートや各サービスのビルドで数分かかります。</p>
</blockquote>
<hr>
<h2 id="kong-%E3%81%AE%E8%A8%AD%E5%AE%9A%E4%BD%9C%E6%88%90-or-%E6%9B%B4%E6%96%B0idempotent">Kong の設定（作成 or 更新：idempotent）</h2>
<p>Kong の <strong>Service</strong> と <strong>Route</strong> を「名前指定の <code>PUT</code>」で作成/更新します。
（既に存在しても上書き更新されるので安全に再実行できます）</p>
<h3 id="1-services">1) Services</h3>
<pre class="hljs"><code><div><span class="hljs-comment"># BFF(Service) → quarkus-authz:8080</span>
curl -sS -X PUT http://localhost:8001/services/api-svc \
  -d url=http://quarkus-authz:8080

<span class="hljs-comment"># Frontend(Service) → frontend:5173 (Vite dev)</span>
curl -sS -X PUT http://localhost:8001/services/frontend-dev-svc \
  -d url=http://frontend:5173
</div></code></pre>
<h3 id="2-routes">2) Routes</h3>
<h4 id="api-%E5%81%B4bff-%E8%A1%8C%E3%81%8D">API 側（BFF 行き）</h4>
<pre class="hljs"><code><div><span class="hljs-comment"># /api → BFF へ。/api を剥がす（/api/* → /*）</span>
curl -sS -X PUT http://localhost:8001/routes/api-route \
  -d service.name=api-svc \
  -d paths[]=/api \
  -d strip_path=<span class="hljs-literal">true</span> \
  -d preserve_host=<span class="hljs-literal">true</span> \
  -d path_handling=v0

<span class="hljs-comment"># OIDC コールバック (/login) は剥がさない（BFF の /login にそのまま渡す）</span>
curl -sS -X PUT http://localhost:8001/routes/login-route \
  -d service.name=api-svc \
  -d paths[]=/login \
  -d strip_path=<span class="hljs-literal">false</span> \
  -d preserve_host=<span class="hljs-literal">true</span> \
  -d path_handling=v0

<span class="hljs-comment"># OIDC 開始の踏み台 (/secure) も剥がさない</span>
curl -sS -X PUT http://localhost:8001/routes/secure-route \
  -d service.name=api-svc \
  -d paths[]=/secure \
  -d strip_path=<span class="hljs-literal">false</span> \
  -d preserve_host=<span class="hljs-literal">true</span> \
  -d path_handling=v0

<span class="hljs-comment"># ログアウト (/logout) も剥がさない</span>
curl -sS -X PUT http://localhost:8001/routes/<span class="hljs-built_in">logout</span>-route \
  -d service.name=api-svc \
  -d paths[]=/<span class="hljs-built_in">logout</span> \
  -d strip_path=<span class="hljs-literal">false</span> \
  -d preserve_host=<span class="hljs-literal">true</span> \
  -d path_handling=v0

<span class="hljs-comment"># （任意）/api/secure を個別に持ちたい場合</span>
curl -sS -X PUT http://localhost:8001/routes/api-secure-route \
  -d service.name=api-svc \
  -d paths[]=/api/secure \
  -d strip_path=<span class="hljs-literal">false</span> \
  -d preserve_host=<span class="hljs-literal">true</span> \
  -d path_handling=v0
</div></code></pre>
<h4 id="frontend-%E5%81%B4">Frontend 側</h4>
<pre class="hljs"><code><div><span class="hljs-comment"># /app → Vite dev にそのまま渡す</span>
<span class="hljs-comment"># Vite の dev サーバとは相性の良い v1 を使用</span>
curl -sS -X PUT http://localhost:8001/routes/frontend-dev-route \
  -d service.name=frontend-dev-svc \
  -d paths[]=/app \
  -d strip_path=<span class="hljs-literal">false</span> \
  -d preserve_host=<span class="hljs-literal">true</span> \
  -d path_handling=v1
</div></code></pre>
<h3 id="3-%E7%AB%B6%E5%90%88%E3%83%AB%E3%83%BC%E3%83%88%E3%81%AE%E6%8E%83%E9%99%A4%E9%87%8D%E8%A6%81">3) 競合ルートの掃除（重要）</h3>
<p>他サービスに向く <code>/secure</code>・<code>/login</code> 等が残っていると、<strong>404 や State エラー</strong>の原因になります。
<code>/secure</code> を例に、BFF 以外に向くルートを削除します。</p>
<pre class="hljs"><code><div>SID_API=$(curl -s http://localhost:8001/services | jq -r <span class="hljs-string">'.data[] | select(.name=="api-svc") | .id'</span>)

<span class="hljs-comment"># /secure が api-svc 以外に向いているルートを削除</span>
<span class="hljs-keyword">for</span> id <span class="hljs-keyword">in</span> $(curl -s http://localhost:8001/routes \
  | jq -r <span class="hljs-string">".data[] | select((.paths|index(\"/secure\")) and .service.id != \"<span class="hljs-variable">$SID_API</span>\") | .id"</span>); <span class="hljs-keyword">do</span>
  curl -sS -X DELETE http://localhost:8001/routes/<span class="hljs-variable">$id</span>
<span class="hljs-keyword">done</span>
</div></code></pre>
<h3 id="4-%E8%A8%AD%E5%AE%9A%E3%81%AE%E7%A2%BA%E8%AA%8D">4) 設定の確認</h3>
<pre class="hljs"><code><div>curl -s http://localhost:8001/routes \
  | jq <span class="hljs-string">'.data[] | {name, paths, strip_path, preserve_host, path_handling, service: .service.id}'</span>
</div></code></pre>
<p><strong>期待する最終形</strong></p>
<ul>
<li><code>login-route</code> … <code>/login</code>（strip_path:false, preserve_host:true, v0）</li>
<li><code>secure-route</code> … <code>/secure</code>（strip_path:false, preserve_host:true, v0）</li>
<li><code>logout-route</code> … <code>/logout</code>（strip_path:false, preserve_host:true, v0）</li>
<li><code>api-route</code> … <code>/api</code>（strip_path:true, preserve_host:true, v0）</li>
<li><code>api-secure-route</code> … <code>/api/secure</code>（strip_path:false, preserve_host:true, v0）※任意</li>
<li><code>frontend-dev-route</code> … <code>/app</code>（strip_path:false, preserve_host:true, <strong>v1</strong>）</li>
</ul>
<hr>
<h2 id="%E3%82%A2%E3%83%97%E3%83%AA%E5%9B%BA%E6%9C%89%E3%81%AE%E3%83%9D%E3%82%A4%E3%83%B3%E3%83%88bff--frontend">アプリ固有のポイント（BFF / Frontend）</h2>
<h3 id="quarkusbff%E8%A8%AD%E5%AE%9A%E3%81%AE%E8%A6%81%E7%82%B9">Quarkus（BFF）設定の要点</h3>
<p><code>quarkus-authz/src/main/resources/application.properties</code>（抜粋）</p>
<pre class="hljs"><code><div><span class="hljs-comment"># OIDC 基本設定</span>
<span class="hljs-meta">quarkus.oidc.auth-server-url</span>=<span class="hljs-string">http://keycloak:8080/realms/demo-realm</span>
<span class="hljs-meta">quarkus.oidc.client-id</span>=<span class="hljs-string">quarkus-client</span>
<span class="hljs-meta">quarkus.oidc.credentials.client-secret.value</span>=<span class="hljs-string">VJEng5YQw6dBE5mf6x5R2tFvU0KMg3KB</span>
<span class="hljs-meta">quarkus.oidc.application-type</span>=<span class="hljs-string">web-app</span>
<span class="hljs-meta">quarkus.http.proxy.proxy-address-forwarding</span>=<span class="hljs-string">true</span>
<span class="hljs-comment">
# OIDC コールバックを /login に固定（/app ではない）</span>
<span class="hljs-meta">quarkus.oidc.authentication.redirect-path</span>=<span class="hljs-string">/login</span>
<span class="hljs-comment"># コールバック後は元URLへ戻さず、LoginResource 側で /app/ に送る</span>
<span class="hljs-meta">quarkus.oidc.authentication.restore-path-after-redirect</span>=<span class="hljs-string">false</span>
<span class="hljs-comment">
# PKCE &amp; state 保護</span>
<span class="hljs-meta">quarkus.oidc.authentication.pkce-required</span>=<span class="hljs-string">true</span>
<span class="hljs-meta">quarkus.oidc.authentication.state-secret</span>=<span class="hljs-string">${STATE_SECRET:change-me-change-me-change-me-1234}</span>
<span class="hljs-comment">
# ログアウト</span>
<span class="hljs-meta">quarkus.oidc.logout.path</span>=<span class="hljs-string">/logout</span>
<span class="hljs-meta">quarkus.oidc.logout.post-logout-path</span>=<span class="hljs-string">/app/</span>
<span class="hljs-comment">
# そのほか（Redis セッション保持、トークン戦略など）はファイル参照</span>
</div></code></pre>
<p><code>LoginResource.java</code>（意図）</p>
<ul>
<li><code>GET /login</code>（PermitAll）
未ログインなら <strong><code>/secure</code> へ 302</strong>、ログイン済みなら <strong><code>/app/</code> へ 302</strong></li>
<li><code>GET /secure</code>（Authenticated）
OIDC のコードフローを開始 → 認証後は <code>/app/</code> に 302</li>
<li><code>GET /logout</code>
セッション破棄 → <code>/app/</code> に 302</li>
</ul>
<h3 id="frontendvite--react">Frontend（Vite / React）</h3>
<p><code>frontend/src/App.tsx</code>（抜粋）</p>
<pre class="hljs"><code><div>&lt;button type=&quot;button&quot; onClick={() =&gt; window.location.assign('/api/login')}&gt;
  ログイン
&lt;/button&gt;
&lt;button type=&quot;button&quot; onClick={() =&gt; window.location.assign('/api/logout')}&gt;
  ログアウト
&lt;/button&gt;

&lt;button onClick={() =&gt; apiGet('/me')}&gt;/api/me&lt;/button&gt;
&lt;button onClick={() =&gt; apiGet('/mashup')}&gt;/api/mashup&lt;/button&gt;
&lt;a href=&quot;/app/&quot; style={{ marginLeft: 8 }}&gt;トップへ&lt;/a&gt;
</div></code></pre>
<ul>
<li>**ログイン/ログアウトはトップレベル遷移（<code>window.location.assign</code>）**が必須</li>
<li>API は <strong><code>/api/*</code></strong> で BFF に到達</li>
</ul>
<hr>
<h2 id="%E5%8B%95%E4%BD%9C%E7%A2%BA%E8%AA%8D">動作確認</h2>
<ol>
<li>
<p><code>http://localhost:8000/app/</code> をブラウザで開く</p>
</li>
<li>
<p>「ログイン」を押下</p>
<ul>
<li>Keycloak ログイン画面 → 認証</li>
<li>コールバック <code>/login</code> → BFF が <code>/app/</code> に戻す</li>
</ul>
</li>
<li>
<p><code>/api/me</code> と <code>/api/mashup</code> が 200 で結果を返すこと</p>
</li>
<li>
<p>「ログアウト」→ <code>/app/</code> に戻り、<code>/api/me</code> が再びログインを促すこと</p>
</li>
</ol>
<hr>
<h2 id="%E3%82%88%E3%81%8F%E3%81%82%E3%82%8B%E3%83%8F%E3%83%9E%E3%82%8A%E3%81%A9%E3%81%93%E3%82%8D">よくあるハマりどころ</h2>
<ul>
<li>
<p><strong><code>/login</code> で 404（Resource not found）</strong>
→ Kong の <code>login-route</code> が <strong><code>strip_path=false</code></strong> / <code>preserve_host=true</code> になっているか確認。
誤って <code>strip_path=true</code> だと BFF の <code>/login</code> まで届かず 404 になります。</p>
</li>
<li>
<p><strong><code>State parameter can not be empty</code> のログ</strong>
→ <code>/login</code> を直接叩いた場合に出ることがある正常なログ。
ユーザの導線は <code>/api/login</code> → <code>/secure</code> → <code>/login</code> → <code>/app/</code> を想定。</p>
</li>
<li>
<p><strong>認証後に <code>/app/</code> へ戻らない</strong>
→ <code>quarkus.oidc.authentication.redirect-path=/login</code> と
<code>quarkus.oidc.authentication.restore-path-after-redirect=false</code> が一致しているか、
そして <strong><code>/login</code> が BFF へ到達</strong>しているか（Kong ルート）を確認。</p>
</li>
<li>
<p><strong><code>/secure</code> に他サービス向きのルートが残っている</strong>
→ 競合ルートの掃除スクリプトを実行して除去（上記参照）。</p>
</li>
<li>
<p><strong>Keycloak のロール / groups が期待通り出ない</strong>
→ <code>quarkus.oidc.roles.*</code> の設定や、Access Token に載るクレーム（<code>realm_access/roles</code> など）を確認。</p>
</li>
<li>
<p><strong>HTTPS/TLS 終端を入れた</strong>
→ Cookie の <code>Secure</code> / <code>SameSite</code> 設定を見直し。
<code>quarkus.oidc.authentication.cookie-force-secure=true</code> などを検討。</p>
</li>
</ul>
<hr>
<h2 id="%E4%BB%98%E9%8C%B2kong-%E8%A8%AD%E5%AE%9A%E3%82%92%E3%81%BE%E3%82%8B%E3%81%A3%E3%81%A8%E5%86%8D%E9%81%A9%E7%94%A8%E3%81%97%E3%81%9F%E3%81%84%E3%81%A8%E3%81%8D">付録：Kong 設定をまるっと再適用したいとき</h2>
<p>何度でも実行可（作成 or 更新）。競合ルートの掃除も含めて再適用できます。</p>
<pre class="hljs"><code><div><span class="hljs-comment"># Services</span>
curl -sS -X PUT http://localhost:8001/services/api-svc -d url=http://quarkus-authz:8080
curl -sS -X PUT http://localhost:8001/services/frontend-dev-svc -d url=http://frontend:5173

<span class="hljs-comment"># Routes</span>
curl -sS -X PUT http://localhost:8001/routes/api-route \
  -d service.name=api-svc -d paths[]=/api -d strip_path=<span class="hljs-literal">true</span> -d preserve_host=<span class="hljs-literal">true</span> -d path_handling=v0
curl -sS -X PUT http://localhost:8001/routes/login-route \
  -d service.name=api-svc -d paths[]=/login -d strip_path=<span class="hljs-literal">false</span> -d preserve_host=<span class="hljs-literal">true</span> -d path_handling=v0
curl -sS -X PUT http://localhost:8001/routes/secure-route \
  -d service.name=api-svc -d paths[]=/secure -d strip_path=<span class="hljs-literal">false</span> -d preserve_host=<span class="hljs-literal">true</span> -d path_handling=v0
curl -sS -X PUT http://localhost:8001/routes/<span class="hljs-built_in">logout</span>-route \
  -d service.name=api-svc -d paths[]=/<span class="hljs-built_in">logout</span> -d strip_path=<span class="hljs-literal">false</span> -d preserve_host=<span class="hljs-literal">true</span> -d path_handling=v0
curl -sS -X PUT http://localhost:8001/routes/api-secure-route \
  -d service.name=api-svc -d paths[]=/api/secure -d strip_path=<span class="hljs-literal">false</span> -d preserve_host=<span class="hljs-literal">true</span> -d path_handling=v0
curl -sS -X PUT http://localhost:8001/routes/frontend-dev-route \
  -d service.name=frontend-dev-svc -d paths[]=/app -d strip_path=<span class="hljs-literal">false</span> -d preserve_host=<span class="hljs-literal">true</span> -d path_handling=v1

<span class="hljs-comment"># 競合ルート掃除（/secure の例）</span>
SID_API=$(curl -s http://localhost:8001/services | jq -r <span class="hljs-string">'.data[] | select(.name=="api-svc") | .id'</span>)
<span class="hljs-keyword">for</span> id <span class="hljs-keyword">in</span> $(curl -s http://localhost:8001/routes \
  | jq -r <span class="hljs-string">".data[] | select((.paths|index(\"/secure\")) and .service.id != \"<span class="hljs-variable">$SID_API</span>\") | .id"</span>); <span class="hljs-keyword">do</span>
  curl -sS -X DELETE http://localhost:8001/routes/<span class="hljs-variable">$id</span>
<span class="hljs-keyword">done</span>

<span class="hljs-comment"># 確認</span>
curl -s http://localhost:8001/routes \
  | jq <span class="hljs-string">'.data[] | {name, paths, strip_path, preserve_host, path_handling, service: .service.id}'</span>
</div></code></pre>
<hr>
<h2 id="%E5%8F%82%E8%80%83%E4%B8%BB%E8%A6%81%E3%82%A8%E3%83%B3%E3%83%89%E3%83%9D%E3%82%A4%E3%83%B3%E3%83%88">参考：主要エンドポイント</h2>
<ul>
<li>
<p>フロント：<code>http://localhost:8000/app/</code></p>
</li>
<li>
<p>API(BFF)：<code>http://localhost:8000/api/*</code></p>
<ul>
<li>ログイン：<code>/api/login</code>（→ <code>/secure</code> → Keycloak → <code>/login</code> → <code>/app/</code>）</li>
<li>ログアウト：<code>/api/logout</code></li>
<li>認証ユーザ：<code>/api/me</code></li>
<li>マッシュアップ：<code>/api/mashup</code></li>
</ul>
</li>
<li>
<p>Keycloak：<code>http://localhost:8080/</code>（realm: <code>demo-realm</code>）</p>
</li>
<li>
<p>Konga（任意）：<code>http://localhost:1337/</code></p>
</li>
</ul>
<hr>
<p>以上です。
このまま <code>README.md</code> に貼り付ければ、frontend 追加後の構成・手順・Kong 設定まで一通り追従できます。必要なら、Kong 設定を <code>deck</code>/宣言的設定に移す版も作れますよ。</p>

</body>
</html>
