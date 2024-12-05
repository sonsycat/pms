import request from '@/utils/request'

// 查询固定资产分类列表
export function listAssetskind(query) {
  return request({
    url: '/assets/assetskind/list',
    method: 'get',
    params: query
  })
}

// 查询固定资产分类详细
export function getAssetskind(id) {
  return request({
    url: '/assets/assetskind/' + id,
    method: 'get'
  })
}

// 新增固定资产分类
export function addAssetskind(data) {
  return request({
    url: '/assets/assetskind',
    method: 'post',
    data: data
  })
}

// 修改固定资产分类
export function updateAssetskind(data) {
  return request({
    url: '/assets/assetskind',
    method: 'put',
    data: data
  })
}

// 删除固定资产分类
export function delAssetskind(id) {
  return request({
    url: '/assets/assetskind/' + id,
    method: 'delete'
  })
}

// 导出固定资产分类
export function exportAssetskind(query) {
  return request({
    url: '/assets/assetskind/export',
    method: 'get',
    params: query
  })
}