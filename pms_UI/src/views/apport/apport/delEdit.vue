<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="40%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-col :span="12">
          <el-form-item label="分摊编码" prop="apportCode">
            <el-input v-model="form.apportCode" placeholder="请输入分摊编码"  maxlength="50" show-word-limit disabled />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="分摊年月" prop="nowDate">
            <el-date-picker disabled size="small" class="w100"
              v-model="form.nowDate"
              type="month"
              value-format="yyyy-MM"
              placeholder="选择年月">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="分摊级别" prop="apportType">
            <el-checkbox-group v-model="form.apportType">
              <el-checkbox
                v-for="dict in apportTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictValue"
                :value="dict.dictValue"
                :disabled="dict.bool"
                @change="handlerChange"
              >{{dict.dictLabel}}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" >确 定</el-button>        
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { getCostApport } from "@/api/system/dict/data"
import { validEmail } from'@/utils/validate'
import { listApport, getApport, addApportFtjs,addApportYjftjs,addApportEjftjs,addApportSjftjs, addVerification1,addVerification2,addVerification3,addVerification,delApport } from "@/api/apport/apport";

export default {
  name: "delEdit",
  props: {
    open: {
      type: Boolean,
      default: false
    },
    id: {
      type: [String, Number],
      default: ""
    },
    delData:{
      type:Object,
      default:null
    }
  },
  watch: {
    id: {
      handler(val) {
        this.reset();
        if (val) {
          this.id = val;
          this.form=this.delData;
          this.apportType=this.delData.apportType
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
      title: "成本分摊-删除",
      openDialog: this.open,
      saveLoading: false,
      apportType:"",
      // 表单参数
      form: {
      },
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
    this.getDicts("cost_apport_type").then(response => {
      let arr=[]
        this.apportTypeOptions = response.data.map(_=>{
          if(_.dictValue<=this.delData.apportType && _.dictValue!=1){
           arr.push(_.dictValue)
           _.bool=false
          }else{
            _.bool=true
          }
          return _
        })
        this.form.apportType=arr
    });
  },
  methods: {
    // 分摊级别(保存最后的级别)字典翻译
    apportTypeFormat(row, column) {
      return this.selectDictLabel(this.apportTypeOptions, row.apportType);
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
    //分摊计算的多选关联
    handlerChange(e,el){
      let arr=[]
      this.apportTypeOptions.forEach((ite,i)=>{
        if(el.target.checked){
          if(ite.dictValue <= this.apportType && ite.dictValue!=1){
            arr.push(ite.dictValue)
          }
        }else{
          if(ite.dictValue<el.target.value && ite.dictValue!=1 && ite.dictValue <= this.apportType){
            arr.push(ite.dictValue)            
          }
        }
      })
      this.form.apportType=arr
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let apportType=this.form.apportType.sort((a,b)=>{return a-b})[0]
          let ids=[this.id,apportType]
          this.saveLoading = true;
          this.$confirm('是否确认删除分摊计算编号为"' + this.id + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delApport(ids);
        }).then(() => {
          this.msgSuccess("删除成功");  
          this.$emit("success", false);    
        }).catch(() => {});
        }
      });
    },
}
};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    height: 30%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
