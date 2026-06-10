import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import MainLayout from '../layout/MainLayout.vue'
import LoginView from '../views/auth/LoginView.vue'
import RegisterView from '../views/auth/RegisterView.vue'
import DashboardView from '../views/dashboard/DashboardView.vue'
import CoursesView from '../views/courses/CoursesView.vue'
import StudyView from '../views/study/StudyView.vue'
import HealthView from '../views/health/HealthView.vue'
import FinanceView from '../views/finance/FinanceView.vue'
import GoalsView from '../views/goals/GoalsView.vue'
import ProfileView from '../views/profile/ProfileView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/login', component: LoginView, meta: { public: true } },
    { path: '/register', component: RegisterView, meta: { public: true } },
    {
      path: '/',
      component: MainLayout,
      children: [
        { path: '', redirect: '/dashboard' },
        { path: 'dashboard', component: DashboardView },
        { path: 'courses', component: CoursesView },
        { path: 'study', component: StudyView },
        { path: 'health', component: HealthView },
        { path: 'finance', component: FinanceView },
        { path: 'goals', component: GoalsView },
        { path: 'profile', component: ProfileView }
      ]
    }
  ]
})

router.beforeEach((to) => {
  const auth = useAuthStore()
  if (!to.meta.public && !auth.isLoggedIn) return '/login'
  if (to.meta.public && auth.isLoggedIn) return '/dashboard'
})

export default router

