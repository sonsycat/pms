<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="40%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-col :span="12">
          <el-form-item label="生成年月" prop="occureTime">
            <el-date-picker class="w100"
              v-model="form.occureTime"
              type="month"
              value-format="yyyy-MM"
              placeholder="选择年月"
              @change="selectOccureTime"
              >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="24" :style="{marginBottom:'10px'}" >
          <div shadow="always" >
            <el-button icon="el-icon-plus" type="success" style="float:right" :disabled="unAllowAdd" @click="submitForm" :loading="loading">生成</el-button>
          </div>
        </el-col>
        <el-col :span="24" class="mb20">
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
              {{activity.content}}
              <div v-if="!activity.flag" class="errorStyle">{{activity.timestamp}}</div>
              <div v-if="activity.flag" class="successStyle">{{activity.timestamp}}</div>
              <!-- <div v-if="activity.flag" class="successStyle">{{activity.idea1}}</div> -->
              <!-- <div v-if="activity.flag" class="successStyle">{{activity.idea2}}</div>
              <div v-if="activity.flag" class="successStyle">{{activity.idea3}}</div> -->
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
import { resolveGenerateCheck1, resolveGenerateCheck2, resolveGenerate1, resolveGenerate2, resolveGenerate3, resolveGenerate4} from "@/api/revenue/apport";

export default {
  name: "addEdit",
  props: {
    open: {
      type: Boolean,
      default: false
    },
  },
  watch: {
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
      title: "收入数据生成-收入生成",
      openDialog: this.open,
      unAllowAdd:true,
      loading: false,
      progressShow:false,
      activities: [],
      percentage:0,
      // 表单参数
      form: {
        compCode:this.$store.state.user.accountInfo.deptId,
        occureTime:'',
      },
	  	// 表单校验
      rules: {
      }
    };
  },
  created() {
  },
  methods: {
    selectOccureTime(val){
      if(!val){
        this.unAllowAdd = true
      }else{
        this.unAllowAdd = false
      }
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit("cancel", false);
    },
    // 取消
    cancel() {
      this.$emit("cancel", false);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.loading = true;
          this.activities = []
          this.progressShow = true
          this.saveStepOne(this.form).then(()=>{
            return this.saveStepOne1(this.form)
          }).then(()=>{
            return this.saveStepOne2(this.form)
          }).then(()=>{
            return this.saveStepOne3(this.form)
          }).then(()=>{
            return this.saveStepTow(this.form)
          }).then(()=>{
            return this.saveStepTow1(this.form)
          }).then(()=>{
            this.loading = false;
          }).catch((res)=>{
            this.loading = false;
          })
        }
      });
    },
    saveStepOne(data){
      return new Promise((reslove,reject)=>{
        resolveGenerateCheck1(data).then(response =>{
          if(response.code == 200){
            this.percentage = 15
            this.activities.push({
              content: '验证是否有当前月数据',
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
            content: '验证是否有当前月数据',
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
        resolveGenerateCheck2(data).then(response =>{
          if(response.code == 200){
            this.percentage = 30
            this.activities.push({
              content: '验证当前是否有对应方案',
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
            content: '验证当前是否有对应方案',
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
        resolveGenerate1(data).then(response =>{
          if(response.code == 200){
            this.percentage = 45
            this.activities.push({
              content: '门诊收入分解',
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
            content: '门诊收入分解',
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
        resolveGenerate2(data).then(response =>{
          if(response.code == 200){
            this.percentage = 60
            this.activities.push({
              content: '门诊收入附加分解',
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
            content: '门诊收入附加分解',
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
        resolveGenerate3(data).then(response =>{
          if(response.code == 200){
            this.percentage = 75
            this.activities.push({
              content: '住院收入分解',
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
          this.percentage = 70
          this.activities.push({
            content: '住院收入分解',
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
        resolveGenerate4(data).then(response =>{
          if(response.code == 200){
            this.percentage = 100
            this.activities.push({
              content: '住院附加收入分解',
              timestamp: 'ok',
              // idea1: '住院附加收入数据计算完成',
              // idea2: '分解前金额'+this.form.forward,
              // idea3: '分解后金额'+this.form.back,
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
          this.percentage = 90
          this.activities.push({
            content: '住院附加收入分解',
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
    // saveStepTow2(data){
    //   return new Promise((reslove,reject)=>{
    //     addApportEjftjs(data).then(response =>{
    //       this.form = response.data;
    //       if(response.code == 200){
    //         this.percentage = 90
    //         this.activities.push({
    //           content: '折算成本',
    //           timestamp: 'ok',
    //           idea1: '折算成本数据计算完成',
    //           // idea2: '分解前金额'+this.form.forward,
    //           // idea3: '分解后金额'+this.form.back,
    //           size: 'large',
    //           type: 'success',
    //           icon: 'el-icon-check',
    //           flag:true
    //         })
    //         reslove()
    //       }else{
    //         reject(response)
    //       }
    //     }).catch((res)=>{
    //       this.percentage = 70
    //       this.activities.push({
    //         content: '折算成本',
    //         timestamp: 'Error',
    //         size: 'large',
    //         type: 'danger',
    //         color:"red",
    //         icon: 'el-icon-close',
    //         flag:false
    //       })
    //       reject(res)
    //     })
    //   })
    // },
    // saveStepTow3(data){
    //   return new Promise((reslove,reject)=>{
    //     addApportSjftjs(data).then(response =>{
    //       this.form = response.data;
    //       if(response.code == 200){
    //         this.percentage = 100
    //         this.activities.push({
    //           content: '完成生成',
    //           timestamp: 'ok',
    //           idea1: '数据计算完成',
    //           // idea2: '分解前金额'+this.form.forward,
    //           // idea3: '分解后金额'+this.form.back,
    //           size: 'large',
    //           type: 'success',
    //           icon: 'el-icon-check',
    //           flag:true
    //         })
    //         reslove()
    //       }else{
    //         reject(response)
    //       }
    //     }).catch((res)=>{
    //       this.percentage = 80
    //       this.activities.push({
    //         content: '完成生成',
    //         timestamp: 'Error',
    //         size: 'large',
    //         type: 'danger',
    //         color:"red",
    //         icon: 'el-icon-close',
    //         flag:false
    //       })
    //       reject(res)
    //     })
    //   })
    // },
}};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    min-height: 10%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
      padding: 30px 20px 0 !important;
    }
  }
</style>