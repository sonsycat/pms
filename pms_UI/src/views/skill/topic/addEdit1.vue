<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="课题编号" prop="topicCode">
              <el-input v-model="form.topicCode" placeholder="请输入课题编号"  maxlength="20" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="课题名称" prop="topicName">
              <el-input v-model="form.topicName" placeholder="请输入课题名称"  maxlength="100" show-word-limit  />
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
            <el-form-item label="课题类别" prop="topicType">
              <el-select v-model="form.topicType" placeholder="请选择课题类别" class="w100" filterable clearable>
                <el-option
                  v-for="dict in topicTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年度" prop="topicYear">
              <el-date-picker clearable size="small" class="w100"
                              v-model="form.topicYear"
                              type="year"
                              value-format="yyyy"
                              placeholder="选择年度">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startTime">
              <el-date-picker clearable size="small" class="w100"
                v-model="form.startTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择开始时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="终止时间" prop="endTime">
              <el-date-picker clearable size="small" class="w100"
                v-model="form.endTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择终止时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="经费投入类别" prop="fundType">
              <el-select v-model="form.fundType" placeholder="请选择经费投入类别" class="w100" filterable clearable>
                <el-option
                  v-for="dict in fundTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="经费投入金额" prop="funMoney">
              <el-input-number v-model="form.funMoney" :precision="2" controls-position="right" :min="0" :step="1" @click.native.stop class="w100"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <staffSearch label="负责人" :staffParams.sync = "form" formItemProp="personCode" :rules="rules.personCode" inputProp="personCodeName"></staffSearch>
          </el-col>
          <el-col :span="12">
            <el-form-item label="负责人学历" prop="education">
              <el-select v-model="form.education" placeholder="请选择负责人学历" class="w100" filterable clearable>
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
            <el-form-item label="负责人专业" prop="special">
              <el-select v-model="form.special" placeholder="请选择负责人专业" class="w100" filterable clearable>
                <el-option
                  v-for="dict in specialOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="负责人职称" prop="titleCode">
              <el-select v-model="form.titleCode" placeholder="请选择负责人职称" class="w100" filterable clearable>
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
            <el-form-item label="批准单位" prop="approve">
              <el-select v-model="form.approve" placeholder="请选择批准单位" class="w100" filterable clearable>
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
            <el-form-item label="等级" prop="grade">
              <el-select v-model="form.grade" placeholder="请选择等级" class="w100" filterable clearable>
                <el-option
                  v-for="dict in gradeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="协作单位" prop="cooperateUnit">
              <el-input v-model="form.cooperateUnit" placeholder="请输入协作单位"  maxlength="100" show-word-limit  />
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
import { listTopic, getTopic, delTopic, addTopic, updateTopic, updateTopic1, exportTopic } from "@/api/skill/topic";
import { treeRoom, getEmpList } from "@/api/until";
import {getYear} from "@/utils/date";
import btns from "@/views/skill/topic/btns";
import formItem from "@/views/components/formItemNew";
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
    proclnsIds: {
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
          if(val!=0){
            this.title = "课题管理 / 查看审批详情";
          }else{
            this.title = "课题管理 / 新增";
          }
          this.getDetailInfo();
          this.popoverD = true;
        } else {
          this.id = "";
          this.title = "课题管理-新增";
          this.popoverD = false;
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
    btns,formItem
  },
  data() {
    return {
      // 审批状态字典
      auditStateOptions: [],
      // 审批意见表格数据
      opinionList: [],
      showDialog:false,
      popoverD: false,
      title: "课题管理-新增",
      openDialog: this.open,
      // 表单参数
      form: {},
      topicTypeOptions: [],
	  	fundTypeOptions: [],
	  	approveOptions: [],
	  	gradeOptions: [],
      //负责人学历
      educationOptions: [],
      specialOptions: [],
      titleCodeOptions: [],
      deptList: [],
      //用户列表
      userOptions: [],
      // 查询参数
      queryParams: {
        compCode: this.$store.state.user.userInfo.deptId,
        technoCode: null,
        technoType: null,
      },
      // 表单校验
      rules: {
        topicCode: [
          { required: true, message: "课题编码不能为空", trigger: "blur" }
        ],
        topicName: [
          { required: true, message: "课题名称不能为空", trigger: "blur" }
        ],
        deptCode: [
          { required: true, message: "科室编码不能为空", trigger: "blur" }
        ],
        topicYear: [
          { required: true, message: "年度不能为空", trigger: "change" }
        ],
        topicType: [
          { required: true, message: "课题类别不能为空", trigger: "change" }
        ],
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" }
        ],
        endTime: [
          { required: true, message: "结束时间不能为空", trigger: "blur" }
        ],
        fundType: [
          { required: true, message: "经费投入类别不能为空", trigger: "change" }
        ],
        funMoney: [
          { required: true, message: "经费投入金额不能为空", trigger: "change" }
        ],
        personCode: [
          { required: true, message: "负责人不能为空", trigger: ["blur","change"] }
        ],
        personCodeName: [
          { required: true, message: "负责人不能为空", trigger: "blur" }
        ],
        education: [
          { required: true, message: "负责人学历不能为空", trigger: "change" }
        ],
        special: [
          { required: true, message: "负责人专业不能为空", trigger: "change" }
        ],
        titleCode: [
          { required: true, message: "负责人职称不能为空", trigger: "change" }
        ],
        approve: [
          { required: true, message: "批准单位不能为空", trigger: "change" }
        ],
        grade: [
          { required: true, message: "等级不能为空", trigger: "change" }
        ],
        cooperateUnit: [
          { required: true, message: "写作单位不能为空", trigger: "blur" }
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
    this.getDicts("skill_ktlb").then(response => {
      this.topicTypeOptions = response.data;
    });
    this.getDicts("skill_jftrlb").then(response => {
      this.fundTypeOptions = response.data;
    });
    this.getDicts("skill_pzdw").then(response => {
      this.approveOptions = response.data;
    });
    this.getDicts("skill_dj").then(response => {
      this.gradeOptions = response.data;
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
      getTopic(this.id).then(response => {
        console.log(response);
        console.log("proclnsIds",this.proclnsIds);
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
    /** 查询课题列表 */
    getList() {
      this.loading = true;
      listOpinion(this.queryParams).then(response => {
        this.opinionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 课题类别字典翻译
    topicTypeFormat(row, column) {
      return this.selectDictLabel(this.topicTypeOptions, row.topicType);
    },
    // 经费投入类别字典翻译
    fundTypeFormat(row, column) {
      return this.selectDictLabel(this.fundTypeOptions, row.fundType);
    },
    // 批准单位字典翻译
    approveFormat(row, column) {
      return this.selectDictLabel(this.approveOptions, row.approve);
    },
    // 等级字典翻译
    gradeFormat(row, column) {
      return this.selectDictLabel(this.gradeOptions, row.grade);
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
        topicCode: null,
        topicName: null,
        deptCode: null,
        topicType: "1",
        topicYear: getYear().substring(0,4),
        startTime: getYear(),
        endTime: getYear(),
        fundType: "1",
        funMoney: null,
        personCode: null,
        personCodeName: null,
        education: "10",
        special: "01",
        titleCode: "1",
        approve: "1",
        grade: "1",
        cooperateUnit: null,
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
            updateTopic(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              saveLoading.close();
            });
          } else {
            addTopic(this.form).then(response => {
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
      updateTopic1(this.form).then(response => {
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
  }
</style>
