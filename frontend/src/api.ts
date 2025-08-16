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

// // api.ts
// export async function apiGet(path: string) {
//   const res = await fetch(`/api${path}`, { credentials: 'include' });
//   if (!res.ok) throw new Error(`${res.status} ${res.statusText}`);
//   return res.json();
// }


// frontend/src/api.ts
export class ApiError extends Error {
  status?: number;
  body?: any;
  constructor(message: string, status?: number, body?: any) {
    super(message);
    this.status = status;
    this.body = body;
  }
}

/** 共通GET。/api を自動付与し、401/403/500 を人間向けに変換して投げる */
export async function apiGet(path: string) {
  const p = path.startsWith('/') ? path : `/${path}`;
  const url = p.startsWith('/api') ? p : `/api${p}`;

  const res = await fetch(url, {
    credentials: 'include', // セッションクッキー送信
    headers: { Accept: 'application/json' },
  });

  const ctype = res.headers.get('content-type') || '';
  const isJson = ctype.includes('application/json');
  const payload = await (isJson ? res.json().catch(() => undefined)
                                : res.text().catch(() => undefined));

  if (!res.ok) {
    // 代表的なケースをわかりやすい日本語に
    if (res.status === 401) {
      throw new ApiError('未ログインまたはセッション切れです。ログインしてください。', 401, payload);
    }
    if (res.status === 403) {
      throw new ApiError('権限エラー：この操作を行う権限がありません。', 403, payload);
    }
    // BFF が下流の 403 を握りつぶして 500 にする実装の場合のフォールバック
    if (res.status === 500) {
      const text = typeof payload === 'string' ? payload : JSON.stringify(payload || '');
      if (text.toLowerCase().includes('forbidden') || text.includes('403')) {
        throw new ApiError('権限エラー：この操作を行う権限がありません。', 403, payload);
      }
    }

    throw new ApiError(`APIエラー: ${res.status}`, res.status, payload);
  }

  return payload;
}
