(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-28ab61c7"],{"0b70":function(e,t,a){"use strict";a.d(t,"c",(function(){return d})),a.d(t,"a",(function(){return u})),a.d(t,"e",(function(){return h})),a.d(t,"d",(function(){return p}));var n=a("b775"),o=a("4360"),i=a("2b0e"),s=i["default"].prototype.processUrl;function r(e){return Object(n["a"])({url:"".concat(s,"/api/wf/queryTaskBtnConfigs"),method:"post",params:e})}function l(e){return Object(n["a"])({url:"".concat(s,"/api/wf/queryNextAssignee"),method:"post",params:e})}function c(e,t,a,o){return Object(n["a"])({url:"/basic/public/queryEmpInfo?compCode="+e+"&deptCode="+t+"&postCode="+a+"&searchName="+o,method:"get"})}function d(e){var t=arguments.length>2?arguments[2]:void 0,a=arguments.length>4?arguments[4]:void 0,n=arguments.length>5?arguments[5]:void 0,i=arguments.length>6?arguments[6]:void 0,s=arguments.length>7?arguments[7]:void 0,d=arguments.length>8?arguments[8]:void 0,u=o["a"].state.user.userInfo.userId,h=o["a"].state.user.userInfo.dept.deptId;if(console.log("abc"+t+"_____"+a+"_"+n+"_"+i+"_"+s+"_"+d+"_"),null==t||""==t)e.btn1=!0;else{var p={systemId:"PMS",userId:u,procInsId:t};r(p).then((function(t){console.log("11111="+t.data.btnCode),"2"==t.data.btnCode?(e.btn1=!0,e.btn2=!0,e.btn4=!0,l(p).then((function(t){if(console.log(t.data),"0"==t.data.type);else if("1"==t.data.type){e.btn5=!0;var a=h,n="",o="",i=t.data.assigneeId;c(a,o,n,i).then((function(t){console.log(t),e.userOptions=t.rows}))}else if("2"==t.data.type){e.btn5=!0;var s=h,r="",l="",d="";c(s,l,r,d).then((function(t){console.log(t),e.userOptions=t.rows}))}else if("3"==t.data.type){e.btn5=!0;var u=h,p="",f="",m="";c(u,f,p,m).then((function(t){console.log(t),e.userOptions=t.rows}))}else if("4"==t.data.type){e.btn5=!0;var g=h,b="",v="",y=t.data.backUserId;c(g,v,b,y).then((function(t){console.log(t),e.userOptions=t.rows}))}}))):"3"==t.data.btnCode?(e.btn3=!0,e.btn4=!0,l(p).then((function(t){if(console.log(t.data.type),"0"==t.data.type);else if("1"==t.data.type){e.btn5=!0;var a=h,n="",o="",i=t.data.assigneeId;c(a,o,n,i).then((function(t){console.log(t),e.userOptions=t.rows}))}else if("2"==t.data.type){e.btn5=!0;var s=h,r="",l="",d="";c(s,l,r,d).then((function(t){console.log(t),e.userOptions=t.rows}))}else if("3"==t.data.type){e.btn5=!0;var u=h,p="",f="",m="";c(u,f,p,m).then((function(t){console.log(t),e.userOptions=t.rows}))}else if("4"==t.data.type){e.btn5=!0;var g=h,b="",v="",y=t.data.backUserId;c(g,v,b,y).then((function(t){console.log(t),e.userOptions=t.rows}))}}))):"1"==t.data.btnCode&&(e.btn1=!0)})).catch((function(e){console.log(e)}))}}function u(e){return Object(n["a"])({url:"/emp/empinfo/checkInfo",method:"get",params:e})}function h(e){return Object(n["a"])({url:"/system/dept/treeselect",method:"get",params:e})}function p(e){return Object(n["a"])({url:"/system/room/list",method:"get",params:e})}},"13d5":function(e,t,a){"use strict";var n=a("23e7"),o=a("d58f").left,i=a("a640"),s=a("ae40"),r=a("2d00"),l=a("605d"),c=i("reduce"),d=s("reduce",{1:0}),u=!l&&r>79&&r<83;n({target:"Array",proto:!0,forced:!c||!d||u},{reduce:function(e){return o(this,e,arguments.length,arguments.length>1?arguments[1]:void 0)}})},"13f3":function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container",staticStyle:{padding:"2px 0 0 0"}},[a("div",{ref:"search"},[a("el-form",{directives:[{name:"show",rawName:"v-show",value:e.showSearch,expression:"showSearch"}],ref:"queryForm",attrs:{model:e.queryParams,inline:!0,"label-width":"68px",hidden:""}},[a("el-form-item",{attrs:{label:"年月",prop:"actDate"}},[a("el-date-picker",{attrs:{clearable:"",size:"small",type:"month","value-format":"yyyy-MM-dd",placeholder:"选择年月"},model:{value:e.queryParams.actDate,callback:function(t){e.$set(e.queryParams,"actDate",t)},expression:"queryParams.actDate"}})],1),a("el-form-item",[a("el-button",{attrs:{type:"primary",icon:"el-icon-search",size:"mini"},on:{click:e.handleQuery}},[e._v("搜索")])],1)],1)],1),a("div",{ref:"btns"},[a("el-row",{staticClass:"mb8",attrs:{gutter:10}},[a("el-col",{attrs:{span:1.5}},[a("el-button",{attrs:{type:"info",icon:"el-icon-back",size:"mini",loading:e.exportLoading},on:{click:e.goBack}},[e._v("返回 ")])],1),a("el-col",{attrs:{span:1.5}},[a("el-button",{attrs:{type:"primary",icon:"el-icon-plus",size:"mini"},on:{click:e.handleAdd}},[e._v("添加人员 ")])],1),a("el-col",{attrs:{span:1.5}},[a("el-button",{attrs:{type:"danger",icon:"el-icon-delete",size:"mini",disabled:e.multiple},on:{click:e.handleDelete}},[e._v("删除人员 ")])],1),a("el-col",{attrs:{span:1.5}},[""===e.auditState||"0"===e.auditState?a("el-button",{attrs:{type:"success",icon:"el-icon-edit-outline",size:"mini",loading:e.saveLoading},on:{click:e.handleSave}},[e._v("保存 ")]):e._e()],1),a("el-col",{attrs:{span:1.5}},["0"===e.auditState?a("el-button",{attrs:{type:"success",icon:"el-icon-edit-outline",size:"mini",loading:e.saveLoading},on:{click:e.handleCommit}},[e._v("提交 ")]):e._e()],1),a("el-col",{attrs:{span:1.5}},[a("el-button",{directives:[{name:"hasPermi",rawName:"v-hasPermi",value:["bonus:calculation:list"],expression:"['bonus:calculation:list']"}],attrs:{type:"primary",icon:"el-icon-notebook-2",size:"mini"},on:{click:e.processExamine}},[e._v("流转历史 ")])],1),a("el-col",{attrs:{span:1.5}},[[a("download-excel",{staticClass:"export-excel-wrapper",attrs:{data:e.sumDataList,fields:e.jsonFields,header:e.title,name:e.excelName}},[a("el-button",{attrs:{type:"warning",icon:"el-icon-document-copy",size:"mini"},on:{click:function(e){}}},[e._v("导出EXCEL ")])],1)]],2),a("right-toolbar",{attrs:{showSearch:e.showSearch},on:{"update:showSearch":function(t){e.showSearch=t},"update:show-search":function(t){e.showSearch=t},queryTable:e.getList}})],1)],1),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],ref:"multipleTable",attrs:{border:"",stripe:"",data:e.EvaluateDataList,height:"calc(100vh - 180px)","row-style":e.rowStyle,"row-class-name":e.rowClassName,"show-summary":!0,"summary-method":e.getSummaries},on:{"selection-change":e.handleSelectionChange,"row-click":e.rowClick}},[a("el-table-column",{attrs:{type:"selection",width:"55",align:"center"}}),a("el-table-column",{attrs:{sortable:"",label:"科室名称",align:"left",prop:"deptRoomName"}}),a("el-table-column",{attrs:{sortable:"",label:"人员姓名",align:"left",prop:"docName"}}),e._l(e.indexList,(function(t,n){return a("el-table-column",{key:n,attrs:{label:t.indexName,prop:t.guideCode,align:"center"},scopedSlots:e._u([{key:"default",fn:function(n){return[a("div",{staticStyle:{display:"inline"},on:{click:function(e){e.stopPropagation(),e.preventDefault()}}},[a("el-input-number",{attrs:{precision:2,step:.01,min:0,"controls-position":"right"},model:{value:n.row[t.guideCode],callback:function(a){e.$set(n.row,t.guideCode,a)},expression:"scope.row[item.guideCode]"}})],1)]}}],null,!0)})})),a("el-table-column",{attrs:{sortable:"",label:"合计",align:"right",prop:"total"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(e.computedStatus(t.row)))])]}}])})],2),a("el-form",{ref:"form",attrs:{model:e.form,"label-width":"80px"}},[a("formItem",{attrs:{showDialog:e.showDialog,showBaseInfo:e.btn3,open:e.btn4,open2:e.btn5,comment:e.form,userOptions:e.userOptions},on:{"update:showDialog":function(t){e.showDialog=t},"update:show-dialog":function(t){e.showDialog=t},success:e.formItemSuccess}})],1),e.openSelect?a("selectList",{attrs:{deptCode:this.queryParams.deptCode,actDate:this.queryParams.actDate,openSelect:e.openSelect,doctorList:this.doctorList},on:{docCodeList:function(t){return e.docCodeList(arguments)},cancelSelect:e.cancelSelect}}):e._e(),a("el-dialog",{attrs:{title:"流转历史",visible:e.processOpen,width:"1200px","append-to-body":"","close-on-click-modal":!1,"custom-class":"el-button--primary"},on:{"update:visible":function(t){e.processOpen=t}}},[a("div",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],style:"height:"+e.height},[a("iframe",{staticStyle:{width:"100%",height:"100%"},attrs:{src:e.src,frameborder:"no",scrolling:"auto"}})])])],1)},o=[],i=a("b85c"),s=(a("b680"),a("99af"),a("d81d"),a("7db0"),a("d3b7"),a("a434"),a("159b"),a("b0c0"),a("a9e3"),a("13d5"),a("c466")),r=a("e472"),l=a("3b86"),c=a("0e67"),d=a("986b"),u=a("0b70"),h=a("f2d9"),p=a("2b0e"),f=a("d623");p["default"].component("downloadExcel",h["a"]);var m={name:"EvaluateData",deptCode:{type:[String,String],default:""},components:{formItem:c["default"],btns:d["default"],selectList:f["default"]},filters:{transToValue:function(e,t){return e[t]}},computed:{computedStatus:function(){return function(e){var t,a=0,n=Object(i["a"])(this.indexList);try{for(n.s();!(t=n.n()).done;){var o=t.value;a+=parseFloat(e[o.guideCode])}}catch(s){n.e(s)}finally{n.f()}return e.total=a.toFixed(2),a.toFixed(2)}},sumDataList:function(){return this.sumDataList_.push({}),this.sumDataList_.push(this.bottomTotal),this.sumDataList_},excelName:function(){return"人员奖金"+Object(s["c"])()+".xls"}},mounted:function(){},watch:{},data:function(){return{processOpen:!1,height:"500px;",src:"",showAudit:!1,id:"",showDialog:!1,userOptions:[],auditState:"0",saveLoading:!1,loading:!1,exportLoading:!1,ids:[],single:!0,multiple:!0,showSearch:!0,total:0,EvaluateDataList:[],sumDataList_:[],selectionRow:[],title:"",open:!1,queryParams:{pageNum:1,pageSize:30,compCode:null,deptCode:null,deptName:null,accountCode:null,guideCode:null,guideValue:null,delFlag:null,actDate:Object(s["c"])()+"-01",createBy:null,createTime:null,updateBy:null,updateTime:null,remark:null,deptType:null},form:{procedureId:null,assignee:null},indexList:[],nextAssignee:[],btn1:!1,btn2:!1,btn3:!1,btn4:!1,btn5:!1,comment:"",proclnsIds:"",datas:{},openSelect:!1,doctorOpen:!1,docParams:{pageNum:1,pageSize:20},doctorList:[],docTotal:0,tableTitle:"人员奖金数据",jsonFields:{"科室名称":"deptRoomName","人员姓名":"docName"},bottomTotal:{deptRoomName:"合计",docName:"",total:0}}},created:function(){this.getDept(),this.getDetailInfo()},methods:{getDept:function(){var e=this;this.loading=!0,this.queryParams.actDate=this.$route.params.actDate,this.queryParams.nowDate=this.$route.params.actDate,this.queryParams.deptCode=this.$route.params.deptCode,Object(l["a"])(this.queryParams).then((function(t){e.indexList=t.data,e.getList(),e.loading=!1}))},getList:function(){var e=this;for(var t in this.loading=!0,this.queryParams.actDate||(this.queryParams.actDate=Object(s["c"])()+"-01"),this.queryParams.deptCode=this.$route.params.deptCode,Object(l["d"])(this.queryParams).then((function(t){for(var a in e.EvaluateDataList=t.data,e.tableDoLayout(),e.sumDataList_=[].concat(t.data),e.EvaluateDataList)""!=e.EvaluateDataList[a]["remark"]&&null!=e.EvaluateDataList[a]["remark"]||(e.EvaluateDataList[a]["remark"]="0");e.loading=!1})),this.indexList)this.jsonFields[this.indexList[t].indexName]=this.indexList[t].guideCode;this.jsonFields["合计"]="total"},successSubmit:function(e){this.open=e,this.getList()},cancel:function(){this.open=!1},cancelSelect:function(){this.openSelect=!1},reset:function(){this.form={id:null,compCode:null,deptName:null,accountCode:null,guideCode:null,guideValue:null,delFlag:null,actDate:null,createBy:null,createTime:null,updateBy:null,updateTime:null,remark:null},this.resetForm("form")},handleQuery:function(){this.queryParams.pageNum=1,this.getList()},resetQuery:function(){this.resetForm("queryForm"),this.handleQuery()},handleSelectionChange:function(e){this.ids=e.map((function(e){return e.docCode})),this.single=1!==e.length,this.multiple=!e.length,this.selectionRow=e},rowClick:function(e,t,a){var n=this.$refs.multipleTable,o=this.selectionRow.find((function(t){return t.rowIndex==e.rowIndex}));o&&1==this.selectionRow.length?n.toggleRowSelection(e,!1):(n.clearSelection(),n.toggleRowSelection(e))},rowStyle:function(e){var t=e.row,a=e.rowIndex;Object.defineProperty(t,"rowIndex",{value:a,writable:!0,enumerable:!1})},rowClassName:function(e){var t=e.row,a=(e.rowIndex,""),n=this.selectionRow.find((function(e){return e.rowIndex===t.rowIndex}));return n&&(a="current-row "),a},handleAdd:function(){this.doctorList=this.EvaluateDataList,this.openSelect=!0},tableRowClassName:function(e){var t=e.row,a=e.rowIndex;t.row_index=a},handleSelectionDocChange:function(e,t){this.singleTable(t)},handleDocRowClick:function(e){this.singleTable(e)},singleTable:function(e){var t=this.$refs.itsmDataTable;t.clearSelection(),this.currentRowIndex==e.row_index?(t.toggleRowSelection(e,!1),this.currentRowIndex=null,this.docSelectData=null):(t.toggleRowSelection(e),this.currentRowIndex=e.row_index,this.docSelectData=e)},sexFormat:function(e,t){return this.selectDictLabel(this.sexOptions,e.gender)},handleDelete:function(){for(var e=this.ids,t=this.EvaluateDataList.length-1;t>=0;t--)if(e.indexOf(this.EvaluateDataList[t].docCode)>-1&&"0"==this.EvaluateDataList[t].remark)return void this.msgError("姓名为："+this.EvaluateDataList[t].docName+"的人员数据不允许删除！");for(var a=this.EvaluateDataList.length-1;a>=0;a--)e.indexOf(this.EvaluateDataList[a].docCode)>-1&&"1"==this.EvaluateDataList[a].remark&&this.EvaluateDataList.splice(a,1)},handleExport:function(){var e=this;this.queryParams;this.$confirm("是否确认导出科室奖金数据项?","警告",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){for(var t in e.indexList)e.jsonFields.push(e.indexList[t].guideCode,e.indexList[t].guideCode);e.exportLoading=!0})).catch((function(){}))},goBack:function(){this.$store.dispatch("tagsView/delView",this.$route),this.$router.go(-1)},packageData:function(){var e=this;this.$refs.multipleTable.data.forEach((function(t){t.unitCode=t.docCode,t.deptCode=t.deptRoomCode,t.actDate=e.queryParams.actDate}));var t={data:this.$refs.multipleTable.data,audit:{deptCode:this.$route.params.deptCode,nowDate:this.queryParams.actDate,comment:this.form.comment,assignee:this.form.assignee}};return t},handleSave:function(){var e=this,t=this.packageData();if(!t.data||t.data.length<1)return this.msgError("缺少数据,无法保存"),!1;t.isCommit=!1,this.saveLoading=!0,Object(r["a"])(t).then((function(t){e.msgSuccess("保存成功"),e.saveLoading=!1,e.auditState="0",e.getDetailInfo(),e.showDialog=!0}))},handleCommit:function(){null===this.proclnsIds||""===this.proclnsIds?this.handleSave():this.showDialog=!0},formItemSuccess:function(){var e=this,t=this.packageData();t.isCommit=!0,this.saveLoading=!0,Object(r["a"])(t).then((function(t){e.msgSuccess("提交成功"),e.saveLoading=!1,e.auditState="1"}))},getDetailInfo:function(){var e=this;Object(r["f"])({deptCode:this.$route.params.deptCode,nowDate:this.queryParams.actDate}).then((function(t){if(t.rows.length>0){var a=t.rows[0],n=e,o=a.procedureId;e.proclnsIds=a.procedureId,e.form.procedureId=e.proclnsIds,e.id=a.id,e.auditState=a.auditState,Object(u["c"])(n,e.$store.state.user.name,o,e.userOptions,e.btn1,e.btn2,e.btn3,e.btn4,e.btn5)}}))},processExamineForm2:function(){this.src="".concat(this.processUrl,"/api/wf/processHistoric?procInsId=").concat(this.proclnsIds)},processExamine:function(){var e=this.proclnsIds;null!=e&&e?(this.processExamineForm2(),this.processOpen=!0):this.msgError("没有流转历史数据！")},docCodeList:function(e){for(var t in e[0]){var a={deptRoomCode:this.EvaluateDataList[0].deptRoomCode,deptRoomName:this.EvaluateDataList[0].deptRoomName,docCode:e[0][t].docCode,docName:e[0][t].docName};for(var n in this.indexList)a[this.indexList[n].guideCode]=0,a["remark"]="1";this.EvaluateDataList.push(a)}},getSummaries:function(e){var t=this,a=e.columns,n=e.data,o=[];return a.forEach((function(e,a){if(0!==a){var i=n.map((function(t){return Number(t[e.property])}));i.every((function(e){return isNaN(e)}))?o[a]="N/A":(o[a]=i.reduce((function(e,t){var a=Number(t);return isNaN(a)?e:e+t}),0),"deptRoomName"!=e.property&&"docName"!=e.property&&(t.bottomTotal[e.property]=parseFloat(o[a])),o[a]=o[a].toFixed(2))}else o[a]="合计"})),o},tableDoLayout:function(){var e=this;this.$refs.multipleTable&&this.$nextTick((function(){e.$refs.multipleTable.doLayout()}))}}},g=m,b=a("2877"),v=Object(b["a"])(g,n,o,!1,null,null,null);t["default"]=v.exports},d58f:function(e,t,a){var n=a("1c0b"),o=a("7b0b"),i=a("44ad"),s=a("50c4"),r=function(e){return function(t,a,r,l){n(a);var c=o(t),d=i(c),u=s(c.length),h=e?u-1:0,p=e?-1:1;if(r<2)while(1){if(h in d){l=d[h],h+=p;break}if(h+=p,e?h<0:u<=h)throw TypeError("Reduce of empty array with no initial value")}for(;e?h>=0:u>h;h+=p)h in d&&(l=a(l,d[h],h,c));return l}};e.exports={left:r(!1),right:r(!0)}},f2d9:function(e,t,a){"use strict";(function(e){"undefined"!==typeof globalThis?globalThis:"undefined"!==typeof window?window:"undefined"!==typeof e||"undefined"!==typeof self&&self;function a(e,t){return t={exports:{}},e(t,t.exports),t.exports}var n=a((function(e,t){(function(t,a){e.exports=a()})(0,(function(){return function e(t,a,n){var o,i,s=window,r="application/octet-stream",l=n||r,c=t,d=!a&&!n&&c,u=document.createElement("a"),h=function(e){return String(e)},p=s.Blob||s.MozBlob||s.WebKitBlob||h,f=a||"download";if(p=p.call?p.bind(s):Blob,"true"===String(this)&&(c=[c,l],l=c[0],c=c[1]),d&&d.length<2048&&(f=d.split("/").pop().split("?")[0],u.href=d,-1!==u.href.indexOf(d))){var m=new XMLHttpRequest;return m.open("GET",d,!0),m.responseType="blob",m.onload=function(t){e(t.target.response,f,r)},setTimeout((function(){m.send()}),0),m}if(/^data:([\w+-]+\/[\w+.-]+)?[,;]/.test(c)){if(!(c.length>2096103.424&&p!==h))return navigator.msSaveBlob?navigator.msSaveBlob(y(c),f):w(c);c=y(c),l=c.type||r}else if(/([\x80-\xff])/.test(c)){var g=0,b=new Uint8Array(c.length),v=b.length;for(g;g<v;++g)b[g]=c.charCodeAt(g);c=new p([b],{type:l})}function y(e){var t=e.split(/[:;,]/),a=t[1],n="base64"==t[2]?atob:decodeURIComponent,o=n(t.pop()),i=o.length,s=0,r=new Uint8Array(i);for(s;s<i;++s)r[s]=o.charCodeAt(s);return new p([r],{type:a})}function w(e,t){if("download"in u)return u.href=e,u.setAttribute("download",f),u.className="download-js-link",u.innerHTML="downloading...",u.style.display="none",document.body.appendChild(u),setTimeout((function(){u.click(),document.body.removeChild(u),!0===t&&setTimeout((function(){s.URL.revokeObjectURL(u.href)}),250)}),66),!0;if(/(Version)\/(\d+)\.(\d+)(?:\.(\d+))?.*Safari\//.test(navigator.userAgent))return/^data:/.test(e)&&(e="data:"+e.replace(/^data:([\w\/\-\+]+)/,r)),window.open(e)||confirm("Displaying New Document\n\nUse Save As... to download, then click back to return to this page.")&&(location.href=e),!0;var a=document.createElement("iframe");document.body.appendChild(a),!t&&/^data:/.test(e)&&(e="data:"+e.replace(/^data:([\w\/\-\+]+)/,r)),a.src=e,setTimeout((function(){document.body.removeChild(a)}),333)}if(o=c instanceof p?c:new p([c],{type:l}),navigator.msSaveBlob)return navigator.msSaveBlob(o,f);if(s.URL)w(s.URL.createObjectURL(o),!0);else{if("string"===typeof o||o.constructor===h)try{return w("data:"+l+";base64,"+s.btoa(o))}catch(x){return w("data:"+l+","+encodeURIComponent(o))}i=new FileReader,i.onload=function(e){w(this.result)},i.readAsDataURL(o)}return!0}}))})),o={props:{type:{type:String,default:"xls"},data:{type:Array,required:!1,default:null},fields:{type:Object,default:()=>null},exportFields:{type:Object,default:()=>null},defaultValue:{type:String,required:!1,default:""},header:{default:null},footer:{default:null},name:{type:String,default:"data.xls"},fetch:{type:Function},meta:{type:Array,default:()=>[]},worksheet:{type:String,default:"Sheet1"},beforeGenerate:{type:Function},beforeFinish:{type:Function},escapeCsv:{type:Boolean,default:!0},stringifyLongNum:{type:Boolean,default:!1}},computed:{idName(){var e=(new Date).getTime();return"export_"+e},downloadFields(){return this.fields?this.fields:this.exportFields?this.exportFields:void 0}},methods:{async generate(){"function"===typeof this.beforeGenerate&&await this.beforeGenerate();let e=this.data;if("function"!==typeof this.fetch&&e||(e=await this.fetch()),!e||!e.length)return;let t=this.getProcessedJson(e,this.downloadFields);return"html"===this.type?this.export(this.jsonToXLS(t),this.name.replace(".xls",".html"),"text/html"):"csv"===this.type?this.export(this.jsonToCSV(t),this.name.replace(".xls",".csv"),"application/csv"):this.export(this.jsonToXLS(t),this.name,"application/vnd.ms-excel")},export:async function(e,t,a){let o=this.base64ToBlob(e,a);"function"===typeof this.beforeFinish&&await this.beforeFinish(),n(o,t,a)},jsonToXLS(e){let t='<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><meta name=ProgId content=Excel.Sheet> <meta name=Generator content="Microsoft Excel 11"><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">\x3c!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>${worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--\x3e<style>br {mso-data-placement: same-cell;}</style></head><body><table>${table}</table></body></html>',a="<thead>";const n=Object.keys(e[0]).length;let o=this;const i=this.header||this.$attrs.title;i&&(a+=this.parseExtraData(i,'<tr><th colspan="'+n+'">${data}</th></tr>')),a+="<tr>";for(let s in e[0])a+="<th>"+s+"</th>";return a+="</tr>",a+="</thead>",a+="<tbody>",e.map((function(e,t){a+="<tr>";for(let n in e)a+="<td>"+o.preprocessLongNum(o.valueReformattedForMultilines(e[n]))+"</td>";a+="</tr>"})),a+="</tbody>",null!=this.footer&&(a+="<tfoot>",a+=this.parseExtraData(this.footer,'<tr><td colspan="'+n+'">${data}</td></tr>'),a+="</tfoot>"),t.replace("${table}",a).replace("${worksheet}",this.worksheet)},jsonToCSV(e){let t=this;var a=[];const n=this.header||this.$attrs.title;n&&a.push(this.parseExtraData(n,"${data}\r\n"));for(let o in e[0])a.push(o),a.push(",");return a.pop(),a.push("\r\n"),e.map((function(e){for(let n in e){let o=e[n]+"";t.escapeCsv&&(o='="'+o+'"',o.match(/[,"\n]/)&&(o='"'+o.replace(/\"/g,'""')+'"')),a.push(o),a.push(",")}a.pop(),a.push("\r\n")})),null!=this.footer&&a.push(this.parseExtraData(this.footer,"${data}\r\n")),a.join("")},getProcessedJson(e,t){let a=this.getKeys(e,t),n=[],o=this;return e.map((function(e,t){let i={};for(let n in a){let t=a[n];i[n]=o.getValue(t,e)}n.push(i)})),n},getKeys(e,t){if(t)return t;let a={};for(let n in e[0])a[n]=n;return a},parseExtraData(e,t){let a="";if(Array.isArray(e))for(var n=0;n<e.length;n++)e[n]&&(a+=t.replace("${data}",e[n]));else a+=t.replace("${data}",e);return a},getValue(e,t){const a="object"!==typeof e?e:e.field;let n="string"!==typeof a?[]:a.split("."),o=this.defaultValue;return o=a?n.length>1?this.getValueFromNestedItem(t,n):this.parseValue(t[a]):t,e.hasOwnProperty("callback")&&(o=this.getValueFromCallback(o,e.callback)),o},valueReformattedForMultilines(e){return"string"==typeof e?e.replace(/\n/gi,"<br/>"):e},preprocessLongNum(e){if(this.stringifyLongNum){if(String(e).startsWith("0x"))return e;if(!isNaN(e)&&""!=e&&(e>99999999999||e<1e-13))return'="'+e+'"'}return e},getValueFromNestedItem(e,t){let a=e;for(let n of t)a&&(a=a[n]);return this.parseValue(a)},getValueFromCallback(e,t){if("function"!==typeof t)return this.defaultValue;const a=t(e);return this.parseValue(a)},parseValue(e){return e||0===e||"boolean"===typeof e?e:this.defaultValue},base64ToBlob(e,t){let a=window.btoa(window.unescape(encodeURIComponent(e))),n=atob(a),o=n.length,i=new Uint8ClampedArray(o);while(o--)i[o]=n.charCodeAt(o);return new Blob([i],{type:t})}}};function i(e,t,a,n,o,i,s,r,l,c){"boolean"!==typeof s&&(l=r,r=s,s=!1);var d,u="function"===typeof a?a.options:a;if(e&&e.render&&(u.render=e.render,u.staticRenderFns=e.staticRenderFns,u._compiled=!0,o&&(u.functional=!0)),n&&(u._scopeId=n),i?(d=function(e){e=e||this.$vnode&&this.$vnode.ssrContext||this.parent&&this.parent.$vnode&&this.parent.$vnode.ssrContext,e||"undefined"===typeof __VUE_SSR_CONTEXT__||(e=__VUE_SSR_CONTEXT__),t&&t.call(this,l(e)),e&&e._registeredComponents&&e._registeredComponents.add(i)},u._ssrRegister=d):t&&(d=s?function(){t.call(this,c(this.$root.$options.shadowRoot))}:function(e){t.call(this,r(e))}),d)if(u.functional){var h=u.render;u.render=function(e,t){return d.call(t),h(e,t)}}else{var p=u.beforeCreate;u.beforeCreate=p?[].concat(p,d):[d]}return a}var s=i;const r=o;var l=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{attrs:{id:e.idName},on:{click:e.generate}},[e._t("default",[e._v(" Download "+e._s(e.name)+" ")])],2)},c=[];l._withStripped=!0;const d=void 0,u=void 0,h=void 0,p=!1;var f=s({render:l,staticRenderFns:c},d,r,u,p,h,void 0,void 0);t["a"]=f}).call(this,a("c8ba"))}}]);