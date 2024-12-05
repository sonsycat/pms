import request from '@/utils/request'

// 查询平衡记分卡列表
export function listScorecard(query) {
  return request({
    url: '/evaluate/scorecard/list',
    method: 'get',
    params: query
  })
}

// 查询平衡记分卡详细
export function getScorecard(id) {
  return request({
    url: '/evaluate/scorecard/' + id,
    method: 'get'
  })
}

// 新增平衡记分卡
export function addScorecard(data) {
  return request({
    url: '/evaluate/scorecard',
    method: 'post',
    data: data
  })
}

// 修改平衡记分卡
export function updateScorecard(data) {
  return request({
    url: '/evaluate/scorecard',
    method: 'put',
    data: data
  })
}

// 删除平衡记分卡
export function delScorecard(id) {
  return request({
    url: '/evaluate/scorecard/' + id,
    method: 'delete'
  })
}

// 导出平衡记分卡
export function exportScorecard(query) {
  return request({
    url: '/evaluate/scorecard/export',
    method: 'get',
    params: query
  })
}