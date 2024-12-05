<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="40%" custom-class="el-button--primary" append-to-body
             :close-on-click-modal="false" @close="handleDialogClose">
	<div ref="btns">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['bonus:deptSortDetail:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['bonus:deptSortDetail:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['bonus:deptSortDetail:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar @queryTable="getList" style="margin-right: 20px;"></right-toolbar>
    </el-row>
	</div>

    <el-table border stripe v-loading="loading" :data="deptSortDetailList" @selection-change="handleSelectionChange" height="calc(70vh - 100px)"
              ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" :selectable="selectableAllow" width="55" align="center"></el-table-column>
      <el-table-column sortable label="类别编码" align="center" prop="sortCode" />
      <el-table-column sortable label="类别名称" align="center" prop="sortName" />
    </el-table>

    <addEdit v-if="openEdit" :openEdit="openEdit" :id="addEditId" @cancel="cancelEdit" @success="successSubmit"></addEdit>
  </el-dialog>
</template>

<script>
import { listDeptSortDetail, getDeptSortDetail, delDeptSortDetail, addDeptSortDetail, updateDeptSortDetail, exportDeptSortDetail } from "@/api/bonus/deptSortDetail";
import addEdit from "./addEdit";

export default {
  name: "DeptSortDetail",
  components: {
    addEdit
  },
  props: {
    open: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      openDialog: this.open,
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
      // 核算单位类别表格数据
      deptSortDetailList: [],
      selectionRow: [],
      // 弹出层标题
      title: "核算单位类别 / 类别设置",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        sortCode: null,
        sortName: null,
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
      },

      //弹窗编辑
      openEdit: false,
      addEditId: ''
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询核算单位类别列表 */
    getList() {
      this.loading = true;
      listDeptSortDetail(this.queryParams).then(response => {
        this.deptSortDetailList = response;
        this.loading = false;
      });
    },
	successSubmit(flag) {
      this.openEdit = flag;
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
        sortCode: null,
        sortName: null,
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
      if(row.editAllow == '1'){
        return false;
      }
      let refsElTable = this.$refs.multipleTable;
      let findRow = this.selectionRow.find(c => c.rowIndex == row.rowIndex);
      if (findRow && this.selectionRow.length == 1) {
        refsElTable.toggleRowSelection(row, false);
        return;
      }
      refsElTable.clearSelection();
      refsElTable.toggleRowSelection(row);
    },
    rowStyle({row, rowIndex}) {
      Object.defineProperty(row, 'rowIndex', {
        value: rowIndex,
        writable: true,
        enumerable: false
      })
    },
    rowClassName({row, rowIndex}) {
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
      this.title = "添加核算单位类别";
    },
     */
    /** 新增按钮操作 */
    handleAdd() {
      this.openEdit = true; // 弹框点开
      this.addEditId = ""; // addEditId 是新增  去子组件方便区分新增编辑
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      this.openEdit = true;
      this.addEditId = id; // id赋值
      console.log("addEditId---" + addEdit);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDeptSortDetail(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDeptSortDetail(this.form).then(response => {
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
      this.$confirm('是否确认删除核算单位类别编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDeptSortDetail(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有核算单位类别数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportDeptSortDetail(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit('cancel', false)
    },
    // 取消按钮
    cancelEdit() {
      this.openEdit = false
      this.reset()
    },
    // 只能选择 末级节点
    selectableAllow(row){
      if(row.editAllow == '1'){
        return false;
      }
      return true;
    },
  }
};
</script>
<style lang="scss" scoped>
::v-deep .top-right-btn button:first-child{display: none}
::v-deep .el-dialog {
  height: 70%;
  min-width: 500px;
  overflow: hidden;
}
.el-dialog__body {
  padding: 10px !important;
}
</style>
