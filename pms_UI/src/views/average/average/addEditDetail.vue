<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="60%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
  <div class="app-container">
<!--  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设置年月" prop="setDate">
        <el-date-picker clearable size="small" v-model="queryParams.setDate" type="month" value-format="yyyy-MM" placeholder="选择设置年月"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
	</div>-->
	<div ref="btns">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-refresh" @click="handleRefresh">刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" icon="el-icon-box" size="mini" @click="handleSave">保存</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button size="mini" type="primary" icon="el-icon-circle-close" @click="cancel">取 消</el-button>
      </el-col>

<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    </el-row>
	</div>

    <el-table border stripe v-loading="loading" :data="averageDetailList" ref="multipleTable">
<!--      <el-table-column type="selection" width="55" align="center" />-->
      <el-table-column sortable label="id" align="center" prop="id" v-if="false"/>
      <el-table-column sortable label="科室名称" align="center" prop="depName" />
      <el-table-column sortable label="人员姓名" align="center" prop="docName" />
      <el-table-column sortable label="是否发奖金" align="center" prop="isReward">
        <template slot-scope="scope">
          <el-checkbox true-label="1" false-label="0" v-model="scope.row.isReward"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column sortable label="工作日数" align="center" prop="workDays" >
      <template slot-scope="scope">
        <el-input-number :precision="2" controls-position="right" :min="0" :max="100" v-model="scope.row.workDays" class="w100"></el-input-number>
      </template>
      </el-table-column>
      <el-table-column sortable label="岗位系数" align="center" prop="postRatio" >
        <template slot-scope="scope">
          <el-input-number :precision="2" controls-position="right" :min="0" :max="100" v-model="scope.row.postRatio" class="w100"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column sortable label="责任奖系数" align="center" prop="dutyRewardRatio" >
        <template slot-scope="scope">
          <el-input-number :precision="2" controls-position="right" :min="0" :max="100" v-model="scope.row.dutyRewardRatio" class="w100"></el-input-number>
        </template>
      </el-table-column>
<!--      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['average:averageDetail:remove']" title="删除"></el-button>
        </template>
      </el-table-column>-->
    </el-table>

<!--    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />-->

  </div>
  </el-dialog>
</template>

<script>
import {
  listAverageDetail,
  getAverageDetail,
  delAverageDetail,
  addAverageDetail,
  updateAverageDetail,
  exportAverageDetail,
  saveAverageDetail
} from '@/api/average/averageDetail'
import { refresh, save } from '@/api/revenue/special'
import { saveData } from '@/api/bonus/deptSort'

export default {
  name: "addEditDetail",
  components: {
  },
  props: {
    openDetail: {
      type: Boolean,
      default: false
    },
    id: {
      type: [String, Number],
      default: ""
    },
  },
  watch: {
    openDetail: {
      handler(val) {
        this.openDialog = val;
      },
      deep: true
    },
  },
  data() {
    return {
      openDialog: this.openDetail,
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
      // 平均奖科人数明细表格数据
      averageDetailList: [],
      selectionRow: [],
      // 弹出层标题
      title: "平均奖科人数",
      // 查询参数
      queryParams: {
        avgId: this.id,
        pageNum: 1,
        pageSize: 500,
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
    /** 查询平均奖科人数明细列表 */
    getList() {
      this.loading = true;
      listAverageDetail(this.queryParams).then(response => {
        this.averageDetailList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
	successSubmit(flag) {
      this.openDetail = flag;
      this.getList();
    },
    // 取消按钮
    cancel() {
      this.$emit("cancelDetail", false);
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        avgCode: null,
        depCode: null,
        depName: null,
        docCode: null,
        docName: null,
        isReward: null,
        workDays: null,
        postRatio: null,
        dutyRewardRatio: null,
        // delFlag: null,
        // createBy: null,
        // createTime: null,
        // updateBy: null,
        updateTime: null,
        // remark: null
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
    /* // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
      this.selectionRow=selection
    }, */
    /* rowClick(row, column, event) {
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
    }, */
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除平均奖科人数明细编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delAverageDetail(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    handleRefresh(){
      this.getList()
    },
    handleSave() {
      this.$confirm('是否保存', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.saveLoading = true
        let data = {
          avgId: this.id,
          data: this.$refs.multipleTable.data,
        }
        saveAverageDetail(data).then(response => {
          this.msgSuccess('保存成功')
          this.saveLoading = false
        })
      })
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit("cancelDetail", false);
    },
  }
};
</script>
<style lang="scss" scoped>
::v-deep .el-dialog {
  height: 60%;
  overflow: hidden;
  .el-dialog__body {
    height: calc(100%);
    overflow: auto;
  }
}
</style>
