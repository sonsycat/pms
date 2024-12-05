import request from '@/utils/request'

// 查询成果列表
export function listAchieve(query) {
  return request({
    url: '/skill/achieve/list',
    method: 'get',
    params: query
  })
}

// 查询成果详细
export function getAchieve(id) {
  return request({
    url: '/skill/achieve/' + id,
    method: 'get'
  })
}

// 新增成果
export function addAchieve(data) {
  return request({
    url: '/skill/achieve',
    method: 'post',
    data: data
  })
}

// 修改成果
export function updateAchieve(data) {
  return request({
    url: '/skill/achieve',
    method: 'put',
    data: data
  })
}

// 修改成果
export function updateAchieve1(data) {
  return request({
    url: '/skill/achieve/upd',
    method: 'put',
    data: data
  })
}

// 修改成果
export function audit(data) {
  return request({
    url: '/skill/achieve/audit',
    method: 'put',
    data: data
  })
}

// 删除成果
export function delAchieve(id) {
  return request({
    url: '/skill/achieve/' + id,
    method: 'delete'
  })
}

// 导出成果
export function exportAchieve(query) {
  return request({
    url: '/skill/achieve/export',
    method: 'get',
    params: query
  })
}
