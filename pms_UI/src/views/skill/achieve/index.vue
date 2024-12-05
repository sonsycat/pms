<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
      <el-form-item label="科室" prop="deptCode">
        <el-select v-model="queryParams.deptCode" clearable placeholder="请选择科室" filterable size="small" :style="{width: '240px'}">
          <el-option
            v-for="item in deptList"
            :key="item.deptRoomId"
            :label="item.deptRoomName"
            :value="item.deptRoomId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="成果名称" prop="achieveName">
        <el-input
          v-model="queryParams.achieveName"
          placeholder="请输入成果名称"
          clearable
          size="small" :style="{width: '240px'}"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="获奖时间" prop="awardTime">
        <el-date-picker
          v-model="daterangeTime"
          :style="{width: '240px'}"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="成果性质" prop="achieveNature">
        <el-select v-model="queryParams.achieveNature" placeholder="请选择成果性质" clearable size="small" :style="{width: '240px'}">
          <el-option
            v-for="dict in achieveNatureOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="获奖类别及等级" prop="awardLevel">
        <el-input
          v-model="queryParams.awardLevel"
          placeholder="请输入获奖类别及等级"
          clearable
          size="small" :style="{width: '240px'}"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务来源" prop="taskSource">
        <el-select v-model="queryParams.taskSource" placeholder="请选择任务来源" clearable size="small" :style="{width: '240px'}">
          <el-option
            v-for="dict in taskSourceOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="推广范围" prop="extensionScope">
        <el-select v-model="queryParams.extensionScope" placeholder="请选择推广范围" clearable size="small" :style="{width: '240px'}">
          <el-option
            v-for="dict in extensionScopeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态" prop="auditState">
        <el-select v-model="queryParams.auditState" placeholder="请选择审核状态" clearable size="small" :style="{width: '240px'}" filterable clearable>
          <el-option
            v-for="dict in stateOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
	</div>
	<div ref="btns">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['skill:achieve:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single || spzt"
          @click="handleUpdate"
          v-hasPermi="['skill:achieve:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple || spzt"
          @click="handleDelete"
          v-hasPermi="['skill:achieve:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAuthor"
          :disabled="single"
          v-hasPermi="['skill:achieve:add']"
        >编辑作者</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-notebook-2"
          size="mini"
          :disabled="single"
          @click="processExamine"
          v-hasPermi="['skill:achieve:list']"
        >流转历史</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-notebook-2"
          size="mini"
          :disabled="single"
          @click="handleOpinion"
          v-hasPermi="['skill:achieve:list']"
        >查看审批详情</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table :height="tableHeight" border stripe v-loading="loading" :data="achieveList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column show-overflow-tooltip sortable label="科室" align="left" prop="deptName" />
      <el-table-column show-overflow-tooltip sortable label="成果名称" align="left" prop="achieveName" >
        <template slot-scope="scope">
          <el-link type="primary" :underline="false" v-html="scope.row.achieveName" @click.stop="handleClickTableCode(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip sortable label="获奖时间" align="center" prop="awardTime" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.awardTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip sortable label="获奖类别及等级" align="center" prop="awardLevel" />
      <el-table-column show-overflow-tooltip sortable label="成果性质" align="center" prop="achieveNature" :formatter="achieveNatureFormat" />
      <el-table-column show-overflow-tooltip sortable label="任务来源" align="center" prop="taskSource" :formatter="taskSourceFormat" />
      <el-table-column show-overflow-tooltip sortable label="审核状态" align="center" prop="auditState" :formatter="stateFormat" >
        <template slot-scope="scope">
          <span :class="stateTextStyle(scope.row.auditState)">{{stateFormat(scope.row)}}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

	<addEdit v-if="open" :open="open" :proclnsIds="proclnsIds[0]" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>

    <addEdit1 v-if="open2" :open="open2" :id="addEditId" @cancel="cancel2" :technoCode="achieveCode" ></addEdit1>

    <!-- 流转历史对话框 -->
    <el-dialog title="流转历史" :visible.sync="processOpen" width="1200px" append-to-body :close-on-click-modal="false" custom-class="el-button--primary">
      <div v-loading="loading" :style="'height:'+ height">
        <iframe :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto" />
      </div>
    </el-dialog>

    <!-- 编辑作者对话框 -->
    <el-dialog :title="title1" class="prodCred" :visible.sync="open1" width="50%" append-to-body :close-on-click-modal="false" custom-class="el-button--primary">
      <el-form ref="postForm" :model="postForm" >
        <el-row>
          <el-col :span="4" style="float:right;text-align:right;">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAddTable"
            >添加行</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-table border stripe v-loading="loading" :data="personList" ref="costNumListTable" >
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column sortable label="参与人或者作者" align="center" prop="personCode" >
                <template slot-scope="scope">
                  <el-input v-model="scope.row.personCode" placeholder="请输入参与人或者作者"  maxlength="50" show-word-limit  />
                </template>
              </el-table-column>
              <el-table-column sortable label="是否第一作者" align="center" prop="isOne" >
                <template slot-scope="scope">
                  <el-select v-model="scope.row.isOne" placeholder="请选择是否第一作者" clearable size="small">
                    <el-option
                      v-for="dict in isOneOptions"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="操作" fixed="right" align="center">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    icon="el-icon-delete" circle
                    type="danger"
                    @click="handleAddDelete(scope.$index, scope.row)"></el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm1">保 存</el-button>
        <el-button @click="cancel1">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listAchieve, getAchieve, delAchieve, addAchieve, updateAchieve, exportAchieve } from "@/api/skill/achieve";
import addEdit from "./addEdit";
import addEdit1 from "./addEdit1";
import { treeRoom, getEmpList } from "@/api/until";
import { listPerson, getPerson, delPerson, addPerson, updatePerson, exportPerson } from "@/api/skill/person";

export default {
  name: "Achieve",
  components: {
addEdit,addEdit1
  },
  data() {
    return {
      achieveCode: null,
      postForm: {},
      //打印参数
      src: "",
      height: "500px;",
      processOpen: false,
      tableHeight: "auto",
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      proclnsIds: [],
      achieveCodes: [],
      // 非单个禁用
      single: true,
      spzt: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 成果表格数据
      achieveList: [],
      selectionRow: [],
      // 技术参与人表格数据
      personList: [],
      // 弹出层标题
      title: "",
      title1: "成果 / 编辑作者",
      // 是否显示弹出层
      open: false,
      open1: false,
      open2: false,
      // 是否推广字典
      isExtensionOptions: [],
      // 批准单位字典
      approveOptions: [],
      // 任务来源字典
      taskSourceOptions: [],
      // 推广范围字典
      extensionScopeOptions: [],
      // 成果性质字典
      achieveNatureOptions: [],
      // 专利类别字典
      patentTypeOptions: [],
      // 状态字典
      stateOptions: [],
      deptList: [],
      // 是否第一作者字典
      isOneOptions: [],
      //时间范围
      daterangeTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
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
      },
      // 查询参数
      queryParams1: {
        compCode: this.$store.state.user.userInfo.deptId,
        technoCode: null,
        technoType: "2",
        personList: []
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        achieveName: [
          { required: true, message: "成果名称不能为空", trigger: "blur" }
        ],
        deptCode: [
          { required: true, message: "科室编码不能为空", trigger: "blur" }
        ],
        completeUnit: [
          { required: true, message: "协作单位不能为空", trigger: "blur" }
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
  watch:{
    "form.compCode": {
      handler(val) {
        this.getTreeDept(val);
      },
      deep: true,
      immediate: true
    },
    listData(val) {
      var that = this;
      setTimeout(function() {
        that.resizeHandler();
      }, 300);
    },
  },
  mounted(){
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
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
    this.getDicts("hr_is_change").then(response => {
      this.isOneOptions = response.data;
    });
    this.getDicts("asset_aduit_state").then(response => {
      this.stateOptions = response.data;
    });
  },
  methods: {
    handleClickTableCode(data) {
      // 选中当前行
      this.rowClick(data, false);
      this.handleUpdate(data);
    },
    /** 新增添加行 */
    handleAddTable() {
      let params={
        compCode: this.$store.state.user.accountInfo.deptId,
        nowDate:this.form.nowDate
      }
      let tablCol={
        // deptCode:this.form.deptCode,
        // nowDate:this.form.nowDate,
        // projectCode:null
      }
      this.personList.push(tablCol);
      this.tableScrollLeft("costNumListTable")
    },
    handleAddDelete(idx) {
      this.personList.splice(idx, 1);
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
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
    /** 查询成果列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeTime && '' != this.daterangeTime) {
        this.queryParams.params["beginTime"] = this.daterangeTime[0];
        this.queryParams.params["endTime"] = this.daterangeTime[1];
      }
      listAchieve(this.queryParams).then(response => {
        this.achieveList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 是否第一作者字典翻译
    isOneFormat(row, column) {
      return this.selectDictLabel(this.isOneOptions, row.isOne);
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
    stateFormat(row, column) {
      return this.selectDictLabel(this.stateOptions, row.auditState);
    },
	successSubmit(flag) {
      this.open = flag;
      this.getList();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    cancel1() {
      this.open1 = false;
    },
    cancel2() {
      this.open2 = false;
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
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.achieveCodes = selection.map(item => item.achieveCode)
      this.proclnsIds = selection.map(item => item.procedureId)
      this.single = selection.length!==1
      this.spzt = true;
      if (selection.length) {
        let reviewState = selection[0].auditState;
        console.log(reviewState)
        if(reviewState=='0'){
          this.spzt = false;
        }else{
          this.spzt = true;
        }
      }
      this.multiple = !selection.length
      this.selectionRow=selection
    },
    rowClick(row, column, event) {
      let refsElTable = this.$refs.multipleTable;
      let findRow = this.selectionRow.find(c => c.rowIndex == row.rowIndex);
      // if (findRow && this.selectionRow.length == 1) {
      //   refsElTable.toggleRowSelection(row, false);
      //   return;
      // }
      refsElTable.clearSelection();
      refsElTable.toggleRowSelection(row);
    },
    rowStyle({
      row,
      rowIndex
    }) {
      Object.defineProperty(row, 'rowIndex', {
        value: rowIndex,
        writable: true,
        enumerable: false
      })
    },
    rowClassName({
      row,
      rowIndex
    }) {
      let rowName = "",
        findRow = this.selectionRow.find(c => c.rowIndex === row.rowIndex);
      if (findRow) {
        rowName = "current-row ";
      }
      return rowName;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$refs.multipleTable.clearSelection();
      this.open = true;
      this.addEditId = "0";
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      this.open = true;
      this.addEditId = id; // id赋值
      console.log("addEditId---" + addEdit);
    },
    handleAuthor(row) {
      this.reset();
      const id = row.id || this.ids[0];
      const achieveCode = row.achieveCode || this.achieveCodes[0];
      this.open1 = true;
      this.queryParams1.technoCode = achieveCode; // id赋值
      this.queryParams1.personList = [];
      console.log("achieveCode---" + achieveCode);
      listPerson(this.queryParams1).then(response => {
        this.personList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAchieve(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAchieve(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    handleOpinion(row) {
      this.reset();
      const achieveCode = row.achieveCode || this.achieveCodes[0];
      const id = row.id || this.ids[0];
      this.addEditId = id; // id赋值
      this.open2 = true;
      this.achieveCode = achieveCode; // id赋值
      console.log("achieveCode---" + achieveCode);
    },
    submitForm1() {
      this.queryParams1.personList = this.personList;
      console.log("personList",this.personList)
      let saveLoading = this.saveLoading();
      let num = 0;
      for (let i=0; i<this.personList.length; i++){
        num += Number(this.personList[i].isOne);
      }
      if (num != this.personList.length - 1) {
        saveLoading.close();
        this.msgError("只能有一个第一作者");
        return true;
      }
      updatePerson(this.queryParams1).then(response => {
        this.msgSuccess("编辑作者成功");
        this.open1 = false;
        saveLoading.close();
        this.getList();
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除成果编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delAchieve(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 流转历史初始化 */
    processExamineForm2() {
      this.src =
        `${this.processUrl}/api/wf/processHistoric?procInsId=${this.proclnsIds[0]}`;
    },
    /** 流转历史按钮操作 */
    processExamine() {
      if (this.proclnsIds[0] == null || !this.proclnsIds[0]) {
        this.msgError("没有流转历史数据！");
      } else {
        this.processExamineForm2();
        this.processOpen = true;
      }
    }
  }
};
</script>
