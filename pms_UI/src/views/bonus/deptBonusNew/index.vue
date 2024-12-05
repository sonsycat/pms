<template>
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px" hidden>
        <el-form-item label="年月" prop="actDate">
          <el-date-picker clearable size="small"
                          v-model="queryParams.actDate"
                          type="month"
                          value-format="yyyy-MM-dd"
                          placeholder="选择年月"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div ref="btns">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="info"
            icon="el-icon-back"
            size="mini"
            :loading="exportLoading"
            @click="goBack"
          >返回</el-button>
        </el-col>
<!--        <el-col :span="1.5">-->
<!--          <el-button-->
<!--            type="warning"-->
<!--            icon="el-icon-document-copy"-->
<!--            size="mini"-->
<!--            @click=""-->
<!--          >导出EXCEL-->
<!--          </el-button>-->
<!--        </el-col>-->
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <el-table border stripe v-loading="loading" :data="EvaluateDataList" :height="tableHeight" @selection-change="handleSelectionChange"
              ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName" >

      <el-table-column sortable label="科室名称" align="left" prop="deptRoomName" fixed="left">
        <template slot-scope="scope" >
          <el-link type="primary" :underline="false" @click="handleUpdate(scope.row.deptRoomId)"
                   class="displayBlock text-ellipsis ">{{scope.row.deptRoomName}}</el-link>
        </template>
      </el-table-column>

      <el-table-column
        v-for="(item,index) in indexList"
        :key="index"
        :label="item.indexName"
        :prop="item.guideCode"
        align="center"
      >{{item.guideValueResult}}
      </el-table-column>
      <el-table-column sortable label="合计" align="center" prop="total">
        <template slot-scope="scope">
          <span>{{ computedStatus(scope.row) }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column sortable label="审核状态" align="center" prop="auditState" :formatter="auditStateFormat" fixed="right" width="100"></el-table-column>-->
<!--      <el-table-column label="流传历史" align="center" fixed="right" width="80">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button @click="processRecord(scope.row)" type="text" size="small"><i class="el-icon-view"></i></el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>

    <el-drawer
      title="流转历史"
      :visible.sync="drawer"
      :with-header="true" size="50%">
      <div>
        <div v-loading="loading" :style="'height:calc(100vh - 100px)'">
          <iframe :src="processSrc" frameborder="no" style="width: 100%;height: 100%" scrolling="auto"/>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>


import { getAccountDept, listDeptBonus } from '@/api/evaluateData/evaluateDataDeptNew'
import { getYearMonth2 } from '@/utils/date'
import {getProcessId} from "@/api/bonus/audit";

export default {
  name: 'EvaluateData',
  deptType: {
    type: [String, String],
    default: ""
  },
  actDate:{
    type: [String, String],
    default: ""
  },
  filters: {
    transToValue(val, index) {
      return val[index]
    }
  },
  computed: {
    computedStatus() {
      return function (row) {
        let result = 0;
        for(let v of this.indexList){
          result += (isNaN(row[v.guideCode])?0:parseFloat(row[v.guideCode]));
        }
        return result.toFixed(2);;
      };
    }

  },
  mounted(){
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  watch: {
    listData(val) {
      let that = this;
      setTimeout(function () {
        that.resizeHandler();
      }, 300);
    }
  },
  data() {
    return {
      saveLoading: false,
      // 遮罩层
      loading: false,
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
      // 表格数据
      EvaluateDataList: [],
      selectionRow: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      tableHeight: "auto",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode: null,
        deptName: null,
        accountCode: null,
        guideCode: null,
        guideValue: null,
        // actDate: getYearMonth2() + '-01',
        actDate: null,
        deptType: null
      },
      // 表单参数
      form: {},
      //类型
      indexList: [],
      auditStateOptions: [],
      // 表单校验
      rules: {},
      drawer: false,
      processSrc: '',
      procInsId: ''
    }
  },
  created() {
    this.getDept();
    this.getDicts('asset_aduit_state').then(response => {
      this.auditStateOptions = response.data
    })
  },
  methods: {
    /** 查询核算科室或平均奖科室对应奖金指标 */
    getDept() {
      this.loading = true;
      this.queryParams.actDate = this.$route.params.actDate;
      this.queryParams.nowDate = this.$route.params.actDate;
      this.queryParams.deptType = this.$route.params.deptType;
      getAccountDept(this.queryParams).then(response => {
        this.indexList = response.data
        this.getList()
        this.loading = false;
      });
    },

    /** 查询核算科室或平均奖科室奖金数据 */
    getList() {
      this.loading = true
      if (!this.queryParams.actDate) {
        this.queryParams.actDate = getYearMonth2() + '-01'
      }
      this.queryParams.deptType = this.$route.params.deptType;
      listDeptBonus(this.queryParams).then(response => {
        this.EvaluateDataList = response.data
        this.loading = false
      })
    },
    successSubmit(flag) {
      this.open = flag
      this.getList()
    },
    // 取消按钮
    cancel() {
      this.open = false
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: null,
        deptName: null,
        accountCode: null,
        guideCode: null,
        guideValue: null,
        delFlag: null,
        actDate: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
      this.selectionRow = selection
    },
    rowClick(row, column, event) {
      let refsElTable = this.$refs.multipleTable
      let findRow = this.selectionRow.find(c => c.rowIndex == row.rowIndex)
      if (findRow && this.selectionRow.length == 1) {
        refsElTable.toggleRowSelection(row, false)
        return
      }
      refsElTable.clearSelection()
      refsElTable.toggleRowSelection(row)
    },
    rowStyle({row, rowIndex}) {
      Object.defineProperty(row, 'rowIndex', {
        value: rowIndex,
        writable: true,
        enumerable: false
      })
    },
    rowClassName({row, rowIndex}) {
      let rowName = '',
        findRow = this.selectionRow.find(c => c.rowIndex === row.rowIndex)
      if (findRow) {
        rowName = 'current-row '
      }
      return rowName
    },
    /** 新增按钮操作
     handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加独立核算提成比";
    },
     */
    /** 新增按钮操作 */
    handleAdd() {
      this.$confirm('是否保存', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.saveLoading = true
        let data = {
          data: this.$refs.multipleTable.data,
          'actDate': this.queryParams.actDate
        }
        addCommission(data).then(response => {
          this.msgSuccess('保存成功')
          this.saveLoading = false
        })
      })
    },
    /** 跳转科室对应人员奖金详情页 */
    handleUpdate(deptCode) {
      this.$store.dispatch("tagsView/delView",this.$route);
      this.$router.push("/personBonusNew/staffview/" + deptCode + "/" + this.queryParams.actDate);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCommission(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addCommission(this.form).then(response => {
              this.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除独立核算提成比编号为"' + ids + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delCommission(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出科室奖金数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.exportLoading = true
        return exportCommission(queryParams)
      }).then(response => {
        this.download(response.msg)
        this.exportLoading = false
      }).catch(() => {
      })
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 35;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    // 审核状态字典翻译
    auditStateFormat(row, column) {
      return this.selectDictLabel(this.auditStateOptions, row.auditState)
    },
    // 返回
    goBack() {
      // 关闭当前页面
      this.$store.dispatch("tagsView/delView",this.$route);
      // 返回上一页面
      this.$router.go(-1)
    },
    /** 流转历史按钮操作 */
    processRecord(row) {
      this.drawer = true;
      let param = {deptCode: row.deptRoomId, nowDate: row.actDate};
      getProcessId(param).then(response => {
        if(response.data){
          this.procInsId = response.data.procedureId;
          if (!this.procInsId) {
            this.msgError('没有流转历史数据！')
          } else {
            this.processSrc = `${this.processUrl}/api/wf/processHistoric?procInsId=${this.procInsId}`;
          }
        }
      });
    }
  }
}
</script>


