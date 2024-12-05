import request from '@/utils/request'

// 查询特殊收入项目科室设置详细
export function getSpecialDept(id) {
  return request({
    url: '/revenue/specialDept/' + id,
    method: 'get'
  })
}

// 修改特殊收入项目科室设置
export function updateSpecialDept(data) {
  return request({
    url: '/revenue/specialDept',
    method: 'put',
    data: data
  })
}

// 删除特殊收入项目科室设置
export function delSpecialDept(id) {
  return request({
    url: '/revenue/specialDept/' + id,
    method: 'delete'
  })
}

// 导出特殊收入项目科室设置
export function exportSpecialDept(query) {
  return request({
    url: '/revenue/specialDept/export',
    method: 'get',
    params: query
  })
}

// 查询科室设置列表
export function listSpecialDept(query) {
  return request({
    url: '/revenue/specialDept/listSpecialDept',
    method: 'get',
    params: query
  })
}
// 新增科室设置
export function addSpecialDept(data) {
  return request({
    url: '/revenue/specialDept/addSpecialDept',
    method: 'post',
    data: data
  })
}
