import request from '@/utils/request'

// 查询HIS收入类别列表
export function listIncome(query) {
  return request({
    url: '/basic/income/list',
    method: 'get',
    params: query
  })
}

// 查询HIS收入类别详细
export function getIncome(id) {
  return request({
    url: '/basic/income/' + id,
    method: 'get'
  })
}

// 新增HIS收入类别
export function addIncome(data) {
  return request({
    url: '/basic/income',
    method: 'post',
    data: data
  })
}

// 修改HIS收入类别
export function updateIncome(data) {
  return request({
    url: '/basic/income',
    method: 'put',
    data: data
  })
}

// 删除HIS收入类别
export function delIncome(id) {
  return request({
    url: '/basic/income/' + id,
    method: 'delete'
  })
}

// 导出HIS收入类别
export function exportIncome(query) {
  return request({
    url: '/basic/income/export',
    method: 'get',
    params: query
  })
}