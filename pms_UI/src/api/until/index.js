import request from '@/utils/request'
import store from '@/store'
import Vue from 'vue'
let processUrl = Vue.prototype.processUrl
// 查询节点配置按钮
function taskBtnConfigs(query) {
  return request({
    url: `${processUrl}/api/wf/queryTaskBtnConfigs`,
    method: 'post',
    params: query
  })
}

// 查询下级节点办理人
function nextAssignee(query) {
  return request({
    url: `${processUrl}/api/wf/queryNextAssignee`,
    method: 'post',
    params: query
  })
}

// 查询用户列表
function listUser(query) {
  return request({
    url: '/system/user/list',
    method: 'get',
    params: query
  })
}

// 查询用户列表
function queryEmpInfo(compCode,deptCode,postCode,searchName) {
  return request({
    url: '/basic/public/queryEmpInfo?compCode='+compCode+'&deptCode='+deptCode+'&postCode='+postCode+'&searchName='+searchName,
    method: 'get'
  })
}


//审核公共方法
/*
 * 1：proclnsIds
 * 2：btn2
 * 3：btn4
 * 4：btn3
 * 5：userOptions
 * 6：btn1
 *
 */
export function getEmpList(that, userId = "", proclnsIds, userOptions, btn1, btn2, btn3, btn4, btn5) {
  let name = store.state.user.userInfo.userId
  let code =  store.state.user.userInfo.dept.deptId
  console.log("abc"+proclnsIds +"_____" + btn1 + "_" + btn2 + "_" + btn3 + "_" + btn4 + "_" + btn5 + "_");
  if (proclnsIds == null || proclnsIds == "") {
    that.btn1 = true;
  } else {
    let data = {
      systemId: "PMS",
      userId: name,
      procInsId: proclnsIds,
    };
    // that.userOptions =
    taskBtnConfigs(data).then(response => {
      console.log("11111="+response.data.btnCode);
      if (response.data.btnCode == "2") {
        that.btn1 = true;
        that.btn2 = true;
        that.btn4 = true;
        nextAssignee(data).then(response => {
          console.log(response.data);
          if (response.data.type == "0") {

          } else if (response.data.type == "1") {
            that.btn5 = true;
            let pageNum= 1
            let pageSize= 30
            let compCode= code
            let postCode= ''
            let deptCode= ''
            let searchName=response.data.assigneeId
            queryEmpInfo(compCode,deptCode,postCode,searchName).then(response => {
              console.log(response);
              that.userOptions = response.rows;
            });
          } else if (response.data.type == "2") {
            that.btn5 = true;
            let pageNum= 1
            let pageSize= 30
            let compCode= code
            let postCode= ''
            let deptCode= ''
            let searchName=''
            queryEmpInfo(compCode,deptCode,postCode,searchName).then(response => {
              console.log(response);
              that.userOptions = response.rows;
            });
          } else if (response.data.type == "3") {
            that.btn5 = true;
            let pageNum= 1
            let pageSize= 30
            let compCode= code
            let postCode= ''
            let deptCode= ''
            let searchName=''
            queryEmpInfo(compCode,deptCode,postCode,searchName).then(response => {
              console.log(response);
              that.userOptions = response.rows;
            });
          }else if (response.data.type == "4"){
            that.btn5 = true;
            let pageNum= 1
            let pageSize= 30
            let compCode= code
            let postCode= ''
            let deptCode= ''
            let searchName=response.data.backUserId
            queryEmpInfo(compCode,deptCode,postCode,searchName).then(response => {
              console.log(response);
              that.userOptions = response.rows;
            });
          }
        });
      } else if (response.data.btnCode == "3") {
        that.btn3 = true;
        that.btn4 = true;
        nextAssignee(data).then(response => {
          console.log(response.data.type);
          if (response.data.type == "0") {

          } else if (response.data.type == "1") {
            that.btn5 = true;
            let pageNum= 1
            let pageSize= 30
            let compCode= code
            let postCode= ''
            let deptCode= ''
            let searchName=response.data.assigneeId
            queryEmpInfo(compCode,deptCode,postCode,searchName).then(response => {
              console.log(response);
              that.userOptions = response.rows;
            });
          } else if (response.data.type == "2") {
            that.btn5 = true;
            let pageNum= 1
            let pageSize= 30
            let compCode= code
            let postCode= ''
            let deptCode= ''
            let searchName=''
            queryEmpInfo(compCode,deptCode,postCode,searchName).then(response => {
              console.log(response);
              that.userOptions = response.rows;
            });
          } else if (response.data.type == "3") {
            that.btn5 = true;
              let pageNum= 1
              let pageSize= 30
            let compCode= code
            let postCode= ''
            let deptCode= ''
            let searchName=''
            queryEmpInfo(compCode,deptCode,postCode,searchName).then(response => {
              console.log(response);
              that.userOptions = response.rows;
            });
          }else if (response.data.type == "4"){
            that.btn5 = true;
            let pageNum= 1
            let pageSize= 30
            let compCode= code
            let postCode= ''
            let deptCode= ''
            let searchName=response.data.backUserId
            queryEmpInfo(compCode,deptCode,postCode,searchName).then(response => {
              console.log(response);
              that.userOptions = response.rows;
            });
          }
        });
      } else if (response.data.btnCode == "1") {
        that.btn1 = true;
      }
    }).catch(res => {
      console.log(res);
    });
  }
}

export function getLogin(query) {
  return request({
    url: '/emp/empinfo/getUserInfo',
    method: 'get',
    params: query
  })
}

//人员信息合同管理表查询
export function contract(query) {
  return request({
    url: '/contract/contract/list',
    method: 'get',
    params: query
  })
}

export function contracts(query) {
  return request({
    url: '/contract/contract/lists',
    method: 'get',
    params: query
  })
}

//资产字典
export function SUPPLIERsDictInfo(query) {
  return request({
    url: '/basic/public/SUPPLIERsDictInfo',
    method: 'get',
    params: query
  })
}

export function checkInfo(query) {
  return request({
    url: '/dimission/dimissioninfo/checkInfo',
    method: 'get',
    params: query
  })
}
export function checkInfo2(query) {
  return request({
    url: '/emp/empinfo/checkInfo',
    method: 'get',
    params: query
  })
}

//单位编码树
export function treeselect(query) {
  return request({
    url: '/system/dept/treeselect',
    method: 'get',
    params: query
  })
}

//科室编码：dept_id
export function treeRoom(query) {
  return request({
    url: '/system/room/list',
    method: 'get',
    params: query
  })
}

// 查询报存储列表
export function listFile(query) {
  return request({
    url: '/sys/file/list',
    method: 'get',
    params: query
  })
}

//获取当前月的天数
export function mGetDate() {
  var date = new Date();
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var d = new Date(year, month, 0);
  return d.getDate();
}

//获取上月月份
export function lastMonth(day) {
  var date = new Date();
  var year = date.getFullYear();
  var month = date.getMonth();
  var d = year + "-" + month + "-" + day;
  return d;
}

//获取本月月份
export function thisMonth(day) {
  var date = new Date();
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var d = year + "-" + month + "-" + day;
  return d;
}



/**
 * 计算两个日期之间的天数
 * @param dateString1  开始日期 yyyy-MM-dd
 * @param dateString2  结束日期 yyyy-MM-dd
 * @param flag  是否包含结束日期当天
 * @returns {number} 如果日期相同 返回一天 开始日期大于结束日期，返回0
 */
export function getDaysBetween(dateString1, dateString2, flag = false) {
  var startDate = Date.parse(dateString1);
  var endDate = Date.parse(dateString2);
  if (startDate > endDate) {
    return 0;
  }
  if (startDate == endDate) {
    return 1;
  }
  if (!flag) {
    var days = (endDate - startDate) / (1 * 24 * 60 * 60 * 1000);
  } else {
    var days = (endDate - startDate) / (1 * 24 * 60 * 60 * 1000) + 1;
  }
  return days;
}

/**
 * 计算两个日期 之间的小时
 * @param dateString1  开始日期 yyyy-MM-dd HH:mm:ss
 * @param dateString2  结束日期 yyyy-MM-dd HH:mm:ss
 * @param flag  是否包含结束日期当天
 * @returns {number} 如果日期相同 返回一天 开始日期大于结束日期，返回0
 */
export function getHoursBetween(dateString1, dateString2) {
  var startDate = new Date(dateString1).getTime();
  var endDate = new Date(dateString2).getTime();
  if (startDate > endDate) {
    return 0;
  }
  if (startDate == endDate) {
    return 24;
  }
  var hours = (endDate - startDate) / (1 * 24 * 60 * 60 * 1000) * 24;
  return hours;
}




//获取当前年月日
export function getYear() {
  var nowDate = new Date();
  var year = nowDate.getFullYear();
  var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
  var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
  var dateStr = year + "-" + month + "-" + day;
  return dateStr;
}

//获取当前年月
export function getYearMonth() {
  var nowDate = new Date();
  var year = nowDate.getFullYear();
  var month = nowDate.getMonth() + 1 < 10 ? nowDate.getMonth() + 1 : nowDate.getMonth() + 1;
  var dateStr = year + "-" + month;
  return dateStr;
}
export function getYearMonth2() {
  var nowDate = new Date();
  var year = nowDate.getFullYear();
  var month = nowDate.getMonth() + 1 < 10 ? nowDate.getMonth() + 1 : nowDate.getMonth() + 1;
  if (month < 10) {
    month = "0" + month;
  }
  var dateStr = year + "-" + month;
  return dateStr;
}


//获取当前年月日后一天
export function getTommory(AddDayCount) {
  var dd = new Date();
  dd.setDate(dd.getDate() + AddDayCount);
  var y = dd.getFullYear();
  var m = dd.getMonth() + 1 < 10 ? "0" + (dd.getMonth() + 1) : dd.getMonth() + 1;
  var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();
  return y + "-" + m + "-" + d;
}

/**
 * 获取当月的最后一天
 * @param year 年份
 * @param month 月份
 **/
export function getLastDay(year, month) {
  var new_year = year;
  var new_month = month++; //取下一个月的第一天，方便计算（最后一天不固定）
  if (month > 12) { //如果当前大于12月，则年份转到下一年
    new_month -= 12; //月份减
    new_year++; //年份增
  }
  // 取当年当月对应的下个月的前一天，即当前月的最后一天
  var last_date = new Date(new_year, new_month, 0).getDate();
  return last_date;
}


/**
 * 获取当前月份的第一天、和最后一天
 **/
export function getFirstLastDay() {
  var now = new Date();
  var strLink = "-";
  var year = now.getFullYear();
  var month = now.getMonth() + 1;
  var day = now.getDate();
  if (month >= 1 && month <= 9) {
    month = "0" + month;
  }
  if (day >= 1 && day <= 9) {
    day = "0" + day;
  }
  var firstDate = year + strLink + month + strLink + '01';
  var sysDate = year + strLink + month + strLink + day;
  var lastDay = getLastDay(year, month);
  var lastDate = year + strLink + month + strLink + lastDay;
  var returnArr = [firstDate, lastDate]; //以数组形式返回
  return returnArr;
}


//获取某年某月的天数
/**
 * 获取某年月的天数
 * @param year 年
 * @param month 月（0-11）
 * @returns {number} 天数
 */
export function getDaysOfMonth(year, month) {
  switch (month) {
    case "01":
    case "03":
    case "05":
    case "07":
    case "08":
    case "10":
    case "12":
      return 31;
    case "04":
    case "06":
    case "09":
    case "11":
      return 30;
    case "02":
      return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ? 29 : 28;
    default:
      return 0;
  }
}
