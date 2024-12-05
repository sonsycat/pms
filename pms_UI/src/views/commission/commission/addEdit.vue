<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="70%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="科室编码" prop="deptCode">
          <el-input v-model="form.deptCode" placeholder="请输入科室编码"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="单位编码" prop="compCode">
          <el-input v-model="form.compCode" placeholder="请输入单位编码"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="科室名称" prop="deptName">
          <el-input v-model="form.deptName" placeholder="请输入科室名称"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="账套编码" prop="accountCode">
          <el-input v-model="form.accountCode" placeholder="请输入账套编码"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="类别" prop="sortCode">
          <el-input v-model="form.sortCode" placeholder="请输入类别"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="比例" prop="ratio">
          <el-input v-model="form.ratio" placeholder="请输入比例"  maxlength="20,2" show-word-limit  />
        </el-form-item>
        <el-form-item label="删除标记(0未删除,1已删除)" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标记(0未删除,1已删除)"  maxlength="1" show-word-limit  />
        </el-form-item>
        <el-form-item label="核算日期,年月" prop="commissionDate">
          <el-date-picker clearable size="small"
            v-model="form.commissionDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择核算日期,年月">
          </el-date-picker>
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
import { listCommission, getCommission, delCommission, addCommission, updateCommission, exportCommission } from "@/api/commission/commission";

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
          this.title = "独立核算提成比管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "独立核算提成比管理-新增";
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
      title: "独立核算提成比管理-新增",
      openDialog: this.open,
      saveLoading: false,
      // 表单参数
      form: {},
      	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
      rules: {
        commissionDate: [
          { required: true, message: "核算日期,年月不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
  	getDetailInfo() {
      getCommission(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询独立核算提成比列表 */
    getList() {
      this.loading = true;
      listCommission(this.queryParams).then(response => {
        this.commissionList = response.rows;
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
        deptCode: null,
        compCode: null,
        deptName: null,
        accountCode: null,
        sortCode: null,
        ratio: null,
        delFlag: null,
        commissionDate: null,
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
            updateCommission(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              this.saveLoading = false;
            });
          } else {
            addCommission(this.form).then(response => {
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