import request from '@/utils/request'

export function nopwdLogin(userName) {
  return request({
    url: '/nopwdLogin',
    method: 'get',
    params: {
      userName: userName
    }
  })
}

export function nopwdLoginByCode(loginCode) {
  return request({
    url: '/nopwdLoginByCode',
    method: 'get',
    params: {
      loginCode: loginCode
    }
  })
}
