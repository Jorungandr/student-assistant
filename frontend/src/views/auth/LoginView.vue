<template>
  <div class="auth-shell">
    <section class="auth-visual">
      <div class="brand-mark">SA</div>
      <h1>大学生个人助手</h1>
      <p>课程、任务、健康、预算和目标，收进一个清晰的学习工作台。</p>
    </section>
    <section class="auth-panel panel">
      <h2>登录</h2>
      <el-form :model="form" label-position="top" @submit.prevent="submit">
        <el-form-item label="用户名">
          <el-input v-model="form.username" size="large" autocomplete="username" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" size="large" type="password" autocomplete="current-password" show-password />
        </el-form-item>
        <el-button type="primary" size="large" :loading="loading" class="full" @click="submit">登录</el-button>
      </el-form>
      <p class="switch">还没有账号？<router-link to="/register">立即注册</router-link></p>
    </section>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'

const router = useRouter()
const auth = useAuthStore()
const loading = ref(false)
const form = reactive({ username: '', password: '' })

const submit = async () => {
  loading.value = true
  try {
    await auth.login(form)
    router.push('/dashboard')
  } finally {
    loading.value = false
  }
}
</script>

