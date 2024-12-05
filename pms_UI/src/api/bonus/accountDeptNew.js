import request from '@/utils/request'

// 查询核算科室设置列表
export function listAccountDept(query) {
  return request({
    url: '/bonus/accountDeptNew/list',
    method: 'get',
    params: query
  })
}
export function saveAccountDeptNew(data) {
  return request({
    url: '/bonus/accountDeptNew/save',
    method: 'post',
    data: data
  })
}
export function copyDataByDate(data) {
  return request({
    url: '/bonus/accountDeptNew/copy',
    method: 'post',
    data: data
  })
}
