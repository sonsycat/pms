<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名称" prop="rewardName">
          <el-input v-model="form.rewardName" placeholder="请输入项目名称"  maxlength="100" show-word-limit  />
        </el-form-item>
        <el-form-item label="项目类型" prop="rewardType">
          <el-select v-model="form.rewardType" placeholder="请选择项目类型" class="w100" @change="changeType">
            <el-option
              v-for="dict in rewardTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="奖惩标准" prop="rewardReason">
          <el-input v-model="form.rewardReason" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="是否启用" prop="isEnable">
          <el-select v-model="form.isEnable" placeholder="请选择是否启用" class="w100">
            <el-option
              v-for="dict in isEnableOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm(true)">保存并继续添加</el-button>
        <el-button type="primary" @click="submitForm(false)">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import { listReward, getReward, delReward, addReward, updateReward, exportReward } from "@/api/subsidy/reward";

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
          this.title = "奖惩项目维护管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "奖惩项目维护管理-新增";
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
      title: "奖惩项目维护管理-新增",
      openDialog: this.open,
      // 表单参数
      form: {},
      	  	  	  	  	  	  	  	  	  	  	  		  rewardTypeOptions: [],
	  	  	  	  	  		  isEnableOptions: [],
	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
      rules: {
        rewardName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
        rewardType: [
          { required: true, message: "项目类型不能为空", trigger: "blur" }
        ],
        rewardReason: [],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("REWARD_TYPE").then(response => {
      this.rewardTypeOptions = response.data;
    });
    this.getDicts("hr_is_change").then(response => {
      this.isEnableOptions = response.data;
    });
  },
  methods: {
    changeType(val) {
      if (val == '1') {
        this.rules.rewardReason = [
          { required: true, message: "奖惩标准不能为空", trigger: "blur" }
        ];
      } else {
        this.rules.rewardReason = [];
      }
    },
  	getDetailInfo() {
      getReward(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询奖惩项目维护列表 */
    getList() {
      this.loading = true;
      listReward(this.queryParams).then(response => {
        this.rewardList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 项目类型(1单项奖惩2其他奖励)字典翻译
    rewardTypeFormat(row, column) {
      return this.selectDictLabel(this.rewardTypeOptions, row.rewardType);
    },
    // 是否启用字典翻译
    isEnableFormat(row, column) {
      return this.selectDictLabel(this.isEnableOptions, row.isEnable);
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
        rewardName: null,
        rewardType: null,
        rewardReason: null,
        isEnable: '0',
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
    submitForm(type) {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let saveLoading = this.saveLoading();
          if (type) {
            if (this.form.id != null) {
              updateReward(this.form).then(response => {
                this.reset();
                this.msgSuccess("修改成功");
                this.$emit("success", true);
                this.title = "奖惩项目维护管理-新增";
                saveLoading.close();
              });
            } else {
              addReward(this.form).then(response => {
                this.reset();
                this.msgSuccess("新增成功");
                this.$emit("success", true);
                this.title = "奖惩项目维护管理-新增";
                saveLoading.close();
              });
            }
          } else {
            if (this.form.id != null) {
              updateReward(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.$emit("success", false);
                saveLoading.close();
              });
            } else {
              addReward(this.form).then(response => {
                this.msgSuccess("新增成功");
                this.$emit("success", false);
                saveLoading.close();
              });
            }
          }
        }
      });
    },
}};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    height: 40%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
