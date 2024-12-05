import request from '@/utils/request'

// 查询指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)列表
export function listTemp(query) {
  return request({
    url: '/bonus/temp/list',
    method: 'get',
    params: query
  })
}

// 查询指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)详细
export function getTemp(id) {
  return request({
    url: '/bonus/temp/' + id,
    method: 'get'
  })
}

// 新增指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
export function addTemp(data) {
  return request({
    url: '/bonus/temp',
    method: 'post',
    data: data
  })
}

// 修改指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
export function updateTemp(data) {
  return request({
    url: '/bonus/temp',
    method: 'put',
    data: data
  })
}

// 删除指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
export function delTemp(id) {
  return request({
    url: '/bonus/temp/' + id,
    method: 'delete'
  })
}

// 导出指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
export function exportTemp(query) {
  return request({
    url: '/bonus/temp/export',
    method: 'get',
    params: query
  })
}