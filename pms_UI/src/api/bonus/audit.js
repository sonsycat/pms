import request from '@/utils/request'

// 查询奖金人员审核列表
export function listAudit(query) {
  return request({
    url: '/bonus/audit/list',
    method: 'get',
    params: query
  })
}

// 查询奖金人员审核详细
export function getAudit(id) {
  return request({
    url: '/bonus/audit/' + id,
    method: 'get'
  })
}

// 新增奖金人员审核
export function addAudit(data) {
  return request({
    url: '/bonus/audit',
    method: 'post',
    data: data
  })
}

// 修改奖金人员审核
export function updateAudit(data) {
  return request({
    url: '/bonus/audit',
    method: 'put',
    data: data
  })
}

// 删除奖金人员审核
export function delAudit(id) {
  return request({
    url: '/bonus/audit/' + id,
    method: 'delete'
  })
}

// 导出奖金人员审核
export function exportAudit(query) {
  return request({
    url: '/bonus/audit/export',
    method: 'get',
    params: query
  })
}

// 新增奖金人员审核
export function personAudit(data) {
  return request({
    url: '/bonus/audit/audit',
    method: 'post',
    data: data
  })
}

// 获取人员奖金审核流程ID
export function getProcessId(query) {
  return request({
    url: '/bonus/audit/getProcessId',
    method: 'get',
    params: query
  })
}
