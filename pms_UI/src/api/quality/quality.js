import request from '@/utils/request'

// 查询质量数据生成列表
export function listQuality(query) {
  return request({
    url: '/quality/quality/selectSumData',
    method: 'get',
    params: query
  })
}

// 新增质量数据生成
export function addQuality(data) {
  return request({
    url: '/quality/quality/creatZLSJ',
    method: 'post',
    data: data
  })
}

// 查询质量数据查看详细
export function getQuality(query) {
  return request({
    url: '/quality/quality/listForD',
    method: 'get',
    params: query
  })
}

//质量数据详情
export function getQualityDetail(query) {
  return request({
    url: '/formProcess/selectForDetails',
    method: 'get',
    params: query
  })
}

// 修改质量数据生成
export function updateQuality(data) {
  return request({
    url: '/quality/quality',
    method: 'put',
    data: data
  })
}

// 删除质量数据生成
export function delQuality(id) {
  return request({
    url: '/quality/quality/' + id,
    method: 'delete'
  })
}

// 导出质量数据生成
export function exportQuality(query) {
  return request({
    url: '/quality/quality/export',
    method: 'get',
    params: query
  })
}

//验证当月是否有数据
export function checkQuality(query) {
  return request({
    url: '/quality/quality/queryForTrue',
    method: 'get',
    params: query
  })
}