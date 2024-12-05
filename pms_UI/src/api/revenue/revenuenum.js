import request from '@/utils/request'

// 查询收入录入列表
export function listRevenuenum(query) {
  return request({
    url: '/revenue/revenuenum/list',
    method: 'get',
    params: query
  })
}

// 查询收入录入详细
export function getRevenuenum(id) {
  return request({
    url: '/revenue/revenuenum/' + id,
    method: 'get'
  })
}

// 新增收入录入
export function addRevenuenum(data) {
  return request({
    url: '/revenue/revenuenum',
    method: 'post',
    data: data
  })
}

// 修改收入录入
export function updateRevenuenum(data) {
  return request({
    url: '/revenue/revenuenum',
    method: 'put',
    data: data
  })
}

//收入提取
export function extractForProjectInCome(data){
  return request({
    url:'/revenue/revenuenum/inertBySql',
    method:'post',
    data: data
  })
}

//收入提取前置数据查询
export function beforeExtract(query){
  return request({
    url:'/revenue/revenuesql/list',
    method:'get',
    params: query
  })
}

// 删除收入录入
export function delRevenuenum(id) {
  return request({
    url: '/revenue/revenuenum/' + id,
    method: 'delete'
  })
}

// 导出收入录入
export function exportRevenuenum(query) {
  return request({
    url: '/revenue/revenuenum/export',
    method: 'get',
    params: query
  })
}
