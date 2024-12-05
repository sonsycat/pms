import request from '@/utils/request'

// 查询多源数据库设置列表
export function listDatabase(query) {
  return request({
    url: '/apportionment/database/list',
    method: 'get',
    params: query
  })
}

// 查询多源数据库设置详细
export function getDatabase(id) {
  return request({
    url: '/apportionment/database/' + id,
    method: 'get'
  })
}

// 新增多源数据库设置
export function addDatabase(data) {
  return request({
    url: '/apportionment/database',
    method: 'post',
    data: data
  })
}

// 修改多源数据库设置
export function updateDatabase(data) {
  return request({
    url: '/apportionment/database',
    method: 'put',
    data: data
  })
}

// 删除多源数据库设置
export function delDatabase(id) {
  return request({
    url: '/apportionment/database/' + id,
    method: 'delete'
  })
}

// 导出多源数据库设置
export function exportDatabase(query) {
  return request({
    url: '/apportionment/database/export',
    method: 'get',
    params: query
  })
}