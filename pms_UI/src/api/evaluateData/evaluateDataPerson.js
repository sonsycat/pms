import request from '@/utils/request'

// 查询科室对应人员指标列表
export function getAccountPerson(query) {
  return request({
    url: '/evaluate/data/getAccountPersonIndex',
    method: 'get',
    params: query
  })
}
// 查询核算科室奖金指标列表
export function listPersonBonus(query) {
  return request({
    url: '/evaluate/data/listAccountPersonBonus',
    method: 'get',
    params: query
  })
}
// 查询医生列表
export function listDoctor(query) {
  return request({
    url: '/evaluate/data/doctorList',
    method: 'get',
    params: query
  })
}
// 查询本月没有奖金数据的医生列表
export function listDoctorNotBonus(query) {
  return request({
    url: '/evaluate/data/doctorListNotBonus',
    method: 'get',
    params: query
  })
}
// 查询科室列表
export function listDept(query) {
  return request({
    url: '/evaluate/data/deptList',
    method: 'get',
    params: query
  })
}
