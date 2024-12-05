import {
  login,
  logout,
  getAccountInfo
} from '@/api/login'
import { getInfo } from "@/api/basic/user";
import {
  getToken,
  setToken,
  removeToken
} from '@/utils/auth'

const defaultSettings = require('./settings.js')

const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    permissions: [],
    userInfo: {},
    accountInfo: {},
    accountSetList:{}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_USERINFO: (state, userInfo) => {
      state.userInfo = userInfo
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    },
    SET_ACCOUNTINFO: (state, accountInfo) => {
      state.accountInfo = accountInfo
    },
    SET_ACCOUNTLIST: (state, data) => {
      state.accountSetList = data
    }
  },

  actions: {
    // 登录
    Login({
      commit
    }, userInfo) {
      const username = userInfo.username.trim()
      const password = userInfo.password
      const code = userInfo.code
      const uuid = userInfo.uuid
      return new Promise((resolve, reject) => {
        login(username, password, code, uuid).then(res => {
          setToken(res.token)
          commit('SET_TOKEN', res.token)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({
      commit,
      state
    }) {
      return new Promise((resolve, reject) => {
        getInfo().then(res => {
          console.log('获取用户信息' );
          const user = res.basicdataUser
          const avatar = user.avatar == "" ? require("@/assets/images/profile.jpg") : process.env.VUE_APP_BASE_API + user.avatar;
          if (res.roles && res.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', res.roles)
            commit('SET_PERMISSIONS', res.permissions)
          } else {
            commit('SET_ROLES', ['ROLE_DEFAULT'])
          }
          commit('SET_NAME', user.userName)
          commit('SET_USERINFO', user)
          commit('SET_AVATAR', avatar)
          commit('SET_AVATAR', avatar)
          commit('SET_ACCOUNTLIST', user.accountSetList)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 用户账套信息
    GetAccountInfo({
      commit,
      state
    }) {
      return new Promise((resolve, reject) => {
        getAccountInfo().then(res => {
          commit('SET_ACCOUNTINFO', res)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 退出系统
    LogOut({
      commit,
      state
    }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_PERMISSIONS', [])
          removeToken()
          resolve()
          //location.href=this.caslogoutUrl
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({
      commit
    }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
