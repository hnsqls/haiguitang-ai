import axios from 'axios';

// 定义响应类型
interface ApiResponse<T> {
  code: number;
  data: T;
  message: string;
}

// 定义故事返回的类型
interface StoryResponse {
  sessionId: string;
  story: string;
}

// 创建axios实例
const api = axios.create({
  baseURL: 'http://localhost:8113/api/haiguitang',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
});

// 处理生成故事的响应，返回{sessionId, story}的格式
export const generateStory = async (): Promise<StoryResponse> => {
  try {
    const response = await api.get('/generate');
    const responseData = response.data as ApiResponse<string>;

    // 从响应中解析sessionId和故事内容
    // 格式：{code: 0, data: "sessionId|故事内容", message: "ok"}
    if (responseData.code === 0 && responseData.data) {
      const parts = responseData.data.split('|');
      if (parts.length >= 2) {
        const sessionId = parts[0].trim();
        const story = parts.slice(1).join('|'); // 防止故事中也包含"|"
        return { sessionId, story };
      }
    }
    throw new Error('响应格式错误');
  } catch (error) {
    console.error('生成故事失败:', error);
    throw error;
  }
};

export const handleUserInput = async (sessionId: string, userInput: string): Promise<string> => {
  try {
    // 使用URLSearchParams来正确处理请求参数
    const params = new URLSearchParams();
    params.append('sessionId', sessionId);
    params.append('userInput', userInput);

    const response = await api.post('/interact', params, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    });

    const responseData = response.data as ApiResponse<string>;

    // 从响应中提取AI的回复
    if (responseData.code === 0) {
      return responseData.data;
    }
    throw new Error('处理用户输入失败: ' + responseData.message);
  } catch (error) {
    console.error('处理用户输入失败:', error);
    throw error;
  }
};

export default api;
