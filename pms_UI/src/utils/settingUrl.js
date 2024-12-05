import Vue from 'vue'
import request from '@/utils/request'
import { getConfigKeyApi } from "@/api/system/config";

// 流程
let casloginUrl = ()=>{
    getConfigKeyApi("casloginUrl").then(res=>{
        Vue.prototype.casloginUrl = res;
    })
}
// 登出
let caslogoutUrl = ()=>{
    getConfigKeyApi("caslogoutUrl").then(res=>{
        Vue.prototype.caslogoutUrl = res;
    })
}
// 登入
let processUrl = ()=>{
    getConfigKeyApi("processUrl").then(res=>{
        Vue.prototype.processUrl = res;
    })
}

// 导航栏消息提醒地址
let sysMessageWSUrl = ()=>{
    getConfigKeyApi("sysMessageWSUrl").then(res=>{
        Vue.prototype.sysMessageWSUrl = res;
    })
}

let  baseSettings = ()=>{
    let baseDictArr = [casloginUrl(),caslogoutUrl(),processUrl(),sysMessageWSUrl()]
    return  Promise.all(baseDictArr)
}

export default baseSettings  