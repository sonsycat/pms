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
      <el-form-item label="科室名称" prop="deptCode">
        <el-select v-model="queryParams.deptCode" clearable class="w100" filterable size="small">
          <el-option
            v-for="item in deptMesList"
            :key="item.deptRoomId"
            :label="item.deptRoomName"
            :value="item.deptRoomId">
          </el-option>
        </el-select>
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
      <el-form-item label="费用类型:" prop="balanceTag">
        <el-select class="search_w220" v-model="queryParams.feeType" clearable placeholder="请选择费用类型">
          <el-option
            v-for="dict in feeTypeList"
            :key="dict.feeTypeValue"
            :label="dict.feeTypeLabel"
            :value="dict.feeTypeValue"
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
      <el-table-column fixed sortable label="医生" align="center" prop="docName" width="160"/>
      <el-table-column sortable label="合计" align="center" prop="total" width="160"/>
      <el-table-column sortable label="西药费" align="center" prop="xyf" width="160"/>
      <el-table-column sortable label="中成药费" align="center" prop="zchyf" width="160"/>
      <el-table-column sortable label="中草药费" align="center" prop="zcyf" width="160"/>
      <el-table-column sortable label="挂号费" align="center" prop="ghf" width="160"/>
      <el-table-column sortable label="诊察费" align="center" prop="zcf" width="160"/>
      <el-table-column sortable label="检查费" align="center" prop="jcf" width="160"/>
      <el-table-column sortable label="化验费" align="center" prop="hyf" width="160"/>
      <el-table-column sortable label="常规类" align="center" prop="cgl" width="160"/>
      <el-table-column sortable label="生化类" align="center" prop="shl" width="160"/>
      <el-table-column sortable label="免疫类" align="center" prop="myl" width="160"/>
      <el-table-column sortable label="细菌类" align="center" prop="xjl" width="160"/>
      <el-table-column sortable label="电诊费" align="center" prop="dzf" width="160"/>
      <el-table-column sortable label="心电" align="center" prop="xd" width="160"/>
      <el-table-column sortable label="动态心电" align="center" prop="dtxd" width="160"/>
      <el-table-column sortable label="脑电" align="center" prop="nd" width="160"/>
      <el-table-column sortable label="动态脑电" align="center" prop="dtnd" width="160"/>
      <el-table-column sortable label="B超" align="center" prop="bc" width="160"/>
      <el-table-column sortable label="彩超" align="center" prop="cc" width="160"/>
      <el-table-column sortable label="碎石" align="center" prop="ss" width="160"/>
      <el-table-column sortable label="放射费" align="center" prop="fsf" width="160"/>
      <el-table-column sortable label="透视费" align="center" prop="tsf" width="160"/>
      <el-table-column sortable label="造影费" align="center" prop="zyf" width="160"/>
      <el-table-column sortable label="拍片费" align="center" prop="ppf" width="160"/>
      <el-table-column sortable label="病理费" align="center" prop="blf" width="160"/>
      <el-table-column sortable label="组织类" align="center" prop="zzl" width="160"/>
      <el-table-column sortable label="宫颈活检" align="center" prop="gjhj" width="160"/>
      <el-table-column sortable label="细胞类" align="center" prop="zbl" width="160"/>
      <el-table-column sortable label="液基费" align="center" prop="yjf" width="160"/>
      <el-table-column sortable label="CT费" align="center" prop="ctf" width="160"/>
      <el-table-column sortable label="头部CT" align="center" prop="topbct" width="160"/>
      <el-table-column sortable label="体部CT" align="center" prop="tbct" width="160"/>
      <el-table-column sortable label="核磁费" align="center" prop="hcf" width="160"/>
      <el-table-column sortable label="头部核磁" align="center" prop="topbhc" width="160"/>
      <el-table-column sortable label="体部核磁" align="center" prop="tbhc" width="160"/>
      <el-table-column sortable label="镜检费" align="center" prop="jjf" width="160"/>
      <el-table-column sortable label="胃镜" align="center" prop="wj" width="160"/>
      <el-table-column sortable label="肠镜" align="center" prop="cj" width="160"/>
      <el-table-column sortable label="体检费" align="center" prop="tjc" width="160"/>
      <el-table-column sortable label="治疗费" align="center" prop="zlf" width="160"/>
      <el-table-column sortable label="输氧费" align="center" prop="syf" width="160"/>
      <el-table-column sortable label="输血费" align="center" prop="sxf" width="160"/>
      <el-table-column sortable label="换药处置费" align="center" prop="hyczf" width="160"/>
      <el-table-column sortable label="静点费" align="center" prop="jdf" width="160"/>
      <el-table-column sortable label="静注费" align="center" prop="jzfee" width="160"/>
      <el-table-column sortable label="肌注费" align="center" prop="jzf" width="160"/>
      <el-table-column sortable label="采血费" align="center" prop="cxf" width="160"/>
      <el-table-column sortable label="注射费" align="center" prop="zsf" width="160"/>
      <el-table-column sortable label="透析费" align="center" prop="txf" width="160"/>
      <el-table-column sortable label="高压氧" align="center" prop="gyf" width="160"/>
      <el-table-column sortable label="灌肠费" align="center" prop="gcf" width="160"/>
      <el-table-column sortable label="导尿费" align="center" prop="ndf" width="160"/>
      <el-table-column sortable label="穿刺费" align="center" prop="ccf" width="160"/>
      <el-table-column sortable label="理疗费" align="center" prop="llf" width="160"/>
      <el-table-column sortable label="针灸费" align="center" prop="zjf" width="160"/>
      <el-table-column sortable label="按摩费" align="center" prop="amf" width="160"/>
      <el-table-column sortable label="人流费" align="center" prop="rlf" width="160"/>
      <el-table-column sortable label="上环费" align="center" prop="shf" width="160"/>
      <el-table-column sortable label="取环费" align="center" prop="qhf" width="160"/>
      <el-table-column sortable label="处置费" align="center" prop="clf" width="160"/>
      <el-table-column sortable label="麻醉费" align="center" prop="mzf" width="160"/>
      <el-table-column sortable label="手术费" align="center" prop="ssf" width="160"/>
      <el-table-column sortable label="接生类" align="center" prop="jsf" width="160"/>
      <el-table-column sortable label="护理费" align="center" prop="hlf" width="160"/>
      <el-table-column sortable label="抢救观察" align="center" prop="qjgc" width="160"/>
      <el-table-column sortable label="留观费" align="center" prop="lgf" width="160"/>
      <el-table-column sortable label="床位费" align="center" prop="cwf" width="160"/>
      <el-table-column sortable label="取暖费" align="center" prop="qnf" width="160"/>
      <el-table-column sortable label="垃圾费" align="center" prop="ljf" width="160"/>
      <el-table-column sortable label="其他费" align="center" prop="qtf" width="160"/>
      <el-table-column sortable label="卫材费" align="center" prop="wcf" width="160"/>
      <el-table-column sortable label="监护费" align="center" prop="jhf" width="160"/>
      <el-table-column sortable label="会诊费" align="center" prop="hzf" width="160"/>
      <el-table-column sortable label="输注器" align="center" prop="szq" width="160"/>
      <el-table-column sortable label="洗胃费" align="center" prop="xwf" width="160"/>
      <el-table-column sortable label="试敏费" align="center" prop="smf" width="160"/>

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
import {  listDocList,exportDetail} from "@/api/table/doc";
import { treeRoom } from "@/api/until";
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
        deptCode: null,
        feeType: null,
        balanceTag: "1",
        endTime: null
      },
      opcList: [],
      deptMesList: [],
      balanceTagList: [
        { balanceTagLabel: '责权发生', balanceTagValue: '1',},
        { balanceTagLabel: '收付实现', balanceTagValue: '0',},
      ],
      feeTypeList: [
        { feeTypeLabel: '门诊', feeTypeValue: '1',},
        { feeTypeLabel: '住院', feeTypeValue: '0',},
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
    "form.compCode": {
      handler(val) {
        this.getTreeDept(val);
      },
      deep: true,
      immediate: true
    },
    listData(val) {
      var that = this;
      setTimeout(function() {
        that.resizeHandler();
      }, 300);
    }
  },
  methods:{
    //获取科室列表
    getTreeDept(deptId) {
      let data = {
        deptId: this.$store.state.user.userInfo.deptId
      };
      this.formDeptList = [];
      treeRoom(data).then(response => {
        if (response.code == 200) {
          this.deptMesList = response.rows;
        } else {
          this.msgError(response.msg);
        }
      });
    },
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
      this.queryParams.balanceTag = '1'
      this.queryParams.deptCode = null
      this.queryParams.feeType = null
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
      listDocList(this.queryParams).then(response => {
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
