<template>
  <div class="app-container">
    <div ref="search">
      <el-form ref="queryForm" :inline="true" v-show="showSearch">
        <el-form-item label="科室类别" prop="deptRoomType" >
          <el-select class="w100" v-model="deptRoomType" @change="switchDeptRoomType($event)"  placeholder="请选择科室类别">
            <el-option
              v-for="dict in deptRoomTypeDict"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-document" size="mini" @click="submitForm">保 存</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-row :gutter="20">
      <el-col :span="12" class="mb10">
        <el-card>
          <el-col :span="12">
            <span style="font-size:16px">待选指标</span>
          </el-col>
          <el-table
            v-loading="indexListLoading"
            :data="indexUnSelectedList"
            :row-class-name="tableRowClassName"
            @select="selectIndex"
            @row-dblclick="rowClickWaitClass"
            height="calc('100% - 48px')"
          >
            <el-table-column type="index" width="50"> </el-table-column>
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="指标名称" align="left" prop="indexName" show-overflow-tooltip/>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12" class="mb10">
        <el-card>
          <el-col :span="10">
            <span style="font-size:16px">已选指标</span>
          </el-col>
          <el-table
            v-loading="indexSelectedLoading"
            :data="indexSelectedList"
            :row-class-name="tableRowClassName"
            @row-dblclick="rowClickHasClass"
            @select="unselectIndex"
            height="calc('100% - 48px')"
          >
            <el-table-column type="index" width="50"> </el-table-column>
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="指标名称" align="left" prop="indexName" show-overflow-tooltip/>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {listBonusIndex, saveBonusIndex} from "@/api/evaluate/index";

export default {
  name: 'BonusIndex',
  props: {

  },
  data() {
    return {
      indexSelectedLoading:false,
      indexListLoading:false,
      form:{},
      rules:{},
      showSearch:true,
      deptRoomTypeDict:[],
      deptRoomType: null,
      indexList: [],
      // 未选指标
      indexUnSelectedList: [],
      // 已选指标
      indexSelectedList: [],
      dataLists:[],
    };
  },

  created(){
    this.getDicts("JXGL_JJ_KSLB").then(response=>{
      this.deptRoomTypeDict = response.data;
      this.deptRoomType = this.deptRoomTypeDict[0].dictValue;
    })
    this.getBonusIndexList();
  },

  mounted() {

  },

  methods: {
    /** 获取奖金指标列表 */
    getBonusIndexList(){
      let param = {orgType: "2"};//科
      listBonusIndex(param).then(response => {
        this.indexList = response.data;
        this.getBonusIndexListSelected(this.deptRoomType)
        this.indexSelectedLoading = false
      });
    },
    /** 获取已选指标列表 */
    getBonusIndexListSelected(deptRoomType){
      this.indexUnSelectedList = [];
      this.indexSelectedList = [];
      this.indexList.forEach(item=>{
        if(deptRoomType == '0'){
          // 独立核算
          if(item.deptType != '2'){

          }
        }else if(deptRoomType == '1'){
          // 平均奖
          if(item.deptType == '2'){

          }
        }
        if((deptRoomType == '0' && item.deptType != '2') || deptRoomType == '1' && item.deptType == '2'){
          if(!item.deptRoomType){
            this.indexUnSelectedList.push(item);
          }else if(item.deptRoomType == deptRoomType){
            this.indexSelectedList.push(item);
          }
        }
      })
    },

    tableRowClassName({ row, rowIndex }) {
      row.index = rowIndex;
    },
    // 待选角色多选框选中数据
    selectIndex(selection, row) {
      this.rowClickWaitClass(selection[0])
    },
    //待选角色 点击行
    rowClickWaitClass(selection){
      this.indexSelectedList.push(selection);
      this.indexUnSelectedList.splice(selection.index, 1);
    },
    // 已选角色多选框选中数据
    unselectIndex(selection, row) {
      this.rowClickHasClass(selection[0])
    },
    // 已经选择点击行
    rowClickHasClass(selection){
      this.indexUnSelectedList.push(selection);
      this.indexSelectedList.splice(selection.index, 1);
    },
    switchDeptRoomType(all){
      this.getBonusIndexListSelected(all);
    },

    submitForm(){
      if(!this.deptRoomType){
        this.msgError("未选择科室类别");
        return;
      }
      let data = {
        compCode: this.$store.state.user.userInfo.deptId,
        deptRoomType: this.deptRoomType,
        evaluateIndexBonusList: this.indexSelectedList.map(item=>{
          this.dataLists.forEach(ite=>{
            if(ite.indexName==item.indexName){
              item.indexCode=ite.indexCode
            }
          })
          return item
        })
      }
      const saveLoading=this.saveLoading()
      saveBonusIndex(data).then(response=>{
        if(response.code==200){
          this.msgSuccess("保存成功");
          this.$emit("success", false);
          this.getBonusIndexList();
          saveLoading.close()
        }
      })
    },
  },
};
</script>

<style lang="scss" scoped>
::v-deep .mb10 {
  height: 100%;
  .el-card__body {
    padding: 10px;
    height: 100%;
  }
  .el-table{
    height: calc(100vh - 220px);
  }
}
.app-container{padding: 0px;}
</style>
