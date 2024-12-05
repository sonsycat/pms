<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="编码" prop="revenueCode">
          <el-input v-model="form.revenueCode" placeholder="系统自动生成"  maxlength="50" show-word-limit disabled />
        </el-form-item>
        <el-form-item label="调整类型" prop="revenueName">
          <el-select v-model="form.revenueName" placeholder="请选择调整类型" class="w100">
            <el-option
              v-for="dict in revenueNameOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker clearable size="small" class="w100"
            v-model="form.startTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable size="small" class="w100"
            v-model="form.endTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="目标年月" prop="targetMonth">
          <el-date-picker clearable size="small" class="w100"
            v-model="form.targetMonth"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择目标年月">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import { listDate, getDate, delDate, addDate, updateDate, exportDate } from "@/api/update/date";

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
          this.title = "更新日期管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "更新日期管理-新增";
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
      title: "更新日期管理-新增",
      openDialog: this.open,
      // 表单参数
      form: {},
      	  	  	  	  	  	  	  	  	  		  revenueNameOptions: [],
	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
      rules: {
        revenueName: [
          { required: true, message: "调整类型不能为空", trigger: "blur" },
          { required: true, message: "调整类型不能为空", trigger: "change" }
        ],
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" },
          { required: true, message: "开始时间不能为空", trigger: "change" }
        ],
        endTime: [
          { required: true, message: "结束时间不能为空", trigger: "blur" },
          { required: true, message: "结束时间不能为空", trigger: "change" }
        ],
        targetMonth: [
          { required: true, message: "目标年月不能为空", trigger: "blur" },
          { required: true, message: "目标年月不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("REVENUE_NAME").then(response => {
      this.revenueNameOptions = response.data;
    });
  },
  methods: {
  	getDetailInfo() {
      getDate(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询更新日期列表 */
    getList() {
      this.loading = true;
      listDate(this.queryParams).then(response => {
        this.dateList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 调整类型字典翻译
    revenueNameFormat(row, column) {
      return this.selectDictLabel(this.revenueNameOptions, row.revenueName);
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
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: null,
        revenueCode: null,
        revenueName: null,
        startTime: null,
        endTime: null,
        targetMonth: null,
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
            updateDate(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              saveLoading.close();
            });
          } else {
            addDate(this.form).then(response => {
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
    min-height: 10%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
