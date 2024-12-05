import {parseTime} from "./zjry"
import store from '../store'
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

//获取当前年份
export function getNowYear() {
  var nowDate = new Date();
  var year = nowDate.getFullYear();
  year=year + ''
  return year;
}

/**
 * 计算两个日期之间的天数
 * @param dateString1  开始日期 yyyy-MM-dd
 * @param dateString2  结束日期 yyyy-MM-dd
 * @returns {number} 如果日期相同 返回一天 开始日期大于结束日期，返回0
 */
export function getDaysBetween(dateString1, dateString2) {
  var startDate = Date.parse(dateString1);
  var endDate = Date.parse(dateString2);
  if (startDate > endDate) {
    return 0;
  }
  if (startDate == endDate) {
    return 1;
  }
  var days = (endDate - startDate) / (1 * 24 * 60 * 60 * 1000);
  return days;
}

/**
 * 计算两个日期之间的月数
 * @param dateString1  开始日期 yyyy-MM-dd
 * @param dateString2  结束日期 yyyy-MM-dd
 */
export function getMonthBetween(date1, date2) {
  //用-分成数组
  date1 = parseTime(date1,"{y}-{m}-{d}").split("-");
  date2 = parseTime(date2,"{y}-{m}-{d}").split("-");
  //获取年,月数
  var year1 = parseInt(date1[0]),
      month1 = parseInt(date1[1]),
      year2 = parseInt(date2[0]),
      month2 = parseInt(date2[1]),
      //通过年,月差计算月份差
      months = (year2 - year1) * 12 + (month2 - month1) + 1;
  return months;
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
  var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();;
  return y + "-" + m + "-" + d;
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

//计算日期是否在某区间内，返回布尔值
export function betwn(beginDateStr, endDateStr, nowDate) {
  var curDate = new Date(nowDate),
    beginDate = new Date(beginDateStr),
    endDate = new Date(endDateStr);
  if (curDate >= beginDate && endDate >= curDate) {
    return true;
  }
  return false;
}


// 时间的选择（填表日期）  在当前期间的默认成当天，不在当前期间的默认成账期的最后一天
export function getAccountDate(){
  let accountStartDate = store.state.user.accountInfo.startDate;
  let accountEndDate = store.state.user.accountInfo.endDate;

  var curDate = new Date().getTime(),
  beginDate = new Date(accountStartDate).getTime(),
  endDate = new Date(accountEndDate).getTime();
  console.log(curDate,"curDate",beginDate,"beginDate",endDate,"endDate")
  if (curDate >= beginDate && endDate >= curDate) {
    return getYear()
  }else{
    return accountEndDate
  }
}
// 获取当前月份第一天
export function getCurrentMonthFirst(){
  var date = new Date();
  date.setDate(1);
  var month = parseInt(date.getMonth()+1);
  var day = date.getDate();
  if (month < 10) {
      month = '0' + month
  }
  if (day < 10) {
      day = '0' + day
  }
  return date.getFullYear() + '-' + month + '-' + day;
}
// 获取当前月份最后一天
export function getCurrentMonthLast(){
  var date=new Date();
  var currentMonth=date.getMonth();
  var nextMonth=++currentMonth;
  var nextMonthFirstDay=new Date(date.getFullYear(),nextMonth,1);
  var oneDay=1000*60*60*24;
  var lastTime = new Date(nextMonthFirstDay-oneDay);
  var month = parseInt(lastTime.getMonth()+1);
  var day = lastTime.getDate();
  if (month < 10) {
      month = '0' + month
  }
  if (day < 10) {
      day = '0' + day
  }
  return date.getFullYear() + '-' + month + '-' + day;
}
