<template>
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="年月" prop="nowDate">
          <el-date-picker :clearable="false" size="small"
                          v-model="queryParams.nowDate" :editable="false"
                          type="month" value-format="yyyy-MM-dd"
                          placeholder="选择年月">
          </el-date-picker>
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
            icon="el-icon-check"
            size="mini"
            :loading="saveLoading"
            @click="handleSave"
            v-hasPermi="['bonus:deptSort:save']"
          >保存
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            size="mini"
            :loading="saveLoading"
            type="default"
            icon="el-icon-refresh"
            @click="handleRefresh"
          >刷新
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-setting"
            size="mini"
            @click="handleSetting"
            v-hasPermi="['bonus:deptSortDetail:list']"
          >类别设置
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-document-copy"
            size="mini"
            :loading="saveLoading"
            @click="handleCopy"
            v-hasPermi="['bonus:deptSort:copy']"
          >复制
          </el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <el-table border stripe v-loading="loading" :height="tableHeight" :data="deptSortList" @selection-change="handleSelectionChange"
              ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" type="index"></el-table-column>
      <el-table-column sortable label="科室名称" min-width="20%" align="left" prop="deptRoomName"/>
      <el-table-column sortable label="类别选择"min-width="75%" align="left" prop="sortCode">
        <template slot-scope="scope">
          <el-radio-group v-model="scope.row.sortCode">
            <el-radio label="0000" border style="margin-right: 0;min-width: 105px;">未设置</el-radio>
            <el-radio border style="margin: 1px 0px;min-width: 105px;"
              v-for="detail in deptSortDetailList"
              :key="detail.sortCode"
              :label="detail.sortCode"
            >{{detail.sortName}}
            </el-radio>
          </el-radio-group>
        </template>
      </el-table-column>
    </el-table>

<!--    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"/>-->

    <deptSortDetail v-if="open" :open="open" @cancel="cancel" @success="successSubmit"></deptSortDetail>

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
import {getYearMonth2} from "@/utils/date";
import {listDeptSort, copyDataByDate, saveData} from "@/api/bonus/deptSort";
import {listDeptSortDetail} from "@/api/bonus/deptSortDetail";
import deptSortDetail from '../deptSortDetail/index'
import {copyData} from "@/api/commission/commission";

export default {
  name: "DeptSort",
  components: {
    deptSortDetail
  },
  data() {
    return {
      saveLoading: false,
      tableHeight: 'auto',
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 核算单位类别设置表格数据
      deptSortList: [],
      deptSortDetailList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        compCode: this.$store.state.user.userInfo.deptId,
        pageSize: 30,
        nowDate: getYearMonth2() + '-01',
      },
      openCopy:false,
      copyForm:{
        // compCode: this.$store.state.user.userInfo.deptId,
        nowDate:null,
        copyDate:null
      },
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
      }
    };
  },
  watch: {
    listData(val) {
      var that = this;
      setTimeout(function () {
        that.resizeHandler();
      }, 300);
    }
  },
  computed: {
    listData() {
      return this.$store.state.app.sidebar.opened;
    }
  },
  created() {
    this.getList();
    this.getDetailList();
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  methods: {
    /** 查询核算单位类别设置列表 */
    getList() {
      this.loading = true;
      listDeptSort(this.queryParams).then(response => {
        this.deptSortList = response;
        // this.total = response.total;
        this.loading = false;
      });
    },
    getDetailList() {
      listDeptSortDetail().then(response => {
        this.deptSortDetailList = response;
      });
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      // let h = a + b + 18 + 45 + 84 + 40 + 20;
      let h = a + b + 18 + 45 + 84 + 40;
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
        deptRoomCode: null,
        sortCode: null,
        compCode: null,
        accountCode: null,
        nowDate: null
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
    /** 重置按钮操作 */
    handleRefresh() {
      this.getList();
      this.getDetailList();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
      this.selectionRow = selection
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
    rowStyle({row, rowIndex}) {
      Object.defineProperty(row, 'rowIndex', {
        value: rowIndex,
        writable: true,
        enumerable: false
      })
    },
    rowClassName({row, rowIndex}) {
      let rowName = "",
        findRow = this.selectionRow.find(c => c.rowIndex === row.rowIndex);
      if (findRow) {
        rowName = "current-row ";
      }
      return rowName;
    },

    /** 新增按钮操作 */
    handleSave() {
      this.$confirm('是否保存', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.saveLoading = true
        let data = {
          data: this.$refs.multipleTable.data,
          'nowDate': this.queryParams.nowDate
        }
        saveData(data).then(response => {
          this.msgSuccess('保存成功')
          this.saveLoading = false
        })
      })
    },
    /** 类别设置 */
    handleSetting() {
      this.open = true;
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
  }
};
</script>
