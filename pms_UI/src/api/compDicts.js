import request from '@/utils/request'

//不启用供应商获取价格
export function getPrice(query) {
    return request({
      url: '/stock/stock/getPrice',
      method: 'get',
      params: query
    })
}

//生成自定义条码
export function getBarcode(query) {
    return request({
      url: '/basic/public/generateBarCode',
      method: 'get',
      params: query
    })
}
//生成物资条码 String barCode, int len
export function getMaterBarcode(query) {
    return request({
      url: '/basic/public/generateMaterialBarCode',
      method: 'get',
      params: query
    })
}
//价格pom
export function priceList(query) {
    return request({
      url: '/supplier/product/selectForPrice',
      method: 'get',
      params: query
    })
}

export function queryLastSupplier(query) {
    return request({
      url: '/purchase/shoppingplandetail/queryLastSupplier',
      method: 'get',
      params: query
    })
}

// 物资分类
export function spdMclist(query) {
    return request({
      url: '/basic/public/spdMclist',
      method: 'get',
      params: query
    })
}
// 物资字典
export function spdMdlist(query) {
    return request({
        url: '/basic/public/spdMdlist',
        method: 'get',
        params: query
    })
}
//export function spdMdlist2(query) {
//  return request({
//      url: '/supplier/supply/queryForSupply',
//      method: 'get',
//      params: query
//  })
//}

//产品证照pom
export function productList(query) {
    return request({
      url: '/supplier/product/listUnit',
      method: 'get',
      params: query
    })
}

// 货架编码下拉
export function shelfInfoByStore(query) {
    return request({
        url: '/basic/public/shelfInfoByStore',
        method: 'get',
        params: query
    })
}

// 货架编码pop
export function shelfInfoByStorePop(query) {
    return request({
        url: '/basic/public/shelfInfoByStorePop',
        method: 'get',
        params: query
    })
}

// 仓库+供应商带出 物资的相关信息（前端是pop框）（仓库与物资的关系，供应商供货管理）
//  参数 storeCode(仓库)  supplierCode(供应商) 必填
export function queryMaterialByStoreAndSupplier(query) {
    return request({
        url: '/basic/public/queryMaterialByStoreAndSupplier',
        method: 'get',
        params: query
    })
}
// 仓库带出物资相关信息（前端是pop框）（仓库与物资的关系）
//  参数 storeCode(仓库) 必填
export function queryMaterialByStore(query) {
    return request({
        url: '/basic/public/queryMaterialByStore',
        method: 'get',
        params: query
    })
}
export function queryMaterialByStore2(query) {
    return request({
        url: '/basic/public/queryMaterialByDeptStore',
        method: 'get',
        params: query
    })
}
export function queryMaterialByStore3(query) {
    return request({
        url: '/stock/stock/listForCheck',
        method: 'get',
        params: query
    })
}
export function queryMaterialByStore4(query) {
    return request({
        url: '/secondary/secondary/listForCheck',
        method: 'get',
        params: query
    })
}

//  物资带出批次信息  materialCode物资编码必传
export function queryBatchByMaterialCode(query) {
    return request({
        url: '/basic/public/queryBatchByMaterialCode',
        method: 'get',
        params: query
    })
}

// 二级库 根据物资获取生产批次号
export function querySecondBatchByMaterialCode(query) {
    return request({
        url: '/secondary/secondaryDetail/lists',
        method: 'get',
        params: query
    })
}
// 诊疗项目  根据物资编码获取批次号带出二级库
export function querySecondBatchStoreByMaterial(query) {
    return request({
        url: '/secondary/secondaryDetail/StockSecondarylists',
        method: 'get',
        params: query
    })
}

// 物资编码查供货管理的物资(审核通过)的供应商  --供应商  materialCode物资编码必传
export function querySupplierInfoByMaterialCode(query) {
    return request({
        url: '/basic/public/querySupplierInfoByMaterialCode',
        method: 'get',
        params: query
    })
}

// 物资+供应商  带出价格信息
// materialCode  supplierCode 必填
export function queryPriceByMaterialCode(query) {
    return request({
        url: '/basic/public/queryPriceByMaterialCode',
        method: 'get',
        params: query
    })
}

// 物资带出库位
// materialCode物资编码必传
export function queryStoreInfoByMaterialCode(query) {
    return request({
        url: '/basic/public/queryStoreInfoByMaterialCode',
        method: 'get',
        params: query
    })
}

// 库位带出货架
// locationCode 库位必填
export function queryShelfInfoByMaterialCode(query) {
    return request({
        url: '/basic/public/queryShelfInfoByMaterialCode',
        method: 'get',
        params: query
    })
}

// 根据仓库编码获取仓库信息 /basic/store/byStoreCode/{storeCode}
export function getStoreInfo(storeCode) {
    return request({
      url: `/basic/store/byStoreCode/${storeCode}`,
      method: 'get'
    })
}

// 根据账套获取账期数据
export function getPeriodName(query) {
    return request({
        url: '/basic/public/getPeriodName',
        method: 'get',
        params: query
    })
} 


// /basic/public/queryBatchByBatchCode
// 生产批次号字典
export function queryBatchByBatchCode(query) {
    return request({
        url: '/basic/public/queryBatchByBatchCode',
        method: 'get',
        params: query
    })
}



