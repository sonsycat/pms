/**
 * 通用js方法封装处理
 * Copyright (c) 2019 ruoyi
 */

const baseURL = process.env.VUE_APP_BASE_API

// 日期格式化
export function parseTime(time, pattern) {
  if (arguments.length === 0 || !time) {
    return null
  }
  const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
      time = parseInt(time)
    } else if (typeof time === 'string') {
      time = time.replace(new RegExp(/-/gm), '/');
    }
    if ((typeof time === 'number') && (time.toString().length === 10)) {
      time = time * 1000
    }
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key]
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') {
      return ['日', '一', '二', '三', '四', '五', '六'][value]
    }
    if (result.length > 0 && value < 10) {
      value = '0' + value
    }
    return value || 0
  })
  return time_str
}

// 表单重置
export function resetForm(refName) {
  if (this.$refs[refName]) {
    this.$refs[refName].resetFields();
  }
}

// 添加日期范围
export function addDateRange(params, dateRange, propName) {
  var search = params;
  search.params = {};
  if (null != dateRange && '' != dateRange) {
    if (typeof (propName) === "undefined") {
      search.params["beginTime"] = dateRange[0];
      search.params["endTime"] = dateRange[1];
    } else {
      search.params["begin" + propName] = dateRange[0];
      search.params["end" + propName] = dateRange[1];
    }
  }
  return search;
}

// 回显数据字典
export function selectDictLabel(datas, value) {
  var actions = [];
  if(datas && datas.length>0){
    Object.keys(datas).some((key) => {
      if (datas[key].dictValue == ('' + value)) {
        actions.push(datas[key].dictLabel);
        return true;
      }
    })
  }
  return actions.join('');
}

// 回显指标数据字典
export function selectDictIndexLabel(datas, value) {
  var actions = [];
  if(datas && datas.length>0){
    Object.keys(datas).some((key) => {
      if (datas[key].id == ('' + value)) {
        actions.push(datas[key].evaCont);
        return true;
      }
    })
  }
  return actions.join('');
}

// 回显供应商类别
export function selectSupplierTypeLabel(datas, value) {
  var actions = [];
  if(datas && datas.length>0){
  Object.keys(datas).some((key) => {
      if (datas[key].typeCode == value) {
        actions.push(datas[key].typeName);
        return true;
      }
    })
  }
  return actions.join('');
}

// 回显数据字典（字符串数组）
export function selectDictLabels(datas, value, separator) {
  var actions = [];
  var currentSeparator = undefined === separator ? "," : separator;
  var temp = value.split(currentSeparator);
  Object.keys(value.split(currentSeparator)).some((val) => {
    Object.keys(datas).some((key) => {
      if (datas[key].dictValue == ('' + temp[val])) {
        actions.push(datas[key].dictLabel + currentSeparator);
      }
    })
  })
  return actions.join('').substring(0, actions.join('').length - 1);
}

// 通用下载方法
export function download(fileName) {
  window.location.href = baseURL + "/common/download?fileName=" + encodeURI(fileName) + "&delete=" + true;
}

// 字符串格式化(%s )
export function sprintf(str) {
  var args = arguments,
    flag = true,
    i = 1;
  str = str.replace(/%s/g, function () {
    var arg = args[i++];
    if (typeof arg === 'undefined') {
      flag = false;
      return '';
    }
    return arg;
  });
  return flag ? str : '';
}

// 转换字符串，undefined,null等转化为""
export function praseStrEmpty(str) {
  if (!str || str == "undefined" || str == "null") {
    return "";
  }
  return str;
}

/**
 * 构造树型结构数据
 * @param {*} data 数据源
 * @param {*} id id字段 默认 'id'
 * @param {*} parentId 父节点字段 默认 'parentId'
 * @param {*} children 孩子节点字段 默认 'children'
 */
export function handleTree(data, id, parentId, children) {
  let config = {
    id: id || 'id',
    parentId: parentId || 'parentId',
    childrenList: children || 'children'
  };

  var childrenListMap = {};
  var nodeIds = {};
  var tree = [];

  for (let d of data) {
    let parentId = d[config.parentId];
    if (childrenListMap[parentId] == null) {
      childrenListMap[parentId] = [];
    }
    nodeIds[d[config.id]] = d;
    childrenListMap[parentId].push(d);
  }

  for (let d of data) {
    let parentId = d[config.parentId];
    if (nodeIds[parentId] == null) {
      tree.push(d);
    }
  }

  for (let t of tree) {
    adaptToChildrenList(t);
  }

  function adaptToChildrenList(o) {
    if (childrenListMap[o[config.id]] !== null) {
      o[config.childrenList] = childrenListMap[o[config.id]];
    }
    if (o[config.childrenList]) {
      for (let c of o[config.childrenList]) {
        adaptToChildrenList(c);
      }
    }
  }
  return tree;
}

/**
 * 根据身份号获取信息
 * @param {*} idCard  身份证号
 */
export function getFromIdCard(idCard) {
  let idNoInfo = {
    sex: "",
    birthday: "",
    age: ""
  }
  //获取性别
  if (parseInt(idCard.substr(16, 1)) % 2 == 1) {
    //男
    idNoInfo["sex"] = "男";
  } else {
    //女
    idNoInfo["sex"] = "女";
  }
  var birthday = "";
  if (idCard != null && idCard != "") {
    if (idCard.length == 15) {
      birthday = "19" + idCard.substr(6, 6);
    } else if (idCard.length == 18) {
      birthday = idCard.substr(6, 8);
    }

    birthday = birthday.replace(/(.{4})(.{2})/, "$1-$2-");
  }
  idNoInfo["birthday"] = birthday;
  //获取年龄
  var myDate = new Date();
  var month = myDate.getMonth() + 1;
  var day = myDate.getDate();
  var age = myDate.getFullYear() - idCard.substring(6, 10) - 1;
  if (idCard.substring(10, 12) < month || idCard.substring(10, 12) == month && idCard.substring(12, 14) <= day) {
    age++;
  }
  idNoInfo["age"] = age;

  return idNoInfo
}

/**
 * 获取当前单位名称
 * @param {*} orgCode  当前用户机构
 * @param {*} data  所有机构数据
 */
export function getUserOrgCodeName(orgCode, data) {
  let userCode = orgCode;
  if (data && data.length) {
    for (let index = 0; index < data.length; index++) {
      let el = data[index];
      if (el.id != userCode) {
        if (el.hasChildren) {
          return getUserOrgCodeName(userCode, el.children);
        } else {
          return "";
        }
      } else {
        return el.label;
      }
    }
  } else {
    return "";
  }
}

/**
 * 金额格式化
 * @param {*} strData  要格式化的数字
 * @param {*}   n 保留几位小数
 */
export function moneyFormat(strData, n = 2) {
  let flag = false;
  console.log(strData)
  if (strData < 0) {
    strData = 0 - strData;
    flag = true;
  }
	var CurrencyAndAmountRegExp = /^(\d{1,18})|(\d{1,18}\.)|(\d{1,17}\.\d{0,1})|(\d{1,16}\.\d{0,2})|(\.\d{1,2})$/;
	var _result = CurrencyAndAmountRegExp.test(strData);
	if(_result == false){
		return strData;
	}
	// 一般来说最多就6位吧，当然如果有特殊需求可自行更改(｀・∀・´)
	n = n > 0 && n <= 6 ? n : 2;
	var formatData = parseFloat((strData + '').replace(/[^\d\.-]/g, '')).toFixed(n) + '';
	var l = formatData.split('.')[0].split('').reverse();
	var r = formatData.split('.')[1];
	var t = '';
	for(let i = 0; i < l.length; i ++ ) {
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? ',' : '');
	}
	if (flag) {
    return '-' + t.split('').reverse().join('') + '.' + r;
  } else {
    return t.split('').reverse().join('') + '.' + r;
  }
}
// 审核状态 文字颜色
export function stateTextStyle(state){
  let stateNum = this.$store.state.settings;
   // 审核中
  if(state == stateNum.conductState){
    return "text-warning"
  // 审核通过
  }else if (state == stateNum.successState){
     return "text-success"
  // 终止
  }else if (state == stateNum.stopState){
  // 审核通过
     return "text-danger"
  }
}

/**
 * 金额处理:保留几位小数，不四舍五入(关于金额数值的处理用这个方法,以防金额计算出错)
 * @param num   金额
 * @param decimal   保留位数
 * @returns {string}
 */
export function moneyFormatDecimal(num, decimal){
  num = num.toString()
  let index = num.indexOf('.')
  if (index !== -1) {
    num = num.substring(0, decimal + index + 1)
  } else {
    num = num.substring(0)
  }
  return parseFloat(num).toFixed(decimal)
}
