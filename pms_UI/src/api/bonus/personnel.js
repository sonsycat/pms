import request from '@/utils/request'

// 查询核算科室设置列表
export function listPersonnel(query) {
  return request({
    url: '/bonus/personnel/list',
    method: 'get',
    params: query
  })
}
export function exportPersonnel(query) {
  return request({
    url: '/bonus/personnel/export',
    method: 'get',
    params: query
  })
}
  export function exportDetail(query) {
    return request({
      url: '/bonus/personnel/exportDetail',
      method: 'get',
      params: query
    })
}
export function getPersonnel(id) {
  return request({
    url: '/bonus/personnel/' + id,
    method: 'get'
  })
}
export function addPersonnel(data) {
  return request({
    url: '/bonus/personnel',
    method: 'post',
    data: data
  })
}

export function updatePersonnel(data) {
  return request({
    url: '/bonus/personnel',
    method: 'put',
    data: data
  })
}
export function delPersonnel(id) {
  return request({
    url: '/bonus/personnel/' + id,
    method: 'delete'
  })
}
