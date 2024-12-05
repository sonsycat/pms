import request from '@/utils/request'

// 查询术式套包字典管理明细列表
export function listPackageDetail(query) {
  return request({
    url: '/basic/packageDetail/list',
    method: 'get',
    params: query
  })
}

// 查询术式套包字典管理明细详细
export function getPackageDetail(id) {
  return request({
    url: '/basic/packageDetail/' + id,
    method: 'get'
  })
}

// 新增术式套包字典管理明细
export function addPackageDetail(data) {
  return request({
    url: '/basic/packageDetail',
    method: 'post',
    data: data
  })
}

// 修改术式套包字典管理明细
export function updatePackageDetail(data) {
  return request({
    url: '/basic/packageDetail',
    method: 'put',
    data: data
  })
}

// 删除术式套包字典管理明细
export function delPackageDetail(id) {
  return request({
    url: '/basic/packageDetail/' + id,
    method: 'delete'
  })
}

// 导出术式套包字典管理明细
export function exportPackageDetail(query) {
  return request({
    url: '/basic/packageDetail/export',
    method: 'get',
    params: query
  })
}