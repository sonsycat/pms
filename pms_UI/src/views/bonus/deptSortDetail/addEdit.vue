<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="40%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="类别编码" prop="sortCode">
          <el-input v-model="form.sortCode" placeholder="系统自动生成" readonly maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="类别名称" prop="sortName">
          <el-input v-model="form.sortName" placeholder="请输入类别名称" maxlength="50" show-word-limit  />
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
import { listDeptSortDetail, getDeptSortDetail, delDeptSortDetail, addDeptSortDetail, updateDeptSortDetail, exportDeptSortDetail } from "@/api/bonus/deptSortDetail";

export default {
  name: "addEdit",
  props: {
    openEdit: {
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
          this.title = "核算单位类别管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "核算单位类别管理-新增";
        }
      },
      deep: true,
      immediate: true
    },
    openEdit: {
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
      title: "核算单位类别管理-新增",
      openDialog: this.openEdit,
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
      getDeptSortDetail(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询核算单位类别列表 */
    getList() {
      this.loading = true;
      listDeptSortDetail(this.queryParams).then(response => {
        this.deptSortDetailList = response.rows;
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
        sortCode: null,
        sortName: null,
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
            updateDeptSortDetail(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              this.saveLoading = false;
            }).catch((err)=>{
              this.saveLoading = false;
            });
          } else {
            addDeptSortDetail(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.$emit("success", false);
              this.saveLoading = false;
            }).catch((err)=>{
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
    height: 222px!important;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px)!important;
      overflow: auto;
    }
  }
</style>
