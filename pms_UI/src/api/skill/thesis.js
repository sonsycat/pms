import request from '@/utils/request'

// 查询论文列表
export function listThesis(query) {
  return request({
    url: '/skill/thesis/list',
    method: 'get',
    params: query
  })
}

// 查询论文详细
export function getThesis(id) {
  return request({
    url: '/skill/thesis/' + id,
    method: 'get'
  })
}

// 新增论文
export function addThesis(data) {
  return request({
    url: '/skill/thesis',
    method: 'post',
    data: data
  })
}

// 修改论文
export function updateThesis(data) {
  return request({
    url: '/skill/thesis',
    method: 'put',
    data: data
  })
}

// 修改论文
export function updateThesis1(data) {
  return request({
    url: '/skill/thesis/upd',
    method: 'put',
    data: data
  })
}

// 删除论文
export function delThesis(id) {
  return request({
    url: '/skill/thesis/' + id,
    method: 'delete'
  })
}

// 导出论文
export function exportThesis(query) {
  return request({
    url: '/skill/thesis/export',
    method: 'get',
    params: query
  })
}

// 修改课题
export function audit(data) {
  return request({
    url: '/skill/thesis/audit',
    method: 'put',
    data: data
  })
}
