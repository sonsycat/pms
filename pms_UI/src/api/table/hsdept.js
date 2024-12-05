import request from '@/utils/request'


export function listHsdeptList(query) {
  return request({
    url: '/table/listHsdeptList',
    method: 'get',
    params: query
  })
}
export function exportDetail(query) {
  return request({
    url: '/table/exportDetailHsdept',
    method: 'get',
    params: query
  })
}
