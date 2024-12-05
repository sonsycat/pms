<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch"  >
      <el-form-item label="年月" prop="nowDate">
        <el-date-picker
          :clearable="false"
          class="search_w220"
          v-model="queryParams.nowDate"
          type="month"
          value-format="yyyy-MM-dd"
          placeholder="选择年月"
          @change="changeQueryDate">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="科室类别" prop="deptType">
        <el-select class="search_w220" v-model="queryParams.deptType" clearable placeholder="请选择科室分类"  @change="getDeptQuery">
          <el-option
            v-for="dict in deptList"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="Number(dict.dictValue)"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="科室"  prop="deptCode" >
          <el-select class="search_w220" v-model="queryParams.deptCode" clearable automatic-dropdown="" :filterable="true" :remote="true" placeholder="请选择科室">
            <el-option
              v-for="item in deptDatas"
              :key="item.dictValue || item.deptRoomId"
              :label="item.dictLabel || item.deptRoomName"
              :value="item.dictValue || item.deptRoomId"
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
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['evaluate:setdept:edit']"
        >编 辑</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          :disabled="setdeptbtn"
          @click="handleSetDeptDetail"
          v-hasPermi="['evaluate:setdept:edit']"
        >科室指标设置</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-document-copy"
          size="mini"
          @click="copyEva"
          v-hasPermi="['evaluate:setdept:edit']"
        >复制</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table border stripe v-loading="loading" :height="tableHeight" :data="setdeptList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="科室名称" align="center" prop="deptName">
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" v-html="scope.row.deptName" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column sortable label="科室代码" align="center" prop="deptCode" />
      <el-table-column sortable label="指标集名称" align="center" prop="setName" />
      <el-table-column v-for="(item,index) in bscList" :key="index" :label="item.bscName+'总分'" :prop="item.bscCode" align="right">
        <template slot-scope="scope">
          {{scope.row[item.bscCode] == null ? 0 : scope.row[item.bscCode]}}
        </template>
      </el-table-column>
      <el-table-column sortable label="年月" align="center" prop="nowDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.nowDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

	<addEdit v-if="open" :open="open" :id="addEditId" :datas="datas" @cancel="cancel" @success="successSubmit"></addEdit>
  <deptIndexEdit v-if="openDeptIndex" :open="openDeptIndex" :datas="setDeptData" :id="addEditId" @cancel="cancelDeptDetail" @success="successSubmitDetail"></deptIndexEdit>

  <el-dialog title="单科室多指标-复制" class="copyEvaDialog" ref="copyEvaDialog" :visible.sync="openCopyEva" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="copyCancel">
    <el-form ref="copyForm" :model="copyForm" :rules="rules">
      <el-row>
        <el-col :span="24">
            <el-form-item label="复制年月" prop="nowDate">
              <el-date-picker clearable
                class="w90"
                v-model="copyForm.nowDate"
                type="month"
                value-format="yyyy-MM-dd"
                placeholder="选择年月"
                >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
                <!-- :picker-options="picOptFun" -->

            <el-form-item label="拷贝年月" prop="copyDate">
              <el-date-picker clearable
                class="w90"
                v-model="copyForm.copyDate"
                type="month"
                value-format="yyyy-MM-dd"
                placeholder="选择年月"
                @change="changeNowDate"
                :picker-options="picOptFun(copyForm.nowDate,copyForm.copyDate)"
                >
              </el-date-picker>
            </el-form-item>
          </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitcopyEvaForm">保 存</el-button>
        <el-button @click="copyCancel">取 消</el-button>
    </div>
  </el-dialog>


  </div>
</template>

<script>
import { listSetdept, getSetdept, delSetdept, addSetdept, updateSetdept, exportSetdept, copySetdept } from "@/api/evaluate/setdept";
import { listSetdeptdetail } from "@/api/evaluate/setdeptdetail";
import {searchList} from "@/api/project/num";
import { treeRoom } from "@/api/until";
import {getYearMonth2} from "@/utils/date";
import addEdit from "./addEdit";
import deptIndexEdit from '../setdeptdetail/index';
import { spdMclist } from '@/api/evaluate/bsc'

export default {
  name: "Setdept",
  components: {
    addEdit,
    deptIndexEdit
  },
  data() {
    return {
      tableHeight:'auto',
      // 遮罩层
      loading: false,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      setdeptbtn:true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 指标集与科室管理表格数据
      setdeptList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openDeptIndex:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode: this.$store.state.user.userInfo.deptId,
        deptType:null,
        deptCode: null,
        nowDate: getYearMonth2() + '-01',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        nowDate:[
          {required:true,message:'复制年月不能为空',trigger:['blur','change']}
        ],
        copyDate:[
          {required:true,message:'拷贝年月不能为空',trigger:['blur','change']}
        ]
      },
      deptList:[],
      openCopyEva:false,
      copyForm:{
        compCode: this.$store.state.user.userInfo.deptId,
        nowDate:null,
        copyDate:null
      },
      deptDatas:[],
      datas:{},
      setDeptData:{},
      bscList:{},
      bscData:{},
    };
  },
   watch:{
    listData(val) {
      var that = this;
      setTimeout(function() {
        that.resizeHandler();
      }, 300);
    }
  },
  computed: {
    listData() {
      return this.$store.state.app.sidebar.opened;
    }
  },
  created() {
    this.getBSC()
    this.getList();
    this.getDicts("cost_dept_type").then(response => {
      this.deptList = response.data;
    });
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
    this.getTreeDept(this.queryParams.compCode)
  },
  methods: {
    /** 查询BSC一级分类 */
    getBSC(){
      spdMclist({parentCode: '0'}).then(response => {
        this.bscList = response.data
      })
    },
    //超链接
    handleClickTableName(data) {
      // 选中当前行
      this.rowClick(data, false);
      this.handleUpdate(data);
    },
    /** 查询指标集与科室管理列表 */
    getList() {
      this.loading = true;
      listSetdept(this.queryParams).then(response => {
        this.setdeptList = response.rows;
        this.total = response.total;
         for (let i in this.setdeptList){
           for (let k in this.setdeptList[i].internalScore){
             for (let j in this.bscList){
               if (this.bscList[j].bscCode === this.setdeptList[i].internalScore[k].bscCode){
                 this.setdeptList[i][this.bscList[j].bscCode] = this.setdeptList[i].internalScore[k].score
               }
             }
           }
        }
        this.loading = false;
      });
    },
	  resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    //科室类型查询科室
    getDeptQuery(value){
      let queryDatas={
        compCode: this.queryParams.compCode,
        type:value
      }
      if(value){
          searchList(queryDatas).then(response=>{
          if(response.code===200){
            this.deptDatas=response.data
          }
        })
      }else{
        this.getTreeDept(this.queryParams.compCode)
      }
    },
    //获取科室列表
    getTreeDept(deptId) {
      let data = { deptId: deptId };
      treeRoom(data).then(response => {
        if (response.code == 200) {
          this.deptDatas = response.rows;
        } else {
          this.msgError(response.msg);
        }
      });
    },
    changeNowDate(val){
      this.picOptFun(val,this.copyForm.nowDate)
    },
    picOptFun(time1,time2){
        if(time1 && time2 && time1==time2){
          this.copyForm.copyDate=null;
          this.msgError("拷贝年月与复制年月不能相同")
        }
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: null,
        setDeptCode: null,
        setCode: null,
        setName:null,
        deptCode: null,
        deptName: null,
        deptType:null,
        internalScore: null,
        financeScore: null,
        serviceScore: null,
        efficientScore: null,
        nowDate: null,
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
      this.getTreeDept(this.queryParams.compCode)

    },
    resetCopy(){
      this.copyForm={
        compCode: this.$store.state.user.userInfo.deptId,
        nowDate: getYearMonth2() + '-01',
        copyDate: null,
      }
      this.resetForm("copyForm");
    },
    successSubmit(flag) {
      this.open = flag;
      this.getList();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      // this.reset();
      this.getList()
    },
    handleSetDeptDetail(row){
      const id = row.id || this.ids[0];
      this.addEditId = id; // id赋值
      this.setDeptData=this.selectionRow[0]
      this.openDeptIndex=true;
    },
    //科室指标设置
    cancelDeptDetail(){
      this.openDeptIndex=false;
      this.setdeptbtn=true;
       this.getList()
    },
    successSubmitDetail(flag){
      this.openDeptIndex=flag;
      this.setdeptbtn=true
      this.getList()
    },
    //复制按钮操作
    copyEva(){
      this.openCopyEva=true;
       this.resetCopy();
    },
    copyCancel(){
      this.resetCopy();
      this.openCopyEva=false;
      this.setdeptbtn=true
      this.getList();
    },
    subCopy(flag){
      this.resetCopy();
      this.openCopyEva=flag;
      this.setdeptbtn=true
      this.getList();
    },
    //复制保存按钮
    submitcopyEvaForm(){
      let params={
        compCode:this.queryParams.compCode,
        setDeptCode:null,
        nowDate:this.copyForm.copyDate,
      }
      listSetdeptdetail(params).then(response => {
        if(response.data.length==0){
           let saveLoading=this.saveLoading()
          copySetdept(this.copyForm).then(res=>{
            if(res.code==200){
              this.msgSuccess(res.msg);
              this.subCopy(false);
              saveLoading.close();
            }
          })
        }else{
          this.msgError("当前年月已有数据");
        }
      })

    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      if(selection.length==1){
        this.setdeptbtn=selection[0].setName?false:true
      }else{
        this.setdeptbtn=true
      }
      this.selectionRow=selection
      this.datas=selection[0]
    },
    rowClick(row, column, event) {
      let refsElTable = this.$refs.multipleTable;
      let findRow = this.selectionRow.find(c => c.rowIndex == row.rowIndex);
      // if (findRow && this.selectionRow.length == 1) {
      //   refsElTable.toggleRowSelection(row, false);
      //   return;
      // }
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
    rowClassName({
      row,
      rowIndex
    }) {
      let rowName = "",
        findRow = this.selectionRow.find(c => c.rowIndex === row.rowIndex);
      if (findRow) {
        rowName = "current-row ";
      }
      return rowName;
    },

    handleUpdate() {
      let id=this.datas.id;
      this.datas.nowDate = this.queryParams.nowDate
      if(id){
        this.addEditId=id
      }else{
        this.addEditId=""
      }
      this.open = true;
    },
    changeQueryDate(){
      this.queryParams.pageNum = 1;
      this.getList();
    }
    // /** 提交按钮 */
    // submitForm() {
    //   this.$refs["form"].validate(valid => {
    //     if (valid) {
    //       if (this.form.id != null) {
    //         updateSetdept(this.form).then(response => {
    //           this.msgSuccess("修改成功");
    //           this.open = false;
    //           this.getList();
    //         });
    //       } else {
    //         addSetdept(this.form).then(response => {
    //           this.msgSuccess("新增成功");
    //           this.open = false;
    //           this.getList();
    //         });
    //       }
    //     }
    //   });
    // },
  }
};
</script>
