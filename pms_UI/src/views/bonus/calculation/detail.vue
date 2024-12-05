<template>
  <div class="app-container" t>
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="奖金年月" prop="period">
          <el-date-picker :clearable="false" :editable="false" size="small"
                          v-model="queryParams.period" type="month"
                          value-format="yyyy-MM-dd" placeholder="选择奖金年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          <!--        <el-button type="info" icon="el-icon-back" size="mini" @click="goBack">返回</el-button>-->
        </el-form-item>
      </el-form>
    </div>
    <div ref="btns">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="info"
            icon="el-icon-back"
            size="mini"
            :loading="exportLoading"
            @click="goBack"
          >返回
          </el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>
    <div style="text-align: center">
      <div class="el-table" style="width: 60%;margin-left: calc((100vw - 200px)*0.4/2)">
        <div class="el-table__header-wrapper">
          <table cellspacing="0" cellpadding="0" border="0" class="el-table__header" style="width: 100%;">
            <thead class="has-gutter">
            <tr class="">
              <th colspan="1" rowspan="1" class="is-leaf">
                <div class="cell">项目</div>
              </th>
              <th colspan="1" rowspan="1" class="is-leaf">
                <div class="cell">内容</div>
              </th>
            </tr>
            </thead>
          </table>
        </div>
        <div class="el-table__body-wrapper is-scrolling-none" style="height: auto;">
          <table cellspacing="0" cellpadding="0" border="0" class="el-table__body" style="width: 100%;">
            <tbody>
            <tr class="el-table__row">
              <td rowspan="1" colspan="1">
                <div class="cell c-title">考核月份</div>
              </td>
              <td rowspan="1" colspan="1">
                <div class="cell c-content">{{ formatDateOfZh }}</div>
              </td>
            </tr>
            <tr class="el-table__row el-table__row--striped">
              <td rowspan="1" colspan="1">
                <div class="cell c-title">开始时间</div>
              </td>
              <td rowspan="1" colspan="1">
                <div class="cell c-content">{{ detailInfo.dateBegin }}</div>
              </td>
            </tr>
            <tr class="el-table__row">
              <td rowspan="1" colspan="1">
                <div class="cell c-title">结束时间</div>
              </td>
              <td rowspan="1" colspan="1">
                <div class="cell c-content">{{ detailInfo.dateEnd }}</div>
              </td>
            </tr>
            <tr class="el-table__row el-table__row--striped">
              <td rowspan="1" colspan="1">
                <div class="cell c-title">独立核算科室奖金</div>
              </td>
              <td rowspan="1" colspan="1">
                <div class="cell c-content" style="text-align: right">
                  <el-link type="primary" :underline="false" @click="bonusAloneDetail()" class="displayBlock text-ellipsis ">{{ detailInfo.bonusAlone }}
                  </el-link>
                </div>
              </td>
            </tr>
            <tr class="el-table__row">
              <td rowspan="1" colspan="1">
                <div class="cell c-title">平均奖科室奖金</div>
              </td>
              <td rowspan="1" colspan="1">
                <div class="cell c-content" style="text-align: right">
                  <el-link type="primary" :underline="false" @click="bonusAverageDetail()" class="displayBlock text-ellipsis ">{{ detailInfo.bonusAverage }}
                  </el-link>
                </div>
              </td>
            </tr>
            <tr class="el-table__row el-table__row--striped">
              <td rowspan="1" colspan="1">
                <div class="cell c-title">总奖金</div>
              </td>
              <td rowspan="1" colspan="1">
                <div class="cell c-content" style="text-align: right">{{ detailInfo.bonusTotal }}</div>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {bonusDetail} from "@/api/bonus/calculation";

export default {
  name: "Calculation",
  id: {
    type: [String, Number],
    default: ""
  },
  data() {
    return {
      // 遮罩层
      loading: true,
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
      // 奖金核算表格数据
      detailInfo: {
        period: '',
        dateBegin: '',
        dateEnd: '',
        bonusAlone: '',
        bonusAverage: '',
        bonusTotal: '',
      },
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        period: this.$route.params.period,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  computed: {
    // 计算属性的 getter
    formatDateOfZh: function () {
      // `this` 指向 vm 实例
      if (this.detailInfo.period) {
        return this.formatDate(this.detailInfo.period);
      }
      return '';
    }
  },
  methods: {
    /** 查询奖金核算列表 */
    getList() {
      this.loading = true;
      bonusDetail(this.queryParams.period).then(response => {
        this.detailInfo = response.data;
        this.queryParams.period = this.detailInfo.period+"-01";
        if(response.msg){
          this.detailInfo.bonusAlone = '--';
          this.detailInfo.bonusAverage = '--';
          this.detailInfo.bonusTotal = '--';
          this.$message.error(this.formatDate(this.detailInfo.period) + response.msg);
        }
        this.loading = false;
      }).catch(err => {
        this.loading = false;
      });
    },
    successSubmit(flag) {
      this.open = flag;
      this.getList();
    },
    formatDate(dateStr){
      let date = new Date(Date.parse(dateStr.replace(/-/g, "/")));
      return date.getFullYear() + '年' + (date.getMonth() + 1) + '月';
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
      this.selectionRow = selection
    },
    rowClick(row, column, event) {
      let refsElTable = this.$refs.multipleTable;
      let findRow = this.selectionRow.find(c => c.rowIndex == row.rowIndex);
      if (findRow && this.selectionRow.length == 1) {
        refsElTable.toggleRowSelection(row, false);
        return;
      }
      refsElTable.clearSelection();
      refsElTable.toggleRowSelection(row);
    },
    rowStyle({row, rowIndex}) {
      Object.defineProperty(row, 'rowIndex', {
        value: rowIndex,
        writable: true,
        enumerable: false
      })
    },
    rowClassName({row, rowIndex}) {
      let rowName = "",
        findRow = this.selectionRow.find(c => c.rowIndex === row.rowIndex);
      if (findRow) {
        rowName = "current-row ";
      }
      return rowName;
    },
    // 返回
    goBack() {
      // 关闭当前页面
      this.$store.dispatch("tagsView/delView", this.$route);
      // 返回上一页面
      this.$router.go(-1)
    },

    /** 独立核算科室奖金详情 */
    bonusAloneDetail() {
      if(isNaN(this.detailInfo.bonusAlone)){
        return;
      }
      const deptType = '0'
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.push("/deptBonus/index/" + deptType + "/" + this.queryParams.period);
    },
    /** 平均奖科室奖金详情 */
    bonusAverageDetail() {
      if(isNaN(this.detailInfo.bonusAverage)){
        return;
      }
      const deptType = '1'
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.push("/deptBonus/index/" + deptType + "/" + this.queryParams.period);
    },
  }
};
</script>
<style lang="scss" scoped>
::v-deep .c-title {
  font-size: 14px;
  font-weight: bold;
  text-align: left;
}

.c-content {
  font-size: 15px;
  color: #007bff;
  font-weight: bold;
}
</style>
