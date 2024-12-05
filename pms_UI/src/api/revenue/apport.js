import request from '@/utils/request'

// 查询收入生成列表
export function listApport(query) {
  return request({
    url: '/revenue/apport/list',
    method: 'get',
    params: query
  })
}

// 查询收入生成详细
export function getApport(id) {
  return request({
    url: '/revenue/apport/' + id,
    method: 'get'
  })
}

// 新增收入生成
export function addApport(data) {
  return request({
    url: '/revenue/apport',
    method: 'post',
    data: data
  })
}

// 修改收入生成
export function updateApport(data) {
  return request({
    url: '/revenue/apport',
    method: 'put',
    data: data
  })
}

// 删除收入生成
export function delApport(id) {
  return request({
    url: '/revenue/apport/' + id,
    method: 'delete'
  })
}

// 导出收入生成
export function exportApport(query) {
  return request({
    url: '/revenue/apport/export',
    method: 'get',
    params: query
  })
}

//收入生成分解前数据
export function getPreList(query) {
  return request({
    url: '/revenue/apport/beforeBreak',
    method: 'get',
    params: query
  })
}

//收入生成分解后数据
export function getAfterList(query) {
  return request({
    url: '/revenue/apport/afterBreak',
    method: 'get',
    params: query
  })
}

//验证是否有当前月的数据
export function resolveGenerateCheck1(query) {
  return request({
    url: '/revenue/apport/verification1',
    method: 'get',
    params: query
  })
}
//验证当前是否有对应方案
export function resolveGenerateCheck2(query) {
  return request({
    url: '/revenue/apport/verification2',
    method: 'get',
    params: query
  })
}
//门诊正常收入分解
export function resolveGenerate1(query) {
  return request({
    url: '/revenue/apport/generate1',
    method: 'get',
    params: query
  })
}

//门诊附加收入分解
export function resolveGenerate2(query) {
  return request({
    url: '/revenue/apport/generate2',
    method: 'get',
    params: query
  })
}

//住院正常收入分解
export function resolveGenerate3(query) {
  return request({
    url: '/revenue/apport/generate3',
    method: 'get',
    params: query
  })
}

//住院附加收入分解
export function resolveGenerate4(query) {
  return request({
    url: '/revenue/apport/generate4',
    method: 'get',
    params: query
  })
}

//分解前明细接口
export function resolveBefore(query) {
  return request({
    url: '/revenue/revenuenum/list',
    method: 'get',
    params: query
  })
}

//分解前明细接口
export function resolveAfter(query) {
  return request({
    url: '/revenue/apport/list',
    method: 'get',
    params: query
  })
}