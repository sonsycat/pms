(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-e574c10e"],{"18da":function(t,e,n){"use strict";n.d(e,"c",(function(){return r})),n.d(e,"f",(function(){return s})),n.d(e,"h",(function(){return i})),n.d(e,"e",(function(){return a})),n.d(e,"a",(function(){return c})),n.d(e,"b",(function(){return u})),n.d(e,"g",(function(){return m})),n.d(e,"d",(function(){return d}));var o=n("b775");function r(t){return Object(o["a"])({url:"/pmsFormDesignField/getFormConfig",method:"get",params:t})}function s(t){return Object(o["a"])({url:"/pmsFormDesignField/save",method:"post",data:t})}function i(t,e){return Object(o["a"])({url:"/pmsFormProcess/list?formDesignId="+e,method:"post",data:t})}function a(t,e){return Object(o["a"])({url:"/pmsFormProcess/getInfo/"+t+"/"+e,method:"get"})}function c(t,e){return Object(o["a"])({url:"/pmsFormProcess/save?formDesignId="+e,method:"post",data:t})}function u(t,e){return Object(o["a"])({url:"/pmsFormProcess/delete/"+t+"/"+e,method:"delete"})}function m(t,e,n){return Object(o["a"])({url:"/formDesign/pmsFormDesign/saveDesign?fdId="+t,method:"post",data:{formJson:e,formHtml:n}})}function d(t){return Object(o["a"])({url:"/formDesign/pmsFormDesign/"+t,method:"get"})}},"1b11":function(t,e,n){},df56:function(t,e,n){"use strict";n.r(e);var o=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"dynamicForm"},[n("fm-making-form",{ref:"makingform",staticStyle:{height:"600px",display:"block"},attrs:{preview:"","generate-code":"","generate-json":""}},[n("template",{slot:"action"},[n("el-button",{attrs:{type:"primary"},on:{click:t.handleSubmit}},[t._v("保存")])],1)],2),n("div",{staticClass:"list-wraper previewForm"},[n("fm-generate-form",{ref:"generateForm",attrs:{data:t.jsonData}})],1)],1)},r=[],s=(n("d3b7"),n("159b"),n("18da")),i={data:function(){return{formName:"",jsonData:{list:[]},formDesignId:""}},components:{},created:function(){this.formDesignId=this.$route.query.formDesignId;this.formDesignId;this.handleFormReady()},methods:{handleFormReady:function(){var t=this,e=this.formDesignId;Object(s["d"])(e).then((function(e){console.log(e);var n=e.data.formJson;if(null!=n&&""!=n){var o=JSON.parse(n);o.size="mini",o.list.forEach((function(t){t.size="mini",t.options.disabled="false"!=t.options.disabled,t.options.required="false"!=t.options.required,t.columns&&t.columns.forEach((function(t){t.span=1*t.span,t.list.forEach((function(t){t.size="mini",t.options.disabled="false"!=t.options.disabled,t.options.required="false"!=t.options.required}))}))}));try{t.$refs.makingform.setJSON(o)}catch(r){console.log(r)}}}))},handleSubmit:function(){var t=this,e=this.$refs.makingform.getJSON(),n=this.saveLoading();Object(s["g"])(this.formDesignId,e,this.$refs.makingform.getHtml()).then((function(e){t.msgSuccess("保存成功"),n.close(),t.close()}))},close:function(){this.$store.dispatch("tagsView/delView",this.$route),this.$router.push({path:"/clbt/CLBTZDY/CLZDYBD",query:{t:Date.now()}})}}},a=i,c=(n("e871"),n("2877")),u=Object(c["a"])(a,o,r,!1,null,"3404b20f",null);e["default"]=u.exports},e871:function(t,e,n){"use strict";n("1b11")}}]);