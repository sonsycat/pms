import request from '@/utils/request'

// 查询分摊计算列表
export function listEquivalent(query) {
  return request({
    url: '/foundation/equivalent/list',
    method: 'get',
    params: query
  })
}
export function getEquivalentDept(query) {
  return request({
    url: '/foundation/equivalent/dept',
    method: 'get',
    params: query
  })
}export function getMatchDept(query) {
  return request({
    url: '/foundation/equivalent/matchDept',
    method: 'get',
    params: query
  })
}
export function getEquivalentItem(keywords) {
  return request({
    url: '/foundation/equivalent/item1/'+keywords,
    method: 'get'
  })
}
export function updateEquivalent(data) {
  return request({
    url: '/foundation/equivalent',
    method: 'put',
    data: data
  })
}
export function getEquivalent(id) {
  return request({
    url: '/foundation/equivalent/' + id,
    method: 'get'
  })
}
