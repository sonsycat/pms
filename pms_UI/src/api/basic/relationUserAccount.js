import request from '@/utils/request'






// 查询用户仓库关联列表
export function listRelationUserAccount(query) {
  return request({
    url: '/basic/relationUserAccount/list',
    method: 'get',
    params: query
  })
}

// 查询用户仓库关联详细
export function getRelationUserAccount(id) {
  return request({
    url: '/basic/relationUserAccount/' + id,
    method: 'get'
  })
}

// 新增用户仓库关联
export function addRelationUserAccount(data) {
  return request({
    url: '/basic/relationUserAccount',
    method: 'post',
    data: data
  })
}

// 修改用户仓库关联
export function updateRelationUserAccount(data) {
  return request({
    url: '/basic/relationUserAccount',
    method: 'put',
    data: data
  })
}

// 删除用户仓库关联
export function delRelationUserAccount(id) {
  return request({
    url: '/basic/relationUserAccount/' + id,
    method: 'delete'
  })
}

// 导出用户仓库关联
export function exportRelationUserAccount(query) {
  return request({
    url: '/basic/relationUserAccount/export',
    method: 'get',
    params: query
  })
}
