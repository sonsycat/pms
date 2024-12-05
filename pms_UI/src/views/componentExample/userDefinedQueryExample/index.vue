<template>
  <div style="margin-left: 20px;margin-top: 20px;margin-right: 20px">

    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="招聘公司" prop="inviteCompany">
          <el-input
            v-model="queryParams.inviteCompany"
            placeholder="请输入招聘公司"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="发布时间">
          <el-date-picker
            v-model="dateRange"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <user-defined-query :show-user-defined-query="showUserDefinedQuery" :exclude-field-arr="excludeFieldArr"
                          :dict-field-obj="dictFieldObj" :dict-checkbox-arr="dictCheckboxArr" @refreshData="refreshData"
                          :page.sync="queryParams.pageNum" :pageSize.sync="queryParams.pageSize"
                          table-name="demo_invite" query-index="demo_invite_1"></user-defined-query>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"

            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['system:invite:add']"
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
            v-hasPermi="['system:invite:edit']"
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
            v-hasPermi="['system:invite:remove']"
          >删除
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"

            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:invite:export']"
          >导出
          </el-button>
        </el-col>
        <right-toolbar :showUserDefinedQuery.sync="showUserDefinedQuery"
                       :showUserDefinedQueryButton.sync="showUserDefinedQueryButton"
                       :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table border stripe v-loading="loading" :data="inviteList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column sortable label="招聘主题" align="center" prop="inviteTitle"/>
        <el-table-column sortable label="招聘公司" align="center" prop="inviteCompany"/>
        <el-table-column sortable label="招聘部门" align="center" prop="deptName"/>
        <el-table-column sortable label="招聘岗位" align="center" prop="invitePost" :formatter="invitePostFormat"/>
        <el-table-column sortable label="招聘人数" align="center" prop="inviteNumber"/>
        <el-table-column sortable label="负责人" align="center" prop="dutyOfficer"/>
        <el-table-column sortable label="联系电话" align="center" prop="phone"/>
        <el-table-column sortable label="优先级" align="center" :formatter="priorityFormat" prop="priority"/>
        <el-table-column sortable label="发布状态" align="center" :formatter="statusFormat" prop="status"/>
        <el-table-column sortable label="发布时间" align="center" prop="publishTime"/>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
  </div>
</template>

<script>
  import { listInvite } from '@/api/demo/componentExample'
  import UserDefinedQuery from '@/components/UserDefinedQuery'

  export default {
    name: 'userDefinedQueryExample',
    components: { UserDefinedQuery },
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        selectionRow: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 显示自定义查询按钮
        showUserDefinedQueryButton: true,
        // 显示自定义查询
        showUserDefinedQuery: false,
        // 不需要自定义查询的字段数组
        excludeFieldArr: ['invite_id', 'dept_id', 'del_flag'],
        // 多选框连接符只能选择包含的字段数组
        dictCheckboxArr: ['invite_post'],
        // 需要从字典中获取Options的自定义查询字段数组
        dictFieldObj: { 'invite_post': 'demo_invite_post', 'priority': 'demo_priority', 'status': 'demo_status' },
        // 日期范围
        dateRange: [],
        // 总条数
        total: 0,
        // 招聘表格数据
        inviteList: [],
        // 状态字典数组
        statusOptions: [],
        // 优先级字典数组
        priorityOptions: [],
        // 招聘岗位字典
        invitePostOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 30,
          inviteCompany: null
        }
      }
    },
    created() {
      this.getList()
      // 状态字典
      this.getDicts('demo_status').then(response => {
        this.statusOptions = response.data
      })
      // 优先级字典
      this.getDicts('demo_priority').then(response => {
        this.priorityOptions = response.data
      })
      // 招聘岗位
      this.getDicts('demo_invite_post').then(response => {
        this.invitePostOptions = response.data
      })
    },
    methods: {
      /** 查询demo招聘列表 */
      getList() {
        this.loading = true
        listInvite(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.inviteList = response.rows
          this.total = response.total
          this.loading = false
        })
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1
        this.getList()
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.dateRange = []
        this.resetForm('queryForm')
        this.handleQuery()
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.inviteId)
        this.single = selection.length !== 1
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
      rowClassName({
        row,
        rowIndex
      }) {
        let rowName = "",
          findRow = this.selectionRow.find(c => c.rowIndex === row.rowIndex);
        if (findRow) {
          rowName = "current-row ";
        }
        return rowName;
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.$message({
          showClose: true,
          message: '示例页面,不可操作'
        })
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.$message({
          showClose: true,
          message: '示例页面,不可操作'
        })
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        this.$message({
          showClose: true,
          message: '示例页面,不可操作'
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        this.$message({
          showClose: true,
          message: '示例页面,不可操作'
        })
      },
      // 状态字典翻译
      statusFormat(row, column) {
        if (row.menuType == 'F') {
          return ''
        }
        return this.selectDictLabel(this.statusOptions, row.status)
      },
      // 优先级字典翻译
      priorityFormat(row, column) {
        if (row.menuType == 'F') {
          return ''
        }
        return this.selectDictLabel(this.priorityOptions, row.priority)
      },
      // 招聘岗位字典翻译
      invitePostFormat(row, column) {
        return this.selectDictLabels(this.invitePostOptions, row.invitePost)
      },
      // 需要通过子组件调用的方法来刷新数据
      refreshData(response){
        this.inviteList = response.rows
        this.total = response.total
      }
    }
  }
</script>
