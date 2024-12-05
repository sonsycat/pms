

import { parseTime, resetForm, addDateRange, selectDictLabel,selectDictIndexLabel, selectSupplierTypeLabel, selectDictLabels, download, handleTree,getFromIdCard,stateTextStyle, moneyFormat,moneyFormatDecimal } from "@/utils/zjry";
import {tableScrollLeft } from "@/utils/index"
import { getDicts,getPostInfo } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import Moment from 'moment'
// import defaultSettings from '@/settings'

import Pagination from "@/components/Pagination";
// 自定义表格工具组件
import RightToolbar from "@/components/RightToolbar";
//主题色弹出框
import ELThemeDialog from "@/components/dialog";
// 字典标签组件
import DictTag from '@/components/DictTag';
import staffSearch from "@/views/components/staffSearch";
// import supplierSelect from "@/views/components/supplierSelect"
// import barCode from "@/views/components/barCode"
// 头部标签插件
import VueMeta from 'vue-meta';
//全局引入FormMaking
import FormMaking from 'form-making';
import {
	GenerateForm,
	MakingForm
} from 'form-making';
import 'form-making/dist/FormMaking.css';
//全局引入echarts
import echarts from 'echarts';

export default (Vue, store) => {
  Vue.prototype.parseTime = parseTime
  Vue.prototype.resetForm = resetForm
  Vue.prototype.addDateRange = addDateRange
  Vue.prototype.selectDictLabel = selectDictLabel
  Vue.prototype.selectDictIndexLabel = selectDictIndexLabel
  Vue.prototype.selectSupplierTypeLabel = selectSupplierTypeLabel
  Vue.prototype.selectDictLabels = selectDictLabels
  Vue.prototype.download = download
  Vue.prototype.handleTree = handleTree
  Vue.prototype.getFromIdCard = getFromIdCard
  Vue.prototype.stateTextStyle = stateTextStyle
  Vue.prototype.moneyFormat = moneyFormat
  Vue.prototype.moneyFormatDecimal = moneyFormatDecimal
  Vue.prototype.tableScrollLeft = tableScrollLeft
  

  Vue.prototype.getDicts = getDicts
  Vue.prototype.getPostInfo = getPostInfo
  Vue.prototype.getConfigKey = getConfigKey
  // Vue.prototype.processUrl = defaultSettings.processUrl
  Vue.prototype.moment = Moment
  Vue.prototype.$echarts = echarts;
  // 全局组件挂载
  Vue.component('Pagination', Pagination)
  Vue.component('RightToolbar', RightToolbar)
  Vue.component(ELThemeDialog.name, ELThemeDialog)
  Vue.component('DictTag', DictTag)
  Vue.component('staffSearch', staffSearch)
  // Vue.component('supplierSelect', supplierSelect)


  Vue.use(VueMeta)
  
  Vue.use(GenerateForm)
  Vue.use(MakingForm)
  Vue.use(FormMaking)
  // 全局方法挂载



  Vue.prototype.msgSuccess = function (msg) {
    this.$message({ showClose: true, message: msg, type: "success" });
  }

  Vue.prototype.msgInfo = function (msg) {
    this.$message.info(msg);
  }

  Vue.prototype.msgError = function (msg) {
    let loadingInstance = this.$loading({
      lock: true,
      text: '',
      spinner: true,
      background: 'rgba(0, 0, 0, 0.7)',
    });
    this.$message({
      duration: 0,
      showClose: true,
      message: msg,
      type: "warning",
      onClose: function () {
        loadingInstance.close();
      }
    });
  }

  Vue.prototype.saveLoading = function (txt) {
    let text = txt||"保存中..."
    const saveLoading = this.$loading({
      lock: true,
      text,
      spinner: "el-icon-loading",
      background: "rgba(0, 0, 0, 0.7)",
    });
    return saveLoading;
  }
  Vue.prototype.dataLoading = function (txt) {
    let text = txt||"加载中..."
    const saveLoading = this.$loading({
      lock: true,
      text,
      spinner: "el-icon-loading",
      background: "rgba(0, 0, 0, 0.7)",
      customClass:"zIndex9999"
    });
    return saveLoading;
  }


};
