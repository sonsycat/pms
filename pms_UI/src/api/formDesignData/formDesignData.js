import request from '@/utils/request'

// 保存表单设计信息
export function saveData(formDesignId, formData) {
  return request({
    url: '/formDesign/formDesign/saveData',
    method: 'post',
    data: {
      fdId: formDesignId,
      formData: JSON.stringify(formData)
    }
  })
}
