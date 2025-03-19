import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  build: {
    outDir: 'build' // 设置构建输出目录为build
  },
  server: {
    // 允许跨域请求
    cors: true
  }
})
