import request from '@/utils/request'


export function listBihList(query) {
  return request({
    url: '/table/listBihList',
    method: 'get',
    params: query
  })
}
export function exportDetail(query) {
  return request({
    url: '/table/exportDetailBih',
    method: 'get',
    params: query
  })
}
