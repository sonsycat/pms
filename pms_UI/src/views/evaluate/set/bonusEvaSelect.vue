<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="62%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
        <el-form-item label="指标集名称" prop="setName">
          <el-input
            class="w100"
            v-model="queryParams.setName"
            placeholder="请输入指标集名称"
            disabled
          />
        </el-form-item>
        <el-form-item label="科室类别" prop="deptTypeForBonuse" >
          <el-select class="w100" v-model="queryParams.deptTypeForBonuse" @change="filterDeptType($event)"  placeholder="请选择科室类别" clearable  >
            <el-option
              v-for="dict in deptTypeForBonuses"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="部门" prop="deptType">
          <el-select class="w100" v-model="queryParams.deptType" placeholder="请选择部门分类" clearable >
            <el-option
              v-for="dict in deptOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="指标代码" prop="indexCode">
          <el-input
            class="w100"
            v-model="queryParams.indexCode"
            placeholder="请输入指标代码"
            clearable
          />
        </el-form-item>
        <el-form-item label="指标名称" prop="indexName">
          <el-input
            class="w100"
            v-model="queryParams.indexName"
            placeholder="请输入指标名称"
            clearable
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-row :gutter="20">
<!--      <el-col :span="7" class="mb9">-->
<!--        <el-card>-->
<!--          <el-table-->
<!--            ref="menmberTable"-->
<!--            v-loading="evaTreeListLoading"-->
<!--            :data="evaTreeList"-->
<!--            :row-class-name="tableRowClassName"-->
<!--            row-key="id"-->
<!--            :tree-props="{children: 'children', hasChildren: 'hasChildren'}"-->
<!--            @row-click="rowMemberClick"-->
<!--            @selection-change="handleSelectionMemberChange"-->
<!--          >-->
<!--            <el-table-column type="selection" :selectable="selectableTota" width="55" align="center" />-->
<!--            <el-table-column label="指标分类树" align="left" prop="bscName" show-overflow-tooltip/>-->
<!--          </el-table>-->
<!--        </el-card>-->
<!--      </el-col>-->
      <el-col :span="10" class="mb10">
        <el-card>
          <el-col :span="12">
            <span style="font-size:16px">待选指标</span>
          </el-col>
          <el-table
            v-loading="indexListLoading"
            :data="indexNoList"
            :row-class-name="tableRowClassName"
            @select="selectionIndex"
            @row-click="rowClickWaitClass"
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="指标名称" align="left" prop="indexName" show-overflow-tooltip/>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="14" class="mb10">
        <el-card>
          <el-col :span="10">
            <span style="font-size:16px">已选指标</span>
          </el-col>
          <el-table
            v-loading="indexSelectedLoading"
            :data="indexSelectedList"
            :row-class-name="tableRowClassName"
            @row-click="rowClickHasClass"
            @select="selectionedIndex"
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="指标名称" align="left" prop="indexName" show-overflow-tooltip/>
            <el-table-column label="权重" align="left" prop="weight" >
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.weight" :min="0" :precision="0"  @click.native.stop/>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button @click="handleDialogClose">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import { getEvaTree, getEvaToSelTree, getEvaSeledTree, addEvaSet} from "@/api/evaluate/set";
  import {listBonus, listBonusAll} from "@/api/evaluate/index";


  export default {
    name: 'evaSelect',
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
        type:Array,
        default:[]
      }
    },
    data() {
      return {
        title:"奖金指标集管理-指标选择",
        openDialog:this.open,
        evaTreeListLoading:false,
        indexSelectedLoading:false,
        indexListLoading:false,
        form:{},
        rules:{},
        showSearch:true,
        table1Height:'508px',
        tableHeight:'500px',
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 30,
          compCode: null,
          accountCode: null,
          setCode: null,
          setName: null,
          indexCode:null,
          indexName:null,
          oneCode: null,
          secondCode: null,
          deptType: null,
          deptTypeForBonuse:null,
          orgType: null,
          partYear: null,
        },
        //指标部门分类
        deptOptions:[],
        deptTypeForBonuses:[],
        //待选角色
        queryParamsIndex: {
          pageNum: 1,
          pageSize: this.$store.state.settings.pageSize,
          compCode: this.$store.state.user.userInfo.deptId,
          bscSecondCode:null,
          weight: null,
        },
        //已选角色
        queryParamsIndexSelected: {
          pageNum: 1,
          pageSize: this.$store.state.settings.pageSize,
          compCode: this.$store.state.user.userInfo.deptId,
          weight: null,
          bscSecondCode:null,
          setCode:null,
        },
        //指标分类树
        evaTreeList:[],
        // 待选角色数据
        indexNoList: [],
        dataLists:[],
        // 已选角色数据
        indexSelectedList: [],
        selectionMemberRow:[],
      };
    },

    created(){

      if(this.datas.length!==0){
        this.queryParams.setName=this.datas[0].setName;
        this.queryParamsIndexSelected.setCode=this.datas[0].setCode;
        this.getEvaSeledTree()
      }
      this.getToBeSelectedList();
      // listBonus(this.queryParams).then(response => {
      //   this.indexNoList = response.rows
      // });
      //指标部门分类字典
      this.getDicts("pms_bm_fl").then(response=>{
        this.deptOptions=response.data;
      })
      this.getDicts("JXGL_JJ_KSLB").then(response=>{
        this.deptTypeForBonuses=response.data;
      })
      this.queryParams.deptTypeForBonuse = this.datas[0].deptType;
    },

    mounted() {

    },

    methods: {
      handleDialogClose(){
        this.$emit("cancel", false);
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        // if(this.selectionMemberRow.length!==0){
          this.getToBeSelectedList()
        // }else{
        //   this.msgError("请选择指标分类")
        // }
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.indexSelectedList = this.indexSelectedList.filter(function(item){
            return item.id;
        })
        this.handleQuery();
      },
      // 只能选择 末级节点
      selectableTota(row){
        if(row.isLast == 0){
          // 末级
          return  true
        }else{
          return false
        }
      },
      // //待选角色列表
      // getEvaToSelTree(){
      //   this.indexListLoading=true;
      //   let res=[]
      //   getEvaToSelTree(this.queryParams).then(response=>{
      //     this.indexNoList=response.rows;
      //     for(let i in this.indexSelectedList){
      //       for(let k in this.indexNoList){
      //         if(this.indexSelectedList[i].indexName == this.indexNoList[k].indexName){
      //           this.indexNoList.splice(k,1)
      //         }
      //       }
      //     }
      //     this.indexListLoading=false;
      //   })
      // },

      //待选角色列表
      getToBeSelectedList(){
        this.indexListLoading=true;
        let res=[]
        this.queryParams.orgType = this.datas[0].orgType;
        listBonusAll(this.queryParams).then(response=>{
          this.indexNoList = response;
          console.log(this.indexNoList)
          console.log(this.indexSelectedList)
          for(let i in this.indexSelectedList){
            for(let k in this.indexNoList){
              if(this.indexSelectedList[i].indexName == this.indexNoList[k].indexName){
                this.indexNoList.splice(k,1)
              }
            }
          }
          this.indexListLoading=false;
        })
      },

      /** 已选角色列表 */
      getEvaSeledTree() {
        this.indexSelectedLoading = true;
        getEvaSeledTree(this.queryParamsIndexSelected).then(response => {
          this.indexSelectedList = response.rows
          this.indexSelectedLoading = false
        });
      },
      tableRowClassName({ row, rowIndex }) {
        row.index = rowIndex;
      },
      // 待选角色多选框选中数据
      selectionIndex(selection, row) {
        this.rowClickWaitClass(selection[0])
      },
      rowMemberClick(row, column, event) {
        if(row.isLast == 0){
          let refsElTable = this.$refs.menmberTable;
          let findRow = this.selectionMemberRow.find(c => c.rowIndex == row.rowIndex);
          refsElTable.clearSelection();
          refsElTable.toggleRowSelection(row);
        }
      },
      handleSelectionMemberChange(selection) {
        let refsElTable = this.$refs.menmberTable;
        if (selection.length > 1) {
          refsElTable.clearSelection();
          refsElTable.toggleRowSelection(
            selection[selection.length - 1],
            false
          );
          selection = Array(selection[selection.length - 1]);
        } else if (selection.length === 1) {
          selection = Array(selection[selection.length - 1]);
        } else {
          selection = [];
        }
       // this.selectionMemberRow=selection;
       // if(this.selectionMemberRow.length!==0){
          this.queryParams.bscSecondCode=this.selectionMemberRow[0].bscCode;
          this.queryParams.orgType=this.datas[0].orgType;
          this.queryParams.compCode=this.$store.state.user.userInfo.deptId;
          this.getToBeSelectedList()
       // }
      },
      //待选角色 点击行
      rowClickWaitClass(selection){
        let {
          indexCode,
          indexName,
          weight
        } = selection;
        this.indexSelectedList.push({
          indexCode,
          indexName,
          weight:weight?weight:0
        });
        this.indexNoList.splice(selection.index, 1);
      },
      // 已选角色多选框选中数据
      selectionedIndex(selection, row) {
        this.rowClickHasClass(selection[0])
      },
      // 已经选择点击行
      rowClickHasClass(selection){
        let {
          indexCode,
          indexName,
          weight
        } = selection;
        this.indexNoList.push({
          indexCode,
          indexName,
          weight
        });
        this.indexSelectedList.splice(selection.index, 1);
      },
      filterDeptType(all){
        this.queryParams.deptType = null;
        this.getDicts("pms_bm_fl").then(response=>{
          let depts = [];
          depts = response.data;
          if(all=='1'){
            for(let k in depts){
              if('机关科室' == depts[k].dictLabel){
                depts.splice(k,1)
                this.queryParams.deptType = depts[0].dictValue
              }
            }
            this.deptOptions =depts
          }else if(all=='2'){
                let arr=[{
                  dictLabel: "机关科室",
                  dictValue: "2",
                }]
                this.deptOptions = arr
            this.queryParams.deptType = arr[0].dictValue

          }
        })
        console.log(this.deptOptions)
      },

      submitForm(){
        if(!this.queryParams.deptTypeForBonuse){
          this.msgError("未选择科室类别");
          return;
        }
        let datas={
          compCode:this.$store.state.user.userInfo.deptId,
          setCode:this.datas[0].setCode,
          deptType:this.queryParams.deptTypeForBonuse,
          pmsEvaluateSetIndexList:this.indexSelectedList.map(item=>{
            this.dataLists.forEach(ite=>{
              if(ite.indexName==item.indexName){
                item.indexCode=ite.indexCode
              }
            })
            return item
          })
        }
        const saveLoading=this.saveLoading()
        addEvaSet(datas).then(response=>{
          if(response.code==200){
            this.msgSuccess("编辑成功");
            this.$emit("success", false);
            saveLoading.close()
          }
        })
      },
    },
  };
</script>

<style lang="scss" scoped>
  ::v-deep .el-dialog {
    min-height: 10%;
    max-height: 85%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
      .el-form-item__label{
        width: auto !important;
      }
      .el-form-item__content{
        margin-left: 0 !important
      }
      .mb9{
        .el-table{
          height: 508px;
          margin-top:10px
        }
      }
      .mb10{
        .el-table{
          height: 500px;
          margin-top:10px
        }
      }
    }
  }

</style>
