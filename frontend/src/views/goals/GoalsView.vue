<template>
  <div class="page">
    <div class="page-head">
      <div><h1 class="page-title">目标与习惯</h1><p class="page-subtitle">跟踪阶段目标、自定义计划和每日打卡。</p></div>
      <div><el-button :icon="Plus" @click="goalDialog = true">新增目标</el-button><el-button type="primary" :icon="Plus" @click="habitDialog = true">新增习惯</el-button></div>
    </div>
    <div class="metric-grid">
      <div class="metric"><div class="metric-label">习惯数量</div><div class="metric-value">{{ statistics.habitCount || 0 }}</div></div>
      <div class="metric"><div class="metric-label">今日打卡</div><div class="metric-value">{{ statistics.todayCheckinCount || 0 }}</div></div>
      <div class="metric"><div class="metric-label">最长连续</div><div class="metric-value">{{ statistics.longestStreak || 0 }}天</div></div>
    </div>
    <div class="section-grid">
      <section class="panel panel-pad">
        <div class="toolbar"><h3>阶段目标</h3></div>
        <el-table :data="goals" size="small">
          <el-table-column prop="title" label="目标" />
          <el-table-column label="进度" width="180"><template #default="{ row }"><el-progress :percentage="progress(row)" /></template></el-table-column>
          <el-table-column prop="endDate" label="结束日期" width="130" />
          <el-table-column label="操作" width="90"><template #default="{ row }"><el-button link type="danger" @click="removeGoal(row.id)">删除</el-button></template></el-table-column>
        </el-table>
      </section>
      <section class="panel panel-pad">
        <div class="toolbar"><h3>每日习惯</h3></div>
        <el-table :data="habits" size="small">
          <el-table-column prop="habitName" label="习惯" />
          <el-table-column prop="targetCount" label="目标次数" width="100" />
          <el-table-column prop="status" label="状态" width="100" />
          <el-table-column label="操作" width="150"><template #default="{ row }"><el-button link type="primary" @click="checkin(row)">打卡</el-button><el-button link type="danger" @click="removeHabit(row.id)">删除</el-button></template></el-table-column>
        </el-table>
      </section>
    </div>
    <el-dialog v-model="goalDialog" title="新增目标" width="520px">
      <el-form :model="goalForm" label-position="top">
        <el-form-item label="目标标题"><el-input v-model="goalForm.title" /></el-form-item>
        <el-form-item label="目标值"><el-input-number v-model="goalForm.targetValue" :min="1" /></el-form-item>
        <el-form-item label="当前值"><el-input-number v-model="goalForm.currentValue" :min="0" /></el-form-item>
        <div class="form-grid"><el-form-item label="开始"><el-date-picker v-model="goalForm.startDate" type="date" value-format="YYYY-MM-DD" /></el-form-item><el-form-item label="结束"><el-date-picker v-model="goalForm.endDate" type="date" value-format="YYYY-MM-DD" /></el-form-item></div>
        <el-button type="primary" @click="saveGoal">保存</el-button>
      </el-form>
    </el-dialog>
    <el-dialog v-model="habitDialog" title="新增习惯" width="520px">
      <el-form :model="habitForm" label-position="top">
        <el-form-item label="习惯名称"><el-input v-model="habitForm.habitName" /></el-form-item>
        <el-form-item label="说明"><el-input v-model="habitForm.description" /></el-form-item>
        <el-form-item label="每日目标次数"><el-input-number v-model="habitForm.targetCount" :min="1" /></el-form-item>
        <el-button type="primary" @click="saveHabit">保存</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { Plus } from 'lucide-vue-next'
import { ElMessage } from 'element-plus'
import { goalApi, habitApi } from '../../api/modules'

const goals = ref([])
const habits = ref([])
const statistics = ref({})
const goalDialog = ref(false)
const habitDialog = ref(false)
const today = new Date().toISOString().slice(0, 10)
const goalForm = reactive({ title: '', targetValue: 100, currentValue: 0, startDate: today, endDate: today, status: 'ACTIVE' })
const habitForm = reactive({ habitName: '', description: '', frequency: 'DAILY', targetCount: 1, status: 'ACTIVE' })
const progress = (goal) => Math.min(100, Math.round((Number(goal.currentValue || 0) / Number(goal.targetValue || 1)) * 100))
const load = async () => {
  goals.value = (await goalApi.list()).data
  habits.value = (await habitApi.list()).data
  statistics.value = (await habitApi.statistics()).data
}
const saveGoal = async () => { await goalApi.create(goalForm); goalDialog.value = false; load() }
const saveHabit = async () => { await habitApi.create(habitForm); habitDialog.value = false; load() }
const checkin = async (row) => { await habitApi.checkin(row.id, { checkinCount: 1 }); ElMessage.success('打卡成功'); load() }
const removeGoal = async (id) => { await goalApi.remove(id); load() }
const removeHabit = async (id) => { await habitApi.remove(id); load() }
onMounted(load)
</script>

<style scoped>
.form-grid { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 12px; }
</style>
