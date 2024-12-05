import request from '@/utils/request'

// 查询质量考评指标列表
export function listIndex(query) {
  return request({
    url: '/quality/index/list',
    method: 'get',
    params: query
  })
}

// 查询质量考评指标详细
export function getIndex(id) {
  return request({
    url: '/quality/index/' + id,
    method: 'get'
  })
}

// 新增质量考评指标
export function addIndex(data) {
  return request({
    url: '/quality/index',
    method: 'post',
    data: data
  })
}

// 修改质量考评指标
export function updateIndex(data) {
  return request({
    url: '/quality/index',
    method: 'put',
    data: data
  })
}

// 删除质量考评指标
export function delIndex(id) {
  return request({
    url: '/quality/index/' + id,
    method: 'delete'
  })
}

// 导出质量考评指标
export function exportIndex(query) {
  return request({
    url: '/quality/index/export',
    method: 'get',
    params: query
  })
}

//获取模板信息
export function getTempCode(query) {
  return request({
    url: '/formDesignField/getForZB',
    method: 'get',
    params: query
  })
}