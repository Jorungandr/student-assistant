<template>
  <div class="page">
    <div class="page-head">
      <div>
        <h1 class="page-title">个人中心</h1>
        <p class="page-subtitle">维护个人资料和账号信息。</p>
      </div>
    </div>

    <div class="section-grid">
      <section class="panel panel-pad">
        <div class="toolbar">
          <h3>资料信息</h3>
        </div>
        <el-form :model="profileForm" label-position="top">
          <el-form-item label="昵称">
            <el-input v-model="profileForm.nickname" />
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="profileForm.email" />
          </el-form-item>
          <el-form-item label="头像地址">
            <el-input v-model="profileForm.avatar" />
          </el-form-item>
          <el-form-item label="性别">
            <el-select v-model="profileForm.gender" clearable>
              <el-option label="男" value="MALE" />
              <el-option label="女" value="FEMALE" />
              <el-option label="其他" value="OTHER" />
            </el-select>
          </el-form-item>
          <el-button type="primary" :loading="profileLoading" @click="saveProfile">保存资料</el-button>
        </el-form>
      </section>

      <section class="panel panel-pad">
        <div class="toolbar">
          <h3>修改密码</h3>
        </div>
        <el-form :model="passwordForm" label-position="top">
          <el-form-item label="原密码">
            <el-input v-model="passwordForm.oldPassword" type="password" show-password />
          </el-form-item>
          <el-form-item label="新密码">
            <el-input v-model="passwordForm.newPassword" type="password" show-password />
          </el-form-item>
          <el-button type="primary" :loading="passwordLoading" @click="savePassword">更新密码</el-button>
        </el-form>
      </section>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { updatePassword, updateProfile } from '../../api/auth'
import { useAuthStore } from '../../stores/auth'

const auth = useAuthStore()
const profileLoading = ref(false)
const passwordLoading = ref(false)
const profileForm = reactive({ nickname: '', email: '', avatar: '', gender: '' })
const passwordForm = reactive({ oldPassword: '', newPassword: '' })

onMounted(async () => {
  const user = await auth.loadProfile()
  Object.assign(profileForm, {
    nickname: user.nickname || '',
    email: user.email || '',
    avatar: user.avatar || '',
    gender: user.gender || ''
  })
})

const saveProfile = async () => {
  profileLoading.value = true
  try {
    const response = await updateProfile(profileForm)
    auth.user = response.data
    localStorage.setItem('student_assistant_user', JSON.stringify(auth.user))
    ElMessage.success('资料已保存')
  } finally {
    profileLoading.value = false
  }
}

const savePassword = async () => {
  passwordLoading.value = true
  try {
    await updatePassword(passwordForm)
    passwordForm.oldPassword = ''
    passwordForm.newPassword = ''
    ElMessage.success('密码已更新')
  } finally {
    passwordLoading.value = false
  }
}
</script>
