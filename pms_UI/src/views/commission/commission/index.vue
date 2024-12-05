<template>
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="年月" prop="commissionDate">
          <el-date-picker clearable size="small"
                          v-model="queryParams.commissionDate"
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
            type="primary"
            icon="el-icon-check"
            size="mini"
            @click="handleAdd"
            :loading="saveLoading"
            v-hasPermi="['commission:commission:add']"
          >保存
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            :loading="saveLoading"
            icon="el-icon-refresh"
            size="mini"
            @click="myRefresh"
            v-hasPermi="['commission:commission:list']"
          >刷新
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="info"
            icon="el-icon-setting"
            size="mini"
            @click="setSort"
            v-hasPermi="['commission:sort:list']"
          >类别设置
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-document-copy"
            size="mini"
            v-hasPermi="['commission:commission:add']"
            @click="copy"
          >复制
          </el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <el-table border stripe v-loading="loading" :data="commissionList" :height="tableHeight" @selection-change="handleSelectionChange"
              ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName"
    >

      <el-table-column sortable label="科室名称" align="left" prop="deptRoomName"/>
      <el-table-column
        v-for="(item,index) in sortList"
        :key="index"
        :label="item.sortName"
        :prop="item.sortCode"
        align="center"
      >
        <template slot-scope="scope">
          <el-input-number :precision="2" :step="0.01" :min="0" v-model="scope.row[item.sortCode]" controls-position="right"
          ></el-input-number>
        </template>
      </el-table-column>
    </el-table>
    <sortIndex v-if="open" :open="open" @cancel="cancel"></sortIndex>
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
import { addCommission, copyData, delCommission, exportCommission, updateCommission } from '@/api/commission/commission'
import sortIndex from '../sort/index'
import { listCommission } from '../../../api/commission/commission'
import { listSort } from '../../../api/commission/sort'
import { getYearMonth2 } from '@/utils/date'

export default {
  name: 'Commission',
  components: {
    sortIndex
  },
  filters: {
    transToValue(val, index) {
      return val[index]
    }
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
      openCopyEva: false,
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
      // 独立核算提成比表格数据
      commissionList: [],
      selectionRow: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      tableHeight: "auto",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        deptCode: null,
        compCode: null,
        deptName: null,
        accountCode: null,
        sortCode: null,
        ratio: null,
        delFlag: null,
        commissionDate: getYearMonth2() + '-01',
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      },
      // 表单参数
      form: {},
      copyForm: {},
      //类型
      sortList: [],
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
    this.getSort()

  },
  methods: {
    getSort() {
      listSort(null).then(response => {
        this.sortList = response.rows
        this.getList()
      })
    },

    setSort() {
      this.open = true
    },

    myRefresh() {
      this.$confirm('确定刷新当前数据吗，未保存数据即将清空！', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        this.getSort()
      }).catch(() => {
      })
    },

    /** 查询独立核算提成比列表 */
    getList() {
      this.loading = true
      if (!this.queryParams.commissionDate) {
        this.queryParams.commissionDate = getYearMonth2() + '-01'
      }
      listCommission(this.queryParams).then(response => {
        this.commissionList = response.data
        // this.commissionList.forEach(item => {
        //   let keys = Object.keys(item)
        //   keys.forEach(key => {
        //     if (item[key] == null) {
        //       item[key] = undefined
        //     }
        //   })
        // })
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
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        deptCode: null,
        compCode: null,
        deptName: null,
        accountCode: null,
        sortCode: null,
        ratio: null,
        delFlag: null,
        commissionDate: null,
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
    rowStyle({row, rowIndex}) {
      Object.defineProperty(row, 'rowIndex', {
        value: rowIndex,
        writable: true,
        enumerable: false
      })
    },
    rowClassName({row, rowIndex}) {
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
          'commissionDate': this.queryParams.commissionDate
        }
        addCommission(data).then(response => {
          this.msgSuccess('保存成功')
          this.saveLoading = false
        })
      })
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
            updateCommission(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addCommission(this.form).then(response => {
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
      this.$confirm('是否确认导出所有独立核算提成比数据项?', '警告', {
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
    copyCancel() {
      this.resetCopy()
      this.openCopyEva = false
    },
    resetCopy() {
      this.copyForm = {
        nowDate: null,
        copyDate: null
      }
      this.resetForm('copyForm')
    },
    submitcopyEvaForm() {
      let nowDate = this.copyForm.nowDate
      let copyDate = this.copyForm.copyDate
      this.$refs['copyForm'].validate(valid => {
        if (valid) {
          if (nowDate === copyDate) {
            this.msgError('日期不能相同')
            return
          } else {
            this.$confirm('复制将覆盖原有数据,是否确定复制?', '警告', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.loading = true
              copyData({ nowDate: nowDate, copyDate: copyDate }).then(response => {
                this.loading = false
                this.msgSuccess('复制成功')
                this.copyCancel()
              })
            })
          }
        }
      })
    },
    copy() {
      this.openCopyEva = true
      this.resetCopy()
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 35;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    }
  }
}
</script>


