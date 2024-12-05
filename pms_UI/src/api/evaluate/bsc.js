import request from '@/utils/request'

// 查询BSC分类列表
export function listBsc(query) {
  return request({
    url: '/evaluate/bsc/list',
    method: 'get',
    params: query
  })
}

export function spdMclist(query) {
  return request({
    url: '/evaluate/bsc/returnList',
    method: 'get',
    params: query
  })
}

// 查询BSC分类详细
export function getBsc(id) {
  return request({
    url: '/evaluate/bsc/' + id,
    method: 'get'
  })
}

// 新增BSC分类
export function addBsc(data) {
  return request({
    url: '/evaluate/bsc',
    method: 'post',
    data: data
  })
}


// 新增BSC分类
export function updateIsStop(data) {
  return request({
    url: '/evaluate/bsc/isStop',
    method: 'post',
    data: data
  })
}

// 修改BSC分类
export function updateBsc(data) {
  return request({
    url: '/evaluate/bsc',
    method: 'put',
    data: data
  })
}

// 删除BSC分类
export function delBsc(id) {
  return request({
    url: '/evaluate/bsc/' + id,
    method: 'delete'
  })
}

// 导出BSC分类
export function exportBsc(query) {
  return request({
    url: '/evaluate/bsc/export',
    method: 'get',
    params: query
  })
}
