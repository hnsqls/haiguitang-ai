import { reactive, readonly } from 'vue';

// 定义消息类型
export interface Message {
  content: string;
  type: 'user' | 'ai';
}

// 定义游戏会话
export interface GameSession {
  sessionId: string;
  story: string;
}

// 创建一个简单的状态管理
interface GameState {
  currentSession: GameSession | null;
  messages: Message[];
  isLoading: boolean;
}

// 初始状态
const state = reactive<GameState>({
  currentSession: null,
  messages: [],
  isLoading: false
});

// 方法
const setSession = (session: GameSession) => {
  state.currentSession = session;
};

const addMessage = (message: Message) => {
  state.messages.push(message);
};

const clearMessages = () => {
  state.messages = [];
};

const setLoading = (isLoading: boolean) => {
  state.isLoading = isLoading;
};

// 为简单起见，我们使用UUID v4生成唯一会话ID
const generateSessionId = (): string => {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    const r = Math.random() * 16 | 0,
      v = c === 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
};

// 导出只读状态和方法
export default {
  state: readonly(state),
  setSession,
  addMessage,
  clearMessages,
  setLoading,
  generateSessionId
};
