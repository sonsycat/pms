import request from '@/utils/request'

// 查询HIS诊疗项目管理明细列表
export function listDiagnosisDetail(query) {
  return request({
    url: '/basic/diagnosisDetail/list',
    method: 'get',
    params: query
  })
}

// 查询HIS诊疗项目管理明细详细
export function getDiagnosisDetail(id) {
  return request({
    url: '/basic/diagnosisDetail/' + id,
    method: 'get'
  })
}

// 新增HIS诊疗项目管理明细
export function addDiagnosisDetail(data) {
  return request({
    url: '/basic/diagnosisDetail',
    method: 'post',
    data: data
  })
}

// 修改HIS诊疗项目管理明细
export function updateDiagnosisDetail(data) {
  return request({
    url: '/basic/diagnosisDetail',
    method: 'put',
    data: data
  })
}

// 删除HIS诊疗项目管理明细
export function delDiagnosisDetail(id) {
  return request({
    url: '/basic/diagnosisDetail/' + id,
    method: 'delete'
  })
}

// 导出HIS诊疗项目管理明细
export function exportDiagnosisDetail(query) {
  return request({
    url: '/basic/diagnosisDetail/export',
    method: 'get',
    params: query
  })
}