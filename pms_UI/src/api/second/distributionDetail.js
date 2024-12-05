import request from '@/utils/request'

// 查询二次分配指标集明细列表
export function listDistributionDetail(query) {
  return request({
    url: '/second/distributionDetail/list',
    method: 'get',
    params: query
  })
}

// 查询二次分配指标集明细列表
export function listDistributionEvaluate(query) {
  return request({
    url: '/second/distributionDetail/listDistributionEvaluate',
    method: 'get',
    params: query
  })
}

// 查询二次分配指标集明细详细
export function getDistributionDetail(id) {
  return request({
    url: '/second/distributionDetail/' + id,
    method: 'get'
  })
}

// 新增二次分配指标集明细
export function addDistributionDetail(data) {
  return request({
    url: '/second/distributionDetail',
    method: 'post',
    data: data
  })
}

// 新增二次分配指标集明细
export function addDistributionEvaluate(data) {
  return request({
    url: '/second/distributionDetail/addDistributionEvaluate',
    method: 'post',
    data: data
  })
}

// 修改二次分配指标集明细
export function updateDistributionDetail(data) {
  return request({
    url: '/second/distributionDetail',
    method: 'put',
    data: data
  })
}

// 删除二次分配指标集明细
export function delDistributionDetail(id) {
  return request({
    url: '/second/distributionDetail/' + id,
    method: 'delete'
  })
}

// 导出二次分配指标集明细
export function exportDistributionDetail(query) {
  return request({
    url: '/second/distributionDetail/export',
    method: 'get',
    params: query
  })
}
