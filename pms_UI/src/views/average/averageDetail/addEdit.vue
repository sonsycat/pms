<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="70%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="平均奖科人数主表编码" prop="avgCode">
          <el-input v-model="form.avgCode" placeholder="请输入平均奖科人数主表编码"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="科室编码" prop="deptCode">
          <el-input v-model="form.deptCode" placeholder="请输入科室编码"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="科室名称" prop="deptName">
          <el-input v-model="form.deptName" placeholder="请输入科室名称"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="医生编码" prop="docCode">
          <el-input v-model="form.docCode" placeholder="请输入医生编码"  maxlength="30" show-word-limit  />
        </el-form-item>
        <el-form-item label="医生名称" prop="docName">
          <el-input v-model="form.docName" placeholder="请输入医生名称"  maxlength="30" show-word-limit  />
        </el-form-item>
        <el-form-item label="是否发奖金" prop="isReward">
          <el-input v-model="form.isReward" placeholder="请输入是否发奖金"  maxlength="1" show-word-limit  />
        </el-form-item>
        <el-form-item label="工作日数" prop="workDays">
          <el-input v-model="form.workDays" placeholder="请输入工作日数"  maxlength="10" show-word-limit  />
        </el-form-item>
        <el-form-item label="岗位系数" prop="postRatio">
          <el-input v-model="form.postRatio" placeholder="请输入岗位系数"  maxlength="10" show-word-limit  />
        </el-form-item>
        <el-form-item label="责任奖系数" prop="dutyRewardRatio">
          <el-input v-model="form.dutyRewardRatio" placeholder="请输入责任奖系数"  maxlength="10" show-word-limit  />
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
import { listAverageDetail, getAverageDetail, delAverageDetail, addAverageDetail, updateAverageDetail, exportAverageDetail } from "@/api/average/averageDetail";

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
          this.title = "平均奖科人数明细管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "平均奖科人数明细管理-新增";
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
      title: "平均奖科人数明细管理-新增",
      openDialog: this.open,
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
      getAverageDetail(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询平均奖科人数明细列表 */
    getList() {
      this.loading = true;
      listAverageDetail(this.queryParams).then(response => {
        this.averageDetailList = response.rows;
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
        avgCode: null,
        deptCode: null,
        deptName: null,
        docCode: null,
        docName: null,
        isReward: null,
        workDays: null,
        postRatio: null,
        dutyRewardRatio: null,
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
            updateAverageDetail(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              this.saveLoading = false;
            });
          } else {
            addAverageDetail(this.form).then(response => {
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