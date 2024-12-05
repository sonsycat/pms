import request from '@/utils/request'

// 查询成本金额管理列表
export function listNum(query) {
  return request({
    url: '/project/num/list',
    method: 'get',
    params: query
  })
}
//动态表头
export function headerList(query) {
  return request({
    url: '/project/num/headList',
    method: 'get',
    params: query
  })
}
//
export function resultList(query) {
  return request({
    url: '/project/num/queryList',
    method: 'get',
    params: query
  })
}

// 查询成本金额管理详细
export function getNum(id) {
  return request({
    url: '/project/num/' + id,
    method: 'get'
  })
}

// 新增成本金额管理
export function addNum(data) {
  return request({
    url: '/project/num',
    method: 'post',
    data: data
  })
}

// 修改成本金额管理
export function updateNum(data) {
  return request({
    url: '/project/num',
    method: 'put',
    data: data
  })
}

// 删除成本金额管理
export function delNum(query) {
  return request({
    url: '/project/num/del',
    method: 'delete',
    params:query
  })
}

// 导出成本金额管理
export function exportNum(query) {
  return request({
    url: '/project/num/export',
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
//成本查询前置
export function searchPreList(query){
  return request({
    url: '/project/num/checkInfo',
    method: 'get',
    params: query
  })
}
//成本科室查询项目pop框列表
export function searchForProject(query){
  return request({
    url: '/project/dept/selectForDept',
    method: 'get',
    params: query
  })
}
//成本sql查询
export function searchForSQL(query){
  return request({
    url: '/project/sql/list',
    method: 'get',
    params: query
  })
}
//科室查询科室类型
export function searchForDeptType(query){
  return request({
    url: '/basic/public/queryTypeByDept',
    method: 'get',
    params: query
  })
}
//提取成本前置数据查询
export function beforeExtractProject(query){
  return request({
    url: '/project/sql/list',
    method: 'get',
    params: query
  })
}
//提取成本
export function extractForProject(data){
  return request({
    url: '/project/num/inertBySql',
    method: 'post',
    data: data
  })
}