import Vue from 'vue'

import Cookies from 'js-cookie'

import Element from 'element-ui'
import './assets/styles/element-variables.scss'

import '@/assets/styles/index.scss' // global css
import '@/assets/styles/zjry.scss' // zjry css
import App from './App'
import store from './store'
import router from './router'
import permission from './directive'
import './assets/icons' // icon
import './permission' // permission control
import baseSettings from "@/utils/settingUrl"
import installUtil from "@/utils/installUtil";

// 注册插件
installUtil(Vue,store)

Vue.use(permission)


/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get('size') || 'mini' // set element-ui default size
})

Vue.config.productionTip = false
baseSettings().then(()=>{
  new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
  })
})
