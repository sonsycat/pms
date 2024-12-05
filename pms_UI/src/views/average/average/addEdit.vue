<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设置年月" prop="setDate">
          <el-date-picker clearable size="small" style="width: 80%;" :disabled="true"
            v-model="form.setDate"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择设置年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="录入时间" prop="setTime">
          <el-date-picker clearable size="small" style="width: 80%;" :disabled="true"
            v-model="form.setTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择录入时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="录入人" prop="setPerson">
          <el-input v-model="form.setPerson" placeholder="请输入录入人" style="width: 80%;" size="small" maxlength="50" show-word-limit  />
        </el-form-item>
<!--        <el-form-item label="删除标记(0未删除,1已删除)" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标记(0未删除,1已删除)"  maxlength="1" show-word-limit  />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="平均奖科人数编码" prop="code">
          <el-input v-model="form.code" placeholder="请输入平均奖科人数编码"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="单位编码" prop="compCode">
          <el-input v-model="form.compCode" placeholder="请输入单位编码"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="账套编码" prop="accountCode">
          <el-input v-model="form.accountCode" placeholder="请输入账套编码"  maxlength="50" show-word-limit  />
        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" :loading="saveLoading">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import { listAverage, getAverage, delAverage, addAverage, updateAverage, exportAverage } from "@/api/average/average";
import { getYear } from '@/utils/date'

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
    },
    addEditSetDate: {
      type: [String],
      default: ""
    }
  },
  watch: {
    id: {
      handler(val) {
        this.reset();
        if (val) {
          this.id = val;
          this.title = "平均奖科人数管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "平均奖科人数管理-新增";
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
    },
    addEditSetDate: {
      handler(val){
        this.setDate = val;
      },
      deep: true
    }

  },
  components: {
  },
  data() {
    return {
      title: "平均奖科人数管理-新增",
      openDialog: this.open,
      saveLoading: false,
      // 表单参数
      form: {},
      	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
      rules: {
        setDate: [
          { required: true, message: "设置年月不能为空", trigger: "blur" }
        ],
        setTime: [
          { required: true, message: "录入时间不能为空", trigger: "blur" }
        ],
        setPerson: [
          { required: true, message: "录入人不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    let newDate = this.addEditSetDate;
    this.form.setDate = newDate;
    this.form.setTime = getYear();
  },
  methods: {
  	getDetailInfo() {
      getAverage(this.id).then(response => {
        this.form = response.data;
      });
    },
    /** 查询平均奖科人数列表 */
    getList() {
      this.loading = true;
      listAverage(this.queryParams).then(response => {
        this.averageList = response.rows;
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
        setDate: null,
        setTime: null,
        setPerson: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        code: null,
        compCode: null,
        accountCode: null
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
        this.saveLoading = true;
          if (this.form.id != null) {
            updateAverage(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              this.saveLoading = false;
            });
          } else {
            addAverage(this.form).then(response => {
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
    height: 30%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
