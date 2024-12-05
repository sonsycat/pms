<template>
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">

        <el-form-item label="年月" prop="workloadDate">
          <el-date-picker clearable size="small"
                          v-model="queryParams.workloadDate"
                          type="month"
                          value-format="yyyy-MM-dd"
                          placeholder="选择年月"
          >
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
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['workload:workload:add']"
          >新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['workload:workload:edit']"
          >修改
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['workload:workload:remove']"
          >删除
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-setting"
            size="mini"
            @click="handleAddType"
            v-hasPermi="['workload:type:list']"
          >类别设置
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-setting"
            size="mini"
            @click="handleAddSelect"
            :disabled="single"
            v-hasPermi="['workload:type:list']"
          >设置收费项目
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-document-copy"
            size="mini"
            @click="copy"
            v-hasPermi="['workload:workload:edit']"
          >复制
          </el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <el-table border stripe v-loading="loading" :data="workloadList" :height="tableHeight" @selection-change="handleSelectionChange"
              ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column sortable label="项目编码" align="center" prop="projectCode"/>
      <el-table-column sortable label="项目名称" align="left" prop="projectName"/>
      <el-table-column sortable label="拼音码" align="left" prop="spell"/>
      <el-table-column sortable label="类别" align="left" prop="typeName"/>
      <el-table-column sortable label="住院积分" align="right" prop="hospitalPoints"/>
      <el-table-column sortable label="门诊积分" align="right" prop="outpatientPoints"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>
    <typeList v-if="openType" :openType="openType" @cancelType="cancelType"></typeList>
    <selectList v-if="openSelect" :projectCode="projectCode" :openSelect="openSelect" @cancelSelect="cancelSelect"
    ></selectList>
    <el-dialog title="复制" v-if="openCopyEva" class="copyEvaDialog" ref="copyEvaDialog" :visible.sync="openCopyEva"
               width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false"
               @close="copyCancel"
    >
      <el-form ref="copyForm" :model="copyForm" :rules="rules">
        <el-row>
          <el-col :span="24">
            <el-form-item label="复制年月" prop="nowDate">
              <el-date-picker clearable
                              class="w90"
                              v-model="copyForm.nowDate"
                              type="month"
                              value-format="yyyy-MM-dd"
                              placeholder="选择年月"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="拷贝年月" prop="copyDate">
              <el-date-picker clearable
                              class="w90"
                              v-model="copyForm.copyDate"
                              type="month"
                              value-format="yyyy-MM-dd"
                              placeholder="选择年月"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitcopyEvaForm">保 存</el-button>
        <el-button @click="copyCancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { addWorkload, copy, delWorkload, exportWorkload, listWorkload, updateWorkload } from '@/api/workload/workload'
import addEdit from './addEdit'
import typeList from '../type/index'
import selectList from '../toll/select'
import { getYearMonth2 } from '@/utils/date'

export default {
  name: 'Workload',
  components: {
    addEdit, typeList, selectList
  },
  mounted(){
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  watch: {
    listData(val) {
      let that = this;
      setTimeout(function () {
        that.resizeHandler();
      }, 300);
    }
  },
  data() {
    return {
      tableHeight: "auto",
      openCopyEva: false,
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
      // 工作量表格数据
      workloadList: [],
      selectionRow: [],
      copyForm: {},
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      openType: false,
      openSelect: false,
      projectCode: '',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode: null,
        accountCode: null,
        projectCode: null,
        projectName: null,
        type: null,
        spell: null,
        hospitalPoints: null,
        outpatientPoints: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        workloadDate: getYearMonth2() + '-01'
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
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询工作量列表 */
    getList() {
      this.loading = true
      if (!this.queryParams.workloadDate) {
        this.queryParams.workloadDate = getYearMonth2() + '-01'
      }
      listWorkload(this.queryParams).then(response => {
        this.workloadList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    successSubmit(flag) {
      this.open = flag
      this.getList()
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    cancelType() {
      this.openType = false
    },
    cancelSelect() {
      this.openSelect = false
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: null,
        accountCode: null,
        projectCode: null,
        projectName: null,
        type: null,
        spell: null,
        hospitalPoints: null,
        outpatientPoints: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        workloadDate: null
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
    /** 新增按钮操作
     handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工作量";
    },
     */
    /** 新增按钮操作 */
    handleAdd() {
      this.open = true // 弹框点开
      this.addEditId = '' // addEditId 是新增  去子组件方便区分新增编辑
    },
    handleAddType() {
      this.openType = true // 弹框点开
    },
    handleAddSelect(row) {
      const projectCode = this.selectionRow[0].projectCode
      this.openSelect = true // 弹框点开
      this.projectCode = projectCode
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids[0]
      this.open = true
      this.addEditId = id // id赋值
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWorkload(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addWorkload(this.form).then(response => {
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
      this.$confirm('是否确认删除工作量编号为"' + ids + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delWorkload(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出所有工作量数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.exportLoading = true
        return exportWorkload(queryParams)
      }).then(response => {
        this.download(response.msg)
        this.exportLoading = false
      }).catch(() => {
      })
    },
    copy() {
      this.openCopyEva = true
      this.resetCopy()
    },
    resetCopy() {
      this.copyForm = {
        nowDate: null,
        copyDate: null
      }
      this.resetForm('copyForm')
    },
    copyCancel() {
      this.resetCopy()
      this.openCopyEva = false
    },
    submitcopyEvaForm() {
      let nowDate = this.copyForm.nowDate
      let copyDate = this.copyForm.copyDate
      this.$refs['copyForm'].validate(valid => {
        if (valid) {
          if (nowDate === copyDate) {
            this.msgError('日期不能相同')

          } else {
            this.$confirm('复制将覆盖原有数据,是否确定复制?', '警告', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.loading = true
              copy({ nowDate: nowDate, copyDate: copyDate }).then(response => {
                this.loading = false
                this.msgSuccess('复制成功')
                this.copyCancel()
              })
            })
          }

        }
      })
    },
    //表格高度设置
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    }
  }
}
</script>
