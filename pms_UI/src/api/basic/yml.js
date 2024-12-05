import request from '@/utils/request'
// 查询cas配置参数
export function getCasConfig() {
  return request({
    url: '/sso/yml/cas',
    method: 'get'
  })
}
