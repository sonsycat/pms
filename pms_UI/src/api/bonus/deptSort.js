import request from '@/utils/request'

// 查询核算单位类别设置列表
export function listDeptSort(query) {
  return request({
    url: '/bonus/deptSort/list',
    method: 'get',
    params: query
  })
}

// 查询核算单位类别设置详细
export function getDeptSort(id) {
  return request({
    url: '/bonus/deptSort/' + id,
    method: 'get'
  })
}

//保存数据
export function saveData(data) {
  return request({
    url: '/bonus/deptSort',
    method: 'post',
    data: data
  })
}

//复制数据年月
export function copyDataByDate(data) {
  return request({
    url: '/bonus/deptSort/copy',
    method: 'post',
    data: data
  })
}

