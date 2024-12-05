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
              <el-input-number v-model="form.wordNum" max="9999999" :min="1" :step="1" placeholder="请输入字数" maxlength="10" controls-position="right" step-strictly/>
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
              <el-input-number v-model="form.priteNum" max="9999999" :min="1" :step="1" placeholder="请输入印刷数量" maxlength="10" controls-position="right" step-strictly show-word-limit/>
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
import { listMonograph, getMonograph, delMonograph, addMonograph, updateMonograph, updateMonograph1, exportMonograph } from "@/api/skill/monograph";
import { treeRoom, getEmpList } from "@/api/until";
import btns from "@/views/skill/monograph/btns";
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
            this.title = "专著管理 / 修改";
          }else{
            this.title = "专著管理 / 新增";
          }
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
    }
  },
  components: {
    btns,formItem
  },
  data() {
    return {
      showDialog:false,
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
      this.monographTypeOptions = response.data;
    });
    this.getDicts("SKILL_FOLIO").then(response => {
      this.folioOptions = response.data;
    });
    this.getDicts("SKILL_POSITION").then(response => {
      this.positionOptions = response.data;
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
      getMonograph(this.id).then(response => {
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
    /** 查询专著列表 */
    getList() {
      this.loading = true;
      listMonograph(this.queryParams).then(response => {
        this.monographList = response.rows;
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
      updateMonograph1(this.form).then(response => {
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
