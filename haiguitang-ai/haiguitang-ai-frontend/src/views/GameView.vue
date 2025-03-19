<script setup lang="ts">
import { ref } from 'vue'
import { Button, Input, Modal } from 'ant-design-vue'

// 定义谜题和答案
const puzzle = ref('一个人走进一家餐厅，点了一份海龟汤，喝了一口后突然冲出餐厅自杀了。为什么？')
const answer = ref(
  '因为他喝的海龟汤是用他失散多年的亲人的肉做的，他尝出了味道，意识到真相后无法承受。',
)

// 定义用户输入
const userInput = ref('')

// 定义对话框的显示状态
const isDialogVisible = ref(false)

// 显示答案的函数
const showAnswer = () => {
  isDialogVisible.value = true
}
</script>

<template>
  <div class="game-container">
    <h1>海龟汤游戏</h1>
    <div class="puzzle-section">
      <h2>谜题</h2>
      <p>{{ puzzle }}</p>
    </div>

    <div class="input-section">
      <h2>你的答案</h2>
      <Input
        v-model:value="userInput"
        placeholder="请输入你的答案"
        style="width: 300px; margin-bottom: 20px"
      />
      <Button type="primary" @click="showAnswer">提交答案</Button>
    </div>

    <!-- 答案对话框 -->
    <Modal
      v-model:visible="isDialogVisible"
      title="正确答案"
      :footer="null"
      @cancel="isDialogVisible = false"
    >
      <p>{{ answer }}</p>
      <Button type="primary" @click="isDialogVisible = false">关闭</Button>
    </Modal>
  </div>
</template>

<style scoped>
.game-container {
  text-align: center;
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

h1 {
  font-size: 2.5rem;
  margin-bottom: 20px;
  color: #333;
}

h2 {
  font-size: 1.8rem;
  margin-bottom: 15px;
  color: #555;
}

.puzzle-section {
  margin-bottom: 30px;
}

.puzzle-section p {
  font-size: 1.2rem;
  color: #666;
}

.input-section {
  margin-bottom: 30px;
}
</style>
