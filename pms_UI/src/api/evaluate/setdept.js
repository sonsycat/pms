import request from '@/utils/request'

// 查询指标集与科室管理列表
export function listSetdept(query) {
  return request({
    url: '/evaluate/setdept/list',
    method: 'get',
    params: query
  })
}

// 查询指标集与科室管理详细
export function getSetdept(id) {
  return request({
    url: '/evaluate/setdept/' + id,
    method: 'get'
  })
}

// 新增指标集与科室管理
export function addSetdept(data) {
  return request({
    url: '/evaluate/setdept',
    method: 'post',
    data: data
  })
}

// 修改指标集与科室管理
export function updateSetdept(data) {
  return request({
    url: '/evaluate/setdept',
    method: 'put',
    data: data
  })
}

// 删除指标集与科室管理
export function delSetdept(id) {
  return request({
    url: '/evaluate/setdept/' + id,
    method: 'delete'
  })
}

// 导出指标集与科室管理
export function exportSetdept(query) {
  return request({
    url: '/evaluate/setdept/export',
    method: 'get',
    params: query
  })
}

//复制数据年月
export function copySetdept(data) {
  return request({
    url: '/evaluate/setdept/copy',
    method: 'post',
    data: data
  })
}

//单指标多科室右侧表格年数据
export function getOneDeptList(query) {
  return request({
    url: '/evaluate/setdeptdetail/selectPmsForDKSN',
    method: 'get',
    params: query
  })
}

//单指标多科室年保存
export function saveOneDept(data) {
  return request({
    url: '/evaluate/setdept/savePmsEvaSetDeptDN',
    method: 'post',
    data: data
  })
}

//单指标多科室右侧表格月的数据
export function getOneDeptMonthList(query) {
  return request({
    url: '/evaluate/setdeptdetail/selectPmsForDKSY',
    method: 'get',
    params: query
  })
}

//单指标多科室月保存
export function saveOneMonthDept(data) {
  return request({
    url: '/evaluate/setdeptdetail/savePmsForDKSY',
    method: 'post',
    data: data
  })
}

// 查询指标集对应指标的BSC分类
export function getBSCSort(setCode) {
  return request({
    url: '/evaluate/setdept/selectBSCSort/' + setCode,
    method: 'get'
  })
}
