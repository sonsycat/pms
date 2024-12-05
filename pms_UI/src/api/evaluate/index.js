import request from '@/utils/request'

// 查询指标管理列表
export function listIndex(query) {
  return request({
    url: '/evaluate/index/list',
    method: 'get',
    params: query
  })
}

// 查询指标管理列表(分页)
export function listBonus(query) {
  return request({
    url: '/evaluate/index/bonusList',
    method: 'get',
    params: query
  })
}

// 查询指标管理列表
export function listBonusAll(query) {
  return request({
    url: '/evaluate/index/bonusListAll',
    method: 'get',
    params: query
  })
}

// 查询指标管理详细
export function getIndex(id) {
  return request({
    url: '/evaluate/index/' + id,
    method: 'get'
  })
}

// 新增指标管理
export function addIndex(data) {
  return request({
    url: '/evaluate/index',
    method: 'post',
    data: data
  })
}


// 新增指标管理
export function addBonus(data) {
  return request({
    url: '/evaluate/index/addBonus',
    method: 'post',
    data: data
  })
}

// 修改指标管理
export function updateIndex(data) {
  return request({
    url: '/evaluate/index',
    method: 'put',
    data: data
  })
}

// 删除指标管理
export function delIndex(id) {
  return request({
    url: '/evaluate/index/' + id,
    method: 'delete'
  })
}

// 导出指标管理
export function exportIndex(query) {
  return request({
    url: '/evaluate/index/export',
    method: 'get',
    params: query
  })
}

// 指标Bsc分类接口
export function getBsc(query){
  return request({
    url: '/evaluate/bsc/returnList',
    method: 'get',
    params: query
  })
}

//指标表达式获取列表
export function getIndicatorList(query){
  return request({
    url: '/evaluate/bsc/listForTree',
    method: 'get',
    params: query
  })
}

//指标管理查看数据
export function getIndicatorDatas(query){
  return request({
    url: '/evaluate/index/creatDataInfo',
    method: 'get',
    params: query
  })
}

//指标管理查看数据
export function createDataFromSql(data){
  return request({
    url: '/evaluate/index/createDataFromSql',
    method: 'post',
    params: data
  })
}

//已选指标列表
export function listBonusIndex(query) {
  return request({
    url: '/evaluate/bonusIndex/list',
    method: 'get',
    params: query
  })
}

//指标选择保存
export function saveBonusIndex(data) {
  return request({
    url: '/evaluate/bonusIndex',
    method: 'post',
    data: data
  })
}
