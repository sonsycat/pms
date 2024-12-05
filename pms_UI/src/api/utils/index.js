import request from '@/utils/request'

//方案配置列表
export function deployList(query) {
  return request({
    url: '/formDesignField/getFormConfig',
    method: 'get',
    params: query
  })
}

//方案配置保存
export function saveDeployList(data) {
  return request({
    url: '/formDesignField/save',
    method: 'post',
    data: data
  })
}


// 查询自定义表单列表
export function testForm(data,formDesignId) {
  return request({
    url: '/formProcess/list?formDesignId=' + formDesignId,
    method: 'post',
    data: data
  })
}

//查询自定义表单详情
export function getFormDetails(formDesignId,id) {
  return request({
    url: '/formProcess/getInfo/' + formDesignId + "/" + id,
    method: 'get'
  })
}

//自定义表单保存
export function addSaveForm(data,formDesignId) {
  return request({
    url: '/formProcess/save?formDesignId=' + formDesignId,
    method: 'post',
    data: data
  })
}

//删除自定义表单
export function delForm(formDesignId,id) {
  return request({
    url: '/formProcess/delete/'+ formDesignId + "/" + id,
    method: 'delete'
  })
}


export function saveDesign(formDesignId, formJson, formHtml) {
  return request({
    url: '/formDesign/formDesign/saveDesign?fdId='+formDesignId,
    method: 'post',
    data: {
      formJson: formJson,
      formHtml: formHtml
    }
  })
}

export function getForm(query) {
  return request({
    url: '/formDesign/formDesign/getFormDesignFields',
    method: 'get',
    params: query
  })
}

// 查询自定义表单详细
export function getFormDesign(id) {
  return request({
    url: '/formDesign/formDesign/' + id,
    method: 'get'
  })
}

//获取当前月的天数
export function mGetDate(){
     var date = new Date();
     var year = date.getFullYear();
     var month = date.getMonth()+1;
     var d = new Date(year, month, 0);
     return d.getDate();
}

//获取上月月份
export function lastMonth(day){
     var date = new Date();
     var year = date.getFullYear();
     var month = date.getMonth();
     var d = year + "-" + month + "-" +day;
     return d;
}

//获取本月月份
export function thisMonth(day){
     var date = new Date();
     var year = date.getFullYear();
     var month = date.getMonth() + 1;
     var d = year + "-" + month + "-" +day;
     return d;
}

/**
 * 计算两个日期之间的天数
 * @param dateString1  开始日期 yyyy-MM-dd
 * @param dateString2  结束日期 yyyy-MM-dd
 * @returns {number} 如果日期相同 返回一天 开始日期大于结束日期，返回0
 */
export function getDaysBetween(dateString1,dateString2){
    var startDate = Date.parse(dateString1);
    var endDate = Date.parse(dateString2);
    if (startDate>endDate){
        return 0;
    }
    if (startDate==endDate){
        return 1;
    }
    var days=(endDate - startDate)/(1*24*60*60*1000);
    return  days;
}

//获取当前年月日
export function getYear(){
	var nowDate = new Date();
	var year = nowDate.getFullYear();
	var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1):nowDate.getMonth() + 1;
	var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
	var dateStr = year + "-" + month + "-" + day;
	return dateStr;
}

//获取当前年月
export function getYearMonth(){
	var nowDate = new Date();
	var year = nowDate.getFullYear();
	var month = nowDate.getMonth() + 1 < 10 ? nowDate.getMonth() + 1:nowDate.getMonth() + 1;
	var dateStr = year + "-" + month;
	return dateStr;
}
export function getYearMonth2(){
	var nowDate = new Date();
	var year = nowDate.getFullYear();
	var month = nowDate.getMonth() + 1 < 10 ? nowDate.getMonth() + 1:nowDate.getMonth() + 1;
	if(month<10){
		month = "0" + month;
	}
	var dateStr = year + "-" + month;
	return dateStr;
}

//获取当前年月日后一天
export function getTommory(AddDayCount){
	var dd = new Date();
	dd.setDate(dd.getDate()+AddDayCount);
	var y = dd.getFullYear();
	var m = dd.getMonth()+1< 10 ? "0" + (dd.getMonth() + 1):dd.getMonth() + 1;
	var d = dd.getDate()< 10 ? "0" + dd.getDate() : dd.getDate();;
	return y+"-"+m+"-"+d;
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

export function getCurrentTime(){
  let vWeek, vWeek_s, year, month, day, hours, minutes, seconds;
  vWeek = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
  let date = new Date();
  year = date.getFullYear();
  month = date.getMonth() + 1;
  month = month < 10 ? "0" + month : month;
  day = date.getDate();
  day = day < 10 ? "0" + day : day;
  hours = date.getHours();
  hours = hours < 10 ? "0" + hours : hours;
  minutes = date.getMinutes();
  minutes = minutes < 10 ? "0" + minutes : minutes;
  seconds = date.getSeconds();
  seconds = seconds < 10 ? "0" + seconds : seconds;
  vWeek_s = date.getDay();
  return year + "-" + month + "-" + day + "\t" + vWeek[vWeek_s] + "\t" + hours + ":" + minutes + ":" + seconds;
}










