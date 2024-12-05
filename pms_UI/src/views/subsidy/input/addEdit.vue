<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="奖惩时间" prop="rewardTime">
          <el-date-picker clearable size="small" class="w100"
                          v-model="form.rewardTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择奖励时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="奖惩科室" prop="deptCode">
          <el-select class="w100" v-model="form.deptCode" filterable clearable placeholder="请选择奖惩科室" >
            <el-option
              v-for="dict in deptList"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="项目金额" prop="amount">
          <el-input-number controls-position="right" v-model="form.amount" :precision="2" placeholder="请输入项目金额" class="w100" show-word-limit  />
        </el-form-item>
        <el-form-item label="奖惩项目" prop="rewardCode">
          <el-select class="w100" v-model="form.rewardCode" filterable clearable placeholder="请选择奖惩项目" @change="changeReward" >
            <el-option
              v-for="dict in rewardOptions"
              :key="dict.rewardCode"
              :label="dict.rewardName"
              :value="dict.rewardCode"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="奖惩标准/原因" prop="rewardReason">
          <el-input v-model="form.rewardReason" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
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
import { listInput, getInput, delInput, addInput, updateInput, exportInput } from "@/api/subsidy/input";
import { listReward} from "@/api/subsidy/reward";
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
          this.title = "单项奖惩录入管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "单项奖惩录入管理-新增";
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
      title: "单项奖惩录入管理-新增",
      openDialog: this.open,
      // 表单参数
      form: {},
      rewardTypeOptions: [],
      //科室字典
      deptList: [],
      //项目字典
      rewardOptions: [],
      // 表单校验
      rules: {
        rewardCode: [
          { required: true, message: "项目编码不能为空", trigger: "blur" }
        ],
        deptCode: [
          { required: true, message: "科室编码不能为空", trigger: "blur" }
        ],
        amount: [
          { required: true, message: "项目金额不能为空", trigger: "blur" }
        ],
        rewardTime: [
          { required: true, message: "奖励时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("REWARD_TYPE").then(response => {
      this.rewardTypeOptions = response.data;
    });
    let data = {
      compCode: this.$store.state.user.userInfo.deptId,
      rewardType: "1",
    };
    listReward(data).then(response => {
      this.rewardOptions = response.rows;
    });
  },
  methods: {
    changeReward(val) {
      let datas = {rewardCode:val};
      listReward(datas).then(response => {
        this.form.rewardReason = response.rows[0].rewardReason;
      });
    },
  	getDetailInfo() {
      getInput(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询单项奖惩录入列表 */
    getList() {
      this.loading = true;
      listInput(this.queryParams).then(response => {
        this.inputList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 项目类型(1单项奖惩2其他奖励)字典翻译
    rewardTypeFormat(row, column) {
      return this.selectDictLabel(this.rewardTypeOptions, row.rewardType);
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
        rewardCode: null,
        deptCode: null,
        amount: null,
        rewardTime: getYear(),
        rewardType: "1",
        rewardReason: null,
        isEnable: null,
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
        let saveLoading = this.saveLoading;
          if (this.form.id != null) {
            updateInput(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              saveLoading.close();
            });
          } else {
            addInput(this.form).then(response => {
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
