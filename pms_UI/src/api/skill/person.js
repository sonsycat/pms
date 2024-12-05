import request from '@/utils/request'

// 查询技术参与人列表
export function listPerson(query) {
  return request({
    url: '/skill/person/list',
    method: 'get',
    params: query
  })
}

// 查询技术参与人详细
export function getPerson(id) {
  return request({
    url: '/skill/person/' + id,
    method: 'get'
  })
}

// 新增技术参与人
export function addPerson(data) {
  return request({
    url: '/skill/person',
    method: 'post',
    data: data
  })
}

// 修改技术参与人
export function updatePerson(data) {
  return request({
    url: '/skill/person',
    method: 'put',
    data: data
  })
}

// 删除技术参与人
export function delPerson(id) {
  return request({
    url: '/skill/person/' + id,
    method: 'delete'
  })
}

// 导出技术参与人
export function exportPerson(query) {
  return request({
    url: '/skill/person/export',
    method: 'get',
    params: query
  })
}