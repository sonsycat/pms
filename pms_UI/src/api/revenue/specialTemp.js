import request from '@/utils/request'

// 查询特殊收入项目方案临时列表
export function listSpecialTemp(query) {
  return request({
    url: '/revenue/specialTemp/list',
    method: 'get',
    params: query
  })
}

// 查询特殊收入项目方案临时详细
export function getSpecialTemp(id) {
  return request({
    url: '/revenue/specialTemp/' + id,
    method: 'get'
  })
}

// 新增特殊收入项目方案临时
export function addSpecialTemp(data) {
  return request({
    url: '/revenue/specialTemp',
    method: 'post',
    data: data
  })
}

// 修改特殊收入项目方案临时
export function updateSpecialTemp(data) {
  return request({
    url: '/revenue/specialTemp',
    method: 'put',
    data: data
  })
}

// 删除特殊收入项目方案临时
export function delSpecialTemp(data) {
  return request({
    url: '/revenue/specialTemp',
    method: 'delete',
    data: data
  })
}

// 导出特殊收入项目方案临时
export function exportSpecialTemp(query) {
  return request({
    url: '/revenue/specialTemp/export',
    method: 'get',
    params: query
  })
}
