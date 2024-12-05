import request from '@/utils/request'

// 查询人员信息列表
export function listSysEmp(query) {
  return request({
    url: '/basic/sysEmp/list',
    method: 'get',
    params: query
  })
}

// 查询人员信息详细
export function getSysEmp(id) {
  return request({
    url: '/basic/sysEmp/' + id,
    method: 'get'
  })
}

// 新增人员信息
export function addSysEmp(data) {
  return request({
    url: '/basic/sysEmp',
    method: 'post',
    data: data
  })
}

// 修改人员信息
export function updateSysEmp(data) {
  return request({
    url: '/basic/sysEmp',
    method: 'put',
    data: data
  })
}

// 删除人员信息
export function delSysEmp(id) {
  return request({
    url: '/basic/sysEmp/' + id,
    method: 'delete'
  })
}

// 导出人员信息
export function exportSysEmp(query) {
  return request({
    url: '/basic/sysEmp/export',
    method: 'get',
    params: query
  })
}

// 获取用户下拉列表
export function listForUser(query) {
  return request({
    url: '/basic/sysEmp/listForUser',
    method: 'get',
    params: query
  })
}

// 获取工号
export function generateEmpCode() {
  return request({
    url: '/basic/sysEmp/generateEmpCode',
    method: 'get'
  })

}
