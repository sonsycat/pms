import request from '@/utils/request'

// 查询科室列表
export function listDeptRoom(query) {
  return request({
    url: '/basic/deptRoom/list',
    method: 'get',
    params: query
  })
}

// 查询科室详细
export function getDeptRoom(deptRoomId) {
  return request({
    url: '/basic/deptRoom/' + deptRoomId,
    method: 'get'
  })
}

// 新增科室
export function addDeptRoom(data) {
  return request({
    url: '/basic/deptRoom',
    method: 'post',
    data: data
  })
}

// 修改科室
export function updateDeptRoom(data) {
  return request({
    url: '/basic/deptRoom',
    method: 'put',
    data: data
  })
}

// 删除科室
export function delDeptRoom(deptRoomId) {
  return request({
    url: '/basic/deptRoom/' + deptRoomId,
    method: 'delete'
  })
}

// 导出科室
export function exportDeptRoom(query) {
  return request({
    url: '/basic/deptRoom/export',
    method: 'get',
    params: query
  })
}

// 根据单位id查询科室树
export function deptRoomTree(query) {
  return request({
    url: '/basic/deptRoom/deptRoomTree',
    method: 'get',
    params: query
  })
}

// 查询科室列表【通知公告】
export function deptRoomListForNotice(query) {
  return request({
    url: '/basic/deptRoom/deptRoomListForNotice',
    method: 'get',
    params: query
  })
}
