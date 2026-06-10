<template>
  <div class="page">
    <div class="page-head">
      <div><h1 class="page-title">生活健康</h1><p class="page-subtitle">记录天气、睡眠、饮水、运动、情绪压力。</p></div>
      <el-button type="primary" :icon="Plus" @click="open">新增记录</el-button>
    </div>
    <div class="metric-grid">
      <div class="metric"><div class="metric-label">今日天气</div><div class="metric-value">{{ today?.weather || '-' }}</div></div>
      <div class="metric"><div class="metric-label">睡眠</div><div class="metric-value">{{ today?.sleepHours || 0 }}h</div></div>
      <div class="metric"><div class="metric-label">饮水</div><div class="metric-value">{{ today?.waterMl || 0 }}ml</div></div>
      <div class="metric"><div class="metric-label">运动</div><div class="metric-value">{{ today?.exerciseMinutes || 0 }}min</div></div>
    </div>
    <section class="panel panel-pad">
      <div class="toolbar"><h3>健康记录</h3></div>
      <el-table :data="records" size="small">
        <el-table-column prop="recordDate" label="日期" width="120" />
        <el-table-column prop="weather" label="天气" width="100" />
        <el-table-column prop="sleepHours" label="睡眠" width="100" />
        <el-table-column prop="waterMl" label="饮水" width="100" />
        <el-table-column prop="exerciseMinutes" label="运动" width="100" />
        <el-table-column prop="moodScore" label="情绪" width="100" />
        <el-table-column prop="stressScore" label="压力" width="100" />
        <el-table-column label="操作" width="90"><template #default="{ row }"><el-button link type="danger" @click="remove(row.id)">删除</el-button></template></el-table-column>
      </el-table>
    </section>
    <el-dialog v-model="dialog" title="新增健康记录" width="560px">
      <el-form :model="form" label-position="top">
        <el-form-item label="日期"><el-date-picker v-model="form.recordDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="天气"><el-input v-model="form.weather" /></el-form-item>
        <div class="form-grid">
          <el-form-item label="睡眠小时"><el-input-number v-model="form.sleepHours" :min="0" :precision="1" /></el-form-item>
          <el-form-item label="饮水 ml"><el-input-number v-model="form.waterMl" :min="0" /></el-form-item>
          <el-form-item label="运动分钟"><el-input-number v-model="form.exerciseMinutes" :min="0" /></el-form-item>
        </div>
        <div class="form-grid">
          <el-form-item label="情绪"><el-slider v-model="form.moodScore" :min="1" :max="10" /></el-form-item>
          <el-form-item label="压力"><el-slider v-model="form.stressScore" :min="1" :max="10" /></el-form-item>
        </div>
        <el-form-item label="备注"><el-input v-model="form.note" type="textarea" /></el-form-item>
        <el-button type="primary" @click="save">保存</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { Plus } from 'lucide-vue-next'
import { healthApi } from '../../api/modules'

const records = ref([])
const today = ref(null)
const dialog = ref(false)
const form = reactive({ recordDate: '', weather: '', sleepHours: 7, waterMl: 1500, exerciseMinutes: 30, moodScore: 7, stressScore: 4, note: '' })

const load = async () => {
  records.value = (await healthApi.list()).data
  today.value = (await healthApi.today()).data
}
const open = () => { Object.assign(form, { recordDate: new Date().toISOString().slice(0, 10), weather: '', sleepHours: 7, waterMl: 1500, exerciseMinutes: 30, moodScore: 7, stressScore: 4, note: '' }); dialog.value = true }
const save = async () => { await healthApi.create(form); dialog.value = false; load() }
const remove = async (id) => { await healthApi.remove(id); load() }
onMounted(load)
</script>

<style scoped>
.form-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); gap: 12px; }
</style>
