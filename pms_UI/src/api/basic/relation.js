import request from '@/utils/request'

// 查询关系分类列表
export function listRelation(query) {
  return request({
    url: '/basic/relation/list',
    method: 'get',
    params: query
  })
}

// 查询关系分类详细
export function getRelation(id) {
  return request({
    url: '/basic/relation/' + id,
    method: 'get'
  })
}

// 新增关系分类
export function addRelation(data) {
  return request({
    url: '/basic/relation',
    method: 'post',
    data: data
  })
}

// 修改关系分类
export function updateRelation(data) {
  return request({
    url: '/basic/relation',
    method: 'put',
    data: data
  })
}

// 删除关系分类
export function delRelation(id) {
  return request({
    url: '/basic/relation/' + id,
    method: 'delete'
  })
}

// 导出关系分类
export function exportRelation(query) {
  return request({
    url: '/basic/relation/export',
    method: 'get',
    params: query
  })
}