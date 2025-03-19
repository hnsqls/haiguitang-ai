<template>
  <div class="chat-layout">
    <!-- 左侧菜单 -->
    <div class="sidebar">
      <a-button
        class="menu-item"
        v-for="(item, index) in menuItems"
        :key="index"
        @click="handleMenuClick(item)"
      >
        {{ item.title }}
      </a-button>
    </div>

    <!-- 右侧聊天区域 -->
    <div class="chat-container">
      <div class="chat-header">
        <h2>AI 海龟汤游戏</h2>
      </div>

      <div class="chat-messages" ref="messagesContainer">
        <div
          v-for="(message, index) in messages"
          :key="index"
          :class="['message-wrapper', message.type === 'user' ? 'user-message' : 'ai-message']"
        >
          <div class="avatar" v-if="message.type !== 'user'">AI</div>
          <div class="message-content">
            {{ message.content }}
          </div>
          <div class="avatar" v-if="message.type === 'user'">用户</div>
        </div>
      </div>

      <div class="chat-input">
        <a-input
          v-model:value="userInput"
          placeholder="请输入您的问题或猜想..."
          @press-enter="sendMessage"
        />
        <a-button type="primary" @click="sendMessage">发送</a-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onUpdated, nextTick, defineEmits } from 'vue'

const emit = defineEmits(['navigate'])

// 菜单项数据
const menuItems = [
  { title: '新游戏', action: 'new-game' },
  { title: '游戏规则', action: 'rules' },
  { title: '历史记录', action: 'history' },
  { title: '排行榜', action: 'leaderboard' },
  { title: '设置', action: 'settings' },
]

// 处理菜单点击
const handleMenuClick = (item: any) => {
  if (item.action === 'new-game') {
    // 重置游戏，清空消息记录
    messages.value = [
      {
        content:
          '欢迎来到AI海龟汤游戏！我已经准备好了一个谜题，你可以通过提问来获取线索。记住，只能问是/否问题哦！',
        type: 'ai',
      },
    ]
  } else if (item.action === 'rules') {
    // 显示游戏规则
    messages.value.push({
      content:
        '游戏规则：\n1. 我会为你准备一个谜题\n2. 你只能问是/否问题\n3. 你需要根据线索猜出谜底\n4. 当你认为找到了答案，可以直接说出你的猜测',
      type: 'ai',
    })
  } else if (item.action === 'history') {
    // 这里可以显示历史记录
  } else if (item.action === 'leaderboard') {
    // 这里可以显示排行榜
  } else if (item.action === 'settings') {
    // 这里可以显示设置项
  }
}

// 聊天消息
const messages = ref([
  {
    content:
      '欢迎来到AI海龟汤游戏！我已经准备好了一个谜题，你可以通过提问来获取线索。记住，只能问是/否问题哦！',
    type: 'ai',
  },
])

const userInput = ref('')
const messagesContainer = ref<HTMLElement | null>(null)

// 发送消息
const sendMessage = () => {
  if (!userInput.value.trim()) return

  // 添加用户消息
  messages.value.push({ content: userInput.value, type: 'user' })

  // 模拟AI回复
  setTimeout(() => {
    messages.value.push({
      content:
        '这是一个示例回复。在实际游戏中，这里将根据您的问题给出是/否的回答，或提供其他相关信息。',
      type: 'ai',
    })
  }, 500)

  // 清空输入框
  userInput.value = ''
}

// 消息列表更新后滚动到底部
onUpdated(() => {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
  })
})
</script>

<style scoped>
.chat-layout {
  display: flex;
  width: 100%;
  height: 100vh;
  overflow: hidden;
  background-color: white;
}

.sidebar {
  width: 200px;
  padding: 20px;
  background-color: #fafafa;
  border-right: 1px solid #f0f0f0;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.menu-item {
  height: 40px;
  text-align: left;
  border-radius: 4px;
}

.chat-container {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.chat-header {
  padding: 15px 20px;
  border-bottom: 1px solid #f0f0f0;
  text-align: center;
}

.chat-header h2 {
  margin: 0;
  font-size: 18px;
  color: #333;
}

.chat-messages {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.message-wrapper {
  display: flex;
  align-items: flex-start;
  margin-bottom: 12px;
}

.user-message {
  flex-direction: row-reverse;
  margin-left: auto;
}

.ai-message {
  margin-right: auto;
}

.avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background-color: #1890ff;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  margin: 0 8px;
}

.user-message .avatar {
  background-color: #52c41a;
}

.message-content {
  max-width: 60%;
  padding: 10px 14px;
  border-radius: 8px;
  word-break: break-word;
  line-height: 1.6;
}

.user-message .message-content {
  background-color: #f0f7ff;
  border: 1px solid #d6e4ff;
}

.ai-message .message-content {
  background-color: #f5f5f5;
  border: 1px solid #e8e8e8;
}

.chat-input {
  display: flex;
  padding: 15px;
  border-top: 1px solid #f0f0f0;
  gap: 10px;
}

.chat-input :deep(.ant-input) {
  border-radius: 4px;
}
</style>
