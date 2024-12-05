import request from '@/utils/request'


export function listOpcList(query) {
  return request({
    url: '/table/listOpcList',
    method: 'get',
    params: query
  })
}
export function exportDetail(query) {
  return request({
    url: '/table/exportDetailOpc',
    method: 'get',
    params: query
  })
}
