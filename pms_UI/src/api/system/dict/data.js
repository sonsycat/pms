import request from '@/utils/request'

// 查询字典数据列表
export function priceList(query) {
  return request({
    url: '/supplier/supply/queryForSupply',
    method: 'get',
    params: query
  })
}
// 查询字典数据列表
export function priceListAll(query) {
  return request({
    url: '/supplier/supply/queryForSupplyAll',
    method: 'get',
    params: query
  })
}

// 查询字典数据列表
export function listData(query) {
  return request({
    url: '/system/dict/data/list',
    method: 'get',
    params: query
  })
}

// 查询字典数据详细
export function getData(dictCode) {
  return request({
    url: '/system/dict/data/' + dictCode,
    method: 'get'
  })
}

// 根据字典类型查询字典数据信息
export function getDicts(dictType) {
  return request({
    url: '/system/dict/data/type/' + dictType,
    method: 'get'
  })
}

// 新增字典数据
export function addData(data) {
  return request({
    url: '/system/dict/data',
    method: 'post',
    data: data
  })
}

// 修改字典数据
export function updateData(data) {
  return request({
    url: '/system/dict/data',
    method: 'put',
    data: data
  })
}

// 删除字典数据
export function delData(dictCode) {
  return request({
    url: '/system/dict/data/' + dictCode,
    method: 'delete'
  })
}

// 导出字典数据
export function exportData(query) {
  return request({
    url: '/system/dict/data/export',
    method: 'get',
    params: query
  })
}
// 查询仓库信息
export function getStoreInfo() {
  return request({
    url: '/basic/public/storeInfo',
    method: 'get'
  })
}
// 查询供应商信息 pop 可以通过供应商分类过滤
export function getSupplierInfoPop(query) {
  let data = Object.assign(query,{state:"2",isStop:"1"})
  return request({
    url: '/basic/supplier/selectSupplier',
    method: 'get',
    params: data
  })
}

export function getSupplierInfoPop2(query) {
  let data = Object.assign(query,{state:"2",isStop:"1"})
  return request({
    url: '/supplier/hospital/list',
    method: 'get',
    params: data
  })
}

export function getSupplierInfoPop3(query) {
  return request({
    url: '/supplier/hospital/listAll',
    method: 'get',
    params: query
  })
}

export function getSupplierInfoPop4(query) {
  return request({
    url: '/basic/public/supplierInfoByNosupplier',
    method: 'get',
    params: query
  })
}

//根据单位查询匹配的供应商
export function getSupplierInfoPop5(query) {
  return request({
    url: '/supplier/hospital/lists',
    method: 'get',
    params: query
  })
}

// 查询供应商信息 字典
export function getSupplierInfo(query) {
  return request({
    url: '/basic/supplier/queryAllSupplierInfo',
    method: 'get',
    params: query
  })
}
// 不启用供应商端 查询所有供应商
export function getNoSupplierInfo(query) {
  return request({
    url: '/basic/public/supplierInfo',
    method: 'get',
    params: query
  })
}

// 查询生产商信息
export function getProducerInfo() {
  return request({
    url: '/basic/public/producerInfo',
    method: 'get'
  })
}
// 查询用户关联的仓库(当前登录用户)
export function getStoreInfoByUser(query) {
  return request({
    url: '/basic/public/storeInfoByUser',
    method: 'get',
    params: query
  })
}

// 查询用户关联的仓库(当前登录用户) formPop
export function getStoreInfoByUserFormPop(query) {
  return request({
    url: '/basic/public/storeInfoByUserForPop',
    method: 'get',
    params: query
  })
}
// 查询当前用户同单位下的用户
export function getUserInfo() {
  return request({
    url: '/basic/public/userInfo',
    method: 'get'
  })
}
// 获取科室的
export function getDeptInfo() {
  return request({
    url: '/basic/public/deptInfo',
    method: 'get'
  })
}

// 查询岗位信息
export function getPostInfo() {
  return request({
    url: '/manage/manage/postInfo',
    method: 'get'
  })
}

//资产类型
export function assetsTypeInfo(query) {
  return request({
    url: '/basic/public/queryLastAssetsDict',
    method: 'get',
    params: query
  })
}

//根据仓库查询物资分类
export function classifyInfoByStore(query) {
  return request({
    url: '/basic/public/classifyInfoByStore',
    method: 'get',
    params: query
  })
}

//根据二级仓库查询物资分类
export function deptClassifyInfoByStore(query) {
  return request({
    url: '/basic/public/deptClassifyInfoByStore',
    method: 'get',
    params: query
  })
}


// 查询单位关联账套
export function getAccountByComp(query) {
  return request({
    url: '/basic/public/accountInfoByCompCode',
    method: 'get',
    params: query
  })
}

// 查询单位关联的科室
export function getDeptByComp(query) {
  return request({
    url: '/basic/public/deptInfoByCompCode',
    method: 'get',
    params: query
  })
}


// 查询单位关联的仓库
// compCode  storeClass isProxy isStop
export function getStoreInfoByComp(query) {
  return request({
    url: '/basic/public/storeInfoByCompCode',
    method: 'get',
    params: query
  })
}

export function getProject1(query) {
  return request({
    url: '/basic/public/getProject1',
    method: 'get',
    params: query
  })
}
export function getProject2(query) {
  return request({
    url: '/basic/public/getProject2',
    method: 'get',
    params: query
  })
}

export function getCostApport(compCode) {
  return request({
    url: '/basic/public/getCostApport?compCode='+compCode,
    method: 'get',
  })
}
export function getDistribution(query) {
  return request({
    url: '/basic/public/getDistribution',
    method: 'get',
    params: query
  })
}
