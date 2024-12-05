<style>
  .el-table .group-row {
    background: #f8f8f9;
  }
</style>
<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="年月" prop="nowDate">
        <el-date-picker :clearable="false" :editable="false" size="small"
          v-model="queryParams.nowDate"
          type="month" value-format="yyyy-MM-dd" placeholder="选择年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="科室名称" prop="deptRoomName">
        <el-input
          v-model="queryParams.deptRoomName"
          placeholder="请输入科室名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['bonus:accountDept:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-document-copy"
          size="mini"
          :loading="saveLoading"
          @click="handleCopy"
          v-hasPermi="['bonus:accountDept:copy']"
        >复制
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table border v-loading="loading" :height="tableHeight" :data="accountDeptList" :span-method="arraySpanMethod"
              @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index"></el-table-column>
      <el-table-column sortable label="科室编码" align="center" prop="deptRoomCode" />
      <el-table-column sortable label="科室名称" align="left" prop="deptRoomName">
        <template slot-scope="scope">
          <span v-if="scope.row.parentCode === '0'">
            <el-tag type="info" effect="dark">核算科室</el-tag>&nbsp;:&nbsp;
            <el-tag type="" effect="dark">{{scope.row.deptRoomName}}（{{scope.row.childNum + 1}}）</el-tag>
          </span>
          <span v-else>{{scope.row.deptRoomName}}</span>
        </template>
      </el-table-column>
      <el-table-column sortable label="上级科室" align="left" prop="parentName" />
      <el-table-column sortable label="是否核算" align="center" prop="isAccount" :formatter="typeFormat" width="100">
        <template slot-scope="scope" v-if="scope.row.isAccount != null">
          <el-tag :type="scope.row.isAccount === '0' ? 'success' : 'warning'"
            close-transition>{{scope.row.isAccount | yesOrNoFormat}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column sortable label="拼音码" align="center" prop="pym" />
    </el-table>
	  <addEdit v-if="open" :open="open" :id="addEditId" :name="deptRoomName" :nowDate="queryParams.nowDate" @cancel="cancel" @success="successSubmit"></addEdit>

    <el-dialog title="单科室多指标-复制" class="copyEvaDialog" ref="copyEvaDialog" :visible.sync="openCopy" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="copyCancel">
      <el-form ref="copyForm" :model="copyForm" :rules="rules">
        <el-row>
          <el-col :span="24">
            <el-form-item label="复制年月" prop="nowDate">
              <el-date-picker clearable class="w90" v-model="copyForm.nowDate" type="month" value-format="yyyy-MM-dd" placeholder="选择年月">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <!-- :picker-options="picOptFun" -->
            <el-form-item label="拷贝年月" prop="copyDate">
              <el-date-picker clearable class="w90" v-model="copyForm.copyDate" type="month" value-format="yyyy-MM-dd"
                              placeholder="选择年月" @change="changeNowDate" :picker-options="picOptFun(copyForm.nowDate,copyForm.copyDate)">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCopyForm">保 存</el-button>
        <el-button @click="copyCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listAccountDept} from "@/api/bonus/accountDept";
import addEdit from "./addEdit";
import {getYearMonth2} from "@/utils/date";
import {copyDataByDate} from "@/api/bonus/accountDept";
var _self;
export default {
  name: "AccountDept",
  components: {addEdit},
  data() {
    return {
      tableHeight: 'auto',
      saveLoading: false,
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      deptRoomCodes: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 核算科室设置表格数据
      accountDeptList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 类型数据字典
      typeOptions: [],
      // 查询参数
      queryParams: {
        compCode: this.$store.state.user.userInfo.deptId,
        nowDate: getYearMonth2() + '-01',
        dataType: 1
      },
      addEditId: '',
      deptRoomName: '',
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        nowDate: [
          { required: true, message: '复制年月不能为空', trigger: ['blur', 'change'] }
        ],
        copyDate: [
          { required: true, message: '拷贝年月不能为空', trigger: ['blur', 'change'] }
        ]
      },
      openCopy:false,
      copyForm:{
        // compCode: this.$store.state.user.userInfo.deptId,
        nowDate:null,
        copyDate:null
      },
    };
  },
  watch: {
    listData(val) {
      let that = this;
      setTimeout(function () {
        that.resizeHandler();
      }, 300);
    }
  },
  beforeCreate() {
    _self = this;
  },
  created() {
    this.getList();
    this.getDicts("yes_or_no").then(response => {
      this.typeOptions = response.data;
    });
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  methods: {
    /** 查询核算科室设置列表 */
    getList() {
      this.loading = true;
      listAccountDept(this.queryParams).then(response => {
        this.accountDeptList = response;
        this.total = response.total;
        this.loading = false;
      });
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      // let h = a + b + 18 + 45 + 84 + 40 + 20;
      let h = a + b + 18 + 45 + 84 + 35;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
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
    // 表单重置
    reset() {
      this.form = {
        id: null,
        parentCode: null,
        deptRoomCode: null,
        deptRoomName: null,
        isAccount: null,
        nowDate: null,
        compCode: null,
        accountCode: null
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
      let tableRef = this.$refs.multipleTable;
      if (selection.length > 1) {
        tableRef.clearSelection(); //清空列表的选中
        tableRef.toggleRowSelection(
          selection[selection.length - 1],
          true
        ); //只显示选中最后一个 这时selection还是多选的列表(就是你选中的几个数据)
        selection = Array(selection[selection.length - 1]);
      } else if (selection.length === 1) {
        selection = Array(selection[selection.length - 1]);
      } else {
        selection = [];
      }
      // 方便点击行取消选中
      this.selectionRow = selection;
      // 设置行高亮
      tableRef.setCurrentRow(selection[0]);
      this.ids = selection.map(item => item.id);
      this.deptRoomCodes = selection.map(item => item.deptRoomCode)
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
      if(selection && selection.length > 0){
        this.deptRoomName = selection[0].deptRoomName;
      }
    },
    rowClick(row, column, event) {
      let refsElTable = this.$refs.multipleTable;
      let findRow = this.selectionRow.find(c => c.rowIndex == row.rowIndex);
      if (findRow && this.selectionRow.length == 1) {
        refsElTable.toggleRowSelection(row, false);
        return;
      }
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
    rowClassName({row, rowIndex}) {
      let rowName = "", findRow = this.selectionRow.find(c => c.rowIndex === row.rowIndex);
      // 其他按照每部分不同隔开
      if (row.parentCode === '0') {
        rowName = 'group-row';
      }
      if (findRow) {
        rowName = "current-row ";
      }
      return rowName;
    },
    arraySpanMethod({ row, column, rowIndex, columnIndex }) {
      if (row.parentCode === '0') {
        if (columnIndex === 0 || columnIndex === 1) {
          return [1, 1];
        } else if(columnIndex === 3) {
          return [1, 5];
        } else{
          return [0, 0];
        }
      }
    },
    handleUpdate(row) {
      this.reset();
      const deptRoomCode = row.deptRoomCode || this.deptRoomCodes[0];
      this.open = true;
      this.addEditId = deptRoomCode; // id赋值
    },
    // 参数系统内置字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.isAccount);
    },
    //复制按钮操作
    handleCopy(){
      this.openCopy = true;
      this.resetCopy();
    },
    resetCopy(){
      this.copyForm={
        // compCode: this.$store.state.user.userInfo.deptId,
        nowDate: getYearMonth2() + '-01',
        copyDate: null,
      }
      this.resetForm("copyForm");
    },
    copyCancel(){
      this.resetCopy();
      this.openCopy = false;
      this.setdeptbtn = true
      this.getList();
    },
    subCopy(flag){
      this.resetCopy();
      this.openCopy = flag;
      this.getList();
    },
    //复制保存按钮
    submitCopyForm(){
      let nowDate = this.copyForm.nowDate
      let copyDate = this.copyForm.copyDate
      this.$refs['copyForm'].validate(valid => {
        if (valid) {
          this.loading = true;
          copyDataByDate(this.copyForm).then(res=>{
            if(res.code == 200){
              this.msgSuccess(res.msg);
              this.queryParams.nowDate = this.copyForm.copyDate;
              this.subCopy(false);
              this.loading = false;
            }else{
              this.msgError(res.msg);
            }
          })
        }
      })
    },
    changeNowDate(val){
      this.picOptFun(val,this.copyForm.nowDate)
    },
    picOptFun(time1,time2){
      if(time1 && time2 && time1 == time2){
        this.copyForm.copyDate=null;
        this.msgError("拷贝年月与复制年月不能相同")
      }
    }
  },
  filters:{
    yesOrNoFormat(val){
      return _self.selectDictLabel(_self.typeOptions, val);
    }
  }
};
</script>
