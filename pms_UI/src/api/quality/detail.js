import request from '@/utils/request'

// 查询质量考评指标明细列表
export function listDetail(query) {
  return request({
    url: '/quality/detail/list',
    method: 'get',
    params: query
  })
}

// 查询质量考评指标明细详细
export function getDetail(id) {
  return request({
    url: '/quality/detail/' + id,
    method: 'get'
  })
}

// 新增质量考评指标明细
export function addDetail(data) {
  return request({
    url: '/quality/detail',
    method: 'post',
    data: data
  })
}

// 修改质量考评指标明细
export function updateDetail(data) {
  return request({
    url: '/quality/detail',
    method: 'put',
    data: data
  })
}

// 删除质量考评指标明细
export function delDetail(id) {
  return request({
    url: '/quality/detail/' + id,
    method: 'delete'
  })
}

// 导出质量考评指标明细
export function exportDetail(query) {
  return request({
    url: '/quality/detail/export',
    method: 'get',
    params: query
  })
}