<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="收入类别编码" prop="revenueCode">
              <el-input v-model="form.revenueCode" placeholder="系统自动生成"  maxlength="50" show-word-limit disabled />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="收入类别名称" prop="revenueName">
              <el-input v-model="form.revenueName" placeholder="请输入收入类别名称"  maxlength="100" show-word-limit @input="handleBlurPY" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="拼音码" prop="spell">
              <el-input v-model="form.spell" placeholder="系统自动生成"  maxlength="30" show-word-limit disabled />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" >确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import { listIncome, getIncome, delIncome, addIncome, updateIncome, exportIncome } from "@/api/basic/income";
import pinyin from "js-pinyin";

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
          this.title = "HIS收入类别管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "HIS收入类别管理-新增";
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
      title: "HIS收入类别管理-新增",
      openDialog: this.open,
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
      getIncome(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询HIS收入类别列表 */
    getList() {
      this.loading = true;
      listIncome(this.queryParams).then(response => {
        this.incomeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handleBlurPY() {
      this.form.spell = pinyin.getCamelChars(this.form.revenueName).toUpperCase();
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
        accountCode: null,
        revenueCode: null,
        revenueName: null,
        spell: null,
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
        let saveLoading = this.saveLoading();
          if (this.form.id != null) {
            updateIncome(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              saveLoading.close();
            });
          } else {
            addIncome(this.form).then(response => {
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
