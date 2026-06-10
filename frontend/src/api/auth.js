import http from './http'

export const updateProfile = (data) => http.put('/auth/profile', data)
export const updatePassword = (data) => http.put('/auth/password', data)

