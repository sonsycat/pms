import request from '@/utils/request'

// 切换角色
export function changeRole(roleId) {
  return request({
    url: '/system/user/changeRole',
    method: 'post',
    params: {roleId: roleId}
  })
}

// 切换机构
export function changeDept(deptId) {
  return request({
    url: '/system/user/changeDept',
    method: 'post',
    params: {deptId: deptId}
  })
}
