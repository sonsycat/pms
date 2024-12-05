<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="40%" custom-class="el-button--primary" append-to-body
             :close-on-click-modal="false" @close="handleDialogClose">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row>
        <el-form-item label="医嘱标准当量" prop="doctorAdviceNum">
          <el-input-number controls-position="right" v-model="form.doctorAdviceNum" :precision="2" placeholder="请输入奖金绩效"
                           class="w100" show-word-limit  />
        </el-form-item>
        <el-form-item label="医技标准当量" prop="doctorTechNum">
          <el-input-number controls-position="right" v-model="form.doctorTechNum" :precision="2" placeholder="请输入奖金绩效"
                           class="w100" show-word-limit  />
        </el-form-item>
        <el-form-item label="护理标准当量" prop="doctorNurseNum">
          <el-input-number controls-position="right" v-model="form.doctorNurseNum" :precision="2" placeholder="请输入奖金绩效"
                           class="w100" show-word-limit  />
        </el-form-item>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm" :loading="saveLoading">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
   import {updateEquivalent, getEquivalent }
   from "@/api/foundation/equivalent";

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
      },
      parentCode: {
        type: [String, String],
        default: ""
      }
    },

    watch: {
      id: {
        handler(val) {
          this.reset();
          if (val) {
            this.id = val;
            this.title = "基础当量-编辑";
            this.getDetailInfo();
          } else {
            this.id = "";
            if (this.parentCode) {
              this.form.parentCode = this.parentCode
            }
            this.title = "基础当量-新增";
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
    components: {},
    data() {
      return {
        deposit: null,
        delivery: null,
        depositArr: [],
        deliveryArr: [],
        //==============================
        title: "基础当量-新增",
        openDialog: this.open,
        saveLoading: false,
        refresh: false,
        // 表单参数
        form: {},
        isStopOptions: [],
        isLastOptions: [],
        // 表单校验
        rules: {
          doctorAdviceNum: [
            {required: true, message: "医嘱标准当量不能为空", trigger: "blur"}
          ],
          doctorTechNum: [
            {required: true, message: "医技标准当量不能为空", trigger: "blur"}
          ],
          doctorNurseNum: [
            {required: true, message: "护理标准当量不能为空", trigger: "blur"}
          ],
        }
      };
    },
    created() {
      // this.getList();
    },
    methods: {
      getDetailInfo() {
        getEquivalent(this.id).then(response => {
          console.log(response);
          this.form = response.data;
        });
      },

      /** 查询BSC分类列表 */
      getList() {
        this.loading = true;
        listSettype(this.queryParams).then(response => {
          this.bscList = response.rows;
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
          bscCode: null,
          bscName: null,
          parentCode: null,
          ancestors: null,
          isStop: "1",
          isLast: "0",
          delFlag: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          remark: null,
          deposit: null,
          delivery: null,
          depositArr: [],
          deliveryArr: []
        };
        this.resetForm("form");
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.saveLoading = true;
            if (this.form.id != null) {
              updateEquivalent(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.$emit("success", false);
                this.saveLoading = false;
              });
            } else {
              addSettype(this.form).then(response => {
                this.msgSuccess("新增成功");
                this.$emit("success", false);
                this.saveLoading = false;
              });
            }
          }
        });
      },
    }
  };
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    height: 35%;
    overflow: hidden;

    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
