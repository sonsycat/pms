<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
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
            <el-form-item label="负责人" prop="personCode">
              <el-input v-model="form.personCode" placeholder="请输入负责人"  maxlength="20" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年度" prop="year">
              <el-date-picker clearable size="small" class="w100"
                              v-model="form.year"
                              type="year"
                              value-format="yyyy"
                              placeholder="选择年度">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="刊物名称" prop="thesisName">
              <el-input v-model="form.thesisName" placeholder="请输入刊物名称"  maxlength="100" show-word-limit  />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="论文级别" prop="thesisType">
              <el-select v-model="form.thesisType" placeholder="请选择论文级别" class="w100">
                <el-option
                  v-for="dict in thesisTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="论文题目" prop="thesisSubject">
              <el-input v-model="form.thesisSubject" placeholder="请输入论文题目"  maxlength="20" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="期刊号" prop="issn">
              <el-input v-model="form.issn" placeholder="请输入期刊号"  maxlength="20" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="论文栏目" prop="thesisColumn">
              <el-input v-model="form.thesisColumn" placeholder="请输入论文栏目"  maxlength="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="刊物等级" prop="thesisGrade">
              <el-select v-model="form.thesisGrade" placeholder="请选择刊物等级" class="w100">
                <el-option
                  v-for="dict in thesisGradeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年" prop="thesisYear">
              <el-date-picker clearable size="small" class="w100"
                              v-model="form.thesisYear"
                              type="year"
                              value-format="yyyy"
                              placeholder="选择刊物年">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="期" prop="thesisStage">
              <el-input v-model="form.thesisStage" placeholder="请输入刊物期"  maxlength="36" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="卷" prop="thesisVolume">
              <el-input v-model="form.thesisVolume" placeholder="请输入刊物卷"  maxlength="36" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="会议名称" prop="meetName">
              <el-input v-model="form.meetName" placeholder="请输入会议名称"  maxlength="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出版社" prop="publish">
              <el-input v-model="form.publish" placeholder="请输入出版社"  maxlength="50" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="担任职务" prop="position">
              <el-select v-model="form.position" placeholder="请选择担任职务" class="w100">
                <el-option
                  v-for="dict in positionOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否统计源期刊" prop="isExtension">
              <el-select v-model="form.isExtension" placeholder="请选择是否统计源期刊" class="w100">
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
            <el-form-item label="出版时间" prop="publisTime">
              <el-date-picker clearable size="small" class="w100"
                              v-model="form.publisTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择出版时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="会议时间" prop="meetTime">
              <el-date-picker clearable size="small" class="w100"
                              v-model="form.meetTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择会议时间">
              </el-date-picker>
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
import { listThesis, getThesis, delThesis, addThesis, updateThesis,updateThesis1, exportThesis } from "@/api/skill/thesis";
import { treeRoom, getEmpList } from "@/api/until";
import btns from "@/views/skill/thesis/btns";
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
            this.title = "论文管理 / 修改";
          }else{
            this.title = "论文管理 / 新增";
          }
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "论文管理-新增";
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
      title: "论文管理-新增",
      openDialog: this.open,
      // 表单参数
      form: {},
      thesisTypeOptions: [],
      thesisGradeOptions: [],
      positionOptions: [],
      isExtensionOptions: [],
      deptList: [],
      //用户列表
      userOptions: [],
      // 表单校验
      rules: {
        thesisName: [
          { required: true, message: "刊物名称不能为空", trigger: "blur" }
        ],
        deptCode: [
          { required: true, message: "科室编码不能为空", trigger: "blur" }
        ],
        year: [
          { required: true, message: "年度不能为空", trigger: "blur" }
        ],
        thesisType: [
          { required: true, message: "论文级别不能为空", trigger: "change" }
        ],
        personCode: [
          { required: true, message: "负责人不能为空", trigger: "blur" }
        ],
        thesisSubject: [
          { required: true, message: "论文题目不能为空", trigger: "blur" }
        ],
        issn: [
          { required: true, message: "期刊号不能为空", trigger: "blur" }
        ],
        thesisColumn: [
          { required: true, message: "论文栏目不能为空", trigger: "blur" }
        ],
        thesisGrade: [
          { required: true, message: "刊物等级不能为空", trigger: "change" }
        ],
        thesisYear: [
          { required: true, message: "刊物年不能为空", trigger: "blur" }
        ],
        thesisStage: [
          { required: true, message: "刊物期不能为空", trigger: "blur" }
        ],
        thesisVolume: [
          { required: true, message: "刊物卷不能为空", trigger: "blur" }
        ],
        publish: [
          { required: true, message: "出版社不能为空", trigger: "blur" }
        ],
        position: [
          { required: true, message: "担任职务不能为空", trigger: "change" }
        ],
        isExtension: [
          { required: true, message: "是否统计源期刊不能为空", trigger: "change" }
        ],
        publisTime: [
          { required: true, message: "出版时间不能为空", trigger: "blur" }
        ],
        meetName: [
          { required: true, message: "会议名称不能为空", trigger: "blur" }
        ],
        meetTime: [
          { required: true, message: "会议时间不能为空", trigger: "blur" }
        ],
        assignee: [
          {
            required: true,
            message: "下一节点审核人不能为空",
            trigger: "change"
          }
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
    this.getDicts("skill_lwjb").then(response => {
      this.thesisTypeOptions = response.data;
    });
    this.getDicts("skill_kwdj").then(response => {
      this.thesisGradeOptions = response.data;
    });
    this.getDicts("skill_cbszw").then(response => {
      this.positionOptions = response.data;
    });
    this.getDicts("hr_is_change").then(response => {
      this.isExtensionOptions = response.data;
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
      getThesis(this.id).then(response => {
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
    /** 查询论文列表 */
    getList() {
      this.loading = true;
      listThesis(this.queryParams).then(response => {
        this.thesisList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 论文级别字典翻译
    thesisTypeFormat(row, column) {
      return this.selectDictLabel(this.thesisTypeOptions, row.thesisType);
    },
    // 刊物等级字典翻译
    thesisGradeFormat(row, column) {
      return this.selectDictLabel(this.thesisGradeOptions, row.thesisGrade);
    },
    // 担任职务字典翻译
    positionFormat(row, column) {
      return this.selectDictLabel(this.positionOptions, row.position);
    },
    // 是否统计源期刊字典翻译
    isExtensionFormat(row, column) {
      return this.selectDictLabel(this.isExtensionOptions, row.isExtension);
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
        thesisCode: null,
        thesisName: null,
        deptCode: null,
        year: null,
        thesisType: null,
        personCode: null,
        thesisSubject: null,
        issn: null,
        thesisColumn: null,
        thesisGrade: null,
        thesisYear: null,
        thesisStage: null,
        thesisVolume: null,
        publish: null,
        position: null,
        isExtension: null,
        publisTime: null,
        meetName: null,
        meetTime: null,
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
          this.form.compCode = this.$store.state.user.userInfo.deptId;
        let saveLoading = this.saveLoading();
          if (this.form.id != null) {
            updateThesis(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              saveLoading.close();
            });
          } else {
            addThesis(this.form).then(response => {
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
      updateThesis1(this.form).then(response => {
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
