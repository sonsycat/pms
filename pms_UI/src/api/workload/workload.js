import request from '@/utils/request'

// 查询工作量列表
export function listWorkload(query) {
  return request({
    url: '/workload/workload/list',
    method: 'get',
    params: query
  })
}

// 查询工作量详细
export function getWorkload(id) {
  return request({
    url: '/workload/workload/' + id,
    method: 'get'
  })
}

// 新增工作量
export function addWorkload(data) {
  return request({
    url: '/workload/workload',
    method: 'post',
    data: data
  })
}

// 修改工作量
export function updateWorkload(data) {
  return request({
    url: '/workload/workload',
    method: 'put',
    data: data
  })
}

// 删除工作量
export function delWorkload(id) {
  return request({
    url: '/workload/workload/' + id,
    method: 'delete'
  })
}

// 导出工作量
export function exportWorkload(query) {
  return request({
    url: '/workload/workload/export',
    method: 'get',
    params: query
  })
}


// 复制工作量
export function copy(data) {
  return request({
    url: '/workload/workload/copy',
    method: 'post',
    data: data
  })
}
