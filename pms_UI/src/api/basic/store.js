import request from '@/utils/request'

// 查询仓库管理列表
export function listStore(query) {
  return request({
    url: '/basic/store/list',
    method: 'get',
    params: query
  })
}


// 根据用户查询仓库管理列表
export function listStoreUser(query) {
  return request({
    url: '/basic/store/listByUser',
    method: 'get',
    params: query
  })
}

// 根据关系分类查询仓库管理列表
export function listStoreRelation(query) {
  return request({
    url: '/basic/store/listByRelation',
    method: 'get',
    params: query
  })
}

// 查询仓库管理详细
export function getStore(id) {
  return request({
    url: '/basic/store/' + id,
    method: 'get'
  })
}

// 新增仓库管理
export function addStore(data) {
  return request({
    url: '/basic/store',
    method: 'post',
    data: data
  })
}

// 修改仓库管理
export function updateStore(data) {
  return request({
    url: '/basic/store',
    method: 'put',
    data: data
  })
}

// 删除仓库管理
export function delStore(id) {
  return request({
    url: '/basic/store/' + id,
    method: 'delete'
  })
}

// 导出仓库管理
export function exportStore(query) {
  return request({
    url: '/basic/store/export',
    method: 'get',
    params: query
  })
}
