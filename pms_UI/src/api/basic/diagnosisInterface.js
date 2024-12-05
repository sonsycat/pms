import request from '@/utils/request'

// 查询HIS诊疗项目接口列表
export function listDiagnosisInterface(query) {
  return request({
    url: '/basic/diagnosisInterface/list',
    method: 'get',
    params: query
  })
}

// 查询HIS诊疗项目接口详细
export function getDiagnosisInterface(id) {
  return request({
    url: '/basic/diagnosisInterface/' + id,
    method: 'get'
  })
}

// 新增HIS诊疗项目接口
export function addDiagnosisInterface(data) {
  return request({
    url: '/basic/diagnosisInterface',
    method: 'post',
    data: data
  })
}

// 修改HIS诊疗项目接口
export function updateDiagnosisInterface(data) {
  return request({
    url: '/basic/diagnosisInterface',
    method: 'put',
    data: data
  })
}

// 删除HIS诊疗项目接口
export function delDiagnosisInterface(id) {
  return request({
    url: '/basic/diagnosisInterface/' + id,
    method: 'delete'
  })
}

// 导出HIS诊疗项目接口
export function exportDiagnosisInterface(query) {
  return request({
    url: '/basic/diagnosisInterface/export',
    method: 'get',
    params: query
  })
}