<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body
             :close-on-click-modal="false" @close="handleDialogClose">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row :gutter="24">
        <el-col :span="12">
          <el-form-item label="项目编码:" prop="projectCode"   >
            <el-input v-model="form.projectCode" @blur="checkCode()"  placeholder="请输入项目编码" maxlength="50" :disabled = "idflag"
                      show-word-limit/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目名称:" prop="projectName">
            <el-input v-model="form.projectName" placeholder="请输入项目名称" maxlength="100" show-word-limit/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="上级项目:" prop="parentProject">
            <el-input v-model="form.parentProject" maxlength="50" disabled show-word-limit/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目级别:" prop="projectLevel">
            <el-select v-model="form.projectLevel" placeholder="" disabled class="w100">
              <el-option
                v-for="dict in projectLevelOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目类型:" prop="projectType">
            <el-select v-model="form.projectType" placeholder="请选择项目类型" class="w100">
              <el-option
                v-for="dict in projectTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="成本属性:" prop="costProperty">
            <el-select v-model="form.costProperty" placeholder="请选择成本属性" class="w100">
              <el-option
                v-for="dict in costPropertyOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否可控:" prop="isControl">
            <el-select v-model="form.isControl" placeholder="请选择是否可控" class="w100">
              <el-option
                v-for="dict in isControlOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否核算:" prop="isAccount">
            <el-select v-model="form.isAccount" placeholder="请选择是否核算" class="w100">
              <el-option
                v-for="dict in isAccountOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="资本性成本:" prop="isCostCapital">
            <el-select v-model="form.isCostCapital" placeholder="请选择是否资本性成本" class="w100">
              <el-option
                v-for="dict in isCostCapitalOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否医疗成本:" prop="isCostHealth">
            <el-select v-model="form.isCostHealth" placeholder="请选择是否医疗成本" class="w100">
              <el-option
                v-for="dict in isCostHealthOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="计算计入成本:" prop="isCostCalculate">
            <el-select v-model="form.isCostCalculate" placeholder="请选择计算计入成本" class="w100" disabled>
              <el-option
                v-for="dict in isCostCalculateOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24" v-if="form.isCostCalculate==0">
          <projectPop label="计算计入成本分摊方案" :formProp="form.costScheme" prop="costScheme" :projectType='2' :queryParams="form" @click="handleClickProjectPop"/>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm(true)" v-show="!idflag">保存并继续添加</el-button>
      <el-button type="primary" @click="submitForm(false)" >确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {validEmail} from '@/utils/validate';
  import {
    listProject,
    getProject,
    delProject,
    addProject,
    updateProject,
    exportProject,
    checkCode
  } from "@/api/project/project";
  import { getProject1,getProject2 } from "@/api/system/dict/data";
  import projectPop from "@/views/components/projectPop";
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
      levelOptions:{
        type:[Array],
        default:[]
      }
    },
    watch: {
      id: {
        handler(val) {
          this.reset();
          if (val) {
            this.id = val;
            this.idflag = true;
            this.title = "成本项目管理-编辑";
            this.getDetailInfo();
          } else {
            this.id = "";
            this.idflag = false;
            this.title = "成本项目管理-新增";
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
    components: {getProject1,getProject2,projectPop},
    data() {
      return {
        title: "成本项目管理-新增",
        openDialog: this.open,
        idflag:'',
        // 表单参数
        form: {
        },
        projectLevelOptions: [],
        projectTypeOptions: [],
        costPropertyOptions: [],
        isControlOptions: [],
        isAccountOptions: [],
        isCostCapitalOptions: [],
        isCostHealthOptions: [],
        isCostCalculateOptions: [],
        costSchemeOptions: [],
        costDeptOptions: [],
        // 表单校验
        rules: {
          projectCode: [
            { required: true, message: "项目编码不能为空", trigger: "blur" }
          ],
          projectName: [
            { required: true, message: "项目名称不能为空", trigger: "blur" }
          ],
          projectType: [
            { required: true, message: "项目类型不能为空", trigger: "blur" }
          ],
          costProperty:[
            { required: true, message: "成本属性不能为空", trigger: "blur" }
          ],
        }
      };
    },
    created() {
      this.getDicts("cost_xm_jb").then(response => {
        this.projectLevelOptions = response.data;
      });
      this.getDicts("cost_xm_lx").then(response => {
        this.projectTypeOptions = response.data;
      });
      this.getDicts("cost_cb_sx").then(response => {
        this.costPropertyOptions = response.data;
      });
      this.getDicts("hr_is_change").then(response => {
        this.isCostHealthOptions = response.data;
        this.isCostCalculateOptions = response.data;
        this.isCostCapitalOptions = response.data;
        this.isAccountOptions = response.data;
        this.isControlOptions = response.data;
      });
      getProject1({"compCode": this.$store.state.user.accountInfo.deptId}).then(response => {
        this.costDeptOptions= response.data;
      });
      getProject2({"compCode": this.$store.state.user.accountInfo.deptId}).then(response => {
        this.costSchemeOptions = response.data;
      });
    },
    methods: {
      handleClickProjectPop(data){
        this.form.costScheme=data.projectCode
      },
      getDetailInfo() {
        getProject(this.id).then(response => {
          this.levelOptions.forEach(item=>{
            if(response.data.costScheme==item.dictValue){
              response.data.costScheme=item.dictLabel
            }

          })
          this.form = response.data;
        });
      },
      checkCode() {
        let data = {
          projectCode: this.form.projectCode,
        };
        checkCode(data).then(response => {
          if('0'== response.msg){
            this.form.parentProject = '0';
            this.form.projectLevel ='1';
            this.form.isCostCalculate='0';
          }else{
            let parent = {projectCode: response.msg}
            this.form.parentProject = response.msg;
            listProject(parent).then(response => {
              this.form.projectLevel =Number(response.rows[0].projectLevel)+1+'';
            })
          }
        })
      },
      // 项目级别字典翻译
      projectLevelFormat(row, column) {
        return this.selectDictLabel(this.projectLevelOptions, row.projectLevel);
      },
      // 项目类型字典翻译
      projectTypeFormat(row, column) {
        return this.selectDictLabel(this.projectTypeOptions, row.projectType);
      },
      // 成本属性字典翻译
      costPropertyFormat(row, column) {
        return this.selectDictLabel(this.costPropertyOptions, row.costProperty);
      },
      // 是否可控字典翻译
      isControlFormat(row, column) {
        return this.selectDictLabel(this.isControlOptions, row.isControl);
      },
      // 是否核算字典翻译
      isAccountFormat(row, column) {
        return this.selectDictLabel(this.isAccountOptions, row.isAccount);
      },
      // 是否资本性成本字典翻译
      isCostCapitalFormat(row, column) {
        return this.selectDictLabel(this.isCostCapitalOptions, row.isCostCapital);
      },
      // 是否医疗成本字典翻译
      isCostHealthFormat(row, column) {
        return this.selectDictLabel(this.isCostHealthOptions, row.isCostHealth);
      },
      // 是否计算计入成本字典翻译
      isCostCalculateFormat(row, column) {
        return this.selectDictLabel(this.isCostCalculateOptions, row.isCostCalculate);
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
          projectCode: "",
          projectName: null,
          parentProject: null,
          projectLevel: null,
          projectType: null,
          costProperty: null,
          isControl: '0',
          isAccount:  '0',
          isCostCapital:  '0',
          isCostHealth:  '0',
          isCostCalculate:  '0',
          costScheme: null

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
    }
  };
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    min-height: 20%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
