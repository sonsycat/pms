<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="60%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <div ref="btns">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['quality:index:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['quality:index:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['quality:index:remove']"
            >删除</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </div>
    
      <el-table style="overflow:hidden" border stripe min-height="100px" max-height="600px" v-loading="loading" :data="detailList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column show-overflow-tooltip sortable label="考评内容" align="left" prop="evaCont">
          <template slot-scope="scope">
            <el-link type="primary" :underline="false" v-html="scope.row.evaCont" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
          </template>
        </el-table-column>
        <el-table-column show-overflow-tooltip sortable label="考评标准" align="left" prop="evaCriteria" />
        <el-table-column show-overflow-tooltip sortable label="考评办法" align="left" prop="evaMethod" />
      </el-table>

    <addEdit v-if="addOpen" :open="addOpen" :indexCodeDatas="datas" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>
    
    <div slot="footer" class="dialog-footer">
        <el-button @click="handleDialogClose">关 闭</el-button>
      </div>
  </el-dialog>
</template>

<script>
import { listDetail, getDetail, delDetail, addDetail, updateDetail, exportDetail } from "@/api/quality/detail";
import addEdit from "./addEdit";

export default {
  name: "Detail",
  props: {
    open: {
      type: Boolean,
      default: false
    },
    id: {
      type: [String, Number],
      default: ""
    },
    datas:{
      type:Array,
      default:[]
    }
  },
  watch: {
    id: {
      handler(val) {
        this.reset();
        if (val) {
          this.id = val;
          this.title = "质量考评指标设置 / 编辑指标内容";
          // this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "质量考评指标设置 / 编辑指标内容";
        }
      },
      deep: true,
      immediate: true
    },
    open: {
      handler(val) {
        this.openDialog = val;
      },
      deep: true
    },
  },
  components: {
    addEdit
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      openDialog: this.open,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 质量考评指标明细表格数据
      detailList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      addOpen: false,
      // 是否KPI字典
      isKpiOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode:this.$store.state.user.userInfo.deptId,
        indexCode: this.datas[0].indexCode,
        evaCont: null,
        evaCriteria: null,
        evaMethod: null,
        isKpi: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("hr_is_change").then(response => {
      this.isKpiOptions = response.data;
    });
  },
  methods: {
    handleClickTableName(data) {
        // 选中当前行
        this.rowClick(data, false);
        this.handleUpdate(data);
    },
    /** 查询质量考评指标明细列表 */
    getList() {
      this.loading = true;
      listDetail(this.queryParams).then(response => {
        if(response.code===200){
          this.detailList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      });
    },
    // 是否KPI字典翻译
    isKpiFormat(row, column) {
      return this.selectDictLabel(this.isKpiOptions, row.isKpi);
    },
    successSubmit(flag) {
      this.addOpen = flag;
      this.getList();
    },
    // 取消按钮
    cancel() {
      this.addOpen = false;
      this.reset();
      this.getList();
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit("cancel", false);
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        indexCode: null,
        evaCont: null,
        evaCriteria: null,
        evaMethod: null,
        isKpi: "0",
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
    /** 新增按钮操作 */
    handleAdd() {
      this.addOpen = true; // 弹框点开
      this.addEditId = ""; // addEditId 是新增  去子组件方便区分新增编辑
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      this.addOpen = true;
      this.addEditId = id; // id赋值
      console.log("addEditId---" + addEdit);
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除质量考评指标明细编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDetail(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
  }
};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    min-height: 20%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
      .app-container{
        .el-form-item{
          width: 100%;
        }
        .el-form-item__content{
          margin-left: 0 !important;
          width:70%;
        }
        .el-select{
          width: 100%;
        }

      }
    }
  }
</style>

