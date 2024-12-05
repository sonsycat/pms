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
            <el-table-column label="方案编码" align="center" prop="projectCode" />
            <el-table-column label="方案名称" align="left" prop="projectName" />
            <el-table-column label="方案比例" align="left" prop="rationShow" />
          </el-table>
          <pagination
            layout="total, prev, pager, next"
            v-show="total>0"
            :total="total"
            :page.sync="params.pageNum"
            :limit.sync="params.pageSize"
            @pagination="handleInput"
          />
          <el-input slot="reference" v-if="projectType==2 && label=='计算计入成本分摊方案'" v-model="queryParams[setProp]" :placeholder="'请选择'+label" @input="handleInput" @keyup.enter.native="handleInput"  @focus="handleInput"/>
           <el-input slot="reference" v-if="projectType==1 && label=='一级分摊方案'" v-model="queryParams[setProp1]" :placeholder="'请选择'+label" @input="handleInput" @keyup.enter.native="handleInput"  @focus="handleInput"/>
         <el-input slot="reference" v-if="projectType==1 && label=='二级分摊方案'" v-model="queryParams[setProp2]" :placeholder="'请选择'+label" @input="handleInput" @keyup.enter.native="handleInput"  @focus="handleInput"/>
          <el-input slot="reference" v-if="projectType==1 && label=='三级分摊方案'" v-model="queryParams[setProp3]" :placeholder="'请选择'+label" @input="handleInput" @keyup.enter.native="handleInput"  @focus="handleInput"/>
        </el-popover>
      </el-form-item>
</template>

<script>
import { getProjectPop } from "@/api/project/project";
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
    projectType:{
      type: [String, Number],
      default: ""
    },
    compCode: {
      type: [String, Number],
      default: ""
    },
    setProp: {
      type: String,
      default: "costScheme"
    },
    setProp1: {
      type: String,
      default: "firstLevelSql"
    },
    setProp2: {
      type: String,
      default: "secondLevelSql"
    },
    setProp3: {
      type: String,
      default: "thirdLevelSql"
    },
    formProp: {
      type: String,
      default: "projectName"
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
    };
  },
  watch: {
    setProp: {
      handler(val) {
        if (val != "projectName" && this.formProp == "costScheme") {
          this.formProp = val;
        }
      },
      deep: true,
      immediate: true
    },
     setProp1: {
      handler(val) {
        if (val != "projectName" && this.formProp == "firstLevelSql") {
          this.formProp = val;
        }
      },
      deep: true,
      immediate: true
    },
     setProp2: {
      handler(val) {       
        if (val != "projectName" && this.formProp == "secondLevelSql") {
          this.formProp = val;
        }
      },
      deep: true,
      immediate: true
    }, 
    setProp3: {
      handler(val) {
        if (val != "projectName" && this.formProp == "thirdLevelSql") {
          this.formProp = val;
        }
      },
      deep: true,
      immediate: true
    }
  },
  created(){
  },
  methods: {
    // 搜索数据
    handleInput(e) {
      if(e.target){
          let searchQuery={
          compCode:this.$store.state.user.userInfo.deptId,
          projectType: this.projectType,
          projectName:e.target.value
        }
        getProjectPop(searchQuery).then(response => {
            this.tableData = response.rows
            this.total = response.total;
        });
      }      
      
    },
    // 点击表格行
    clickRow(row, column, cell, event) {
        if(this.projectType==2 && this.label=="计算计入成本分摊方案"){
            this.queryParams[this.setProp] = row.projectName;
        }else if(this.projectType==1 && this.label=="一级分摊方案"){
            this.queryParams[this.setProp1] = row.projectName;
        }else if(this.projectType==1 && this.label=="二级分摊方案"){
            this.queryParams[this.setProp2] = row.projectName;
        }else{
            this.queryParams[this.setProp3] = row.projectName;            
        }

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
