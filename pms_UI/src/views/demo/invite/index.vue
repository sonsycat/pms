<template>
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
      <el-form-item label="招聘部门" prop="deptId">
        <el-input v-on:click.native="deptSearchDialog=true" placeholder="选择招聘部门" v-model="queryShowDeptName" disabled/>
        <el-input type="text" style="display:none" v-model="queryParams.deptId"/>
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

    <!-- 招聘部门search-dialog -->
    <el-theme-dialog :visible.sync="deptSearchDialog" :close-on-click-modal="false" title="选择机构" append-to-body width="500px">
      <div style="height: 400px;overflow-y: scroll">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            :load="getTreeSelect"
            lazy
            @node-click="handleNodeClick"
          />
        </div>
      </div>
    </el-theme-dialog>

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
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['system:invite:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table border stripe v-loading="loading" :data="inviteList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column sortable label="招聘主题" align="center" prop="inviteTitle"/>
      <el-table-column sortable label="招聘公司" align="center" prop="inviteCompany"/>
      <el-table-column sortable label="招聘部门" align="center" prop="deptName"/>
      <el-table-column sortable label="招聘岗位" align="center" prop="invitePost" :formatter="invitePostFormat" />
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

    <!-- 添加或修改demo招聘对话框 -->
    <el-theme-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="招聘主题" prop="inviteTitle">
              <el-input v-model="form.inviteTitle" placeholder="请输入招聘主题"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="招聘公司" prop="inviteCompany">
              <el-input v-model="form.inviteCompany" placeholder="请输入招聘公司"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="招聘部门" prop="deptName">
              <el-input v-on:click.native="deptSearchFormDialog=true" placeholder="请选择招聘部门" v-model="form.deptName"
                        disabled/>
              <el-input type="text" style="display:none" v-model="form.deptId"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="招聘人数" prop="inviteNumber">
              <el-input v-model="form.inviteNumber" placeholder="请输入招聘人数"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="招聘岗位">
              <el-checkbox-group v-model="form.invitePost">
                <el-checkbox
                  v-for="dict in invitePostOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue">
                  {{dict.dictLabel}}
                </el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="负责人" prop="dutyOfficer">
              <el-input v-model="form.dutyOfficer" placeholder="请输入负责人"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="优先级" prop="priority">
              <el-select v-model="form.priority" placeholder="优先级" clearable size="small">
                <el-option
                  v-for="dict in priorityOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发布状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="招聘要求" prop="inviteAsk">
              <el-input v-model="form.inviteAsk" type="textarea" :rows="2" placeholder="请输入内容"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="公司地址" prop="companyAddress">
              <el-input v-model="form.companyAddress" type="textarea" :rows="2" placeholder="请输入内容"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="请输入备注"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-theme-dialog>
    <!-- Form招聘部门search-dialog -->
    <el-theme-dialog :visible.sync="deptSearchFormDialog" :close-on-click-modal="false" title="选择机构" append-to-body
               width="500px">
      <div style="height: 400px;overflow-y: scroll">
        <div class="head-container">
          <el-input
            v-model="deptNameForm"
            placeholder="请输入机构名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="treeForm"
            :load="getTreeSelect"
            lazy
            @node-click="handleFormNodeClick"
          />
        </div>
      </div>
    </el-theme-dialog>
  </div>
</template>

<script>
  import { listInvite, getInvite, delInvite, addInvite, updateInvite, exportInvite } from '@/api/demo/invite'
  import { treeselect } from '@/api/system/dept'

  export default {
    name: 'Invite',
    components: {},
    data() {
      return {
        // 遮罩层
        loading: true,
        // 导出遮罩层
        exportLoading: false,
        // 选中数组
        ids: [],
        selectionRow: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // demo招聘表格数据
        inviteList: [],
        // 弹出层标题
        title: '',
        // 是否显示弹出层
        open: false,
        // 状态字典数组
        statusOptions: [],
        // 优先级字典数组
        priorityOptions: [],
        // 表格上方部门查询dialog
        deptSearchDialog: false,
        // 用于显示查询的部门名称
        queryShowDeptName: '',
        // 机构树选项
        deptOptions: undefined,
        // dialog中search机构名称
        deptName: undefined,
        // Form表单机构查询dialog
        deptSearchFormDialog: false,
        // Form中search机构名称
        deptNameForm: undefined,
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        // 日期范围
        dateRange: [],
        // 招聘岗位字典
        invitePostOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 30,
          inviteCompany: null,
          invitePost: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      }
    },
    watch: {
      // 根据名称筛选部门树
      deptName(val) {
        this.$refs.tree.filter(val)
      },
      // 根据名称筛选部门树
      deptNameForm(val) {
        this.$refs.treeForm.filter(val)
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
        console.log(this.invitePostOptions)
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
      // 获取左侧部门数据
      getTreeSelect(node, resolve) {
        let pid = ''
        if (node.level == 0) {
          pid = ''
        } else {
          pid = node.data.id
        }
        setTimeout(() => {
          treeselect({ parentId: pid }).then(response => {
            if (resolve) {
              resolve(response.data)
            } else {
              this.deptOptions = response.data
            }
          })
        }, 100)
      },
      // 取消按钮
      cancel() {
        this.open = false
        this.reset()
      },
      // 表单重置
      reset() {
        this.form = {
          inviteId: null,
          inviteTitle: null,
          inviteCompany: null,
          deptId: null,
          deptName: null,
          invitePost: [],
          inviteNumber: null,
          inviteAsk: null,
          companyAddress: null,
          dutyOfficer: null,
          phone: null,
          priority: null,
          status: '0',
          remark: null,
          delFlag: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null
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
        this.queryShowDeptName = ''
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
        this.reset()
        this.open = true
        this.title = '添加demo招聘'
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset()
        const inviteId = row.inviteId || this.ids
        getInvite(inviteId).then(response => {
          this.form = response.data
          this.form.invitePost = this.form.invitePost.split(",");
          this.open = true
          this.title = '修改demo招聘'
        })
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            this.form.invitePost = this.form.invitePost.join(",");
            if (this.form.inviteId != null) {
              updateInvite(this.form).then(response => {
                this.msgSuccess('修改成功')
                this.open = false
                this.getList()
              })
            } else {
              addInvite(this.form).then(response => {
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
        const inviteIds = row.inviteId || this.ids
        this.$confirm('是否确认删除demo招聘编号为"' + inviteIds + '"的数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          return delInvite(inviteIds)
        }).then(() => {
          this.getList()
          this.msgSuccess('删除成功')
        }).catch(() => {
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams
        this.$confirm('是否确认导出所有demo招聘数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.exportLoading = true
          return exportInvite(queryParams)
        }).then(response => {
          this.download(response.msg)
          this.exportLoading = false
        }).catch(() => {
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
      // search筛选节点
      filterNode(value, data) {
        if (!value) return true
        return data.label.indexOf(value) !== -1
      },
      // search节点单击事件
      handleNodeClick(data) {
        this.queryParams.deptId = data.id
        this.queryShowDeptName = data.label
        this.deptSearchDialog = false
      },
      // form表单中search节点单击事件
      handleFormNodeClick(data) {
        this.form.deptId = data.id
        this.form.deptName = data.label
        this.deptSearchFormDialog = false
      },
      // 招聘岗位字典翻译
      invitePostFormat(row, column) {
        return this.selectDictLabels(this.invitePostOptions, row.invitePost);
      },
    }
  }
</script>
