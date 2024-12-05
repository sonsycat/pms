<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="70%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分摊编码" prop="apportCode">
          <el-input v-model="form.apportCode" placeholder="请输入分摊编码"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="科室编码" prop="deptCode">
          <el-input v-model="form.deptCode" placeholder="请输入科室编码"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="项目编码" prop="projectCode">
          <el-input v-model="form.projectCode" placeholder="请输入项目编码"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="分摊级别" prop="apportType">
          <el-select v-model="form.apportType" placeholder="请选择分摊级别">
            <el-option
              v-for="dict in apportTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分摊金额" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入分摊金额"  maxlength="50" show-word-limit  />
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
import { listApportDetail, getApportDetail, delApportDetail, addApportDetail, updateApportDetail, exportApportDetail } from "@/api/apport/apportDetail";

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
          this.title = "分摊明细管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "分摊明细管理-新增";
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
      title: "分摊明细管理-新增",
      openDialog: this.open,
      saveLoading: false,
      // 表单参数
      form: {},
      apportTypeOptions: [],
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("cost_apport_type").then(response => {
      this.apportTypeOptions = response.data;
    });
  },
  methods: {
  	getDetailInfo() {
      getApportDetail(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询分摊明细列表 */
    getList() {
      this.loading = true;
      listApportDetail(this.queryParams).then(response => {
        this.apportDetailList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 分摊级别字典翻译
    apportTypeFormat(row, column) {
      return this.selectDictLabel(this.apportTypeOptions, row.apportType);
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
        apportCode: null,
        deptCode: null,
        projectCode: null,
        apportType: null,
        amount: null,
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
            updateApportDetail(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              this.saveLoading = false;
            });
          } else {
            addApportDetail(this.form).then(response => {
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