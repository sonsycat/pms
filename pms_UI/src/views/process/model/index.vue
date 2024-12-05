<template>
    <!-- 流程管理 - 流程模板配置 -->
  <div clas="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="流程名称" prop="procinsName">
          <el-input
            v-model="queryParams.procinsName"
            placeholder="请输入流程名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="表名" prop="tabelName">
          <el-input
            v-model="queryParams.tabelName"
            placeholder="请输入表名"
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
          v-hasPermi="['process:model:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"

          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['process:model:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"

          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['process:model:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    </div>
    <el-table :height="tableHeight" ref="modelListTable" v-loading="loading" :data="modelList" @selection-change="handleSelectionChange" :default-sort="defaultSort" @sort-change="handleSortChange"  highlight-current-row @row-click="handleRowClick" >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column label="流程key" align="left" prop="procinsId" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column label="流程名称" align="left" prop="procinsName" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column label="表名" align="left" prop="tabelName" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column label="表单url" align="left" prop="formUrl" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column label="类型" align="left" prop="type" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['process:model:edit']" title="修改"
          ></el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['process:model:remove']" title="删除"
          ></el-button>
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

    <!-- 添加或修改流程模块对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false" custom-class="el-button--primary">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="流程key" prop="procinsId">
          <el-select v-model="form.procinsId" placeholder="请选择流程key" class="w100">
            <el-option
              v-for="dict in procinsIdOptions"
              :key="dict.key"
              :label="dict.name"
              :value="dict.key"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="流程名称" prop="procinsName">
          <el-input v-model="form.procinsName" placeholder="请输入流程名称" />
        </el-form-item>
        <el-form-item label="表名" prop="tabelName">
          <el-input v-model="form.tabelName" placeholder="请输入表名" />
        </el-form-item>
        <el-form-item label="表单url" prop="formUrl">
          <el-input v-model="form.formUrl" placeholder="请输入表单url" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-input v-model="form.type" placeholder="请输入类型" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" maxlength="100" show-word-limit rows="3" resize="none"/>
        </el-form-item>
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
  listModel,
  listsModel,
  getModel,
  delModel,
  addModel,
  updateModel,
  exportModel
} from "@/api/process/model";

export default {
  name: "Model",
  components: {},
  data() {
    return {
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
      // 流程模块表格数据
      modelList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        procinsName: null,
        tabelName: null
      },
      // 查询参数
      queryParamsS: {
        procinsId: null
      },
      // 表单参数
      form: {},
      //流程key字典
      procinsIdOptions: [],
      // 表单校验
      rules: {
        procinsId: [
          { required: true, message: "流程key不能为空", trigger: "change" }
        ],
        procinsName: [
          { required: true, message: "流程名称不能为空", trigger: "blur" }
        ],
        tabelName: [
          { required: true, message: "表名不能为空", trigger: "blur" }
        ],
        formUrl: [
          { required: true, message: "表单url不能为空", trigger: "blur" }
        ],
        type: [{ required: true, message: "type不能为空", trigger: "blur" }]
      },
      defaultSort: { prop: "createTime", order: "descending" },
      selectionRow: []
    };
  },
  created() {
    this.getList();
    this.procinsIdList();
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
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    /** 查询流程模块列表 */
    getList() {
      this.loading = true;
      listModel(this.queryParams).then(response => {
        console.log(response);
        this.modelList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    procinsIdList() {
      listsModel(this.queryParamsS).then(response => {
        console.log(response.data);
        this.procinsIdOptions = response.data;
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
        procinsId: null,
        procinsName: null,
        tabelName: null,
        formUrl: null,
        type: null,
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
    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
    // 点击行
    handleRowClick(row, flag = true) {
      let modelListTable = this.$refs.modelListTable;
      if (flag) {
        let findRow = this.selectionRow.find(c => c.id == row.id);
        if (findRow) {
          modelListTable.toggleRowSelection(row, false);
          return;
        }
      }
      modelListTable.clearSelection(); //清空列表的选中
      modelListTable.toggleRowSelection(row, true);
    },
    // 多选框选中数据
    handleSelectionChange(selection, row) {
      let modelListTable = this.$refs.modelListTable;
      if (selection.length > 1) {
        modelListTable.clearSelection(); //清空列表的选中
        modelListTable.toggleRowSelection(
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
      modelListTable.setCurrentRow(selection[0]);
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$refs.modelListTable.clearSelection();
      this.reset();
      this.open = true;
      this.title = "流程模块 / 新增";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      const loading = this.dataLoading()                   
      getModel(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "流程模块 / 修改";
        loading.close()                        
      }).catch(()=>{
        loading.close();            
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateModel(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addModel(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(
        '是否确认删除流程模块编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delModel(ids);
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
      this.$confirm("是否确认导出所有流程模块数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.exportLoading = true;
          return exportModel(queryParams);
        })
        .then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    }
  }
};
</script>
