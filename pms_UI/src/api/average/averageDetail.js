import request from '@/utils/request'

// 查询平均奖科人数明细列表
export function listAverageDetail(query) {
  return request({
    url: '/average/averageDetail/list',
    method: 'get',
    params: query
  })
}

// 查询平均奖科人数明细详细
export function getAverageDetail(id) {
  return request({
    url: '/average/averageDetail/' + id,
    method: 'get'
  })
}

// 新增平均奖科人数明细
export function addAverageDetail(data) {
  return request({
    url: '/average/averageDetail',
    method: 'post',
    data: data
  })
}

// 修改平均奖科人数明细
export function updateAverageDetail(data) {
  return request({
    url: '/average/averageDetail',
    method: 'put',
    data: data
  })
}

// 删除平均奖科人数明细
export function delAverageDetail(id) {
  return request({
    url: '/average/averageDetail/' + id,
    method: 'delete'
  })
}

// 导出平均奖科人数明细
export function exportAverageDetail(query) {
  return request({
    url: '/average/averageDetail/export',
    method: 'get',
    params: query
  })
}

// 保存平均奖科人数明细
export function saveAverageDetail(data) {
  return request({
    url: '/average/averageDetail/save',
    method: 'post',
    data: data
  })
}
