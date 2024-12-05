import request from '@/utils/request'






// 查询用户仓库关联列表
export function listRelationUserStore(query) {
  return request({
    url: '/basic/relationUserStore/list',
    method: 'get',
    params: query
  })
}

// 查询用户仓库关联详细
export function getRelationUserStore(id) {
  return request({
    url: '/basic/relationUserStore/' + id,
    method: 'get'
  })
}

// 新增用户仓库关联
export function addRelationUserStore(data) {
  return request({
    url: '/basic/relationUserStore',
    method: 'post',
    data: data
  })
}

// 修改用户仓库关联
export function updateRelationUserStore(data) {
  return request({
    url: '/basic/relationUserStore',
    method: 'put',
    data: data
  })
}

// 删除用户仓库关联
export function delRelationUserStore(id) {
  return request({
    url: '/basic/relationUserStore/' + id,
    method: 'delete'
  })
}

// 导出用户仓库关联
export function exportRelationUserStore(query) {
  return request({
    url: '/basic/relationUserStore/export',
    method: 'get',
    params: query
  })
}
