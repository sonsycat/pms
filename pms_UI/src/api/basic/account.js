import request from '@/utils/request'

// 查询账套管理列表
export function listAccount(query) {
  return request({
    url: '/basic/account/list',
    method: 'get',
    params: query
  })
}

// 查询账套管理详细
export function getAccount(id) {
  return request({
    url: '/basic/account/' + id,
    method: 'get'
  })
}

// 新增账套管理
export function addAccount(data) {
  return request({
    url: '/basic/account',
    method: 'post',
    data: data
  })
}
// 根据用户查询仓库管理列表
export function listAccountUser(query) {
  return request({
    url: '/basic/account/listByUser',
    method: 'get',
    params: query
  })
}

// 修改账套管理
export function updateAccount(data) {
  return request({
    url: '/basic/account',
    method: 'put',
    data: data
  })
}

// 删除账套管理
export function delAccount(id) {
  return request({
    url: '/basic/account/' + id,
    method: 'delete'
  })
}

// 导出账套管理
export function exportAccount(query) {
  return request({
    url: '/basic/account/export',
    method: 'get',
    params: query
  })
}
