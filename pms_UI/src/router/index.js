import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import ParentView from '@/components/ParentView';
import InnerLink from '@/layout/components/InnerLink'

/**
 * Note: 路由配置项
 *
 hidden: true                       // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                  // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                  // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                  // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect             // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'               // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * query: '{"id": 1, "name": "ry"}' // 访问路由的默认传递参数
 * meta : {
    noCache: true                   // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
    title: 'title'                  // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'                // 设置该路由的图标，对应路径src/assets/icons/svg
    breadcrumb: false               // 如果设置为false，则不会在breadcrumb面包屑中显示
    activeMenu: '/system/user'      // 当路由设置了该属性，则会高亮相对应的侧边栏。
  }
 */

// 公共路由
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: (resolve) => require(['@/views/redirect'], resolve)
      }
    ]
  },
  {
    path: '/login',
    component: (resolve) => require(['@/views/login'], resolve),
    hidden: true
  },
  {
    path: '/404',
    component: (resolve) => require(['@/views/error/404'], resolve),
    hidden: true
  },
  {
    path: '/401',
    component: (resolve) => require(['@/views/error/401'], resolve),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: (resolve) => require(['@/views/index'], resolve),
        name: '首页',
        meta: { title: '首页', icon: 'dashboard', noCache: true, affix: true }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: (resolve) => require(['@/views/system/user/profile/index'], resolve),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user' }
      }
    ]
  },
  {
    path: '/auth',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'role/:userId(\\d+)',
        component: (resolve) => require(['@/views/system/user/authRole'], resolve),
        name: 'AuthRole',
        meta: { title: '分配角色', activeMenu: '/system/user'}
      }
    ]
  },
  {
    path: '/auth',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: (resolve) => require(['@/views/system/role/authUser'], resolve),
        name: 'AuthUser',
        meta: { title: '分配用户', activeMenu: '/system/role'}
      }
    ]
  },
  {
    path: '/dict',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'type/data/:dictId(\\d+)',
        component: (resolve) => require(['@/views/system/dict/data'], resolve),
        name: 'Data',
        meta: { title: '字典数据', icon: '', activeMenu: '/system/dict' }
      }
    ]
  },
  // {
  //   path: '/job',
  //   component: Layout,
  //   hidden: true,
  //   children: [
  //     {
  //       // path: 'log',
  //       path: 'index',
  //       component: (resolve) => require(['@/views/monitor/job/log'], resolve),
  //       name: 'JobLog',
  //       meta: { title: '调度日志', activeMenu: '/monitor/job' }
  //     }
  //   ]
  // },
  {
    path: '/gen',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'edit/:tableId(\\d+)',
        component: (resolve) => require(['@/views/tool/gen/editTable'], resolve),
        name: 'GenEdit',
        meta: { title: '修改生成配置', activeMenu: '/tool/gen' }
      }
    ]
  },
  {
    path: '/subsidy',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'formDeploy',
        component: (resolve) => require(['@/views/subsidy/formDeploy/index'], resolve),
        name: 'formDeploy',
        meta: {title: '表单配置'}
      }
    ]
  },
  {
    path: '/subsidy',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'formMaking',
        component: (resolve) => require(['@/views/subsidy/formDesign/formMaking/index'], resolve),
        name: 'formMaking',
        meta: {title: '表单设计'}
      }
    ]
  },
  {
    path: '/subsidy',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'formProcess',
        component: (resolve) => require(['@/views/subsidy/formProcess/index'], resolve),
        name: 'formProcess',
        meta: {title: '展示列表'}
      }
    ]
  },
  {
    path: '/system',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'message',
        component: (resolve) => require(['@/views/system/message/index'], resolve),
        name: 'messageList',
        meta: {title: '消息列表'}
      }
    ]
  },
  {
    path: '/message',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'showDetails',
        component: (resolve) => require(['@/views/system/message/showDetails'], resolve),
        name: 'showMessageDetails',
        meta: {title: '消息详情'}
      }
    ]
  },
  {
    path: '/calculation',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'detail/:period(\.*)',
        component: (resolve) => require(['@/views/bonus/calculation/detail'], resolve),
        name: 'bonusCalculationDetail',
        meta: { title: '奖金生成', activeMenu: '/bonus/calculation' }
      }
    ]
  },
  {
    path: '/calculationNew',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'detail/:period(\.*)',
        component: (resolve) => require(['@/views/bonus/calculationNew/detail'], resolve),
        name: 'bonusCalculationNewDetail',
        meta: { title: '奖金生成', activeMenu: '/bonus/calculationNew' }
      }
    ]
  },
  {
    path: '/deptBonus',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index/:deptType(\.*)/:actDate',
        component: (resolve) => require(['@/views/bonus/deptBonus/index'], resolve),
        name: 'bonusDeptBonusIndex',
        meta: { title: '科室奖金详情', activeMenu: '/bonus/deptBonus' }
      }
    ]
  },
  {
    path: '/deptBonusNew',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index/:deptType(\.*)/:actDate',
        component: (resolve) => require(['@/views/bonus/deptBonusNew/index'], resolve),
        name: 'bonusDeptBonusNewIndex',
        meta: { title: '科室奖金详情', activeMenu: '/bonus/deptBonusNew' }
      }
    ]
  },
  {
    path: '/personBonus',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index/:deptCode(\.*)/:actDate',
        component: (resolve) => require(['@/views/bonus/personBonus/index'], resolve),
        name: 'bonusPersonBonusIndex',
        meta: { title: '人员奖金详情', activeMenu: '/bonus/personBonus' }
      }
    ]
  },
  {
    path: '/personBonusNew',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'staffview/:deptCode(\.*)/:actDate',
        component: (resolve) => require(['@/views/bonus/personBonus/staffview'], resolve),
        name: 'bonusPersonBonusNewIndex',
        meta: { title: '人员奖金详情', activeMenu: '/bonus/personBonusNew' }
      }
    ]
  },
  {
    path: '/personBonus',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'view/:deptCode(\.*)/:actDate',
        component: (resolve) => require(['@/views/bonus/personBonus/view'], resolve),
        name: 'bonusPersonBonusIndex',
        meta: { title: '人员奖金详情', activeMenu: '/bonus/personBonus' }
      }
    ]
  }
]

//1 hash 模式下，仅 hash 符号之前的内容会被包含在请求中，如 http://www.abc.com，因此对于后端来说，即使没有做到对路由的全覆盖，也不会返回 404 错误。
//
// 2 history 模式下，前端的 URL 必须和实际向后端发起请求的 URL 一致，如 http://www.abc.com/book/id 如果后端缺少对 /book/id 的路由处理，将返回 404 错误。
// Vue-Router 官网里如此描述：“不过这种模式要玩好，还需要后台配置支持……所以呢，你要在服务端增加一个覆盖所有情况的候选资源：如果 URL 匹配不到任何静态资源，
// 则应该返回同一个 index.html 页面，这个页面就是你 app 依赖的页面。”
//
// 3 对于一般的 Vue + Vue-Router + Webpack + XXX 形式的 Web 开发场景，用 history 模式即可，只需在后端（Apache 或 Nginx）进行简单的路由配置，
// 同时搭配前端路由的 404 页面支持。

export default new Router({
  mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
