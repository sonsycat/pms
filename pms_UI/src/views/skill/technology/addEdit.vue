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
            <staffSearch label="负责人" :staffParams.sync = "form" formItemProp="personCode" :rules="rules.personCode" inputProp="personCodeName"></staffSearch>
          </el-col>
          <el-col :span="12">
            <el-form-item label="完成例数" prop="completeNum">
              <el-input-number v-model="form.completeNum" max="99999" :min="1" :step="1" placeholder="请输入完成例数" maxlength="5" controls-position="right" step-strictly/>
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
        <formItem  :showDialog.sync="showDialog" :showBaseInfo="btn3" :open="btn4" :open2="btn5" :comment = "form" :userOptions="userOptions" @success="formItemSuccess"></formItem>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <btns v-if="btn3" @success="successSubmit" :open="btn3" :id="id" :proclnsIds="proclnsIds" :comment="form.comment" :assignee="form.assignee" ></btns>
        <el-button v-if="btn2"  type="primary" @click="submitFormContinue" >保存并提交</el-button>
        <el-button v-if="btn1"  type="primary" @click="submitForm" >保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import { listTechnology, getTechnology, delTechnology, addTechnology, updateTechnology, updateTechnology1, exportTechnology } from "@/api/skill/technology";
import { treeRoom, getEmpList } from "@/api/until";
import btns from "@/views/skill/technology/btns";
import formItem from "@/views/components/formItemNew";

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
    proclnsIds: {
      type: [String, Number],
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
          if(val!=0){
            this.title = "新业务新技术管理 / 修改";
          }else{
            this.title = "新业务新技术管理 / 新增";
          }
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
    }
  },
  components: {
    btns,formItem
  },
  data() {
    return {
      showDialog:false,
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
      },
      // 下级节点办理人
      nextAssignee: [],
      // 按钮编码
      btn1: false,
      btn2: false,
      btn3: false,
      btn4: false,
      btn5: false,
      datas: {},
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
  },
  methods: {
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
        let that = this;
        let proclnsIds = response.data.procedureId;
        this.datas = {
        };
        getEmpList(
          that,
          this.$store.state.user.name,
          proclnsIds,
          this.userOptions,
          this.btn1,
          this.btn2,
          this.btn3,
          this.btn4,
          this.btn5
        );
      });
    },
    /** 查询新业务新技术列表 */
    getList() {
      this.loading = true;
      listTechnology(this.queryParams).then(response => {
        this.technologyList = response.rows;
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
    submitFormContinue() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 下级审核人弹框
          this.showDialog = true
        }else{
          this.msgError("请输入必填项");
        }
      });
    },
    formItemSuccess(data) {
      this.form = JSON.parse(JSON.stringify(data))
      this.form.compCode = this.$store.state.user.userInfo.deptId;
      let saveLoading = this.saveLoading();
      updateTechnology1(this.form).then(response => {
        this.msgSuccess("修改成功");
        this.$emit("success", false);
        saveLoading.close()
      }).catch(()=>{
        saveLoading.close()
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
    .el-input-number--mini{
      width: 100%;
    }
  }
</style>
