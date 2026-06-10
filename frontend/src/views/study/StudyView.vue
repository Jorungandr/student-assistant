<template>
  <div class="page">
    <div class="page-head">
      <div><h1 class="page-title">学习分析</h1><p class="page-subtitle">记录学习时长，查看完成率和课程占比。</p></div>
      <el-button type="primary" :icon="Plus" @click="dialog = true">新增记录</el-button>
    </div>
    <div class="metric-grid">
      <div class="metric"><div class="metric-label">任务总数</div><div class="metric-value">{{ completion.total || 0 }}</div></div>
      <div class="metric"><div class="metric-label">已完成</div><div class="metric-value">{{ completion.completed || 0 }}</div></div>
      <div class="metric"><div class="metric-label">完成率</div><div class="metric-value">{{ completion.completionRate || 0 }}%</div></div>
    </div>
    <div class="section-grid">
      <section class="panel panel-pad"><div class="toolbar"><h3>学习时长趋势</h3></div><v-chart class="chart" :option="durationOption" autoresize /></section>
      <section class="panel panel-pad"><div class="toolbar"><h3>课程学习占比</h3></div><v-chart class="chart" :option="ratioOption" autoresize /></section>
    </div>
    <section class="panel panel-pad">
      <div class="toolbar"><h3>学习记录</h3></div>
      <el-table :data="records" size="small">
        <el-table-column prop="studyDate" label="日期" width="130" />
        <el-table-column prop="durationMinutes" label="分钟" width="100" />
        <el-table-column prop="content" label="内容" />
        <el-table-column label="操作" width="90"><template #default="{ row }"><el-button link type="danger" @click="remove(row.id)">删除</el-button></template></el-table-column>
      </el-table>
    </section>
    <section class="panel panel-pad">
      <div class="toolbar"><h3>优先级任务</h3></div>
      <el-table :data="priorityTasks" size="small">
        <el-table-column prop="title" label="任务" />
        <el-table-column prop="priority" label="优先级" width="110" />
        <el-table-column prop="deadline" label="截止时间" width="180" />
      </el-table>
    </section>
    <el-dialog v-model="dialog" title="新增学习记录" width="520px">
      <el-form :model="form" label-position="top">
        <el-form-item label="日期"><el-date-picker v-model="form.studyDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="学习时长"><el-input-number v-model="form.durationMinutes" :min="1" /></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" /></el-form-item>
        <el-button type="primary" @click="save">保存</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import VChart from 'vue-echarts'
import 'echarts'
import { Plus } from 'lucide-vue-next'
import { analysisApi, studyApi } from '../../api/modules'

const records = ref([])
const duration = ref([])
const ratio = ref([])
const completion = ref({})
const priorityTasks = ref([])
const dialog = ref(false)
const form = reactive({ studyDate: '', durationMinutes: 60, content: '' })

const durationOption = computed(() => ({
  tooltip: {},
  xAxis: { type: 'category', data: duration.value.map((item) => item.date) },
  yAxis: { type: 'value' },
  series: [{ type: 'bar', data: duration.value.map((item) => item.value), itemStyle: { color: '#1868db' } }]
}))
const ratioOption = computed(() => ({
  tooltip: { trigger: 'item' },
  series: [{ type: 'pie', radius: ['45%', '70%'], data: ratio.value.map((item) => ({ name: item.name, value: item.value })) }]
}))

const load = async () => {
  records.value = (await studyApi.list()).data
  duration.value = (await analysisApi.studyDuration()).data
  ratio.value = (await analysisApi.courseRatio()).data
  completion.value = (await analysisApi.taskCompletion()).data
  priorityTasks.value = (await analysisApi.priorityTasks()).data
}
const save = async () => { await studyApi.create(form); dialog.value = false; load() }
const remove = async (id) => { await studyApi.remove(id); load() }
onMounted(load)
</script>
