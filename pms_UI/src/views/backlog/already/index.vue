<template>
  <div class="app-container">
    <div ref="btns" ></div>
    <div ref="search" >
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item>
          <el-button type="primary" icon="el-icon-edit" size="mini" :disabled="single" @click="processExamine">流转历史</el-button>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">刷新</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table  :height="tableHeight" ref="recordListTable" stripe v-loading="loading" :data="recordList" @selection-change="handleSelectionChange" highlight-current-row @row-click="handleRowClick">
      <el-table-column type="selection" width="55" align="center"/>
      <!--<el-table-column label="任务节点ID" align="center" prop="taskId" />
      <el-table-column label="流程定义值" align="center" prop="taskDefKey" />-->
      <el-table-column label="标题" align="left" prop="title"  width="280"  :sort-orders="['descending', 'ascending']"/>
      <el-table-column label="任务名称" align="left" prop="taskName"  :sort-orders="['descending', 'ascending']"/>
      <el-table-column label="流程名称" align="left" prop="procName" />
      <el-table-column label="办理时间" align="center" prop="endTimeStr"  :sort-orders="['descending', 'ascending']"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNo"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <component v-if="open" :is="componentUrl" :id="componentId" :proclnsIds="proclnsIds" :open="open" @cancel="cancel" @success="submitSuccess"></component>

    <!-- 流转历史对话框 -->
    <el-dialog title="流转历史" :visible.sync="processOpen" width="1200px" append-to-body :close-on-click-modal="false" custom-class="el-button--primary">
      <div v-loading="loading" :style="'height:'+ height">
        <iframe :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto" />
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAlready } from "@/api/process/model";

export default {
  name: "backlog",
  data() {
    return {
      tableHeight: "auto",
      src: "",
      height: "500px;",
      componentUrl: null,
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      procInsIds: [],
      // 非单个禁用
      single: true,
      // 流转历史对话框
      processOpen: false,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 合同修改记录表格数据
      recordList: [],
      // 流程编号数组
      proclnsIdss: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 30,
        compCode: null,
        deptCode: null,
        systemId: "PMS",
        userId: this.$store.state.user.userInfo.userId
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      componentId: "",
      selectionRow: []
    };
  },
  created() {
    this.getList();
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  computed: {
    listData() {
      return this.$store.state.app.sidebar.opened;
    }
  },
  watch: {
    listData(val) {
      var that = this;
      setTimeout(function() {
        that.resizeHandler();
      }, 300);
    }
  },
  methods: {
    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + this.$store.state.settings.tableHeight;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    /** 查询合同修改记录列表 */
    getList() {
      this.loading = true;
      listAlready(this.queryParams).then(response => {
        if (response.code == 200) {
          if (response.data.list == null || response.data.list == "") {
          } else {
            this.recordList = response.data.list;
          }
          this.total = response.data.count;
        } else {
          this.msgError(response.msg);
        }
        this.loading = false;
      });
    },
    /** 刷新按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 点击行
    handleRowClick(row) {
      let recordListTable = this.$refs.recordListTable;
      let findRow = this.selectionRow.find(c => c.procInsId == row.procInsId);
      if (findRow) {
        recordListTable.toggleRowSelection(row, false);
        return;
      }
      recordListTable.clearSelection(); //清空列表的选中
      recordListTable.toggleRowSelection(row, true);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      let recordListTable = this.$refs.recordListTable;
      if (selection.length > 1) {
        recordListTable.clearSelection(); //清空列表的选中
        recordListTable.toggleRowSelection(
          selection[selection.length - 1],
          true
        ); //只显示选中最后一个 这时selection还是多选的列表(就是你选中的几个数据)
        selection = Array(selection[selection.length - 1]);
      } else if (selection.length === 1) {
        selection = Array(selection[selection.length - 1]);
      } else {
        selection = [];
      }
      // 方便点击行取消选中
      this.selectionRow = selection;
      // 设置行高亮
      recordListTable.setCurrentRow(selection[0]);

      this.single = selection.length !== 1;
      this.multiple = !selection.length;
      this.proclnsIdss = selection.map(item => item.procInsId);
    },
    // 取消按钮
    cancel(flag) {
      this.open = flag;
    },
    submitSuccess(flag) {
      this.open = flag;
      this.getList();
    },
    /** 流转历史初始化 */
    processExamineForm() {
      this.src =
        `${this.processUrl}/api/wf/processHistoric?procInsId=${this.proclnsIdss[0]}`;
    },
    /** 流转历史按钮操作 */
    processExamine() {
      console.log(this.proclnsIdss[0]);
      if (this.proclnsIdss[0] == null || !this.proclnsIdss[0]) {
        this.msgError("没有流转历史数据！");
      } else {
        this.processExamineForm();
        this.processOpen = true;
      }
    }
  }
};
</script>
