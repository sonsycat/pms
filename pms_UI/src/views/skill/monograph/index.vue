<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="80px">
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
      <el-form-item label="专著名称" prop="monographName">
        <el-input
          v-model="queryParams.monographName"
          placeholder="请输入专著名称"
          clearable
          size="small" :style="{width: '240px'}"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出版社" prop="publish">
        <el-input
          v-model="queryParams.publish"
          placeholder="请输入出版社"
          clearable
          size="small" :style="{width: '240px'}"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出版时间" prop="publisTime">
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
      <el-form-item label="作者" prop="author">
        <el-input
          v-model="queryParams.author"
          placeholder="请输入作者"
          clearable
          size="small" :style="{width: '240px'}"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['skill:monograph:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single || spzt"
          @click="handleUpdate"
          v-hasPermi="['skill:monograph:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple  || spzt"
          @click="handleDelete"
          v-hasPermi="['skill:monograph:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-notebook-2"
          size="mini"
          :disabled="single"
          @click="processExamine"
          v-hasPermi="['skill:monograph:list']"
        >流转历史</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-notebook-2"
          size="mini"
          :disabled="single"
          @click="handleOpinion"
          v-hasPermi="['skill:monograph:list']"
        >查看审批详情</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table border :height="tableHeight" stripe v-loading="loading" :data="monographList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column show-overflow-tooltip sortable label="科室" align="left" prop="deptName" />
      <el-table-column show-overflow-tooltip sortable label="专著名称" align="left" prop="monographName" >
        <template slot-scope="scope">
          <el-link type="primary" :underline="false" v-html="scope.row.monographName" @click.stop="handleClickTableCode(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip sortable label="出版社" align="left" prop="publish" />
      <el-table-column show-overflow-tooltip sortable label="出版时间" align="center" prop="publisTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publisTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip sortable label="作者" align="center" prop="author" />
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

    <addEdit1 v-if="open1" :open="open1" :id="addEditId" @cancel="cancel1" :technoCode="monographCode" ></addEdit1>

    <!-- 流转历史对话框 -->
    <el-dialog title="流转历史" :visible.sync="processOpen" width="1200px" append-to-body :close-on-click-modal="false" custom-class="el-button--primary">
      <div v-loading="loading" :style="'height:'+ height">
        <iframe :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto" />
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMonograph, getMonograph, delMonograph, addMonograph, updateMonograph, exportMonograph } from "@/api/skill/monograph";
import addEdit from "./addEdit";
import addEdit1 from "./addEdit1";
import { treeRoom, getEmpList } from "@/api/until";
import { listPerson, getPerson, delPerson, addPerson, updatePerson, exportPerson } from "@/api/skill/person";

export default {
  name: "Monograph",
  components: {
addEdit,addEdit1
  },
  data() {
    return {
      monographCode: null,
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
      monographCodes: [],
      // 非单个禁用
      single: true,
      spzt: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 专著表格数据
      monographList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      open1: false,
      // 专著级别字典
      monographTypeOptions: [],
      // 开本字典
      folioOptions: [],
      // 担任职务字典
      positionOptions: [],
      deptList: [],
      //时间范围
      daterangeTime: [],
      // 状态字典
      stateOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
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
      },
      // 表单参数
      form: {},
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
    /** 查询专著列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeTime && '' != this.daterangeTime) {
        this.queryParams.params["beginTime"] = this.daterangeTime[0];
        this.queryParams.params["endTime"] = this.daterangeTime[1];
      }
      listMonograph(this.queryParams).then(response => {
        this.monographList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    stateFormat(row, column) {
      return this.selectDictLabel(this.stateOptions, row.auditState);
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
      this.monographCodes = selection.map(item => item.monographCode)
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
    /** 新增按钮操作
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加专著";
    },
     */
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
            updateMonograph(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMonograph(this.form).then(response => {
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
      const monographCode = row.monographCode || this.monographCodes[0];
      const id = row.id || this.ids[0];
      this.addEditId = id; // id赋值
      this.open1 = true;
      this.monographCode = monographCode; // id赋值
      console.log("monographCode---" + monographCode);
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除专著编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delMonograph(ids);
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
