import request from '@/utils/request'

// 查询仓库关系设置列表
export function listRelationStore(query) {
  return request({
    url: '/basic/relationStore/list',
    method: 'get',
    params: query
  })
}

// 查询仓库关系设置详细
export function getRelationStore(id) {
  return request({
    url: '/basic/relationStore/' + id,
    method: 'get'
  })
}

// 新增仓库关系设置
export function addRelationStore(data) {
  return request({
    url: '/basic/relationStore',
    method: 'post',
    data: data
  })
}

// 修改仓库关系设置
export function updateRelationStore(data) {
  return request({
    url: '/basic/relationStore',
    method: 'put',
    data: data
  })
}

// 删除仓库关系设置
export function delRelationStore(id) {
  return request({
    url: '/basic/relationStore/' + id,
    method: 'delete'
  })
}

// 导出仓库关系设置
export function exportRelationStore(query) {
  return request({
    url: '/basic/relationStore/export',
    method: 'get',
    params: query
  })
}