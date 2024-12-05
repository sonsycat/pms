import request from '@/utils/request'

const authLicenseSysCode = "BASE-FRAME";
const authLicenseState = false;

export function authLicenseVerify() {
  return request({
    url: '/auth/license/verify',
    method: 'post',
    params: {
      sysCode: authLicenseSysCode
    }
  })
}

export function active(code) {
  return request({
    url: '/auth/license/active',
    method: 'post',
    params: {
      sysCode: authLicenseSysCode,
      code: code
    }
  })
}
