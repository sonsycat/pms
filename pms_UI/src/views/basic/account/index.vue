<template>
    <!-- 基础信息 - 账套管理 -->
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <orgSearch label="单位" :queryParams="queryParams" compCode="deptId" compName="deptName"></orgSearch>
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
              v-hasPermi="['basic:account:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"

              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['basic:account:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"

              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['basic:account:remove']"
            >删除</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
    </div>
    <el-table v-loading="loading" ref="accountListRef" :height="tableHeight" :data="accountList" @selection-change="handleSelectionChange" stripe :default-sort="defaultSort" @sort-change="handleSortChange"  highlight-current-row @row-click="handleRowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="单位名称" align="left" prop="deptName" show-overflow-tooltip sortable="custom" :sort-orders="['descending', 'ascending']" />
      <el-table-column label="账套编码" align="center" prop="accountCode" sortable="custom" :sort-orders="['descending', 'ascending']" />
      <el-table-column label="账套名称" align="left" prop="accountName" show-overflow-tooltip sortable="custom" :sort-orders="['descending', 'ascending']">
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" v-html="scope.row.accountName" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column label="账套开始年度" width="140" align="center" prop="startYear" sortable="custom" :sort-orders="['descending', 'ascending']" />
      <el-table-column label="账套开始月份"  width="140" align="center" prop="startMonth" sortable="custom" :sort-orders="['descending', 'ascending']" />
      <el-table-column label="结束日期" width="120" align="center" prop="endDate"  sortable="custom" :sort-orders="['descending', 'ascending']">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
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

    <!-- 添加或修改账套管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body :close-on-click-modal="false"  custom-class="el-button--primary">
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="账套编码" prop="accountCode">
              <el-input v-model="form.accountCode" placeholder="系统自动生成" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账套名称" prop="accountName">
              <el-input v-model="form.accountName" placeholder="请输入账套名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <orgSearch label="单位" :queryParams="form" compCode="deptId" compName="deptName"></orgSearch>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账套开始年度" prop="startYear">
              <el-date-picker
                v-model="form.startYear"
                type="year"
                placeholder="选择账套开始年度">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账套开始月份" prop="startMonth">
              <el-date-picker
                v-model="form.startMonth"
                type="month"
                placeholder="选择账套开始月份">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束日期" prop="endDate">
              <el-date-picker clearable
                v-model="form.endDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择结束日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listAccount,
  getAccount,
  delAccount,
  addAccount,
  updateAccount,
  exportAccount
} from "@/api/basic/account";
import orgSearch from "@/views/components/orgSearch";

export default {
  name: "Account",
  components: {
    orgSearch
  },
  data() {
    return {
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
      // 账套管理表格数据
      accountList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        deptId: this.$store.state.user.userInfo.deptId,
        deptName: this.$store.state.user.userInfo.deptName
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        deptName: [
          { required: true, message: "单位不能为空", trigger: "blur" },
          { required: true, message: "单位不能为空", trigger: "change" }
        ],
        accountName: [
          { required: true, message: "账套名称不能为空", trigger: "blur" }
        ]
      },
      defaultSort: { prop: "createTime", order: "descending" },
      selectionRow: [],
      tableHeight: "auto"
    };
  },
  computed: {
    listData() {
      return this.$store.state.app.sidebar.opened;
    }
  },
  watch: {
    listData(val) {
      var that = this;
      setTimeout(function() {
        that.resizeHandler();
      }, 300);
    }
  },
  created() {
    this.getList();
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  methods: {
    handleClickTableName(data) {
      // 选中当前行
      this.handleRowClick(data, false);
      this.handleUpdate(data);
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    /** 查询账套管理列表 */
    getList() {
      this.loading = true;
      listAccount(this.queryParams).then(response => {
        if (response.code == 200) {
          this.accountList = response.rows;
          this.total = response.total;
        } else {
          this.msgError(response.msg);
        }
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        accountCode: null,
        accountName: null,
        deptId: null,
        deptName: null,
        startYear: null,
        startMonth: null,
        endDate: null,
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        delFlag: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
    handleRowClick(row, flag = true) {
      let accountListRef = this.$refs.accountListRef;
      if (flag) {
        let findRow = this.selectionRow.find(c => c.id == row.id);
        if (findRow) {
          accountListRef.toggleRowSelection(row, false);
          return;
        }
      }
      accountListRef.clearSelection(); //清空列表的选中
      accountListRef.toggleRowSelection(row, true);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      let accountListRef = this.$refs.accountListRef;
      if (selection.length > 1) {
        accountListRef.clearSelection(); //清空列表的选中
        accountListRef.toggleRowSelection(
          selection[selection.length - 1],
          true
        ); //只显示选中最后一个 这时selection还是多选的列表(就是你选中的几个数据)
        selection = Array(selection[selection.length - 1]);
      } else if (selection.length === 1) {
        selection = Array(selection[selection.length - 1]);
      } else {
        selection = [];
      }
      // 方便点击行取消选中
      this.selectionRow = selection;
      // 设置行高亮
      accountListRef.setCurrentRow(selection[0]);
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$refs.accountListRef.clearSelection();
      this.reset();
      this.open = true;
      this.title = "账套管理 / 新增";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      const loading = this.dataLoading()                   
      getAccount(id).then(response => {
        if (response.code == 200) {
          this.form = response.data;
          this.open = true;
          this.title = "账套管理 / 修改";
          loading.close()                          
        } else {
          loading.close()                 
          this.msgError(response.msg);
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAccount(this.form).then(response => {
              if (response.code == 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addAccount(this.form).then(response => {
              if (response.code == 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(
        '是否确认删除账套管理编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delAccount(ids);
        })
        .then(response => {
          if (response.code == "200") {
            this.getList();
            this.msgSuccess("删除成功!");
          } else {
            this.msgError(response.msg);
          }
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有账套管理数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.exportLoading = true;
          return exportAccount(queryParams);
        })
        .then(response => {
          if (response.code == 200) {
            this.download(response.msg);
            this.exportLoading = false;
          } else {
            this.msgError(response.msg);
          }
        })
        .catch(() => {});
    }
  }
};
</script>
<style lang="scss" scoped>
::v-deep{
  .el-date-editor.el-input, .el-date-editor.el-input__inner{
    width: 100%;
  }
  .el-form-item__content{
    width:220px !important;
  }
  .el-dialog{
    .el-dialog__body{
      .el-form-item__content{
        width: 70% !important;
        .el-form-item__content{
           width: 100% !important;
        }
      }
    }
  }
}

</style>

