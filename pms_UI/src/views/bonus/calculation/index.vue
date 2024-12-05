<template>
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-date-picker clearable :editable="false" size="small"
                        v-model="queryParams.period" type="month"
                        value-format="yyyy-MM-dd" placeholder="选择奖金年月"
        >
        </el-date-picker>
        <el-form-item style="margin-left: 20px;">
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
            v-hasPermi="['bonus:calculation:add']"
          >生成新奖金
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-search"
            size="mini"
            :disabled="detailFlag"
            @click="showDetail"
            v-hasPermi="['bonus:calculation:detail']"
          >查看详情
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['bonus:calculation:remove']"
          >删除
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-edit-outline"
            size="mini"
            :disabled="submitFlag"
            @click="handleCommit"
          >提交
          </el-button>
        </el-col>
<!--        <el-col :span="1.5">-->
<!--          <el-button-->
<!--            type="success"-->
<!--            icon="el-icon-edit-outline"-->
<!--            size="mini"-->
<!--            :disabled="single"-->
<!--            @click="handleAudit"-->
<!--            v-hasPermi="['bonus:audit:commit']"-->
<!--          >审核-->
<!--          </el-button>-->
<!--        </el-col>-->
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-notebook-2"
            size="mini"
            :disabled="single"
            @click="processExamine"
            v-hasPermi="['bonus:calculation:list']"
          >流转历史
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="info"
            icon="el-icon-document"
            size="mini"
            :disabled="single"
            @click="archive"
            v-hasPermi="['bonus:calculation:archive']"
          >归档
          </el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <el-table border stripe v-loading="loading" :data="calculationList" :height="tableHeight"
              @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle"
              :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column sortable label="奖金名称" align="left" prop="name"/>
      <el-table-column sortable label="奖金年月" align="center" prop="period">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.period, '{y}年{m}月') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="updateTime"/>
      <el-table-column sortable label="审核状态" align="center" prop="auditState" :formatter="auditStateFormat"/>
      <el-table-column sortable label="是否归档" align="center" prop="archiveState" :formatter="yesOrNoFormat" width="100">
        <template v-slot="scope">
          <el-tag v-if="scope.row.archiveState != null" :type="scope.row.archiveState === '0' ? 'success' : 'warning'"
                  close-transition>{{scope.row.archiveState | yesOrNoFormat}}</el-tag>
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

    <addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>
    <audit v-if="open2" :open="open2" :id="addEditId" @cancel="cancel2" @success="successSubmit2"></audit>
    <!-- 流转历史对话框 -->
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
import {
  addCalculation,
  delCalculation,
  archiveCalculation,
  getProId,
  listCalculation,
  updateCalculation
} from '@/api/bonus/calculation'
import addEdit from './addEdit'
import audit from './audit'
var _self;
export default {
  name: 'Calculation',
  components: {
    addEdit, audit
  },
  data() {
    return {
      procedureId: '',
      tableHeight: 'auto',
      processOpen: false,
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      height: '500px;',
      src: '',
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      detailFlag: true,
      submitFlag: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 奖金核算表格数据
      calculationList: [],
      selectionRow: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      open2: false,
      // 审核状态字典
      auditStateOptions: [],
      // 是否
      yesOrNoOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        period: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: '奖金名称不能为空', trigger: 'blur' }
        ],
        period: [
          { required: true, message: '奖金年月不能为空', trigger: 'blur' }
        ],
        auditState: [
          { required: true, message: '审核状态不能为空', trigger: 'change' }
        ]
      }
    }
  },
  watch: {
    listData(val) {
      var that = this
      setTimeout(function() {
        that.resizeHandler()
      }, 300)
    }
  },
  beforeCreate() {
    _self = this;
  },
  created() {
    this.getList()
    this.getDicts('asset_aduit_state').then(response => {
      this.auditStateOptions = response.data
    });
    this.getDicts("yes_or_no").then(response => {
      this.yesOrNoOptions = response.data;
    });
    this.$nextTick(() => {
      this.resizeHandler()
      window.addEventListener('resize', this.resizeHandler)
    })
  },
  filters: {
    yesOrNoFormat(val){
      return _self.selectDictLabel(_self.yesOrNoOptions, val);
    }
  },
  methods: {
    /** 查询奖金核算列表 */
    getList() {
      this.loading = true
      listCalculation(this.queryParams).then(response => {
        this.calculationList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 审核状态字典翻译
    auditStateFormat(row, column) {
      return this.selectDictLabel(this.auditStateOptions, row.auditState)
    },
    successSubmit(flag) {
      this.open = flag
      this.getList()
    },
    successSubmit2(flag) {
      this.open2 = flag
      this.getList()
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    cancel2() {
      this.open2 = false
      this.getList()

    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        period: null,
        auditState: null,
        delFlag: null,
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
      this.queryParams.period = '';
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      /*let tableRef = this.$refs.multipleTable;
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
      }*/
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
      this.selectionRow = selection
      if(!this.single){
        let row = this.selectionRow[0];
        if(row.auditState == '0'){//草稿
          this.detailFlag = false;
          this.submitFlag = false;
        }else if(row.auditState == '1' || row.auditState == '2'){
          this.detailFlag = false;
        }
      }else{
        this.detailFlag = true;
        this.submitFlag = true;
      }
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
      let rowName = '',
        findRow = this.selectionRow.find(c => c.rowIndex === row.rowIndex)
      if (findRow) {
        rowName = 'current-row '
      }
      return rowName
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.open = true // 弹框点开
      this.addEditId = '' // addEditId 是新增  去子组件方便区分新增编辑
    },
    showDetail(row) {
      if(this.selectionRow && this.selectionRow.length > 0){
        this.$store.dispatch('tagsView/delView', this.$route)
        this.$router.push('/calculation/detail/' + this.selectionRow[0].period)
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCalculation(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addCalculation(this.form).then(response => {
              this.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      if(this.selectionRow && this.selectionRow.length > 0){
        for(let row of this.selectionRow){
          if(row.auditState != '0'){
            this.msgError('只有草稿状态的数据方可删除');
            return;
          }
        }
      }else{
        this.msgError('请选择要删除的数据');
        return;
      }
      this.$confirm('是否确认删除奖金核算编号为"' + ids + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delCalculation(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 归档按钮操作 */
    archive() {
      let row = this.selectionRow[0];
      if(row.auditState != '2'){
        this.msgError('只有审核通过的数据方可归档');
        return;
      }
      this.$confirm('是否确认归档?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return archiveCalculation(row.id)
      }).then(response => {
        this.msgSuccess(response.msg)
      }).catch(() => {

      })
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight
      let b = this.$refs.btns.offsetHeight
      let h = a + b + 18 + 45 + 84 + 40 + 20
      h = h + 'px'
      this.tableHeight = `calc(100vh - ${h})`
    },
    handleAudit(row) {
      const id = row.id || this.ids[0]
      if (this.selectionRow[0].auditState === '2') {
        this.msgError('奖金审核已通过')
        return false
      }
      if (this.selectionRow[0].auditState === '4') {
        this.msgError('奖金审核已终止')
        return false
      }
      if (this.selectionRow[0].auditState !== '1') {
        this.msgError('奖金审核未提交')
        return false
      }

      getProId({ id: id }).then(response => {
        this.procedureId = response.data
        this.open2 = true
        this.addEditId = id // id赋值
      })
    },
    handleCommit(row) {
      const id = row.id || this.ids[0]
      let state = this.selectionRow[0].auditState
      if (state !== '0' && state !== '3') {
        this.msgError('草稿状态才可提交')
        return false
      }
      this.loading = true
      getProId({ id: id }).then(response => {
        this.procedureId = response.data
        this.open2 = true
        this.addEditId = id // id赋值
        this.loading = false
      })

    },
    /** 流转历史初始化 */
    processExamineForm2() {
      this.src = `${this.processUrl}/api/wf/processHistoric?procInsId=${this.selectionRow[0].procedureId}`
    },
    /** 流转历史按钮操作 */
    processExamine() {
      let procedureId = this.selectionRow[0].procedureId
      if (procedureId == null || !procedureId) {
        this.msgError('没有流转历史数据！')
      } else {
        this.processExamineForm2()
        this.processOpen = true
      }
    },
    // 参数系统内置字典翻译
    yesOrNoFormat(row, column) {
      return this.selectDictLabel(this.yesOrNoOptions, row.archiveState);
    },
  }
}
</script>
