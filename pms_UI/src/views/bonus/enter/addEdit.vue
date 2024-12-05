<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="时间" prop="enterDate">
          <el-input v-model="form.enterDate" placeholder="系统自动生成"  maxlength="50" show-word-limit disabled />
        </el-form-item>
        <el-form-item label="科室编码" prop="deptCode">
          <el-input v-model="form.deptCode" placeholder="请输入科室编码"  maxlength="50" show-word-limit  disabled/>
        </el-form-item>

        <el-form-item label="科室名称" prop="deptName">
          <el-input v-model="form.deptName" placeholder="请输入科室名称"  maxlength="50" show-word-limit disabled />
        </el-form-item>
        <el-form-item label="员工姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入员工姓名"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="奖金绩效" prop="achievement">
          <el-input-number controls-position="right" v-model="form.achievement" :precision="2" placeholder="请输入奖金绩效"
                           class="w100" show-word-limit  />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea"  />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" >确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>

import { listEnter, getEnter, addEnter, updateEnter } from "@/api/bonus/enter";

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
          this.title = "奖金导入-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "奖金导入-新增";
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
      title: "奖金导入-新增",
      openDialog: this.open,
      // 表单参数
      form: {},
      	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
      rules: {
        deptCode: [
          { required: true, message: "科室编码不能为空", trigger: "blur" }
        ],
        deptName: [
          { required: true, message: "科室名称不能为空", trigger: "blur" }
        ],
        userName: [
          { required: true, message: "员工姓名不能为空", trigger: "blur" }
        ],
        achievement: [
          { required: true, message: "奖金绩效不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    // this.getList();
  },
  methods: {
  	getDetailInfo() {
      getEnter(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询HIS收入类别列表 */
    getList() {
      this.loading = true;
      listEnter(this.queryParams).then(response => {
        this.incomeList = response.rows;
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
        compCode: this.$store.state.user.accountInfo.deptId,
        deptCode: null,
        deptName: null,
        userName: null,
        achievement: null,
        enterDate: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
        let saveLoading = this.saveLoading();
          if (this.form.id != null) {
            updateEnter(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              saveLoading.close();
            });
          } else {
            addEnter(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.$emit("success", false);
              saveLoading.close();
            });
          }
        }
      });
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
    }
  }
</style>
