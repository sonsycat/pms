<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
      <el-form-item label="年月" prop="nowDate">
        <el-date-picker class="search_w220" clearable
                        v-model="queryParams.startDate"
                        type="month"
                        value-format="yyyy-MM"
                        placeholder="选择年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="至年月" prop="nowDate">
        <el-date-picker class="search_w220" clearable
                        v-model="queryParams.endDate"
                        type="month"
                        value-format="yyyy-MM"
                        placeholder="选择年月">
        </el-date-picker>
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
          v-hasPermi="['apport:apport:add']"
        >分摊计算</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['apport:apport:edit']"
        >查看明细</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="single"
          @click="handleDelete"
          v-hasPermi="['apport:apport:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table :height="tableHeight" border stripe v-loading="loading" :data="apportList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="分摊编码" align="center" prop="apportCode">
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" v-html="scope.row.apportCode" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column sortable label="年月" align="center" prop="nowDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.nowDate, '{y}-{m}') }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column sortable label="分摊级别" align="center" prop="apportType" :formatter="apportTypeFormat" />-->
      <el-table-column sortable label="分摊前金额" align="right" prop="forward" />
      <el-table-column sortable label="分摊后金额" align="right" prop="back" />
      <el-table-column sortable label="分摊时间" align="center" prop="apportTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.apportTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column sortable label="操作人" align="left" prop="createBy" :formatter="listUserFormat" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

	  <addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>
    <detailEdit v-if="open1" :open="open1" :id="addEditId" @cancel="cancel1" @success="successSubmit1"></detailEdit>
    <delEdit v-if="openDel" :open="openDel" :id="addEditId" :delData="delData" @cancel="cancelDel" @success="successSubmitDel"></delEdit>

  </div>
</template>

<script>
import { listApport, getApport, delApport, addApport, updateApport, exportApport } from "@/api/apport/apport";
import addEdit from "./addEdit";
import detailEdit from "./detailEdit";
import delEdit from "./delEdit";

export default {
  name: "Apport",
  components: {
    addEdit,
    detailEdit,
    delEdit
  },
  watch:{
    "$store.state.dicts": {
      handler(val) {
        // 基础字典
        this.nameOptions = val.userOptsInfo;
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
      // 分摊计算表格数据
      apportList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      open1: false,
      openDel:false,
      delData:{},
      // 分摊级别(保存最后的级别)字典
      apportTypeOptions: [],
      nameOptions:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: null,
        apportCode: null,
        nowDate: null,
        startDate: null,
        endDate: null,
        apportType: null,
        forward: null,
        back: null,
        apportTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  computed: {
    listData() {
      return this.$store.state.app.sidebar.opened;
    }
  },
  created() {
    if(this.$route.query.id){
      this.handleUpdate(this.$route.query)
    }
    this.getList();
    this.getDicts("cost_apport_type").then(response => {
      this.apportTypeOptions = response.data;
    });
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  methods: {
    handleClickTableName(data) {
        // 选中当前行
        this.rowClick(data, false);
        this.handleUpdate(data);
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    listUserFormat(row, column) {
        return this.selectDictLabel(this.nameOptions, row.createBy);
    },
    /** 查询分摊计算列表 */
    getList() {
      this.loading = true;
      listApport(this.queryParams).then(response => {
        this.apportList = response.rows;
        this.total = response.total;
        this.loading = false;
        for (let i = 0; i < this.apportList.length; i++) {
            for (var j in this.apportList[i]) {
              if ((typeof this.apportList[i][j]) == "number" && this.apportList[i][j] != 0 && j!="id") {
                this.apportList[i][j] = this.moneyFormat(this.apportList[i][j])
              }
            }
          }
      });
    },
    // 分摊级别(保存最后的级别)字典翻译
    apportTypeFormat(row, column) {
      return this.selectDictLabel(this.apportTypeOptions, row.apportType);
    },
	  successSubmit(flag) {
      this.open = flag;
      this.getList();
    },
    successSubmit1(flag) {
      this.open1 = flag;
      this.getList();
    },
    successSubmitDel(flag){
      this.openDel=flag
      this.getList()
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    cancel1() {
      this.open1 = false;
      this.reset();
    },
    cancelDel(){
      this.openDel=false
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: this.$store.state.user.accountInfo.accountCode,
        apportCode: null,
        nowDate: null,
        apportType: null,
        forward: null,
        back: null,
        apportTime: null,
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
      this.queryParams.apportType =1;
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.startDate=null,
      this.queryParams.endDate=null,
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.apportCodes = selection.map(item => item.apportCode)
      this.single = selection.length!==1
      this.multiple = !selection.length
      this.selectionRow=selection
      this.delData=selection[0]
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
      this.open = true; // 弹框点开
      this.addEditId = ""; // addEditId 是新增  去子组件方便区分新增编辑
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      this.open1 = true;
      this.addEditId = id; // id赋值
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateApport(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addApport(this.form).then(response => {
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
      const code = row.apportCode || this.apportCodes;
      this.$confirm('是否确认删除分摊计算编码为"' + code + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delApport(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(() => {});



    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有分摊计算数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportApport(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
