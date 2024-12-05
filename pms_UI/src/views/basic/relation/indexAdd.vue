<template>

    <!-- 添加或修改关系分类对话框 -->
    <el-dialog :title="title" :visible.sync="openDialog" width="500px" append-to-body :close-on-click-modal="false" @close="handleDialogClose" custom-class="el-button--primary">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-col :span="24">
          <orgSearch label="单位" :queryParams="form" ></orgSearch>
        </el-col>
        <el-col :span="24">
          <el-form-item label="分类编码" prop="relaCode">
            <el-input v-model="form.relaCode" placeholder="系统自动生成" disabled/>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="分类名称" prop="relaName">
            <el-input v-model="form.relaName" placeholder="请输入分类名称" />
          </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormContinue" v-if="!id">保存并继续添加</el-button>
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

</template>

<script>
import { addRelation, updateRelation, getRelation } from "@/api/basic/relation";
import orgSearch from "@/views/components/orgSearch";

export default {
  name: "addEdit",
  components: {
    orgSearch
  },
  data() {
    return {
      title: "关系分类 / 新增",
      openDialog: this.open,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        compCode: [
          { required: true, message: "单位不能为空", trigger: "blur" }
        ],
        compName: [
          { required: true, message: "单位不能为空", trigger: "blur" },
          { required: true, message: "单位不能为空", trigger: "change" }
        ],
        relaName: [
          { required: true, message: "分类关系名称不能为空", trigger: "blur" }
        ]
      }
    };
  },
  props: {
    open: {
      type: Boolean,
      default: false
    },
    id: {
      type: [String, Number],
      default: ""
    },
    relationIds: {
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
          this.title = "关系分类 / 修改";
          this.getDetailInfo();
        } else {
          this.id = "";

          this.title = "关系分类 / 新增";
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
  methods: {
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRelation(this.form).then(response => {
              if (response.code == 200) {
                this.msgSuccess("修改成功");
                this.$emit("success", false); // 父组件传值 关闭弹框
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addRelation(this.form).then(response => {
              if (response.code == 200) {
                this.msgSuccess("新增成功");
                this.$emit("success", false);
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    submitFormContinue() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRelation(this.form).then(response => {
              if (response.code == 200) {
                this.msgSuccess("修改成功");
                this.$emit("success", false); // 父组件传值 关闭弹框
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addRelation(this.form).then(response => {
              if (response.code == 200) {
                this.msgSuccess("新增成功");
                this.reset();
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    reset() {
      this.form = {
        id: null,
        compCode: null,
        compName: null,
        deptCode: null,
        relaCode: null,
        relaName: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    // 取消按钮
    cancel() {
      this.$emit("cancel", false);
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit("cancel", false);
    },
    getDetailInfo() {
      const loading = this.dataLoading()                   
      getRelation(this.id).then(response => {
        if (response.code == 200) {
          this.form = response.data;
        loading.close()                          
        } else {
          this.msgError(response.msg);
        }
      }).catch(()=>{
        loading.close();            
      })
    }
  }
};
</script>
