<template>
    <!-- 基础信息 - 仓库信息管理 -->
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <orgSearch size="small" label="单位名称" :queryParams="queryParams"  ></orgSearch>
        <el-form-item label="仓库编码" prop="storeCode">
          <el-input
            v-model="queryParams.storeCode"
            placeholder="请输入仓库编码"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="仓库名称" prop="storeName">
          <el-input
            v-model="queryParams.storeName"
            placeholder="请输入仓库名称"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="库房类型" prop="storeClass">
          <el-select v-model="queryParams.storeClass" placeholder="请选择库房类型" clearable size="small">
            <el-option
              v-for="dict in storeClassOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
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
            v-hasPermi="['basic:store:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"

            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['basic:store:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['basic:store:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-circle-check"
            size="mini"
            :disabled="single||stop2"
            @click="handleStart"
            v-hasPermi="['basic:store:start']"
          >启用</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-remove-outline"
            size="mini"
            :disabled="single||start2"
            @click="handleStop"
            v-hasPermi="['basic:store:stop']"
          >停用</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>
    <el-table v-loading="loading" :height="tableHeight"  ref="storeListRef" :data="storeList" @selection-change="handleSelectionChange" stripe  :default-sort="defaultSort" @sort-change="handleSortChange"  highlight-current-row @row-click="handleRowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column show-overflow-tooltip label="仓库分类" align="left" prop="storeType" :formatter="storeTypeFormat" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column show-overflow-tooltip label="仓库编码" align="center" prop="storeCode" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column show-overflow-tooltip label="仓库名称" align="left" prop="storeName" sortable="custom" :sort-orders="['descending', 'ascending']">
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" v-html="scope.row.storeName" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip label="库房类型" width="120" align="center" prop="storeClass" sortable="custom" :formatter="storeClassFormat" :sort-orders="['descending', 'ascending']"/>
      <el-table-column show-overflow-tooltip label="单位" align="left" prop="compName" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column show-overflow-tooltip label="负责人" width="120" align="center" prop="principalCode" :formatter="listUserFormat" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column show-overflow-tooltip label="电话" width="120" align="center" prop="tel" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column show-overflow-tooltip label="是否代销" width="120" align="center" prop="isProxy" :formatter="isProxyFormat" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column show-overflow-tooltip label="是否停用" width="120" align="center" prop="isStop" :formatter="isStopFormat" sortable="custom" :sort-orders="['descending', 'ascending']"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改仓库管理对话框 -->
    <addEdit v-if="open" :open="open" :id="addEditId" :addEditorForm="addEditorForm" @cancel="cancel" @success="successSubmit"></addEdit>
  </div>
</template>

<script>
import {
  listStore,
  getStore,
  delStore,
  addStore,
  updateStore,
  exportStore
} from "@/api/basic/store";
import { listUser } from "@/api/system/user";
import orgSearch from "@/views/components/orgSearch";
import pinyin from "js-pinyin";
import addEdit from "./addEdit";
export default {
  name: "Store",
  components: {
    orgSearch,
    addEdit
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      isStops: [],
      // 非单个禁用
      single: true,
      start2: false,
      stop2: false,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 仓库管理表格数据
      storeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 库房分类字典
      storeTypeOptions: [],
      // 是否停用字典
      isStopOptions: [],
      // 是否代销字典
      isProxyOptions: [],
      // 库房类型字典
      storeClassOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: this.$store.state.user.userInfo.deptId,
        compName: this.$store.state.user.userInfo.deptName,
        storeCode: null,
        storeName: null,
        storeClass: null,
      },
      // 表单参数
      form: {},
      // 新增修改表单参数
      addEditorForm: {},
      // 表单校验
      rules: {
        compCode: [
          { required: true, message: "单位编码不能为空", trigger: "blur" }
        ],
        compName: [
          { required: true, message: "单位不能为空", trigger: "blur" },
          { required: true, message: "单位不能为空", trigger: "change" }
        ],
        storeName: [
          { required: true, message: "仓库名称不能为空", trigger: "blur" }
        ]
      },
      // 新增修改id
      addEditId: "",
      defaultSort: { prop: "createTime", order: "descending" },
      selectionRow: [],
      tableHeight: "auto",
      userOptions: []
    };
  },
  created() {
    this.getList();
    // this.getDicts("basic_store_type").then(response => {
    //   this.storeTypeOptions = response.data;
    // });
    // this.getDicts("hr_is_change").then(response => {
    //   this.isStopOptions = response.data;
    // });
    // this.getDicts("hr_is_change").then(response => {
    //   this.isProxyOptions = response.data;
    // });
    // this.getUserInfo().then(response => {
    //   this.userOptions = response.data;
    // });
  },
  computed: {
    listData() {
      return this.$store.state.app.sidebar.opened;
    }
  },
  watch: {
    "$store.state.dicts": {
      handler(val) {
        let baseDicts = val.baseDicts;
        // 基础字典
        this.isProxyOptions = baseDicts.hrIsChange;
        this.isStopOptions = baseDicts.hrIsChange;
        this.storeTypeOptions = baseDicts.basicStoreType;
        this.storeClassOptions = baseDicts.storeClass;
        // 特殊
        this.userOptions = val.userOptsInfo;
      },
      deep: true,
      immediate: true
    },
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
    /** 拼音码 */
    handleSpell() {
      this.form.spell = pinyin.getCamelChars(this.form.storeName).toUpperCase();
    },
    /** 查询仓库管理列表 */
    getList() {
      this.loading = true;
      listStore(this.queryParams).then(response => {
        if (response.code == 200) {
          this.storeList = response.rows;
          this.total = response.total;
        } else {
          this.msgError(response.msg);
        }
        this.loading = false;
      });
    },
    // 库房分类字典翻译
    storeTypeFormat(row, column) {
      return this.selectDictLabel(this.storeTypeOptions, row.storeType);
    },
    // listUser this.userOptions
    listUserFormat(row, column) {
      return this.selectDictLabel(this.userOptions, row.principalCode);
    },
    // 是否停用字典翻译
    isStopFormat(row, column) {
      return this.selectDictLabel(this.isStopOptions, row.isStop);
    },
    // 是否代销字典翻译
    isProxyFormat(row, column) {
      return this.selectDictLabel(this.isProxyOptions, row.isProxy);
    },
    // 库房类型字典翻译
    storeClassFormat(row, column) {
      return this.selectDictLabel(this.storeClassOptions, row.storeClass);
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
        compCode: null,
        compName: null,
        deptCode: null,
        storeCode: null,
        storeName: null,
        storeClass: null,
        spell: null,
        storeType: null,
        address: null,
        tel: null,
        principalCode: null,
        custodyCode: null,
        accountantCode: null,
        isStop: "1",
        isProxy: "1",
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
    handleRowClick(row, flag = true) {
      let storeListRef = this.$refs.storeListRef;
      if (flag) {
        let findRow = this.selectionRow.find(c => c.id == row.id);
        if (findRow) {
          storeListRef.toggleRowSelection(row, false);
          return;
        }
      }
      storeListRef.clearSelection(); //清空列表的选中
      storeListRef.toggleRowSelection(row, true);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      let storeListRef = this.$refs.storeListRef;
      if (selection.length > 1) {
        storeListRef.clearSelection(); //清空列表的选中
        storeListRef.toggleRowSelection(selection[selection.length - 1], true); //只显示选中最后一个 这时selection还是多选的列表(就是你选中的几个数据)
        selection = Array(selection[selection.length - 1]);
      } else if (selection.length === 1) {
        selection = Array(selection[selection.length - 1]);
      } else {
        selection = [];
      }
      // 方便点击行取消选中
      this.selectionRow = selection;
      // 设置行高亮
      storeListRef.setCurrentRow(selection[0]);
      this.ids = selection.map(item => item.id);
      this.storeCodes = selection.map(item => item.storeCode);
      this.isStops = selection.map(item => item.isStop);
      this.single = selection.length !== 1;
      if (this.single == false) {
        if (this.isStops[0] == 0) {
          this.start2 = true;
          this.stop2 = false;
        } else {
          this.stop2 = true;
          this.start2 = false;
        }
      } else {
        this.stop2 = false;
        this.start2 = false;
      }
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$refs.storeListRef.clearSelection();
      this.open = true;
      this.addEditId = "";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.addEditId = this.ids[0];
      this.open = true;
    },
    /** 提交成功 */
    successSubmit(flag) {
      this.open = flag;
      this.getList();
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      const storeCodes = row.storeCode || this.storeCodes;
      this.$confirm(
        '是否确认删除仓库管理编号为"' + storeCodes + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delStore(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 停用按钮操作 */
    handleStop(row) {
      const ids = row.id || this.ids;
      const storeCodes = row.storeCode || this.storeCodes;
      this.$confirm(
        '是否确认停用仓库管理编号为"' + storeCodes + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          this.form.id = ids[0];
          this.form.isStop = "0";
          updateStore(this.form).then(response => {
            if (response.code == 200) {
              this.msgSuccess("停用成功");
              this.getList();
            } else {
              this.msgError(response.msg);
            }
          });
        })
        .catch(() => {});
    },
    /** 启用按钮操作 */
    handleStart(row) {
      const ids = row.id || this.ids;
      const storeCodes = row.storeCode || this.storeCodes;
      this.$confirm(
        '是否确认启用仓库管理编号为"' + storeCodes + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          this.form.id = ids[0];
          this.form.isStop = "1";
          updateStore(this.form).then(response => {
            if (response.code == 200) {
              this.msgSuccess("启用成功");
              this.getList();
            } else {
              this.msgError(response.msg);
            }
          });
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有仓库管理数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.exportLoading = true;
          return exportStore(queryParams);
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
