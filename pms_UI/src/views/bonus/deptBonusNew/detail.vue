<template>
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="年月" prop="actDate">
          <el-date-picker :clearable="false" :editable="false" label="奖金年月" size="small"
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

    <el-table border stripe v-loading="loading" :data="deptRoomList" :height="tableHeight" @selection-change="handleSelectionChange"
              ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName" >
      <el-table-column sortable label="科室名称" align="left" prop="deptRoomName" fixed>
        <template slot-scope="scope" >
          <el-link type="primary" :underline="false" @click="showDetail(scope.row.deptRoomId)"
                   class="displayBlock text-ellipsis ">{{scope.row.deptRoomName}}</el-link>
        </template>
      </el-table-column>

      <el-table-column
        v-for="(item,index) in deptBonusIndexList"
        :key="index"
        :label="item.indexName"
        :prop="item.guideCode"
        align="right"
      >{{item.guideValueResult}}
      </el-table-column>
      <el-table-column sortable label="合计" align="right" prop="total" fixed="right" width="100">
        <template slot-scope="scope">
          <span>{{ sumTotal(scope.row) }}</span>
        </template>
      </el-table-column>
      <el-table-column sortable label="审核状态" align="center" prop="auditState" :formatter="auditStateFormat" fixed="right" width="100"></el-table-column>
      <el-table-column sortable label="是否归档" align="center" prop="archiveState" :formatter="yesOrNoFormat" fixed="right" width="100">
        <template slot-scope="scope" v-if="scope.row.archiveState != null">
          <el-tag :type="scope.row.archiveState === '0' ? 'success' : 'warning'"
                  close-transition>{{scope.row.archiveState | yesOrNoFormat}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="流传历史" align="center" fixed="right" width="80">
        <template slot-scope="scope">
<!--          <el-button @click="handleClick(scope.row)" type="text" size="small"><i class="el-icon-view"></i></el-button>-->
          <el-button @click="processRecord" type="text" size="small"><i class="el-icon-view"></i></el-button>
        </template>
      </el-table-column>
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
import {deptBonusDetail} from '@/api/evaluateData/evaluateDataDept'
import { getYearMonth2 } from '@/utils/date'
import {getProcessId} from "@/api/bonus/audit";
var _self;
export default {
  name: 'DeptBonus',
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
    },
    yesOrNoFormat(val){
      return _self.selectDictLabel(_self.yesOrNoOptions, val);
    }
  },
  computed: {
    sumTotal() {
      return function (row) {
        console.log(JSON.stringify(row))
        let result = 0;
        for(let v of this.deptBonusIndexList){
          result += parseFloat(v.guideValueResult);
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
      // 部门列表
      deptRoomList: [],
      // 部门指标奖金列表
      deptBonusIndexList: [],
      selectionRow: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      tableHeight: "auto",
      // 查询参数
      queryParams: {
        compCode: null,
        deptName: null,
        accountCode: null,
        guideCode: null,
        guideValue: null,
        actDate: getYearMonth2() + '-01'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      yesOrNoOptions: [],
      auditStateOptions: [],
      drawer: false,
      processSrc: '',
      processInstanceId: ''
    }
  },
  beforeCreate() {
    _self = this;
  },
  created() {
    this.getDicts("yes_or_no").then(response => {
      this.yesOrNoOptions = response.data;
    });
    this.getDicts('asset_aduit_state').then(response => {
      this.auditStateOptions = response.data
    })
    this.getDept()
  },
  methods: {
    /** 查询核算科室或平均奖科室对应奖金指标 */
    getDept() {
      this.loading = true;
      deptBonusDetail(this.queryParams).then(response => {
        if(response.code == '200'){
          this.deptRoomList = response.data.deptRoomList;
          this.deptBonusIndexList = response.data.deptBonusIndexList;
        }
        this.loading = false;
      }).catch(err => {
        this.loading = false;
      });
    },
    successSubmit(flag) {
      this.open = flag
      this.getDept()
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
        actDate: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getDept()
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
    /** 跳转科室对应人员奖金详情页 */
    showDetail(deptCode) {
      this.$store.dispatch("tagsView/delView",this.$route);
      this.$router.push("/personBonus/index/" + deptCode + "/" + this.queryParams.actDate);
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let h = a + 18 + 45 + 84 + 35;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    // 审核状态字典翻译
    auditStateFormat(row, column) {
      return this.selectDictLabel(this.auditStateOptions, row.auditState)
    },
    // 参数系统内置字典翻译
    yesOrNoFormat(row, column) {
      return this.selectDictLabel(this.yesOrNoOptions, row.archiveState);
    },
    /** 流转历史按钮操作 */
    processRecord() {
      let param = {deptCode: this.deptRoomList[0].deptRoomId, nowDate: this.queryParams.actDate};
      getProcessId(param).then(response => {
        if(response.data){
          this.processInstanceId = response.data.procedureId;
          if (!this.processInstanceId) {
            this.msgError('没有流转历史数据！');
          } else {
            this.processSrc = `${this.processUrl}/api/wf/processHistoric?procInsId=${this.processInstanceId}`;
            this.drawer = true;
          }
        }else{
          this.msgError('没有流转历史数据！');
        }
      });
    }
  }
}
</script>


