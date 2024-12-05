<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
      <el-form-item label="项目名称" prop="revenueCode">
        <el-select class="search_w220" v-model="queryParams.revenueCode" filterable clearable placeholder="请选择项目名称" >
          <el-option
            v-for="dict in revenueList"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="开单科室" prop="billDept">
        <el-select class="search_w220" v-model="queryParams.billDept" filterable clearable placeholder="请选择开单科室" >
          <el-option
            v-for="dict in deptList"
            :key="dict.deptRoomId"
            :label="dict.deptRoomName"
            :value="dict.deptRoomId"
          ></el-option>
          <!--            :value="Number(dict.dictValue)"-->
        </el-select>
      </el-form-item>
      <el-form-item label="执行科室" prop="execDept">
        <el-select class="search_w220" v-model="queryParams.execDept" filterable clearable placeholder="请选择执行科室" >
          <el-option
            v-for="dict in deptList"
            :key="dict.deptRoomId"
            :label="dict.deptRoomName"
            :value="dict.deptRoomId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
	</div>
	<div ref="btns">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          size="mini"
          type="primary"
          icon="el-icon-refresh"
          @click="handleRefresh"
        >刷新</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-box"
          size="mini"
          @click="handleSave"
        >保存</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAddTable"
        >添加行</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          :disabled="single"
          @click="handleDept"
        >科室设置</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table border v-loading="loading" :height="tableHeight" :data="specialList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="项目名称" align="center" prop="revenueCode" width="160px" fixed="left">
        <template slot-scope="scope">
          <el-select v-model="scope.row.revenueCode" filterable clearable placeholder="请选择项目名称" @change="changeRow(scope.row)" >
            <el-option
              v-for="dict in revenueList"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column sortable label="开单科室" align="center" prop="billDept" width="160px" fixed="left">
        <template slot-scope="scope">
          <el-select v-model="scope.row.billDept" filterable clearable placeholder="请选择开单科室" @change="changeRow(scope.row)" >
            <el-option
              v-for="dict in deptList"
              :key="dict.deptRoomId"
              :label="dict.deptRoomName"
              :value="dict.deptRoomId"
            ></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column sortable label="执行科室" align="center" prop="execDept" width="160px" fixed="left">
        <template slot-scope="scope">
          <el-select v-model="scope.row.execDept" filterable clearable placeholder="请选择执行科室" @change="changeRow(scope.row)" >
            <el-option
              v-for="dict in deptList"
              :key="dict.deptRoomId"
              :label="dict.deptRoomName"
              :value="dict.deptRoomId"
            ></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column sortable label="住院开单" align="center" prop="inpBill" width="120px" >
        <template slot-scope="scope">
          <div @click.stop.prevent style="display:inline">
          <el-input-number :precision="2" :disabled="checkedNumFlag2" controls-position="right" :min="0" :max="100" v-model="scope.row.inpBill" class="w100" @change="changeRow(scope.row)"></el-input-number>
          </div>
        </template>
      </el-table-column>
      <el-table-column sortable label="住院执行" align="center" prop="inpExec" width="120px" >
        <template slot-scope="scope">
          <div @click.stop.prevent style="display:inline">
          <el-input-number :precision="2" :disabled="checkedNumFlag2" controls-position="right" :min="0" :max="100" v-model="scope.row.inpExec" class="w100" @change="changeRow(scope.row)"></el-input-number>
          </div>
        </template>
      </el-table-column>
      <el-table-column sortable label="住院护理" align="center" prop="inpCare" width="120px" >
        <template slot-scope="scope">
          <div @click.stop.prevent style="display:inline">
          <el-input-number :precision="2" :disabled="checkedNumFlag2" controls-position="right" :min="0" :max="100" v-model="scope.row.inpCare" class="w100" @change="changeRow(scope.row)"></el-input-number>
          </div>
        </template>
      </el-table-column>
      <el-table-column sortable label="门诊开单" align="center" prop="outpBill" width="120px" >
        <template slot-scope="scope">
          <div @click.stop.prevent style="display:inline">
          <el-input-number :precision="2" :disabled="checkedNumFlag2" controls-position="right" :min="0" :max="100" v-model="scope.row.outpBill" class="w100" @change="changeRow(scope.row)"></el-input-number>
          </div>
        </template>
      </el-table-column>
      <el-table-column sortable label="门诊执行" align="center" prop="outpExec" width="120px" >
        <template slot-scope="scope">
          <div @click.stop.prevent style="display:inline">
          <el-input-number :precision="2" :disabled="checkedNumFlag2" controls-position="right" :min="0" :max="100" v-model="scope.row.outpExec" class="w100" @change="changeRow(scope.row)"></el-input-number>
          </div>
        </template>
      </el-table-column>
      <el-table-column sortable label="门诊护理" align="center" prop="outpCare" width="120px" >
        <template slot-scope="scope">
          <div @click.stop.prevent style="display:inline">
          <el-input-number :precision="2" :disabled="checkedNumFlag2" controls-position="right" :min="0" :max="100" v-model="scope.row.outpCare" class="w100" @change="changeRow(scope.row)"></el-input-number>
          </div>
        </template>
      </el-table-column>
      <el-table-column sortable label="合作医疗" align="center" prop="coopMed" width="120px" >
        <template slot-scope="scope">
          <div @click.stop.prevent style="display:inline">
          <el-input-number :precision="2" controls-position="right" :min="0" :max="100" v-model="scope.row.coopMed" class="w100" @change="changeRow(scope.row)"></el-input-number>
          </div>
        </template>
      </el-table-column>
      <el-table-column sortable label="成本对照" align="center" prop="projectCode" width="160px" >
        <template slot-scope="scope">
          <el-select v-model="scope.row.projectCode" clearable placeholder="请选择成本对照" @change="changeRow(scope.row)" >
            <el-option
              v-for="dict in projectList"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column sortable label="固定折算" align="center" prop="fixConv" width="120px" >
        <template slot-scope="scope">
          <div @click.stop.prevent style="display:inline">
          <el-input-number :precision="2" controls-position="right" :min="0" :max="100" v-model="scope.row.fixConv" class="w100" @change="changeRow(scope.row)"></el-input-number>
          </div>
        </template>
      </el-table-column>
      <el-table-column sortable label="收入类别" align="center" prop="hisRevenumTypede" width="150px" >
        <template slot-scope="scope">
          <el-select v-model="scope.row.hisRevenumTypede" clearable placeholder="请选择项目名称" @change="changeRow(scope.row)" >
            <el-option
              v-for="dict in hisIncomeList"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            icon="el-icon-delete" circle
            type="danger"
            @click="handleAddDelete(scope.$index, scope.row)"></el-button>
        </template>
      </el-table-column>
      <el-table-column prop="isError" v-if="false"></el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

	<addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>

  <dept v-if="openDept" :open="openDept" :id="addEditId" @cancel="cancelDept" @success="successSubmitDept"></dept>

  </div>
</template>

<script>
import { listSpecial, getSpecial, delSpecial, addSpecial, updateSpecial, exportSpecial,getRevenue,getProject,getIncome,refresh,getSpecialCode,save } from "@/api/revenue/special";
import { listSpecialTemp, getSpecialTemp, delSpecialTemp, addSpecialTemp, updateSpecialTemp, exportSpecialTemp } from "@/api/revenue/specialTemp";
import addEdit from "./addEdit";
import dept from "./dept";
import {getConfigKeyApi} from "@/api/system/config";
import { truncate } from 'fs';
import { treeRoom } from '@/api/until'

export default {
  name: "Special",
  components: {
addEdit,dept
  },
  data() {
    return {
      checkedNumFlag2:false,
      openDept: false,
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
      // 特殊收入项目方案表格数据
      specialList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: null,
        specialCode: null,
        revenueCode: null,
        revenueName: null,
        billDept: null,
        execDept: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      //科室字典
      deptList: [],
      //收入核算方案
      revenueList: [],
      //成本项目字典
      projectList: [],
      //his收入类别字典
      hisIncomeList: [],
      tableHeight: "auto",
      isAddChecked:true,
      checkedNum:null,
      checkedNumFlag:false,
      checkedItem:false,
      isOnlyDeptCode:false,
      fixConvBool:false,
    };
  },
  created() {
    refresh(this.queryParams).then(()=>{
      this.getList();
    });
    getRevenue(this.$store.state.user.userInfo.deptId).then(res=>{
      this.revenueList = res.data;
    });
    getProject(this.$store.state.user.userInfo.deptId).then(res=>{
      this.projectList = res.data;
    });
    getIncome(this.$store.state.user.userInfo.deptId).then(res=>{
      this.hisIncomeList = res.data;
    });
    getConfigKeyApi("SFSRSBFT").then(res=>{
      this.checkedNum=res
      if (this.checkedNum == '0') {
        this.checkedNumFlag2 = true;
      }
    });
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });

    this.getDeptList(this.$store.state.user.userInfo.deptId);
  },
  computed: {
    listData() {
      return this.$store.state.app.sidebar.opened;
    }
  },
  watch: {
    "$store.state.dicts": {
      handler(val) {
        // 基础字典
        let baseDicts = val.baseDicts;
        // this.deptList = val.deptInfo;
      },
      deep: true,
      immediate: true
    },
    listData(val) {
      var that = this;
      setTimeout(function() {
        that.resizeHandler();
      }, 300);
    }
  },
  methods: {
    //获取开单科室
    getDeptList(deptId){
      let data={deptId:deptId}
      treeRoom(data).then(response=>{
        if(response.code==200){
          this.deptList=JSON.parse(JSON.stringify(response.rows))
        }
      })
    },
    successSubmitDept(flag) {
      this.openDept = flag;
      this.getList();
    },
    // 取消按钮
    cancelDept() {
      this.openDept = false;
      this.reset();
    },
    handleDept(row) {
      this.reset();
      const id = row.id || this.ids[0];
      this.openDept = true;
      this.addEditId = id; // id赋值
      console.log("addEditId---" + addEdit);
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    /** 查询特殊收入项目方案列表 */
    getList() {
      this.loading = true;
      listSpecialTemp(this.queryParams).then(response => {
        this.specialList = response.rows;
        this.total = response.total;
        this.loading = false;
          this.specialList.forEach(i=>{
            i.isError = 1;
          })
      });
    },
	successSubmit(flag) {
      this.open = flag;
      this.getList();
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
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: null,
        specialCode: null,
        revenueCode: null,
        revenueName: null,
        billDept: null,
        execDept: null,
        inpBill: null,
        inpExec: null,
        inpCare: null,
        outpBill: null,
        outpExec: null,
        outpCare: null,
        coopMed: null,
        accountType: null,
        convRatio: null,
        fixConv: null,
        projectCode: null,
        hisRevenumTypede: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
      this.single = selection.length!==1
      this.multiple = !selection.length
      this.selectionRow=selection
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
    rowStyle({
      row,
      rowIndex
    }) {
      Object.defineProperty(row, 'rowIndex', {
        value: rowIndex,
        writable: true,
        enumerable: false
      })
    },
    rowClassName({row, rowIndex}) {
      if(row && row.isError==0){
        return "warning-row "
      }else {
        if (rowIndex % 2 === 1) {
          return "striped";
        } else {
          return "";
        }
      }
    },
    /** 新增添加行 */
    handleAddTable() {
      let saveLoading = this.saveLoading();
      getSpecialCode(this.queryParams).then(response=>{
        let tablCol={
          id: response.data.id,
          compCode: this.$store.state.user.userInfo.deptId,
          specialCode: response.data.specialCode,
          revenueCode: null,
          revenueName: null,
          billDept: null,
          execDept: null,
          inpBill: null,
          inpExec: null,
          inpCare: null,
          outpBill: null,
          outpExec: null,
          outpCare: null,
          coopMed: null,
          convRatio: null,
          fixConv: null,
          projectCode: null,
          hisRevenumTypede: null,
          type: '1',
          isError: 1
        }
        if (this.checkedNum == '0') {
          tablCol={
            id: response.data.id,
            compCode: this.$store.state.user.userInfo.deptId,
            specialCode: response.data.specialCode,
            revenueCode: null,
            revenueName: null,
            billDept: null,
            execDept: null,
            inpBill: '100',
            inpExec: '100',
            inpCare: '0',
            outpBill: '100',
            outpExec: '100',
            outpCare: '0',
            coopMed: null,
            convRatio: null,
            fixConv: null,
            projectCode: null,
            hisRevenumTypede: null,
            type: '1',
            isError: 1
          }
        }
        this.specialList.push(tablCol);
        this.tableScrollLeft("multipleTable")
        saveLoading.close();
      })
    },
    handleAddDelete(idx,row) {
      this.specialList.splice(idx, 1);
      delSpecialTemp(row).then(()=>{

      })
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      this.open = true;
      this.addEditId = id; // id赋值
      console.log("addEditId---" + addEdit);
    },
    /** 刷新按钮操作 */
    handleRefresh() {
      this.$confirm('确定刷新当前数据吗，未保存数据即将清空！', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let saveLoading = this.saveLoading();
          refresh(this.queryParams).then(()=>{
            this.getList();
            this.msgSuccess("刷新成功");
            saveLoading.close();
          })
        }).catch(() => {});
    },
    changeRow(row){
      let saveLoading = this.saveLoading();
      updateSpecialTemp(row).then(()=>{
        saveLoading.close();
      })
    },
    checkedData(){
      if(this.checkedNum==1){
        if(this.specialList.length>0){
            this.specialList.forEach(_=>{
              _.inpNum=_.inpBill + _.inpExec + _.inpCare
              _.outpNum=_.outpBill + _.outpExec + _.outpCare
            if(_.inpNum==100 && _.outpNum==100){
              this.checkedNumFlag=true;
            }else{
              this.checkedNumFlag=false;
              _.isError=0;
              this.rowClassName(_)
            }
          })
          if(!this.checkedNumFlag){
            this.msgError("分摊比例设置系统参数设置为否，门诊与住院 开单+执行+护理应分别为100")
          }
        }else{
          this.checkedNumFlag=true;
        }
      }else{
        if(this.specialList.length>0){
          // this.specialList.forEach(_=>{
          //   _unNum=_.inpBill + _.outpBill + _.inpExec + _.outpExec;
          //   if(_unNum==100){
              this.checkedNumFlag=true;
          //   }else{
          //     this.checkedNumFlag=false;
          //   }
          // })
          // if(!this.checkedNumFlag){
          //   this.msgError("门诊和住院开单+执行应为100")
          // }
        }else{
          this.checkedNumFlag=true;
        }
      }
    },
    checkedNumFun(){
      if(this.specialList.length>0){
        this.specialList.forEach(_=>{
          _.sum=_.coopMed + _.fixConv;
          if(_.sum>100){
            this.isAddChecked=false;
            _.isError=0;
              this.rowClassName(_);
          }else{
            this.isAddChecked=true;
          }
        })
        if(!this.isAddChecked){
            this.msgError("合作医疗+固定折算比例不能大于100")
        }
      }else{
          this.isAddChecked=true;
      }
    },
    checkedDataItem(){
      if(this.specialList.length>0){
        this.specialList.forEach(_=>{
          if(_.revenueCode&&_.billDept &&_.execDept&&_.hisRevenumTypede){
            this.checkedItem=true;
            if(_.billDept == _.execDept){
              _.isError=0;
              this.isOnlyDeptCode=false;
              this.checkedItem=false;
            }else{
              this.isOnlyDeptCode=true;
              this.checkedItem=true;
              if(_.fixConv>0){
                if(_.projectCode){
                  this.fixConvBool=true;
                  this.checkedItem=true;
                }else{
                  this.fixConvBool=false;
                  this.checkedItem=false;
                  _.isError=0;
                  this.rowClassName(_)
                }
              }else{
                this.fixConvBool=true;
              }
            }
          }else{
            this.isOnlyDeptCode=true;
            this.checkedItem= false;
            this.fixConvBool=true;
            _.isError=0;
            this.rowClassName(_)
          }
        })
        if(!this.isOnlyDeptCode){
          this.msgError("开单科室与执行科室不能相同")
        }
        if(!this.fixConvBool){
          this.msgError("固定折算比例大于0，请选择成本对照")
        }
        if(!this.checkedItem && this.isOnlyDeptCode && this.fixConvBool){
          this.msgError("请检查数据是否填写完整")
        }
      }else{
        this.checkedItem= true;
      }
    },
    handleSave() {
        this.checkedData()
        if(this.checkedNumFlag){
          this.checkedNumFun();
          if(this.isAddChecked){
            this.checkedDataItem();
            if(this.checkedItem){
              let saveLoading = this.saveLoading();
              save(this.queryParams).then(()=>{
                refresh(this.queryParams).then(()=>{
                  this.getList();
                  this.msgSuccess("保存成功");
                  saveLoading.close();
                })
              })
            }
          }
        }
      }
    }
};
</script>
<style lang="scss" scoped>
::v-deep{
  .el-table__body-wrapper.is-scrolling-left{
      height:auto !important;
      max-height:calc(100vh - 326px) !important;
    }
    .el-table__body-wrapper.is-scrolling-middle{
      max-height:calc(100vh - 326px) !important;
      height:auto !important;
    }
    .el-table__body-wrapper.is-scrolling-right{
      height:auto !important;
      max-height:calc(100vh - 326px) !important;
    }
}
</style>

