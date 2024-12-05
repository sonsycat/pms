import request from '@/utils/request'

// 查询分摊计算列表
export function listApport(query) {
  return request({
    url: '/apport/apport/list',
    method: 'get',
    params: query
  })
}

// 查询分摊计算详细
export function getApport(id) {
  return request({
    url: '/apport/apport/' + id,
    method: 'get'
  })
}

// 验证科室快照
export function addVerification1(data) {
  return request({
    url: '/apport/apport/verification1',
    method: 'post',
    data: data
  })
}
// 验证分摊方案
export function addVerification2(data) {
  return request({
    url: '/apport/apport/verification2',
    method: 'post',
    data: data
  })
}
// 验证分摊比例
export function addVerification3(data) {
  return request({
    url: '/apport/apport/verification3',
    method: 'post',
    data: data
  })
}
// 验证成本数据
export function addVerification(data) {
  return request({
    url: '/apport/apport/verification',
    method: 'post',
    data: data
  })
}

// 分摊计算
export function addApportFtjs(data) {
  return request({
    url: '/apport/apport/ftjs',
    method: 'post',
    data: data
  })
}

// 一级分摊
export function addApportYjftjs(data) {
  return request({
    url: '/apport/apport/yjftjs',
    method: 'post',
    data: data
  })
}

// 二级分摊
export function addApportEjftjs(data) {
  return request({
    url: '/apport/apport/ejftjs',
    method: 'post',
    data: data
  })
}

// 三级分摊
export function addApportSjftjs(data) {
  return request({
    url: '/apport/apport/sjftjs',
    method: 'post',
    data: data
  })
}

// 修改分摊计算
export function updateApport(data) {
  return request({
    url: '/apport/apport',
    method: 'put',
    data: data
  })
}

// 删除分摊计算
export function delApport(id) {
  return request({
    url: '/apport/apport/' + id,
    method: 'delete'
  })
}

// 导出分摊计算
export function exportApport(query) {
  return request({
    url: '/apport/apport/export',
    method: 'get',
    params: query
  })
}
