import request from '@/utils/request'

// 查询特殊收入项目方案列表
export function listSpecial(query) {
  return request({
    url: '/revenue/special/list',
    method: 'get',
    params: query
  })
}

// 查询特殊收入项目方案详细
export function getSpecial(id) {
  return request({
    url: '/revenue/special/' + id,
    method: 'get'
  })
}

// 新增特殊收入项目方案
export function addSpecial(data) {
  return request({
    url: '/revenue/special',
    method: 'post',
    data: data
  })
}

// 修改特殊收入项目方案
export function updateSpecial(data) {
  return request({
    url: '/revenue/special',
    method: 'put',
    data: data
  })
}

// 删除特殊收入项目方案
export function delSpecial(id) {
  return request({
    url: '/revenue/special/' + id,
    method: 'delete'
  })
}

// 导出特殊收入项目方案
export function exportSpecial(query) {
  return request({
    url: '/revenue/special/export',
    method: 'get',
    params: query
  })
}

// 获取核算方案字典
export function getRevenue(compCode) {
  return request({
    url: '/basic/public/getRevenue?compCode='+compCode,
    method: 'get'
  })
}

// 获取核算方案字典
export function getProject(compCode) {
  return request({
    url: '/basic/public/getProject?compCode='+compCode,
    method: 'get'
  })
}

// 获取核算方案字典
export function getIncome(compCode) {
  return request({
    url: '/basic/public/getHisIncome?compCode='+compCode,
    method: 'get'
  })
}

// 刷新操作
export function refresh(query) {
  return request({
    url: '/revenue/special/refresh',
    method: 'get',
    params: query
  })
}

// 刷新操作
export function getSpecialCode(data) {
  return request({
    url: '/revenue/special/getSpecialCode',
    method: 'post',
    data: data
  })
}

// 保存操作
export function save(data) {
  return request({
    url: '/revenue/special/save',
    method: 'post',
    data: data
  })
}
