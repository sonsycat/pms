<template>
  <div class="app-container" style="padding: 0">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="科室组总费用" name="tabPane1">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-table ref="multipleTable1" v-loading="preLoading" :data="dataList1" :row-class-name="rowClassName" :row-style="rowStyle" border stripe>
              <el-table-column align="left" label="科室组" prop="dept"/>
              <el-table-column align="center" label="本期" prop="currentPeriod"/>
              <el-table-column align="center" label="同期" prop="correspondPeriod"/>
              <el-table-column align="center" label="增长/下降">
                <template v-slot="scope">
                  <div style="width: 75px;margin-left: calc((100% - 75px)/2)">
                <span v-if="scope.row.currentPeriod - scope.row.correspondPeriod >= 0" style="float: left">
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
            <div ref="chartContainer1" style="height:600px;width: calc(44vw)"></div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="例均费用" name="tabPane2">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-table ref="multipleTable2" v-loading="afterLoading" :data="dataList2" :row-class-name="rowClassName" :row-style="rowStyle" border stripe>
              <el-table-column align="left" label="指标" prop="target"/>
              <el-table-column align="center" label="本期" prop="currentPeriod"/>
              <el-table-column align="center" label="同期" prop="correspondPeriod"/>
              <el-table-column align="center" label="增长/下降">
                <template v-slot="scope">
                  <div style="width: 70px;margin-left: calc((100% - 70px)/2)">
                  <span v-if="scope.row.currentPeriod - scope.row.correspondPeriod >= 0" style="float: left">
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
            <div ref="chartContainer2" style="height:500px;width: calc(44vw);"></div>
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
      activeName: 'tabPane1',
      preLoading: false,
      afterLoading: false,
      single: true,
      selectionRow: [],
      dataList1: [
        {
          "dept": "血液内科",
          "currentPeriod": 396.02,
          "correspondPeriod": 385.15,
          "change": "100%"
        },
        {
          "dept": "神经内科",
          "currentPeriod": 1589.21,
          "correspondPeriod": 1548.45,
          "change": "100%"
        },
        {
          "dept": "骨科",
          "currentPeriod": 2648.85,
          "correspondPeriod": 2584.58,
          "change": "100%"
        },
        {
          "dept": "（显微）手外科",
          "currentPeriod": 7856.00,
          "correspondPeriod": 7856.00,
          "change": "100%"
        },
        {
          "dept": "介入血管外科",
          "currentPeriod": 6125.38,
          "correspondPeriod": 6006.10,
          "change": "100%"
        },
        {
          "dept": "神经外科",
          "currentPeriod": 2298.36,
          "correspondPeriod": 2325.45,
          "change": "100%"
        },
        {
          "dept": "康复医学科",
          "currentPeriod": 1500.09,
          "correspondPeriod": 1515.38,
          "change": "100%"
        },
        {
          "dept": "急诊科",
          "currentPeriod": 2503.25,
          "correspondPeriod": 2419.00,
          "change": "100%"
        },
        {
          "dept": "儿科",
          "currentPeriod": 1386.23,
          "correspondPeriod": 1234.00,
          "change": "100%"
        }
      ],
      dataList2: [
        {
          "target": "总费用（万元",
          "currentPeriod": 95666.12,
          "correspondPeriod": 95648.45,
          "change": "100%"
        },
        {
          "target": "例均费用（万元）",
          "currentPeriod": 2089.56,
          "correspondPeriod": 2154.58,
          "change": "100%"
        }
      ]
    };
  },
  created() {
    this.$nextTick(() => {
      this.initChart(this.dataList1, 'chartContainer1', '科室组总费用', ['dept', 'currentPeriod', 'correspondPeriod'])
      this.initChart(this.dataList2, 'chartContainer2', '例均费用', ['target', 'currentPeriod', 'correspondPeriod'])
    })
  },
  computed: {},
  mounted() {
  },
  methods: {
    handleClick(tab, event) {

    },
    calPercent(val1, val2){
      if(val1 - val2 > 0){
        return ((val1 - val2)/val1*100).toFixed(2)
      }else{
        return ((val2 - val1)/val2*100).toFixed(2)
      }
    },
    initChart(data, chartRef, chartTitle, axisArr) {
      let myChart = this.$echarts.init(this.$refs[chartRef], null, {
        renderer: 'canvas',
        useDirtyRect: false
      });
      // 绘制图表
      myChart.setOption({
        title: {
          text: chartTitle
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
          data: data.map(obj => {return obj[axisArr[0]]}).reverse()
        },
        color: ['#66b3ff', '#91cc75'],
        series: [
          {
            name: '2023',
            type: 'bar',
            data: data.map(obj => {return obj[axisArr[1]]}).reverse()
          },
          {
            name: '2022',
            type: 'bar',
            data: data.map(obj => {return obj[axisArr[2]]}).reverse()
          }
        ],
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

