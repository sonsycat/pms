<template>
<!-- 货架 -->
    <el-form-item :label="label" :prop="formProp" :rules="rules" >
        <el-popover
          placement="bottom"
          ref="popover"
          width="1000"
          trigger="click"
          :disabled = "disabled">
          <el-table  :data="tableData" @cell-click="clickRow"  border stripe height="280px">
            <el-table-column label="项目编码" align="center" prop="projectCode" />
            <el-table-column label="项目名称" align="left" prop="projectName" />
            <el-table-column label="项目类型" align="center" prop="projectType" :formatter="projectTypeFormat" />
            <el-table-column label="成本属性" align="center" prop="costProperty" :formatter="costPropertyFormat" />
            <el-table-column label="是否可控" align="center" prop="isControl" :formatter="isControlFormat"/>
            <el-table-column label="是否核算" align="center" prop="isCostCalculate" :formatter="isAccountFormat" />
            <el-table-column label="资本性成本" align="center" prop="phonenumber" :formatter="isCostCapitalFormat"/>
            <el-table-column label="医疗成本" align="center" prop="sex1" :formatter="isCostHealthFormat"/>
            <el-table-column label="计算机入成本" align="center" prop="phonenumber1" :formatter="isCostCalculateFormat"/>
          </el-table>
          <pagination
            layout="total, prev, pager, next"
            v-show="total>0"
            :total="total"
            :page.sync="params.pageNum"
            :limit.sync="params.pageSize"
            @pagination="handleInput"
          />
          <el-input slot="reference" :style="{marginTop:'10px'}" v-model="queryParams[setProp]" :placeholder="'请选择'+label" @input="handleInput" @keyup.enter.native="handleInput"  @focus="handleInput"/>
        </el-popover>
      </el-form-item>
</template>

<script>
import {searchForProject} from "@/api/project/num";

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
    projectCode: {
      type: [String, Number],
      default: ""
    },
    compCode: {
      type: [String, Number],
      default: ""
    },
    setProp: {
      type: String,
      default: "projectCode"
    },
    formProp: {
      type: String,
      default: "projectCode"
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
      costPropertyOptions:[],
      projectLevelOptions:[],
      projectTypeOptions:[],
      isControlOptions: [],
      isAccountOptions: [],
      isCostCapitalOptions: [],
      isCostHealthOptions: [],
      isCostCalculateOptions: [],

    };
  },
  watch: {
    setProp: {
      handler(val) {
        if (val != "projectCode" && this.formProp == "projectCode") {
          this.formProp = val;
        }
      },
      deep: true,
      immediate: true
    },
    rules: {
      type: Array,
      default() {
        return [ ];
      }
    },
  },
  created(){
    this.getDicts("cost_xm_jb").then(response => {
      this.projectLevelOptions = response.data;
    });
    this.getDicts("cost_xm_lx").then(response => {
      this.projectTypeOptions = response.data;
    });
    this.getDicts("cost_cb_sx").then(response => {
      this.costPropertyOptions = response.data;
    });
    this.getDicts("hr_is_change").then(response => {
      this.isCostCalculateOptions = response.data;
      this.isControlOptions = response.data;
      this.isCostHealthOptions = response.data;
      this.isCostCapitalOptions = response.data;
      this.isAccountOptions = response.data;
    });
  },
  methods: {
    // 搜索数据
    handleInput(e) {
      if(e.target){
         this.params={
          deptCode:this.datas.deptCode,
          nowDate:this.datas.nowDate,
          queryString:e.target.value,
          compCode: this.$store.state.user.userInfo.deptId,          
        }
        // console.log(this.params,'params')
         searchForProject(this.params).then(response => {
            this.tableData = response.rows
            this.total = response.total;
        });
      }
     
    },
    // 点击表格行
    clickRow(row, column, cell, event) {
      this.queryParams[this.setProp] = row.projectCode;
      this.$emit("click", row);
      this.$refs.popover.doClose();
    },
     //科室类型字典翻译
      deptTypeFormat(row, column) {
          return this.selectDictLabel(this.deptTypeOptions, row.deptType);
      },
     // 项目类型字典翻译
      projectTypeFormat(row, column) {
        return this.selectDictLabel(this.projectTypeOptions, row.projectType);
      },
      // 成本属性字典翻译
      costPropertyFormat(row, column) {
        return this.selectDictLabel(this.costPropertyOptions, row.costProperty);
      },
      // 是否可控字典翻译
      isControlFormat(row, column) {
        return this.selectDictLabel(this.isControlOptions, row.isControl);
      },
      // 是否核算字典翻译
      isAccountFormat(row, column) {
        return this.selectDictLabel(this.isAccountOptions, row.isAccount);
      },
      // 是否资本性成本字典翻译
      isCostCapitalFormat(row, column) {
        return this.selectDictLabel(this.isCostCapitalOptions, row.isCostCapital);
      },
      // 是否医疗成本字典翻译
      isCostHealthFormat(row, column) {
        return this.selectDictLabel(this.isCostHealthOptions, row.isCostHealth);
      },
      // 是否计算计入成本字典翻译
      isCostCalculateFormat(row, column) {
        return this.selectDictLabel(this.isCostCalculateOptions, row.isCostCalculate);
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
