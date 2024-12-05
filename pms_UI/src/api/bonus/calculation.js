import request from '@/utils/request'

// 查询奖金核算列表
export function listCalculation(query) {
  return request({
    url: '/bonus/calculation/list',
    method: 'get',
    params: query
  })
}

// 查询奖金核算详细
export function getCalculation(id) {
  return request({
    url: '/bonus/calculation/' + id,
    method: 'get'
  })
}

// 新增奖金核算
export function addCalculation(data) {
  return request({
    url: '/bonus/calculation',
    method: 'post',
    data: data
  })
}

// 修改奖金核算
export function updateCalculation(data) {
  return request({
    url: '/bonus/calculation',
    method: 'put',
    data: data
  })
}

// 删除奖金核算
export function delCalculation(id) {
  return request({
    url: '/bonus/calculation/' + id,
    method: 'delete'
  })
}

// 导出奖金核算
export function archiveCalculation(id) {
  return request({
    url: '/bonus/calculation/archive/'+id,
    method: 'post'
  })
}

// 院级核算结果
export function bonusDetail(id) {
  return request({
    url: '/bonus/calculation/detail/' + id,
    method: 'get'
  })
}

// 提交审核
export function commitAudit(data) {
  return request({
    url: '/bonus/calculation/commit/audit',
    method: 'post',
    data: data
  })
}

// 获取流程id
export function getProId(data) {
  return request({
    url: '/bonus/calculation/get/proId',
    method: 'post',
    data: data
  })
}

// 修改成果
export function audit(data) {
  return request({
    url: '/bonus/calculation/audit',
    method: 'post',
    data: data
  })
}

