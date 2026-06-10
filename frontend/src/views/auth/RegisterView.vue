<template>
  <div class="auth-shell">
    <section class="auth-visual">
      <div class="brand-mark">SA</div>
      <h1>建立你的学习工作台</h1>
      <p>注册后即可维护课程表、DDL、健康记录、消费预算和每日习惯。</p>
    </section>
    <section class="auth-panel panel">
      <h2>注册</h2>
      <el-form :model="form" label-position="top">
        <el-form-item label="用户名">
          <el-input v-model="form.username" size="large" />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" size="large" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" size="large" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" size="large" type="password" show-password />
        </el-form-item>
        <el-button type="primary" size="large" :loading="loading" class="full" @click="submit">创建账号</el-button>
      </el-form>
      <p class="switch">已有账号？<router-link to="/login">去登录</router-link></p>
    </section>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '../../stores/auth'

const router = useRouter()
const auth = useAuthStore()
const loading = ref(false)
const form = reactive({ username: '', nickname: '', email: '', password: '' })

const submit = async () => {
  loading.value = true
  try {
    await auth.register(form)
    ElMessage.success('注册成功，请登录')
    router.push('/login')
  } finally {
    loading.value = false
  }
}
</script>

