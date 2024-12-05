<template>
  <el-dialog :title="title" :visible.sync="open" width="90%" custom-class="el-button--primary" append-to-body
             :close-on-click-modal="false" @close="handleDialogClose" v-loading="saveLoading"
  >
    <div class="app-container" style="min-height: 500px;">
      <el-table border stripe v-loading="loading" :data="EvaluateDataList" height="480px"
                @selection-change="handleSelectionChange"
                ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName"
      >
        <el-table-column sortable label="科室名称" align="left" prop="deptRoomName"></el-table-column>
        <el-table-column sortable label="人员姓名" align="left" prop="docName"></el-table-column>

        <el-table-column
          v-for="(item,index) in indexList"
          :key="index"
          :label="item.indexName"
          :prop="item.guideCode"
          align="right"
        >{{ item.guideValue }}
        </el-table-column>
        <el-table-column sortable label="合计" align="right" prop="total">
          <template slot-scope="scope">
            <span>{{ computedStatus(scope.row) }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-form ref="form" :model="form" label-width="80px">
      <formItem :showDialog.sync="showDialog" :showBaseInfo="btn3" :open="btn4" :open2="btn5" :comment="form"
                :userOptions="userOptions" @success="formItemSuccess"
      ></formItem>
    </el-form>
    <span slot="footer" class="dialog-footer">
       <btns v-if="btn3" @success="successSubmit" :open="btn3" :id="id" :proclnsIds="proclnsIds"
             :comment="form.comment"
             :assignee="form.assignee"
       ></btns>
        <el-button v-if="btn2" type="primary" @click="submitForm" :loading="saveLoading">审 批</el-button>
         <el-button @click="open=false" style="margin-left: 5px;">取 消</el-button>
          </span>
  </el-dialog>
</template>
<script>


import { getYearMonth2 } from '@/utils/date'
import { addAudit, getAudit, listAudit } from '@/api/bonus/audit'
import { getAccountPerson, listPersonBonus } from '@/api/evaluateData/evaluateDataPerson'
import formItem from '@/views/components/formItemNew'
import { getEmpList } from '@/api/until'
import btns from '@/views/bonus//btns/index2'

export default {
  name: 'EvaluateData',
  deptCode: {
    type: [String, String],
    default: ''
  },
  props: {
    open: {
      type: Boolean,
      default: false
    },
    id: {
      type: [String, Number],
      default: 0
    },
    proclnsIds: {
      type: [String, Number],
      default: ''
    }
  },
  components: {
    formItem, btns
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
        return result.toFixed(2)

      }
    }

  },
  mounted() {
    this.$nextTick(() => {
      this.resizeHandler()
      window.addEventListener('resize', this.resizeHandler)
    })
  },
  watch: {
    listData(val) {
      let that = this
      setTimeout(function() {
        that.resizeHandler()
      }, 300)
    },
    open: {
      handler(val) {
        this.openDialog = val
        this.getDetailInfo()
      },
      deep: true
    }
  },
  data() {
    return {
      openDialog: false,
      showAudit: false,
      showDialog: false,
      userOptions: [],
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
      selectionRow: [],
      // 弹出层标题
      title: '奖金生成人员审核',
      tableHeight: 'auto',
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
      datas: {}
    }
  },
  created() {
    getAudit(this.id).then(response => {
      console.log(response)
      this.queryParams.nowDate = response.data.nowDate
      this.queryParams.deptCode = response.data.deptCode
      this.getDept()
      this.getDetailInfo()
    })

  },
  methods: {
    /** 查询科室对应人员的奖金指标 */
    getDept() {
      this.loading = true
      getAccountPerson(this.queryParams).then(response => {
        this.indexList = response.data
        this.getList()
        this.loading = false
      })
    },

    /** 查询科室对应人员的奖金数据 */
    getList() {
      this.loading = true
      this.queryParams.actDate = !this.queryParams.nowDate?getYearMonth2() + '-01':this.queryParams.nowDate;
      listPersonBonus(this.queryParams).then(response => {
        this.EvaluateDataList = response.data
        this.loading = false
      })
    },
    successSubmit(flag) {
      this.open = flag
      this.getList()
      this.$emit('success', false)
    },
    // 取消按钮
    cancel() {
      this.open = false
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
    /** 新增按钮操作
     handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加独立核算提成比";
    },
     */
    /** 新增按钮操作 */
    handleAdd() {
      this.$confirm('是否保存', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.saveLoading = true
        let data = {
          data: this.$refs.multipleTable.data,
          'actDate': this.queryParams.actDate
        }
        addCommission(data).then(response => {
          this.msgSuccess('保存成功')
          this.saveLoading = false
        })
      })
    },
    /** 跳转科室对应人员奖金详情页 */
    handleUpdate(deptCode) {
      this.$store.dispatch('tagsView/delView', this.$route)
      this.$router.push('/personBonus/index/' + deptCode)
    },
    /** 提交按钮 */
    submitForm() {
      this.showDialog = true
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除独立核算提成比编号为"' + ids + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delCommission(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出科室奖金数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.exportLoading = true
        return exportCommission(queryParams)
      }).then(response => {
        this.download(response.msg)
        this.exportLoading = false
      }).catch(() => {
      })
    },
    resizeHandler() {
      let h = 18 + 45 + 84 + 35
      h = h + 'px'
      this.tableHeight = `calc(100vh - ${h})`
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
          deptCode: this.queryParams.deptCode,
          nowDate: this.queryParams.actDate,
          comment: this.form.comment,
          assignee: this.form.assignee
        }
      }
      return data
    },
    handleSave() {
      let data = this.packageData()
      data.isCommit = false
      addAudit(data).then(response => {
        this.msgSuccess('保存成功')
      })
    },
    handleCommit() {
      if (this.proclnsIds === null || this.proclnsIds === '') {
        this.handleSave()
      }
      this.showDialog = true
    },
    formItemSuccess() {
      let data = this.packageData()
      data.isCommit = true
      addAudit(data).then(response => {
        this.msgSuccess('提交成功')
        this.$emit('success', false)
      })
    },
    handleDialogClose() {
      this.$emit('cancel', false)
    },
    getDetailInfo() {
      listAudit({
        deptCode: this.queryParams.deptCode,
        nowDate: this.queryParams.nowDate
      }).then(response => {
        if (response.rows.length > 0) {
          let audit = response.rows[0]
          let that = this
          let proclnsIds = audit.procedureId
          this.form.procedureId = this.proclnsIds
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
    }

  }
}
</script>
<style lang="scss" scoped>
::v-deep .el-dialog {
  height: 90%;
  overflow: hidden;

  .el-dialog__body {
    padding: 10px !important;
    height: calc(100% - 100px);
    overflow: auto;
  }
}
</style>



