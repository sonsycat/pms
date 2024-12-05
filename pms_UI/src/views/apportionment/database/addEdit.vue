<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="36">
          <el-col :span="12">
            <el-form-item label="编码" prop="code">
              <el-input v-model="form.code" placeholder="系统自动生成"  maxlength="50" show-word-limit disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
          <el-form-item label="名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入名称"  maxlength="100" show-word-limit  />
          </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="类型" prop="type">
              <el-select v-model="form.type" placeholder="请选择类型" class="w100">
                <el-option
                  v-for="dict in typeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="url" prop="url">
              <el-input v-model="form.url" type="textarea" placeholder="请输入url" rows="3" maxlength="225" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户名" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入用户名"  maxlength="225" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="密码" prop="passWord">
              <el-input v-model="form.passWord" placeholder="请输入密码"  maxlength="225" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" rows="3" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" :loading="saveLoading">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import { listDatabase, getDatabase, delDatabase, addDatabase, updateDatabase, exportDatabase } from "@/api/apportionment/database";

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
          this.title = "多源数据库设置-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "多源数据库设置-新增";
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
      title: "多源数据库设置-新增",
      openDialog: this.open,
      saveLoading: false,
      // 表单参数
      form: {},
      typeOptions: [],
	  	// 表单校验
      rules: {
        name: [
          { required: true, message: "名称不能为空", trigger: "change" }
        ],
        type: [
          { required: true, message: "类型不能为空", trigger: "change" }
        ],
        url: [
          { required: true, message: "url不能为空", trigger: "change" }
        ],
        userName: [
          { required: true, message: "用户名不能为空", trigger: "change" }
        ],
        passWord: [
          { required: true, message: "密码不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("cost_data_source_type").then(response => {
      this.typeOptions = response.data;
    });
  },
  methods: {
  	getDetailInfo() {
      getDatabase(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询多源数据库设置列表 */
    getList() {
      this.loading = true;
      listDatabase(this.queryParams).then(response => {
        this.databaseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 类型字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
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
        code: null,
        name: null,
        type: null,
        url: null,
        userName: null,
        passWord: null,
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
            updateDatabase(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              this.saveLoading = false;
            }).catch((err)=>{
              this.saveLoading = false;
            });
          } else {
            addDatabase(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.$emit("success", false);
              this.saveLoading = false;
            }).catch((err)=>{
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
    min-height: 20%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
      .el-form-item__label{
        width: 80px !important;
      }
      .el-form-item__content{
        margin-left: 80px !important
      }
    }
  }
</style>
