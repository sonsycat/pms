import request from '@/utils/request'

// 查询编码规则列表
export function listMaterialrule(query) {
  return request({
    url: '/basic/materialrule/list',
    method: 'get',
    params: query
  })
}

// 查询编码规则详细
export function getMaterialrule(id) {
  return request({
    url: '/basic/materialrule/' + id,
    method: 'get'
  })
}

// 新增编码规则
export function addMaterialrule(data) {
  return request({
    url: '/basic/materialrule',
    method: 'post',
    data: data
  })
}

// 修改编码规则
export function updateMaterialrule(data) {
  return request({
    url: '/basic/materialrule',
    method: 'put',
    data: data
  })
}

// 删除编码规则
export function delMaterialrule(id) {
  return request({
    url: '/basic/materialrule/' + id,
    method: 'delete'
  })
}

// 导出编码规则
export function exportMaterialrule(query) {
  return request({
    url: '/basic/materialrule/export',
    method: 'get',
    params: query
  })
}