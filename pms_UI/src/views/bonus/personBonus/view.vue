<template>
  <div class="app-container" style="padding: 2px 0 0 0">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px" hidden>
        <el-form-item label="年月" prop="actDate">
          <el-date-picker clearable size="small"
                          v-model="queryParams.actDate"
                          type="month"
                          value-format="yyyy-MM-dd"
                          placeholder="选择年月"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        </el-form-item>
      </el-form>
    </div>
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
            type="primary"
            icon="el-icon-notebook-2"
            size="mini"
            @click="processExamine"
            v-hasPermi="['bonus:calculation:list']"
          >流转历史
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <template>
            <download-excel class="export-excel-wrapper" :data="sumDataList" :fields="jsonFields" :header="title" :name="excelName">
              <el-button
                type="warning"
                icon="el-icon-document-copy"
                size="mini"
                @click=""
              >导出EXCEL
              </el-button>
            </download-excel>
          </template>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <el-table border stripe v-loading="loading" :data="EvaluateDataList" height="calc(100vh - 180px)"
              @selection-change="handleSelectionChange"
              ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName"
              :show-summary="true" :summary-method="getSummaries">

      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column sortable label="科室名称" align="left" prop="deptRoomName"></el-table-column>
      <el-table-column sortable label="人员姓名" align="left" prop="docName"></el-table-column>

      <el-table-column
        v-for="(item,index) in indexList"
        :key="index"
        :label="item.indexName"
        :prop="item.guideCode"
        align="right"
      >
<!--        <template slot-scope="scope">
          <div @click.stop.prevent style="display:inline">
          <el-input-number :precision="2" :step="0.01" :min="0" v-model="scope.row[item.guideCode]"
                           controls-position="right"/>
          </div>
        </template>-->
      </el-table-column>
      <el-table-column sortable label="合计" align="right" prop="total">
        <template slot-scope="scope">
          <span>{{ computedStatus(scope.row) }}</span>
        </template>
      </el-table-column>
    </el-table>
    <el-form ref="form" :model="form" label-width="80px">
      <formItem :showDialog.sync="showDialog" :showBaseInfo="btn3" :open="btn4" :open2="btn5" :comment="form"
                :userOptions="userOptions" @success="formItemSuccess"
      ></formItem>
    </el-form>

    <el-dialog title="流转历史" :visible.sync="processOpen" width="1200px" append-to-body :close-on-click-modal="false"
               custom-class="el-button--primary"
    >
      <div v-loading="loading" :style="'height:'+ height">
        <iframe :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto"/>
      </div>
    </el-dialog>
  </div>
</template>

<script>


import { getYearMonth2 } from '@/utils/date'
import { addAudit, listAudit } from '@/api/bonus/audit'
import { getAccountPerson, listDoctor, listPersonBonus } from '@/api/evaluateData/evaluateDataPerson'
import formItem from '@/views/components/formItemNew'
import btns from '@/views/bonus//btns/index2'
import { getEmpList } from '@/api/until'
import JsonExcel from 'vue-json-excel'
import Vue from 'vue'
import selectList from '@/views/bonus/personBonus/selectDoctor'
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
    computedStatus() {
      return function(row) {
        let result = 0
        for (let v of this.indexList) {
          result += parseFloat(row[v.guideCode])
        }
        row.total = result.toFixed(2);
        return result.toFixed(2)

      }
    },
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
      indexList: [],
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
        "科室名称":'deptRoomName',
        "人员姓名":'docName'
      },
      bottomTotal:{
        deptRoomName: "合计",
        docName: "",
        total: 0
      }
    }
  },
  created() {
    this.getDept()
    this.getDetailInfo()

  },
  methods: {
    /** 查询科室对应人员的奖金指标 */
    getDept() {
      this.loading = true
      this.queryParams.actDate = this.$route.params.actDate
      this.queryParams.nowDate = this.$route.params.actDate
      this.queryParams.deptCode = this.$route.params.deptCode
      getAccountPerson(this.queryParams).then(response => {
        this.indexList = response.data
        this.getList()
        this.loading = false
      })
    },

    /** 查询科室对应人员的奖金数据 */
    getList() {
      this.loading = true
      if (!this.queryParams.actDate) {
        this.queryParams.actDate = getYearMonth2() + '-01'
      }
      this.queryParams.deptCode = this.$route.params.deptCode
      listPersonBonus(this.queryParams).then(response => {
        this.EvaluateDataList = response.data
        this.tableDoLayout();
        this.sumDataList_ = [].concat(response.data)
        for (let eva in this.EvaluateDataList) {
          if (this.EvaluateDataList[eva]['remark'] == '' || this.EvaluateDataList[eva]['remark'] == null) {
            this.EvaluateDataList[eva]['remark'] = '0'
          }
        }
        this.loading = false
      })
      for (let index in this.indexList){
        this.jsonFields[this.indexList[index].indexName] = this.indexList[index].guideCode
      }
      this.jsonFields["合计"] = "total"
    },
    successSubmit(flag) {
      this.open = flag
      this.getList()
    },
    // 取消按钮
    cancel() {
      this.open = false
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
      this.ids = selection.map(item => item.docCode)
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
    // 医生设置单选
    handleSelectionDocChange(selection, row) {
      this.singleTable(row)
    },
    handleDocRowClick(row) {
      this.singleTable(row)
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
    sexFormat(row, column) {
      return this.selectDictLabel(this.sexOptions, row.gender)
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出科室奖金数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        for (let index in this.indexList){
          this.jsonFields.push(this.indexList[index].guideCode,this.indexList[index].guideCode)
        }
        this.exportLoading = true
      }).catch(() => {
      })
    },

    // 返回
    goBack() {
      // 关闭当前页面
      this.$store.dispatch('tagsView/delView', this.$route)
      // 返回上一页面
      this.$router.go(-1)
    },
    packageData() {

      this.$refs.multipleTable.data.forEach(item => {
        item.unitCode = item.docCode
        item.deptCode = item.deptRoomCode
        item.actDate = this.queryParams.actDate
      })

      let data = {
        data: this.$refs.multipleTable.data,
        audit: {
          deptCode: this.$route.params.deptCode,
          nowDate: this.queryParams.actDate,
          comment: this.form.comment,
          assignee: this.form.assignee
        }
      }
      return data
    },
    formItemSuccess() {
      let data = this.packageData()
      data.isCommit = true
      this.saveLoading = true
      addAudit(data).then(response => {
        this.msgSuccess('提交成功')
        this.saveLoading = false
        this.auditState = '1'
      })
    },
    getDetailInfo() {
      listAudit({
        deptCode: this.$route.params.deptCode,
        nowDate: this.queryParams.actDate
      }).then(response => {
        if (response.rows.length > 0) {
          let audit = response.rows[0]
          let that = this
          let proclnsIds = audit.procedureId
          this.proclnsIds = audit.procedureId
          this.form.procedureId = this.proclnsIds
          this.id = audit.id
          this.auditState = audit.auditState
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
          )
        }
      })
    },
    /** 流转历史初始化 */
    processExamineForm2() {
      this.src =
        `${this.processUrl}/api/wf/processHistoric?procInsId=${this.proclnsIds}`
    },
    /** 流转历史按钮操作 */
    processExamine() {
      let procedureId = this.proclnsIds
      if (procedureId == null || !procedureId) {
        this.msgError('没有流转历史数据！')
      } else {
        this.processExamineForm2()
        this.processOpen = true
      }
    },
    /** 添加人员 */
    docCodeList(data){
      for (let d in data[0]){
        let tableCol = {
          deptRoomCode: this.EvaluateDataList[0].deptRoomCode,
          deptRoomName: this.EvaluateDataList[0].deptRoomName,
          docCode: data[0][d].docCode,
          docName: data[0][d].docName
        }
        for (let index in this.indexList) {
          tableCol[this.indexList[index].guideCode] = 0
          // 新增人员标识为1，可以进行删除操作
          tableCol['remark'] = '1'
        }
        this.EvaluateDataList.push(tableCol)
      }
    },
    getSummaries(param) {
      const { columns, data } = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计';
          return;
        }
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
          if(column.property != "deptRoomName" && column.property != "docName"){
            this.bottomTotal[column.property] = parseFloat(sums[index]);
          }
          sums[index] = sums[index].toFixed(2);
        } else {
          sums[index] = 'N/A';
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


