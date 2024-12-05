import request from '@/utils/request'

// 查询指标集管理列表
export function listSet(query) {
  return request({
    url: '/evaluate/set/list',
    method: 'get',
    params: query
  })
}



// 查询指标集管理列表
export function listBonus(query) {
  return request({
    url: '/evaluate/set/bonusList',
    method: 'get',
    params: query
  })
}

// 查询指标集管理详细
export function getSet(id) {
  return request({
    url: '/evaluate/set/' + id,
    method: 'get'
  })
}

// 新增指标集管理
export function addSet(data) {
  return request({
    url: '/evaluate/set',
    method: 'post',
    data: data
  })
}


// 新增指标集管理
export function addBonus(data) {
  return request({
    url: '/evaluate/set/addBonus',
    method: 'post',
    data: data
  })
}

// 修改指标集管理
export function updateSet(data) {
  return request({
    url: '/evaluate/set',
    method: 'put',
    data: data
  })
}

// 删除指标集管理
export function delSet(id) {
  return request({
    url: '/evaluate/set/' + id,
    method: 'delete'
  })
}

// 导出指标集管理
export function exportSet(query) {
  return request({
    url: '/evaluate/set/export',
    method: 'get',
    params: query
  })
}

//指标集管理分类
export function setType(query) {
  return request({
    url: '/settype/settype/returnList',
    method: 'get',
    params: query
  })
}

//指标集分类树
export function getEvaTree(query) {
  return request({
    url: '/evaluate/bsc/returnList',
    method: 'get',
    params: query
  })
}

//待选指标列表
export function getEvaToSelTree(query) {
  return request({
    url: '/evaluate/index/list',
    method: 'get',
    params: query
  })
}

//已选指标列表
export function getEvaSeledTree(query) {
  return request({
    url: '/evaluate/setindex/list',
    method: 'get',
    params: query
  })
}

//指标选择保存
export function addEvaSet(data) {
  return request({
    url: '/evaluate/setindex',
    method: 'post',
    data: data
  })
}

//已选角色列表
export function getRoleSeledTree(query) {
  return request({
    url: '/evaluate/setrole/list',
    method: 'get',
    params: query
  })
}

//保存指标权限角色设置
export function subIndexRole(data) {
  return request({
    url: '/evaluate/setrole',
    method: 'post',
    data: data
  })
}

//指标集管理复制
export function indexCopy(data) {
  return request({
    url: '/evaluate/set/copy',
    method: 'post',
    data: data
  })
}
