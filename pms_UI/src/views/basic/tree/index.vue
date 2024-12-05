<template>
    <!-- 基础信息 - 树级规则管理 -->
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
        <el-form-item label="规则名称" prop="ruleName">
          <el-input
            class="search_w220"
            v-model="queryParams.ruleName"
            placeholder="请输入规则名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="表名" prop="ruleDb">
          <el-input
            class="search_w220"
            v-model="queryParams.ruleDb"
            placeholder="请输入规则数据库表"
            clearable
            @keyup.enter.native="handleQuery"
          />
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
            v-hasPermi="['encode:tree:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"

            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['encode:tree:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"

            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['encode:tree:remove']"
          >删除</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>
    <el-table :height="tableHeight" ref="treeListTable" v-loading="loading" :data="treeList" @selection-change="handleSelectionChange" :default-sort="defaultSort" @sort-change="handleSortChange"  highlight-current-row @row-click="handleRowClick" >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="规则名称" align="left" prop="ruleName" sortable="custom" :sort-orders="['descending', 'ascending']" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-link type="primary" :underline="false" v-html="scope.row.ruleName" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column label="规则数据库表" align="left" prop="ruleDb" sortable="custom" :sort-orders="['descending', 'ascending']" show-overflow-tooltip/>
      <el-table-column label="规则数据库对应字段" align="left" prop="ruleColumn" sortable="custom" :sort-orders="['descending', 'ascending']" show-overflow-tooltip/>
      <el-table-column label="规则内容" align="left" prop="ruleContent" sortable="custom" :sort-orders="['descending', 'ascending']" show-overflow-tooltip/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改编码规则树形对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="50%" append-to-body :close-on-click-modal="false" custom-class="el-button--primary">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="36">
          <el-col :span="12">
            <el-form-item label="规则名称" prop="ruleName">
              <el-input v-model="form.ruleName" placeholder="请输入规则名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="规则数据库表" prop="ruleDb">
              <el-input v-model="form.ruleDb" placeholder="请输入规则数据库表" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="对应字段" prop="ruleColumn">
              <el-input v-model="form.ruleColumn" placeholder="请输入规则数据库对应字段" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="规则内容" prop="ruleContent">
              <el-input v-model="form.ruleContent" placeholder="请输入规则内容" />
            </el-form-item>
          </el-col>
          <el-col :span="12" style="display: none">
            <el-form-item label="删除标记" prop="delFlag">
              <el-input v-model="form.delFlag" placeholder="请输入删除标记" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"  maxlength="100" show-word-limit rows="3" resize="none"/> 
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
  listTree,
  getTree,
  delTree,
  addTree,
  updateTree,
  exportTree
} from "@/api/encode/tree";

export default {
  name: "Tree",
  components: {},
  data() {
    return {
      defaultSort: { prop: "createTime", order: "descending" },
      tableHeight: "auto",
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
      // 编码规则树形表格数据
      treeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ruleName: null,
        ruleDb: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        ruleName: [
          { required: true, message: "规则名称不能为空", trigger: "blur" }
        ],
        ruleDb: [
          { required: true, message: "规则数据库表不能为空", trigger: "blur" }
        ],
        ruleColumn: [
          {
            required: true,
            message: "规则数据库对应字段不能为空",
            trigger: "blur"
          }
        ],
        ruleContent: [
          { required: true, message: "规则内容不能为空", trigger: "blur" }
        ]
      },
      selectionRow: []
    };
  },
  created() {
    this.getList();
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
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
  methods: {
    handleClickTableName(data) {
      // 选中当前行
      this.handleRowClick(data, false);
      this.handleUpdate(data);
    },
    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    /** 查询编码规则树形列表 */
    getList() {
      this.loading = true;
      listTree(this.queryParams).then(response => {
        if (response.code == 200) {
          this.treeList = response.rows;
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
        ruleName: null,
        ruleDb: null,
        ruleColumn: null,
        ruleContent: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
    // 点击行
    handleRowClick(row, flag = true) {
      let treeListTable = this.$refs.treeListTable;
      if (flag) {
        let findRow = this.selectionRow.find(c => c.id == row.id);
        if (findRow) {
          treeListTable.toggleRowSelection(row, false);
          return;
        }
      }
      treeListTable.clearSelection(); //清空列表的选中
      treeListTable.toggleRowSelection(row, true);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      let treeListTable = this.$refs.treeListTable;
      if (selection.length > 1) {
        treeListTable.clearSelection(); //清空列表的选中
        treeListTable.toggleRowSelection(selection[selection.length - 1], true); //只显示选中最后一个 这时selection还是多选的列表(就是你选中的几个数据)
        selection = Array(selection[selection.length - 1]);
      } else if (selection.length === 1) {
        selection = Array(selection[selection.length - 1]);
      } else {
        selection = [];
      }
      // 方便点击行取消选中
      this.selectionRow = selection;
      // 设置行高亮
      treeListTable.setCurrentRow(selection[0]);

      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$refs.treeListTable.clearSelection();
      this.reset();
      this.open = true;
      this.title = "编码规则树形 / 新增";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      const loading = this.dataLoading()                   
      getTree(id).then(response => {
        if (response.code == 200) {
          this.form = response.data;
          this.open = true;
          this.title = "编码规则树形 / 修改";
          loading.close()                           
        } else {
          this.msgError(response.msg);
        }
      }).catch(()=>{
        loading.close();            
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTree(this.form).then(response => {
              if (response.code == 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addTree(this.form).then(response => {
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
        '是否确认删除编码规则树形编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delTree(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有编码规则树形数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.exportLoading = true;
          return exportTree(queryParams);
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
