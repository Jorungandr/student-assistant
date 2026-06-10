import http from './http'

const crud = (base) => ({
  list: () => http.get(base),
  create: (data) => http.post(base, data),
  update: (id, data) => http.put(`${base}/${id}`, data),
  remove: (id) => http.delete(`${base}/${id}`)
})

export const dashboardApi = {
  summary: () => http.get('/dashboard/summary')
}

export const courseApi = {
  ...crud('/courses'),
  today: () => http.get('/courses/today')
}

export const scheduleApi = crud('/schedules')

export const taskApi = {
  ...crud('/tasks'),
  upcoming: () => http.get('/tasks/upcoming'),
  statistics: () => http.get('/tasks/statistics'),
  updateStatus: (id, status) => http.put(`/tasks/${id}/status`, { status })
}

export const studyApi = crud('/study-records')

export const analysisApi = {
  studyDuration: () => http.get('/analysis/study-duration'),
  taskCompletion: () => http.get('/analysis/task-completion'),
  courseRatio: () => http.get('/analysis/course-ratio'),
  priorityTasks: () => http.get('/analysis/priority-tasks')
}

export const healthApi = {
  ...crud('/health-records'),
  today: () => http.get('/health-records/today'),
  statistics: () => http.get('/health-records/statistics')
}

export const financeApi = {
  ...crud('/finance-records'),
  statistics: () => http.get('/finance-records/statistics')
}

export const budgetApi = {
  ...crud('/budgets'),
  warnings: () => http.get('/budgets/warnings')
}

export const goalApi = {
  ...crud('/goals'),
  updateProgress: (id, currentValue) => http.put(`/goals/${id}/progress`, { currentValue })
}

export const habitApi = {
  ...crud('/habits'),
  statistics: () => http.get('/habits/statistics'),
  checkin: (id, data = {}) => http.post(`/habits/${id}/checkin`, data),
  checkins: (id) => http.get(`/habits/${id}/checkins`)
}

