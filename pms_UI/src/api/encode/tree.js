import request from '@/utils/request'

// 查询编码规则树形列表
export function listTree(query) {
  return request({
    url: '/encode/tree/list',
    method: 'get',
    params: query
  })
}

// 查询编码规则树形详细
export function getTree(id) {
  return request({
    url: '/encode/tree/' + id,
    method: 'get'
  })
}

// 新增编码规则树形
export function addTree(data) {
  return request({
    url: '/encode/tree',
    method: 'post',
    data: data
  })
}

// 修改编码规则树形
export function updateTree(data) {
  return request({
    url: '/encode/tree',
    method: 'put',
    data: data
  })
}

// 删除编码规则树形
export function delTree(id) {
  return request({
    url: '/encode/tree/' + id,
    method: 'delete'
  })
}

// 导出编码规则树形
export function exportTree(query) {
  return request({
    url: '/encode/tree/export',
    method: 'get',
    params: query
  })
}