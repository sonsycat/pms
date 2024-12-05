import request from '@/utils/request'

// 查询课题列表
export function listTopic(query) {
  return request({
    url: '/skill/topic/list',
    method: 'get',
    params: query
  })
}

// 查询课题详细
export function getTopic(id) {
  return request({
    url: '/skill/topic/' + id,
    method: 'get'
  })
}

// 新增课题
export function addTopic(data) {
  return request({
    url: '/skill/topic',
    method: 'post',
    data: data
  })
}

// 修改课题
export function updateTopic(data) {
  return request({
    url: '/skill/topic',
    method: 'put',
    data: data
  })
}

// 修改课题
export function updateTopic1(data) {
  return request({
    url: '/skill/topic/upd',
    method: 'put',
    data: data
  })
}

// 删除课题
export function delTopic(id) {
  return request({
    url: '/skill/topic/' + id,
    method: 'delete'
  })
}

// 导出课题
export function exportTopic(query) {
  return request({
    url: '/skill/topic/export',
    method: 'get',
    params: query
  })
}
// 修改课题
export function audit(data) {
  return request({
    url: '/skill/topic/audit',
    method: 'put',
    data: data
  })
}
