import request from '@/utils/request'

// 查询工作量-类别设置列表
export function listType(query) {
  return request({
    url: '/workload/type/list',
    method: 'get',
    params: query
  })
}

// 查询工作量-类别设置详细
export function getType(id) {
  return request({
    url: '/workload/type/' + id,
    method: 'get'
  })
}

// 新增工作量-类别设置
export function addType(data) {
  return request({
    url: '/workload/type',
    method: 'post',
    data: data
  })
}

// 修改工作量-类别设置
export function updateType(data) {
  return request({
    url: '/workload/type',
    method: 'put',
    data: data
  })
}

// 删除工作量-类别设置
export function delType(id) {
  return request({
    url: '/workload/type/' + id,
    method: 'delete'
  })
}

// 导出工作量-类别设置
export function exportType(query) {
  return request({
    url: '/workload/type/export',
    method: 'get',
    params: query
  })
}