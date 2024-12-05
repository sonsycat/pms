import {
  getDicts,
  getUserInfo,
  getDeptInfo,
  getPostInfo
} from "@/api/system/dict/data";
export default {
  state: {
    baseDicts: {},
    // 基础字典数据
    baseDictObj: {
      "hr_is_change": "hrIsChange",
      "asset_aduit_state": "assetAduitState", //审核状态
      "QU123456": "quantityUnit", //数量单位
      "DE02.01.015.00": "nationality", //国籍
      "DE08.10.052.00": "organizationCode", //组织机构代码
      "company_type": "companyType", //企业类型
      "post_level": "postLevel", //岗位级别
    },
    // postInfoDicts: [], // 岗位字典
    userOptsInfo: [], //人员获取
    deptInfo: [], //科室
    supplierTypeInfo:[]// 供应商类别
  },
  mutations: {
    setMasterData(state, data) {
      state.baseDicts = data;
    },
    // 人员获取
    setUserData(state, data) {
      state.userOptsInfo = data;
    },
    // 科室
    setDeptData(state, data) {
      state.deptInfo = data;
    },
  },
  actions: {
    getDicts({
      dispatch,
      commit
    }) {
      return new Promise((resolve, reject) => {
        dispatch("getBaseDicts");
        dispatch("getUserInfo");
        dispatch("getDeptInfo");
      })
    },
    /**
     * 人员获取
     * @param {*}
     */
    getUserInfo({
      state,
      commit
    }) {
      return new Promise((resolve, reject) => {
        getUserInfo().then(response => {
          if (response.code === 200) {
            const data = response.data;
            commit("setUserData",
              data);
          } else {
            reject(err);
          }
        }).catch(err => {
          reject(err);
        });
      })
    },
    /**
     * 科室
     * @param {*}
     */
    getDeptInfo({
      state,
      commit
    }) {
      return new Promise((resolve, reject) => {
        getDeptInfo().then(response => {
          if (response.code === 200) {
            const data = response.data;
            commit("setDeptData",
              data);
          } else {
            reject(err);
          }
        }).catch(err => {
          reject(err);
        });
      })
    },
    /**
     * 获取基础字典数据
     *
     * @param state
     * @param commit
     * @returns {Promise<any>}
     */
    async getBaseDicts({
      state,
      commit
    }) {
      let res = await getDictsObj(state)
      return new Promise((resolve, reject) => {
        try {
          commit("setMasterData",
            res);
        } catch (error) {
          reject(error);
        }
      });
    }
  }
};
async function getDictsObj(state) {
  let dicObj = {}
  let object = state.baseDictObj
  for (const key in object) {
    if (object.hasOwnProperty(key)) {
      let res = await getDicts(key).then(response => {
        if (response.code === 200) {
          dicObj[object[key]] = response.data
        } else {

        }


      })
    }
  }
  return dicObj
}
