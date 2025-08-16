// frontend/src/App.tsx
import React, { useState } from 'react'
import { apiGet, ApiError } from './api'   // ★ 追加：ApiError をインポート

export default function App() {
  const [mashup, setMashup] = useState<any>(null)
  const [me, setMe] = useState<any>(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  async function callMashup() {
    setLoading(true); setError(null)
    try {
      const data = await apiGet('/mashup') // BFF -> Service A/B
      setMashup(data)
    } catch (e: any) {
      if (e instanceof ApiError) {
        setError(e.message) // 401/403/500→人間向けメッセージ
      } else {
        setError('処理に失敗しました。しばらくしてから再度お試しください。')
      }
    } finally { setLoading(false) }
  }

  async function whoAmI() {
    setLoading(true); setError(null)
    try {
      const data = await apiGet('/me') // 認証ユーザ情報
      setMe(data)
    } catch (e: any) {
      if (e instanceof ApiError) {
        setError(e.message)
      } else {
        setError('処理に失敗しました。しばらくしてから再度お試しください。')
      }
    } finally { setLoading(false) }
  }

  return (
    <main style={{ fontFamily: 'ui-sans-serif, system-ui', padding: 24 }}>
      <h1 style={{ fontSize: 22, marginBottom: 12 }}>Multi-Authn Demo (React + Quarkus BFF)</h1>

      <div style={{ display: 'flex', gap: 8, flexWrap: 'wrap' }}>
        <button type="button" onClick={() => window.location.assign('/api/login')}>
          ログイン
        </button>
        <button type="button" onClick={() => window.location.assign('/api/logout')}>
          ログアウト
        </button>

        <button onClick={whoAmI}>/api/me</button>
        <button onClick={callMashup}>/api/mashup</button>
        <a href="/app/" style={{ marginLeft: 8 }}>トップへ</a>
      </div>

      {loading && <p>Loading...</p>}
      {error && <p style={{ color: 'crimson' }}>{error}</p>}

      <section style={{ marginTop: 16 }}>
        <h2 style={{ fontSize: 18 }}>me</h2>

        {/* ここは前回追加の roles 表示（me.roles があれば一覧化） */}
        {Array.isArray(me?.roles) && me.roles.length > 0 && (
          <ul>{me.roles.map((r: string) => <li key={r}>{r}</li>)}</ul>
        )}

        <pre style={{ background: '#111', color: '#eee', padding: 12, borderRadius: 8 }}>
{JSON.stringify(me, null, 2)}
        </pre>
      </section>

      <section style={{ marginTop: 16 }}>
        <h2 style={{ fontSize: 18 }}>mashup</h2>
        <pre style={{ background: '#111', color: '#eee', padding: 12, borderRadius: 8 }}>
{JSON.stringify(mashup, null, 2)}
        </pre>
      </section>
    </main>
  )
}
