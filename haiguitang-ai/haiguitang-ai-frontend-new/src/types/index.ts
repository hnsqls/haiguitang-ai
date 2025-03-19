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
