import request from '@/utils/request'

// 查询核算科室设置列表
export function listEnter(query) {
  return request({
    url: '/bonus/enter/list',
    method: 'get',
    params: query
  })
}
export function exportEnter(query) {
  return request({
    url: '/bonus/enter/export',
    method: 'get',
    params: query
  })
}
  export function exportDetail(query) {
    return request({
      url: '/bonus/enter/exportDetail',
      method: 'get',
      params: query
    })
}
export function getEnter(id) {
  return request({
    url: '/bonus/enter/' + id,
    method: 'get'
  })
}
export function addEnter(data) {
  return request({
    url: '/bonus/enter',
    method: 'post',
    data: data
  })
}

export function updateEnter(data) {
  return request({
    url: '/bonus/enter',
    method: 'put',
    data: data
  })
}
export function delEnter(id) {
  return request({
    url: '/bonus/enter/' + id,
    method: 'delete'
  })
}
