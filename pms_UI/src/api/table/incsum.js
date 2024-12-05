import request from '@/utils/request'


export function listIncsumList(query) {
  return request({
    url: '/table/listIncsumList',
    method: 'get',
    params: query
  })
}
export function exportDetail(query) {
  return request({
    url: '/table/exportDetailIncsum',
    method: 'get',
    params: query
  })
}
