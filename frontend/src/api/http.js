import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '../stores/auth'

let redirectingToLogin = false

const http = axios.create({
  baseURL: '/api',
  timeout: 15000
})

http.interceptors.request.use((config) => {
  const auth = useAuthStore()
  if (auth.token) {
    config.headers.Authorization = `Bearer ${auth.token}`
  }
  return config
})

http.interceptors.response.use(
  (response) => response.data,
  (error) => {
    if (error.response?.status === 401) {
      const auth = useAuthStore()
      auth.logout()
      if (!redirectingToLogin && window.location.pathname !== '/login') {
        redirectingToLogin = true
        ElMessage.warning('登录状态已失效，请重新登录')
        window.location.href = '/login'
      }
      return Promise.reject(error)
    }

    const message = error.response?.data?.message || '请求失败，请稍后重试'
    ElMessage.error(message)
    return Promise.reject(error)
  }
)

export default http
