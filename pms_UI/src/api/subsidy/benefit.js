import request from '@/utils/request'

// 查询科室效益调整列表
export function listBenefit(query) {
  return request({
    url: '/subsidy/benefit/list',
    method: 'get',
    params: query
  })
}

// 查询科室效益调整详细
export function getBenefit(id) {
  return request({
    url: '/subsidy/benefit/' + id,
    method: 'get'
  })
}

// 新增科室效益调整
export function addBenefit(data) {
  return request({
    url: '/subsidy/benefit',
    method: 'post',
    data: data
  })
}

// 修改科室效益调整
export function updateBenefit(data) {
  return request({
    url: '/subsidy/benefit',
    method: 'put',
    data: data
  })
}

// 删除科室效益调整
export function delBenefit(id) {
  return request({
    url: '/subsidy/benefit/' + id,
    method: 'delete'
  })
}

// 导出科室效益调整
export function exportBenefit(query) {
  return request({
    url: '/subsidy/benefit/export',
    method: 'get',
    params: query
  })
}