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
          icon="el-icon-save"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['bonus:accountDeptNew:save']"
        >保存</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-document-copy"
          size="mini"
          :loading="saveLoading"
          @click="handleCopy"
          v-hasPermi="['bonus:accountDeptNew:copy']"
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
      <el-table-column sortable label="科室名称" align="center" prop="deptRoomName" />
      <el-table-column sortable label="核算类别"min-width="75%" align="center" prop="accountType">
        <template slot-scope="scope">
          <el-radio-group v-model="scope.row.accountType" >
            <el-radio border style="margin: 1px 0px;min-width: 105px;"
                      v-for="detail in accountTypeList"
                      :key="detail.accountType"
                      :label="detail.accountType"
            >{{detail.accountTypeName}}
            </el-radio>
          </el-radio-group>
        </template>
      </el-table-column>
    </el-table>

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
// import {listAccountDept} from "@/api/bonus/accountDept";
import {listAccountDept,saveAccountDeptNew,copyDataByDate} from "@/api/bonus/accountDeptNew";
import addEdit from "./addEdit";
import {getYearMonth2} from "@/utils/date";
import { getMapJSON } from 'v-charts/lib/utils'
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
      selectionMes:'',
      accountTypeList:[
        {accountType:'0',accountTypeName:'未设置'},
        {accountType:'1',accountTypeName:'平均奖'},
        {accountType:'2',accountTypeName:'独立核算'}
      ],
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
        nowDate: getYearMonth2() + '-01'
      },
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
        let dataTime = this.queryParams.nowDate;
        let code = this.queryParams.compCode;
        this.accountDeptList.forEach(item => {
          item.nowDate = dataTime;
          item.compCode = code;
        })
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
      this.ids = selection.map(item => item.id)
      this.selectionMes = selection
      this.single = !selection.length
      this.multiple = !selection.length
      this.selectionRow=selection
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

      saveAccountDeptNew(this.selectionMes).then(res=>{
        if(res.code == 200){
          this.msgSuccess(res.msg);
          this.getList();
        }else{
          this.msgError(res.msg);
        }
      })
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
