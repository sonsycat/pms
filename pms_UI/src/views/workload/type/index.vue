<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="40%" custom-class="el-button--primary" append-to-body
             :close-on-click-modal="false" @close="handleDialogClose"
  >
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['workload:type:add']"
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
          v-hasPermi="['workload:type:edit']"
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
          v-hasPermi="['workload:type:remove']"
        >删除
        </el-button>
      </el-col>
<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    </el-row>

    <el-table border stripe v-loading="loading" :data="typeList" @selection-change="handleSelectionChange"
              ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column sortable label="类别编码" align="center" prop="typeCode"/>
      <el-table-column sortable label="类别名称" align="left" prop="typeName"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

        <addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>
  </el-dialog>
</template>

<script>
import { addType, delType, listType, updateType } from '@/api/workload/type'
import addEdit from './addEdit'

export default {
  name: 'Type',
  components: {
    addEdit
  },
  props: {
    openType: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    openType: {
      handler(val) {
        this.openDialog = val
      },
      deep: true
    }
  },
  data() {
    return {
      openDialog: this.openType,
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
      // 工作量-类别设置表格数据
      typeList: [],
      selectionRow: [],
      // 弹出层标题
      title: '类别设置',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        projectCode: null,
        typeName: null,
        typeCode: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询工作量-类别设置列表 */
    getList() {
      this.loading = true
      listType(this.queryParams).then(response => {
        this.typeList = response.rows
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
    // 表单重置
    reset() {
      this.form = {
        id: null,
        projectCode: null,
        typeName: null,
        typeCode: null,
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
      this.title = "添加工作量-类别设置";
    },
     */
    /** 新增按钮操作 */
    handleAdd() {
      this.open = true // 弹框点开
      this.addEditId = '' // addEditId 是新增  去子组件方便区分新增编辑
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids[0]
      this.open = true
      this.addEditId = id // id赋值
      console.log('addEditId---' + addEdit)
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateType(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addType(this.form).then(response => {
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
      this.$confirm('是否确认删除工作量-类别设置编号为"' + ids + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delType(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      }).catch(() => {
      })
    },

    handleDialogClose() {
      this.$emit('cancelType', false)
    }
  }
}
</script>

<style lang="scss" scoped>
::v-deep .el-dialog {
  height: 70%;
  overflow: hidden;

  .el-dialog__body {
    height: 100%;
    overflow: auto;
  }
}
</style>

