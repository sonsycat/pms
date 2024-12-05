import request from '@/utils/request'


export function listDeptbeList(query) {
  return request({
    url: '/table/listDeptbeList',
    method: 'get',
    params: query
  })
}
export function exportDetail(query) {
  return request({
    url: '/table/exportDetailDeptbe',
    method: 'get',
    params: query
  })
}
