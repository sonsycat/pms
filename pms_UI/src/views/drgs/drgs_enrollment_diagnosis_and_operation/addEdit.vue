<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="70%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="MDC编码" prop="mdcCode">
          <el-input v-model="form.mdcCode" placeholder="请输入MDC编码"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="MDC名称" prop="mdcName">
          <el-input v-model="form.mdcName" placeholder="请输入MDC名称"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="ADRG编码" prop="adrgCode">
          <el-input v-model="form.adrgCode" placeholder="请输入ADRG编码"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="ADRG名称" prop="adrgName">
          <el-input v-model="form.adrgName" placeholder="请输入ADRG名称"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="ICD编码" prop="icdCode">
          <el-input v-model="form.icdCode" placeholder="请输入ICD编码"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="ICD名称" prop="icdName">
          <el-input v-model="form.icdName" placeholder="请输入ICD名称"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="ICD类型" prop="icdType">
          <el-select v-model="form.icdType" placeholder="请选择ICD类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="组合号" prop="combinationNo">
          <el-input v-model="form.combinationNo" placeholder="请输入组合号"  maxlength="10" show-word-limit  />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" :loading="saveLoading">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from '@/utils/validate'
import { listDrgs, getDrgs, delDrgs, addDrgs, updateDrgs, exportDrgs } from "@/api/drgs/drgs_enrollment_diagnosis_and_operation";

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
          this.title = "ADRG入组诊断及操作管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "ADRG入组诊断及操作管理-新增";
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
      title: "ADRG入组诊断及操作管理-新增",
      openDialog: this.open,
      saveLoading: false,
      // 表单参数
      form: {},
      	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
  	getDetailInfo() {
      getDrgs(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询ADRG入组诊断及操作列表 */
    getList() {
      this.loading = true;
      listDrgs(this.queryParams).then(response => {
        this.drgsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        mdcCode: null,
        mdcName: null,
        adrgCode: null,
        adrgName: null,
        icdCode: null,
        icdName: null,
        icdType: null,
        combinationNo: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        delFlag: null
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
        this.saveLoading = true;
          if (this.form.id != null) {
            updateDrgs(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              this.saveLoading = false;
            });
          } else {
            addDrgs(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.$emit("success", false);
              this.saveLoading = false;
            });
          }
        }
      });
    },
}};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    height: 80%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
