<template>
	<div class="add_form_dialog">
		<el-dialog :title="title" :visible.sync="openDialog" width="80%" custom-class="el-button--primary" append-to-body :close-on-click-modal="true" @close="handleDialogClose">
			<div class="dynamicForm">
				<!--  高级定义和布局字段 我不需要就设置为空啦   :advance-fields = '[]'   :layout-fields = '[]'  -->
				<fm-making-form
					ref="makingform"
					style="height:700px;display: block;"
					preview
					generate-code
					generate-json
				>
				<!-- 头部内容自定义，添加提交按钮 -->
					<template slot="action">
						<el-button type="primary" icon="el-icon-upload" size="mini" @click="handleSubmit">保存</el-button>
					</template>
				</fm-making-form>
				<div class="list-wraper previewForm">
					<fm-generate-form
						:data="jsonData"
						ref="generateForm"
						>
					</fm-generate-form>
					<!-- <el-button type="primary" @click="handleSubmit">提交</el-button> -->
				</div>
			</div>
		</el-dialog>
	</div>
</template>

<script>
import { saveDesign,getForm,getFormDesign } from '@/api/utils'
export default {
	name:'addForm',
	props: {
		open: {
			type: Boolean,
			default: false
		},
		formMarkingId: {
			type: String,
			default: ""
		}
	},
	watch: {
		formMarkingId: {
			handler(val) {
				if (val) {
					this.formMarkingId = val;
					this.title = "模板管理-编辑模板-模板字段-新增";
				}
			},
			deep: true,
			immediate: true
		},
		open: {
			handler(val) {
				this.openDialog = val;
			},
			deep: true,
			immediate: true
		}
  	},
    data(){
        return{
		  openDialog:false,
		  title:'模板管理-编辑模板-模板字段-新增',
          formName:'',
          jsonData:{
          	list:[],
		  },
		  list:[],
        }
    },
    components:{},
    created() {
		this.$nextTick(()=>{
			this.handleFormReady();
		})
    },
    methods:{
		// 弹框关闭
		handleDialogClose() {
			this.$emit("cancel", false);
		},
    	handleFormReady () {
	        const formDesignId = this.formMarkingId;
	        getFormDesign(formDesignId).then(response => {
	            const formJson = response.data.formJson;
	            if(formJson != null && formJson != '') {
					let data=JSON.parse(formJson);
					data.size="mini"
					data.list.forEach(_=>{
						_.size="mini";
						_.options.disabled=_.options.disabled=="false"?false:true;
						_.options.required=_.options.required=="false"?false:true;
						_.columns && _.columns.forEach(ite=>{
							ite.span=ite.span * 1;
							ite.list.forEach(item=>{
								item.size="mini";
								item.options.disabled=item.options.disabled=="false"?false:true;
								item.options.required=item.options.required=="false"?false:true;
							})
						})
					})
					// console.log(data,'data')
					try {
						this.$refs.makingform.setJSON(data)
					} catch (d) {
						console.log(d)
					}
	            }
	        });
	    },
		saveData(){
			let saveLoading=this.saveLoading()
				const json = this.$refs.makingform.getJSON();
				saveDesign(this.formMarkingId, json, this.$refs.makingform.getHtml()).then((res)=>{
					if(res.code==200){
					this.msgSuccess("保存成功");
					this.handleDialogClose();
					saveLoading.close()
					}
				})
		},
        handleSubmit () {
			this.$confirm('是否确认覆盖原有配置数据?', "警告", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning"
			}).then(()=> {
				return this.saveData()
			}).catch(() => {});

        },
        // close() {
        // 	this.$store.dispatch('tagsView/delView', this.$route)
        // 	this.$router.push({ path:'/tool/formDesign', query: { t: Date.now() } })
      	// },
    },

}
</script>

<style lang="scss" scoped>
.add_form_dialog{
	::v-deep .el-dialog {
		min-height: 10%;
		overflow: hidden;
		.el-dialog__body {
			max-height: auto !important;
			overflow: auto;
		}
	}
}
.dynamicForm{
	::v-deep .el-aside{
		width: 310px !important;
		&.widget-config-container{
			width: 350px !important;
			.el-form-item__content{
				margin-left: 0 !important
			}
		}
	}
}
</style>
