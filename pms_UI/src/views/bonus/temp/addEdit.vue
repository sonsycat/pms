<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="70%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="院，科，人编码" prop="unitCode">
          <el-input v-model="form.unitCode" placeholder="请输入院，科，人编码"  maxlength="64" show-word-limit  />
        </el-form-item>
        <el-form-item label="固定与指标代码相同" prop="guideCode">
          <el-input v-model="form.guideCode" placeholder="请输入固定与指标代码相同"  maxlength="64" show-word-limit  />
        </el-form-item>
        <el-form-item label="指标值" prop="guideValue">
          <el-input v-model="form.guideValue" placeholder="请输入指标值"  maxlength="10,4" show-word-limit  />
        </el-form-item>
        <el-form-item label="指标值计算结果" prop="guideValueResult">
          <el-input v-model="form.guideValueResult" placeholder="请输入指标值计算结果"  maxlength="10,4" show-word-limit  />
        </el-form-item>
        <el-form-item label="Y，K，R" prop="guideType">
          <el-select v-model="form.guideType" placeholder="请选择Y，K，R">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="数据作用日期" prop="actDate">
          <el-date-picker clearable size="small"
            v-model="form.actDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择数据作用日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="数据作用开始日期" prop="actDateEnd">
          <el-date-picker clearable size="small"
            v-model="form.actDateEnd"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择数据作用开始日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="数据作用结束日期" prop="actDateBegin">
          <el-date-picker clearable size="small"
            v-model="form.actDateBegin"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择数据作用结束日期">
          </el-date-picker>
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
import { listTemp, getTemp, delTemp, addTemp, updateTemp, exportTemp } from "@/api/bonus/temp";

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
          this.title = "指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)管理-新增";
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
      title: "指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)管理-新增",
      openDialog: this.open,
      saveLoading: false,
      // 表单参数
      form: {},
      	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
      rules: {
        unitCode: [
          { required: true, message: "院，科，人编码不能为空", trigger: "blur" }
        ],
        guideCode: [
          { required: true, message: "固定与指标代码相同不能为空", trigger: "blur" }
        ],
        guideValue: [
          { required: true, message: "指标值不能为空", trigger: "blur" }
        ],
        guideType: [
          { required: true, message: "Y，K，R不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
  	getDetailInfo() {
      getTemp(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)列表 */
    getList() {
      this.loading = true;
      listTemp(this.queryParams).then(response => {
        this.tempList = response.rows;
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
        unitCode: null,
        guideCode: null,
        guideValue: null,
        guideValueResult: null,
        guideType: null,
        actDate: null,
        actDateEnd: null,
        actDateBegin: null,
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
            updateTemp(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              this.saveLoading = false;
            });
          } else {
            addTemp(this.form).then(response => {
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