import request from '@/utils/request'

// 查询更新日期列表
export function listDate(query) {
  return request({
    url: '/update/date/list',
    method: 'get',
    params: query
  })
}

// 查询更新日期详细
export function getDate(id) {
  return request({
    url: '/update/date/' + id,
    method: 'get'
  })
}

// 新增更新日期
export function addDate(data) {
  return request({
    url: '/update/date',
    method: 'post',
    data: data
  })
}

// 修改更新日期
export function updateDate(data) {
  return request({
    url: '/update/date',
    method: 'put',
    data: data
  })
}

// 删除更新日期
export function delDate(id) {
  return request({
    url: '/update/date/' + id,
    method: 'delete'
  })
}

// 导出更新日期
export function exportDate(query) {
  return request({
    url: '/update/date/export',
    method: 'get',
    params: query
  })
}