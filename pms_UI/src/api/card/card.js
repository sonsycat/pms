import request from '@/utils/request'

// 查询卡片信息列表
export function listCard(query) {
  return request({
    url: '/card/card/list',
    method: 'get',
    params: query
  })
}

// 查询卡片信息详细
export function getCard(id) {
  return request({
    url: '/card/card/' + id,
    method: 'get'
  })
}

// 查询卡片其它信息详细
export function getListCard(id) {
  return request({
    url: '/card/card/list/' + id,
    method: 'get'
  })
}

// 新增卡片信息
export function addCard(data) {
  return request({
    url: '/card/card',
    method: 'post',
    data: data
  })
}

// 修改卡片信息
export function updateCard(data) {
  return request({
    url: '/card/card',
    method: 'put',
    data: data
  })
}

// 修改其他卡片信息
export function updateCardElse(data) {
  return request({
    url: '/card/card/upd',
    method: 'put',
    data: data
  })
}

// 卡片拆分
export function updateSplitCard(data) {
  return request({
    url: '/card/card/split',
    method: 'put',
    data: data
  })
}


// 卡片自动拆分
export function voluntarilyCard(data) {
  return request({
    url: '/card/card/voluntarily',
    method: 'put',
    data: data
  })
}

// 删除卡片信息
export function delCard(id) {
  return request({
    url: '/card/card/' + id,
    method: 'delete'
  })
}

// 导出卡片信息
export function exportCard(query) {
  return request({
    url: '/card/card/export',
    method: 'get',
    params: query
  })
}
