import request from '@/utils/request'

// 查询平均奖科人数列表
export function listAverage(query) {
  return request({
    url: '/average/average/list',
    method: 'get',
    params: query
  })
}

// 查询平均奖科人数详细
export function getAverage(id) {
  return request({
    url: '/average/average/' + id,
    method: 'get'
  })
}

// 新增平均奖科人数
export function addAverage(data) {
  return request({
    url: '/average/average',
    method: 'post',
    data: data
  })
}

// 修改平均奖科人数
export function updateAverage(data) {
  return request({
    url: '/average/average',
    method: 'put',
    data: data
  })
}

// 删除平均奖科人数
export function delAverage(id) {
  return request({
    url: '/average/average/' + id,
    method: 'delete'
  })
}

// 导出平均奖科人数
export function exportAverage(query) {
  return request({
    url: '/average/average/export',
    method: 'get',
    params: query
  })
}

// 查询最新生成日期
export function getAverageNewData() {
  return request({
    url: '/average/average/getNewDate',
    method: 'get'
  })
}

//复制数据年月
export function copyDataByDate(data) {
  return request({
    url: '/average/average/copy',
    method: 'post',
    data: data
  })
}
