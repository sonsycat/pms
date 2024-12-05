<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="100px">
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
      <el-form-item label="名称" prop="technoName">
        <el-input
          v-model="queryParams.technoName"
          placeholder="请输入名称"
          :style="{width: '240px'}"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人" prop="personCode">
        <el-input
          v-model="queryParams.personCode"
          placeholder="请输入负责人"
          clearable
          :style="{width: '240px'}"
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人学历" prop="education">
        <el-select v-model="queryParams.education" placeholder="请选择负责人学历" clearable size="small" :style="{width: '240px'}">
          <el-option
            v-for="dict in educationOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="负责人职称" prop="titleCode">
        <el-select v-model="queryParams.titleCode" placeholder="请选择负责人职称" clearable size="small" :style="{width: '240px'}">
          <el-option
            v-for="dict in titleCodeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="开展日期" prop="developTime">
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
          v-hasPermi="['skill:technology:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single || spzt"
          @click="handleUpdate"
          v-hasPermi="['skill:technology:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple || spzt"
          @click="handleDelete"
          v-hasPermi="['skill:technology:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAuthor"
          :disabled="single"
          v-hasPermi="['skill:technology:add']"
        >参加人员</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-notebook-2"
          size="mini"
          :disabled="single"
          @click="processExamine"
          v-hasPermi="['skill:technology:list']"
        >流转历史</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-notebook-2"
          size="mini"
          :disabled="single"
          @click="handleOpinion"
          v-hasPermi="['skill:technology:list']"
        >查看审批详情</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table :height="tableHeight" border stripe v-loading="loading" :data="technologyList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column show-overflow-tooltip sortable label="科室" align="left" prop="deptName" />
      <el-table-column show-overflow-tooltip sortable label="新业务新技术" align="center" prop="techno" :formatter="technoFormat" />
      <el-table-column show-overflow-tooltip sortable label="名称" align="left" prop="technoName" >
        <template slot-scope="scope">
          <el-link type="primary" :underline="false" v-html="scope.row.technoName" @click.stop="handleClickTableCode(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip sortable label="负责人" align="center" prop="personName" />
      <el-table-column show-overflow-tooltip sortable label="负责人学历" align="center" prop="education" :formatter="educationFormat" />
      <el-table-column show-overflow-tooltip sortable label="负责人职称" align="center" prop="titleCode" :formatter="titleCodeFormat" />
      <el-table-column show-overflow-tooltip sortable label="开展日期" align="center" prop="developTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.developTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip sortable label="进展情况" align="left" prop="progress" />
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

    <addEdit1 v-if="open2" :open="open2" :id="addEditId" @cancel="cancel2" :technoCode="technoCode" ></addEdit1>

    <!-- 流转历史对话框 -->
    <el-dialog title="流转历史" :visible.sync="processOpen" width="1200px" append-to-body :close-on-click-modal="false" custom-class="el-button--primary">
      <div v-loading="loading" :style="'height:'+ height">
        <iframe :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto" />
      </div>
    </el-dialog>

    <!-- 参加人员对话框 -->
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
              <el-table-column sortable label="参加人员" align="center" prop="personCode" >
                <template slot-scope="scope">
                  <el-input v-model="scope.row.personCode" placeholder="请输入参加人员"  maxlength="50" show-word-limit  />
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
import { listTechnology, getTechnology, delTechnology, addTechnology, updateTechnology, exportTechnology } from "@/api/skill/technology";
import addEdit from "./addEdit";
import addEdit1 from "./addEdit1";
import { treeRoom, getEmpList } from "@/api/until";
import { listPerson, getPerson, delPerson, addPerson, updatePerson, exportPerson } from "@/api/skill/person";

export default {
  name: "Technology",
  components: {
addEdit,addEdit1
  },
  data() {
    return {
      technoCode: null,
      postForm: {},
      title1: "新业务新技术 / 参加人员",
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
      technoCodes: [],
      // 非单个禁用
      single: true,
      spzt: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 新业务新技术表格数据
      technologyList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      open1: false,
      open2: false,
      // 水平字典
      technoTypeOptions: [],
      // 负责人学历字典
      educationOptions: [],
      // 负责人专业字典
      specialOptions: [],
      // 职称字典
      titleCodeOptions: [],
      // 效果字典
      effectOptions: [],
      // 新业务新技术字典
      technoOptions: [],
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
      },
      // 查询参数
      queryParams1: {
        compCode: this.$store.state.user.userInfo.deptId,
        technoCode: null,
        technoType: "5",
        personList: []
      },
      // 技术参与人表格数据
      personList: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        technoCode: [
          { required: true, message: "编码不能为空", trigger: "blur" }
        ],
        technoName: [
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
    /** 查询新业务新技术列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeTime && '' != this.daterangeTime) {
        this.queryParams.params["beginTime"] = this.daterangeTime[0];
        this.queryParams.params["endTime"] = this.daterangeTime[1];
      }
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
      this.technoCodes = selection.map(item => item.technoCode)
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
      this.title = "添加新业务新技术";
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
    handleAuthor(row) {
      this.reset();
      const id = row.id || this.ids[0];
      const technoCode = row.technoCode || this.technoCodes[0];
      this.open1 = true;
      this.queryParams1.technoCode = technoCode; // id赋值
      this.queryParams1.personList = [];
      console.log("technoCode---" + technoCode);
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
            updateTechnology(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTechnology(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    submitForm1() {
      this.queryParams1.personList = this.personList;
      console.log("personList",this.personList)
      let saveLoading = this.saveLoading();
      updatePerson(this.queryParams1).then(response => {
        this.msgSuccess("编辑作者成功");
        this.open1 = false;
        saveLoading.close();
        this.getList();
      });
    },
    handleOpinion(row) {
      this.reset();
      const technoCode = row.technoCode || this.technoCodes[0];
      const id = row.id || this.ids[0];
      this.addEditId = id; // id赋值
      this.open2 = true;
      this.technoCode = technoCode; // id赋值
      console.log("technoCode---" + technoCode);
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除新业务新技术编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTechnology(ids);
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
