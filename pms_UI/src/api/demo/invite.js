import request from '@/utils/request'

// 查询demo招聘列表
export function listInvite(query) {
  return request({
    url: '/demo/invite/list',
    method: 'get',
    params: query
  })
}

// 查询demo招聘详细
export function getInvite(inviteId) {
  return request({
    url: '/demo/invite/' + inviteId,
    method: 'get'
  })
}

// 新增demo招聘
export function addInvite(data) {
  return request({
    url: '/demo/invite',
    method: 'post',
    data: data
  })
}

// 修改demo招聘
export function updateInvite(data) {
  return request({
    url: '/demo/invite',
    method: 'put',
    data: data
  })
}

// 删除demo招聘
export function delInvite(inviteId) {
  return request({
    url: '/demo/invite/' + inviteId,
    method: 'delete'
  })
}

// 导出demo招聘
export function exportInvite(query) {
  return request({
    url: '/demo/invite/export',
    method: 'get',
    params: query
  })
}
