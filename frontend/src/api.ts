// export async function apiGet(path: string) {
//   const res = await fetch(`/api${path}`, {
//     credentials: 'include', // ← Quarkus のセッション Cookie を同一オリジンで送る
//   })
//   if (res.status === 401) {
//     // 未ログイン時は Quarkus の保護ルートへ
//     location.href = '/secure'
//     throw new Error('Unauthenticated')
//   }
//   if (!res.ok) {
//     const text = await res.text()
//     throw new Error(`${res.status}: ${text}`)
//   }
//   const contentType = res.headers.get('content-type') || ''
//   return contentType.includes('application/json') ? res.json() : res.text()
// }

// api.ts
export async function apiGet(path: string) {
  const res = await fetch(`/api${path}`, { credentials: 'include' });
  if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);
  return res.json();
}
