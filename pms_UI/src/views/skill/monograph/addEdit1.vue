<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
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
            <el-form-item label="专著名称" prop="monographName">
              <el-input v-model="form.monographName" placeholder="请输入专著名称"  maxlength="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="专著级别" prop="monographType">
              <el-select v-model="form.monographType" placeholder="请选择专著级别" class="w100">
                <el-option
                  v-for="dict in monographTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出版社" prop="publish">
              <el-input v-model="form.publish" placeholder="请输入出版社"  maxlength="50" show-word-limit  />
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
            <el-form-item label="期刊号" prop="issn">
              <el-input v-model="form.issn" placeholder="请输入期刊号"  maxlength="20" show-word-limit  />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="杂志名称" prop="magazine">
              <el-input v-model="form.magazine" placeholder="请输入杂志名称"  maxlength="120" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="字数" prop="wordNum">
              <el-input v-model="form.wordNum" placeholder="请输入字数"  maxlength="10" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="图书编号" prop="bookNumber">
              <el-input v-model="form.bookNumber" placeholder="请输入图书编号"  maxlength="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开本" prop="folio">
              <el-select v-model="form.folio" placeholder="请选择开本" class="w100">
                <el-option
                  v-for="dict in folioOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="印刷数量" prop="priteNum">
              <el-input v-model="form.priteNum" placeholder="请输入印刷数量"  maxlength="10" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="会议名称" prop="meetName">
              <el-input v-model="form.meetName" placeholder="请输入会议名称"  maxlength="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="作者" prop="author">
              <el-input v-model="form.author" placeholder="请输入作者"  maxlength="50" show-word-limit  />
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
import { listMonograph, getMonograph, delMonograph, addMonograph, updateMonograph, exportMonograph } from "@/api/skill/monograph";
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
          this.title = "专著管理 / 查看审批详情";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "专著管理-新增";
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
      title: "专著管理-新增",
      openDialog: this.open,
      // 表单参数
      form: {},
      	  	  	  	  	  	  	  	  	  	  	  		  monographTypeOptions: [],
	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  		  folioOptions: [],
	  	  	  	  	  		  positionOptions: [],
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
        monographCode: [
          { required: true, message: "专著编码不能为空", trigger: "blur" }
        ],
        monographName: [
          { required: true, message: "专著名称不能为空", trigger: "blur" }
        ],
        monographType: [
          { required: true, message: "专著级别不能为空", trigger: "change" }
        ],
        deptCode: [
          { required: true, message: "科室编码不能为空", trigger: "blur" }
        ],
        publish: [
          { required: true, message: "出版社不能为空", trigger: "blur" }
        ],
        publisTime: [
          { required: true, message: "出版时间不能为空", trigger: "blur" }
        ],
        issn: [
          { required: true, message: "期刊号不能为空", trigger: "blur" }
        ],
        magazine: [
          { required: true, message: "杂志名称不能为空", trigger: "blur" }
        ],
        wordNum: [
          { required: true, message: "字数不能为空", trigger: "blur" }
        ],
        author: [
          { required: true, message: "作者不能为空", trigger: "blur" }
        ],
        bookNumber: [
          { required: true, message: "图书编号不能为空", trigger: "blur" }
        ],
        folio: [
          { required: true, message: "开本不能为空", trigger: "change" }
        ],
        priteNum: [
          { required: true, message: "印刷数量不能为空", trigger: "blur" }
        ],
        position: [
          { required: true, message: "担任职务不能为空", trigger: "change" }
        ],
        meetName: [
          { required: true, message: "会议名称不能为空", trigger: "blur" }
        ],
        meetTime: [
          { required: true, message: "会议时间不能为空", trigger: "blur" }
        ],
        annex: [
          { required: true, message: "附件不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("skill_lwjb").then(response => {
      this.monographTypeOptions = response.data;
    });
    this.getDicts("SKILL_FOLIO").then(response => {
      this.folioOptions = response.data;
    });
    this.getDicts("SKILL_POSITION").then(response => {
      this.positionOptions = response.data;
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
      getMonograph(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询专著列表 */
    getList() {
      this.loading = true;
      listOpinion(this.queryParams).then(response => {
        this.opinionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 专著级别字典翻译
    monographTypeFormat(row, column) {
      return this.selectDictLabel(this.monographTypeOptions, row.monographType);
    },
    // 开本字典翻译
    folioFormat(row, column) {
      return this.selectDictLabel(this.folioOptions, row.folio);
    },
    // 担任职务字典翻译
    positionFormat(row, column) {
      return this.selectDictLabel(this.positionOptions, row.position);
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
        monographCode: null,
        monographName: null,
        monographType: null,
        deptCode: null,
        publish: null,
        publisTime: null,
        issn: null,
        magazine: null,
        wordNum: null,
        author: null,
        bookNumber: null,
        folio: null,
        priteNum: null,
        position: null,
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
        let saveLoading = this.saveLoading();
          this.form.compCode = this.$store.state.user.userInfo.deptId;
          if (this.form.id != null) {
            updateMonograph(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              saveLoading.close();
            });
          } else {
            addMonograph(this.form).then(response => {
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
