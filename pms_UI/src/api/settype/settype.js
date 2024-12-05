import request from '@/utils/request'

// 查询指标集分类列表
export function listSettype(query) {
  return request({
    url: '/settype/settype/list',
    method: 'get',
    params: query
  })
}

// 查询指标集分类详细
export function getSettype(id) {
  return request({
    url: '/settype/settype/' + id,
    method: 'get'
  })
}

export function updateIsStop(data) {
  return request({
    url: '/settype/settype/isStop',
    method: 'post',
    data: data
  })
}

export function spdMclist(query) {
  return request({
    url: '/settype/settype/returnList',
    method: 'get',
    params: query
  })
}

// 新增指标集分类
export function addSettype(data) {
  return request({
    url: '/settype/settype',
    method: 'post',
    data: data
  })
}

// 修改指标集分类
export function updateSettype(data) {
  return request({
    url: '/settype/settype',
    method: 'put',
    data: data
  })
}

// 删除指标集分类
export function delSettype(id) {
  return request({
    url: '/settype/settype/' + id,
    method: 'delete'
  })
}

// 导出指标集分类
export function exportSettype(query) {
  return request({
    url: '/settype/settype/export',
    method: 'get',
    params: query
  })
}
