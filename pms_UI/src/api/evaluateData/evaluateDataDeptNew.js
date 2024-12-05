import request from '@/utils/request'

// 查询核算科室对应指标列表
export function getAccountDept(query) {
  return request({
    url: '/bonus/calculationNew/getAccountDeptIndex',
    method: 'get',
    params: query
  })
}
// 查询核算科室奖金指标列表
export function listDeptBonus(query) {
  return request({
    url: '/bonus/calculationNew/listAccountDeptBonus',
    method: 'get',
    params: query
  })
}
// 导出科室奖金
export function exportDeptBonus(query) {
  return request({
    url: '/evaluate/data/exportDeptBonus',
    method: 'get',
    params: query
  })
}

// 查询核算科室对应指标列表
export function deptBonusDetail(query) {
  return request({
    url: '/evaluate/data/deptBonusDetail',
    method: 'get',
    params: query
  })
}
