<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="60%"  custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <div ref="search">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="复制年月" prop="copyDate">
              <el-date-picker clearable size="small"
                              v-model="form.copyDate"
                              type="month"
                              value-format="yyyy-MM"
                              placeholder="选择复制年月"
                              @change="getList"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="当前年月" prop="nowDate">
              <el-date-picker clearable size="small"
                              v-model="form.nowDate"
                              type="month"
                              value-format="yyyy-MM"
                              placeholder="选择当前年月" disabled>
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-table border stripe max-height="600px" v-loading="loading" :data="projectList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column sortable label="方案编码" align="center" prop="projectCode"  />
              <el-table-column sortable label="方案名称" align="left" prop="projectName" />
              <el-table-column sortable label="拼音码" align="center" prop="spell"  />
              <el-table-column sortable label="方案比例" align="center" prop="rationShow" width="372px" show-overflow-tooltip/>
            </el-table>
          </el-col>
        </el-row>
      </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import { listProject, getProject, delProject, addProjectDept, updateProject, listProjectDept,copyProject } from "@/api/apportionment/project";
import pinyin from "js-pinyin";
import { getCostApport } from "@/api/system/dict/data"
import {getYear} from "@/utils/date";

export default {
  name: "addEdit",
  props: {
    open: {
      type: Boolean,
      default: false
    },
  },
  watch: {
    open: {
      handler(val) {
        this.openDialog = val;
      },
      deep: true
    },
  },
  components: {
  },
  data() {
    return {
      // 分摊方案管理表格数据
      projectList: [],
      selectionRow: [],
      tableHeight: "auto",
      title: "分摊方案管理-新增",
      openDialog: this.open,
      // 遮罩层
      loading: true,
      pym: "",
      // 表单参数
      form: {},
      projectTypeOptions: [],
	  	// 表单校验
      rules: {
      },
    };
  },
  created() {
    this.reset();
    this.getList();
    this.getDicts("cost_fa_type").then(response => {
      this.projectTypeOptions = response.data;
    });
     this.getCostApport();
  },
  computed: {
    listData() {
      return this.$store.state.app.sidebar.opened;
    }
  },
  mounted() {
  },
  methods: {
    //拷贝增加年月限制
    getCostApport() {
      getCostApport(this.$store.state.user.accountInfo.deptId).then(response => {
        if (response.data) {
          this.form.nowDate = response.data;
        } else {
          this.form.nowDate = getYear();
        }
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multiple = !selection.length
      this.selectionRow=selection
      this.projectType = selection.map(item => item.projectType);
      this.projectCode = selection.map(item => item.projectCode);
      this.projectInCode = selection.map(item => item.projectInCode);
    },
    rowClick(row, column, event) {
      let refsElTable = this.$refs.multipleTable;
      let findRow = this.selectionRow.find(c => c.rowIndex == row.rowIndex);
      if (findRow && this.selectionRow.length == 1) {
        refsElTable.toggleRowSelection(row, false);
        return;
      }
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
    tableRowClassName({ row, rowIndex }) {
      row.index = rowIndex;
    },
  	getDetailInfo() {
      getProject(this.id).then(response => {
        this.form = response.data;
      });
    },
    /** 查询分摊方案管理列表 */
    getList() {
      this.loading = true;
      let queryParams ={
          compCode: this.$store.state.user.accountInfo.deptId,
          nowDate: this.form.copyDate
        };
      listProject(queryParams).then(response => {
        this.projectList = response.rows;
        console.log(this.projectList)
        this.loading = false;
      });
    },
    // 方案类型字典翻译
    projectTypeFormat(row, column) {
      return this.selectDictLabel(this.projectTypeOptions, row.projectType);
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit("cancel", false);
    },
    // 取消
    cancel() {
      this.$emit("cancel", false);
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: this.$store.state.user.accountInfo.deptId,
        accountCode: null,
        projectCode: null,
        projectName: null,
        copyDate: null,
        nowDate: null,
        spell: null,
        projectType: null
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      this.form.costApportionmentProjectList = this.selectionRow
      console.log("form",this.form)
      let saveLoading = this.saveLoading()
      copyProject(this.form).then(response => {
        saveLoading.close()
        this.msgSuccess("拷贝成功");
        this.$emit("success", false);
      }).catch(()=>{
        saveLoading.close();
      });
    },
}};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    min-height: 20%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
