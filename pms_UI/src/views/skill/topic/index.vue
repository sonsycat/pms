<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="编号" prop="topicCode">
        <el-input :style="{width: '240px'}"
          v-model="queryParams.topicCode"
          placeholder="请输入编号  "
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课题名称" prop="topicName">
        <el-input :style="{width: '240px'}"
          v-model="queryParams.topicName"
          placeholder="请输入课题名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年度" prop="topicYear">
        <el-date-picker clearable size="small" :style="{width: '240px'}"
                        v-model="queryParams.topicYear"
                        type="year"
                        value-format="yyyy"
                        placeholder="选择年度">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="课题类别" prop="topicType">
        <el-select v-model="queryParams.topicType" placeholder="请选择课题类别" clearable size="small" :style="{width: '240px'}" filterable clearable>
          <el-option
            v-for="dict in topicTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="科室" prop="deptCode">
        <el-select v-model="queryParams.deptCode" clearable placeholder="请选择科室" :style="{width: '240px'}" filterable clearable>
          <el-option
            v-for="item in deptList"
            :key="item.deptRoomId"
            :label="item.deptRoomName"
            :value="item.deptRoomId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="负责人" prop="personCode">
        <el-input :style="{width: '240px'}"
          v-model="queryParams.personName"
          placeholder="请输入负责人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker
          v-model="daterangeStartTime"
          :style="{width: '240px'}"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="终止时间" prop="endTime">
        <el-date-picker
          v-model="daterangeEndTime"
          :style="{width: '240px'}"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
          v-hasPermi="['skill:topic:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single || spzt"
          @click="handleUpdate"
          v-hasPermi="['skill:topic:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple  || spzt"
          @click="handleDelete"
          v-hasPermi="['skill:topic:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-notebook-2"
          size="mini"
          :disabled="single"
          @click="processExamine"
          v-hasPermi="['skill:topic:list']"
        >流转历史</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-notebook-2"
          size="mini"
          :disabled="single"
          @click="handleOpinion"
          v-hasPermi="['skill:topic:list']"
        >查看审批详情</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table border :height="tableHeight"  stripe v-loading="loading" :data="topicList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column show-overflow-tooltip sortable label="编号" align="left" prop="topicCode" />
      <el-table-column show-overflow-tooltip sortable label="年度" align="center" prop="topicYear" />
      <el-table-column show-overflow-tooltip sortable label="课题名称" align="left" prop="topicName" >
        <template slot-scope="scope">
          <el-link type="primary" :underline="false" v-html="scope.row.topicName" @click.stop="handleClickTableCode(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip sortable label="课题类别" align="center" prop="topicType" :formatter="topicTypeFormat" />
      <el-table-column show-overflow-tooltip sortable label="科室" align="left" prop="deptName" />
      <el-table-column show-overflow-tooltip sortable label="开始时间" align="center" prop="startTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip sortable label="终止时间" align="center" prop="endTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip sortable label="负责人" align="left" prop="personName" />
      <el-table-column show-overflow-tooltip sortable label="负责人学历" align="left" prop="education" :formatter="educationFormat" />
      <el-table-column show-overflow-tooltip sortable label="负责人职称" align="center" prop="titleCode" :formatter="titleCodeFormat" />
      <el-table-column show-overflow-tooltip sortable label="负责人专业" align="left" prop="special" :formatter="specialFormat" />
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

	<addEdit1 v-if="open1" :open="open1" :id="addEditId" @cancel="cancel1" :technoCode="topicCode" ></addEdit1>

    <!-- 流转历史对话框 -->
    <el-dialog title="流转历史" :visible.sync="processOpen" width="1200px" append-to-body :close-on-click-modal="false" custom-class="el-button--primary">
      <div v-loading="loading" :style="'height:'+ height">
        <iframe :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto" />
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTopic, getTopic, delTopic, addTopic, updateTopic, exportTopic } from "@/api/skill/topic";
import addEdit from "./addEdit";
import addEdit1 from "./addEdit1";
import opinion from "../opinion/index";
import { treeRoom, getEmpList } from "@/api/until";

export default {
  name: "Topic",
  components: {
addEdit,opinion,addEdit1
  },
  data() {
    return {
      topicCode: null,
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
      topicCodes: [],
      // 非单个禁用
      single: true,
      spzt: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 课题表格数据
      topicList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      open1: false,
      // 课题类别字典
      topicTypeOptions: [],
      // 经费投入类别字典
      fundTypeOptions: [],
      // 批准单位字典
      approveOptions: [],
      // 等级字典
      gradeOptions: [],
      //负责人学历
      educationOptions: [],
      specialOptions: [],
      titleCodeOptions: [],
      deptList: [],
      //时间范围
      daterangeStartTime: [],
      daterangeEndTime: [],
      // 状态字典
      stateOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        personName: null,
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: null,
        topicCode: null,
        topicName: null,
        deptCode: null,
        topicType: null,
        topicYear: null,
        startTime: null,
        endTime: null,
        fundType: null,
        funMoney: null,
        personCode: null,
        education: null,
        special: null,
        titleCode: null,
        approve: null,
        grade: null,
        cooperateUnit: null,
        annex: null,
        auditState: null,
        procedureId: null,
      },
      // 表单参数
      form: {},
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
      }
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
      this.stateOptions = response.data;
    });
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
  methods: {
    handleClickTableCode(data) {
      // 选中当前行
      this.rowClick(data, false);
      this.handleUpdate(data);
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
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    /** 查询课题列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeStartTime && '' != this.daterangeStartTime) {
        this.queryParams.params["beginStartTime"] = this.daterangeStartTime[0];
        this.queryParams.params["endStartTime"] = this.daterangeStartTime[1];
      }
      if (null != this.daterangeEndTime && '' != this.daterangeEndTime) {
        this.queryParams.params["beginEndTime"] = this.daterangeEndTime[0];
        this.queryParams.params["endEndTime"] = this.daterangeEndTime[1];
      }
      listTopic(this.queryParams).then(response => {
        this.topicList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    stateFormat(row, column) {
      return this.selectDictLabel(this.stateOptions, row.auditState);
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
    specialFormat(row, column) {
      return this.selectDictLabel(this.specialOptions, row.special);
    },
    titleCodeFormat(row, column) {
      return this.selectDictLabel(this.titleCodeOptions, row.titleCode);
    },
    educationFormat(row, column) {
      return this.selectDictLabel(this.educationOptions, row.education);
    },
	successSubmit(flag) {
      this.open = flag;
      this.getList();
    },
	successSubmit1(flag) {
      this.open1 = flag;
      this.getList();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮
    cancel1() {
      this.open1 = false;
      this.reset();
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
        topicType: null,
        startTime: null,
        endTime: null,
        fundType: null,
        funMoney: null,
        personCode: null,
        education: null,
        special: null,
        titleCode: null,
        approve: null,
        grade: null,
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
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeStartTime = [];
      this.daterangeEndTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.topicCodes = selection.map(item => item.topicCode)
      this.proclnsIds = selection.map(item => item.procedureId);
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
    handleOpinion(row) {
      this.reset();
      const topicCode = row.topicCode || this.topicCodes[0];
      const id = row.id || this.ids[0];
      this.addEditId = id; // id赋值
      this.open1 = true;
      this.topicCode = topicCode; // id赋值
      console.log("topicCode---" + topicCode);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTopic(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTopic(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除课题编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTopic(ids);
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
