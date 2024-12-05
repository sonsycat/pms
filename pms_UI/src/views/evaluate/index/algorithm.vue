<template>
<!--  <div>-->
    <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="指标代码" prop="indexCode">
              <el-input class="w90" v-model="form.indexCode" placeholder="系统自动生成指标代码" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="指标名称" prop="indexName">
              <el-input class="w90" v-model="form.indexName" placeholder="指标名称"  maxlength="100" show-word-limit disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否区间指标" prop="isSection">
              <el-select class="w90" v-model="form.isSection" placeholder="请选择是否区间指标" @change="changeSection">
                <el-option
                  v-for="dict in isSectionOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据源:" prop="dbCode" >
              <el-select class="w90" v-model="form.dbCode" placeholder="请选择数据源" >
                <el-option
                  v-for="dict in dbOptions"
                  :key="dict.code"
                  :label="dict.name"
                  :value="dict.code"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="指标算法sql" prop="indexSql">
              <el-input v-model="form.indexSql" type="textarea" :rows="10" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="指标数据从" prop="dateTimeRange" v-show="form.isSection=='0'">
              <el-date-picker
                v-model="dateTimeRange"
                type="daterange"
                value-format="yyyy-MM-dd"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="" prop="" v-show="form.isSection!='0'">

            </el-form-item>
          </el-col>
<!--          <el-col :span="12">
            <el-form-item label="">
              <el-button type="success" @click="showDataFun">生成数据</el-button>
            </el-form-item>
          </el-col>-->
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
<!--        <el-button type="success" @click="showDataFun">生成数据</el-button>-->
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

<!--  </div>-->
</template>

<script>
import { listDatabase } from "@/api/apportionment/database";
import { validEmail } from'@/utils/validate';
import {
  listIndex,
  getIndex,
  delIndex,
  addIndex,
  updateIndex,
  exportIndex,
  createDataFromSql
} from "@/api/evaluate/index";
import Editor from '@/components/Editor';
import {Message} from "element-ui";

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
    addIndexName:{
      type:String,
      default:""
    },

  },
  watch: {
    id: {
      handler(val) {
        if (val) {
          this.id = val;
          this.getDetailInfo();
        } else {
          this.id = "";
          this.form.indexName=this.addIndexName
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
    Editor,

  },
  data() {
    return {
      title: "指标管理-指标算法",
      openDialog: this.open,
      dateTimeRange:[],
      dbOptions: [],//数据源
      // 表单参数
      form: {

      }, 	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
      rules: {
        isSection:[
          {required: true, message: "是否区间指标不能为空", trigger: ["blur","change"]}
        ],
        indexSql:[
          {required: true, message: "指标算法Spl不能为空", trigger: "blur"}
        ],
        dbCode:[
          {required: true, message: "请选择数据源", trigger: ["blur","change"]}
        ]
      },
      isSectionOptions:[],

    };
  },
  created() {
    this.getList()
    //是否字典类型
    this.getDicts("hr_is_change").then(response => {
      this.isSectionOptions = response.data;
    });
  },
  methods: {
    getList() {
      //数据源获取
      listDatabase().then(response => {
        this.dbOptions = response.rows;
      });
    },
    getDetailInfo() {
      getIndex(this.id).then(response => {
        this.form = response.data;
        this.form.isSection=this.form.isSection?this.form.isSection:'1';
        if(this.form.isSection==0){
          this.$set(this.dateTimeRange,0,this.form.sectionStart)
          this.$set(this.dateTimeRange,1,this.form.sectionEnd)
        }else{
          this.$set(this.dateTimeRange,0,'')
          this.$set(this.dateTimeRange,1,'')
        }
      });
    },
    showDataFun(){
      this.$refs["form"].validate(valid => {
        if(valid){
          this.form.sectionStart=this.dateTimeRange[0]||null;
          this.form.sectionEnd=this.dateTimeRange[1]||null;
          let params = {
            id:this.id,
            orgType:this.form.orgType,
            indexCode:this.form.indexCode,
            dbCode:this.form.dbCode,
            indexSql:this.form.indexSql,
            isSection:this.form.isSection,
            sectionStart:this.form.sectionStart,
            sectionEnd:this.form.sectionEnd
          }
          this.$emit('showData',params);
          /*createDataFromSql(params).then(response => {
            if (response.code === 200) {
              this.msgSuccess(response.msg)
            } else {
              reject(response.msg);
            }
          }).catch((err)=>{
            reject(err);
          });*/
        }else{
          this.msgError("请先填写数据")
        }
      })
    },
    setIndicatorExp(){
      this.$emit("openIndExp")
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit("cancel", false);
    },
    // 取消
    cancel() {
      this.$emit("cancel", false);
    },
    changeSection(val){
      if(val==1){
        this.dateTimeRange=[]
      }
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: null,
        accountCode: null,
        bscOneCode: null,
        bscSecondCode: null,
        deptType: null,
        orgType: null,
        indexCode: null,
        indexName: null,
        isExpress: null,
        formulaCode: null,
        formulaName: null,
        isSection: '1',
        indexSql: null,
        sectionStart: '',
        sectionEnd: '',
        isCouns: null,
        isStop: null,
        isComp: null,
        isAbsolute: null,
        indexType: null,
        isRegular: null,
        indexRatio: null,
        indexContent: null,
        indexSort: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        dbCode:null,
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      if(this.form.isSection==0&&this.dateTimeRange.length==0){
        this.rules.dateTimeRange=[
          {required: true, message: "指标数据时间区间不能为空", trigger: "blur"}
        ]
      }else{
        this.rules.dateTimeRange=[
          {message: "",}
        ]
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.sectionStart=this.dateTimeRange[0]||null;
          this.form.sectionEnd=this.dateTimeRange[1]||null;
          if(!this.id){
            this.$emit("success", this.form);
          }else{
            let saveLoading=this.saveLoading()
            updateIndex(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              saveLoading.close()
            });
          }

        }
      });
    },
  }};
</script>
<style lang="scss" scoped>
::v-deep .el-dialog {
  max-height: 52% !important;
  overflow: hidden;
  .el-dialog__body {
    height: calc(100% - 100px);
    overflow: auto;
    .lit-title{
      font-size: 16px;
      padding-bottom: 8px;
      border-bottom: 1px dashed #cccccc;
    }
  }
}
</style>
