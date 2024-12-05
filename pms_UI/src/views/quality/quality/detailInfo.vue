<template>
 <el-dialog :title="title"  :visible.sync="openDialog" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules">
        <el-row>
	    		<template v-for="(item,i) in form.lists2">
		      		<template v-if="item.fieldType=='input'">
	      				<template v-if="item.isEdit=='1'">
	      					<template v-if="item.validType==null">
                    <el-col :span="item.span">
                      <el-form-item
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'"
                      v-if="item.isNotNull=='1'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-input disabled class="w80" :maxlength="item.fieldLength" v-model="item.val" placeholder="请输入"/>
                    </el-form-item>
                    <el-form-item
                        :label="item.fieldDesc"
                        v-else-if="item.isNotNull!='1'"
                        :prop="'lists2.' + i + '.val'"
                        >
                        <el-input disabled class="w80" :maxlength="item.fieldLength" v-model="item.val" placeholder="请输入" />
                      </el-form-item>
                    </el-col>
	      					</template>
	      					<template v-else-if="item.validType=='email'">
                    <el-col :span="item.span">
                      <el-form-item
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'"
                      :rules="[
                        { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                        { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
                      ]"
                  >
                      <el-input disabled class="w80" v-model="item.val" />
                    </el-form-item>
                    </el-col>
				      		</template>
			      		</template>
		      		</template>

		      		<template v-else-if="item.fieldType=='select'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <template slot-scope="scope">
                        <el-tooltip :content="item.val" placement="top" v-if="item.fieldName=='cs_target'"> 
                          <el-select disabled v-model="item.val" clearable filterable remote class="w80" no-data-text>
                            <el-option
                                v-for="dict in item.options"
                                :key="dict.dictValue || dict.id"
                                :label="dict.dictLabel || dict.evaCont"
                                :value="dict.dictValue || dict.id"
                              >
                              </el-option>
                            </el-select>
                        </el-tooltip>
                        <el-select v-else disabled v-model="item.val" clearable filterable remote class="w80" no-data-text>
                            <el-option
                                v-for="dict in item.options"
                                :key="dict.dictValue || dict.id"
                                :label="dict.dictLabel || dict.evaCont"
                                :value="dict.dictValue || dict.id"
                              >
                              </el-option>
                            </el-select>
                      </template>
                    </el-form-item>
                    <el-form-item
                      v-else-if="item.isNotNull!='1'"
                      :label="item.fieldDesc"
                    :prop="'lists2.' + i + '.val'"
                      >
											<el-tooltip :content="item.val" placement="top" v-if="item.fieldName=='cs_target'"> 
                        <el-select disabled clearable filterable remote v-model="item.val" class="w80" no-data-text>
                          <el-option
                              v-for="dict in item.options"
                              :key="dict.dictValue || dict.id"
                              :label="dict.dictLabel || dict.evaCont"
                              :value="dict.dictValue || dict.id"
                            >
                            </el-option>
                          </el-select>
												</el-tooltip>
                        <el-select v-else disabled clearable filterable remote v-model="item.val" class="w80" no-data-text>
                          <el-option
                              v-for="dict in item.options"
                              :key="dict.dictValue || dict.id"
                              :label="dict.dictLabel || dict.evaCont"
                              :value="dict.dictValue || dict.id"
                            >
                            </el-option>
                          </el-select>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='date'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-date-picker
                        disabled
                        v-model="item.val"
                        class="w80"
                        value-format="yyyy-MM-dd"
                      ></el-date-picker>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-date-picker
                        disabled
                        v-model="item.val"
                        class="w80"
                        value-format="yyyy-MM-dd"
                      ></el-date-picker>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='time'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-date-picker
                      disabled
                      v-model="item.val"
                      class="w80"
                      type="datetime"
                      value-format="yyyy-MM-dd HH:mm:ss"
                    ></el-date-picker>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-date-picker
                      disabled
                      v-model="item.val"
                      class="w80"
                      type="datetime"
                      value-format="yyyy-MM-dd HH:mm:ss"
                    ></el-date-picker>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='textarea'">
                <!-- v-if="item.isEdit=='1'" -->
			      		<template >
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-input disabled class="w90" :maxlength="item.fieldLength" type="textarea" :rows="item.textLines" v-model="item.val" show-word-limit placeholder="请输入"></el-input>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-input disabled class="w90" :maxlength="item.fieldLength" type="textarea" :rows="item.textLines" v-model="item.val" show-word-limit placeholder="请输入"></el-input>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='number'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-input-number disabled class="w80" controls-position="right" :precision="item.decimlDigit *1" :min="item.minValue * 1" :max="item.maxValue * 1" size="mini" v-model="item.val"></el-input-number>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-input-number disabled class="w80" controls-position="right" :precision="item.decimlDigit *1" :min="item.minValue * 1" :max="item.maxValue * 1" size="mini" v-model="item.val"></el-input-number>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='rate'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-rate disabled v-model="item.val" class="w80"></el-rate>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-rate disabled v-model="item.val" class="w80"></el-rate>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='color'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-color-picker class="w80" v-model="item.val"></el-color-picker>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-color-picker disabled class="w80" v-model="item.val"></el-color-picker>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='switch'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-switch disabled v-model="item.val" active-value="打开" inactive-value="关闭"></el-switch>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-switch disabled v-model="item.val" active-value="打开" inactive-value="关闭"></el-switch>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='radio'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-radio-group disabled v-model="item.val">
                        <template v-for="(ite,ii) in item.options">
                          <el-radio :label="ite.label">{{ite.value}}</el-radio>
                        </template>
                      </el-radio-group>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-radio-group disabled v-model="item.val">
                        <template v-for="(ite,ii) in item.options">
                          <el-radio :label="ite.label">{{ite.value}}</el-radio>
                        </template>
                      </el-radio-group>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='checkbox'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                    <el-checkbox-group disabled v-model="item.val">
                      <template v-for="(ite,ii) in item.options">
                        <el-checkbox :label="ite.value"></el-checkbox>
                      </template>
                    </el-checkbox-group>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-checkbox-group disabled v-model="item.val">
                        <template v-for="(ite,ii) in item.options">
                          <el-checkbox :label="ite.value"></el-checkbox>
                        </template>
                    </el-checkbox-group>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else>
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'"
                      v-if="item.isNotNull=='1'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}"
                    >
                      <el-input disabled :maxlength="item.fieldLength" v-model="item.val" />
                    </el-form-item>
                    <el-form-item
                        :label="item.fieldDesc"
                        v-else
                      :prop="'lists2.' + i + '.val'"
                        >
                        <el-input disabled :maxlength="item.fieldLength" v-model="item.val" />
                      </el-form-item>
                  </el-col>
			      		</template>
			      	</template>
	      		</template>
    		</el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import { getFormDetails} from '@/api/utils';
import { listDetail } from "@/api/quality/detail";
import {getDeptInfoForPms} from "@/api/formDesign/formDesign";

export default {
  name: "addEdit",
  props: {
    open: {
      type: Boolean,
      default: false
    },
    id: {
      type: [String, Number],
      default: ""
    },
    ids:{
      type: [String, Number],
      default: ""
    }
  },
  watch: {
    id: {
      handler(val) {
        this.reset();
        if (val) {
          this.id = val;
          this.title = "查看详情 / 质量得分 / 得分明细 / 详细信息";
          this.getDetailInfo();
        } 
      },
      deep: true,
      immediate: true
    },
    open: {
      handler(val) {
        this.openDialog = val;
      },
      deep: true
    }
  },
  components: {
  },
  data() {
    return {
      title: "查看详情 / 质量得分 / 得分明细 / 详细信息",
      openDialog: this.open,
      saveLoading: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      queryParams:{},
      qualityList:[],
      loading:false,
    };
  },
  created() {

  },
  methods: {
  	getDetailInfo() {
      getFormDetails(this.id,this.ids).then(response=>{
          if(response.code===200){
            // console.log(response,'res')
            let lists = response.data.datas;
            this.form = {
	        		lists2:lists
            }
            let lists2Length=this.form.lists2.filter(_=>{return _.fieldName=="dept_track"});
						// console.log(this.form.lists2,'form.lists2')
	      		for(let i=0;i<this.form.lists2.length;i++){
							this.$set(this.form.lists2[i],"val", this.form.lists2[i].fieldValue )
  					if(this.form.lists2[i].fieldType=="select"){
							//指标详情数据
							if(this.form.lists2[i].fieldName=="cs_target"){
									this.$set(this.form.lists2[i],"val", null )
								let params={
											indexCode:this.form.lists2[i].indexCode,
											compCode:this.$store.state.user.userInfo.deptId  // 必传
								};
								listDetail(params).then(response=>{
										if(response.code===200){
											this.$set( this.form.lists2[i], "options",response.rows );
											let	targetValue=response.rows.filter(_=>{
												if(_.id==this.form.lists2[i].fieldValue || _.evaCont==this.form.lists2[i].fieldValue){
													return _
												}
											});
											this.$set(this.form.lists2[i],"val", targetValue[0].evaCont )
										}
								})
							}else if(this.form.lists2[i].fieldName=="cs_dept"){
								//部门详情数据
									let params= {
											compCode:this.$store.state.user.userInfo.deptId
										}
										getDeptInfoForPms(params).then(res=>{
												if(res.code===200){
														this.$set( this.form.lists2[i], "options",res.data )
												}
										})
							}else{
								this.getDicts(this.form.lists2[i].dictType).then(response => {
			        		this.$set(this.form.lists2[i], "options", response.data )
				      	});
							}
						}
  					if(this.form.lists2[i].fieldType=="radio"){
	        			this.form.lists2[i].options = JSON.parse(this.form.lists2[i].options)
	        		}
  					if(this.form.lists2[i].fieldType=="checkbox"){
	        			this.form.lists2[i].options = JSON.parse(this.form.lists2[i].options)
	        			let arr = this.form.lists2[i].fieldValue.split(',');
	        			this.$set(this.form.lists2[i],"val",arr)
							}
						}
						// console.log(this.selectionRow[0],'row')
						// console.log(this.form.lists2,'bianji')
          }
        })
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit("cancel", false);
    },
    // 取消
    cancel() {
      this.$emit("cancel", false);
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: null,
        accountCode: null,
        deptCode: null,
        indexCode: null,
        tempCode: null,
        indexScore: null,
        occureTime: null,
        actualScore: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
}};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    min-height: 10%;
    height: auto !important;
    max-height: 80%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>