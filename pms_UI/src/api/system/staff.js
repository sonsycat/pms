import request from '@/utils/request'

// 查询医生信息列表
export function listStaff(query) {
  return request({
    url: '/system/staff/list',
    method: 'get',
    params: query
  })
}
export function getStaff(id) {
  return request({
    url: '/system/staff/' + id,
    method: 'get'
  })
}
export function addStaff(data) {
  return request({
    url: '/system/staff',
    method: 'post',
    data: data
  })
}

export function updateStaff(data) {
  return request({
    url: '/system/staff',
    method: 'put',
    data: data
  })
}
export function delStaff(id) {
  return request({
    url: '/system/staff/' + id,
    method: 'delete'
  })
}
export function exportStaff(query) {
  return request({
    url: '/system/staff/export',
    method: 'get',
    params: query
  })
}
export function exportDetail(query) {
  return request({
    url: '/system/staff/exportDetail',
    method: 'get',
    params: query
  })
}
