import request from '@/utils/request'

// 查询审批意见列表
export function listOpinion(query) {
  return request({
    url: '/skill/opinion/list',
    method: 'get',
    params: query
  })
}

// 查询审批意见详细
export function getOpinion(id) {
  return request({
    url: '/skill/opinion/' + id,
    method: 'get'
  })
}

// 新增审批意见
export function addOpinion(data) {
  return request({
    url: '/skill/opinion',
    method: 'post',
    data: data
  })
}

// 修改审批意见
export function updateOpinion(data) {
  return request({
    url: '/skill/opinion',
    method: 'put',
    data: data
  })
}

// 删除审批意见
export function delOpinion(id) {
  return request({
    url: '/skill/opinion/' + id,
    method: 'delete'
  })
}

// 导出审批意见
export function exportOpinion(query) {
  return request({
    url: '/skill/opinion/export',
    method: 'get',
    params: query
  })
}