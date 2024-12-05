import request from '@/utils/request'

// 查询分摊明细列表
export function listApportDetail(query) {
  return request({
    url: '/apport/apportDetail/list',
    method: 'get',
    params: query
  })
}

// 查询分摊明细详细
export function getApportDetail(id) {
  return request({
    url: '/apport/apportDetail/' + id,
    method: 'get'
  })
}

// 新增分摊明细
export function addApportDetail(data) {
  return request({
    url: '/apport/apportDetail',
    method: 'post',
    data: data
  })
}

// 修改分摊明细
export function updateApportDetail(data) {
  return request({
    url: '/apport/apportDetail',
    method: 'put',
    data: data
  })
}

// 删除分摊明细
export function delApportDetail(id) {
  return request({
    url: '/apport/apportDetail/' + id,
    method: 'delete'
  })
}

// 导出分摊明细
export function exportApportDetail(query) {
  return request({
    url: '/apport/apportDetail/export',
    method: 'get',
    params: query
  })
}