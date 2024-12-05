<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="年度" prop="year">
        <el-date-picker clearable size="small" :style="{width: '240px'}"
                        v-model="queryParams.year"
                        type="year"
                        value-format="yyyy"
                        placeholder="选择年度">
        </el-date-picker>
      </el-form-item>
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
      <el-form-item label="刊物名称" prop="thesisName">
        <el-input :style="{width: '240px'}"
          v-model="queryParams.thesisName"
          placeholder="请输入刊物名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="论文题目" prop="thesisSubject">
        <el-input :style="{width: '240px'}"
          v-model="queryParams.thesisSubject"
          placeholder="请输入论文题目"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="论文栏目" prop="thesisColumn">
        <el-input :style="{width: '240px'}"
          v-model="queryParams.thesisColumn"
          placeholder="请输入论文栏目"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="刊物等级" prop="thesisGrade">
        <el-select v-model="queryParams.thesisGrade" placeholder="请选择刊物等级" clearable size="small" :style="{width: '240px'}">
          <el-option
            v-for="dict in thesisGradeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="年" prop="thesisYear">
        <el-input :style="{width: '240px'}"
          v-model="queryParams.thesisYear"
          placeholder="请输入刊物年"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="期" prop="thesisStage">
        <el-input :style="{width: '240px'}"
          v-model="queryParams.thesisStage"
          placeholder="请输入刊物期"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人" prop="personCode">
        <el-input :style="{width: '240px'}"
          v-model="queryParams.personCode"
          placeholder="请输入负责人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审核状态" prop="auditState">
        <el-select v-model="queryParams.auditState" placeholder="请选择审核状态" clearable size="small" filterable clearable :style="{width: '240px'}">
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
          v-hasPermi="['skill:thesis:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single || spzt"
          @click="handleUpdate"
          v-hasPermi="['skill:thesis:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple || spzt"
          @click="handleDelete"
          v-hasPermi="['skill:thesis:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-notebook-2"
          size="mini"
          :disabled="single"
          @click="processExamine"
          v-hasPermi="['skill:thesis:list']"
        >流转历史</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-notebook-2"
          size="mini"
          :disabled="single"
          @click="handleOpinion"
          v-hasPermi="['skill:thesis:list']"
        >查看审批详情</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table border :height="tableHeight" stripe v-loading="loading" :data="thesisList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column show-overflow-tooltip sortable label="年度" align="center" prop="year" />
      <el-table-column show-overflow-tooltip sortable label="科室" align="left" prop="deptName" />
      <el-table-column show-overflow-tooltip sortable label="刊物名称" align="left" prop="thesisName" >
        <template slot-scope="scope">
          <el-link type="primary" :underline="false" v-html="scope.row.thesisName" @click.stop="handleClickTableCode(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip sortable label="论文题目" align="left" prop="thesisSubject" />
      <el-table-column show-overflow-tooltip sortable label="论文栏目" align="left" prop="thesisColumn" />
      <el-table-column show-overflow-tooltip sortable label="刊物等级" align="center" prop="thesisGrade" :formatter="thesisGradeFormat" />
      <el-table-column show-overflow-tooltip sortable label="年" align="center" prop="thesisYear" />
      <el-table-column show-overflow-tooltip sortable label="期" align="center" prop="thesisStage" />
      <el-table-column show-overflow-tooltip sortable label="负责人" align="center" prop="personCode" />
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

    <addEdit1 v-if="open1" :open="open1" :id="addEditId" @cancel="cancel1" :technoCode="thesisCode" ></addEdit1>

    <!-- 流转历史对话框 -->
    <el-dialog title="流转历史" :visible.sync="processOpen" width="1200px" append-to-body :close-on-click-modal="false" custom-class="el-button--primary">
      <div v-loading="loading" :style="'height:'+ height">
        <iframe :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto" />
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listThesis, getThesis, delThesis, addThesis, updateThesis, exportThesis } from "@/api/skill/thesis";
import addEdit from "./addEdit";
import addEdit1 from "./addEdit1";
import opinion from "../opinion/index";
import { treeRoom, getEmpList } from "@/api/until";

export default {
  name: "Thesis",
  components: {
addEdit,opinion,addEdit1
  },
  data() {
    return {
      thesisCode: null,
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
      thesisCodes: [],
      proclnsIds: [],
      // 非单个禁用
      single: true,
      spzt: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 论文表格数据
      thesisList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      open1: false,
      // 论文级别字典
      thesisTypeOptions: [],
      // 刊物等级字典
      thesisGradeOptions: [],
      // 担任职务字典
      positionOptions: [],
      // 是否统计源期刊字典
      isExtensionOptions: [],
      // 状态字典
      stateOptions: [],
      deptList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        personName: null,
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
      },
      // 表单参数
      form: {},
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
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.thesisCodes = selection.map(item => item.thesisCode)
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
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateThesis(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addThesis(this.form).then(response => {
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
      this.$confirm('是否确认删除论文编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delThesis(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    handleOpinion(row) {
      this.reset();
      const thesisCode = row.thesisCode || this.thesisCodes[0];
      const id = row.id || this.ids[0];
      this.addEditId = id; // id赋值
      this.open1 = true;
      this.thesisCode = thesisCode; // id赋值
      console.log("thesisCode---" + thesisCode);
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
