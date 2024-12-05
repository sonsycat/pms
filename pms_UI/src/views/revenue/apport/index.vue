<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
      <el-form-item label="年月" prop="occureTime">
        <el-date-picker clearable
          class="search_w220"
          v-model="queryParams.occureTime"
          type="month"
          value-format="yyyy-MM"
          placeholder="请选择年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结算标识" prop="settleType">
        <el-select class="search_w220" v-model="queryParams.settleType" placeholder="请选择结算标识" clearable>
          <el-option
            v-for="dict in settleTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
          v-hasPermi="['revenue:apport:add']"
        >收入生成</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          :disabled="single"
          icon="el-icon-search"
          size="mini"
          @click="handleUpdate"
          v-hasPermi="['revenue:apport:edit']"
        >查看明细</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="freshTable"></right-toolbar>
    </el-row>
	</div>
	<el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="分解前" name="resolvePre">
      <el-table border stripe v-loading="preLoading" :data="apportList" ref="multipleTablePre" @selection-change="handleSelectionChangeBefore" @row-click="rowClickBefore" :row-style="rowStyle" :row-class-name="rowClassName">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="名称" align="left" prop="name">
          <template slot-scope="scope">
            <el-link type="primary" :underline="false" v-if="scope.row.name!=='合计'" v-html="scope.row.name" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
            <span style="font-size:14px" v-else>合计</span>
          </template>
        </el-table-column>
        <el-table-column label="总收入" align="right" prop="value" />
    </el-table>
    <div class="canvas">
      <div style="width:500px;height:500px;left:50%;transform:translateX(-50%)" ref="chart"></div>
    </div>
    </el-tab-pane>
    <el-tab-pane label="分解后" name="resolveNext">
      <el-table border stripe v-loading="afterLoading" :data="apportListNext" ref="multipleTableAfter" @selection-change="handleSelectionChangeAfter" @row-click="rowClickAfter" :row-style="rowStyle" :row-class-name="rowClassName">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="名称" align="left" prop="name">
          <template slot-scope="scope">
            <el-link type="primary" :underline="false" v-if="scope.row.name!=='合计'" v-html="scope.row.name" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
            <span style="font-size:14px" v-else>合计</span>
          </template>
        </el-table-column>
        <el-table-column label="总收入" align="right" prop="value" />
    </el-table>
    <div class="canvas">
      <div style="width:500px;height:500px;top:40%;left:50%;transform:translateX(-50%)" ref="chartNext"></div>
    </div>
    </el-tab-pane>
	</el-tabs>

	<addEdit v-if="open" :open="open" @cancel="cancel" @success="successSubmit"></addEdit>
	<detail v-if="detailOpen" :types="type" :open="detailOpen" :params="form" @cancel="cancelDetail" @success="successSubmitDetail"/>
  </div>
</template>

<script>
// listApport, getApport, delApport, addApport, updateApport, exportApport,
import { getPreList, getAfterList} from "@/api/revenue/apport";
import addEdit from "./addEdit";
import detail from "./detail";
import {getYearMonth2} from "@/utils/date";

export default {
  name: "Apport",
  components: {
    addEdit,
    detail
  },
  data() {
    return {
      activeName:'resolvePre',
      preLoading: false,
      afterLoading: false,
      single:true,
      // 导出遮罩层
      exportLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      afterTotal:0,
      // 收入生成表格数据
      apportList: [],
      apportListNext:[],
      selectionRow: [],
      echartsPreList:[],
      echartsAfterList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      detailOpen:false,
      type:'resolveBefore',
      // 结算标识(1,正常,2附加收入)字典
      settleTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode: this.$store.state.user.accountInfo.deptId,
        accountCode: null,
        revenueCode: null,
        receiveNum: null,
        actualNum: null,
        dpetCode: null,
        occureTime: getYearMonth2(),
        revenueType: null,
        revenueKind: null,
        settleType: null,
        settleMark: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getPreList();
    this.getDicts("pms_jsbs_type").then(response => {
      this.settleTypeOptions = response.data;
    });
  },
  mounted(){
  },
  methods: {
    handleClickTableName(row){
      if(this.activeName=="resolvePre"){
        this.rowClickBefore(row);
        this.handleUpdate(row);
      }else{
        this.rowClickAfter(row);
        this.handleUpdate(row);
      }
    },
    handleClick(tab, event) {
      if(tab.label=="分解后"){
        this.getAfterList();
      }else{
        this.getPreList();
      }
    },
    getPreList(){
      this.preLoading=true;
      getPreList(this.queryParams).then(response=>{
        if(response.code==200){
          this.apportList = JSON.parse(JSON.stringify(response.rows));
          this.total=response.total;
          let data=response.rows.map(_=>{
            _.name=_.name + ' ￥' + _.value;
            return _
          })
          this.echartsPreList=JSON.parse(JSON.stringify(data))
          this.echartsPreList.pop()
          this.initCharts();
          for (let i = 0; i < this.apportList.length; i++) {
              for (var j in this.apportList[i]) {
                if ((typeof this.apportList[i][j]) == "number" && this.apportList[i][j] != 0 && j!="id") {
                  this.apportList[i][j] = this.moneyFormat(this.apportList[i][j])
                }
              }
          }
          this.preLoading=false;
        }
      })
    },
    getAfterList(){
      this.afterLoading=true;
      getAfterList(this.queryParams).then(response=>{
        if(response.code==200){
          this.apportListNext = JSON.parse(JSON.stringify(response.rows));
          this.afterTotal=response.total;
          let data=response.rows.map(_=>{
            _.name=_.name + ' ￥' + _.value;
            return _
          })
          this.echartsAfterList=JSON.parse(JSON.stringify(data));
          this.echartsAfterList.pop()
          this.initChartsNext();
          for (let i = 0; i < this.apportListNext.length; i++) {
              for (var j in this.apportListNext[i]) {
                if ((typeof this.apportListNext[i][j]) == "number" && this.apportListNext[i][j] != 0 && j!="id") {
                  this.apportListNext[i][j] = this.moneyFormat(this.apportListNext[i][j])
                }
              }
          }
          this.afterLoading=false
        }
      })
    },
    //更新表格数据
    freshTable(){
      if(this.activeName=="resolvePre"){
        this.getPreList();
      }else{
        this.getAfterList();
      }
    },
    // 多选框选中数据
    handleSelectionChangeBefore(selection) {
      if(selection[0]&&selection[0].name=='合计'){
        this.single=true
      }else{
        this.single = selection.length!==1
        this.selectionRow=selection;
      }
    },
    rowClickBefore(row, column, event) {
      let refsElTable = this.$refs.multipleTablePre;
      let findRow = this.selectionRow.find(c => c.rowIndex == row.rowIndex);
      if (findRow && this.selectionRow.length == 1) {
        refsElTable.toggleRowSelection(row, false);
        return;
      }
      refsElTable.clearSelection();
      refsElTable.toggleRowSelection(row);
    },
    handleSelectionChangeAfter(selection) {
      if(selection[0]&&selection[0].name=='合计'){
        this.single=true
      }else{
        this.single = selection.length!==1
        this.selectionRow=selection;
      }
    },
    rowClickAfter(row, column, event) {
      let refsElTable = this.$refs.multipleTableAfter;
      let findRow = this.selectionRow.find(c => c.rowIndex == row.rowIndex);
      if (findRow && this.selectionRow.length == 1) {
        refsElTable.toggleRowSelection(row, false);
        return;
      }
      refsElTable.clearSelection();
      refsElTable.toggleRowSelection(row);
    },
    // 结算标识(1,正常,2附加收入)字典翻译
    settleTypeFormat(row, column) {
      return this.selectDictLabel(this.settleTypeOptions, row.settleType);
    },
    initCharts () {
  　　let myChart = this.$echarts.init(this.$refs.chart);
  　　// 绘制图表
  　　myChart.setOption({
    　  title: {
          text: '',
          subtext: '',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b}'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          bottom:0,
        },
        colorBy:'series',
        color:['#66b3ff','#91cc75','#fac858','#ee6666','#73c0de','#3ba272','#fc8452','#9a60b4','#ea7ccc'],
        series: [
          {
            name: '',
            type: 'pie',
            radius: '60%',
            data: this.echartsPreList,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
    　});
    },
    initChartsNext () {
  　　let myChart = this.$echarts.init(this.$refs.chartNext);
  　　// 绘制图表
  　　myChart.setOption({
    　  title: {
          text: '',
          subtext: '',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b}'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          bottom:0,
        },
        colorBy:'series',
        color:['#66b3ff','#91cc75','#fac858','#ee6666','#73c0de','#3ba272','#fc8452','#9a60b4','#ea7ccc'],
        series: [
          {
            name: '',
            type: 'pie',
            radius: '60%',
            data: this.echartsAfterList,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
    　});
    },
	  successSubmit(flag) {
      this.open = flag;
      this.freshTable();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.freshTable();
    },
    successSubmitDetail(flag) {
      this.detailOpen = flag;
      this.freshTable();
    },
    // 取消按钮
    cancelDetail() {
      this.detailOpen = false;
      this.freshTable();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: null,
        accountCode: null,
        revenueCode: null,
        receiveNum: null,
        actualNum: null,
        dpetCode: null,
        occureTime: getYearMonth2(),
        revenueType: null,
        revenueKind: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        settleType: null
      };
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.freshTable();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.activeName='resolvePre';
      this.freshTable();
    },
    rowStyle({
      row,
      rowIndex
    }) {
      Object.defineProperty(row, 'rowIndex', {
        value: rowIndex,
        writable: true,
        enumerable: false,
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
      this.open = true;
      this.title = "添加收入生成";
    },

    //明细
    handleUpdate(row) {
      this.reset()
      let obj={};
        switch(this.selectionRow[0].name){
          case '门诊收入':
          obj={
            revenueType:1,
            revenueKind:1,
          };
          break;
          case '门诊附加收入':
          obj={
            revenueType:1,
            revenueKind:2,
          };
          break;
          case '住院收入':
          obj={
            revenueType:2,
            revenueKind:1,
          };
          break;
          case '住院附加收入':
          obj={
            revenueType:2,
            revenueKind:2,
          };
          break;
          default:
          obj={}
          return;
        }
      if(this.activeName=="resolveNext"){
        this.type='resolveAfter';
        this.form={
          pageNum: 1,
          pageSize: 30,
          compCode: this.$store.state.user.accountInfo.deptId,
          oTime:this.queryParams.occureTime,
          settleType:this.queryParams.settleType,
          ...obj
        }
      }else{
        this.type='resolveBefore';
        this.form={
          pageNum: 1,
          pageSize: 30,
          compCode: this.$store.state.user.accountInfo.deptId,
          occureT:this.queryParams.occureTime,
          settleMark:this.queryParams.settleType,
          ...obj
        }
      }
      this.detailOpen = true;
    },
  }
};
</script>
<style lang="scss" scoped>
  ::v-deep .el-tabs{
    .el-table{
      width: 50%;
      float: left;
      th{
        height: 60px;
      }
      tr{
        height: 66px;
      }
    }
    .canvas{
      float: left;
      width:50%;
      margin-top: -4%;
    }
    .el-tabs__nav-scroll{
      padding-left:20px;
    }
  }
</style>

