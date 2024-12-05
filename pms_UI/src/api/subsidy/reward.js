import request from '@/utils/request'

// 查询奖惩项目维护列表
export function listReward(query) {
  return request({
    url: '/subsidy/reward/list',
    method: 'get',
    params: query
  })
}

// 查询奖惩项目维护详细
export function getReward(id) {
  return request({
    url: '/subsidy/reward/' + id,
    method: 'get'
  })
}

// 新增奖惩项目维护
export function addReward(data) {
  return request({
    url: '/subsidy/reward',
    method: 'post',
    data: data
  })
}

// 修改奖惩项目维护
export function updateReward(data) {
  return request({
    url: '/subsidy/reward',
    method: 'put',
    data: data
  })
}

// 删除奖惩项目维护
export function delReward(id) {
  return request({
    url: '/subsidy/reward/' + id,
    method: 'delete'
  })
}

// 导出奖惩项目维护
export function exportReward(query) {
  return request({
    url: '/subsidy/reward/export',
    method: 'get',
    params: query
  })
}