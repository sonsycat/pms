import request from '@/utils/request'

// 查询自定义表单列表
export function listFormDesign(query) {
  return request({
    url: '/formDesign/pmsFormDesign/list',
    method: 'get',
    params: query
  })
}

//排序接口
export function listFormSort(query) {
  return request({
    url: '/formDesign/pmsFormDesign/selectForSortNo',
    method: 'get',
    params: query
  })
}

// 查询自定义表单详细
export function getFormDesign(id) {
  return request({
    url: '/formDesign/pmsFormDesign/' + id,
    method: 'get'
  })
}

//编辑表格列表数据
export function getFormTableList(data) {
  return request({
    url: '/pmsFormDesignField/getFormConfig',
    method: 'get',
    params:data
  })
}

//编辑控制主表每页条数
export function setPageNo(data) {
  return request({
    url: '/formDesign/pmsFormDesign/setPageNo',
    method: 'put',
    data:data
  })
}

//根据indexCode 查询质量考评明细
export function getListForPms(data) {
  return request({
    url: '/quality/detail/listForPms',
    method: 'get',
    params:data
  })
}

//根据单位查询部门
export function getDeptInfoForPms(data) {
  return request({
    url: '/basic/public/deptInfoForPms',
    method: 'get',
    params:data
  })
}
//编辑表格单行编辑
export function editTableItem(data) {
  return request({
    url: '/pmsFormDesignField/updateFormDesignField',
    method: 'post',
    data:data
  })
}

//待选项
export function selectDictType(data) {
  return request({
    url: '/pmsFormDesignField/selectDictType',
    method: 'get',
    params:data
  })
}

//质量考评指标
export function listForTemplate(data){
  return request({
    url: '/quality/index/listForTemplate',
    method: 'get',
    params:data
  })
}
//编辑保存新增
export function editAddFormDesign(data) {
  return request({
    url: '/pmsFormDesignField/updateAll',
    method: 'post',
    data: data
  })
}

// 新增自定义表单
export function addFormDesign(data) {
  return request({
    url: '/formDesign/pmsFormDesign',
    method: 'post',
    data: data
  })
}

// 修改自定义表单
export function updateFormDesign(data) {
  return request({
    url: '/formDesign/pmsFormDesign',
    method: 'put',
    data: data
  })
}

// 删除自定义表单
export function delFormDesign(id) {
  return request({
    url: '/formDesign/pmsFormDesign/' + id,
    method: 'delete'
  })
}

// 导出自定义表单
export function exportFormDesign(query) {
  return request({
    url: '/formDesign/pmsFormDesign/export',
    method: 'get',
    params: query
  })
}

// 保存表单设计信息
export function saveDesign(formDesignId, formJson, formHtml) {
  return request({
    url: '/formDesign/pmsFormDesign/saveDesign?fdId='+formDesignId,
    method: 'post',
    //contentType: "application/json",
    data: {
      //fdId: formDesignId,
      //formJson: JSON.stringify(formJson),
      formJson: formJson,
      formHtml: formHtml
    }
  })
}

// 校验表单编码唯一性
export function checkFormCodeUnique(formCode) {
  return request({
    url: '/formDesign/pmsFormDesign/checkFormCodeUnique?formCode=' + formCode,
    method: 'get'
  })
}
