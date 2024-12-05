<template>
  <div class="app-container" style="padding: 2px 0 0 0">
    <div ref="btns">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="info"
            icon="el-icon-back"
            size="mini"
            :loading="exportLoading"
            @click="goBack"
          >返回
          </el-button>
        </el-col>

        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-save"
            size="mini"
            :disabled="multiple"
            @click="handleSave"
            v-hasPermi="['bonus:staff:save']"
          >保存</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-upload2"
            size="mini"
            @click="handleImport"
            v-hasPermi="['bonus:staff:add']"
          >导入Excel</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-upload2"
            size="mini"
            :loading="exportLoading"
            @click="handleExportMes"
            v-hasPermi="['bonus:staff:exportDetail']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <el-table border stripe v-loading="loading" :data="EvaluateDataList" height="calc(100vh - 180px)"
              @selection-change="handleSelectionChange"
              ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName"
              :show-summary="true" :summary-method="getSummaries">

      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column sortable label="奖金日期" align="center" prop="actDate"></el-table-column>
      <el-table-column sortable label="科室名称" align="center" prop="deptName"></el-table-column>
      <el-table-column sortable label="员工编码" align="center" prop="staffCode"></el-table-column>
      <el-table-column sortable label="员工姓名" align="center" prop="staffName"></el-table-column>
      <el-table-column sortable label="奖金金额" align="right" prop="bonusAmount" >
        <template slot-scope="scope">
<!--          <el-input-number v-text="moneyFormat(scope.row.bonusAmount)" v-show="true"></el-input-number>-->
          <el-input-number v-model="scope.row.bonusAmount" ></el-input-number>
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

    <!-- 导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" custom-class="el-button--primary"
               :close-on-click-modal="false" >
      <el-upload
        ref="upload"
        :limit="1"
        :data="uploadDate"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :on-error="handleFileError"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          注意：生成数据需要大约俩分钟时间，在此期间请不要关闭您的浏览器或刷新界面。
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-link type="info" style="font-size:12px" @click="handleExport">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:#cb6c6c" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>


import { getYearMonth2 } from '@/utils/date'
import { listBonusStaff,exportStaff,saveBonusStaff,exportDetail } from '@/api/evaluateData/evaluateDataStaff'
import formItem from '@/views/components/formItemNew'
import btns from '@/views/bonus//btns/index2'
import JsonExcel from 'vue-json-excel'
import Vue from 'vue'
import selectList from '@/views/bonus/personBonus/selectDoctor'
import { getToken } from "@/utils/auth";

Vue.component('downloadExcel',JsonExcel)

export default {
  name: 'EvaluateData',
  deptCode: {
    type: [String, String],
    default: ''
  },
  components: {
    formItem, btns, selectList
  },
  filters: {
    transToValue(val, index) {
      return val[index]
    }
  },
  computed: {
    sumDataList(){
      this.sumDataList_.push({});
      this.sumDataList_.push(this.bottomTotal);
      return this.sumDataList_;
    },
    excelName(){
      return "人员奖金"+getYearMonth2()+".xls";
    }
  },
  mounted() {

  },
  watch: {

  },
  data() {
    return {
      processOpen: false,
      height: '500px;',
      src: '',
      showAudit: false,
      id: '',
      showDialog: false,
      userOptions: [],
      auditState: '',
      saveLoading: false,
      // 遮罩层
      loading: false,
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
      // 表格数据
      EvaluateDataList: [],
      sumDataList_: [],
      selectionRow: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode: null,
        deptCode: null,
        deptName: null,
        accountCode: null,
        guideCode: null,
        guideValue: null,
        delFlag: null,
        actDate: getYearMonth2() + '-01',
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        deptType: null
      },
      // 表单参数
      form: { procedureId: null, assignee: null },
      //类型
      nextAssignee: [],
      // 按钮编码
      btn1: false,
      btn2: false,
      btn3: false,
      btn4: false,
      btn5: false,
      comment: '',
      proclnsIds: '',
      datas: {},
      // 医生选择是否弹出
      openSelect: false,
      doctorOpen: false,
      docParams: {
        pageNum: 1,
        pageSize: 20
      },
      doctorList: [],
      docTotal: 0,
      //表格标题
      tableTitle: "人员奖金数据",
      //表格数据
      jsonFields: {
        "科室名称":'deptName',
        "人员姓名":'staffName'
      },
      bottomTotal:{
        deptName: "合计",
        staffName: "",
        total: 0
      },
      uploadDate: {
        deptCode: null,
        actDate: null,
      },
      // 导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/bonus/staff/importData"
      },
    }
  },
  created() {
    this.getList()

  },
  methods: {
    /** 导出按钮操作 */
    handleExportMes() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有员工数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.exportLoading = true;
        return exportDetail(queryParams);
      }).then(response => {
        this.download(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "员工奖金导入";
      this.upload.open = true;
    },
    /** 按钮操作 */
    handleSave(row) {
      saveBonusStaff(this.selectionRow).then(response => {
          if(response.code == 200){
            this.msgSuccess(response.msg);
            this.getList();
          }else{
            this.msgError(res.msg);
          }
        // this.msgSuccess("保存成功");
        // this.$emit("success", false);
        // this.getList()
      });
    },
    // 提交上传文件
    submitFileForm() {
      this.uploadDate.deptCode = this.$route.params.deptCode;
      this.uploadDate.actDate = this.$route.params.actDate
      this.$refs.upload.submit();
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    handleFileError(err,file,fileList){
      this.upload.isUploading = false;
      this.msgError("上传失败！");
    },
    /** 查询科室对应人员的奖金数据 */
    getList() {
      this.loading = true
      this.queryParams.deptCode = this.$route.params.deptCode
      this.queryParams.actDate = this.$route.params.actDate
      listBonusStaff(this.queryParams).then(response => {
        this.EvaluateDataList = response.data
        this.tableDoLayout();
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    successSubmit(flag) {
      this.open = flag;
      this.getList();
    },
    // 取消按钮
    cancelSelect() {
      this.openSelect = false
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: null,
        deptName: null,
        accountCode: null,
        guideCode: null,
        guideValue: null,
        delFlag: null,
        actDate: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
      this.selectionRow = selection
    },
    rowClick(row, column, event) {
      let refsElTable = this.$refs.multipleTable
      let findRow = this.selectionRow.find(c => c.rowIndex == row.rowIndex)
      if (findRow && this.selectionRow.length == 1) {
        refsElTable.toggleRowSelection(row, false)
        return
      }
      refsElTable.clearSelection()
      refsElTable.toggleRowSelection(row)
    },
    rowStyle({ row, rowIndex }) {
      Object.defineProperty(row, 'rowIndex', {
        value: rowIndex,
        writable: true,
        enumerable: false
      })
    },
    rowClassName({ row, rowIndex }) {
      let rowName = '',
        findRow = this.selectionRow.find(c => c.rowIndex === row.rowIndex)
      if (findRow) {
        rowName = 'current-row '
      }
      return rowName
    },
    tableRowClassName({ row, rowIndex }) {
      row.row_index = rowIndex
    },

    singleTable(row) {
      let tableRef = this.$refs.itsmDataTable
      tableRef.clearSelection()
      if (this.currentRowIndex == row.row_index) {
        tableRef.toggleRowSelection(row, false)
        this.currentRowIndex = null
        this.docSelectData = null
      } else {
        tableRef.toggleRowSelection(row)
        this.currentRowIndex = row.row_index
        this.docSelectData = row
      }
    },

    handleExport() {
      const queryParams = this.queryParams;
      this.exportLoading = true;
      exportStaff(queryParams).then(response => {
        this.download(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    },

    // 返回
    goBack() {
      // 关闭当前页面
      this.$store.dispatch('tagsView/delView', this.$route)
      // 返回上一页面
      this.$router.go(-1)
    },

    getSummaries(param) {
      const { columns, data } = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计';
          return;
        }else if (index === 5) {
          const values = data.map(item => Number(item[column.property]));
          if (!values.every(value => isNaN(value))) {
            sums[index] = values.reduce((prev, curr) => {
              const value = Number(curr);
              if (!isNaN(value)) {
                return prev + curr;
              } else {
                return prev;
              }
            }, 0);
            if (column.property != "deptName") {
              this.bottomTotal[column.property] = parseFloat(sums[index]);
            }
            sums[index] = sums[index].toFixed(2);
          } else {
            sums[index] = '';
          }
        }
      });
      return sums;
    },
    tableDoLayout(){
      if(this.$refs.multipleTable){
        this.$nextTick(()=>{
          this.$refs.multipleTable.doLayout();
        })
      }
    }
  }
}
</script>


