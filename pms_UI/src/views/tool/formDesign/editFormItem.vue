<template>
    <div class="app-container">
        <el-dialog :title="title" :visible.sync="openDialog" width="40%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
            <el-form ref="form" :model="form" :rules="rules" class="edit_form" >
                <el-row>
                    <el-col :span="14" style="margin-left:18%">
                        <el-form-item label="字段名称：" prop="fieldDesc">
                            <el-input class="w100" v-model="form.fieldDesc" placeholder="请输入模板名称" readonly/>
                        </el-form-item>
                        <el-form-item label="字段类型：" prop="pmsType">
                            <el-select class="w100" v-model="form.pmsType" filterable clearable remote placeholder="请选择字段类型" @change="changeType">
                                <el-option
                                    v-for="dict in typeListOption"
                                    :key="dict.dictValue"
                                    :label="dict.dictLabel"
                                    :value="dict.dictValue"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <p class="lit-title" >字段属性</p>
                    </el-col>
                    <el-col :span="14" style="margin-left:18%">

                        <el-form-item label="说明：" prop="explain">
                            <el-input class="w100" v-model="form.explain" type="textarea" rows="4" placeholder="请输入说明" />
                        </el-form-item>
                        <el-form-item v-if="selectType!==5" label="是否必填：" prop="isNotNull">
                            <el-radio-group class="w100" v-model="form.isNotNull">
                                <el-radio label="1">是</el-radio>
                                <el-radio label="0">否</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item v-if="selectType==1 || selectType==9" label="是否多行文本框：" prop="isTextareaLines">
                            <el-radio-group class="w100" v-model="form.isTextareaLines">
                                <el-radio label="1">是</el-radio>
                                <el-radio label="0">否</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item v-if="selectType==1 || selectType==9" label="文本框行数：" prop="textLines">
                            <el-input-number class="w100" controls-position="right" :min="0" v-model="form.textLines" placeholder="请输入文本框行数" />
                        </el-form-item>
                        <el-form-item v-if="selectType==1 || selectType==9" label="最大长度：" prop="fieldLength">
                            <el-input-number class="w100" controls-position="right" :min="0" v-model="form.fieldLength" placeholder="请输入最大长度" />
                        </el-form-item>
                        <el-form-item v-if="selectType==2" label="最大值：" prop="maxValue">
                            <el-input-number class="w100" controls-position="right" v-model="form.maxValue" placeholder="请输入最大值" />
                        </el-form-item>
                        <el-form-item v-if="selectType==2" label="最小值：" prop="minValue">
                            <el-input-number class="w100" controls-position="right" v-model="form.minValue" placeholder="请输入最小值" />
                        </el-form-item>
                        <el-form-item v-if="selectType==2" label="小数位数：" prop="decimlDigit">
                            <el-input-number class="w100" controls-position="right" :min="0" v-model="form.decimlDigit" placeholder="请输入小数位数" />
                        </el-form-item>
                        <el-form-item v-if="selectType==3" label="待选项：" prop="dictType">
                             <el-select class="w100" v-model="form.dictType" filterable clearable remote placeholder="请选择待选项" @change="changeType">
                                <el-option
                                    v-for="dict in selectDictData"
                                    :key="dict.dictValue"
                                    :label="dict.dictLabel"
                                    :value="dict.dictValue"
                                ></el-option>
                                </el-select>
                        </el-form-item>
                        <el-form-item v-if="selectType==3" label="选择样式：" prop="options">
                            <el-select class="w100" v-model="form.options" filterable clearable placeholder="请选择样式">
                                <el-option label="下拉" value="1">下拉</el-option>
                                <el-option label="单选" value="2">单选</el-option>
                                <el-option label="复选" value="3">复选</el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item v-if="selectType==5" label="质量考核指标" prop="indexCode">
                            <el-select class="w100" v-model="form.indexCode" filterable clearable remote placeholder="请选择质量考核指标">
                                <el-option
                                    v-for="dict in indexLists"
                                    :key="dict.indexCode"
                                    :label="dict.indexName"
                                    :value="dict.indexCode"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item v-if="selectType==2 || selectType==1 || selectType==9 ||selectType==3" label="默认值：" prop="defaultValue">
                            <el-input class="w100" v-model="form.defaultValue" placeholder="请输入默认值" />
                        </el-form-item>
                        <el-form-item class="w100" v-if="selectType==2" label="是否为百分比：" prop="isPercente">
                            <el-radio-group v-model="form.isPercente">
                                <el-radio label="0">是</el-radio>
                                <el-radio label="1">否</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item class="w100" v-if="selectType==4" label="默认值：" prop="dateDefaultValue">
                            <el-radio-group v-model="form.dateDefaultValue" @change="changDefaultRadioNum" >
                                <el-radio v-for="item in defaultValueOption" :key="item.dictValue" :label="item.dictLabel" class="vertical_radio"></el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <!--   -->
                        <el-form-item v-if="selectType==4" prop="defaultValue">
                            <el-date-picker class="w100" type="date" placeholder="年/月/日" v-model="form.defaultValue" style="width: 100%;" @change="changeDateValue" :disabled="isEditDate" ></el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">保 存</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import {editTableItem,selectDictType,listForTemplate,getDeptInfoForPms} from "@/api/formDesign/formDesign";
import { resolve } from 'url';
export default {
    name: 'editFormItem',
    props: {
      open: {
        type: Boolean,
        default: false
        },
      queryParams: {
        type: Object,
        default: []
      }
    },
    watch: {
        queryParams:{
            handler(val){
                if(this.queryParams.textLines<=1){
                    this.queryParams.isTextareaLines='0';
                }else{
                    this.queryParams.isTextareaLines='1'
                }
                this.form=this.queryParams;
                this.form.dateDefaultValue=this.form.dateDefaultValue=='1'?'当前日期':this.form.dateDefaultValue;
                this.form.dateDefaultValue=this.form.dateDefaultValue=='2'?'指定日期':this.form.dateDefaultValue;
                this.form.dateDefaultValue=this.form.dateDefaultValue=='0'?'无':this.form.dateDefaultValue;
                this.form.isPercente='1';
                this.selectType=this.form.pmsType;
                if(this.form.dateDefaultValue){
                    if(this.form.dateDefaultValue=='无'){
                        this.isEditDate=true;
                    }else if(this.form.dateDefaultValue=='当前日期'){
                        this.isEditDate=true;
                    }else{
                        this.isEditDate=false;
                    }
                }else{
                    this.isEditDate=true;
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
    data() {
        return {
            title:"模板管理-编辑模板-模板字段-编辑",
            isEditDate:false,
            form:{},
            rules:{
                fieldDesc: [
                    { required: true, message: "字段名称不能为空", trigger: "blur" }
                ],
                pmsType: [
                    { required: true, message: "标题不能为空", trigger: "blur" }
                ],
                textLines:[
                    { required: true, message: "文本框行数不能为空", trigger: "blur" }
                ],
                fieldLength:[
                    { required: true, message: "最大长度不能为空", trigger: "blur" }
                ],
                maxValue:[
                    { required: true, message: "最大值不能为空", trigger: "blur" }
                ],
                 minValue:[
                    { required: true, message: "最小值不能为空", trigger: "blur" }
                ],
                decimlDigit:[
                    { required: true, message: "小数位数不能为空", trigger: "blur" }
                ],
                isPercente:[
                    { required: true, message: "请选择是否为百分比", trigger: "blur" }
                ],
                dictType:[
                     { required: true, message: "请选择待选项", trigger: "blur" }
                ],
                options:[
                    { required: true, message: "请选择样式", trigger: "blur" }
                ],
                indexCode:[
                    { required: true, message: "请选择质量考核指标", trigger: "blur" }
                ]
            },
            selectType:null,
            //字段类型字典
            typeListOption:[],
            defaultValueOption:[],
            selectDictData:[],
            indexLists:[],
        };
    },
    created(){
        //字段类型字段翻译
      this.getDicts("pms_ysmb_zdlx").then(response => {
          let arr=[]
          if(this.form.fieldName=="cs_target"){
              this.typeListOption = response.data.filter(_=>{return _.dictLabel=="指标"});
          }else{
              let index=response.data.findIndex((_=>_.dictLabel=="指标"))
              response.data.splice(index,1);
              this.typeListOption=response.data;
          }
      });
      //默认值类型
      this.getDicts("pms_mbrq_default").then(response=>{
          this.defaultValueOption = response.data;
      })
      this.selectDictType();
      this.listForTemplate()
    },
    mounted() {
        
    },

    methods: {
        // 字段类型字典翻译
      typeListFormat(row, column) {
        return this.selectDictLabel(this.typeListOption, row.apportType);
      },
      // 字段类型字典翻译
     defaultValueFormat(row, column) {
        return this.selectDictLabel(this.defaultValueOption, row.apportType);
      },
      //待选项
      selectDictType(){
          if(this.form.fieldName=="cs_dept"){
              let params= {
                  compCode:this.$store.state.user.userInfo.deptId
              }
              getDeptInfoForPms(params).then(res=>{
                  if(res.code===200){
                      this.selectDictData=res.data;
                  }
              })
            }else{
                selectDictType().then(response=>{
                    if(response.code===200){
                        this.selectDictData=response.data;
                    }
                })
            }
      },
      //质量考评指标
      listForTemplate(value){
          let params={
              indexName:value || '',
              compCode:this.$store.state.user.userInfo.deptId   //必传
          }
          listForTemplate(params).then(response=>{
              if(response.code===200){
                  this.indexLists=response.data
              }
          })
      },
      changeType(val){
          this.selectType=val;
          this.queryParams.isTextareaLines=null;
          this.form.defaultValue=null;
      },
      changDefaultRadioNum(val){
            if(val=='指定日期'){
                if(!this.form.defaultValue){
                    this.rules.defaultValue=[
                        { required: true, message: "请选择指定日期", trigger: "blur" }
                    ]
                }
                this.isEditDate=false;
            }else if(val=='当前日期'){
                this.isEditDate=true;
                this.form.defaultValue=new Date();
                this.rules.defaultValue=[{ required: false, message: "", trigger: "blur" }]
            }else{
                this.isEditDate=true;
                this.form.defaultValue=null;
                this.rules.defaultValue=[{ required: false, message: "", trigger: "blur" }]
            }
      },
      changeDateValue(val){
          if(!val){
              this.form.dateDefaultValue='无'
          }
      },
      // 弹框关闭
      handleDialogClose() {    
        this.selectType=null;    
        this.$emit("cancel", false);
      },
      cancel(){
        this.selectType=null;
        this.reset();
        this.$emit("cancel", false);
      },
      reset(){
          this.form={
            id: null,
            fieldDesc: null,
            pmsType: null,
            createBy: null,
            createDate: null,
            updateBy: null,
            updateDate: null,
            remark: null,
            textLines: null,
            fieldLength: null,
            isEdit:null,
            isList:null,
            isListQuery:null,
            isNotNull:null,
            isPercente:null,
            isQuery:null,
            isSort:null,
            isSum:null,
            indexCode:null,
            maxValue: null,
            minValue: null,
            decimlDigit: null,
            options:null,
            options:null,
            dictType:null,
            indexCode:null,
            defaultValue:null,
            defaultValue1:null,
            defaultDate:null,
            pageNum:null,
          }
          this.resetForm("form");
      },
      submitForm(){
          delete this.form.isTextareaLines
          if(this.form.defaultValue && this.form.pmsType==4){
              this.form.defaultValue=this.moment(this.form.defaultValue).format('YYYY-MM-DD')
          }
          if(this.form.dateDefaultValue=='当前日期'){
              this.form.dateDefaultValue='1'
          }else if(this.form.dateDefaultValue=='指定日期'){
              this.form.dateDefaultValue='2'
          }else{
               this.form.dateDefaultValue='0'
          }
          this.$refs["form"].validate(valid => {
            if (valid) {
                let saveLoading=this.saveLoading()
                editTableItem(this.form).then(res=>{
                    if(res.code==200){
                        this.msgSuccess("编辑成功");
                        this.$emit("cancel", false);
                        saveLoading.close()
                    }
                    
                })

            }
          })
      }
    },
};
</script>

<style lang="scss" scoped>
.lit-title{
    font-size: 16px;
    padding-bottom: 8px;
    border-bottom: 1px dashed #cccccc;
}
.edit_form{
  ::v-deep{
     .el-input-number--mini{
        width: 100%;
      }
      .el-form-item__label{
        width: 140px !important;
      }
      .el-form-item__content{
        margin-left: 140px !important;
      }
  }
}
.vertical_radio{
    display: block;
    margin: 10px 0;
    &:last-child{
        margin-bottom: 0;
    }
}
</style>