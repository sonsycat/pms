<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="30%"  custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row>
        <el-form-item label="方案编码" prop="projectCode">
          <el-input v-model="form.projectCode" placeholder="请输入方案编码"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="方案名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入方案名称"  maxlength="50" show-word-limit @blur="getPym"  />
        </el-form-item>
        <el-form-item label="当前年月" prop="nowDate" >
          <el-date-picker clearable size="small" class="w100" disabled
                          v-model="form.nowDate"
                          type="month"
                          value-format="yyyy-MM"
                          placeholder="选择当前年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="拼音码" prop="spell" >
          <el-input v-model="form.spell"  disabled maxlength="50" show-word-limit  :placeholder="pym" />
        </el-form-item>
        <el-form-item label="方案类型" prop="projectType" >
          <el-select v-model="form.projectType" class="w100" placeholder="请选择方案类型" disabled>
            <el-option
              v-for="dict in projectTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm(true)" v-show="!id" >保存并继续添加</el-button>
      <el-button type="primary" @click="submitForm(false)" >确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import { validEmail } from'@/utils/validate'
  import { listProject, getProject, delProject, addProject, updateProject, exportProject } from "@/api/apportionment/project";
  import pinyin from "js-pinyin";
  import {getYear} from "@/utils/date";
  import { getCostApport } from "@/api/system/dict/data"

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
            this.title = "分摊方案管理-编辑";
            this.getDetailInfo();
          } else {
            this.id = "";
            this.pym = "自动生成";
            this.form.projectType ='2';
            this.title = "分摊方案管理-新增";
            this.getCostApport();
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
      getCostApport
    },
    data() {
      return {
        title: "分摊方案管理-新增",
        openDialog: this.open,
        pym: "",
        // 表单参数
        form: {},
        projectTypeOptions: [],
        // 表单校验
        rules: {
          projectCode: [
            { required: true, message: "方案编码不能为空", trigger: "blur" }
          ],
          projectName: [
            { required: true, message: "方案名称不能为空", trigger: "blur" }
          ],
          nowDate: [
            { required: true, message: "当前年月不能为空", trigger: "blur" }
          ],
          projectType:[
            { required: true, message: "方案类型不能为空", trigger: "blur" }
          ],
        }
      };
    },
    created() {
      this.getList();
      this.getDicts("cost_fa_type").then(response => {
        this.projectTypeOptions = response.data;
      });
    },
    methods: {
      getDetailInfo() {
        getProject(this.id).then(response => {
          console.log(response);
          this.form = response.data;
        });
      },
      getCostApport() {
        getCostApport(this.$store.state.user.accountInfo.deptId).then(response => {
          if (response.data) {
            this.form.nowDate = response.data;
          } else {
            this.form.nowDate = getYear();
          }
        });
      },
      // 获取拼音码
      getPym() {
        // 全是字母的情况下,拼音码等于所有字母
        if (/^[a-zA-Z]*$/.test(this.form.projectName)) {
          this.form.spell = this.form.projectName;
          return;
        }
        // 有汉字的情况下,转为首字母
        let fullName = pinyin.getCamelChars(this.form.projectName).toUpperCase();
        let pym = "";
        for (var i = 0; i < fullName.length; i++) {
          var c = fullName.charAt(i);
          if (/^[A-Z]+$/.test(c)) {
            pym += c;
          }
        }
        this.form.spell = pym;
      },
      /** 查询分摊方案管理列表 */
      getList() {
        this.loading = true;
        listProject(this.queryParams).then(response => {
          this.projectList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 方案类型字典翻译
      projectTypeFormat(row, column) {
        return this.selectDictLabel(this.projectTypeOptions, row.projectType);
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
          compCode: this.$store.state.user.accountInfo.deptId,
          accountCode: null,
          projectCode: null,
          projectName: null,
          nowDate: null,
          spell: null,
          projectType: null
        };
        this.resetForm("form");
      },
      /** 提交按钮 */
      submitForm(type) {
        this.$refs["form"].validate(valid => {
          if (valid) {
            let saveLoading = this.saveLoading();
            if (this.form.id != null) {
              updateProject(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.$emit("success", false);
                saveLoading.close();
              });
            } else {
              addProject(this.form).then(response => {
                if(type){
                  this.msgSuccess("保存成功");
                  this.reset()
                }else{
                  this.msgSuccess("新增成功");
                  this.$emit("success", false);
                }
                saveLoading.close();
              });
            }
          }
        });
      },
    }};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    overflow: hidden;
    min-height:20%;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
