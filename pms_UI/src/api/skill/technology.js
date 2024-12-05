import request from '@/utils/request'

// 查询新业务新技术列表
export function listTechnology(query) {
  return request({
    url: '/skill/technology/list',
    method: 'get',
    params: query
  })
}

// 查询新业务新技术详细
export function getTechnology(id) {
  return request({
    url: '/skill/technology/' + id,
    method: 'get'
  })
}

// 新增新业务新技术
export function addTechnology(data) {
  return request({
    url: '/skill/technology',
    method: 'post',
    data: data
  })
}

// 修改新业务新技术
export function updateTechnology(data) {
  return request({
    url: '/skill/technology',
    method: 'put',
    data: data
  })
}

// 修改新业务新技术
export function updateTechnology1(data) {
  return request({
    url: '/skill/technology/upd',
    method: 'put',
    data: data
  })
}

// 修改新业务新技术
export function audit(data) {
  return request({
    url: '/skill/technology/audit',
    method: 'put',
    data: data
  })
}

// 删除新业务新技术
export function delTechnology(id) {
  return request({
    url: '/skill/technology/' + id,
    method: 'delete'
  })
}

// 导出新业务新技术
export function exportTechnology(query) {
  return request({
    url: '/skill/technology/export',
    method: 'get',
    params: query
  })
}
