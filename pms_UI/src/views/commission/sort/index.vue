<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="40%" custom-class="el-button--primary" append-to-body
             :close-on-click-modal="false" @close="handleDialogClose"
  >
    <el-row :gutter=" 10
  " class="mb8"
    >
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['commission:sort:add']"
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
          v-hasPermi="['commission:sort:edit']"
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
          v-hasPermi="['commission:sort:remove']"
        >删除
        </el-button>
      </el-col>

<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    </el-row>

    <el-table border stripe v-loading="loading" :data="sortList" @selection-change="handleSelectionChange" height="calc(100vh - 350px)"
              ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column sortable label="类别编码" align="center" prop="sortCode"/>
      <el-table-column sortable label="类别名称" align="left" prop="sortName"/>
<!--      <el-table-column label="操作" align="center" fixed="right">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['commission:sort:edit']" title="修改"-->
<!--          ></el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['commission:sort:remove']" title="删除"-->
<!--          ></el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <addEdit v-if="open2" :open2="open2" :id="addEditId" @cancel="cancel2" @success="successSubmit"></addEdit>
  </el-dialog>
</template>

<script>
import { addSort, delSort, exportSort, listSort, updateSort } from '@/api/commission/sort'
import addEdit from './addEdit'

export default {
  name: 'Sort',
  components: {
    addEdit
  },
  props: {
    open: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      openDialog: this.open,
      // 遮罩层
      loading: true,
      //二级弹出康
      open2: false,
      addEditId: '',
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
      // 独立核算提成比类别表格数据
      sortList: [],
      selectionRow: [],
      // 弹出层标题
      title: '类别设置',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        sortCode: null,
        sortName: null,
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
    // 弹框关闭
    handleDialogClose() {
      this.$emit('cancel', false)
    },
    /** 查询独立核算提成比类别列表 */
    getList() {
      this.loading = true
      listSort(this.queryParams).then(response => {
        this.sortList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    successSubmit(flag) {
      this.open2 = flag
      this.getList()
    },
    // 取消按钮
    cancel2() {
      this.open2 = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        sortCode: null,
        sortName: null,
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
      this.title = "添加独立核算提成比类别";
    },
     */
    /** 新增按钮操作 */
    handleAdd() {
      this.open2 = true // 弹框点开
      this.addEditId = '' // addEditId 是新增  去子组件方便区分新增编辑
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids[0]
      this.open2 = true
      this.addEditId = id // id赋值
      console.log('addEditId---' + addEdit)
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSort(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addSort(this.form).then(response => {
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
      this.$confirm('是否确认删除独立核算提成比类别编号为"' + ids + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delSort(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出所有独立核算提成比类别数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.exportLoading = true
        return exportSort(queryParams)
      }).then(response => {
        this.download(response.msg)
        this.exportLoading = false
      }).catch(() => {
      })
    }
  }
}
</script>
<style lang="scss" scoped>
::v-deep .el-dialog {
  height: 80%;
  overflow: hidden;

  .el-dialog__body {
    height: 100%;
    overflow: auto;
  }
}
</style>
