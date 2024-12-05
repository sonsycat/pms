<template>
<!-- 查询员工信息 -->
      <el-form-item :label="label" :prop="itemProp" :rules="rules">
        <el-popover
          placement="bottom"
          ref="popover"
          width="700"
          trigger="click"
          :disabled = "disabled">
          <el-table  :data="tableData" @cell-click="clickRow"  border stripe height="280px">
            <el-table-column label="用户编号" align="center" prop="userId" show-overflow-tooltip/>
          	<el-table-column show-overflow-tooltip label="工号" align="center" prop="empCode"/>
            <el-table-column label="用户名称" align="left" prop="userName" show-overflow-tooltip/>
            <el-table-column label="用户昵称" align="left" prop="empName" show-overflow-tooltip/>
          </el-table>
          <pagination
            layout="total, prev, pager, next"
            v-show="total>0"
            :total="total"
            :page.sync="params.pageNum"
            :limit.sync="params.pageSize"
            @pagination="handleInput"
          />
          <el-input slot="reference" class="w100" :size="size" :disabled = "disabled" v-model="formData[inputProp]" :placeholder="'请选择'+label" @input="handleInput" @keyup.enter.native="handleInput"  @focus="handleInput"/>
          <el-input slot="reference" v-if="false" :disabled = "disabled" v-model="staffParams[itemProp]"/>
        </el-popover>
      </el-form-item>
</template>

<script>
import { getStaffInfo } from "@/api/system/user";
export default {
  name: "index",
  props: {
    label: {
      type: String,
      default: "员工"
    },
    // 是否禁用
    disabled: {
      type: Boolean,
      default: false
    },
    // 传参为form表单 方便数据回显渲染
    staffParams: {
      type: [Object, Array]
    },
    datas: {
      type: Object,
      default() {
        return {};
      }
    },
    size:{
      type: String,
      default: "mini"
    },
    inputProp: {
      type: String,
      default: "empCode"
    },
    rules: {
      type: Array,
      default() {
        return [];
      }
    },
    formItemProp: {
      type: String,
      default: "empCode"
    },
    //用户数据
    userInfoList: {
      type: [Object, Array]
    },
    // datas 传值有单位 科室等重新赋值 是否默认当前用户单位下搜索
    userCompFlag:{
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      // 表格数据
      tableData: [],
      formData: {},
      params: {
        pageNum: 1,
        pageSize: this.$store.state.settings.popPageSize
      },
      total: 0,
      itemProp: this.formItemProp,
      hasData: false
    };
  },
  computed: {
    empCodeName() {
      return this.formData[this.itemProp];
    },
    staffEmpCode() {
      return this.staffParams[this.itemProp];
    }
  },
  created() {},
  watch: {
    // 必填切换
    formItemProp: {
      handler(val) {
        this.itemProp = val;
      },
      deep: true,
      immediate: true
    },
    staffEmpCode: {
      handler(val) {
        this.formData = JSON.parse(JSON.stringify(this.staffParams));
      },
      deep: true,
      immediate: true
    },
    inputProp: {
      handler(val) {
        if (val != "empCode" && this.formItemProp == "empCode") {
          this.itemProp = val;
        }
      },
      deep: true,
      immediate: true
    },
    empCodeName: {
      handler(val) {
        if (val) {
          this.searchStaffName(val);
        }
      },
      deep: true,
      immediate: true
    },
    userInfoList: {
      handler(val) {
        if (val) {
          this.hasData = true;
          this.tableData = val;
        }
      },
      deep: true,
      immediate: true
    }
    // "$store.state.dicts": {
    //   handler(val) {
    //     // 基础字典
    //     let baseDicts = val.baseDicts;
    //     this.unitOptions = baseDicts.quantityUnit;

    //     // 特殊
    //     this.supplierOptions = val.supplierInfo;
    //   },
    //   deep: true,
    //   immediate: true
    // }
  },
  mounted() {},

  methods: {
    searchStaffName(searchName, userData) {
      console.log(searchName,"searchName")
      if (userData) {
      } else {
        let data = null
        if(this.userCompFlag){
          data =  {
            searchName,
            compCode: this.$store.state.user.userInfo.deptId,
            deptCode: this.$store.state.user.userInfo.deptRoomId
          };
        }else{
          data = Object.assign({
            searchName
          },
          this.datas)
        }
        getStaffInfo(data).then(response => {
          if (response.code == 200) {
            if (response.rows.length) {
              let data = response.rows[0].empName;
              this.$set(this.formData, this.inputProp, data);
              // this.$emit("update:staffParams", this.formData);
            }
          } else {
            this.msgError(response.msg);
          }
        });
      }
    },
    // 搜索数据
    handleInput() {
      if (this.hasData) {
        console.log(this.userInfoList,"this.userInfoList")
        let val = this.formData[this.inputProp]
      // 下节点审核人 传的固定数据
        if(val){
          let data = this.userInfoList.filter(el => {
            return el.empCode.indexOf(val)>-1 || el.empName.indexOf(val)>-1 ||el.userName.indexOf(val) > -1 || el.userId.indexOf(val) > -1
          })
          this.tableData = data
        }else{
          this.tableData = this.userInfoList
        }
      } else {
        let data = Object.assign(
          {
            searchName: this.formData[this.inputProp],
            compCode: this.$store.state.user.userInfo.deptId,
            deptCode: this.$store.state.user.userInfo.deptRoomId
          },
          this.datas
        );

        if(this.formData[this.inputProp] == ""){
          this.staffParams[this.formItemProp] = null
        }
        getStaffInfo(data).then(response => {
          if (response.code == 200) {
            this.tableData = response.rows;
            this.total = response.total;
          } else {
            this.msgError(response.msg);
          }
        });
      }
    },
    // 点击表格行
    clickRow(row, column, cell, event) {
      let obj = {};
      obj[this.inputProp] = row.empName;
      obj[this.itemProp] = row.userId;

      this.formData[this.inputProp] = row.empName;
      obj = Object.assign(this.staffParams, obj);
      this.$emit("update:staffParams", obj);
      this.$emit("handleStaff", row);
      this.$refs.popover.doClose();
    }
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
