import request from '@/utils/request'


export function listCostList(query) {
  return request({
    url: '/table/listCostList',
    method: 'get',
    params: query
  })
}
export function exportDetail(query) {
  return request({
    url: '/table/exportDetailCost',
    method: 'get',
    params: query
  })
}
