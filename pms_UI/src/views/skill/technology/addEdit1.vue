<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="名称" prop="technoName">
              <el-input v-model="form.technoName" placeholder="请输入名称"  maxlength="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开展时间" prop="developTime">
              <el-date-picker clearable size="small" class="w100"
                              v-model="form.developTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择开展时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="负责人" prop="personCode">
              <el-input v-model="form.personCode" placeholder="请输入负责人"  maxlength="20" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="完成例数" prop="completeNum">
              <el-input v-model="form.completeNum" placeholder="请输入完成例数"  maxlength="5" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="科室" prop="deptCode">
              <el-select v-model="form.deptCode" clearable placeholder="请选择科室" class="w100" filterable clearable>
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
            <el-form-item label="水平" prop="technoType">
              <el-select v-model="form.technoType" placeholder="请选择水平" class="w100">
                <el-option
                  v-for="dict in technoTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="效果" prop="effect">
              <el-select v-model="form.effect" placeholder="请选择效果" class="w100">
                <el-option
                  v-for="dict in effectOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="新业务新技术" prop="techno">
              <el-select v-model="form.techno" placeholder="请选择新业务新技术" class="w100">
                <el-option
                  v-for="dict in technoOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="负责人学历" prop="education">
              <el-select v-model="form.education" placeholder="请选择负责人学历" class="w100">
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
            <el-form-item label="负责人职称" prop="titleCode">
              <el-select v-model="form.titleCode" placeholder="请选择负责人职称" class="w100">
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
            <el-form-item label="进展情况" prop="progress">
              <el-input v-model="form.progress" placeholder="请输入进展情况"  maxlength="240" show-word-limit  />
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
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import { listTechnology, getTechnology, delTechnology, addTechnology, updateTechnology, exportTechnology } from "@/api/skill/technology";
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
          this.title = "新业务新技术管理 / 查看审批详情";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "新业务新技术管理-新增";
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
      title: "新业务新技术管理-新增",
      openDialog: this.open,
      // 表单参数
      form: {},
      	  	  	  	  	  	  	  	  	  	  	  		  technoTypeOptions: [],
	  	  	  	  	  	  	  	  	  		  educationOptions: [],
	  	  	  		  specialOptions: [],
	  	  	  		  titleCodeOptions: [],
	  	  	  		  effectOptions: [],
	  	  	  		  technoOptions: [],
      deptList: [],
      //用户列表
      userOptions: [],
      queryParams: {
        compCode: this.$store.state.user.userInfo.deptId,
        technoCode: null,
        technoType: null,
      },
	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
      rules: {
        technoCode: [
          { required: true, message: "编码不能为空", trigger: "blur" }
        ],
        technoName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        developTime: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        technoType: [
          { required: true, message: "水平不能为空", trigger: "change" }
        ],
        deptCode: [
          { required: true, message: "科室编码不能为空", trigger: "blur" }
        ],
        completeNum: [
          { required: true, message: "完成例数不能为空", trigger: "blur" }
        ],
        personCode: [
          { required: true, message: "负责人不能为空", trigger: "blur" }
        ],
        education: [
          { required: true, message: "负责人学历不能为空", trigger: "change" }
        ],
        special: [
          { required: true, message: "负责人专业不能为空", trigger: "change" }
        ],
        titleCode: [
          { required: true, message: "职称不能为空", trigger: "change" }
        ],
        effect: [
          { required: true, message: "效果不能为空", trigger: "change" }
        ],
        techno: [
          { required: true, message: "新业务新技术不能为空", trigger: "change" }
        ],
        progress: [
          { required: true, message: "进展情况不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("SKILL_TECHNO_TYPE").then(response => {
      this.technoTypeOptions = response.data;
    });
    this.getDicts("DE02.01.041.00").then(response => {
      this.educationOptions = response.data;
    });
    this.getDicts("DE02.01.037.00").then(response => {
      this.specialOptions = response.data;
    });
    this.getDicts("DE02.01.050.00").then(response => {
      this.titleCodeOptions = response.data;
    });
    this.getDicts("SKILL_EFFECT").then(response => {
      this.effectOptions = response.data;
    });
    this.getDicts("SKILL_TECHNO").then(response => {
      this.technoOptions = response.data;
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
      getTechnology(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询新业务新技术列表 */
    getList() {
      this.loading = true;
      listOpinion(this.queryParams).then(response => {
        this.opinionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 水平字典翻译
    technoTypeFormat(row, column) {
      return this.selectDictLabel(this.technoTypeOptions, row.technoType);
    },
    // 负责人学历字典翻译
    educationFormat(row, column) {
      return this.selectDictLabel(this.educationOptions, row.education);
    },
    // 负责人专业字典翻译
    specialFormat(row, column) {
      return this.selectDictLabel(this.specialOptions, row.special);
    },
    // 职称字典翻译
    titleCodeFormat(row, column) {
      return this.selectDictLabel(this.titleCodeOptions, row.titleCode);
    },
    // 效果字典翻译
    effectFormat(row, column) {
      return this.selectDictLabel(this.effectOptions, row.effect);
    },
    // 新业务新技术字典翻译
    technoFormat(row, column) {
      return this.selectDictLabel(this.technoOptions, row.techno);
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
        technoCode: null,
        technoName: null,
        developTime: null,
        technoType: null,
        deptCode: null,
        completeNum: null,
        personCode: null,
        education: null,
        special: null,
        titleCode: null,
        effect: null,
        techno: null,
        progress: null,
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
          this.form.compCode = this.$store.state.user.userInfo.deptId;
          if (this.form.id != null) {
            updateTechnology(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              saveLoading.close();
            });
          } else {
            addTechnology(this.form).then(response => {
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
    height: 60%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
