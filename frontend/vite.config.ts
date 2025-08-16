import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// // Kong で /app にルーティングするため、アセットの base を /app/ に固定
// export default defineConfig({
//   plugins: [react()],
//   base: '/app/'
// })

export default defineConfig({
  base: '/app/',
  server: {
    host: true,
    allowedHosts: ['localhost'],
    watch: { usePolling: true, interval: 300 }, // 追加
  },
})