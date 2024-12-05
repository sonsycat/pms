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
      <el-form-item label="方式类型:" prop="balanceTag">
        <el-select class="search_w220" v-model="queryParams.balanceTag" clearable placeholder="请选择方式类型">
          <el-option
            v-for="dict in balanceTagList"
            :key="dict.balanceTagValue"
            :label="dict.balanceTagLabel"
            :value="dict.balanceTagValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="科室类别:" prop="balanceTag">
        <el-select class="search_w220" v-model="queryParams.deptType" clearable placeholder="请选择科室类别">
          <el-option
            v-for="dict in deptTypeList"
            :key="dict.deptTypeValue"
            :label="dict.deptTypeLabel"
            :value="dict.deptTypeValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="收费类型:" prop="balanceTag">
        <el-select class="search_w220" v-model="queryParams.feeType" clearable placeholder="请选择收费类型">
          <el-option
            v-for="dict in feeList"
            :key="dict.feeValue"
            :label="dict.feeLabel"
            :value="dict.feeValue"
          ></el-option>
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
      <el-table-column sortable label="人力成本" align="center" prop="rlcb" width="160"/>
      <el-table-column sortable label="卫生材料" align="center" prop="wscl" width="160"/>
      <el-table-column sortable label="手术室材料" align="center" prop="ssscl" width="160"/>
      <el-table-column sortable label="消毒材料费" align="center" prop="xdclf" width="160"/>
      <el-table-column sortable label="低值易耗" align="center" prop="dzyh" width="160"/>
      <el-table-column sortable label="西药费" align="center" prop="xyf" width="160"/>
      <el-table-column sortable label="中成药" align="center" prop="zcf" width="160"/>
      <el-table-column sortable label="中草药" align="center" prop="zyf" width="160"/>
      <el-table-column sortable label="折旧费" align="center" prop="zjf" width="160"/>
      <el-table-column sortable label="房屋折旧费" align="center" prop="fwzjf" width="160"/>
      <el-table-column sortable label="资产处置费用" align="center" prop="zcczf" width="160"/>
      <el-table-column sortable label="医疗风险基金" align="center" prop="ylfxjj" width="160"/>
      <el-table-column sortable label="医疗设备贷款利息" align="center" prop="ylsbdk" width="160"/>
      <el-table-column sortable label="维修(护)费" align="center" prop="wxhf" width="160"/>
      <el-table-column sortable label="医疗设备考察费" align="center" prop="ylsbkcf" width="160"/>
      <el-table-column sortable label="差旅费" align="center" prop="clf" width="160"/>
      <el-table-column sortable label="被服洗涤费" align="center" prop="bfxdf" width="160"/>
      <el-table-column sortable label="水费" align="center" prop="sf" width="160"/>
      <el-table-column sortable label="电梯使用费" align="center" prop="dtsyf" width="160"/>
      <el-table-column sortable label="电费" align="center" prop="df" width="160"/>
      <el-table-column sortable label="设备修理费" align="center" prop="sbxlf" width="160"/>
      <el-table-column sortable label="邮电费" align="center" prop="ydf" width="160"/>
      <el-table-column sortable label="医杂费" align="center" prop="yzf" width="160"/>
      <el-table-column sortable label="印刷费" align="center" prop="ysf" width="160"/>
      <el-table-column sortable label="保洁费" align="center" prop="bjf" width="160"/>
      <el-table-column sortable label="医用垃圾处理费" align="center" prop="yyljclf" width="160"/>
      <el-table-column sortable label="科研课题费" align="center" prop="kykqf" width="160"/>
      <el-table-column sortable label="氧气费" align="center" prop="yqf" width="160"/>
      <el-table-column sortable label="管理费" align="center" prop="glf" width="160"/>
      <el-table-column sortable label="其它支出" align="center" prop="qtzc" width="160"/>
      <el-table-column sortable label="取暖费" align="center" prop="qnf" width="160"/>
      <el-table-column sortable label="污水处理费" align="center" prop="wsclf" width="160"/>
      <el-table-column sortable label="医疗设备修理费" align="center" prop="ylsbxlf" width="160"/>
      <el-table-column sortable label="仪器配件费" align="center" prop="yqpjf" width="160"/>
      <el-table-column sortable label="器械费" align="center" prop="qxf" width="160"/>
      <el-table-column sortable label="办公费" align="center" prop="bgf" width="160"/>
      <el-table-column sortable label="其他交通费" align="center" prop="qtjtf" width="160"/>
      <el-table-column sortable label="培训费" align="center" prop="pxf" width="160"/>
      <el-table-column sortable label="会议接待费" align="center" prop="hyjdf" width="160"/>
      <el-table-column sortable label="劳务费" align="center" prop="lwf" width="160"/>
      <el-table-column sortable label="福利费" align="center" prop="flf" width="160"/>
      <el-table-column sortable label="其他材料" align="center" prop="qtcl" width="160"/>
      <el-table-column sortable label="工会经费" align="center" prop="ghjf" width="160"/>
      <el-table-column sortable label="维修费" align="center" prop="wxf" width="160"/>
      <el-table-column sortable label="银行手续费" align="center" prop="yhsxf" width="160"/>
      <el-table-column sortable label="租赁费" align="center" prop="zlf" width="160"/>
      <el-table-column sortable label="其他一般商品服务支出" align="center" prop="qtspfw" width="160"/>
      <el-table-column sortable label="会务费" align="center" prop="hwf" width="160"/>
      <el-table-column sortable label="宣传品" align="center" prop="xcp" width="160"/>
      <el-table-column sortable label="物业管理费" align="center" prop="wyglf" width="160"/>
      <el-table-column sortable label="利息费用" align="center" prop="lxf" width="160"/>
      <el-table-column sortable label="会议费" align="center" prop="hyf" width="160"/>
      <el-table-column sortable label="委托业务费" align="center" prop="wtywf" width="160"/>
      <el-table-column sortable label="其他材料费用" align="center" prop="qtclf" width="160"/>
      <el-table-column sortable label="汽油费" align="center" prop="qyf" width="160"/>
      <el-table-column sortable label="无形资产摊销" align="center" prop="wxzctx" width="160"/>
      <el-table-column sortable label="被服购置费" align="center" prop="bfgzf" width="160"/>
      <el-table-column sortable label="电脑设备、耗材购置费" align="center" prop="dnhcf" width="160"/>
      <el-table-column sortable label="医用表格费" align="center" prop="yybgf" width="160"/>
      <el-table-column sortable label="总务材料" align="center" prop="zwcl" width="160"/>
      <el-table-column sortable label="血费" align="center" prop="xf" width="160"/>


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
import {  listCostList,exportDetail} from "@/api/table/cost";
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
        balanceTag: "1",
        feeType: "1",
        endTime: null
      },
      opcList: [],
      balanceTagList: [
        { balanceTagLabel: '责权发生', balanceTagValue: '1',},
        { balanceTagLabel: '收付实现', balanceTagValue: '0',},
      ],
      deptTypeList: [
        { deptTypeLabel: '机关科室', deptTypeValue: '0',},
        { deptTypeLabel: '临床科室', deptTypeValue: '1',},
        { deptTypeLabel: '医技科室', deptTypeValue: '2',},
        { deptTypeLabel: '医辅科室', deptTypeValue: '3',},
        { deptTypeLabel: '护理单元', deptTypeValue: '4',},
        { deptTypeLabel: '其他科室', deptTypeValue: '5',},
      ],
      feeList: [
        { feeLabel: '全部', feeValue: '1',},
        { feeLabel: '实收', feeValue: '0',},
        { feeLabel: '计价', feeValue: '2',},

      ],
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
      this.queryParams.balanceTag = '1'
      this.queryParams.feeType = '1'
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
      listCostList(this.queryParams).then(response => {
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
