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
        <el-form-item label="方式类型:" prop="opTag">
          <el-select class="search_w220" v-model="queryParams.balanceTag" clearable placeholder="请选择方式类型">
            <el-option
              v-for="dict in balanceTagList"
              :key="dict.balanceTagValue"
              :label="dict.balanceTagLabel"
              :value="dict.balanceTagValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否包含中心收入:" prop="isReck">
          <el-select class="search_w220" v-model="queryParams.isReck" clearable placeholder="请选择是否包含中心收入">
            <el-option
              v-for="dict in isReckList"
              :key="dict.isReckValue"
              :label="dict.isReckLabel"
              :value="dict.isReckValue"
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
    <el-table
      border
      :height="tableHeight" stripe v-loading="loading" :data="deptList" ref="multipleTable">
      <el-table-column align="center" prop="deptName" label="科室" fixed width="100" />
      <el-table-column align="center" prop="hj" label="总收入" fixed width="120" />
      <el-table-column align="center" label="住院">
        <el-table-column align="center" label="小计" prop="zyhj"></el-table-column>
        <el-table-column align="center" label="实际收入" prop="zyss"></el-table-column>
        <el-table-column align="center" label="计价收入" prop="zyjd"></el-table-column>
      </el-table-column>
      <el-table-column align="center" label="门诊">
        <el-table-column align="center" label="小计" prop="mzhj"></el-table-column>
        <el-table-column align="center" label="实际收入" prop="mzss"></el-table-column>
        <el-table-column align="center" label="计价收入" prop="mzjd"></el-table-column>
      </el-table-column>
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
import {  listDeptarList,exportDetail} from "@/api/table/deptar";
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
        opTag: "1",
        isReck: "1",
        endTime: null
      },
      deptList: [],
      deptMesList: [],
      balanceTagList: [
        { balanceTagLabel: '责权发生', balanceTagValue: '1',},
        { balanceTagLabel: '收付实现', balanceTagValue: '0',},
      ],
      isReckList: [
        { isReckLabel: '是', isReckValue: '1',},
        { isReckLabel: '否', isReckValue: '0',},
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
    // /** 表头合并控制 */
    // spanHeader2({ row, column, rowIndex, columnIndex }) {
    //   let comStyle = {};
    //   // 1.1 让第0行的第0列跨2行
    //   if (rowIndex === 0) {
    //     row[0].colSpan = 0; // 将表头第一列和第二列合并，内容展示为第二列的内容
    //     row[1].colSpan = 2;
    //     if (columnIndex === 0) { // 将表头第一列隐藏
    //       return {
    //         display: "none",
    //       };
    //     }
    //   }
    //   return comStyle;
    // },
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
      this.queryParams.settleMark = '1'
      this.queryParams.type = '1'
      this.queryParams.opTag = '1'
      this.queryParams.isReck = '1'
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
      listDeptarList(this.queryParams).then(response => {
        this.deptList = response.rows;
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
