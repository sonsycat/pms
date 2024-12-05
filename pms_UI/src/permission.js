import Vue from 'vue'
import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken,setToken } from '@/utils/auth'
import { nopwdLogin, nopwdLoginByCode } from '@/utils/nopwdLogin'
import { getConfigKeyApi } from "@/api/system/config";

NProgress.configure({ showSpinner: false })

const whiteList = ['/login', '/auth-redirect', '/bind', '/register']
// const casloginUrl = Vue.prototype.casloginUrl

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    to.meta.title && store.dispatch('settings/setTitle', to.meta.title)
    /* has token*/
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      if (store.getters.roles.length === 0) {
        // 判断当前用户是否已拉取完user_info信息
        store.dispatch('GetAccountInfo').then(() => {
          store.dispatch('GetInfo').then(() => {
            store.dispatch('GenerateRoutes').then(accessRoutes => {
              // 根据roles权限生成可访问的路由表
              router.addRoutes(accessRoutes) // 动态添加可访问路由表
              next({...to, replace: true}) // hack方法 确保addRoutes已完成
            })
          }).catch(err => {
            store.dispatch('LogOut').then(() => {
              Message.error(err)
              next({path: '/'})
            })
          })
        })
      } else {
        next()
      }
    }
  } else {
    // 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    }else if(to.path === '/ssologin'){
      console.log(to.path);
      getConfigKeyApi("casloginUrl").then(res=>{
        Vue.prototype.casloginUrl = res;
        window.location.href = res
      })
    }else if (to.path === '/nopwdLogin') {
      let userName = to.query.userName;
      let redirectUrl = to.query.redirectUrl
      nopwdLogin(userName).then(res => {
        console.log('res.token----' + res.token)
        setToken(res.token)
        if(redirectUrl){
          next({ path: redirectUrl})
        }else{
          next({ path: "/"})
        }
        NProgress.done()
      })
    } else if (to.path === '/nopwdLoginByCode') {
      let loginCode = to.query.loginCode;
      let redirectUrl = to.query.redirectUrl
      console.log('loginCode---'+loginCode)
      nopwdLoginByCode(loginCode).then(res=>{
        console.log('res.token---'+res.token)
        setToken(res.token)
        if(redirectUrl){
          next({ path: redirectUrl})
        }else{
          next({ path: "/"})
        }
        NProgress.done()
      })
    }
    else {
      next(`/login?redirect=${to.fullPath}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
