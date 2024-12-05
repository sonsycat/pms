import request from '@/utils/request'

// 查询工作量-收费项设置列表
export function listToll(query) {
  return request({
    url: '/workload/toll/list',
    method: 'get',
    params: query
  })
}

// 查询工作量-收费项设置详细
export function getToll(id) {
  return request({
    url: '/workload/toll/' + id,
    method: 'get'
  })
}

// 新增工作量-收费项设置
export function addToll(data) {
  return request({
    url: '/workload/toll',
    method: 'post',
    data: data
  })
}

// 修改工作量-收费项设置
export function updateToll(data) {
  return request({
    url: '/workload/toll',
    method: 'put',
    data: data
  })
}

// 删除工作量-收费项设置
export function delToll(id) {
  return request({
    url: '/workload/toll/' + id,
    method: 'delete'
  })
}

// 导出工作量-收费项设置
export function exportToll(query) {
  return request({
    url: '/workload/toll/export',
    method: 'get',
    params: query
  })
}

// 导出工作量-收费项设置
export function listAll(data) {
  return request({
    url: '/workload/toll/listAll',
    method: 'post',
    data: data
  })
}

// 导出工作量-收费项目保存
export function save(data) {
  return request({
    url: '/workload/toll/save',
    method: 'post',
    data: data
  })
}
