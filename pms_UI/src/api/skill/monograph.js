import request from '@/utils/request'

// 查询专著列表
export function listMonograph(query) {
  return request({
    url: '/skill/monograph/list',
    method: 'get',
    params: query
  })
}

// 查询专著详细
export function getMonograph(id) {
  return request({
    url: '/skill/monograph/' + id,
    method: 'get'
  })
}

// 新增专著
export function addMonograph(data) {
  return request({
    url: '/skill/monograph',
    method: 'post',
    data: data
  })
}

// 修改专著
export function updateMonograph(data) {
  return request({
    url: '/skill/monograph',
    method: 'put',
    data: data
  })
}

// 修改专著
export function updateMonograph1(data) {
  return request({
    url: '/skill/monograph/upd',
    method: 'put',
    data: data
  })
}

// 修改专著
export function audit(data) {
  return request({
    url: '/skill/monograph/audit',
    method: 'put',
    data: data
  })
}

// 删除专著
export function delMonograph(id) {
  return request({
    url: '/skill/monograph/' + id,
    method: 'delete'
  })
}

// 导出专著
export function exportMonograph(query) {
  return request({
    url: '/skill/monograph/export',
    method: 'get',
    params: query
  })
}
