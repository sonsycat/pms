import request from '@/utils/request'

// 查询编码规则列表
export function listRule(query) {
  return request({
    url: '/encode/rule/list',
    method: 'get',
    params: query
  })
}

// 查询编码规则详细
export function getRule(id) {
  return request({
    url: '/encode/rule/' + id,
    method: 'get'
  })
}

// 新增编码规则
export function addRule(data) {
  return request({
    url: '/encode/rule',
    method: 'post',
    data: data
  })
}

// 修改编码规则
export function updateRule(data) {
  return request({
    url: '/encode/rule',
    method: 'put',
    data: data
  })
}

// 删除编码规则
export function delRule(id) {
  return request({
    url: '/encode/rule/' + id,
    method: 'delete'
  })
}

// 导出编码规则
export function exportRule(query) {
  return request({
    url: '/encode/rule/export',
    method: 'get',
    params: query
  })
}