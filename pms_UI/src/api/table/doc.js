import request from '@/utils/request'


export function listDocList(query) {
  return request({
    url: '/table/listDocList',
    method: 'get',
    params: query
  })
}
export function exportDetail(query) {
  return request({
    url: '/table/exportDetailDoc',
    method: 'get',
    params: query
  })
}
