<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="70%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="单位编码" prop="compCode">
          <el-input v-model="form.compCode" placeholder="请输入单位编码"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="账套编码" prop="accountCode">
          <el-input v-model="form.accountCode" placeholder="请输入账套编码"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="特殊项目编码" prop="specialCode">
          <el-input v-model="form.specialCode" placeholder="请输入特殊项目编码"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="项目编码" prop="revenueCode">
          <el-input v-model="form.revenueCode" placeholder="请输入项目编码"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="项目名称" prop="revenueName">
          <el-input v-model="form.revenueName" placeholder="请输入项目名称"  maxlength="100" show-word-limit  />
        </el-form-item>
        <el-form-item label="开单科室" prop="billDept">
          <el-input v-model="form.billDept" placeholder="请输入开单科室"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="执行科室" prop="execDept">
          <el-input v-model="form.execDept" placeholder="请输入执行科室"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="住院开单(%)" prop="inpBill">
          <el-input v-model="form.inpBill" placeholder="请输入住院开单(%)"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="住院执行(%)" prop="inpExec">
          <el-input v-model="form.inpExec" placeholder="请输入住院执行(%)"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="住院护理(%)" prop="inpCare">
          <el-input v-model="form.inpCare" placeholder="请输入住院护理(%)"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="门诊开单(%)" prop="outpBill">
          <el-input v-model="form.outpBill" placeholder="请输入门诊开单(%)"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="门诊执行(%)" prop="outpExec">
          <el-input v-model="form.outpExec" placeholder="请输入门诊执行(%)"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="门诊护理(%)" prop="outpCare">
          <el-input v-model="form.outpCare" placeholder="请输入门诊护理(%)"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="合作医疗(%)" prop="coopMed">
          <el-input v-model="form.coopMed" placeholder="请输入合作医疗(%)"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="核算类型(1参与核算，2不参与核算)" prop="accountType">
          <el-select v-model="form.accountType" placeholder="请选择核算类型(1参与核算，2不参与核算)">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="折算比(%)" prop="convRatio">
          <el-input v-model="form.convRatio" placeholder="请输入折算比(%)"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="固定折算(%)" prop="fixConv">
          <el-input v-model="form.fixConv" placeholder="请输入固定折算(%)"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="成本对照(成本项目)" prop="projectCode">
          <el-input v-model="form.projectCode" placeholder="请输入成本对照(成本项目)"  maxlength="20" show-word-limit  />
        </el-form-item>
        <el-form-item label="收入类别(HIS)" prop="hisRevenumTypede">
          <el-input v-model="form.hisRevenumTypede" placeholder="请输入收入类别(HIS)"  maxlength="20" show-word-limit  />
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
import { listSpecial, getSpecial, delSpecial, addSpecial, updateSpecial, exportSpecial } from "@/api/revenue/special";

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
          this.title = "特殊收入项目方案管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "特殊收入项目方案管理-新增";
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
      title: "特殊收入项目方案管理-新增",
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
      getSpecial(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询特殊收入项目方案列表 */
    getList() {
      this.loading = true;
      listSpecial(this.queryParams).then(response => {
        this.specialList = response.rows;
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
        compCode: null,
        accountCode: null,
        specialCode: null,
        revenueCode: null,
        revenueName: null,
        billDept: null,
        execDept: null,
        inpBill: null,
        inpExec: null,
        inpCare: null,
        outpBill: null,
        outpExec: null,
        outpCare: null,
        coopMed: null,
        accountType: null,
        convRatio: null,
        fixConv: null,
        projectCode: null,
        hisRevenumTypede: null,
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
            updateSpecial(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              this.saveLoading = false;
            });
          } else {
            addSpecial(this.form).then(response => {
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