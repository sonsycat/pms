<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="院，科，人编码" prop="unitCode">
        <el-input
          v-model="queryParams.unitCode"
          placeholder="请输入院，科，人编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="固定与指标代码相同" prop="guideCode">
        <el-input
          v-model="queryParams.guideCode"
          placeholder="请输入固定与指标代码相同"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="指标值" prop="guideValue">
        <el-input
          v-model="queryParams.guideValue"
          placeholder="请输入指标值"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="指标值计算结果" prop="guideValueResult">
        <el-input
          v-model="queryParams.guideValueResult"
          placeholder="请输入指标值计算结果"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Y，K，R" prop="guideType">
        <el-select v-model="queryParams.guideType" placeholder="请选择Y，K，R" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="数据作用日期" prop="actDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.actDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择数据作用日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="数据作用开始日期" prop="actDateEnd">
        <el-date-picker clearable size="small"
          v-model="queryParams.actDateEnd"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择数据作用开始日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="数据作用结束日期" prop="actDateBegin">
        <el-date-picker clearable size="small"
          v-model="queryParams.actDateBegin"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择数据作用结束日期">
        </el-date-picker>
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
          v-hasPermi="['bonus:temp:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['bonus:temp:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['bonus:temp:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-upload2"
          size="mini"
		  :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['bonus:temp:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>
	
    <el-table border stripe v-loading="loading" :data="tempList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="自增主键" align="center" prop="id" />
      <el-table-column sortable label="院，科，人编码" align="center" prop="unitCode" />
      <el-table-column sortable label="固定与指标代码相同" align="center" prop="guideCode" />
      <el-table-column sortable label="指标值" align="center" prop="guideValue" />
      <el-table-column sortable label="指标值计算结果" align="center" prop="guideValueResult" />
      <el-table-column sortable label="Y，K，R" align="center" prop="guideType" />
      <el-table-column sortable label="数据作用日期" align="center" prop="actDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.actDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column sortable label="数据作用开始日期" align="center" prop="actDateEnd" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.actDateEnd, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column sortable label="数据作用结束日期" align="center" prop="actDateBegin" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.actDateBegin, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
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
            v-hasPermi="['bonus:temp:edit']" title="修改"
          ></el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['bonus:temp:remove']" title="删除"
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
import { listTemp, getTemp, delTemp, addTemp, updateTemp, exportTemp } from "@/api/bonus/temp";
import addEdit from "./addEdit";

export default {
  name: "Temp",
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
      // 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)表格数据
      tempList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        unitCode: null,
        guideCode: null,
        guideValue: null,
        guideValueResult: null,
        guideType: null,
        actDate: null,
        actDateEnd: null,
        actDateBegin: null,
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
        unitCode: [
          { required: true, message: "院，科，人编码不能为空", trigger: "blur" }
        ],
        guideCode: [
          { required: true, message: "固定与指标代码相同不能为空", trigger: "blur" }
        ],
        guideValue: [
          { required: true, message: "指标值不能为空", trigger: "blur" }
        ],
        guideType: [
          { required: true, message: "Y，K，R不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)列表 */
    getList() {
      this.loading = true;
      listTemp(this.queryParams).then(response => {
        this.tempList = response.rows;
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
        unitCode: null,
        guideCode: null,
        guideValue: null,
        guideValueResult: null,
        guideType: null,
        actDate: null,
        actDateEnd: null,
        actDateBegin: null,
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
      this.title = "添加指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)";
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
            updateTemp(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTemp(this.form).then(response => {
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
      this.$confirm('是否确认删除指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTemp(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportTemp(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
