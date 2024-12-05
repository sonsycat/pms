import request from '@/utils/request'

// 查询独立核算提成比列表
export function listCommission(query) {
  return request({
    url: '/commission/commission/list',
    method: 'get',
    params: query
  })
}

// 查询独立核算提成比详细
export function getCommission(id) {
  return request({
    url: '/commission/commission/' + id,
    method: 'get'
  })
}

// 新增独立核算提成比
export function addCommission(data) {
  return request({
    url: '/commission/commission',
    method: 'post',
    data: data
  })
}

// 修改独立核算提成比
export function updateCommission(data) {
  return request({
    url: '/commission/commission',
    method: 'put',
    data: data
  })
}

// 删除独立核算提成比
export function delCommission(id) {
  return request({
    url: '/commission/commission/' + id,
    method: 'delete'
  })
}

// 导出独立核算提成比
export function exportCommission(query) {
  return request({
    url: '/commission/commission/export',
    method: 'get',
    params: query
  })
}

export function copyData(data) {
  return request({
    url: '/commission/commission/copy',
    method: 'post',
    data: data
  })
}
