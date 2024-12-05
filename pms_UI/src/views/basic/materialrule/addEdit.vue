<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="70%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="物资类型" prop="materialType">
          <el-select v-model="form.materialType" placeholder="请选择物资类型">
            <el-option
              v-for="dict in materialTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="条码类型" prop="barType">
          <el-select v-model="form.barType" placeholder="请选择条码类型">
            <el-option
              v-for="dict in barTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="规则内容">
          <editor v-model="form.ruleContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="规则内容第一部分" prop="ruleFirst">
          <el-input v-model="form.ruleFirst" placeholder="请输入规则内容第一部分"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="规则内容第二部分" prop="ruleSecond">
          <el-input v-model="form.ruleSecond" placeholder="请输入规则内容第二部分"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="规则内容第三部分" prop="ruleThird">
          <el-input v-model="form.ruleThird" placeholder="请输入规则内容第三部分"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="删除标记" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标记"  maxlength="1" show-word-limit  />
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
import { listMaterialrule, getMaterialrule, delMaterialrule, addMaterialrule, updateMaterialrule, exportMaterialrule } from "@/api/basic/materialrule";
import Editor from '@/components/Editor';

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
          this.title = "编码规则管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "编码规则管理-新增";
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
    Editor,
  },
  data() {
    return {
      title: "编码规则管理-新增",
      openDialog: this.open,
      saveLoading: false,
      // 表单参数
      form: {},
      	  	  	  		  materialTypeOptions: [],
	  	  	  		  barTypeOptions: [],
	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("spd_wz_type").then(response => {
      this.materialTypeOptions = response.data;
    });
    this.getDicts("spd_tm_type").then(response => {
      this.barTypeOptions = response.data;
    });
  },
  methods: {
  	getDetailInfo() {
      getMaterialrule(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询编码规则列表 */
    getList() {
      this.loading = true;
      listMaterialrule(this.queryParams).then(response => {
        this.materialruleList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 物资类型字典翻译
    materialTypeFormat(row, column) {
      return this.selectDictLabel(this.materialTypeOptions, row.materialType);
    },
    // 条码类型字典翻译
    barTypeFormat(row, column) {
      return this.selectDictLabel(this.barTypeOptions, row.barType);
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
        materialType: null,
        barType: null,
        ruleContent: null,
        ruleFirst: null,
        ruleSecond: null,
        ruleThird: null,
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
            updateMaterialrule(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              this.saveLoading = false;
            });
          } else {
            addMaterialrule(this.form).then(response => {
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