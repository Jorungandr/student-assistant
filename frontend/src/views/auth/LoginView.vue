<template>
  <div class="login-page">
    <section class="login-hero">
      <div class="brand-row">
        <span class="brand-badge">SA</span>
        <div>
          <strong>大学生个人助手</strong>
          <small>Study Assistant</small>
        </div>
      </div>

      <div class="hero-copy">
        <p class="eyebrow">课程 · 任务 · 健康 · 消费 · 目标</p>
        <h1>把每天的学习和生活，整理成清晰可执行的计划。</h1>
        <p>
          面向大学生日常管理场景，集中处理课程表、DDL、学习统计、健康记录、
          预算提醒和习惯打卡，让首页看板真正成为一天的控制台。
        </p>
      </div>

      <div class="preview-board" aria-hidden="true">
        <div class="preview-top">
          <span>今日概览</span>
          <strong>82%</strong>
        </div>
        <div class="preview-grid">
          <div>
            <BookOpen :size="18" />
            <strong>2</strong>
            <span>今日课程</span>
          </div>
          <div>
            <ClipboardCheck :size="18" />
            <strong>4</strong>
            <span>待办任务</span>
          </div>
          <div>
            <Droplets :size="18" />
            <strong>1.8L</strong>
            <span>饮水记录</span>
          </div>
          <div>
            <Target :size="18" />
            <strong>3</strong>
            <span>进行目标</span>
          </div>
        </div>
      </div>
    </section>

    <section class="login-card">
      <div class="card-head">
        <div>
          <p class="eyebrow">Welcome back</p>
          <h2>登录工作台</h2>
        </div>
        <ShieldCheck class="head-icon" :size="30" />
      </div>

      <el-form :model="form" label-position="top" @submit.prevent="submit">
        <el-form-item label="用户名">
          <el-input
            v-model.trim="form.username"
            size="large"
            autocomplete="username"
            placeholder="请输入用户名"
            :prefix-icon="UserRound"
            @keyup.enter="submit"
          />
        </el-form-item>
        <el-form-item label="密码">
          <el-input
            v-model="form.password"
            size="large"
            type="password"
            autocomplete="current-password"
            placeholder="请输入密码"
            show-password
            :prefix-icon="LockKeyhole"
            @keyup.enter="submit"
          />
        </el-form-item>

        <div class="form-tools">
          <el-checkbox v-model="remember">记住登录状态</el-checkbox>
          <button type="button" class="text-button" @click="fillDemo">使用演示账号</button>
        </div>

        <el-button type="primary" size="large" :loading="loading" class="full" @click="submit">
          登录系统
        </el-button>
      </el-form>

      <div class="demo-card">
        <div>
          <strong>演示账号</strong>
          <span>用于课堂展示和功能验收，已内置完整示例数据。</span>
        </div>
        <code>demo / 123456</code>
      </div>

      <p class="switch">还没有账号？<router-link to="/register">立即注册</router-link></p>
    </section>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import {
  BookOpen,
  ClipboardCheck,
  Droplets,
  LockKeyhole,
  ShieldCheck,
  Target,
  UserRound
} from 'lucide-vue-next'
import { useAuthStore } from '../../stores/auth'

const router = useRouter()
const auth = useAuthStore()
const loading = ref(false)
const remember = ref(true)
const form = reactive({ username: '', password: '' })

const fillDemo = () => {
  form.username = 'demo'
  form.password = '123456'
}

const submit = async () => {
  if (!form.username || !form.password) {
    ElMessage.warning('请输入用户名和密码')
    return
  }

  loading.value = true
  try {
    await auth.login(form)
    router.push('/dashboard')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: grid;
  grid-template-columns: minmax(0, 1.15fr) minmax(380px, 460px);
  gap: 30px;
  padding: 34px;
  background:
    radial-gradient(circle at 12% 18%, rgba(23, 168, 139, 0.22), transparent 30%),
    radial-gradient(circle at 82% 8%, rgba(24, 104, 219, 0.20), transparent 28%),
    linear-gradient(135deg, #eef6ff 0%, #f7fbff 48%, #edf5f2 100%);
}

.login-hero,
.login-card {
  position: relative;
  overflow: hidden;
  border: 1px solid rgba(148, 163, 184, 0.28);
  border-radius: 8px;
  box-shadow: 0 24px 70px rgba(15, 32, 53, 0.12);
}

.login-hero {
  min-height: 650px;
  padding: 42px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  color: #eef7ff;
  background:
    linear-gradient(140deg, rgba(15, 32, 53, 0.96), rgba(15, 76, 129, 0.92) 52%, rgba(23, 168, 139, 0.82)),
    linear-gradient(90deg, rgba(255,255,255,0.12) 1px, transparent 1px),
    linear-gradient(0deg, rgba(255,255,255,0.10) 1px, transparent 1px);
  background-size: auto, 42px 42px, 42px 42px;
}

.login-hero::after {
  content: "";
  position: absolute;
  right: -90px;
  bottom: -130px;
  width: 360px;
  height: 360px;
  border: 1px solid rgba(255, 255, 255, 0.22);
  border-radius: 50%;
}

.brand-row {
  position: relative;
  z-index: 1;
  display: flex;
  align-items: center;
  gap: 14px;
}

.brand-row strong,
.brand-row small {
  display: block;
}

.brand-row strong {
  font-size: 20px;
}

.brand-row small {
  margin-top: 3px;
  color: rgba(238, 247, 255, 0.68);
}

.brand-badge {
  width: 48px;
  height: 48px;
  display: grid;
  place-items: center;
  border: 1px solid rgba(255, 255, 255, 0.46);
  border-radius: 8px;
  color: #a7f3d0;
  font-weight: 800;
}

.hero-copy {
  position: relative;
  z-index: 1;
  max-width: 670px;
}

.eyebrow {
  margin: 0 0 12px;
  color: #17a88b;
  font-size: 13px;
  font-weight: 700;
}

.hero-copy .eyebrow {
  color: #9ee6d7;
}

.hero-copy h1 {
  margin: 0;
  max-width: 720px;
  font-size: 46px;
  line-height: 1.18;
  font-weight: 800;
  letter-spacing: 0;
}

.hero-copy p:last-child {
  max-width: 620px;
  margin: 20px 0 0;
  color: rgba(238, 247, 255, 0.78);
  font-size: 17px;
  line-height: 1.8;
}

.preview-board {
  position: relative;
  z-index: 1;
  width: min(680px, 100%);
  padding: 20px;
  background: rgba(255, 255, 255, 0.12);
  border: 1px solid rgba(255, 255, 255, 0.18);
  border-radius: 8px;
  backdrop-filter: blur(12px);
}

.preview-top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 16px;
  color: rgba(238, 247, 255, 0.78);
}

.preview-top strong {
  color: #ffffff;
  font-size: 30px;
}

.preview-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 10px;
}

.preview-grid div {
  min-height: 108px;
  padding: 14px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border-radius: 8px;
  background: rgba(255, 255, 255, 0.14);
}

.preview-grid svg {
  color: #a7f3d0;
}

.preview-grid strong {
  font-size: 25px;
}

.preview-grid span {
  color: rgba(238, 247, 255, 0.72);
  font-size: 13px;
}

.login-card {
  align-self: center;
  padding: 34px;
  background: rgba(255, 255, 255, 0.93);
  backdrop-filter: blur(18px);
}

.card-head {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 18px;
  margin-bottom: 28px;
}

.card-head h2 {
  margin: 0;
  color: #0f2035;
  font-size: 30px;
  line-height: 1.2;
}

.head-icon {
  flex: 0 0 auto;
  color: #1868db;
}

.form-tools {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  margin: -4px 0 18px;
}

.text-button {
  padding: 0;
  border: 0;
  background: transparent;
  color: #1868db;
  cursor: pointer;
}

.text-button:hover {
  color: #0d3b78;
}

.full {
  width: 100%;
  height: 44px;
  font-weight: 700;
}

.demo-card {
  margin-top: 20px;
  padding: 14px;
  display: grid;
  grid-template-columns: minmax(0, 1fr) auto;
  gap: 12px;
  align-items: center;
  border: 1px solid rgba(24, 104, 219, 0.16);
  border-radius: 8px;
  background: linear-gradient(135deg, rgba(24, 104, 219, 0.07), rgba(23, 168, 139, 0.08));
}

.demo-card strong,
.demo-card span {
  display: block;
}

.demo-card span {
  margin-top: 4px;
  color: #647084;
  font-size: 13px;
  line-height: 1.5;
}

.demo-card code {
  padding: 8px 10px;
  border-radius: 8px;
  color: #0d3b78;
  background: #ffffff;
  border: 1px solid rgba(24, 104, 219, 0.15);
  font-family: "Cascadia Mono", Consolas, monospace;
  white-space: nowrap;
}

.switch {
  margin: 20px 0 0;
  color: #647084;
  text-align: center;
}

.switch a {
  color: #1868db;
  font-weight: 700;
  text-decoration: none;
}

:deep(.el-form-item__label) {
  color: #334155;
  font-weight: 700;
}

:deep(.el-input__wrapper) {
  min-height: 44px;
  border-radius: 8px;
}

@media (max-width: 940px) {
  .login-page {
    grid-template-columns: 1fr;
    padding: 18px;
  }

  .login-hero {
    min-height: 430px;
    padding: 28px;
  }

  .hero-copy h1 {
    font-size: 34px;
  }

  .preview-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 560px) {
  .login-page {
    padding: 12px;
  }

  .login-card {
    padding: 22px;
  }

  .hero-copy h1 {
    font-size: 28px;
  }

  .demo-card,
  .form-tools {
    align-items: stretch;
    grid-template-columns: 1fr;
    flex-direction: column;
  }
}
</style>
