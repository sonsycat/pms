import request from '@/utils/request'

export function getStatementEquivalentType(query) {
  return request({
    url: '/bonus/statement/equivalentType',
    method: 'get',
    params: query
  })
}
export function getStatementAchievementType(query) {
  return request({
    url: '/bonus/statement/achievementType',
    method: 'get',
    params: query
  })
}
export function getStatementLabourType(query) {
  return request({
    url: '/bonus/statement/labourType',
    method: 'get',
    params: query
  })
}
export function listEquivalent(query) {
  return request({
    url: '/bonus/statement/equivalentList',
    method: 'get',
    params: query
  })
}
export function exportDetail(query) {
  return request({
    url: '/bonus/statement/exportDetail',
    method: 'get',
    params: query
  })
}
