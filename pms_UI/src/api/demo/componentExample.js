import request from '@/utils/request'

// 查询招聘信息列表
export function listInvite(query) {
  return request({
    url: '/demo/ComponentExample/list',
    method: 'get',
    params: query
  })
}

// 查询子列表文档数据
export function listDocument(query) {
  return request({
    url: '/demo/ComponentExample/listDocument',
    method: 'get',
    params: query
  })
}

// 获取级联数据
export function getCasOptions() {
  return request({
    url: '/demo/ComponentExample/getCasOptions',
    method: 'get'
  })
}

// 获取chart数据
export function getChartData() {
  return request({
    url: '/demo/ComponentExample/getChartData',
    method: 'get'
  })
}


