import request from '@/utils/request'

// 查询二次分配科室指标列表
export function listDept(query) {
  return request({
    url: '/second/dept/list',
    method: 'get',
    params: query
  })
}

// 查询二次分配科室指标列表
export function listDeptByCompCode(query) {
  return request({
    url: '/second/dept/listDeptByCompCode',
    method: 'get',
    params: query
  })
}

// 查询二次分配科室指标详细
export function getDept(id) {
  return request({
    url: '/second/dept/' + id,
    method: 'get'
  })
}

// 新增二次分配科室指标
export function addDept(data) {
  return request({
    url: '/second/dept',
    method: 'post',
    data: data
  })
}

// 新增二次分配科室指标
export function saveDept(data) {
  return request({
    url: '/second/dept/saveDept',
    method: 'post',
    data: data
  })
}

// 修改二次分配科室指标
export function updateDept(data) {
  return request({
    url: '/second/dept',
    method: 'put',
    data: data
  })
}

// 删除二次分配科室指标
export function delDept(id) {
  return request({
    url: '/second/dept/' + id,
    method: 'delete'
  })
}

// 导出二次分配科室指标
export function exportDept(query) {
  return request({
    url: '/second/dept/export',
    method: 'get',
    params: query
  })
}
