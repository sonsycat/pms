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
      <el-form-item label="科室编码" prop="deptCode">
        <el-input
          v-model="queryParams.deptCode"
          placeholder="请输入科室编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年月" prop="nowDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.nowDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="审核状态" prop="auditState">
        <el-input
          v-model="queryParams.auditState"
          placeholder="请输入审核状态"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流程id" prop="procedureId">
        <el-input
          v-model="queryParams.procedureId"
          placeholder="请输入流程id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="删除标记(0未删除,1已删除)" prop="delFlag">
        <el-input
          v-model="queryParams.delFlag"
          placeholder="请输入删除标记(0未删除,1已删除)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建者" prop="createBy">
        <el-input
          v-model="queryParams.createBy"
          placeholder="请输入创建者"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="修改者" prop="updateBy">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="请输入修改者"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="修改时间" prop="updateTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.updateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择修改时间">
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
          v-hasPermi="['bonus:audit:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['bonus:audit:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['bonus:audit:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-upload2"
          size="mini"
		  :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['bonus:audit:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>
	
    <el-table border stripe v-loading="loading" :data="auditList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="备注" align="center" prop="id" />
      <el-table-column sortable label="单位编码" align="center" prop="compCode" />
      <el-table-column sortable label="账套编码" align="center" prop="accountCode" />
      <el-table-column sortable label="科室编码" align="center" prop="deptCode" />
      <el-table-column sortable label="年月" align="center" prop="nowDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.nowDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column sortable label="审核状态" align="center" prop="auditState" />
      <el-table-column sortable label="流程id" align="center" prop="procedureId" />
      <el-table-column sortable label="删除标记(0未删除,1已删除)" align="center" prop="delFlag" />
      <el-table-column sortable label="创建者" align="center" prop="createBy" />
      <el-table-column sortable label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column sortable label="修改者" align="center" prop="updateBy" />
      <el-table-column sortable label="修改时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column sortable label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['bonus:audit:edit']" title="修改"
          ></el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['bonus:audit:remove']" title="删除"
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
import { listAudit, getAudit, delAudit, addAudit, updateAudit, exportAudit } from "@/api/bonus/audit";
import addEdit from "./addEdit";

export default {
  name: "Audit",
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
      // 奖金人员审核表格数据
      auditList: [],
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
        deptCode: null,
        nowDate: null,
        auditState: null,
        procedureId: null,
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
        auditState: [
          { required: true, message: "审核状态不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询奖金人员审核列表 */
    getList() {
      this.loading = true;
      listAudit(this.queryParams).then(response => {
        this.auditList = response.rows;
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
        deptCode: null,
        nowDate: null,
        auditState: null,
        procedureId: null,
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
      this.title = "添加奖金人员审核";
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
            updateAudit(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAudit(this.form).then(response => {
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
      this.$confirm('是否确认删除奖金人员审核编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delAudit(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有奖金人员审核数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportAudit(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
