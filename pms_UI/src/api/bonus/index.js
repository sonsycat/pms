import request from '@/utils/request'

// 查询指标配置维护列表
export function listIndex(query) {
  return request({
    url: '/bonus/index/list',
    method: 'get',
    params: query
  })
}

// 查询指标配置维护详细
export function getIndex(id) {
  return request({
    url: '/bonus/index/' + id,
    method: 'get'
  })
}

// 新增指标配置维护
export function addIndex(data) {
  return request({
    url: '/bonus/index',
    method: 'post',
    data: data
  })
}

// 修改指标配置维护
export function updateIndex(data) {
  return request({
    url: '/bonus/index',
    method: 'put',
    data: data
  })
}

// 删除指标配置维护
export function delIndex(id) {
  return request({
    url: '/bonus/index/' + id,
    method: 'delete'
  })
}

// 导出指标配置维护
export function exportIndex(query) {
  return request({
    url: '/bonus/index/export',
    method: 'get',
    params: query
  })
}