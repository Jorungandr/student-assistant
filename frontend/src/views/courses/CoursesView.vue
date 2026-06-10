<template>
  <div class="page">
    <div class="page-head">
      <div>
        <h1 class="page-title">课程与日程</h1>
        <p class="page-subtitle">维护课程表、作业 DDL、普通日程和任务状态。</p>
      </div>
      <el-segmented v-model="tab" :options="tabs" />
    </div>

    <section v-if="tab === 'courses'" class="panel panel-pad">
      <div class="toolbar"><h3>课程表</h3><el-button type="primary" :icon="Plus" @click="openCourse">新增课程</el-button></div>
      <el-table :data="courses" size="small">
        <el-table-column prop="courseName" label="课程" />
        <el-table-column prop="teacher" label="教师" width="120" />
        <el-table-column prop="classroom" label="教室" width="120" />
        <el-table-column prop="weekday" label="星期" width="90" />
        <el-table-column label="节次" width="120"><template #default="{ row }">{{ row.startSection }}-{{ row.endSection }}</template></el-table-column>
        <el-table-column label="操作" width="90"><template #default="{ row }"><el-button link type="danger" @click="removeCourse(row.id)">删除</el-button></template></el-table-column>
      </el-table>
    </section>

    <section v-if="tab === 'tasks'" class="panel panel-pad">
      <div class="toolbar"><h3>任务 DDL</h3><el-button type="primary" :icon="Plus" @click="openTask">新增任务</el-button></div>
      <el-table :data="tasks" size="small">
        <el-table-column prop="title" label="任务" />
        <el-table-column prop="taskType" label="类型" width="110" />
        <el-table-column prop="priority" label="优先级" width="110" />
        <el-table-column prop="status" label="状态" width="120" />
        <el-table-column prop="deadline" label="截止时间" width="180" />
        <el-table-column label="操作" width="160">
          <template #default="{ row }">
            <el-button link type="primary" @click="completeTask(row)">完成</el-button>
            <el-button link type="danger" @click="removeTask(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </section>

    <section v-if="tab === 'schedules'" class="panel panel-pad">
      <div class="toolbar"><h3>普通日程</h3><el-button type="primary" :icon="Plus" @click="openSchedule">新增日程</el-button></div>
      <el-table :data="schedules" size="small">
        <el-table-column prop="title" label="日程" />
        <el-table-column prop="location" label="地点" width="140" />
        <el-table-column prop="startTime" label="开始" width="180" />
        <el-table-column prop="endTime" label="结束" width="180" />
        <el-table-column label="操作" width="90"><template #default="{ row }"><el-button link type="danger" @click="removeSchedule(row.id)">删除</el-button></template></el-table-column>
      </el-table>
    </section>

    <el-dialog v-model="courseDialog" title="新增课程" width="520px">
      <el-form :model="courseForm" label-position="top">
        <el-form-item label="课程名称"><el-input v-model="courseForm.courseName" /></el-form-item>
        <el-form-item label="教师"><el-input v-model="courseForm.teacher" /></el-form-item>
        <el-form-item label="教室"><el-input v-model="courseForm.classroom" /></el-form-item>
        <div class="form-grid">
          <el-form-item label="星期"><el-input-number v-model="courseForm.weekday" :min="1" :max="7" /></el-form-item>
          <el-form-item label="开始节"><el-input-number v-model="courseForm.startSection" :min="1" /></el-form-item>
          <el-form-item label="结束节"><el-input-number v-model="courseForm.endSection" :min="1" /></el-form-item>
        </div>
        <el-button type="primary" @click="saveCourse">保存</el-button>
      </el-form>
    </el-dialog>

    <el-dialog v-model="taskDialog" title="新增任务" width="520px">
      <el-form :model="taskForm" label-position="top">
        <el-form-item label="任务标题"><el-input v-model="taskForm.title" /></el-form-item>
        <el-form-item label="说明"><el-input v-model="taskForm.description" type="textarea" /></el-form-item>
        <div class="form-grid">
          <el-form-item label="类型"><el-select v-model="taskForm.taskType"><el-option label="作业" value="HOMEWORK" /><el-option label="复习" value="REVIEW" /><el-option label="考试" value="EXAM" /></el-select></el-form-item>
          <el-form-item label="优先级"><el-select v-model="taskForm.priority"><el-option label="高" value="HIGH" /><el-option label="中" value="MEDIUM" /><el-option label="低" value="LOW" /></el-select></el-form-item>
        </div>
        <el-form-item label="截止时间"><el-date-picker v-model="taskForm.deadline" type="datetime" value-format="YYYY-MM-DDTHH:mm:ss" /></el-form-item>
        <el-button type="primary" @click="saveTask">保存</el-button>
      </el-form>
    </el-dialog>

    <el-dialog v-model="scheduleDialog" title="新增日程" width="520px">
      <el-form :model="scheduleForm" label-position="top">
        <el-form-item label="日程标题"><el-input v-model="scheduleForm.title" /></el-form-item>
        <el-form-item label="地点"><el-input v-model="scheduleForm.location" /></el-form-item>
        <el-form-item label="开始时间"><el-date-picker v-model="scheduleForm.startTime" type="datetime" value-format="YYYY-MM-DDTHH:mm:ss" /></el-form-item>
        <el-form-item label="结束时间"><el-date-picker v-model="scheduleForm.endTime" type="datetime" value-format="YYYY-MM-DDTHH:mm:ss" /></el-form-item>
        <el-button type="primary" @click="saveSchedule">保存</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { Plus } from 'lucide-vue-next'
import { courseApi, scheduleApi, taskApi } from '../../api/modules'

const tabs = [{ label: '课程表', value: 'courses' }, { label: '任务', value: 'tasks' }, { label: '日程', value: 'schedules' }]
const tab = ref('courses')
const courses = ref([])
const tasks = ref([])
const schedules = ref([])
const courseDialog = ref(false)
const taskDialog = ref(false)
const scheduleDialog = ref(false)
const courseForm = reactive({ courseName: '', teacher: '', classroom: '', weekday: 1, startSection: 1, endSection: 2, startWeek: 1, endWeek: 18, color: '#1868db' })
const taskForm = reactive({ title: '', description: '', taskType: 'HOMEWORK', priority: 'MEDIUM', status: 'TODO', deadline: '' })
const scheduleForm = reactive({ title: '', location: '', startTime: '', endTime: '', scheduleType: 'NORMAL' })

const load = async () => {
  courses.value = (await courseApi.list()).data
  tasks.value = (await taskApi.list()).data
  schedules.value = (await scheduleApi.list()).data
}
const openCourse = () => { Object.assign(courseForm, { courseName: '', teacher: '', classroom: '', weekday: 1, startSection: 1, endSection: 2, startWeek: 1, endWeek: 18, color: '#1868db' }); courseDialog.value = true }
const openTask = () => { Object.assign(taskForm, { title: '', description: '', taskType: 'HOMEWORK', priority: 'MEDIUM', status: 'TODO', deadline: '' }); taskDialog.value = true }
const openSchedule = () => { Object.assign(scheduleForm, { title: '', location: '', startTime: '', endTime: '', scheduleType: 'NORMAL' }); scheduleDialog.value = true }
const saveCourse = async () => { await courseApi.create(courseForm); courseDialog.value = false; load() }
const saveTask = async () => { await taskApi.create(taskForm); taskDialog.value = false; load() }
const saveSchedule = async () => { await scheduleApi.create(scheduleForm); scheduleDialog.value = false; load() }
const removeCourse = async (id) => { await courseApi.remove(id); load() }
const removeTask = async (id) => { await taskApi.remove(id); load() }
const removeSchedule = async (id) => { await scheduleApi.remove(id); load() }
const completeTask = async (row) => { await taskApi.updateStatus(row.id, 'COMPLETED'); load() }
onMounted(load)
</script>

<style scoped>
.form-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 12px;
}
</style>
