<template>
<!-- 货架 -->
    <el-form-item :label="label" :prop="formProp" :rules="rules">
        <el-popover
          placement="bottom"
          ref="popover"
          width="1000"
          trigger="click"
          :disabled = "disabled">
          <el-table  :data="tableData" @cell-click="clickRow"  border stripe height="280px">
            <el-table-column label="项目编码" align="center" prop="revenueCode" />
            <el-table-column label="项目名称" align="left" prop="revenueName" />
            <el-table-column label="项目类型" align="center" prop="revenueType" :formatter="projectTypeFormat" />
          </el-table>
          <pagination
            layout="total, prev, pager, next"
            v-show="total>0"
            :total="total"
            :page.sync="params.pageNum"
            :limit.sync="params.pageSize"
            @pagination="handleInput"
          />
          <el-input slot="reference" v-model="queryParams[setProp]" :placeholder="'请选择'+label" @input="handleInput" @keyup.enter.native="handleInput"  @focus="handleInput"/>
        </el-popover>
      </el-form-item>
</template>

<script>
//import { searchForProject } from "@/api/num/revenue";
// label 为空时  调用组件 增加class labelEmpty
export default {
  name: "index",
  props: {
    // 是否禁用
    disabled: {
      type: Boolean,
      default: false
    },
    // 传参为form表单 方便数据回显渲染
    queryParams: {
      type: [Object, Array]
    },
    datas: {
      type: Object,
      default() {
        return {};
      }
    },
    label: {
      type: String,
      default: ""
    },
    revenueCode: {
      type: [String, Number],
      default: ""
    },
    compCode: {
      type: [String, Number],
      default: ""
    },
    setProp: {
      type: String,
      default: "revenueCode"
    },
    formProp: {
      type: String,
      default: "revenueCode"
    },
    rules: {
      type: Array,
      default() {
        return [ ];
      }
    },
  },
  data() {
    return {
      // 表格数据
      tableData: [],
      formData: this.queryParams,
      params: {
        pageNum: 1,
        pageSize: this.$store.state.settings.popPageSize,
      },
      total: 0,
      projectTypeOptions:[],

    };
  },
  watch: {
    setProp: {
      handler(val) {
        if (val != "revenueCode" && this.formProp == "revenueCode") {
          this.formProp = val;
        }
      },
      deep: true,
      immediate: true
    }
  },
  created(){
     this.getDicts("cost_xm_lx").then(response => {
        this.projectTypeOptions = response.data;
      });
  },
  methods: {
      // 项目类型字典翻译
      projectTypeFormat(row, column) {
        return this.selectDictLabel(this.projectTypeOptions, row.revenueType);
      },
    // 搜索数据
    handleInput(e) {
      if(e.target){
          let searchQuery={
          compCode:this.$store.state.user.userInfo.deptId,
          deptCode: this.formData.deptCode,
          nowDate:this.formData.nowDate.split("-")[0]+'-'+this.formData.nowDate.split("-")[1],
          queryString:e.target.value
        }
        // searchForProject(searchQuery).then(response => {
        //     this.tableData = response.rows
        //     this.total = response.total;
        // });
      }

    },
    // 点击表格行
    clickRow(row, column, cell, event) {
      this.queryParams[this.setProp] = row.revenueCode;
      this.$emit("click", row);
      this.$refs.popover.doClose();
    },
  }
};
</script>

<style lang="scss" scoped>
.pagination-container {
  height: 36px !important;
}

.labelEmpty {
  ::v-deep .el-form-item {
    margin-bottom: 12px;
  }
  ::v-deep .el-form-item__content {
    margin-left: 0 !important;
  }
}
</style>
