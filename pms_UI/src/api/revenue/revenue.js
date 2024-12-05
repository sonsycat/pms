import request from '@/utils/request'

// 查询收入核算方案列表
export function listRevenue(query) {
  return request({
    url: '/revenue/revenue/list',
    method: 'get',
    params: query
  })
}

// 查询收入核算方案详细
export function getRevenue(id) {
  return request({
    url: '/revenue/revenue/' + id,
    method: 'get'
  })
}

// 新增收入核算方案
export function addRevenue(data) {
  return request({
    url: '/revenue/revenue',
    method: 'post',
    data: data
  })
}

// 修改收入核算方案
export function updateRevenue(data) {
  return request({
    url: '/revenue/revenue',
    method: 'put',
    data: data
  })
}

// 删除收入核算方案
export function delRevenue(id) {
  return request({
    url: '/revenue/revenue/' + id,
    method: 'delete'
  })
}

// 导出收入核算方案
export function exportRevenue(query) {
  return request({
    url: '/revenue/revenue/export',
    method: 'get',
    params: query
  })
}

//收入核算方案科室设置列表查询
export function getRevenueDept(query) {
  return request({
    url: '/revenue/revenuedept/list',
    method: 'get',
    params: query
  })
}
//收入核算待选科室列表
export function getDeptList(query) {
  return request({
    url: '/basic/public/getDeptForPms',
    method: 'get',
    params: query
  })
}

//科室类型查询科室
export function searchList(query){
  return request({
    url: '/basic/public/deptInfoByType',
    method: 'get',
    params: query
  })
}
//收入核算科室管理新增
export function addDeptList(data){
  return request({
    url: '/revenue/revenuedept',
    method: 'post',
    data: data
  })
}
