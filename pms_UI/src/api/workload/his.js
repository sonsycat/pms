import request from '@/utils/request'

// 查询工作量-HIS收费项目列表
export function listHis(query) {
  return request({
    url: '/workload/his/list',
    method: 'get',
    params: query
  })
}

// 查询工作量-HIS收费项目详细
export function getHis(id) {
  return request({
    url: '/workload/his/' + id,
    method: 'get'
  })
}

// 新增工作量-HIS收费项目
export function addHis(data) {
  return request({
    url: '/workload/his',
    method: 'post',
    data: data
  })
}

// 修改工作量-HIS收费项目
export function updateHis(data) {
  return request({
    url: '/workload/his',
    method: 'put',
    data: data
  })
}

// 删除工作量-HIS收费项目
export function delHis(id) {
  return request({
    url: '/workload/his/' + id,
    method: 'delete'
  })
}

// 导出工作量-HIS收费项目
export function exportHis(query) {
  return request({
    url: '/workload/his/export',
    method: 'get',
    params: query
  })
}
