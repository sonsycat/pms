import request from '@/utils/request'

// 查询分摊方案管理列表
export function listProject(query) {
  return request({
    url: '/apportionment/project/list',
    method: 'get',
    params: query
  })
}

// 查询科室设置列表
export function listProjectDept(query) {
  return request({
    url: '/apportionment/dept/listProjectDept',
    method: 'get',
    params: query
  })
}

// 查询科室设置列表
export function listProjectDept2(query) {
  return request({
    url: '/apportionment/dept/listProjectDept2',
    method: 'get',
    params: query
  })
}

// 查询分摊方案科室比例列表
export function listDeptProject(query) {
  return request({
    url: '/apportionment/dept/list',
    method: 'get',
    params: query
  })
}

// 查询分摊方案比例列表
export function listRatio(query) {
  return request({
    url: '/apportionment/ratio/list',
    method: 'get',
    params: query
  })
}

// 查询分摊方案管理详细
export function getProject(id) {
  return request({
    url: '/apportionment/project/' + id,
    method: 'get'
  })
}

// 新增分摊方案管理
export function addDept(data) {
  return request({
    url: '/apportionment/dept',
    method: 'post',
    data: data
  })
}

// 新增分摊方案管理
export function copyProject(data) {
  return request({
    url: '/apportionment/project/copy',
    method: 'post',
    data: data
  })
}

// 新增比例
export function insertRatio(data) {
  return request({
    url: '/apportionment/ratio',
    method: 'post',
    data: data
  })
}

// 新增科室设置
export function addProjectDept(data) {
  return request({
    url: '/apportionment/dept/addProjectDept',
    method: 'post',
    data: data
  })
}

// 新增分摊方案管理
export function addProject(data) {
  return request({
    url: '/apportionment/project',
    method: 'post',
    data: data
  })
}

// 修改分摊方案管理
export function updateProject(data) {
  return request({
    url: '/apportionment/project',
    method: 'put',
    data: data
  })
}

// 删除分摊方案管理
export function delProject(id) {
  return request({
    url: '/apportionment/project/' + id,
    method: 'delete'
  })
}

// 导出分摊方案管理
export function exportProject(query) {
  return request({
    url: '/apportionment/project/export',
    method: 'get',
    params: query
  })
}
