import request from '@/utils/request'

// 查询核算科室设置列表
export function listAccountDept(query) {
  return request({
    url: '/bonus/accountDept/list',
    method: 'get',
    params: query
  })
}

// 查询核算科室设置详细
export function getAccountDept(deptRoomCode, nowDate) {
  return request({
    url: '/bonus/accountDept/' + deptRoomCode + "?nowDate="+nowDate,
    method: 'get'
  })
}

// 新增核算科室设置
export function editAccountDept(data) {
  return request({
    url: '/bonus/accountDept',
    method: 'post',
    data: data
  })
}

// 删除核算科室设置
export function delAccountDept(id) {
  return request({
    url: '/bonus/accountDept/' + id,
    method: 'delete'
  })
}

// 导出核算科室设置
export function exportAccountDept(query) {
  return request({
    url: '/bonus/accountDept/export',
    method: 'get',
    params: query
  })
}

//复制数据年月
export function copyDataByDate(data) {
  return request({
    url: '/bonus/accountDept/copy',
    method: 'post',
    data: data
  })
}
