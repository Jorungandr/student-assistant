<template>
  <div class="page">
    <div class="page-head">
      <div><h1 class="page-title">消费预算</h1><p class="page-subtitle">管理收支记录、分类统计和月度预算。</p></div>
      <div><el-button :icon="Plus" @click="recordDialog = true">记一笔</el-button><el-button type="primary" :icon="Plus" @click="budgetDialog = true">新增预算</el-button></div>
    </div>
    <div class="section-grid">
      <section class="panel panel-pad"><div class="toolbar"><h3>支出分类</h3></div><v-chart class="chart" :option="chartOption" autoresize /></section>
      <section class="panel panel-pad">
        <div class="toolbar"><h3>预算状态</h3></div>
        <el-table :data="warnings" size="small">
          <el-table-column prop="category" label="分类" />
          <el-table-column prop="usedAmount" label="已用" width="100" />
          <el-table-column prop="budgetAmount" label="预算" width="100" />
          <el-table-column prop="usageRate" label="使用率" width="100" />
          <el-table-column prop="status" label="状态" width="100" />
        </el-table>
      </section>
    </div>
    <section class="panel panel-pad">
      <div class="toolbar"><h3>收支记录</h3></div>
      <el-table :data="records" size="small">
        <el-table-column prop="recordDate" label="日期" width="120" />
        <el-table-column prop="recordType" label="类型" width="100" />
        <el-table-column prop="category" label="分类" width="120" />
        <el-table-column prop="amount" label="金额" width="120" />
        <el-table-column prop="description" label="说明" />
        <el-table-column label="操作" width="90"><template #default="{ row }"><el-button link type="danger" @click="remove(row.id)">删除</el-button></template></el-table-column>
      </el-table>
    </section>
    <el-dialog v-model="recordDialog" title="新增收支" width="520px">
      <el-form :model="recordForm" label-position="top">
        <el-form-item label="类型"><el-select v-model="recordForm.recordType"><el-option label="收入" value="INCOME" /><el-option label="支出" value="EXPENSE" /></el-select></el-form-item>
        <el-form-item label="分类"><el-input v-model="recordForm.category" /></el-form-item>
        <el-form-item label="金额"><el-input-number v-model="recordForm.amount" :min="0.01" :precision="2" /></el-form-item>
        <el-form-item label="日期"><el-date-picker v-model="recordForm.recordDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="说明"><el-input v-model="recordForm.description" /></el-form-item>
        <el-button type="primary" @click="saveRecord">保存</el-button>
      </el-form>
    </el-dialog>
    <el-dialog v-model="budgetDialog" title="新增预算" width="520px">
      <el-form :model="budgetForm" label-position="top">
        <el-form-item label="月份"><el-date-picker v-model="budgetForm.month" type="month" value-format="YYYY-MM" /></el-form-item>
        <el-form-item label="分类"><el-input v-model="budgetForm.category" /></el-form-item>
        <el-form-item label="预算金额"><el-input-number v-model="budgetForm.amount" :min="1" :precision="2" /></el-form-item>
        <el-form-item label="预警阈值"><el-input-number v-model="budgetForm.warningThreshold" :min="1" :max="100" /></el-form-item>
        <el-button type="primary" @click="saveBudget">保存</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import VChart from 'vue-echarts'
import 'echarts'
import { Plus } from 'lucide-vue-next'
import { budgetApi, financeApi } from '../../api/modules'

const records = ref([])
const stats = ref([])
const warnings = ref([])
const recordDialog = ref(false)
const budgetDialog = ref(false)
const today = new Date().toISOString().slice(0, 10)
const month = today.slice(0, 7)
const recordForm = reactive({ recordType: 'EXPENSE', category: '餐饮', amount: 20, description: '', recordDate: today })
const budgetForm = reactive({ month, category: '餐饮', amount: 1000, warningThreshold: 80 })
const chartOption = computed(() => ({ tooltip: { trigger: 'item' }, series: [{ type: 'pie', radius: '70%', data: stats.value.map((item) => ({ name: item.name, value: item.value })) }] }))
const load = async () => {
  records.value = (await financeApi.list()).data
  stats.value = (await financeApi.statistics()).data
  warnings.value = (await budgetApi.warnings()).data
}
const saveRecord = async () => { await financeApi.create(recordForm); recordDialog.value = false; load() }
const saveBudget = async () => { await budgetApi.create(budgetForm); budgetDialog.value = false; load() }
const remove = async (id) => { await financeApi.remove(id); load() }
onMounted(load)
</script>
