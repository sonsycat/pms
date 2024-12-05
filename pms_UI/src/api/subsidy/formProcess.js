import request from '@/utils/request'

//方案配置列表
export function deployList(query) {
  return request({
    url: '/pmsFormDesignField/getFormConfig',
    method: 'get',
    params: query
  })
}

//方案配置保存
export function saveDeployList(data) {
  return request({
    url: '/pmsFormDesignField/save',
    method: 'post',
    data: data
  })
}


// 查询自定义表单列表
export function testForm(data,formDesignId) {
  return request({
    url: '/pmsFormProcess/list?formDesignId=' + formDesignId,
    method: 'post',
    data: data
  })
}

//查询自定义表单详情
export function getFormDetails(formDesignId,id) {
  return request({
    url: '/pmsFormProcess/getInfo/' + formDesignId + "/" + id,
    method: 'get'
  })
}

//自定义表单保存
export function addSaveForm(data,formDesignId) {
  return request({
    url: '/pmsFormProcess/save?formDesignId=' + formDesignId,
    method: 'post',
    data: data
  })
}

//删除自定义表单
export function delForm(formDesignId,id) {
  return request({
    url: '/pmsFormProcess/delete/'+ formDesignId + "/" + id,
    method: 'delete'
  })
}


export function saveDesign(formDesignId, formJson, formHtml) {
  return request({
    url: '/formDesign/pmsFormDesign/saveDesign?fdId='+formDesignId,
    method: 'post',
    data: {
      formJson: formJson,
      formHtml: formHtml
    }
  })
}

export function getForm(query) {
  return request({
    url: '/formDesign/pmsFormDesign/getFormDesignFields',
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