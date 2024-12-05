import request from '@/utils/request'
import {
  praseStrEmpty
} from "@/utils/zjry";

// 获取用户的账套信息
export function initAccountMsg() {
  return request({
    url: '/basic/user/initAccountMsg',
    method: 'get'
  })
}
// 获取用户的角色信息
export function initRoleMsg() {
  return request({
    url: '/basic/user/initRoleMsg',
    method: 'get'
  })
}
// 查询角色列表（用户角色表）
export function getRoleByUserId(userId) {
  return request({
    url: '/basic/user/getRoleByUserId/' + userId,
    method: 'get'
  })
}
// 获取用户的科室列表信息
export function initLoginMsgById(accountId, roleId) {
  const data = {
    accountId,
    roleId
  }
  return request({
    url: '/basic/user/initLoginMsgById',
    method: 'get',
    params: data
  })
}

// 查询用户列表
export function listUser(query) {
  return request({
    url: '/system/user/list',
    method: 'get',
    params: query
  })
}

// 查询用户详细
export function getUser(userId) {
  return request({
    url: '/system/user/' + praseStrEmpty(userId),
    method: 'get'
  })
}

// 新增用户
export function addUser(data) {
  return request({
    url: '/basic/public/insertUser',
    method: 'post',
    data: data
  })
}
export function addUserNew(data) {
  return request({
    url: '/basic/public/insertUserNew',
    method: 'post',
    data: data
  })
}
// 修改用户
export function updateUser(data) {
  return request({
    url: '/basic/public/updateUser',
    method: 'put',
    data: data
  })
}

// 删除用户
export function delUser(userId) {
  return request({
    url: '/system/user/' + userId,
    method: 'delete'
  })
}

// 导出用户
export function exportUser(query) {
  return request({
    url: '/system/user/export',
    method: 'get',
    params: query
  })
}

// 用户密码重置
export function resetUserPwd(userId, password) {
  const data = {
    userId,
    password
  }
  return request({
    url: '/system/user/resetPwd',
    method: 'put',
    data: data
  })
}

// 用户状态修改
export function changeUserStatus(userId, status) {
  const data = {
    userId,
    status
  }
  return request({
    url: '/system/user/changeStatus',
    method: 'put',
    data: data
  })
}

// 查询用户个人信息
export function getUserProfile() {
  return request({
    url: '/system/user/profile',
    method: 'get'
  })
}

// 修改用户个人信息
export function updateUserProfile(data) {
  return request({
    url: '/system/user/profile',
    method: 'put',
    data: data
  })
}

// 用户密码重置
export function updateUserPwd(oldPassword, newPassword) {
  const data = {
    oldPassword,
    newPassword
  }
  return request({
    url: '/system/user/profile/updatePwd',
    method: 'put',
    params: data
  })
}

// 用户头像上传
export function uploadAvatar(data) {
  return request({
    url: '/system/user/profile/avatar',
    method: 'post',
    data: data
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: '/system/user/importTemplate',
    method: 'get'
  })
}

// 获取版本信息
export function getZjryConfig(query) {
  return request({
    url: '/system/user/profile/getZjryConfig',
    method: 'get',
    params: query
  })
}


// 查询员工信息
export function getStaffInfo(query) {
  return request({
    url: '/basic/public/queryEmpInfo',
    method: 'get',
    params: query
  })
}
