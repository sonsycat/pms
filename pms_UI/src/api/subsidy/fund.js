import request from '@/utils/request'

// 查询科室效益调整列表
export function listFund(query) {
  return request({
    url: '/subsidy/fund/list',
    method: 'get',
    params: query
  })
}
export function submitFundBalance(data) {
  return request({
    url: '/subsidy/fund/submitBalance',
    method: 'post',
    data: data
  })
}
export function getFund(id) {
  return request({
    url: '/subsidy/fund/' + id,
    method: 'get'
  })
}
// 新增科室效益调整
export function addFund(data) {
  return request({
    url: '/subsidy/fund',
    method: 'post',
    data: data
  })
}

// 修改科室效益调整
export function updateFund(data) {
  return request({
    url: '/subsidy/fund',
    method: 'put',
    data: data
  })
}

// 删除科室效益调整
export function delFund(id) {
  return request({
    url: '/subsidy/fund/' + id,
    method: 'delete'
  })
}
