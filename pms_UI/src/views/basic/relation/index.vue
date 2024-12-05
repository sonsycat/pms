<template>
<!-- 对应关系设置 - 仓库关系分类 -->
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <orgSearch size="small" label="单位" :queryParams="queryParams"  ></orgSearch>
        <el-form-item label="分类编码" prop="relaCode">
          <el-input
            v-model="queryParams.relaCode"
            placeholder="请输入分类编码"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="分类名称" prop="relaName">
          <el-input
            v-model="queryParams.relaName"
            placeholder="请输入分类名称"
            clearable
            size="small"
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
          v-hasPermi="['basic:relation:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['basic:relation:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['basic:relation:remove']"
        >删除</el-button>
      </el-col>
    <!--  <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-upload2"
          size="mini"
		  :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['basic:relation:export']"
        >导出</el-button>
      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
  </div>
    <el-table v-loading="loading" ref="relationListRef" :height="tableHeight" :data="relationList" @selection-change="handleSelectionChange" :default-sort="defaultSort" @sort-change="handleSortChange"  highlight-current-row @row-click="handleRowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="分类编码" align="center" prop="relaCode" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column label="分类名称" align="left" prop="relaName" sortable="custom" :sort-orders="['descending', 'ascending']">
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" v-html="scope.row.relaName" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column label="单位" align="left" prop="compName" show-overflow-tooltip sortable="custom" :sort-orders="['descending', 'ascending']"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <addEdit v-if="open" :open="open" :id="addEditId"  @cancel="cancel" @success="successSubmit"></addEdit>
  </div>
</template>

<script>
import {
  listRelation,
  delRelation,
  exportRelation
} from "@/api/basic/relation";
import orgSearch from "@/views/components/orgSearch";
import addEdit from "@/views/basic/relation/indexAdd";

export default {
  name: "Relation",
  components: {
    orgSearch,
    addEdit // 新增修改
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
      // 关系分类表格数据
      relationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      addEditId: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        //compCode: null,
        compCode: this.$store.state.user.userInfo.deptId,
        compName: this.$store.state.user.userInfo.deptName,
        deptCode: null,
        relaCode: null,
        relaName: null,
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
      rules: {
        compCode: [
          { required: true, message: "单位不能为空", trigger: "blur" }
        ],
        compName: [
          { required: true, message: "单位不能为空", trigger: "blur" },
          { required: true, message: "单位不能为空", trigger: "change" }
        ],
        relaName: [
          { required: true, message: "分类关系名称不能为空", trigger: "blur" }
        ]
      },
      defaultSort: { prop: "createTime", order: "descending" },
      selectionRow: [],
      tableHeight: "auto"
    };
  },
  created() {
    this.getList();
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
  mounted() {
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
      let h = a + b + this.$store.state.settings.tableHeight;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    /** 查询关系分类列表 */
    getList() {
      this.loading = true;
      listRelation(this.queryParams).then(response => {
        if (response.code == 200) {
          this.relationList = response.rows;
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
      this.getList();
    },
    // 表单重置

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
      let relationListRef = this.$refs.relationListRef;
      if (flag) {
        let findRow = this.selectionRow.find(c => c.id == row.id);
        if (findRow) {
          relationListRef.toggleRowSelection(row, false);
          return;
        }
      }
      relationListRef.clearSelection(); //清空列表的选中
      relationListRef.toggleRowSelection(row, true);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      let relationListRef = this.$refs.relationListRef;
      if (selection.length > 1) {
        relationListRef.clearSelection(); //清空列表的选中
        relationListRef.toggleRowSelection(
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
      relationListRef.setCurrentRow(selection[0]);
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },

    successSubmit(flag) {
      this.open = flag;
      this.getList();
    },

    // 点击修改新增
    /** 新增按钮操作 */
    handleAdd() {
      this.$refs.relationListRef.clearSelection();
      this.open = true; // 弹框点开
      this.addEditId = ""; // addEditId 是新增  去子组件方便区分新增编辑
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.open = true;
      this.addEditId = this.ids[0]; // id赋值
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(
        '是否确认删除关系分类编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delRelation(ids);
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
      this.$confirm("是否确认导出所有关系分类数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.exportLoading = true;
          return exportRelation(queryParams);
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
