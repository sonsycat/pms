<template>
  <div style="margin-left: 20px;margin-top: 20px;margin-right: 20px">
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="1">预览</el-menu-item>
      <el-menu-item index="2">查看源代码</el-menu-item>
    </el-menu>
    <div v-show="activeIndex==1">
      <div class="app-container" style="height: 400px;">
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
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table border stripe v-loading="loading" max-height="220" :data="inviteList" @selection-change="handleSelectionChange"
                  ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
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
      <div class="app-container" style="height: 250px;">
        <el-table border stripe v-loading="loading" max-height="240" :data="documentList">
          <el-table-column sortable label="文本名称" align="center" prop="documentName"/>
          <el-table-column sortable label="文本描述" align="center" prop="documentDesc"/>
          <el-table-column sortable label="版本" align="center" prop="version"/>
          <el-table-column sortable label="区域编码" align="center" prop="areaCode"/>
          <el-table-column sortable label="区域名称" align="center" prop="areaName"/>
          <el-table-column sortable label="发布时间" align="center" prop="publishTime"/>
        </el-table>
      </div>
    </div>
    <div v-show="activeIndex==2">
      <pre style="background-color: #dadada">{{code}}</pre>
    </div>
  </div>
</template>

<script>
  import { listInvite, listDocument } from '@/api/demo/componentExample'

  export default {
    name: 'upDownTable',
    components: {},
    data() {
      return {
        // 导航栏切换index
        activeIndex: '1',
        // 查看源代码code
        code: '',
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
        },
        documentList: []
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
      // 初始化源代码
      this.initCode()
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
        listDocument(row).then(response => {
          this.documentList = response.data
        })
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
      // 导航切换
      handleSelect(key, keyPath) {
        this.activeIndex = key
      },
      // 初始化源代码
      initCode() {
        this.code =
          '<template>\n' +
          '  <div style="margin-left: 20px;margin-top: 20px;margin-right: 20px">\n' +
          '    <div class="app-container" style="height: 400px;">\n' +
          '      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">\n' +
          '        <el-form-item label="招聘公司" prop="inviteCompany">\n' +
          '          <el-input\n' +
          '            v-model="queryParams.inviteCompany"\n' +
          '            placeholder="请输入招聘公司"\n' +
          '            clearable\n' +
          '            size="small"\n' +
          '            @keyup.enter.native="handleQuery"\n' +
          '          />\n' +
          '        </el-form-item>\n' +
          '        <el-form-item label="发布时间">\n' +
          '          <el-date-picker\n' +
          '            v-model="dateRange"\n' +
          '            size="small"\n' +
          '            style="width: 240px"\n' +
          '            value-format="yyyy-MM-dd"\n' +
          '            type="daterange"\n' +
          '            range-separator="-"\n' +
          '            start-placeholder="开始日期"\n' +
          '            end-placeholder="结束日期"\n' +
          '          ></el-date-picker>\n' +
          '        </el-form-item>\n' +
          '        <el-form-item>\n' +
          '          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>\n' +
          '          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>\n' +
          '        </el-form-item>\n' +
          '      </el-form>\n' +
          '      <el-row :gutter="10" class="mb8">\n' +
          '        <el-col :span="1.5">\n' +
          '          <el-button\n' +
          '            type="primary"\n' +
          '            plain\n' +
          '            icon="el-icon-plus"\n' +
          '            size="mini"\n' +
          '            @click="handleAdd"\n' +
          '            v-hasPermi="[\'system:invite:add\']"\n' +
          '          >新增\n' +
          '          </el-button>\n' +
          '        </el-col>\n' +
          '        <el-col :span="1.5">\n' +
          '          <el-button\n' +
          '            type="success"\n' +
          '            plain\n' +
          '            icon="el-icon-edit"\n' +
          '            size="mini"\n' +
          '            :disabled="single"\n' +
          '            @click="handleUpdate"\n' +
          '            v-hasPermi="[\'system:invite:edit\']"\n' +
          '          >修改\n' +
          '          </el-button>\n' +
          '        </el-col>\n' +
          '        <el-col :span="1.5">\n' +
          '          <el-button\n' +
          '            type="danger"\n' +
          '            plain\n' +
          '            icon="el-icon-delete"\n' +
          '            size="mini"\n' +
          '            :disabled="multiple"\n' +
          '            @click="handleDelete"\n' +
          '            v-hasPermi="[\'system:invite:remove\']"\n' +
          '          >删除\n' +
          '          </el-button>\n' +
          '        </el-col>\n' +
          '        <el-col :span="1.5">\n' +
          '          <el-button\n' +
          '            type="warning"\n' +
          '            plain\n' +
          '            icon="el-icon-download"\n' +
          '            size="mini"\n' +
          '            @click="handleExport"\n' +
          '            v-hasPermi="[\'system:invite:export\']"\n' +
          '          >导出\n' +
          '          </el-button>\n' +
          '        </el-col>\n' +
          '        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>\n' +
          '      </el-row>\n' +
          '\n' +
          '      <el-table border stripe v-loading="loading" max-height="220" :data="inviteList" @selection-change="handleSelectionChange"\n' +
          '                 ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">\n' +
          '        <el-table-column type="selection" width="55" align="center"/>\n' +
          '        <el-table-column sortable label="招聘主题" align="center" prop="inviteTitle"/>\n' +
          '        <el-table-column sortable label="招聘公司" align="center" prop="inviteCompany"/>\n' +
          '        <el-table-column sortable label="招聘部门" align="center" prop="deptName"/>\n' +
          '        <el-table-column sortable label="招聘岗位" align="center" prop="invitePost" :formatter="invitePostFormat"/>\n' +
          '        <el-table-column sortable label="招聘人数" align="center" prop="inviteNumber"/>\n' +
          '        <el-table-column sortable label="负责人" align="center" prop="dutyOfficer"/>\n' +
          '        <el-table-column sortable label="联系电话" align="center" prop="phone"/>\n' +
          '        <el-table-column sortable label="优先级" align="center" :formatter="priorityFormat" prop="priority"/>\n' +
          '        <el-table-column sortable label="发布状态" align="center" :formatter="statusFormat" prop="status"/>\n' +
          '        <el-table-column sortable label="发布时间" align="center" prop="publishTime"/>\n' +
          '      </el-table>\n' +
          '      <pagination\n' +
          '        v-show="total>0"\n' +
          '        :total="total"\n' +
          '        :page.sync="queryParams.pageNum"\n' +
          '        :limit.sync="queryParams.pageSize"\n' +
          '        @pagination="getList"\n' +
          '      />\n' +
          '    </div>\n' +
          '    <div class="app-container" style="height: 250px;">\n' +
          '      <el-table border stripe v-loading="loading" max-height="240" :data="documentList">\n' +
          '        <el-table-column sortable label="文本名称" align="center" prop="documentName"/>\n' +
          '        <el-table-column sortable label="文本描述" align="center" prop="documentDesc"/>\n' +
          '        <el-table-column sortable label="版本" align="center" prop="version"/>\n' +
          '        <el-table-column sortable label="区域编码" align="center" prop="areaCode"/>\n' +
          '        <el-table-column sortable label="区域名称" align="center" prop="areaName"/>\n' +
          '        <el-table-column sortable label="发布时间" align="center" prop="publishTime"/>\n' +
          '      </el-table>\n' +
          '    </div>\n' +
          '  </div>\n' +
          '</template>\n' +
          '\n' +
          '<script>\n' +
          '  import { listInvite,listDocument } from \'@/api/demo/componentExample\'\n' +
          '\n' +
          '  export default {\n' +
          '    name: \'upDownTable\',\n' +
          '    components: {},\n' +
          '    data() {\n' +
          '      return {\n' +
          '        // 遮罩层\n' +
          '        loading: true,\n' +
          '        // 选中数组\n' +
          '        ids: [],\n' +
          '         selectionRow: [],\n' +
          '        // 非单个禁用\n' +
          '        single: true,\n' +
          '        // 非多个禁用\n' +
          '        multiple: true,\n' +
          '        // 显示搜索条件\n' +
          '        showSearch: true,\n' +
          '        // 日期范围\n' +
          '        dateRange: [],\n' +
          '        // 总条数\n' +
          '        total: 0,\n' +
          '        // 招聘表格数据\n' +
          '        inviteList: [],\n' +
          '        // 状态字典数组\n' +
          '        statusOptions: [],\n' +
          '        // 优先级字典数组\n' +
          '        priorityOptions: [],\n' +
          '        // 招聘岗位字典\n' +
          '        invitePostOptions: [],\n' +
          '        // 查询参数\n' +
          '        queryParams: {\n' +
          '          pageNum: 1,\n' +
          '          pageSize: 30,\n' +
          '          inviteCompany: null\n' +
          '        },\n' +
          '        documentList: []\n' +
          '      }\n' +
          '    },\n' +
          '    created() {\n' +
          '      this.getList()\n' +
          '      // 状态字典\n' +
          '      this.getDicts(\'demo_status\').then(response => {\n' +
          '        this.statusOptions = response.data\n' +
          '      })\n' +
          '      // 优先级字典\n' +
          '      this.getDicts(\'demo_priority\').then(response => {\n' +
          '        this.priorityOptions = response.data\n' +
          '      })\n' +
          '      // 招聘岗位\n' +
          '      this.getDicts(\'demo_invite_post\').then(response => {\n' +
          '        this.invitePostOptions = response.data\n' +
          '      })\n' +
          '    },\n' +
          '    methods: {\n' +
          '      /** 查询demo招聘列表 */\n' +
          '      getList() {\n' +
          '        this.loading = true\n' +
          '        listInvite(this.addDateRange(this.queryParams, this.dateRange)).then(response => {\n' +
          '          this.inviteList = response.rows\n' +
          '          this.total = response.total\n' +
          '          this.loading = false\n' +
          '        })\n' +
          '      },\n' +
          '      /** 搜索按钮操作 */\n' +
          '      handleQuery() {\n' +
          '        this.queryParams.pageNum = 1\n' +
          '        this.getList()\n' +
          '      },\n' +
          '      /** 重置按钮操作 */\n' +
          '      resetQuery() {\n' +
          '        this.dateRange = []\n' +
          '        this.resetForm(\'queryForm\')\n' +
          '        this.handleQuery()\n' +
          '      },\n' +
          '      // 多选框选中数据\n' +
          '      handleSelectionChange(selection) {\n' +
          '        this.ids = selection.map(item => item.inviteId)\n' +
          '        this.single = selection.length !== 1\n' +
          '        this.multiple = !selection.length\n' +
          '        this.selectionRow=selection\n' +
          '      },\n' +
          '     rowClick(row, column, event) {\n' +
          '      let refsElTable = this.$refs.multipleTable;\n' +
          '      let findRow = this.selectionRow.find(c => c.rowIndex == row.rowIndex);\n' +
          '      if (findRow && this.selectionRow.length == 1) {\n' +
          '        refsElTable.toggleRowSelection(row, false);\n' +
          '        return;\n' +
          '      }\n' +
          '      refsElTable.clearSelection();\n' +
          '      refsElTable.toggleRowSelection(row);\n' +
          '       listDocument(row).then(response => {\n' +
          '          this.documentList = response.data\n' +
          '        })\n' +
          '    },\n' +
          '    rowStyle({\n' +
          '      row,\n' +
          '      rowIndex\n' +
          '    }) {\n' +
          '      Object.defineProperty(row, \'rowIndex\', {\n' +
          '        value: rowIndex,\n' +
          '        writable: true,\n' +
          '        enumerable: false\n' +
          '      })\n' +
          '    },\n' +
          '    rowClassName({\n' +
          '      row,\n' +
          '      rowIndex\n' +
          '    }) {\n' +
          '      let rowName = "",\n' +
          '        findRow = this.selectionRow.find(c => c.rowIndex === row.rowIndex);\n' +
          '      if (findRow) {\n' +
          '        rowName = "current-row ";\n' +
          '      }\n' +
          '      return rowName;\n' +
          '    },  \n' +
          '      /** 新增按钮操作 */\n' +
          '      handleAdd() {\n' +
          '        this.$message({\n' +
          '          showClose: true,\n' +
          '          message: \'示例页面,不可操作\'\n' +
          '        })\n' +
          '      },\n' +
          '      /** 修改按钮操作 */\n' +
          '      handleUpdate(row) {\n' +
          '        this.$message({\n' +
          '          showClose: true,\n' +
          '          message: \'示例页面,不可操作\'\n' +
          '        })\n' +
          '      },\n' +
          '      /** 删除按钮操作 */\n' +
          '      handleDelete(row) {\n' +
          '        this.$message({\n' +
          '          showClose: true,\n' +
          '          message: \'示例页面,不可操作\'\n' +
          '        })\n' +
          '      },\n' +
          '      /** 导出按钮操作 */\n' +
          '      handleExport() {\n' +
          '        this.$message({\n' +
          '          showClose: true,\n' +
          '          message: \'示例页面,不可操作\'\n' +
          '        })\n' +
          '      },\n' +
          '      // 状态字典翻译\n' +
          '      statusFormat(row, column) {\n' +
          '        if (row.menuType == \'F\') {\n' +
          '          return \'\'\n' +
          '        }\n' +
          '        return this.selectDictLabel(this.statusOptions, row.status)\n' +
          '      },\n' +
          '      // 优先级字典翻译\n' +
          '      priorityFormat(row, column) {\n' +
          '        if (row.menuType == \'F\') {\n' +
          '          return \'\'\n' +
          '        }\n' +
          '        return this.selectDictLabel(this.priorityOptions, row.priority)\n' +
          '      },\n' +
          '      // 招聘岗位字典翻译\n' +
          '      invitePostFormat(row, column) {\n' +
          '        return this.selectDictLabels(this.invitePostOptions, row.invitePost)\n' +
          '      },\n' +
          '    }\n' +
          '  }\n' +
          '<\/script>\n' +
          '\n' +
          '<style scoped>\n' +
          ' .el-table__body-wrapper.is-scrolling-none {\n' +
          '   overflow-y: auto;\n' +
          '   max-height: calc(100vh - 457px);\n' +
          ' }\n' +
          '</style>\n'
      }
    }
  }
</script>

<style scoped>
  .el-table__body-wrapper.is-scrolling-none {
    overflow-y: auto;
    max-height: calc(100vh - 457px);
  }
</style>
