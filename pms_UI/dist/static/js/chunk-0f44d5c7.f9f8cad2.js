(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-0f44d5c7"],{"18da":function(e,t,l){"use strict";l.d(t,"c",(function(){return i})),l.d(t,"f",(function(){return a})),l.d(t,"h",(function(){return r})),l.d(t,"e",(function(){return o})),l.d(t,"a",(function(){return n})),l.d(t,"b",(function(){return c})),l.d(t,"g",(function(){return u})),l.d(t,"d",(function(){return d}));var s=l("b775");function i(e){return Object(s["a"])({url:"/pmsFormDesignField/getFormConfig",method:"get",params:e})}function a(e){return Object(s["a"])({url:"/pmsFormDesignField/save",method:"post",data:e})}function r(e,t){return Object(s["a"])({url:"/pmsFormProcess/list?formDesignId="+t,method:"post",data:e})}function o(e,t){return Object(s["a"])({url:"/pmsFormProcess/getInfo/"+e+"/"+t,method:"get"})}function n(e,t){return Object(s["a"])({url:"/pmsFormProcess/save?formDesignId="+t,method:"post",data:e})}function c(e,t){return Object(s["a"])({url:"/pmsFormProcess/delete/"+e+"/"+t,method:"delete"})}function u(e,t,l){return Object(s["a"])({url:"/formDesign/pmsFormDesign/saveDesign?fdId="+e,method:"post",data:{formJson:t,formHtml:l}})}function d(e){return Object(s["a"])({url:"/formDesign/pmsFormDesign/"+e,method:"get"})}},4730:function(e,t,l){"use strict";l.d(t,"k",(function(){return i})),l.d(t,"l",(function(){return a})),l.d(t,"h",(function(){return r})),l.d(t,"i",(function(){return o})),l.d(t,"n",(function(){return n})),l.d(t,"g",(function(){return c})),l.d(t,"e",(function(){return u})),l.d(t,"m",(function(){return d})),l.d(t,"j",(function(){return f})),l.d(t,"d",(function(){return m})),l.d(t,"a",(function(){return p})),l.d(t,"o",(function(){return v})),l.d(t,"c",(function(){return h})),l.d(t,"f",(function(){return g})),l.d(t,"b",(function(){return b}));var s=l("b775");function i(e){return Object(s["a"])({url:"/formDesign/formDesign/list",method:"get",params:e})}function a(e){return Object(s["a"])({url:"/formDesign/formDesign/selectForSortNo",method:"get",params:e})}function r(e){return Object(s["a"])({url:"/formDesign/formDesign/"+e,method:"get"})}function o(e){return Object(s["a"])({url:"/formDesignField/getFormConfig",method:"get",params:e})}function n(e){return Object(s["a"])({url:"/formDesign/formDesign/setPageNo",method:"put",data:e})}function c(e){return Object(s["a"])({url:"/basic/public/deptInfoForPms",method:"get",params:e})}function u(e){return Object(s["a"])({url:"/formDesignField/updateFormDesignField",method:"post",data:e})}function d(e){return Object(s["a"])({url:"/formDesignField/selectDictType",method:"get",params:e})}function f(e){return Object(s["a"])({url:"/quality/index/listForTemplate",method:"get",params:e})}function m(e){return Object(s["a"])({url:"/formDesignField/updateAll",method:"post",data:e})}function p(e){return Object(s["a"])({url:"/formDesign/formDesign",method:"post",data:e})}function v(e){return Object(s["a"])({url:"/formDesign/formDesign",method:"put",data:e})}function h(e){return Object(s["a"])({url:"/formDesign/formDesign/"+e,method:"delete"})}function g(e){return Object(s["a"])({url:"/formDesign/formDesign/export",method:"get",params:e})}function b(e){return Object(s["a"])({url:"/formDesign/formDesign/checkFormCodeUnique?formCode="+e,method:"get"})}},a2fb:function(e,t,l){"use strict";l.r(t);var s=function(){var e=this,t=e.$createElement,l=e._self._c||t;return l("div",{staticClass:"app-container"},[l("div",{ref:"searchs"},[l("el-form",{ref:"queryForm",attrs:{model:e.queryParams,inline:!0}},[e._l(e.lists,(function(t,s){return["input"==t.fieldType?["1"==t.isQuery?l("el-form-item",{attrs:{label:t.fieldDesc,prop:t.fieldName}},[l("el-input",{staticClass:"search_w220",attrs:{clearable:"",maxlength:t.fieldLength},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):e._e()]:"select"==t.fieldType?["1"==t.isQuery?l("el-form-item",{attrs:{label:t.fieldDesc,prop:t.fieldName}},[l("el-select",{staticClass:"search_w220",model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}},e._l(t.options,(function(e){return l("el-option",{key:e.dictValue,attrs:{label:e.dictLabel,value:e.dictValue}})})),1)],1):e._e()]:"date"==t.fieldType?["1"==t.isQuery?l("el-form-item",{attrs:{label:t.fieldDesc,prop:t.fieldName}},[l("el-date-picker",{staticClass:"search_w220",attrs:{"value-format":"yyyy-MM-dd"},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):e._e()]:"time"==t.fieldType?["1"==t.isQuery?l("el-form-item",{attrs:{label:t.fieldDesc,prop:t.fieldName}},[l("el-date-picker",{staticClass:"search_w220",attrs:{type:"datetime","value-format":"yyyy-MM-dd HH:mm:ss"},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):e._e()]:"textarea"==t.fieldType?["1"==t.isQuery?l("el-form-item",{attrs:{label:t.fieldDesc,prop:t.fieldName}},[l("el-input",{attrs:{maxlength:t.fieldLength,type:"textarea"},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):e._e()]:"number"==t.fieldType?["1"==t.isQuery?l("el-form-item",{attrs:{label:t.fieldDesc,prop:t.fieldName}},[l("el-input-number",{staticClass:"search_w220",attrs:{"controls-position":"right",min:0},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):e._e()]:"rate"==t.fieldType?["1"==t.isQuery?l("el-form-item",{attrs:{label:t.fieldDesc,prop:t.fieldName}},[l("el-rate",{model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):e._e()]:"color"==t.fieldType?["1"==t.isQuery?l("el-form-item",{attrs:{label:t.fieldDesc,prop:t.fieldName}},[l("el-color-picker",{model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):e._e()]:"switch"==t.fieldType?["1"==t.isQuery?l("el-form-item",{attrs:{label:t.fieldDesc,prop:t.fieldName}},[l("el-switch",{attrs:{"active-value":"打开","inactive-value":"关闭"},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):e._e()]:"radio"==t.fieldType?["1"==t.isQuery?l("el-form-item",{attrs:{label:t.fieldDesc,prop:t.fieldName}},[l("el-radio-group",{model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}},[e._l(t.options,(function(t,s){return[l("el-radio",{attrs:{label:t.label}},[e._v(e._s(t.value))])]}))],2)],1):e._e()]:"checkbox"==t.fieldType?["1"==t.isQuery?l("el-form-item",{attrs:{label:t.fieldDesc,prop:t.fieldName}},[l("el-checkbox-group",{model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}},[e._l(t.options,(function(e,t){return[l("el-checkbox",{attrs:{label:e.value}})]}))],2)],1):e._e()]:["1"==t.isQuery?l("el-form-item",{attrs:{label:t.fieldDesc,prop:t.fieldName}},[l("el-input",{attrs:{clearable:"",maxlength:t.fieldLength},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):e._e()]]})),l("el-form-item",[l("el-button",{attrs:{type:"primary",icon:"el-icon-search",size:"mini"},on:{click:e.handleQuery}},[e._v("搜索")]),l("el-button",{attrs:{icon:"el-icon-refresh",size:"mini"},on:{click:e.resetQuery}},[e._v("重置")])],1)],2)],1),l("div",{ref:"btns"},[l("el-row",{staticClass:"mb8",attrs:{gutter:10}},[l("el-col",{attrs:{span:1.5}},[l("el-button",{attrs:{type:"primary",icon:"el-icon-plus",size:"mini"},on:{click:e.handleAdd}},[e._v("新增")])],1),l("el-col",{attrs:{span:1.5}},[l("el-button",{attrs:{type:"success",icon:"el-icon-edit",size:"mini",disabled:e.single},on:{click:e.handleUpdate}},[e._v("修改")])],1),l("el-col",{attrs:{span:1.5}},[l("el-button",{attrs:{type:"danger",icon:"el-icon-delete",size:"mini",disabled:e.multiple},on:{click:e.handleDelete}},[e._v("删除")])],1)],1)],1),l("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],ref:"multipleTable",attrs:{data:e.formDesignList,"max-height":e.tableHeight,"row-style":e.rowStyle,"row-class-name":e.rowClassName,"highlight-current-row":""},on:{"selection-change":e.handleSelectionChange,"row-click":e.rowClick}},[l("el-table-column",{attrs:{type:"selection",width:"55",align:"center"}}),e._l(e.cols,(function(t){return["1"==t.isList?["select"==t.fieldType?[l("el-table-column",{attrs:{align:"center",label:t.label,prop:t.prop,formatter:e.stateFormat}})]:[l("el-table-column",{attrs:{align:"center",label:t.label,prop:t.prop}})]]:e._e()]}))],2),l("el-dialog",{attrs:{title:e.title,"custom-class":"el-button--primary",visible:e.open,width:"700px","append-to-body":""},on:{"update:visible":function(t){e.open=t}}},[l("el-form",{ref:"form",attrs:{model:e.form,"label-width":"100px"}},[l("el-row",[e._l(e.form.lists2,(function(t,s){return["input"==t.fieldType?["1"==t.isEdit?[null==t.validType?[l("el-col",{attrs:{span:t.span}},["1"==t.isNotNull?l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val",rules:{required:!0,message:t.fieldDesc+"不能为空",trigger:"change"}}},[l("el-input",{staticClass:"w90",attrs:{maxlength:t.fieldLength},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val"}},[l("el-input",{staticClass:"w90",attrs:{maxlength:t.fieldLength},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1)],1)]:"email"==t.validType?[l("el-col",{attrs:{span:t.span}},[l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val",rules:[{required:!0,message:"请输入邮箱地址",trigger:"blur"},{type:"email",message:"请输入正确的邮箱地址",trigger:["blur","change"]}]}},[l("el-input",{staticClass:"w90",model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1)],1)]:e._e()]:e._e()]:"select"==t.fieldType?["1"==t.isEdit?[l("el-col",{attrs:{span:t.span}},["1"==t.isNotNull?l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val",rules:{required:!0,message:t.fieldDesc+"不能为空",trigger:"change"}}},[l("el-select",{staticClass:"w90",model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}},e._l(t.options,(function(e){return l("el-option",{key:e.dictValue,attrs:{label:e.dictLabel,value:e.dictValue}})})),1)],1):l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val"}},[l("el-select",{staticClass:"w90",model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}},e._l(t.options,(function(e){return l("el-option",{key:e.dictValue,attrs:{label:e.dictLabel,value:e.dictValue}})})),1)],1)],1)]:e._e()]:"date"==t.fieldType?["1"==t.isEdit?[l("el-col",{attrs:{span:t.span}},["1"==t.isNotNull?l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val",rules:{required:!0,message:t.fieldDesc+"不能为空",trigger:"change"}}},[l("el-date-picker",{staticClass:"w90",attrs:{"value-format":"yyyy-MM-dd"},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val"}},[l("el-date-picker",{staticClass:"w90",attrs:{"value-format":"yyyy-MM-dd"},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1)],1)]:e._e()]:"time"==t.fieldType?["1"==t.isEdit?[l("el-col",{attrs:{span:t.span}},["1"==t.isNotNull?l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val",rules:{required:!0,message:t.fieldDesc+"不能为空",trigger:"change"}}},[l("el-date-picker",{staticClass:"w90",attrs:{type:"datetime","value-format":"yyyy-MM-dd HH:mm:ss"},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val"}},[l("el-date-picker",{staticClass:"w90",attrs:{type:"datetime","value-format":"yyyy-MM-dd HH:mm:ss"},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1)],1)]:e._e()]:"textarea"==t.fieldType?["1"==t.isEdit?[l("el-col",{attrs:{span:t.span}},["1"==t.isNotNull?l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val",rules:{required:!0,message:t.fieldDesc+"不能为空",trigger:"change"}}},[l("el-input",{staticClass:"w90",attrs:{maxlength:t.fieldLength,type:"textarea"},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val"}},[l("el-input",{staticClass:"w90",attrs:{maxlength:t.fieldLength,type:"textarea"},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1)],1)]:e._e()]:"number"==t.fieldType?["1"==t.isEdit?[l("el-col",{attrs:{span:t.span}},["1"==t.isNotNull?l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val",rules:{required:!0,message:t.fieldDesc+"不能为空",trigger:"change"}}},[l("el-input-number",{staticClass:"w90",attrs:{"controls-position":"right",min:0,size:"mini"},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val"}},[l("el-input-number",{staticClass:"w90",attrs:{"controls-position":"right",min:0,size:"mini"},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1)],1)]:e._e()]:"rate"==t.fieldType?["1"==t.isEdit?[l("el-col",{attrs:{span:t.span}},["1"==t.isNotNull?l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val",rules:{required:!0,message:t.fieldDesc+"不能为空",trigger:"change"}}},[l("el-rate",{model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val"}},[l("el-rate",{model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1)],1)]:e._e()]:"color"==t.fieldType?["1"==t.isEdit?[l("el-col",{attrs:{span:t.span}},["1"==t.isNotNull?l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val",rules:{required:!0,message:t.fieldDesc+"不能为空",trigger:"change"}}},[l("el-color-picker",{staticStyle:{width:"100%"},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val"}},[l("el-color-picker",{staticStyle:{width:"100%"},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1)],1)]:e._e()]:"switch"==t.fieldType?["1"==t.isEdit?[l("el-col",{attrs:{span:t.span}},["1"==t.isNotNull?l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val",rules:{required:!0,message:t.fieldDesc+"不能为空",trigger:"change"}}},[l("el-switch",{attrs:{"active-value":"打开","inactive-value":"关闭"},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val"}},[l("el-switch",{attrs:{"active-value":"打开","inactive-value":"关闭"},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1)],1)]:e._e()]:"radio"==t.fieldType?["1"==t.isEdit?[l("el-col",{attrs:{span:t.span}},["1"==t.isNotNull?l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val",rules:{required:!0,message:t.fieldDesc+"不能为空",trigger:"change"}}},[l("el-radio-group",{model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}},[e._l(t.options,(function(t,s){return[l("el-radio",{attrs:{label:t.label}},[e._v(e._s(t.value))])]}))],2)],1):l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val"}},[l("el-radio-group",{model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}},[e._l(t.options,(function(t,s){return[l("el-radio",{attrs:{label:t.label}},[e._v(e._s(t.value))])]}))],2)],1)],1)]:e._e()]:"checkbox"==t.fieldType?["1"==t.isEdit?[l("el-col",{attrs:{span:t.span}},["1"==t.isNotNull?l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val",rules:{required:!0,message:t.fieldDesc+"不能为空",trigger:"change"}}},[l("el-checkbox-group",{model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}},[e._l(t.options,(function(e,t){return[l("el-checkbox",{attrs:{label:e.value}})]}))],2)],1):l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val"}},[l("el-checkbox-group",{model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}},[e._l(t.options,(function(e,t){return[l("el-checkbox",{attrs:{label:e.value}})]}))],2)],1)],1)]:e._e()]:["1"==t.isEdit?[l("el-col",{attrs:{span:t.span}},["1"==t.isNotNull?l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val",rules:{required:!0,message:t.fieldDesc+"不能为空",trigger:"change"}}},[l("el-input",{attrs:{maxlength:t.fieldLength},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1):l("el-form-item",{attrs:{label:t.fieldDesc,prop:"lists2."+s+".val"}},[l("el-input",{attrs:{maxlength:t.fieldLength},model:{value:t.val,callback:function(l){e.$set(t,"val",l)},expression:"item.val"}})],1)],1)]:e._e()]]}))],2)],1),l("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[l("el-button",{attrs:{type:"primary"},on:{click:e.submitForm}},[e._v("确 定")]),l("el-button",{on:{click:e.cancel}},[e._v("取 消")])],1)],1)],1)},i=[],a=(l("b0c0"),l("e9c4"),l("d81d"),l("7db0"),l("d3b7"),l("18da")),r=l("4730"),o={data:function(){return{tableHeight:"auto",idd:null,addEditId:"",lists:[],lists2:[],cols:[],jsonData:{list:[]},formDesignId:this.$route.name,formName:"",loading:!1,exportLoading:!1,ids:[],single:!0,multiple:!0,showSearch:!0,total:0,formDesignList:[],title:"",open:!1,queryParams:{pageNum:1,pageSize:30},form:{lists2:[]},rules:{},selectionRow:[]}},components:{},created:function(){var e=this;console.log("自定义表单"),console.log(".$route.path="+this.$route.path),Object(r["h"])(this.$route.name).then((function(t){e.formDesignId=t.data.id,e.formName=t.data.formName})),this.getList(),this.$nextTick((function(){e.resizeHandler(),window.addEventListener("resize",e.resizeHandler)}))},computed:{listData:function(){return this.$store.state.app.sidebar.opened}},watch:{listData:function(e){var t=this;setTimeout((function(){t.resizeHandler()}),300)}},methods:{resizeHandler:function(){var e=this.$refs.btns.offsetHeight,t=this.$refs.searchs.offsetHeight,l=e+t+18+45+84+36;l=l,this.tableHeight=document.documentElement.clientHeight-l+"px"},stateFormat:function(e,t,l){for(var s=this.lists,i=0;i<s.length;i++)if(s[i].fieldName==t.property)return this.selectDictLabel(s[i].options,l)},getList:function(){var e=this;this.loading=!0;var t={formDesignId:this.formDesignId};Object(a["c"])(t).then((function(t){console.log(t),e.lists=t.data,e.lists2=JSON.parse(JSON.stringify(e.lists)),e.form={lists2:e.lists2};for(var l=function(t){e.$set(e.lists[t],e.lists[t].fieldName,null),e.$set(e.lists[t],"val",null),"select"==e.lists[t].fieldType&&e.getDicts(e.lists[t].dictType).then((function(l){e.$set(e.lists[t],"options",l.data)})),"radio"==e.lists[t].fieldType&&(e.lists[t].options=JSON.parse(e.lists[t].options)),"checkbox"==e.lists[t].fieldType&&(e.lists[t].options=JSON.parse(e.lists[t].options),e.$set(e.lists[t],"val",[]))},s=0;s<e.lists.length;s++)l(s);for(var i=function(t){e.$set(e.lists2[t],e.lists2[t].fieldName,null),e.$set(e.lists2[t],"val",null),"select"==e.lists2[t].fieldType&&e.getDicts(e.lists2[t].dictType).then((function(l){e.$set(e.lists2[t],"options",l.data)})),"radio"==e.lists2[t].fieldType&&(e.lists2[t].options=JSON.parse(e.lists2[t].options)),"checkbox"==e.lists2[t].fieldType&&(e.lists2[t].options=JSON.parse(e.lists2[t].options),e.$set(e.lists2[t],"val",[]))},a=0;a<e.lists2.length;a++)i(a);for(var r=t.data,o=[],n=0;n<r.length;n++){var c={};c.label=r[n].fieldDesc,c.prop=r[n].fieldName,c.isList=r[n].isList,c.fieldType=r[n].fieldType,o.push(c)}e.cols=o,e.loading=!1,e.$set(e.queryParams,"formDesignId",e.formDesignId),e.getTable()}))},cancel:function(){this.open=!1,this.reset()},reset:function(){this.idd=null;for(var e=0;e<this.form.lists2.length;e++)this.form.lists2[e].val=null,"checkbox"==this.form.lists2[e].fieldType&&this.$set(this.form.lists2[e],"val",[]);this.resetForm("form")},handleQuery:function(){for(var e=this.lists,t=0;t<e.length;t++)this.queryParams[e[t].fieldName]=e[t].val;this.$set(this.queryParams,"formDesignId",this.formDesignId),this.queryParams.pageNum=1,this.queryParams.pageSize=30,console.log(this.queryParams),this.getTable()},getTable:function(){var e=this;console.log(this.queryParams),this.$set(this.queryParams,"formDesignId",this.formDesignId),Object(a["h"])(this.queryParams,this.formDesignId).then((function(t){console.log(t),e.formDesignList=t.rows,e.total=t.total}))},resetQuery:function(){for(var e=this.lists,t=0;t<e.length;t++)e[t].val=null,"checkbox"==e[t].fieldType&&this.$set(e[t],"val",[]);this.handleQuery()},handleSelectionChange:function(e){console.log(e),this.ids=e.map((function(e){return e.id})),this.single=1!==e.length,this.multiple=1!==e.length},rowClick:function(e,t,l){var s=this.$refs.multipleTable;this.selectionRow.find((function(t){return t.rowIndex==e.rowIndex}));s.clearSelection(),s.toggleRowSelection(e)},rowStyle:function(e){var t=e.row,l=e.rowIndex;Object.defineProperty(t,"rowIndex",{value:l,writable:!0,enumerable:!1})},rowClassName:function(e){var t=e.row,l=(e.rowIndex,""),s=this.selectionRow.find((function(e){return e.rowIndex===t.rowIndex}));return s&&(l="current-row "),l},handleAdd:function(){this.reset(),this.open=!0,this.addEditId="",this.title="添加"+this.formName},handleUpdate:function(e){var t=this;this.reset();var l=e.id||this.ids;this.addEditId=this.ids[0];var s=this.formDesignId;this.open=!0,this.title="编辑"+this.formName,Object(a["e"])(s,l).then((function(e){var l=e.data.datas;t.form={lists2:l},t.idd=e.data.id;for(var s=function(e){if(t.$set(t.form.lists2[e],"val",t.form.lists2[e].fieldValue),"select"==t.form.lists2[e].fieldType&&t.getDicts(t.form.lists2[e].dictType).then((function(l){t.$set(t.form.lists2[e],"options",l.data)})),"radio"==t.form.lists2[e].fieldType&&(t.form.lists2[e].options=JSON.parse(t.form.lists2[e].options)),"checkbox"==t.form.lists2[e].fieldType){t.form.lists2[e].options=JSON.parse(t.form.lists2[e].options);var l=t.form.lists2[e].fieldValue.split(",");t.$set(t.form.lists2[e],"val",l)}},i=0;i<t.form.lists2.length;i++)s(i);console.log(t.form.lists2),t.open=!0,t.title="编辑"+t.formName}))},submitForm:function(){for(var e=this,t=this.form.lists2,l=0;l<t.length;l++)this.form[t[l].fieldName]=t[l].val;this.$set(this.form,"formDesignId",this.formDesignId),this.$set(this.form,"id",this.idd),this.$refs["form"].validate((function(t){if(t){var l=JSON.parse(JSON.stringify(e.form));console.log(l),delete l.lists2,null!=e.form.id?Object(a["a"])(l,e.formDesignId).then((function(t){200==t.code&&(e.msgSuccess("编辑成功"),e.open=!1,e.getTable())})):Object(a["a"])(l,e.formDesignId).then((function(t){200==t.code&&(e.msgSuccess("新增成功"),e.open=!1,e.getTable())}))}}))},handleDelete:function(e){var t=this,l=e.id||this.ids,s=this.formDesignId;this.$confirm('是否确认删除自定义表单编号为"'+l+'"的数据项?',"警告",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){return Object(a["b"])(s,l)})).then((function(){t.getTable(),t.msgSuccess("删除成功")})).catch((function(){}))}}},n=o,c=l("2877"),u=Object(c["a"])(n,s,i,!1,null,"026fe82a",null);t["default"]=u.exports}}]);