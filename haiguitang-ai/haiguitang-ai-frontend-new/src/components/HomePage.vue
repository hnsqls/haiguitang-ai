<template>
  <div class="home-container">
    <div class="home-content">
      <h1 class="title">AI 海龟汤</h1>
      <a-button
        type="primary"
        class="start-button"
        size="large"
        @click="startGame"
        :loading="isLoading"
      >
        {{ isLoading ? '加载中...' : '开始游戏' }}
      </a-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { generateStory } from '../services/api';
import gameStore from '../store/gameStore';

// 定义事件
const emit = defineEmits(['navigate']);
const isLoading = ref(false);

// 开始游戏
const startGame = async () => {
  try {
    isLoading.value = true;

    // 清空之前的消息记录
    gameStore.clearMessages();

    // 调用API获取故事和sessionId
    const storyData = await generateStory();

    // 设置当前会话信息
    gameStore.setSession({
      sessionId: storyData.sessionId,
      story: storyData.story
    });

    // 添加AI的第一条消息
    gameStore.addMessage({
      content: `欢迎来到AI海龟汤游戏！\n\n${storyData.story}\n\n你可以通过提问来获取线索。记住，只能问是/否问题哦！`,
      type: 'ai'
    });

    // 导航到聊天页面
    emit('navigate', 'chat');
  } catch (error) {
    console.error('开始游戏失败:', error);
    // 可以在这里添加错误提示
  } finally {
    isLoading.value = false;
  }
};
</script>

<style scoped>
.home-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100vh;
  background-color: #f5f5f5;
}

.home-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 40px;
  padding: 50px;
  border-radius: 8px;
  border: 1px solid #e8e8e8;
  background-color: white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  width: 90%;
  max-width: 500px;
  text-align: center;
}

.title {
  font-size: 42px;
  font-weight: 500;
  color: #333;
  margin: 0;
}

.start-button {
  min-width: 160px;
  height: 50px;
  font-size: 18px;
}
</style>
