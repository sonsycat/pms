<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="年月" prop="adjustMonth">
        <el-date-picker clearable size="small"
                        v-model="queryParams.adjustMonth"
                        type="month"
                        value-format="yyyy-MM"
                        placeholder="选择年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="科室" prop="deptCode">
        <el-select class="search_w220" v-model="queryParams.deptCode" filterable clearable placeholder="请选择科室" >
          <el-option
            v-for="dict in deptList"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="调整类型" prop="adjustType">
        <el-select v-model="queryParams.adjustType" placeholder="请选择调整类型" clearable size="small">
          <el-option
            v-for="dict in adjustTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="调整金额" prop="adjustAmount">
        <el-input
          v-model="queryParams.adjustAmountMin"
          placeholder="请输入"
          clearable
          :style="{width:'120px'}"
          @keyup.enter.native="handleQuery"
        />
        至
        <el-input
          v-model="queryParams.adjustAmountMax"
          placeholder="请输入"
          clearable
          :style="{width:'120px'}"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="调整方向" prop="adjustDirection">
        <el-select v-model="queryParams.adjustDirection" placeholder="请选择调整方向" clearable size="small">
          <el-option
            v-for="dict in adjustDirectionOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="调整人" prop="adjustName">
        <el-input v-model="queryParams.adjustName" placeholder="请输入调整人"  maxlength="100" show-word-limit class="search_w220" />
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
          v-hasPermi="['subsidy:benefit:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['subsidy:benefit:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['subsidy:benefit:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table border :height="tableHeight" stripe v-loading="loading" :data="benefitList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column show-overflow-tooltip sortable label="科室" align="left" prop="deptCode" :formatter="deptCodeFormat" />
      <el-table-column show-overflow-tooltip sortable label="时间" align="center" prop="adjustTime" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.adjustTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip sortable label="调整类型" align="center" prop="adjustType" :formatter="adjustTypeFormat" />
      <el-table-column show-overflow-tooltip sortable label="调整金额" align="right" prop="adjustAmount" >
        <template slot-scope="scope">
          <span v-text="moneyFormat(scope.row.adjustAmount)"></span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip sortable label="调整方向" align="center" prop="adjustDirection" :formatter="adjustDirectionFormat" />
      <el-table-column show-overflow-tooltip sortable label="调整人" align="center" prop="adjustName"  />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

	<addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>

  </div>
</template>

<script>
import { listBenefit, getBenefit, delBenefit, addBenefit, updateBenefit, exportBenefit } from "@/api/subsidy/benefit";
import addEdit from "./addEdit";

export default {
  name: "Benefit",
  components: {
addEdit
  },
  watch: {
    "$store.state.dicts": {
      handler(val) {
        // 基础字典
        let baseDicts = val.baseDicts;
        this.deptList = val.deptInfo;
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
  mounted(){
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
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
      // 科室效益调整表格数据
      benefitList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 调整类型(1成本2收入)字典
      adjustTypeOptions: [],
      // 调整方向(1地方2军队)字典
      adjustDirectionOptions: [],
      //科室字典
      deptList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: null,
        deptCode: null,
        adjustTime: null,
        adjustType: null,
        adjustAmount: null,
        adjustAmountMin: null,
        adjustAmountMax: null,
        adjustReason: null,
        adjustDirection: null,
        adjustMonth: null,
        adjustName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("ADJUST_TYPE").then(response => {
      this.adjustTypeOptions = response.data;
    });
    this.getDicts("ADJUST_DIRECTION").then(response => {
      this.adjustDirectionOptions = response.data;
    });
  },
  methods: {
    // 科室翻译
    deptCodeFormat(row, column) {
      return this.selectDictLabel(this.deptList, row.deptCode);
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    /** 查询科室效益调整列表 */
    getList() {
      this.loading = true;
      listBenefit(this.queryParams).then(response => {
        this.benefitList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 调整类型(1成本2收入)字典翻译
    adjustTypeFormat(row, column) {
      return this.selectDictLabel(this.adjustTypeOptions, row.adjustType);
    },
    // 调整方向(1地方2军队)字典翻译
    adjustDirectionFormat(row, column) {
      return this.selectDictLabel(this.adjustDirectionOptions, row.adjustDirection);
    },
	successSubmit(flag) {
      this.open = flag;
      this.getList();
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
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: null,
        deptCode: null,
        adjustTime: null,
        adjustType: null,
        adjustAmount: null,
        adjustReason: null,
        adjustDirection: null,
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
      this.queryParams.adjustAmountMax=undefined;
      this.queryParams.adjustAmountMin=undefined;
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
    /** 新增按钮操作
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加科室效益调整";
    },
     */
    /** 新增按钮操作 */
    handleAdd() {
      this.open = true; // 弹框点开
      this.addEditId = ""; // addEditId 是新增  去子组件方便区分新增编辑
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      this.open = true;
      this.addEditId = id; // id赋值
      console.log("addEditId---" + addEdit);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBenefit(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBenefit(this.form).then(response => {
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
      this.$confirm('是否确认删除科室效益调整编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delBenefit(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
  }
};
</script>
