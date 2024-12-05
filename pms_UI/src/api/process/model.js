import request from '@/utils/request'
import Vue from 'vue'
let processUrl = Vue.prototype.processUrl

// 查询流程模块列表
export function listModel(query) {
  return request({
    url: '/process/model/list',
    method: 'get',
    params: query
  })
}

// 查询OA流程模块列表
export function listsModel(query) {
  return request({
    url: `${processUrl}/api/wf/getMyProcesses?systemId=PMS`,
    method: 'post',
    params: query
  })
}

// 查比例模块列表
export function listBacklog(query) {
  return request({
    url:`${processUrl}/api/wf/todoList`,
    method: 'post',
    params: query
  })
}

// 查询方案模块列表
export function listIntransit(query) {
  return request({
    url:`${processUrl}/api/wf/runningList`,
    method: 'post',
    params: query
  })
}

// 查询分摊模块列表
export function listAlready(query) {
  return request({
    url:`${processUrl}/api/wf/historicList`,
    method: 'post',
    params: query
  })
}

// 查询当前节点名称
export function currActivityName(query) {
  return request({
    url: `${processUrl}/api/wf/queryCurrActivity`,
    method: 'post',
    params: query
  })
}

// 查询流程模块详细
export function getModel(id) {
  return request({
    url: '/process/model/' + id,
    method: 'get'
  })
}

// 新增流程模块
export function addModel(data) {
  return request({
    url: '/process/model',
    method: 'post',
    data: data
  })
}

// 修改流程模块
export function updateModel(data) {
  return request({
    url: '/process/model',
    method: 'put',
    data: data
  })
}

// 删除流程模块
export function delModel(id) {
  return request({
    url: '/process/model/' + id,
    method: 'delete'
  })
}

// 导出流程模块
export function exportModel(query) {
  return request({
    url: '/process/model/export',
    method: 'get',
    params: query
  })
}
