import request from '@/utils/request'

// 查询指标集与科室管理明细列表
export function listSetdeptdetail(query) {
  return request({
    url: '/evaluate/setdeptdetail/queryList',
    method: 'get',
    params: query
  })
}

// 查询指标集与科室管理明细详细
export function getSetdeptdetail(query) {
  return request({
    url: '/evaluate/setdeptdetail/querySetInfo',
    method: 'get',
    params: query
  })
}
// 修改指标集与科室管理明细
export function updateSetdeptdetail(data) {
  return request({
    url: '/evaluate/setdeptdetail',
    method: 'post',
    data: data
  })
}

// 查询该科室对应BSC分类及总分
export function getBscScoreById(id) {
  return request({
    url: '/evaluate/setdeptdetail/getBscScoreById/' + id,
    method: 'get'
  })
}
