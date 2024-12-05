import request from '@/utils/request'

// 查询年度帐期间管理列表
export function listPeriod(query) {
  return request({
    url: '/basic/period/list',
    method: 'get',
    params: query
  })
}

// 查询年度帐期间管理详细
export function getPeriod(id) {
  return request({
    url: '/basic/period/' + id,
    method: 'get'
  })
}

// 新增年度帐期间管理
export function addPeriod(data) {
  return request({
    url: '/basic/period',
    method: 'post',
    data: data
  })
}

// 修改年度帐期间管理
export function updatePeriod(data) {
  return request({
    url: '/basic/period',
    method: 'put',
    data: data
  })
}

// 删除年度帐期间管理
export function delPeriod(id) {
  return request({
    url: '/basic/period/' + id,
    method: 'delete'
  })
}

// 根据名称年度删除年度帐期间管理
export function delPeriodAll(data) {
  return request({
    url: '/basic/period/delAll' ,
    method: 'delete',
    data: data
  })
}

// 导出年度帐期间管理
export function exportPeriod(query) {
  return request({
    url: '/basic/period/export',
    method: 'get',
    params: query
  })
}
