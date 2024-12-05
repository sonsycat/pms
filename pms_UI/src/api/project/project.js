import request from '@/utils/request'

// 查询成本项目管理列表
export function listProject(query) {
  return request({
    url: '/project/project/list',
    method: 'get',
    params: query
  })
}

// 查询科室列表
export function getDeptList(compCode,isCostCalculate) {
  return request({
    url: '/basic/public/getDeptList?compCode='+compCode+'&isCostCalculate='+isCostCalculate,
    method: 'get',
  })
}

// 查询成本项目管理详细
export function getProject(id) {
  return request({
    url: '/project/project/' + id,
    method: 'get'
  })
}

// 新增成本项目管理
export function addProject(data) {
  return request({
    url: '/project/project',
    method: 'post',
    data: data
  })
}

export function checkCode(data) {
  return request({
    url: '/project/project/checkCode',
    method: 'get',
    params: data
  })
}


// 修改成本项目管理
export function updateProject(data) {
  return request({
    url: '/project/project',
    method: 'put',
    data: data
  })
}

// 成本项目管理科室设置
export function updateProjectDept(data) {
  return request({
    url: '/project/project/dept',
    method: 'put',
    data: data
  })
}

// 删除成本项目管理
export function delProject(id) {
  return request({
    url: '/project/project/' + id,
    method: 'delete'
  })
}

// 导出成本项目管理
export function exportProject(query) {
  return request({
    url: '/project/project/export',
    method: 'get',
    params: query
  })
}

//成本分摊项目pop列表数据
export function getProjectPop(query) {
  return request({
    url: '/apportionment/project/selectForProject',
    method: 'get',
    params: query
  })
}
//分摊方案字典查询
export function getDictList(query){
  return request({
    url:'/basic/public/projectft',
    method:'get',
    params:query
  })
}
