import request from '@/utils/request'

// 查询科室列表
export function listRoom(query) {
  return request({
    url: '/system/room/list',
    method: 'get',
    params: query
  })
}

// 查询科室详细
export function getRoom(deptRoomId) {
  return request({
    url: '/system/room/' + deptRoomId,
    method: 'get'
  })
}

// 新增科室
export function addRoom(data) {
  return request({
    url: '/system/room',
    method: 'post',
    data: data
  })
}

// 修改科室
export function updateRoom(data) {
  return request({
    url: '/system/room',
    method: 'put',
    data: data
  })
}

// 删除科室
export function delRoom(deptRoomId) {
  return request({
    url: '/system/room/' + deptRoomId,
    method: 'delete'
  })
}

// 导出科室
export function exportRoom(query) {
  return request({
    url: '/system/room/export',
    method: 'get',
    params: query
  })
}

// 通过机构id查询科室数据
export function getDeptRoomListByDeptId(query) {
  return request({
    url: '/system/room/getDeptRoomListByDeptId',
    method: 'get',
    params: query
  })
}
