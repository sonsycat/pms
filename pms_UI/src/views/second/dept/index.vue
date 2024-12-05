<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :rules="rules" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="年月" prop="nowDate">
        <el-date-picker class="search_w220" clearable
                        v-model="queryParams.nowDate"
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
          icon="el-icon-box"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['second:dept:add']"
        >保存</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          size="mini"
          type="primary"
          icon="el-icon-refresh"
          @click="handleRefresh"
        >刷新</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table border stripe v-loading="loading" :data="deptList" :height="tableHeight" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column sortable label="核算科室编码" align="center" prop="deptCode" />
      <el-table-column sortable label="核算科室名称" align="left" prop="deptName" />
      <el-table-column sortable label="指标集编码" align="center" prop="disCode" >
        <template slot-scope="scope">
          <el-select class="w100" v-model="scope.row.disCode" placeholder="请选择指标集编码">
            <el-option
              v-for="dict in distributionOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
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

  </div>
</template>

<script>
import { listDeptByCompCode, saveDept, delDept, addDept, updateDept, exportDept } from "@/api/second/dept";
import {getYear} from "@/utils/date";
import {getDistribution} from "@/api/system/dict/data";

export default {
  name: "Dept",
  components: {
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
      // 二次分配科室指标表格数据
      deptList: [],
      selectionRow: [],
      //指标集字典
      distributionOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      tableHeight: "auto",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: null,
        deptCode: null,
        disCode: null,
        nowDate: getYear(),
        deptList: [],
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        nowDate: [
          { required: true, message: '请选择年月', trigger: 'change' }
        ]
      }
    };
  },
  created() {
    this.getList();
    getDistribution(this.queryParams).then(response => {
      this.distributionOptions = response.data;
    });
  },
  methods: {
    /** 查询二次分配科室指标列表 */
    getList() {
      this.loading = true;
      listDeptByCompCode(this.queryParams).then(response => {
        this.deptList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
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
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          let saveLoading = this.saveLoading();
          this.queryParams.deptList = this.deptList;
          saveDept(this.queryParams).then(response => {
            this.msgSuccess("保存成功");
            this.queryParams.deptList = [];
            saveLoading.close();
          });
        }
      });
    },
    /** 刷新按钮操作 */
    handleRefresh() {
      this.$confirm('确定刷新当前数据吗，未保存数据即将清空！', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.handleQuery();
      }).catch(() => {});
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
  }
};
</script>
