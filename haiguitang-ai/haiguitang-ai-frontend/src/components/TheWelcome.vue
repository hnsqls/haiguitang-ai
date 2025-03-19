<script setup lang="ts">
import { ref } from 'vue'
import { Button } from 'ant-design-vue'
import { useRouter } from 'vue-router' // 引入 useRouter

// 获取路由实例
const router = useRouter()

// 定义一个状态来控制游戏是否开始
const gameStarted = ref(false)

// 开始游戏的函数
const startGame = () => {
  gameStarted.value = true
  // 跳转到游戏页面
  router.push('/game')
}
</script>

<template>
  <div class="container">
    <div class="home">
      <h1>欢迎来到海龟汤游戏</h1>
      <p>这是一个推理和解谜的游戏，点击下面的按钮开始游戏。</p>

      <!-- 使用 Ant Design Vue 的 Button 组件 -->
      <Button
        type="primary"
        @click="startGame"
        v-if="!gameStarted"
        size="large"
        style="font-size: 1.2rem; padding: 0 40px; height: 50px"
      >
        开始游戏
      </Button>

      <!-- 游戏开始后的内容 -->
      <div v-if="gameStarted">
        <p>游戏已经开始，请享受游戏！</p>
        <!-- 这里可以添加更多的游戏内容 -->
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 容器样式，使内容居中 */
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; /* 使容器占满整个视口高度 */
}

/* 内容区域样式 */
.home {
  text-align: center;
  max-width: 600px; /* 限制内容宽度 */
  padding: 40px;
}

h1 {
  font-size: 2.5rem;
  margin-bottom: 20px;
  color: #333; /* 标题颜色 */
}

p {
  font-size: 1.2rem;
  margin-bottom: 30px;
  color: #666; /* 文字颜色 */
}
</style>
