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
      <el-table-column sortable label="类别" align="center" prop="deptType" width="160"/>
      <el-table-column sortable label="合计" align="center" prop="total" width="160"/>
      <el-table-column sortable label="药品" align="center" prop="yp" width="160"/>
      <el-table-column sortable label="检验" align="center" prop="jy" width="160"/>
      <el-table-column sortable label="检查" align="center" prop="jc" width="160"/>
      <el-table-column sortable label="治疗" align="center" prop="zl" width="160"/>
      <el-table-column sortable label="放射" align="center" prop="fs" width="160"/>
      <el-table-column sortable label="手术" align="center" prop="ss" width="160"/>
      <el-table-column sortable label="输血" align="center" prop="sx" width="160"/>
      <el-table-column sortable label="护理" align="center" prop="hl" width="160"/>
      <el-table-column sortable label="挂号" align="center" prop="gh" width="160"/>
      <el-table-column sortable label="床位" align="center" prop="cw" width="160"/>
      <el-table-column sortable label="其它" align="center" prop="qt" width="160"/>
      <el-table-column sortable label="治疗收入" align="center" prop="zlsr" width="160"/>
      <el-table-column sortable label="诊疗费" align="center" prop="zlf" width="160"/>
      <el-table-column sortable label="麻醉" align="center" prop="mz" width="160"/>
      <el-table-column sortable label="中心" align="center" prop="zx" width="160"/>
      <el-table-column sortable label="材料" align="center" prop="cl" width="160"/>

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
import {  listIncsumList,exportDetail} from "@/api/table/incsum";
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
      listIncsumList(this.queryParams).then(response => {
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
