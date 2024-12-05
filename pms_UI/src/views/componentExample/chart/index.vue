<template>
  <div style="margin-left: 100px;margin-top: 50px;margin-right: 100px">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane name="histogram" lazy>
        <span slot="label"><svg-icon icon-class="histogram"/> 柱状图</span>
        <ve-histogram :data="chartData" height="600px" style="margin-top: 50px"></ve-histogram>
      </el-tab-pane>
      <el-tab-pane name="histograms" lazy>
        <span slot="label"><svg-icon icon-class="histograms"/> 多维柱状图</span>
        <ve-histogram :data="chartData" height="600px" style="margin-top: 50px"></ve-histogram>
      </el-tab-pane>
      <el-tab-pane name="line" lazy>
        <span slot="label"><svg-icon icon-class="line"/> 折线图</span>
        <ve-line :data="chartData" height="600px" style="margin-top: 50px"></ve-line>
      </el-tab-pane>
      <el-tab-pane name="bar" lazy>
        <span slot="label"><svg-icon icon-class="bar"/> 条形图</span>
        <ve-bar :data="chartData" height="600px" style="margin-top: 50px"></ve-bar>
      </el-tab-pane>
      <el-tab-pane name="pie" lazy>
        <span slot="label"><svg-icon icon-class="pie"/> 饼图</span>
        <ve-pie :data="chartData" height="600px" style="margin-top: 50px"></ve-pie>
      </el-tab-pane>
      <el-tab-pane name="ring" lazy>
        <span slot="label"><svg-icon icon-class="ring"/> 环图</span>
        <ve-ring :data="chartData" height="600px" style="margin-top: 50px"></ve-ring>
      </el-tab-pane>
      <el-tab-pane name="funnel" lazy>
        <span slot="label"><svg-icon icon-class="funnel"/> 漏斗图</span>
        <ve-funnel :data="chartData" height="600px" style="margin-top: 50px"></ve-funnel>
      </el-tab-pane>
      <el-tab-pane name="waterfall" lazy>
        <span slot="label"><svg-icon icon-class="waterfall"/> 瀑布图</span>
        <ve-waterfall :data="chartData" height="600px" style="margin-top: 50px"></ve-waterfall>
      </el-tab-pane>
      <el-tab-pane name="radar" lazy>
        <span slot="label"><svg-icon icon-class="radar"/> 雷达图</span>
        <ve-radar :data="chartData" height="600px" style="margin-top: 50px"></ve-radar>
      </el-tab-pane>
      <el-tab-pane name="map" lazy>
        <span slot="label"><svg-icon icon-class="map"/> 地图</span>
        <ve-map :data="chartData" width="800px" height="600px" style="margin-top: 50px"></ve-map>
      </el-tab-pane>
      <el-tab-pane name="gauge" lazy>
        <span slot="label"><svg-icon icon-class="gauge"/> 仪表盘</span>
        <ve-gauge :data="chartData" height="600px" style="margin-top: 50px"></ve-gauge>
      </el-tab-pane>
      <el-tab-pane name="sanKey" lazy>
        <span slot="label"><svg-icon icon-class="sanKey"/> 桑基图</span>
        <ve-san-key :data="chartData" height="600px" style="margin-top: 50px"></ve-san-key>
      </el-tab-pane>
      <el-tab-pane name="tree" lazy>
        <span slot="label"><svg-icon icon-class="tree"/> 树图</span>
        <ve-tree :data="chartData" height="600px" style="margin-top: 50px"></ve-tree>
      </el-tab-pane>
      <el-tab-pane name="heatMap" lazy>
        <span slot="label"><svg-icon icon-class="heatMap"/> 热力图</span>
        <ve-heat-map :data="chartData" height="600px" style="margin-top: 50px"></ve-heat-map>
      </el-tab-pane>
      <el-tab-pane name="scatter" lazy>
        <span slot="label"><svg-icon icon-class="scatter"/> 散点图</span>
        <ve-scatter :data="chartData" height="600px" style="margin-top: 50px"></ve-scatter>
      </el-tab-pane>
      <el-tab-pane name="candle" lazy>
        <span slot="label"><svg-icon icon-class="candle"/> K线图</span>
        <ve-candle :data="chartData" height="600px" style="margin-top: 50px"></ve-candle>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import VeLine from 'v-charts/lib/line'
  import VeHistogram from 'v-charts/lib/histogram'
  import VeBar from 'v-charts/lib/bar'
  import VePie from 'v-charts/lib/pie'
  import VeRing from 'v-charts/lib/ring'
  import VeFunnel from 'v-charts/lib/funnel'
  import VeWaterfall from 'v-charts/lib/waterfall'
  import VeRadar from 'v-charts/lib/radar'
  import VeMap from 'v-charts/lib/map'
  import VeGauge from 'v-charts/lib/gauge'
  import VeSanKey from 'v-charts/lib/sankey'
  import VeTree from 'v-charts/lib/tree'
  import VeHeatMap from 'v-charts/lib/heatmap'
  import VeScatter from 'v-charts/lib/scatter'
  import VeCandle from 'v-charts/lib/candle'
  // v-charts的无数据和loading的样式是单独的,需要引入css
  import 'v-charts/lib/style.css'
  import { getChartData } from '@/api/demo/componentExample'

  export default {
    name: 'VCharts',
    components: {
      VeLine, VeHistogram, VeBar, VePie, VeRing, VeFunnel, VeWaterfall, VeRadar, VeMap, VeGauge, VeSanKey,
      VeTree, VeHeatMap, VeScatter, VeCandle
    },
    data() {
      return {
        // v-charts使用请参考https://v-charts.js.org/
        // 私有属性(图表自身的属性)
        chartSettings: {},
        // 详细属性(对已配置好的内部属性进行单独的设置)
        chartExtend: {},
        // 数据
        chartData: {},
        // 默认选项卡
        activeName: 'histogram',
        // chart所有数据
        chartDataList: {}
      }
    }
    ,
    created() {
      // 从后台获取图表数据
      this.getData()
    }
    ,
    methods: {
      // 从后台获取所有图表数据并给柱形图赋值
      getData() {
        getChartData().then(response => {
          this.chartDataList = response.data
          this.chartData = this.chartDataList.histogram
        })
      }
      ,
      // tab切换点击事件
      handleClick(tab, event) {
        this.chartData = this.chartDataList[tab.name]
      }
    }
  }

</script>

