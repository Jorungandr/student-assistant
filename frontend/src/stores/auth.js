import { defineStore } from 'pinia'
import http from '../api/http'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('student_assistant_token') || '',
    user: JSON.parse(localStorage.getItem('student_assistant_user') || 'null')
  }),
  getters: {
    isLoggedIn: (state) => Boolean(state.token)
  },
  actions: {
    async login(payload) {
      const response = await http.post('/auth/login', payload)
      this.token = response.data.token
      this.user = response.data.user
      localStorage.setItem('student_assistant_token', this.token)
      localStorage.setItem('student_assistant_user', JSON.stringify(this.user))
    },
    async register(payload) {
      return http.post('/auth/register', payload)
    },
    async loadProfile() {
      const response = await http.get('/auth/profile')
      this.user = response.data
      localStorage.setItem('student_assistant_user', JSON.stringify(this.user))
      return this.user
    },
    logout() {
      this.token = ''
      this.user = null
      localStorage.removeItem('student_assistant_token')
      localStorage.removeItem('student_assistant_user')
    }
  }
})

