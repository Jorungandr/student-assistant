<template>
  <div class="app-shell">
    <aside class="sidebar">
      <div class="side-brand">
        <span class="side-logo">SA</span>
        <div>
          <strong>学生助手</strong>
          <small>Study Desk</small>
        </div>
      </div>
      <nav class="side-nav">
        <router-link v-for="item in navItems" :key="item.path" :to="item.path">
          <component :is="item.icon" :size="18" />
          <span>{{ item.label }}</span>
        </router-link>
      </nav>
    </aside>
    <main class="main-area">
      <header class="topbar panel">
        <div>
          <strong>{{ auth.user?.nickname || '同学' }}</strong>
          <span>今天也把节奏握在手里</span>
        </div>
        <div class="top-actions">
          <el-button :icon="UserRound" circle @click="$router.push('/profile')" />
          <el-button :icon="LogOut" circle @click="logout" />
        </div>
      </header>
      <section class="content-area">
        <router-view />
      </section>
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { BookOpen, CalendarDays, ChartNoAxesCombined, HeartPulse, LogOut, Target, UserRound, WalletCards } from 'lucide-vue-next'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const auth = useAuthStore()

const navItems = [
  { path: '/dashboard', label: '首页看板', icon: ChartNoAxesCombined },
  { path: '/courses', label: '课程日程', icon: CalendarDays },
  { path: '/study', label: '学习分析', icon: BookOpen },
  { path: '/health', label: '健康记录', icon: HeartPulse },
  { path: '/finance', label: '消费预算', icon: WalletCards },
  { path: '/goals', label: '目标习惯', icon: Target }
]

const logout = () => {
  auth.logout()
  router.push('/login')
}
</script>

<style scoped>
.app-shell {
  min-height: 100vh;
  display: grid;
  grid-template-columns: 244px minmax(0, 1fr);
}

.sidebar {
  position: sticky;
  top: 0;
  height: 100vh;
  padding: 18px;
  background: #0f2035;
  color: #eef7ff;
}

.side-brand {
  height: 64px;
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 22px;
}

.side-logo {
  width: 42px;
  height: 42px;
  display: grid;
  place-items: center;
  border: 1px solid rgba(255,255,255,0.35);
  border-radius: 8px;
  color: #9ee6d7;
  font-weight: 800;
}

.side-brand small {
  display: block;
  margin-top: 2px;
  color: rgba(238,247,255,0.62);
}

.side-nav {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.side-nav a {
  height: 44px;
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 0 12px;
  color: rgba(238,247,255,0.78);
  text-decoration: none;
  border-radius: 8px;
}

.side-nav a.router-link-active {
  color: #ffffff;
  background: linear-gradient(90deg, rgba(23,168,139,0.34), rgba(24,104,219,0.18));
}

.main-area {
  min-width: 0;
  padding: 18px;
}

.topbar {
  height: 64px;
  padding: 0 18px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.topbar span {
  margin-left: 10px;
  color: var(--muted);
}

.top-actions {
  display: flex;
  gap: 8px;
}

.content-area {
  padding: 18px 0;
}

@media (max-width: 860px) {
  .app-shell {
    grid-template-columns: 1fr;
  }
  .sidebar {
    position: relative;
    height: auto;
  }
  .side-nav {
    display: grid;
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}
</style>

