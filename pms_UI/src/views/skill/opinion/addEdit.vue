<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="70%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="单位编码" prop="compCode">
          <el-input v-model="form.compCode" placeholder="请输入单位编码"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="账套编码" prop="accountCode">
          <el-input v-model="form.accountCode" placeholder="请输入账套编码"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="编码" prop="technoCode">
          <el-input v-model="form.technoCode" placeholder="请输入编码"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="类别" prop="technoType">
          <el-select v-model="form.technoType" placeholder="请选择类别">
            <el-option
              v-for="dict in technoTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审批状态" prop="auditState">
          <el-select v-model="form.auditState" placeholder="请选择审批状态">
            <el-option
              v-for="dict in auditStateOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="意见" prop="opinion">
          <el-input v-model="form.opinion" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="审批时间" prop="approvelTime">
          <el-date-picker clearable size="small"
            v-model="form.approvelTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择审批时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审批人姓名" prop="approvelName">
          <el-input v-model="form.approvelName" placeholder="请输入审批人姓名"  maxlength="50" show-word-limit  />
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
import { listOpinion, getOpinion, delOpinion, addOpinion, updateOpinion, exportOpinion } from "@/api/skill/opinion";

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
          this.title = "审批意见管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "审批意见管理-新增";
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
      title: "审批意见管理-新增",
      openDialog: this.open,
      saveLoading: false,
      // 表单参数
      form: {},
      	  	  	  	  	  	  	  	  	  		  technoTypeOptions: [],
	  	  	  		  auditStateOptions: [],
	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("TECHNO_CODE").then(response => {
      this.technoTypeOptions = response.data;
    });
    this.getDicts("asset_aduit_state").then(response => {
      this.auditStateOptions = response.data;
    });
  },
  methods: {
  	getDetailInfo() {
      getOpinion(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询审批意见列表 */
    getList() {
      this.loading = true;
      listOpinion(this.queryParams).then(response => {
        this.opinionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 类别字典翻译
    technoTypeFormat(row, column) {
      return this.selectDictLabel(this.technoTypeOptions, row.technoType);
    },
    // 审批状态字典翻译
    auditStateFormat(row, column) {
      return this.selectDictLabel(this.auditStateOptions, row.auditState);
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
        compCode: null,
        accountCode: null,
        technoCode: null,
        technoType: null,
        auditState: null,
        opinion: null,
        approvelTime: null,
        approvelName: null,
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
            updateOpinion(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              this.saveLoading = false;
            });
          } else {
            addOpinion(this.form).then(response => {
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