import request from '@/utils/request'


export function listDeptarList(query) {
  return request({
    url: '/table/listDeptarList',
    method: 'get',
    params: query
  })
}
export function exportDetail(query) {
  return request({
    url: '/table/exportDetailDeptar',
    method: 'get',
    params: query
  })
}
