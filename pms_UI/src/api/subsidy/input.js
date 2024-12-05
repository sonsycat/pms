import request from '@/utils/request'

// 查询单项奖惩录入列表
export function listInput(query) {
  return request({
    url: '/subsidy/input/list',
    method: 'get',
    params: query
  })
}

// 查询单项奖惩录入详细
export function getInput(id) {
  return request({
    url: '/subsidy/input/' + id,
    method: 'get'
  })
}

// 新增单项奖惩录入
export function addInput(data) {
  return request({
    url: '/subsidy/input',
    method: 'post',
    data: data
  })
}

// 修改单项奖惩录入
export function updateInput(data) {
  return request({
    url: '/subsidy/input',
    method: 'put',
    data: data
  })
}

// 删除单项奖惩录入
export function delInput(id) {
  return request({
    url: '/subsidy/input/' + id,
    method: 'delete'
  })
}

// 导出单项奖惩录入
export function exportInput(query) {
  return request({
    url: '/subsidy/input/export',
    method: 'get',
    params: query
  })
}