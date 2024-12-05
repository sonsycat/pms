<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
    <el-table border max-height="570px" v-loading="loading" :data="tableData" ref="cardListRef" stripe>
      <el-table-column type="index"  width="50"/>
      <el-table-column label="院|科|人编码" align="center" prop="unitCode" show-overflow-tooltip/>
      <el-table-column label="指标值" align="center" prop="guideValue" show-overflow-tooltip/>
    </el-table>

    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {createDataFromSql} from "@/api/evaluate/index";

export default {
  name: 'detailData',
  props: {
    open: {
      type: Boolean,
      default: false
    },
    id: {
      type: [String, Number],
      default: ""
    },
    datas:{
      type:Object,
      default:null
    }
  },
  data() {
    return {
      loading:false,
      title: "指标管理-数据查看",
      openDialog:this.open,
      tableData:[],
      total:0,
      queryParams:{
        pageSize:30,
        pageNum:1
      }
    };
  },

  mounted() {

  },
  created(){
    this.getList()
  },
  methods: {
    getList(){
      this.loading=true;
      createDataFromSql(this.datas).then(response => {
        if (response.code === 200) {
          this.tableData = response.data;
        }
        this.loading = false;
      }).catch((err)=>{
        this.loading=false;
      });
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit("cancel", false);
    },
    cancel(){
      this.$emit("cancel", false);
    },
  },
};
</script>

<style lang="scss" scoped>
::v-deep .el-dialog {
  height: 80%;
  overflow: hidden;
  .el-dialog__body {
    // height: calc(100% - 100px);
    overflow: auto;
    .lit-title{
      font-size: 16px;
      padding-bottom: 8px;
      border-bottom: 1px dashed #cccccc;
    }
    .el-table{
      overflow: hidden;
    }
  }
}
</style>
