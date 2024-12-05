<template>
     <el-dialog :title="title" :visible.sync="openExtract" width="50%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
     <el-form ref="form" :model="form" :rules="rules" label-width="80px">
         <el-col :span="12">
          <el-form-item label="SQL类型:" prop="sqlType">
            <el-select v-model="form.sqlType" :remote="isRemote" @change="changeSqlType" @click="changeSqlType" placeholder="请选择SQL类型">
              <el-option
                v-for="dict in statusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="数据源:"  prop="coonectCode" >
          <el-select v-model="form.coonectCode" placeholder="请选择数据源" >
            <el-option
                v-for="dict in coonectOptions"
                :key="dict.code"
                :label="dict.name"
                :value="dict.code"
              ></el-option>
          </el-select>
        </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="年月:" prop="nowDate">
                <el-date-picker :clearable="false"
                    v-model="form.nowDate"
                    type="month"
                    value-format="yyyy-MM"
                    placeholder="选择年月"
                    >
                </el-date-picker>
            </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="分摊SQL:" prop="numSql">
                <el-input :style="{width:'94%'}" v-model="form.numSql" @change="changeNumSql" @input="changeNumSql" type="textarea" rows="10" placeholder="请输入内容" />
            </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" :loading="saveLoading" >保存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { listDatabase } from "@/api/apportionment/database";
import { searchForSQL, extractForProject, beforeExtractProject} from "@/api/project/num";
import { extractForProjectInCome,beforeExtract} from "@/api/revenue/revenuenum";

export default {
    name: 'extractForm',
    props: {
        open: {
            type: Boolean,
            default: false
        },
        id: {
            type: [String, Number],
            default: ""
        },
        queryForm:{
            type: [Object,String],
            default: ""
        },
        isInCome:{
            type:Boolean,
            default:false
        }
    },
    watch:{
        open: {
            handler(val) {
                this.openExtract = val;
            },
            deep: true
        },
    },
    data() {
        return {
            title: this.isInCome?'成本提取':'收入提取',
            openExtract: this.open,
            saveLoading:false,
            isRemote:true,
            coonectOptions: [],//数据源
            statusOptions:[],//sql数据类型
            form: {},//表单参数
            deptTypeOptions:[],
            ratioTypeOptions:[],
            rules:{
                coonectCode: [
                    { required: true, message: "数据源不能为空", trigger: "change" }
                ],
                 nowDate: [
                    { required: true, message: "当前年月不能为空", trigger: "blur" }
                ],
                sqlType: [
                    { required: true, message: "请选择成本类型", trigger: "blur" }
                ],
                numSql: [
                    { required: true, message: "请输入分摊sql内容", trigger: "blur" }
                ],
            }
        };
    },
    created(){
        this.getBeforeList();
        this.getList();
        this.getDicts("cost_sql_type").then(response => {
            this.statusOptions = response.data;
        });
    },
    mounted() {

    },

    methods: {
        getBeforeList(){
            let query={
                comeCode:this.$store.state.user.accountInfo.deptId,
            }
            if(this.isInCome){
               beforeExtractProject(query).then(response=>{
                   if(response.code===200){
                        this.form.sqlType=response.rows[0].sqlType
                        this.form.numSql=response.rows[0].numSql
                   }
                })
            }else{
                beforeExtract(query).then(response=>{
                    if(response.code===200){
                        this.form.sqlType=response.rows[0].sqlType
                        this.form.numSql=response.rows[0].numSql
                    }
                })
            }

        },
        getList() {
            //数据源获取
            listDatabase(this.queryParams).then(response => {
                this.coonectOptions = response.rows;
            });
        },
        changeSqlType(e){
            this.reset(1)
            this.form.sqlType=e
        },
        changeNumSql(e){
            this.reset(2)
            this.form.numSql=e
        },
         cancel() {
            this.$emit("cancel", false);
        },
         // 弹框关闭
        handleDialogClose() {
            this.$emit("cancel", false);
            this.resetForm('form')
        },
        reset(type){
            if(type==1){
                this.form={
                    sqlType: "",
                    numSql:this.form.numSql
                }
            }else{
                this.form={
                    sqlType:this.form.sqlType,
                    numSql: "",
                }
            }

        },
        submitForm(){
             this.$refs["form"].validate(valid => {
                 if(valid){
                     let costProjectNumSql={
                        compCode:this.$store.state.user.userInfo.deptId,
                        ...this.form
                    }
                    if (this.isInCome) {
                        extractForProject(costProjectNumSql).then(response=>{
                            if(response.code===200){
                                this.msgSuccess('成本提取成功')
                                this.$emit("cancel", false);
                            }
                        })
                    }else{
                        extractForProjectInCome(costProjectNumSql).then(response=>{
                            if(response.code===200){
                                this.msgSuccess('收入提取成功')
                                this.$emit("cancel", false);
                            }
                        })
                    }
                 }
             })
        }
    },
};
</script>

<style lang="scss" scoped>
  ::v-deep .el-dialog {
    min-height: 20%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
    .el-input--mini{
        width:300px
    }
  }
</style>
