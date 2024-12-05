<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="日期" prop="adjustTime">
          <el-date-picker clearable size="small" class="w100"
                          v-model="form.adjustTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择调整日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="科室" prop="deptCode">
          <el-select class="w100" v-model="form.deptCode" filterable clearable placeholder="请选择科室" >
            <el-option
              v-for="dict in deptList"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调整类型" prop="adjustType">
          <el-select v-model="form.adjustType" placeholder="请选择调整类型" class="w100">
            <el-option
              v-for="dict in adjustTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调整金额" prop="adjustAmount">
          <el-input-number controls-position="right" v-model="form.adjustAmount" :precision="2" placeholder="请输入调整金额" class="w100" show-word-limit  />
        </el-form-item>
        <el-form-item label="调整说明" prop="adjustReason">
          <el-input v-model="form.adjustReason" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="调整方向" prop="adjustDirection">
          <el-select v-model="form.adjustDirection" placeholder="请选择调整方向" class="w100">
            <el-option
              v-for="dict in adjustDirectionOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" >确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import { listBenefit, getBenefit, delBenefit, addBenefit, updateBenefit, exportBenefit } from "@/api/subsidy/benefit";
import {getYear} from "@/utils/date";

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
    "$store.state.dicts": {
      handler(val) {
        // 基础字典
        let baseDicts = val.baseDicts;
        this.deptList = val.deptInfo;
      },
      deep: true,
      immediate: true
    },
    id: {
      handler(val) {
        this.reset();
        if (val) {
          this.id = val;
          this.title = "科室效益调整管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "科室效益调整管理-新增";
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
      title: "科室效益调整管理-新增",
      openDialog: this.open,
      // 表单参数
      form: {},
      adjustTypeOptions: [],
      adjustDirectionOptions: [],
      //科室字典
      deptList: [],
      // 表单校验
      rules: {
        adjustType: [
          { required: true, message: "调整类型不能为空", trigger: "blur" }
        ],
        adjustDirection: [
          { required: true, message: "调整方向不能为空", trigger: "blur" }
        ],
        deptCode: [
          { required: true, message: "科室编码不能为空", trigger: "blur" }
        ],
        adjustAmount: [
          { required: true, message: "调整金额不能为空", trigger: "blur" }
        ],
        adjustTime: [
          { required: true, message: "调整时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("ADJUST_TYPE").then(response => {
      this.adjustTypeOptions = response.data;
    });
    this.getDicts("ADJUST_DIRECTION").then(response => {
      this.adjustDirectionOptions = response.data;
    });
  },
  methods: {
  	getDetailInfo() {
      getBenefit(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询科室效益调整列表 */
    getList() {
      this.loading = true;
      listBenefit(this.queryParams).then(response => {
        this.benefitList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 调整类型(1成本2收入)字典翻译
    adjustTypeFormat(row, column) {
      return this.selectDictLabel(this.adjustTypeOptions, row.adjustType);
    },
    // 调整方向(1地方2军队)字典翻译
    adjustDirectionFormat(row, column) {
      return this.selectDictLabel(this.adjustDirectionOptions, row.adjustDirection);
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
        deptCode: null,
        adjustTime: getYear(),
        adjustType: null,
        adjustAmount: null,
        adjustReason: null,
        adjustDirection: null,
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
            updateBenefit(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              saveLoading.close();
            });
          } else {
            addBenefit(this.form).then(response => {
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
    height: 50%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
