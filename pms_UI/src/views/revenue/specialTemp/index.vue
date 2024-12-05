<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="单位编码" prop="compCode">
        <el-input
          v-model="queryParams.compCode"
          placeholder="请输入单位编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账套编码" prop="accountCode">
        <el-input
          v-model="queryParams.accountCode"
          placeholder="请输入账套编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="特殊项目编码" prop="specialCode">
        <el-input
          v-model="queryParams.specialCode"
          placeholder="请输入特殊项目编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目编码" prop="revenueCode">
        <el-input
          v-model="queryParams.revenueCode"
          placeholder="请输入项目编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目名称" prop="revenueName">
        <el-input
          v-model="queryParams.revenueName"
          placeholder="请输入项目名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开单科室" prop="billDept">
        <el-input
          v-model="queryParams.billDept"
          placeholder="请输入开单科室"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="执行科室" prop="execDept">
        <el-input
          v-model="queryParams.execDept"
          placeholder="请输入执行科室"
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
          v-hasPermi="['revenue:specialTemp:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['revenue:specialTemp:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['revenue:specialTemp:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-upload2"
          size="mini"
		  :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['revenue:specialTemp:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>
	
    <el-table border stripe v-loading="loading" :data="specialTempList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="执行科室" align="center" prop="id" />
      <el-table-column sortable label="单位编码" align="center" prop="compCode" />
      <el-table-column sortable label="账套编码" align="center" prop="accountCode" />
      <el-table-column sortable label="特殊项目编码" align="center" prop="specialCode" />
      <el-table-column sortable label="项目编码" align="center" prop="revenueCode" />
      <el-table-column sortable label="项目名称" align="center" prop="revenueName" />
      <el-table-column sortable label="开单科室" align="center" prop="billDept" />
      <el-table-column sortable label="执行科室" align="center" prop="execDept" />
      <el-table-column sortable label="住院开单(%)" align="center" prop="inpBill" />
      <el-table-column sortable label="住院执行(%)" align="center" prop="inpExec" />
      <el-table-column sortable label="住院护理(%)" align="center" prop="inpCare" />
      <el-table-column sortable label="门诊开单(%)" align="center" prop="outpBill" />
      <el-table-column sortable label="门诊执行(%)" align="center" prop="outpExec" />
      <el-table-column sortable label="门诊护理(%)" align="center" prop="outpCare" />
      <el-table-column sortable label="合作医疗(%)" align="center" prop="coopMed" />
      <el-table-column sortable label="核算类型(1参与核算，2不参与核算)" align="center" prop="accountType" />
      <el-table-column sortable label="折算比(%)" align="center" prop="convRatio" />
      <el-table-column sortable label="固定折算(%)" align="center" prop="fixConv" />
      <el-table-column sortable label="成本对照(成本项目)" align="center" prop="projectCode" />
      <el-table-column sortable label="收入类别(HIS)" align="center" prop="hisRevenumTypede" />
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['revenue:specialTemp:edit']" title="修改"
          ></el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['revenue:specialTemp:remove']" title="删除"
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

	<addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>
	
  </div>
</template>

<script>
import { listSpecialTemp, getSpecialTemp, delSpecialTemp, addSpecialTemp, updateSpecialTemp, exportSpecialTemp } from "@/api/revenue/specialTemp";
import addEdit from "./addEdit";

export default {
  name: "SpecialTemp",
  components: {
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
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 特殊收入项目方案临时表格数据
      specialTempList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode: null,
        accountCode: null,
        specialCode: null,
        revenueCode: null,
        revenueName: null,
        billDept: null,
        execDept: null,
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
  },
  methods: {
    /** 查询特殊收入项目方案临时列表 */
    getList() {
      this.loading = true;
      listSpecialTemp(this.queryParams).then(response => {
        this.specialTempList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        compCode: null,
        accountCode: null,
        specialCode: null,
        revenueCode: null,
        revenueName: null,
        billDept: null,
        execDept: null,
        inpBill: null,
        inpExec: null,
        inpCare: null,
        outpBill: null,
        outpExec: null,
        outpCare: null,
        coopMed: null,
        accountType: null,
        convRatio: null,
        fixConv: null,
        projectCode: null,
        hisRevenumTypede: null,
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
      this.title = "添加特殊收入项目方案临时";
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
            updateSpecialTemp(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSpecialTemp(this.form).then(response => {
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
      this.$confirm('是否确认删除特殊收入项目方案临时编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delSpecialTemp(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有特殊收入项目方案临时数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportSpecialTemp(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
