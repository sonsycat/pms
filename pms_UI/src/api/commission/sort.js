import request from '@/utils/request'

// 查询独立核算提成比类别列表
export function listSort(query) {
  return request({
    url: '/commission/sort/list',
    method: 'get',
    params: query
  })
}

// 查询独立核算提成比类别详细
export function getSort(id) {
  return request({
    url: '/commission/sort/' + id,
    method: 'get'
  })
}

// 新增独立核算提成比类别
export function addSort(data) {
  return request({
    url: '/commission/sort',
    method: 'post',
    data: data
  })
}

// 修改独立核算提成比类别
export function updateSort(data) {
  return request({
    url: '/commission/sort',
    method: 'put',
    data: data
  })
}

// 删除独立核算提成比类别
export function delSort(id) {
  return request({
    url: '/commission/sort/' + id,
    method: 'delete'
  })
}

// 导出独立核算提成比类别
export function exportSort(query) {
  return request({
    url: '/commission/sort/export',
    method: 'get',
    params: query
  })
}