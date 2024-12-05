import request from '@/utils/request'


// 查询核算科室奖金指标列表
export function listBonusStaff(query) {
  return request({
    url: '/bonus/staff/listBonusStaff',
    method: 'get',
    params: query
  })
}
export function exportStaff(query) {
  return request({
    url: '/bonus/staff/export',
    method: 'get',
    params: query
  })
}
export function exportDetail(query) {
  return request({
    url: '/bonus/staff/exportDetail',
    method: 'get',
    params: query
  })
}
export function saveBonusStaff(data) {
  return request({
    url: '/bonus/staff/save',
    method: 'post',
    data: data
  })
}
