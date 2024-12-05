import request from '@/utils/request'

// 查询ADRG入组诊断及操作列表
export function listDrgs(query) {
  return request({
    url: '/drgs/enrollmentDiagnosisAndOperation/list',
    method: 'get',
    params: query
  })
}

// 查询ADRG入组诊断及操作详细
export function getDrgs(id) {
  return request({
    url: '/drgs/enrollmentDiagnosisAndOperation/' + id,
    method: 'get'
  })
}

// 新增ADRG入组诊断及操作
export function addDrgs(data) {
  return request({
    url: '/drgs/enrollmentDiagnosisAndOperation',
    method: 'post',
    data: data
  })
}

// 修改ADRG入组诊断及操作
export function updateDrgs(data) {
  return request({
    url: '/drgs/enrollmentDiagnosisAndOperation',
    method: 'put',
    data: data
  })
}

// 删除ADRG入组诊断及操作
export function delDrgs(id) {
  return request({
    url: '/drgs/enrollmentDiagnosisAndOperation/' + id,
    method: 'delete'
  })
}

// 导出ADRG入组诊断及操作
export function exportDrgs(query) {
  return request({
    url: '/drgs/enrollmentDiagnosisAndOperation/export',
    method: 'get',
    params: query
  })
}
