<template>
  <div>
    <el-form-item :label="label" :prop="prop">
        <el-popover
          placement="bottom"
          ref="popover"
          width="700"
          trigger="click"
          :disabled = "disabled">
          <el-table  :data="tableData" @cell-click="clickRow"  border stripe height="280px">
            <el-table-column label="用户编号" align="center" prop="userId" />
            <el-table-column label="人员姓名" align="left" prop="nickName" />
            <el-table-column label="性别" align="center" prop="sex" :formatter="sexFormat" />
            <el-table-column label="手机号码" align="center" prop="phonenumber"/>
            <el-table-column label="部门" align="left" prop="dept.deptName" show-overflow-tooltip/>
          </el-table>
          <pagination
            layout="total, prev, pager, next"
            v-show="total>0"
            :total="total"
            :page.sync="params.pageNum"
            :limit.sync="params.pageSize"
            @pagination="handleInput"
          />
          <el-input slot="reference" v-model="searchValue" @input="handleInput"  @focus="handleInput"/>
        </el-popover>
      </el-form-item>
  </div>
</template>

<script>
import { listUser } from "@/api/system/user";
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
      type: Object
    },
    label: {
      type: String,
      default: "点击搜索"
    },
    prop:String,
    compCode: {
      type: [String, Number],
      default: ""
    }
  },
  data() {
    return {
      // 搜索内容
      searchValue: "",
      // 表格数据
      tableData: [],
      formData: this.queryParams,
      //性别字典
      sexOptions: [],
      // 职称字典
      titleNameOptions: [],
      params: {
        pageNum: 1,
        pageSize: this.$store.state.settings.popPageSize,

      },
      total: 0
    };
  },
  mounted() {
    this.initDic();
    console.log(this.formData);
  },

  methods: {
    // 初始化字典数据
    initDic() {
      this.getDicts("sys_user_sex").then(response => {
        this.sexOptions = response.data;
      });
      this.getDicts("DE02.01.049.00").then(response => {
        this.titleNameOptions = response.data;
      });
    },
    sexFormat(row, column) {
      return this.selectDictLabel(this.sexOptions, row.gender);
    },
    // 职称字典翻译
    titleNameFormat(row, column) {
      return this.selectDictLabel(this.titleNameOptions, row.titleCode);
    },
    // 搜索数据
    handleInput() {
        // 人员基本信息
        let data = Object.assign({ userName: this.searchValue }, this.params);
        listUser(data).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
        });
      
    },
    // 点击表格行
    clickRow(row, column, cell, event) {
      console.log(row);
      // if (this.formData && Object.keys(this.formData).length) {
      //   Object.keys(this.formData).forEach(key => {
      //     if (row.hasOwnProperty(key)) {
      //       this.formData[key] = row[key];
      //     }
      //   });
      // }
      this.searchValue = "";
      this.$emit("handlePeople", row);
      this.$refs.popover.doClose();
    }
  }
};
</script>

<style lang="scss" scoped>
</style>