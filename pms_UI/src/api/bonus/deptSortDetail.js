import request from '@/utils/request'

// 查询核算单位类别列表
export function listDeptSortDetail(query) {
  return request({
    url: '/bonus/deptSortDetail/list',
    method: 'get',
    params: query
  })
}

// 查询核算单位类别详细
export function getDeptSortDetail(id) {
  return request({
    url: '/bonus/deptSortDetail/' + id,
    method: 'get'
  })
}

// 新增核算单位类别
export function addDeptSortDetail(data) {
  return request({
    url: '/bonus/deptSortDetail',
    method: 'post',
    data: data
  })
}

// 修改核算单位类别
export function updateDeptSortDetail(data) {
  return request({
    url: '/bonus/deptSortDetail',
    method: 'put',
    data: data
  })
}

// 删除核算单位类别
export function delDeptSortDetail(id) {
  return request({
    url: '/bonus/deptSortDetail/' + id,
    method: 'delete'
  })
}

// 导出核算单位类别
export function exportDeptSortDetail(query) {
  return request({
    url: '/bonus/deptSortDetail/export',
    method: 'get',
    params: query
  })
}