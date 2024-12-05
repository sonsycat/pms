import request from '@/utils/request'

// 通过表名查询字段
export function getUserDefinedQueryField(query) {
  return request({
    url: '/system/userDefinedQuery/getUserDefinedQueryField',
    method: 'get',
    params: query
  })
}

// 自定义查询获取业务数据
export function userDefinedQueryDataList(data) {
  return request({
    url: '/system/userDefinedQuery/userDefinedQueryDataList',
    method: 'post',
    data: data
  })
}

// 新增自定义查询
export function addUserDefinedQuery(data) {
  return request({
    url: '/system/userDefinedQuery',
    method: 'post',
    data: data
  })
}

// 查询自定义查询列表
export function listUserDefinedQuery(query) {
  return request({
    url: '/system/userDefinedQuery/list',
    method: 'get',
    params: query
  })
}


// 删除自定义查询
export function delUserDefinedQuery(queryId) {
  return request({
    url: '/system/userDefinedQuery/' + queryId,
    method: 'delete'
  })
}


