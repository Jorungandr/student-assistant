<template>
  <div class="page">
    <div class="page-head">
      <div>
        <h1 class="page-title">首页看板</h1>
        <p class="page-subtitle">今日课程、待办、健康、预算和目标概览。</p>
      </div>
      <el-button type="primary" :icon="RefreshCw" @click="load">刷新</el-button>
    </div>

    <div class="metric-grid">
      <div class="metric"><div class="metric-label">今日课程</div><div class="metric-value">{{ summary.todayCourses?.length || 0 }}</div></div>
      <div class="metric"><div class="metric-label">待办任务</div><div class="metric-value">{{ summary.todoTasks?.length || 0 }}</div></div>
      <div class="metric"><div class="metric-label">饮水</div><div class="metric-value">{{ summary.healthOverview?.waterMl || 0 }}ml</div></div>
      <div class="metric"><div class="metric-label">连续打卡</div><div class="metric-value">{{ summary.habitStatistics?.longestStreak || 0 }}天</div></div>
    </div>

    <el-alert v-for="flag in summary.statusFlags" :key="flag" :title="flag" type="warning" show-icon :closable="false" />

    <div class="section-grid">
      <section class="panel panel-pad">
        <div class="toolbar"><h3>今日课程</h3></div>
        <el-empty v-if="!summary.todayCourses?.length" description="今日暂无课程" />
        <el-table v-else :data="summary.todayCourses" size="small">
          <el-table-column prop="courseName" label="课程" />
          <el-table-column prop="classroom" label="教室" width="110" />
          <el-table-column label="节次" width="110">
            <template #default="{ row }">{{ row.startSection }}-{{ row.endSection }}</template>
          </el-table-column>
        </el-table>
      </section>

      <section class="panel panel-pad">
        <div class="toolbar"><h3>待办任务</h3></div>
        <el-empty v-if="!summary.todoTasks?.length" description="暂无待办任务" />
        <el-table v-else :data="summary.todoTasks" size="small">
          <el-table-column prop="title" label="任务" />
          <el-table-column prop="priority" label="优先级" width="100" />
          <el-table-column prop="deadline" label="截止时间" width="180" />
        </el-table>
      </section>

      <section class="panel panel-pad">
        <div class="toolbar"><h3>预算状态</h3></div>
        <el-table :data="summary.budgetOverview || []" size="small">
          <el-table-column prop="category" label="分类" />
          <el-table-column prop="usedAmount" label="已用" width="100" />
          <el-table-column prop="budgetAmount" label="预算" width="100" />
          <el-table-column prop="status" label="状态" width="100" />
        </el-table>
      </section>

      <section class="panel panel-pad">
        <div class="toolbar"><h3>目标进度</h3></div>
        <el-table :data="summary.goalProgress || []" size="small">
          <el-table-column prop="title" label="目标" />
          <el-table-column label="进度" width="160">
            <template #default="{ row }">
              <el-progress :percentage="progress(row)" />
            </template>
          </el-table-column>
        </el-table>
      </section>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { RefreshCw } from 'lucide-vue-next'
import { dashboardApi } from '../../api/modules'

const summary = ref({})

const progress = (goal) => {
  if (!goal.targetValue) return 0
  return Math.min(100, Math.round((Number(goal.currentValue || 0) / Number(goal.targetValue)) * 100))
}

const load = async () => {
  const response = await dashboardApi.summary()
  summary.value = response.data
}

onMounted(load)
</script>
