<template>
	<div class="dynamicForm">
	  <!--  高级定义和布局字段 我不需要就设置为空啦   :advance-fields = '[]'   :layout-fields = '[]'  -->
	  <fm-making-form 
	    ref="makingform" 
	    style="height:600px;display: block;" 
	    preview 
	    generate-code 
    	generate-json
	  >
	  <!-- 头部内容自定义，添加提交按钮 -->
	    <template slot="action">
	        <el-button type="primary" @click="handleSubmit">保存</el-button>
	    </template>
	  </fm-making-form>
	  
	  	<div class="list-wraper previewForm">
        	<fm-generate-form 
            	:data="jsonData" 
            	ref="generateForm">
        	</fm-generate-form>
        	<!--<el-button type="primary" @click="handleSubmit">提交</el-button>-->
		</div>
	</div>
</template>

<script>
import { saveDesign,getForm,getFormDesign } from '@/api/utils'	
export default {
    data(){
        return{
          formName:'',
          jsonData:{
          	list:[],
          },
          formDesignId:"",
          
        }
    },
    components:{},
    created() {
        this.formDesignId = this.$route.query.formDesignId
        let data = {
        	formDesignId:this.formDesignId
        }
        this.handleFormReady();
     getForm(data).then(response => {
     	console.log(response);
     	let jsons = JSON.parse(response.data.formJson)
     	console.log(jsons);
     	this.jsonData = jsons
     })
    },
    methods:{
		// 弹框关闭
		handleDialogClose() {
			this.$emit("cancel", false);
		},
    	handleFormReady () {
	        const formDesignId = this.formDesignId;
	        getFormDesign(formDesignId).then(response => {
	        	console.log(response)
	            const formJson = response.data.formJson;
	            if(formJson != null && formJson != '') {
	              this.$refs.makingform.setJSON(JSON.parse(formJson))
	            }
	        });
	    },
        handleSubmit () {
            const json = this.$refs.makingform.getJSON();
            saveDesign(this.formDesignId, json, this.$refs.makingform.getHtml()).then(response => {
        		console.log(response);
        		this.msgSuccess("保存成功");
        		this.close();
	        })
        },
        close() {
        	this.$store.dispatch('tagsView/delView', this.$route)
        	this.$router.push({ path:'/tool/formDesign', query: { t: Date.now() } })
      	},
    },
    
}
</script>

<style>
</style>