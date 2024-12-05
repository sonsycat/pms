<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="40%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-col :span="12">
          <el-form-item label="分摊编码" prop="apportCode">
            <el-input v-model="form.apportCode" placeholder="请输入分摊编码"  maxlength="50" show-word-limit disabled />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="分摊年月" prop="nowDate">
            <el-date-picker disabled class="w100"
              v-model="form.nowDate"
              type="month"
              value-format="yyyy-MM"
              placeholder="选择年月">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <div shadow="always" >
            <el-button type="success" style="float:right" @click="submitForm" :loading="saveLoading">开始计算</el-button>
          </div>
        </el-col>
          <el-col :span="24" class="mb20" style="margin:10px 0 0">
            <el-progress v-if="progressShow" :text-inside="true" :stroke-width="20" :percentage="percentage"></el-progress>
          </el-col>
          <el-col :span="24" style="max-height:450px;overflow:auto;padding:10px 0">
            <el-timeline>
              <el-timeline-item
                v-for="(activity, index) in activities"
                :key="index"
                :icon="activity.icon"
                :type="activity.type"
                :size="activity.size"
              >
                <!-- :timestamp="activity.timestamp" -->
                {{activity.content}}
                <div v-if="!activity.flag" class="errorStyle">{{activity.timestamp}}</div>
                <div v-if="activity.flag" class="successStyle">{{activity.timestamp}}</div>
                <div v-if="activity.flag" class="successStyle">{{activity.idea1}}</div>
                <div v-if="activity.flag" class="successStyle">{{activity.idea2}}</div>
                <div v-if="activity.flag" class="successStyle">{{activity.idea3}}</div>
              </el-timeline-item>
            </el-timeline>
          </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { getCostApport } from "@/api/system/dict/data"
import { validEmail } from'@/utils/validate'
import { listApport, getApport, addApportFtjs,addApportYjftjs,addApportEjftjs,addApportSjftjs, addVerification1,addVerification2,addVerification3,addVerification } from "@/api/apport/apport";

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
    }
  },
  watch: {
    id: {
      handler(val) {
        this.reset();
        if (val) {
          this.id = val;
          this.title = "成本分摊 / 分摊计算";
          this.getDetailInfo();
        } else {
          this.form.apportType='1';
          this.id = "";
          this.title = "成本分摊 / 分摊计算";
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
      title: "分摊计算管理-新增",
      openDialog: this.open,
      saveLoading: false,
      // 表单参数
      form: {},
      apportTypeOptions: [],
      checkData:[],
      progressShow:false,
      activities: [],
      percentage:0,
	  	// 表单校验
      rules: {
        nowDate: [
          { required: true, message: "分摊年月不能为空", trigger: "blur" }
        ],
        apportType: [
          { required: true, message: "分摊级别不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("cost_apport_type").then(response => {
    });
    this.getCostApport()
  },
  methods: {
    //分摊计算弹框年月限制
    getCostApport() {
      getCostApport(this.$store.state.user.accountInfo.deptId).then(response => {
        if (response.data) {
          this.form.nowDate = response.data;
        } else {
          this.form.nowDate = getYear();
        }
      });
    },
  	getDetailInfo() {
      getApport(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询分摊计算列表 */
    getList() {
      this.loading = true;
      listApport(this.queryParams).then(response => {
        this.apportList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 分摊级别(保存最后的级别)字典翻译
    apportTypeFormat(row, column) {
      return this.selectDictLabel(this.apportTypeOptions, row.apportType);
    },
    //分摊计算的多选关联
    handlerChange(e,el){
      let arr=[]
      this.apportTypeOptions.forEach((ite,i)=>{
        if(el.target.checked){
          if(ite.dictValue<=el.target.value){
            arr.push(ite.dictValue)
          }
        }else{
          if(ite.dictValue<el.target.value){
            arr.push(ite.dictValue)
          }
        }
      })
      this.form.apportType=arr
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit("success", false);
      this.$emit("cancel", false);
    },
    // 取消
    cancel() {
      this.$emit("success", false);
      this.$emit("cancel", false);
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: this.$store.state.user.accountInfo.accountCode,
        apportCode: null,
        nowDate: null,
        apportType: [],
        forward: null,
        back: null,
        apportTime: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.saveLoading = true;
          this.activities = []
          this.progressShow = true
         // this.form.apportType=this.form.apportType.sort((a,b)=>{return b-a})[0]

            this.saveStepOne1(this.form).then(()=>{
            }).then(()=>{
              return this.saveStepOne2(this.form)
            }).then(()=>{
              return this.saveStepOne3(this.form)
            }).then(()=>{
              return this.saveStepTow(this.form)
            }).then(()=>{
              this.saveLoading = false;
            }).catch((res)=>{
              this.saveLoading = false;
            })

        }
      });
    },
    saveStepOne(data){
      return new Promise((reslove,reject)=>{
        addVerification1(data).then(response =>{
          if(response.code == 200){
            this.percentage = 15
            this.activities.push({
              content: '验证科室快照',
              timestamp: 'ok',
              size: 'large',
              type: 'success',
              icon: 'el-icon-check',
              flag:true
            })
            reslove()
          }else{
            reject(response)
          }
        }).catch((res)=>{
          this.percentage = 10
          this.activities.push({
            content: '验证科室快照',
            timestamp: 'Error',
            size: 'large',
            type: 'danger',
            color:"red",
            icon: 'el-icon-close',
            flag:false
          })
          reject(res)
        })
      })
    },
    saveStepOne1(data){
      return new Promise((reslove,reject)=>{
        addVerification2(data).then(response =>{
          if(response.code == 200){
            this.percentage = 30
            this.activities.push({
              content: '验证分摊方案',
              timestamp: 'ok',
              size: 'large',
              type: 'success',
              icon: 'el-icon-check',
              flag:true
            })
            reslove()
          }else{
            reject(response)
          }
        }).catch((res)=>{
          this.percentage = 20
          this.activities.push({
            content: '验证分摊方案',
            timestamp: 'Error',
            size: 'large',
            type: 'danger',
            color:"red",
            icon: 'el-icon-close',
            flag:false
          })
          reject(res)
        })
      })
    },
    saveStepOne2(data){
      return new Promise((reslove,reject)=>{
        addVerification3(data).then(response =>{
          if(response.code == 200){
            this.percentage = 45
            this.activities.push({
              content: '验证分摊比例',
              timestamp: 'ok',
              size: 'large',
              type: 'success',
              icon: 'el-icon-check',
              flag:true
            })
            reslove()
          }else{
            reject(response)
          }
        }).catch((res)=>{
          this.percentage = 30
          this.activities.push({
            content: '验证分摊比例',
            timestamp: 'Error',
            size: 'large',
            type: 'danger',
            color:"red",
            icon: 'el-icon-close',
            flag:false
          })
          reject(res)
        })
      })
    },
    saveStepOne3(data){
      return new Promise((reslove,reject)=>{
        addVerification(data).then(response =>{
          if(response.code == 200){
            this.percentage = 60
            this.activities.push({
              content: '验证成本数据',
              timestamp: 'ok',
              size: 'large',
              type: 'success',
              icon: 'el-icon-check',
              flag:true
            })
            reslove()
          }else{
            reject(response)
          }
        }).catch((res)=>{
          this.percentage = 40
          this.activities.push({
            content: '验证成本数据',
            timestamp: 'Error',
            size: 'large',
            type: 'danger',
            color:"red",
            icon: 'el-icon-close',
            flag:false
          })
          reject(res)
        })
      })
    },
    saveStepTow(data){
      return new Promise((reslove,reject)=>{
        addApportFtjs(data).then(response =>{
          this.form = response.data;
          if(response.code == 200){
            this.percentage = 100
            this.activities.push({
              content: '开始计算计入成本分摊数据',
              timestamp: 'ok',
              size: 'large',
              type: 'success',
              icon: 'el-icon-check',
              flag:true
            })
            reslove()
          }else{
            reject(response)
          }
        }).catch((res)=>{
          this.percentage = 50
          this.activities.push({
            content: '开始计算计入成本分摊数据',
            timestamp: 'Error',
            size: 'large',
            type: 'danger',
            color:"red",
            icon: 'el-icon-close',
            flag:false
          })
          reject(res)
        })
      })
    },
    saveStepTow1(data){
      return new Promise((reslove,reject)=>{
        addApportYjftjs(data).then(response =>{
          this.form = response.data;
          if(response.code == 200){
            this.percentage = 80
            this.activities.push({
              content: '开始计算一级分摊数据',
              timestamp: 'ok',
              idea1: '一级分摊数据计算完成',
              idea2: '分摊前金额'+this.form.forward,
              idea3: '分摊后金额'+this.form.back,
              size: 'large',
              type: 'success',
              icon: 'el-icon-check',
              flag:true
            })
            reslove()
          }else{
            reject(response)
          }
        }).catch((res)=>{
          this.percentage = 60
          this.activities.push({
            content: '开始计算一级分摊数据',
            timestamp: 'Error',
            size: 'large',
            type: 'danger',
            color:"red",
            icon: 'el-icon-close',
            flag:false
          })
          reject(res)
        })
      })
    },
    saveStepTow2(data){
      return new Promise((reslove,reject)=>{
        addApportEjftjs(data).then(response =>{
          this.form = response.data;
          if(response.code == 200){
            this.percentage = 90
            this.activities.push({
              content: '开始计算二级分摊数据',
              timestamp: 'ok',
              idea1: '二级分摊数据计算完成',
              idea2: '分摊前金额'+this.form.forward,
              idea3: '分摊后金额'+this.form.back,
              size: 'large',
              type: 'success',
              icon: 'el-icon-check',
              flag:true
            })
            reslove()
          }else{
            reject(response)
          }
        }).catch((res)=>{
          this.percentage = 70
          this.activities.push({
            content: '开始计算二级分摊数据',
            timestamp: 'Error',
            size: 'large',
            type: 'danger',
            color:"red",
            icon: 'el-icon-close',
            flag:false
          })
          reject(res)
        })
      })
    },
    saveStepTow3(data){
      return new Promise((reslove,reject)=>{
        addApportSjftjs(data).then(response =>{
          this.form = response.data;
          if(response.code == 200){
            this.percentage = 100
            this.activities.push({
              content: '开始计算三级分摊数据',
              timestamp: 'ok',
              idea1: '三级分摊数据计算完成',
              idea2: '分摊前金额'+this.form.forward,
              idea3: '分摊后金额'+this.form.back,
              size: 'large',
              type: 'success',
              icon: 'el-icon-check',
              flag:true
            })
            reslove()
          }else{
            reject(response)
          }
        }).catch((res)=>{
          this.percentage = 80
          this.activities.push({
            content: '开始计算三级分摊数据',
            timestamp: 'Error',
            size: 'large',
            type: 'danger',
            color:"red",
            icon: 'el-icon-close',
            flag:false
          })
          reject(res)
        })
      })
    },
}};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    min-height: 20%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
      padding: 30px 20px 0 !important;
    }
  }
</style>
