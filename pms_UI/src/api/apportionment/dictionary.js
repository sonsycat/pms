import request from '@/utils/request'

// 查询比例分摊字典管理列表
export function listDictionary(query) {
  return request({
    url: '/apportionment/dictionary/list',
    method: 'get',
    params: query
  })
}

// 查询比例分摊字典管理列表
export function listDictionarys(data) {
  return request({
    url: '/apportionment/dictionary/lists',
    method: 'post',
    data: data
  })
}

// 查询比例分摊字典管理详细
export function getDictionary(id) {
  return request({
    url: '/apportionment/dictionary/' + id,
    method: 'get'
  })
}

// 新增比例分摊字典管理
export function addDictionary(data) {
  return request({
    url: '/apportionment/dictionary',
    method: 'post',
    data: data
  })
}

// 修改比例分摊字典管理
export function updateDictionary(data) {
  return request({
    url: '/apportionment/dictionary',
    method: 'put',
    data: data
  })
}

// 删除比例分摊字典管理
export function delDictionary(id) {
  return request({
    url: '/apportionment/dictionary/' + id,
    method: 'delete'
  })
}

// 导出比例分摊字典管理
export function exportDictionary(query) {
  return request({
    url: '/apportionment/dictionary/export',
    method: 'get',
    params: query
  })
}
