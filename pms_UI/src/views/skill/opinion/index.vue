<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
    <div class="app-container">
      <el-table border stripe v-loading="loading" :data="opinionList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
        <el-table-column show-overflow-tooltip sortable label="审批状态" align="center" prop="auditState" :formatter="auditStateFormat" />
        <el-table-column show-overflow-tooltip sortable label="意见" align="left" prop="opinion" />
        <el-table-column show-overflow-tooltip sortable label="审批时间" align="center" prop="approvelTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.approvelTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column show-overflow-tooltip sortable label="审批人姓名" align="center" prop="approvelName" />
      </el-table>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />

    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listOpinion, getOpinion, delOpinion, addOpinion, updateOpinion, exportOpinion } from "@/api/skill/opinion";

export default {
  name: "Opinion",
  components: {
  },
  props: {
    open: {
      type: Boolean,
      default: false
    },
    technoCode: {
      type: [String, String],
      default: ""
    },
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
      // 总条数
      total: 0,
      // 审批意见表格数据
      opinionList: [],
      selectionRow: [],
      // 弹出层标题
      title: "查看审批详情",
      // 类别字典
      technoTypeOptions: [],
      // 审批状态字典
      auditStateOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: null,
        technoCode: null,
        technoType: null,
        auditState: null,
        opinion: null,
        approvelTime: null,
        approvelName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  watch: {
    open: {
      handler(val) {
        this.openDialog = val;
      },
      deep: true
    },
    technoCode: {
      handler(val) {
        this.reset();
        if (val) {
          this.queryParams.technoCode = val;
          console.log("this.queryParams",this.queryParams)
          this.getList();
        }
      },
      deep: true,
      immediate: true
    },
  },
  created() {
    this.getList();
    this.getDicts("TECHNO_CODE").then(response => {
      this.technoTypeOptions = response.data;
    });
    this.getDicts("asset_aduit_state").then(response => {
      this.auditStateOptions = response.data;
    });
  },
  methods: {
    /** 查询审批意见列表 */
    getList() {
      this.loading = true;
      listOpinion(this.queryParams).then(response => {
        this.opinionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 类别字典翻译
    technoTypeFormat(row, column) {
      return this.selectDictLabel(this.technoTypeOptions, row.technoType);
    },
    // 审批状态字典翻译
    auditStateFormat(row, column) {
      return this.selectDictLabel(this.auditStateOptions, row.auditState);
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: null,
        technoCode: null,
        technoType: null,
        auditState: null,
        opinion: null,
        approvelTime: null,
        approvelName: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit("cancel", false);
    },
    // 取消
    cancel() {
      this.$emit("cancel", false);
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
  }
};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    height: 60%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>

