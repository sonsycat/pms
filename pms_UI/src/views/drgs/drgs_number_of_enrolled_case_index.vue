<template>
  <div class="app-container" style="padding: 0">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="科室组入组病例" name="resolvePre">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-table ref="multipleTablePre" v-loading="preLoading" :data="deptGroupEnrolledCasesList" :row-class-name="rowClassName" :row-style="rowStyle" border stripe>
              <el-table-column align="left" label="科室组" prop="dept"/>
              <el-table-column align="center" label="本期" prop="currentPeriod"/>
              <el-table-column align="center" label="同期" prop="correspondPeriod"/>
              <el-table-column align="center" label="增长/下降">
                <template v-slot="scope">
                  <div style="width: 70px;margin-left: calc((100% - 70px)/2)">
                <span v-if="scope.row.currentPeriod - scope.row.correspondPeriod > 0" style="float: left">
                  <el-tag type="success"><i class="el-icon-top" style="font-weight: bold;"/></el-tag>
                </span>
                    <span v-else style="float: left">
                  <el-tag type="danger"><i class="el-icon-bottom" style="font-weight: bold;"/></el-tag>
                </span>
                    <span style="float: right">{{calPercent(scope.row.currentPeriod,scope.row.correspondPeriod)}}%</span>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
          <el-col :span="12">
            <div ref="chart" style="height:600px;width: calc(44vw)"></div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="全院" name="resolveNext">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-table ref="multipleTableAfter" v-loading="afterLoading" :data="allEnrolledCasesList" :row-class-name="rowClassName" :row-style="rowStyle" border stripe>
              <el-table-column align="left" label="指标" prop="target"/>
              <el-table-column align="center" label="本期" prop="currentPeriod"/>
              <el-table-column align="center" label="同期" prop="correspondPeriod"/>
              <el-table-column align="center" label="增长/下降">
                <template v-slot="scope">
                  <div style="width: 70px;margin-left: calc((100% - 70px)/2)">
                  <span v-if="scope.row.currentPeriod - scope.row.correspondPeriod > 0" style="float: left">
                    <el-tag type="success"><i class="el-icon-top" style="font-weight: bold;"/></el-tag>
                  </span>
                    <span v-else style="float: left">
                    <el-tag type="danger"><i class="el-icon-bottom" style="font-weight: bold;"/></el-tag>
                  </span>
                    <span style="float: right">{{calPercent(scope.row.currentPeriod,scope.row.correspondPeriod)}}%</span>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
          <el-col :span="12">
            <div ref="chartNext" style="height:500px;width: calc(44vw);"></div>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: "report",
  components: {},
  data() {
    return {
      activeName: 'resolvePre',
      preLoading: false,
      afterLoading: false,
      single: true,
      selectionRow: [],
      deptGroupEnrolledCasesList: [
        {
          "dept": "血液内科",
          "currentPeriod": 564,
          "correspondPeriod": 512,
          "change": "100%"
        },
        {
          "dept": "神经内科",
          "currentPeriod": 5803,
          "correspondPeriod": 5465,
          "change": "100%"
        },
        {
          "dept": "骨科",
          "currentPeriod": 528,
          "correspondPeriod": 456,
          "change": "100%"
        },
        {
          "dept": "（显微）手外科",
          "currentPeriod": 120,
          "correspondPeriod": 111,
          "change": "100%"
        },
        {
          "dept": "介入血管外科",
          "currentPeriod": 520,
          "correspondPeriod": 458,
          "change": "100%"
        },
        {
          "dept": "神经外科",
          "currentPeriod": 1080,
          "correspondPeriod": 878,
          "change": "100%"
        },
        {
          "dept": "康复医学科",
          "currentPeriod": 4985,
          "correspondPeriod": 5456,
          "change": "100%"
        },
        {
          "dept": "急诊科",
          "currentPeriod": 623,
          "correspondPeriod": 456,
          "change": "100%"
        },
        {
          "dept": "儿科",
          "currentPeriod": 1487,
          "correspondPeriod": 1515,
          "change": "100%"
        }
      ],
      allEnrolledCasesList:[
        {
          "target": "入组病例数",
          "currentPeriod": 51159,
          "correspondPeriod": 51218,
          "change": "100%"
        },
        {
          "target": "DRGs组数",
          "currentPeriod": 520036,
          "correspondPeriod": 546511,
          "change": "100%"
        },
        {
          "target": "总权重数",
          "currentPeriod": 468346,
          "correspondPeriod": 456111,
          "change": "100%"
        },
        {
          "target": "CMI值",
          "currentPeriod": 1.2,
          "correspondPeriod": 1.008,
          "change": "100%"
        }
      ]
    };
  },
  created() {
    this.$nextTick(() => {
      this.initCharts()
      //this.initChartsNext()
    })
  },
  computed: {},
  mounted() {
  },
  methods: {
    handleClick(tab, event) {
      if(tab.label=="科室组入组病例"){
        this.initCharts();
      }else{
        this.initChartsNext();
      }
    },
    calPercent(val1, val2){
      if(val1 - val2 > 0){
        return ((val1 - val2)/val1*100).toFixed(2)
      }else{
        return ((val2 - val1)/val2*100).toFixed(2)
      }
    },
    initCharts() {
      let myChart = this.$echarts.init(this.$refs.chart, null, {
        renderer: 'canvas',
        useDirtyRect: false
      });
      // 绘制图表
      myChart.setOption({
        title: {
          text: '科室组入组病例'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {},
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          data: this.deptGroupEnrolledCasesList.map(obj => {return obj.dept}).reverse()
        },
        color: ['#66b3ff', '#91cc75'],
        series: [
          {
            name: '2023',
            type: 'bar',
            data: this.deptGroupEnrolledCasesList.map(obj => {return obj.currentPeriod}).reverse()
          },
          {
            name: '2022',
            type: 'bar',
            data: this.deptGroupEnrolledCasesList.map(obj => {return obj.correspondPeriod}).reverse()
          }
        ]
      });
    },
    initChartsNext() {
      let myChart = this.$echarts.init(this.$refs.chartNext, null, {
        renderer: 'canvas',
        useDirtyRect: false
      });
      // 绘制图表
      myChart.setOption({
        title: {
          text: '全院'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {},
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          data: this.allEnrolledCasesList.map(obj => {return obj.target}).reverse()
        },
        color: ['#66b3ff', '#91cc75'],
        series: [
          {
            name: '2023',
            type: 'bar',
            data: this.allEnrolledCasesList.map(obj => {return obj.currentPeriod}).reverse()
          },
          {
            name: '2022',
            type: 'bar',
            data: this.allEnrolledCasesList.map(obj => {return obj.correspondPeriod}).reverse()
          }
        ]
      });
    },
    rowStyle({row, rowIndex}) {
      Object.defineProperty(row, 'rowIndex', {
        value: rowIndex,
        writable: true,
        enumerable: false,
      })
    },
    rowClassName({row, rowIndex}) {
      let rowName = "",
        findRow = this.selectionRow.find(c => c.rowIndex === row.rowIndex);
      if (findRow) {
        rowName = "current-row ";
      }
      return rowName;
    }
  }
};
</script>
<style lang="scss" scoped>
::v-deep .el-tabs {
  .el-tabs__nav-scroll {
    padding-left: 20px;
  }
}
</style>

