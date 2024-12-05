<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="成果编码" prop="achieveCode">
              <el-input v-model="form.achieveCode" placeholder="请输入成果编码"  maxlength="20" show-word-limit />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="成果名称" prop="achieveName">
              <el-input v-model="form.achieveName" placeholder="请输入成果名称"  maxlength="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="科室" prop="deptCode">
              <el-select v-model="form.deptCode" clearable placeholder="请选择科室" class="w100" filterable clearable >
                <el-option
                  v-for="item in deptList"
                  :key="item.deptRoomId"
                  :label="item.deptRoomName"
                  :value="item.deptRoomId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="主要完成单位" prop="completeUnit">
              <el-input v-model="form.completeUnit" placeholder="请输入主要完成单位"  maxlength="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="主要完成人" prop="personCode">
              <el-input v-model="form.personCode" placeholder="请输入完成人"  maxlength="20" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="完成人学历" prop="education">
              <el-select v-model="form.education" placeholder="请选择完成人学历" class="w100" filterable clearable>
                <el-option
                  v-for="dict in educationOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="完成人职称" prop="titleCode">
              <el-select v-model="form.titleCode" placeholder="请选择完成人职称" class="w100" filterable clearable>
                <el-option
                  v-for="dict in titleCodeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="获奖类别及等级" prop="awardLevel">
              <el-input v-model="form.awardLevel" placeholder="请输入获奖级别"  maxlength="36" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否推广应用" prop="isExtension">
              <el-select v-model="form.isExtension" placeholder="请选择是否推广" class="w100">
                <el-option
                  v-for="dict in isExtensionOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="获奖时间" prop="awardTime">
              <el-date-picker clearable size="small" class="w100"
                              v-model="form.awardTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择获奖时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="推广收益" prop="extensionIncome">
              <el-input v-model="form.extensionIncome" placeholder="请输入推广收益"  maxlength="20" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="批准单位" prop="approve">
              <el-select v-model="form.approve" placeholder="请选择批准单位" class="w100">
                <el-option
                  v-for="dict in approveOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务来源" prop="taskSource">
              <el-select v-model="form.taskSource" placeholder="请选择任务来源" class="w100">
                <el-option
                  v-for="dict in taskSourceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="推广范围" prop="extensionScope">
              <el-select v-model="form.extensionScope" placeholder="请选择推广范围" class="w100">
                <el-option
                  v-for="dict in extensionScopeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="成果性质" prop="achieveNature">
              <el-select v-model="form.achieveNature" placeholder="请选择成果性质" class="w100">
                <el-option
                  v-for="dict in achieveNatureOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="新药批文号" prop="drugApproval">
              <el-input v-model="form.drugApproval" placeholder="请输入新药批文号"  maxlength="50" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="专利收益" prop="patentIncome">
              <el-input v-model="form.patentIncome" placeholder="请输入专利收益"  maxlength="50" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="专利号" prop="patentNo">
              <el-input v-model="form.patentNo" placeholder="请输入专利号"  maxlength="50" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="专利类别" prop="patentType">
              <el-select v-model="form.patentType" placeholder="请选择专利类别" class="w100">
                <el-option
                  v-for="dict in patentTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="主题词" prop="subjectWords">
              <el-input v-model="form.subjectWords" placeholder="请输入主题词"  maxlength="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="成果摘要" prop="achieveAbstract">
              <el-input v-model="form.achieveAbstract" placeholder="请输入成果摘要"  maxlength="200" show-word-limit  />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-table border stripe v-loading="loading" :data="opinionList" ref="multipleTable"  >
            <el-table-column show-overflow-tooltip sortable label="审批状态" align="center" prop="auditState" :formatter="auditStateFormat" />
            <el-table-column show-overflow-tooltip sortable label="意见" align="left" prop="opinion" />
            <el-table-column show-overflow-tooltip sortable label="审批时间" align="center" prop="approvelTime">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.approvelTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip sortable label="审批人姓名" align="center" prop="approvelName" />
          </el-table>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import { listAchieve, getAchieve, delAchieve, addAchieve, updateAchieve, exportAchieve } from "@/api/skill/achieve";
import { treeRoom, getEmpList } from "@/api/until";
import { listOpinion} from "@/api/skill/opinion";

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
    technoCode: {
      type: [String, String],
      default: ""
    },
  },
  watch: {
    "form.compCode": {
      handler(val) {
        this.getTreeDept(val);
      },
      deep: true,
      immediate: true
    },
    id: {
      handler(val) {
        this.reset();
        if (val) {
          this.id = val;
          this.title = "成果管理 / 查看审批详情";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "成果管理-新增";
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
    },
    technoCode: {
      handler(val) {
        this.reset();
        if (val) {
          this.queryParams.technoCode = val;
          console.log("this.queryParams",this.queryParams)
          this.getList();
        }
      },
      deep: true,
      immediate: true
    },
  },
  components: {
  },
  data() {
    return {
      // 审批状态字典
      auditStateOptions: [],
      // 审批意见表格数据
      opinionList: [],
      title: "成果管理-新增",
      openDialog: this.open,
      // 表单参数
      form: {},
      	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  		  isExtensionOptions: [],
	  	  	  	  	  	  	  		  approveOptions: [],
	  	  	  		  taskSourceOptions: [],
	  	  	  		  extensionScopeOptions: [],
	  	  	  		  achieveNatureOptions: [],
	  	  	  	  	  	  	  	  	  		  patentTypeOptions: [],
      deptList: [],
      //用户列表
      userOptions: [],
      //学历
      educationOptions: [],
      specialOptions: [],
      titleCodeOptions: [],
      queryParams: {
        compCode: this.$store.state.user.userInfo.deptId,
        technoCode: null,
        technoType: null,
      },
	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
      rules: {
        achieveCode: [
          { required: true, message: "成果编号不能为空", trigger: "blur" }
        ],
        achieveName: [
          { required: true, message: "成果名称不能为空", trigger: "blur" }
        ],
        deptCode: [
          { required: true, message: "科室编码不能为空", trigger: "blur" }
        ],
        completeUnit: [
          { required: true, message: "主要完成单位不能为空", trigger: "blur" }
        ],
        personCode: [
          { required: true, message: "完成人不能为空", trigger: "blur" }
        ],
        education: [
          { required: true, message: "完成人学历不能为空", trigger: "blur" }
        ],
        special: [
          { required: true, message: "完成人专业不能为空", trigger: "blur" }
        ],
        titleCode: [
          { required: true, message: "职称不能为空", trigger: "blur" }
        ],
        awardCategory: [
          { required: true, message: "获奖类别不能为空", trigger: "blur" }
        ],
        awardLevel: [
          { required: true, message: "获奖级别不能为空", trigger: "blur" }
        ],
        isExtension: [
          { required: true, message: "是否推广不能为空", trigger: "change" }
        ],
        awardTime: [
          { required: true, message: "获奖时间不能为空", trigger: "blur" }
        ],
        extensionIncome: [
          { required: true, message: "获奖收益不能为空", trigger: "blur" }
        ],
        approve: [
          { required: true, message: "批准单位不能为空", trigger: "change" }
        ],
        taskSource: [
          { required: true, message: "任务来源不能为空", trigger: "change" }
        ],
        extensionScope: [
          { required: true, message: "推广范围不能为空", trigger: "change" }
        ],
        achieveNature: [
          { required: true, message: "成果性质不能为空", trigger: "change" }
        ],
        drugApproval: [
          { required: true, message: "药批号不能为空", trigger: "blur" }
        ],
        patentIncome: [
          { required: true, message: "专利收益不能为空", trigger: "blur" }
        ],
        patentNo: [
          { required: true, message: "专利号不能为空", trigger: "blur" }
        ],
        patentType: [
          { required: true, message: "专利类别不能为空", trigger: "change" }
        ],
        subjectWords: [
          { required: true, message: "主题词不能为空", trigger: "blur" }
        ],
        achieveAbstract: [
          { required: true, message: "成果摘要不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("hr_is_change").then(response => {
      this.isExtensionOptions = response.data;
    });
    this.getDicts("SKILL_APPROVE").then(response => {
      this.approveOptions = response.data;
    });
    this.getDicts("SKILL_TASK_SOURCE").then(response => {
      this.taskSourceOptions = response.data;
    });
    this.getDicts("EXTENSION_SCOPE").then(response => {
      this.extensionScopeOptions = response.data;
    });
    this.getDicts("ACHIEVE_NATURE").then(response => {
      this.achieveNatureOptions = response.data;
    });
    this.getDicts("SKILL_PATENT_TYPE").then(response => {
      this.patentTypeOptions = response.data;
    });
    this.getDicts("GBT4658.2006").then(response => {
      this.educationOptions = response.data;
    });
    this.getDicts("DE02.01.037.00").then(response => {
      this.specialOptions = response.data;
    });
    this.getDicts("DE02.01.050.00").then(response => {
      this.titleCodeOptions = response.data;
    });
    this.getDicts("asset_aduit_state").then(response => {
      this.auditStateOptions = response.data;
    });
  },
  methods: {
    // 审批状态字典翻译
    auditStateFormat(row, column) {
      return this.selectDictLabel(this.auditStateOptions, row.auditState);
    },
    successSubmit() {
      this.$emit("success", false);
    },
    //获取科室列表
    getTreeDept(deptId) {
      let data = {
        deptId: this.$store.state.user.userInfo.deptId
      };
      this.formDeptList = [];
      treeRoom(data).then(response => {
        if (response.code == 200) {
          this.deptList = response.rows;
        } else {
          this.msgError(response.msg);
        }
      });
    },
  	getDetailInfo() {
      getAchieve(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询成果列表 */
    getList() {
      this.loading = true;
      listOpinion(this.queryParams).then(response => {
        this.opinionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 是否推广字典翻译
    isExtensionFormat(row, column) {
      return this.selectDictLabel(this.isExtensionOptions, row.isExtension);
    },
    // 批准单位字典翻译
    approveFormat(row, column) {
      return this.selectDictLabel(this.approveOptions, row.approve);
    },
    // 任务来源字典翻译
    taskSourceFormat(row, column) {
      return this.selectDictLabel(this.taskSourceOptions, row.taskSource);
    },
    // 推广范围字典翻译
    extensionScopeFormat(row, column) {
      return this.selectDictLabel(this.extensionScopeOptions, row.extensionScope);
    },
    // 成果性质字典翻译
    achieveNatureFormat(row, column) {
      return this.selectDictLabel(this.achieveNatureOptions, row.achieveNature);
    },
    // 专利类别字典翻译
    patentTypeFormat(row, column) {
      return this.selectDictLabel(this.patentTypeOptions, row.patentType);
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
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: null,
        achieveCode: null,
        achieveName: null,
        deptCode: null,
        completeUnit: null,
        personCode: null,
        education: null,
        special: null,
        titleCode: null,
        awardCategory: null,
        awardLevel: null,
        isExtension: null,
        awardTime: null,
        extensionIncome: null,
        approve: null,
        taskSource: null,
        extensionScope: null,
        achieveNature: null,
        drugApproval: null,
        patentIncome: null,
        patentNo: null,
        patentType: null,
        subjectWords: null,
        achieveAbstract: null,
        annex: null,
        auditState: null,
        procedureId: null,
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
        let saveLoading = this.saveLoading();
          if (this.form.id != null) {
            updateAchieve(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              saveLoading.close();
            });
          } else {
            addAchieve(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.$emit("success", false);
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
    height: 70%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
      .el-form-item__label{
        width: 120px !important;
      }
      .el-form-item__content{
        margin-left:120px !important;
      }
    }
  }
</style>
