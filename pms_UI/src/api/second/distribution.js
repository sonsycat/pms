import request from '@/utils/request'

// 查询二次分配指标集设置列表
export function listDistribution(query) {
  return request({
    url: '/second/distribution/list',
    method: 'get',
    params: query
  })
}

// 查询二次分配指标集设置详细
export function getDistribution(id) {
  return request({
    url: '/second/distribution/' + id,
    method: 'get'
  })
}

// 新增二次分配指标集设置
export function addDistribution(data) {
  return request({
    url: '/second/distribution',
    method: 'post',
    data: data
  })
}

// 修改二次分配指标集设置
export function updateDistribution(data) {
  return request({
    url: '/second/distribution',
    method: 'put',
    data: data
  })
}

// 删除二次分配指标集设置
export function delDistribution(id) {
  return request({
    url: '/second/distribution/' + id,
    method: 'delete'
  })
}

// 导出二次分配指标集设置
export function exportDistribution(query) {
  return request({
    url: '/second/distribution/export',
    method: 'get',
    params: query
  })
}