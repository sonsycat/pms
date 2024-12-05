<template>
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" :rules="rules">
        <el-form-item label="起始年月" prop="dateTime">
          <el-date-picker style="width:250px" clearable
                          v-model="queryParams.dateTime"
                          type="daterange"
                          range-separator="至"
                          start-placeholder="开始月份"
                          end-placeholder="结束月份">
            value-format="yyyy-MM-dd"
          </el-date-picker>
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
            type="warning"
            icon="el-icon-upload2"
            size="mini"
            :loading="exportLoading"
            @click="handleExportMes"
            v-hasPermi="['bonus:opc:exportDetail']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <el-table border :height="tableHeight" stripe v-loading="loading" :data="opcList" ref="multipleTable"    >
      <el-table-column fixed sortable label="科室名称" align="center" prop="deptName" width="160"/>
      <el-table-column sortable label="合计" align="center" prop="total" width="160"/>
      <el-table-column sortable label="西药费" align="center" prop="xyFee" width="160"/>
      <el-table-column sortable label="中成药" align="center" prop="zyFee" width="160"/>
      <el-table-column sortable label="草药费" align="center" prop="cyFee" width="160"/>
      <el-table-column sortable label="特殊材料费" align="center" prop="tsclFee" width="160"/>
      <el-table-column sortable label="诊查费" align="center" prop="zcFee" width="160"/>
      <el-table-column sortable label="挂号费" align="center" prop="ghFee" width="160"/>
      <el-table-column sortable label="体检费" align="center" prop="tjFee" width="160"/>
      <el-table-column sortable label="救护车费" align="center" prop="jhcFee" width="160"/>
      <el-table-column sortable label="取暖费" align="center" prop="qnFee" width="160"/>
      <el-table-column sortable label="床位费" align="center" prop="cwFee" width="160"/>
      <el-table-column sortable label="会诊费" align="center" prop="hzFee" width="160"/>
      <el-table-column sortable label="护理费" align="center" prop="hlFee" width="160"/>
      <el-table-column sortable label="抢救费" align="center" prop="qjFee" width="160"/>
      <el-table-column sortable label="注射费" align="center" prop="zsFee" width="160"/>
      <el-table-column sortable label="治疗费" align="center" prop="zlFee" width="160"/>
      <el-table-column sortable label="检查费" align="center" prop="jcFee" width="160"/>
      <el-table-column sortable label="输血费" align="center" prop="sxFee" width="160"/>
      <el-table-column sortable label="彩超费" align="center" prop="ccFee" width="160"/>
      <el-table-column sortable label="放射费" align="center" prop="fsFee" width="160"/>
      <el-table-column sortable label="检验费" align="center" prop="jyFee" width="160"/>
      <el-table-column sortable label="病理费" align="center" prop="blFee" width="160"/>
      <el-table-column sortable label="脑电图费" align="center" prop="ndtFee" width="160"/>
      <el-table-column sortable label="肌电图费" align="center" prop="jdtFee" width="160"/>
      <el-table-column sortable label="肺功能检查" align="center" prop="fgnFee" width="160"/>
      <el-table-column sortable label="心电图费" align="center" prop="xdtFee" width="160"/>
      <el-table-column sortable label="胃镜费" align="center" prop="wjFee" width="160"/>
      <el-table-column sortable label="麻醉费" align="center" prop="mzFee" width="160"/>
      <el-table-column sortable label="手术费" align="center" prop="shFee" width="160"/>
      <el-table-column sortable label="其它费" align="center" prop="otherFee" width="160"/>
      <el-table-column sortable label="CT费" align="center" prop="ctFee" width="160"/>
      <el-table-column sortable label="康复费" align="center" prop="kfFee" width="160"/>
      <el-table-column sortable label="急诊科检查费" align="center" prop="jzkjcFee" width="160"/>
      <el-table-column sortable label="ICU检查费" align="center" prop="icujcFee" width="160"/>
      <el-table-column sortable label="内一科检查费" align="center" prop="nykjcFee" width="160"/>
      <el-table-column sortable label="内二科检查费" align="center" prop="nekjcFee" width="160"/>
      <el-table-column sortable label="体检科检查费" align="center" prop="tjkjcFee" width="160"/>
      <el-table-column sortable label="五官科检查费" align="center" prop="wgkjcFee" width="160"/>
      <el-table-column sortable label="妇产科检查费" align="center" prop="fckjcFee" width="160"/>
      <el-table-column sortable label="外二科检查费" align="center" prop="wekjcFee" width="160"/>
      <el-table-column sortable label="感染科检查费" align="center" prop="grkjcFee" width="160"/>
      <el-table-column sortable label="氧气费" align="center" prop="yqFee" width="160"/>
      <el-table-column sortable label="血材料费" align="center" prop="xclFee" width="160"/>

    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {  listBihList,exportDetail} from "@/api/table/bih";
export default {
  name: "UreportDisplay",
  components: {
  },
  data() {
    return {
      tableHeight: "auto",
      exportLoading: false,
      src: "",
      height: document.documentElement.clientHeight - 210 + "px;",
      loading: true,
      queryParams: {
        dateTime : [new Date(),new Date()],
        startTime: null,
        endTime: null
      },
      opcList: [],
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      rules: {
      },
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
  methods:{
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.dateTime = [new Date(),new Date()]
      this.queryParams.settleMark = '1'
      this.queryParams.type = '1'
    },
    /** 搜索按钮操作 */
    handleQuery() {
      var startTime = null;
      let endTime = null;
      var nowDate = this.queryParams.dateTime[0];
      if (nowDate.length != 10) {
        var year = nowDate.getFullYear();
        var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
        var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
        startTime = year +"-"+ month +"-"+ day ;
        nowDate = this.queryParams.dateTime[1];
        var year = nowDate.getFullYear();
        var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
        var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
        endTime = year +"-"+ month +"-"+ day ;
      }
      this.queryParams.pageNum = 1;
      this.queryParams.startTime = startTime;
      this.queryParams.endTime = endTime;
      this.getList();
    },
    getList(){
      this.loading = true;
      listBihList(this.queryParams).then(response => {
        this.opcList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    removeMask() {
      this.loading = false;
    },
    /** 导出按钮操作 */
    handleExportMes() {
      const queryParams = this.queryParams;
      var startTime = null;
      let endTime = null;
      var nowDate = this.queryParams.dateTime[0];
      if (nowDate.length != 10) {
        var year = nowDate.getFullYear();
        var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
        var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
        startTime = year +"-"+ month +"-"+ day ;
        nowDate = this.queryParams.dateTime[1];
        var year = nowDate.getFullYear();
        var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
        var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
        endTime = year +"-"+ month +"-"+ day ;
      }
      this.queryParams.startTime = startTime;
      this.queryParams.endTime = endTime;
      this.$confirm('是否确认导出所有数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.exportLoading = true;
        return exportDetail(queryParams);
      }).then(response => {
        this.download(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    },
  }
}
</script>
