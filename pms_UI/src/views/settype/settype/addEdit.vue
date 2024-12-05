<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="40%" custom-class="el-button--primary" append-to-body
             :close-on-click-modal="false" @close="handleDialogClose">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="类别编码" prop="bscCode">
            <el-input v-model="form.bscCode" placeholder="系统生成类别编码" disabled maxlength="50" show-word-limit/>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="类别名称" prop="bscName">
            <el-input v-model="form.bscName" placeholder="请输入类别名称" maxlength="100" show-word-limit/>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <bscSelect :form="form" :disableBranch="false" forItemProp="parentCode" label="上级分类" placeholder="请选择上级分类"
                     :refresh="refresh" @selectTree="handleTreeSelect"></bscSelect>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否停用" prop="isStop">
            <el-select class="w100" v-model="form.isStop" placeholder="请选择是否停用">
              <el-option
                v-for="dict in isStopOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否末级" prop="isLast">
            <el-select class="w100" v-model="form.isLast" placeholder="请选择是否末级">
              <el-option
                v-for="dict in isLastOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm" :loading="saveLoading">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {validEmail} from '@/utils/validate'
  import {updateIsStop, listSettype, getSettype, delSettype, addSettype, updateSettype, exportSettype } from "@/api/settype/settype";
  import bscSelect from "@/views/components/bscList/setTypeIndex"

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
            this.title = "指标集分类管理-编辑";
            this.getDetailInfo();
          } else {
            this.id = "";
            console.log("parentCode", this.parentCode)
            if (this.parentCode) {
              this.form.parentCode = this.parentCode
            }
            this.title = "指标集分类管理-新增";
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
    components: {bscSelect},
    data() {
      return {
        deposit: null,
        delivery: null,
        depositArr: [],
        deliveryArr: [],
        //==============================
        title: "BSC分类管理-新增",
        openDialog: this.open,
        saveLoading: false,
        refresh: false,
        // 表单参数
        form: {},
        isStopOptions: [],
        isLastOptions: [],
        // 表单校验
        rules: {
          bscName: [
            {required: true, message: "BSC名称不能为空", trigger: "blur"}
          ],
        }
      };
    },
    created() {
      this.getList();
      this.getDicts("hr_is_change").then(response => {
        this.isStopOptions = response.data;
      });
      this.getDicts("hr_is_change").then(response => {
        this.isLastOptions = response.data;
      });
    },
    methods: {

      handleTreeSelect(data) {
        let deposit = data.deposit.split(",");
        let delivery = data.delivery.split(",");

        this.$set(this.form, 'depositArr', deposit)
        this.$set(this.form, 'deliveryArr', delivery)

      },
      getDetailInfo() {
        getSettype(this.id).then(response => {
          console.log(response);
          this.form = response.data;
          if (this.form.deposit) {
            this.form.depositArr = this.form.deposit.split(",")
          } else {
            this.form.depositArr = []
          }
          if (this.form.delivery) {
            this.form.deliveryArr = this.form.delivery.split(",");
          } else {
            this.form.deliveryArr = []
          }
          if (this.form.parentCode == "0") {
            this.form.parentCode = null;
          }
          loading.close()
        }).catch(() => {
          loading.close();
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
      // 是否停用字典翻译
      isStopFormat(row, column) {
        return this.selectDictLabel(this.isStopOptions, row.isStop);
      },
      // 是否末级字典翻译
      isLastFormat(row, column) {
        return this.selectDictLabel(this.isLastOptions, row.isLast);
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
              updateSettype(this.form).then(response => {
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
