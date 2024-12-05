import variables from '@/assets/styles/element-variables.scss'
import defaultSettings from '@/settings'
import { getConfigKey } from "@/api/system/config";
const {
  sideTheme,
  showSettings,
  topNav,
  tagsView,
  fixedHeader,
  sidebarLogo,
  dynamicTitle,
  sysTabeleBorder,
  pageSize,
  tableHeight,
  popPageSize
} = defaultSettings

const storageSetting = JSON.parse(localStorage.getItem('layout-setting')) || ''
const state = {
  title: '',
  theme: storageSetting.theme || variables.theme,
  sideTheme: storageSetting.sideTheme || sideTheme,
  showSettings: showSettings,
  topNav: storageSetting.topNav === undefined ? topNav : storageSetting.topNav,
  tagsView: storageSetting.tagsView === undefined ? tagsView : storageSetting.tagsView,
  fixedHeader: storageSetting.fixedHeader === undefined ? fixedHeader : storageSetting.fixedHeader,
  sidebarLogo: storageSetting.sidebarLogo === undefined ? sidebarLogo : storageSetting.sidebarLogo,
  dynamicTitle: storageSetting.dynamicTitle === undefined ? dynamicTitle : storageSetting.dynamicTitle,
  sysTabeleBorder: storageSetting.sysTabeleBorder === undefined ? sysTabeleBorder : storageSetting.sysTabeleBorder,
  pageSize: pageSize,
  popPageSize:popPageSize,
  tableHeight: tableHeight,
  // 草稿
  waitState: 0,
  // 审核中
  conductState: 1,
  // 审核通过
  successState: 2,
  // 驳回 (没有驳回 会 改成草稿)
  failState: 3, 
  // 终止
  stopState: 4,
  // 已作废
  failDelState: 1,
  // 系统参数  是否启用供应商
  issupply:1,
  // 系统参数  是否启用波次管理  0启用，1不启
  iswell:1,
  /**中心库业务类型*/

  // 正常入库
  zCInCenter:"1",
  //专购入库 2
  zGInCenter:"2",
  //调拨入库
  dBInCenter:"3",
  //代销入库
  dXInCenter:"4",
  //退货入库
  tHInCenter:"5",
  // 盘盈入库
  pYInCenter:"6",

  /**物资退货*/
  // 一般退货
  zCReturnCen:"1",
  //专购品退货
  zGReturnCen:"2",
  //代销品退货
  dXReturnCen:"3",

  /**物资出库*/
  // 正常出库
  zCOutCen:"1",
  // 专购出库
  zGOutCen:"2",
  // 报废出库
  bFOutCen:"3",
  // 调拨出库
  dBOutCen:"4",
  // 领用出库
  lYOutCen:"5",
  //盘亏出库
  pKOutCen:"6",
  // 代销出库
  dXOutCen:"7",

  
  /**物资退库*/
  // 一般退库
  ybWidthCen:"1",
  // 专购品退库
  zGWidthCen:"2",
  //代销品退库
  dXWidthCen:"3",

  /**二级库业务类型*/
  // 一般
  zCInSec:"1",
  // 代销
  dXInSec:"2",
  // 盘盈
  pYInSec:"3",
  // 物资退货
  // 二级库退货
  zCReturnSec:"1",
  // 二级库代销退货
  dXReturnSec:"2",
  // 物资消耗
  // 消耗出库
  xHConSec:"1",
  // 损坏出库
  sHConSec:"2",
  // 加工出库
  jGConSec:"3",
  //盘亏出库
  pKConSec:"4",
  // 二级库退库
  zCWidthSec:"1",
  // 二级库代销退库
  dXWidthSec:"2",
  // 加工回库
  jGWidthSec:"3",
}
const mutations = {
  CHANGE_SETTING: (state, {
    key,
    value
  }) => {
    if (state.hasOwnProperty(key)) {
      state[key] = value
    }
  },
  SETTING_ISSUPPLY: (state, dicValue) => {
      state["issupply"] = dicValue
  },
  SETTING_ISWELL: (state, dicValue) => {
      state["iswell"] = dicValue
  },
}

const actions = {
  // 修改布局设置
  changeSetting({
    commit
  }, data) {
    commit('CHANGE_SETTING', data)
  },
  // 设置网页标题
  setTitle({
    commit
  }, title) {
    state.title = title
  },
  // 获取是否供应商
  getIssupply({
    commit
  }) {
    return new Promise((resolve, reject) => {
      getConfigKey("ISSUPPLY").then(response => {
        if (response.code === 200) {
          const data = response.msg;
          commit("SETTING_ISSUPPLY",
            data);
        } else {
          reject(response.msg);
        }
      }).catch(err => {
        reject(err);
      });
    })
  },
   // 是否启用波次管理
   getIswell({
    commit
  }) {
    return new Promise((resolve, reject) => {
      getConfigKey("ISWELL").then(response => {
        if (response.code === 200) {
          const data = response.msg;
          commit("SETTING_ISWELL",
            data);
        } else {
          reject(response.msg);
        }
      }).catch(err => {
        reject(err);
      });
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
