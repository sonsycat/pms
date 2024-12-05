import request from '@/utils/request'

// 查询MybatisPlus测试列表
export function listPlus(query) {
  return request({
    url: '/demo/plus/list',
    method: 'get',
    params: query
  })
}

// 查询MybatisPlus测试详细
export function getPlus(inviteId) {
  return request({
    url: '/demo/plus/' + inviteId,
    method: 'get'
  })
}

// 新增MybatisPlus测试
export function addPlus(data) {
  return request({
    url: '/demo/plus',
    method: 'post',
    data: data
  })
}

// 修改MybatisPlus测试
export function updatePlus(data) {
  return request({
    url: '/demo/plus',
    method: 'put',
    data: data
  })
}

// 删除MybatisPlus测试
export function delPlus(inviteId) {
  return request({
    url: '/demo/plus/' + inviteId,
    method: 'delete'
  })
}

// 导出MybatisPlus测试
export function exportPlus(query) {
  return request({
    url: '/demo/plus/export',
    method: 'get',
    params: query
  })
}