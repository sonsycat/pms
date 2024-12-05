<template>
   <el-dialog :title="title" :visible.sync="openDialog" width="70%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
    <el-table border stripe v-loading="loading" min-height='100px' max-height='600px' :data="apportList" ref="multipleTable">
      <el-table-column sortable label="收入项目" align="left" prop="revenueName" />
      <el-table-column sortable label="科室" align="left" prop="dpetName" v-if="!isShow" />
      <el-table-column sortable label="应收金额" align="right" prop="receiveNum" v-if="isShow"/>
      <el-table-column sortable label="实际金额" align="right" prop="actualNum" />
      <el-table-column sortable label="开单科室" align="center" prop="billDeptName" v-if="isShow" />
      <el-table-column sortable label="执行科室" align="center" prop="execDeptName" v-if="isShow"/>
      <el-table-column sortable label="护理单元" align="center" prop="careDeptName" v-if="isShow"/>
      <el-table-column sortable label="开单医生" align="center" prop="billDoc" v-if="isShow"/>
      <el-table-column sortable label="收入类别" align="center" prop="revenueType" :formatter="revenueTypeFormat" />
      <el-table-column sortable label="结算标识" align="center" prop="settleMark" :formatter="settleTypeFormat" />
      <el-table-column sortable label="发生日期" align="center" prop="occureTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.occureTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="params.pageNum"
      :limit.sync="params.pageSize"
      @pagination="getDetailInfo"
    />
    <pagination
      v-show="afterTotal>0"
      :total="afterTotal"
      :page.sync="params.pageNum"
      :limit.sync="params.pageSize"
      @pagination="getDetailInfo"
    />
    <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
   </el-dialog>
</template>

<script>
import { resolveBefore, resolveAfter } from "@/api/revenue/apport";

export default {
  name: "addEdit",
  props: {
    open: {
      type: Boolean,
      default: false
    },
    params:{
      type: Object,
      default: null
    },
    types:{
      type:String,
      default:''
    }
  },
  watch: {
    open: {
      handler(val) {
        this.openDialog = val;
      },
      deep: true
    }
  },
  components: {
  },
  data() {
    return {
      title: "收入数据生成-查看明细",
      openDialog: this.open,
      isShow:true,
      loading:false,
      saveLoading: false,
      revenueTypeOptions: [],
	  	settleTypeOptions: [],
      apportList:[],
      total:0,
      afterTotal:0,
    };
  },
  created() {
    this.getDetailInfo()
    this.getDicts("pms_srlb_type").then(response => {
      this.revenueTypeOptions = response.data;
    });
    this.getDicts("pms_jsbs_type").then(response => {
      this.settleTypeOptions = response.data;
    });
  },
  mounted(){
  },
  methods: {
  	getDetailInfo() {
      if(this.types=='resolveBefore'){
        resolveBefore(this.params).then(response => {
          if(response.code==200){
            this.apportList = response.rows.map(_=>{
              _.settleType=_.settleMark;
              return _
            });
            this.total=response.total;
            for (let i = 0; i < this.apportList.length; i++) {
              for (var j in this.apportList[i]) {
                if ((typeof this.apportList[i][j]) == "number" && this.apportList[i][j] != 0 && j!="id") {
                  this.apportList[i][j] = this.moneyFormat(this.apportList[i][j])
                }
              }
          }
          }
        });
      }else{
        resolveAfter(this.params).then(response => {
          if(response.code==200){
            this.apportList = response.rows.map(_=>{
              _.receiveNum=_.receiveNum?_.receiveNum:0;
              return _
            });
            this.afterTotal=response.total;
            for (let i = 0; i < this.apportList.length; i++) {
              for (var j in this.apportList[i]) {
                if ((typeof this.apportList[i][j]) == "number" && this.apportList[i][j] != 0 && j!="id") {
                  this.apportList[i][j] = this.moneyFormat(this.apportList[i][j])
                }
              }
          }
            this.isShow=false;
          }
        });
      }

    },
    // 收入类别(1门诊，2住院)字典翻译
    revenueTypeFormat(row, column) {
      return this.selectDictLabel(this.revenueTypeOptions, row.revenueType);
    },
    // // 收入类型字典翻译
    // revenueKindFormat(row, column) {
    //   return this.selectDictLabel(this.revenueKindOptions, row.revenueKind);
    // },
    // 结算标识(1,正常,2附加收入)字典翻译
    settleTypeFormat(row, column) {
      return this.selectDictLabel(this.settleTypeOptions, row.settleType);
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit("cancel", false);
    },
    // 取消
    cancel() {
      this.$emit("cancel", false);
    },
}};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    min-height: 20%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: hidden;
      .pagination-container{
        height: 35px;
      }
    }
  }
</style>
