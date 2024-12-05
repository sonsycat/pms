<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="70%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="单位编码" prop="compCode">
          <el-input v-model="form.compCode" placeholder="请输入单位编码"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="账套编码" prop="accountCode">
          <el-input v-model="form.accountCode" placeholder="请输入账套编码"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="科室编码" prop="deptCode">
          <el-input v-model="form.deptCode" placeholder="请输入科室编码"  maxlength="100" show-word-limit  />
        </el-form-item>
        <el-form-item label="年月" prop="nowDate">
          <el-date-picker clearable size="small"
            v-model="form.nowDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审核状态" prop="auditState">
          <el-input v-model="form.auditState" placeholder="请输入审核状态"  maxlength="1" show-word-limit  />
        </el-form-item>
        <el-form-item label="流程id" prop="procedureId">
          <el-input v-model="form.procedureId" placeholder="请输入流程id"  maxlength="64" show-word-limit  />
        </el-form-item>
        <el-form-item label="删除标记(0未删除,1已删除)" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标记(0未删除,1已删除)"  maxlength="1" show-word-limit  />
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
import { validEmail } from'@/utils/validate'
import { listAudit, getAudit, delAudit, addAudit, updateAudit, exportAudit } from "@/api/bonus/audit";

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
          this.title = "奖金人员审核管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "奖金人员审核管理-新增";
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
      title: "奖金人员审核管理-新增",
      openDialog: this.open,
      saveLoading: false,
      // 表单参数
      form: {},
      	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
      rules: {
        auditState: [
          { required: true, message: "审核状态不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
  	getDetailInfo() {
      getAudit(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询奖金人员审核列表 */
    getList() {
      this.loading = true;
      listAudit(this.queryParams).then(response => {
        this.auditList = response.rows;
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
        compCode: null,
        accountCode: null,
        deptCode: null,
        nowDate: null,
        auditState: null,
        procedureId: null,
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
          if (this.form.id != null) {
            updateAudit(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              this.saveLoading = false;
            });
          } else {
            addAudit(this.form).then(response => {
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