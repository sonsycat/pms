<template>
  <div class="app-container">
    <el-dialog :title="title" :visible.sync="openDialog" width="60%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" class="edit_form">
        <el-row>
          <el-col :span="24">
            <p class="lit-title" >基础信息</p>
          </el-col>
          <el-col :span="12" style="margin-left:20%">
            <el-form-item label="模板名称：" prop="formName">
              <el-input v-model="form.formName" placeholder="请输入模板名称"  disabled/>
            </el-form-item>
            <el-form-item label="标题：" prop="title">
              <el-input v-model="form.title" placeholder="请输入标题" disabled/>
            </el-form-item>
            <el-form-item label="显示顺序：" prop="sortNo">
              <el-input-number controls-position="right" v-model="form.sortNo" placeholder="请输入显示顺序"/>
            </el-form-item>
            <el-form-item label="生成汇总数据：" prop="isSum">
              <el-radio-group v-model="form.isSum" disabled>
                <el-radio label="0">是</el-radio>
                <el-radio label="1">否</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="说明：" prop="remark">
              <el-input v-model="form.remark" type="textarea" rows="4" placeholder="请输入说明" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <p class="lit-title" >模板字段</p>
          </el-col>
        </el-row>
              <!-- v-hasPermi="['formDesign:formDesign:add']"   v-hasPermi="['formDesign:formDesign:edit']"-->
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="formDesign"
            >新 增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
            >编 辑</el-button>
          </el-col>
        </el-row>
        <el-table border v-loading="tableLoading" max-height="300px" :data="formDataList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="字段名称" align="center" prop="fieldDesc" />
          <el-table-column label="字段类型" align="center" prop="dbType" />
          <el-table-column label="字段属性" align="center" prop="fieldType" />
          <el-table-column label="字段布局" align="center" prop="span">
            <div slot-scope="scope">
              <el-input-number v-model="scope.row.span" controls-position="right" :min="0" :precision="1" />
            </div>
          </el-table-column>
        </el-table>
        <el-col :span="24">
          <p class="lit-title" >模板视图</p>
        </el-col>

        <el-table border v-loading="tableLoading" max-height="300px" :data="formDataList1" >
          <el-table-column label="字段名称" align="center" prop="fieldDesc" />
          <el-table-column label="是否在列表显示" align="center" prop="isList">
            <template slot-scope="scope">
              <el-select v-model="scope.row.isList" clearable placeholder="请选择是否显示" >
                <el-option label="是" value="1">是</el-option>
                <el-option label="否" value="0">否</el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="是否在编辑页显示" align="center" prop="isEdit">
            <template slot-scope="scope">
              <el-select v-model="scope.row.isEdit" clearable placeholder="请选择是否显示" >
                <el-option label="是" value="1">是</el-option>
                <el-option label="否" value="0">否</el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="是否查询列" align="center" prop="isQuery">
            <template slot-scope="scope">
              <el-select v-model="scope.row.isQuery" clearable placeholder="请选择是否查询列" @change="selectFieldDesc(scope.row)">
                <el-option label="是" value="1">是</el-option>
                <el-option label="否" value="0">否</el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="显示位置" align="center" prop="sortNo">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.sortNo" controls-position="right" />
            </template>
          </el-table-column>
          <el-table-column label="是否排序" align="center" prop="isSort" >
            <template slot-scope="scope">
              <el-select v-model="scope.row.isSort" clearable placeholder="请选择是否排序" >
                <el-option label="是" value="1">是</el-option>
                <el-option label="否" value="0">否</el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="排序方式" align="center" prop="soortType" >
            <template slot-scope="scope">
              <el-select v-model="scope.row.soortType" clearable placeholder="请选择排序方式" >
                <el-option
                v-for="dict in mbpxTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
              </el-select>
            </template>
          </el-table-column>
        </el-table>
        <el-col :span="14" style="margin-left:16%;margin-top:10px">
          <el-form-item label="列表筛选条件" prop="fieldDesc">
              <el-select class="w100" multiple clearable v-model="form.fieldDesc" placeholder="请选择列表筛选条件" @change="selectFieldDescTable" @remove-tag="removeFun" @clear="clearSelectItem">
                <el-option v-for="dict in allDataList"
                :key="dict.fieldName"
                :label="dict.fieldDesc"
                :value="dict.fieldName"></el-option>
              </el-select>
          </el-form-item>
        </el-col>

        <el-table border v-loading="tableLoading" max-height="300px" :data="formDataList2">
          <el-table-column label="字段名称" align="center" prop="fieldDesc" />
          <el-table-column label="条件" align="center" prop="queryType" >
            <template slot-scope="scope">
              <el-select v-model="scope.row.queryType" clearable placeholder="请选择条件" >
                <el-option
                v-for="dict in queryTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="筛选值" align="center" prop="queryDefault">
            <template slot-scope="scope">
              <el-input-number controls-position="right" v-if="scope.row.fieldName=='cs_score'" v-model="scope.row.queryDefault"/>
              <el-input v-else v-model="scope.row.queryDefault" />
            </template>
          </el-table-column>
        </el-table>

        <el-col :span="24">
          <p class="lit-title" >汇总设置</p>
        </el-col>

        <el-col :span="14" style="margin-left:16%;margin-top:10px">
          <el-form-item label="选择汇总字段" prop="fieldDesc1">
             <el-select class="w100" multiple v-model="form.fieldDesc1" clearable placeholder="请选择汇总字段" @change="selectFieldDescSum">
                <el-option v-for="dict in allDataList1"
                :key="dict.fieldName"
                :label="dict.fieldDesc"
                :value="dict.fieldName"></el-option>
              </el-select>
          </el-form-item>
        </el-col>

        <el-table border v-loading="tableLoading" max-height="300px" :data="formDataList3">
          <el-table-column label="字段名称" align="center" prop="fieldDesc" />
          <el-table-column label="汇总设置" align="center" prop="sumType">
            <template slot-scope="scope">
              <el-select v-model="scope.row.sumType" clearable placeholder="请选择条件" >
                <el-option
                v-for="dict in mbhzSzOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
              </el-select>
            </template>
          </el-table-column>
        </el-table>

        <el-col :span="24">
          <p class="lit-title" >页面设置</p>
        </el-col>

        <el-col :span="8"  style="margin-left:30%;margin-top:10px">
          <el-form-item label="每页显示条数" prop="pageNum" >
              <el-input-number v-model="form.pageNum" controls-position="right" :min="1" />
          </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm(true)">保 存</el-button>
        <el-button @click="close()">取 消</el-button>
      </div>
    </el-dialog>

    <addForm v-if="openFormMarking" :open="openFormMarking" :formMarkingId="itemID" @cancel="cancelFormMarking"></addForm>
    <editFormItem v-if="openEditForm" :open="openEditForm" :queryParams="editFormItemData" @cancel="cancelEditForm"></editFormItem>
  </div>
</template>

<script>
import { deployList ,saveDeployList} from '@/api/utils';
import {getFormDesign,getFormTableList,editAddFormDesign,setPageNo} from "@/api/formDesign/formDesign";
// import { optionselect as getDictOptionselect } from '@/api/system/dict/type';
import addForm from '@/views/components/formDesignAdd';
import editFormItem from './editFormItem';

export default {
  name: "EditModel",
  components:{
    addForm,
    editFormItem
  },
  props: {
    open: {
      type: Boolean,
      default: false
    },
    formDesignId: {
      type: String,
      default: ""
    },
  },
  watch: {
    formDesignId: {
      handler(val) {
        if (val) {
          this.formDesignId = val;
          this.title = "模板管理-编辑模板";
          this.getDetailForm();
          this.getFormTableList();
        }
      },
      deep: true,
      immediate: true
    },
    open: {
      handler(val) {
        this.openDialog = val;
      },
      deep: true,
      immediate: true
    },
  },
	data(){
        return{
          openFormMarking:false,
          openEditForm:false,
          itemID:"",
        	// 字典信息
        	dictOptions: [],
           	// 遮罩层
	      	loading: false,
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
	      	// 自定义表单表格数据
	      	formDesignList: [],
	      	// 弹出层标题
	      	title: "模板管理-编辑模板",
	      	// 是否显示弹出层
          openDialog: false,
	      	// 表单参数
	      	form: {},
          rules:{
            formName: [
              { required: true, message: "模板名称不能为空", trigger: "blur" }
            ],
            title: [
              { required: true, message: "标题不能为空", trigger: "blur" }
            ],
            pageNum:[
               { required: true, message: "每页显示条数不能为空", trigger: "blur"}
            ],
          },
          editTableBool:false,
          tableLoading:false,
          sub1Bool:false,
          sub2Bool:false,
          allDataList:[],
          allDataList1:[],
          formDataList:[],
          formDataList1:[],
          formDataList2:[],
          formDataList3:[],
          mbpxTypeOptions:[],
          selectionRow:[],
          editFormItemData:{},
          queryTypeOptions:[],
          mbhzSzOptions:[],
        }
    },
    created() {
      this.getDicts("pms_mbpx_type").then(response => {
        this.mbpxTypeOptions = response.data;
      });
      this.getDicts("pms_mbsx_tj").then(response => {
        this.queryTypeOptions = response.data;
      });
      this.getDicts("pms_mbhz_sz").then(response => {
        this.mbhzSzOptions = response.data;
      });
    },
    methods:{
      // 排序类型字典翻译
      mbpxTypeFormat(row, column) {
        return this.selectDictLabel(this.mbpxTypeOptions, row.apportType);
      },
      //编辑表单明细
      getDetailForm(){
        getFormDesign(this.formDesignId).then(response=>{
          if(response.code===200){
            this.form=response.data;
            this.form.pageNum=!response.data.pageNum?30:response.data.pageNum;
          }
        })
      },
      //编辑表格列表数据
      getFormTableList(){
        this.tableLoading=true;
        getFormTableList({formDesignId:this.formDesignId}).then(response=>{
          if(response.code===200){
           this.allDataList=JSON.parse(JSON.stringify(response.data.filter((item)=>{
             if(item.fieldName=='pms_guidance' || item.fieldName=='pms_result'){
               item.isEdit='0';
               item.isQuery='0';
               item.isList='1'
               item.isListQuery='0'
             }
             if(item.fieldName=='pms_state' || item.fieldName=='dept_track' || item.fieldName=="comp_code"){
               item.isEdit='0';
               item.isQuery='0';
               item.isList='0'
               item.isListQuery='0'
             }
             return item.isQuery==1 && item.isListQuery!=1
           })));
           this.allDataList1=JSON.parse(JSON.stringify(response.data));
           this.formDataList=JSON.parse(JSON.stringify(response.data.map(item=>{
             item.span=24;
             return item
           })));
           this.formDataList1=JSON.parse(JSON.stringify(response.data.map(item=>{
             if(item.fieldName=='pms_guidance' || item.fieldName=='pms_result'){
               item.isEdit='0';
               item.isQuery='0';
               item.isList='1'
               item.isListQuery='0'
             }
             if(item.fieldName=='pms_state' || item.fieldName=='dept_track' || item.fieldName=="comp_code"){
               item.isEdit='0';
               item.isQuery='0';
               item.isList='0'
               item.isListQuery='0'
             }
             return item
           })));
           this.formDataList2=JSON.parse(JSON.stringify(response.data.filter((item)=>{
             return item.isListQuery==1
           })));
           this.formDataList3=JSON.parse(JSON.stringify(response.data.filter((ite)=>{
             return ite.isSum==1
           })));
           this.tableLoading=false;
          }
        })
      },

      selectFieldDesc(val){
        if(val.isQuery=="0"){
          val.isListQuery="0";
        }
        this.allDataList=JSON.parse(JSON.stringify(this.formDataList1.filter((ite)=>{
             return ite.isQuery==1 && ite.isListQuery!=1
        })));
        this.formDataList2=JSON.parse(JSON.stringify(this.formDataList1.filter((ite)=>{
          return ite.isQuery==1 && ite.isListQuery==1
        })))

      },
      selectFieldDescTable(val){
        val.forEach(ite=>{
          this.allDataList.forEach((_,ind)=>{
            if(ite==_.fieldName){
              _.isListQuery=1;
              if(this.formDataList2.findIndex((item=>_.fieldName==item.fieldName))==-1){
                this.formDataList2.push(_);
                this.allDataList.splice(ind,1)
              }
            }
          })
        })
      },
      removeFun(val){
          this.formDataList2.forEach((_,ind)=>{
            if(val==_.fieldName){
                this.allDataList.push(_);
                this.formDataList2.splice(ind,1)
            }
          })
      },
      clearSelectItem(){
        this.allDataList=JSON.parse(JSON.stringify(this.formDataList1.filter((ite)=>{
             return ite.isQuery==1 && ite.isListQuery!=1
        })));
        this.formDataList2=JSON.parse(JSON.stringify(this.formDataList1.filter((ite)=>{
          return ite.isQuery==1 && ite.isListQuery==1
        })))
      },
      // selectFieldDescSum
      selectFieldDescSum(val){
        let arr=[]
        val.forEach(ite=>{
          this.allDataList1.forEach(_=>{
            if(ite==_.fieldName){
              if(arr.findIndex((item=>_.fieldName==item.fieldName))==-1){
                arr.push(_)
              }
            }
          })
        })
        this.formDataList3=arr
      },
      /** 新增操作打开表单设计页面 */
      formDesign() {
        this.itemID= this.formDesignId;
        this.openFormMarking=true;
      },
      cancelFormMarking(){
        this.openFormMarking=false;
        this.getFormTableList();
      },
      cancelEditForm(){
        this.openEditForm=false;
        this.getFormTableList();
      },
       /** 修改按钮操作 */
      handleUpdate() {
        this.editFormItemData=JSON.parse(JSON.stringify(this.selectionRow[0]));
        this.openEditForm=true;
      },
      /** 删除按钮操作 */
      // handleDelete(row) {
      //   const ids = row.id || this.ids;
      //   this.$confirm('是否确认删除分摊明细编号为"' + ids + '"的数据项?', "警告", {
      //       confirmButtonText: "确定",
      //       cancelButtonText: "取消",
      //       type: "warning"
      //     }).then(function() {
      //       // return delApportDetail(ids);
      //     }).then(() => {
      //       // this.getDetailList();
      //       this.msgSuccess("删除成功");
      //     }).catch(() => {});
      // },
       // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        if(selection[0]&&selection[0].fieldName=='comp_code'){
          this.single=true;
        }else{
          this.single = selection.length!==1
          this.multiple = !selection.length
          this.selectionRow=selection;
        }
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
      chackedSubData(data){
        if(data.length!=0){
          let data1=data.filter(_=>_.fieldName=="cs_dept");
          let data2=data.filter(_=>_.fieldName=="cs_date");
          let data3=data.filter(_=>_.fieldName=="cs_target");
          let data4=data.filter(_=>_.fieldName=="cs_score");
          if(data1.length!==0 && data2.length!==0 && data3.length!==0 && data4.length!==0){
            this.sub2Bool=true;
          }else{
            this.sub2Bool=false;
            this.msgError("字段名称必须包含 日期，部门，指标，分值");
            return
          }
        }else{
          this.msgError("请先配置数据")
        }
      },
	    /** 提交按钮 */
      submitForm(type) {
        this.$refs["form"].validate(valid => {
          if (valid) {
            let fdcList=[],count=0;
            let pageData={
              id:this.formDesignId,
              pageNum:this.form.pageNum
            }
            for(let i = 0; i < this.formDataList.length; i++) {
                let tempArr1 = this.formDataList[i]
                for(let j = 0; j < this.formDataList1.length; j++) {
                  let tempArr2 = this.formDataList1[j];
                  if(tempArr2.id === tempArr1.id){
                    tempArr1.isList=tempArr2.isList;
                    tempArr1.isEdit=tempArr2.isEdit;
                    tempArr1.isQuery=tempArr2.isQuery;
                    tempArr1.sortNo=tempArr2.sortNo;
                    tempArr1.isSort=tempArr2.isSort;
                    tempArr1.soortType=tempArr2.soortType;
                    break;
                  }
                }
                for(let k=0;k<this.formDataList2.length;k++){
                    let tempArr2 = this.formDataList2[k];
                    if(tempArr2.id === tempArr1.id){
                      tempArr1.queryType=tempArr2.queryType;
                      tempArr1.queryDefault=tempArr2.queryDefault;
                      tempArr1.isListQuery=1;
                      break;
                  }
                }
                for(let k=0;k<this.formDataList3.length;k++){
                    let tempArr2 = this.formDataList3[k];
                    if(tempArr2.id === tempArr1.id){
                      tempArr1.sumType=tempArr2.sumType;
                      tempArr1.isSum=1;
                      break;
                  }
                }
                fdcList.push(tempArr1)
            }
            //判断必填字段
            this.chackedSubData(fdcList);
            fdcList.forEach(_=>{
                if(_.isSort=="1"){
                  count++;
                }
                if(count>2){
                  this.sub1Bool=false;
                }else{
                  this.sub1Bool=true;
                }
            })
            if(!this.sub1Bool && fdcList.length!=0){
                this.msgError("仅支持两个排序字段");
                return
            }
            if(this.sub1Bool && this.sub2Bool){
              let saveLoading=this.saveLoading()
              let data = {fdcList:fdcList, id:this.formDesignId, sortNo:this.form.sortNo, remarks:this.form.remarks}
              editAddFormDesign(data).then(response => {
                if(response.code===200){
                  setPageNo(pageData).then(response=>{
                    if(response.code===200){
                      this.msgSuccess("保存成功");
                      this.close();
                      saveLoading.close();
                    }
                  })
                }
              })
            }
          }
        })

      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          formName: null,
          title:null,
          formJson: null,
          createBy: null,
          createDate: null,
          updateBy: null,
          updateDate: null,
          remarks: null,
          remark:null,
          pageNum:30,
          delFlag: null,
          formCode: null,
          genTableName: null,
          formUrl: null,
          formType: null,
          isSum:null,
          sortNo:null,
        };
        this.resetForm("form");
      },

      // 弹框关闭
      handleDialogClose() {
        this.$emit("cancel", false);
      },
      /** 关闭按钮 */
      close() {
        this.$emit("cancel", false);
      },
      // //利用单元格的 className 的回调方法，给行列索引赋值
      // tableCellClassName({ row, column, rowIndex, columnIndex }) {//注意这里是解构
      //   row.index = rowIndex
      //   column.index = columnIndex
      // },
    },
}

</script>
<style lang="scss" scoped>
::v-deep .el-dialog{
  min-height: 10%;
  overflow: hidden;
  .el-dialog__body {
    max-height: 700px;
    overflow: auto;
    &::-webkit-scrollbar {
      width: 8px;
      height: 8px
    }
    &::-webkit-scrollbar-thumb  {
      background-color: #cccccc;
      border-radius: 8px;
    }
    .lit-title{
      font-size: 16px;
      padding-bottom: 8px;
      margin: 20px 0;
      border-bottom: 1px dashed #cccccc;
    }
    .edit_form{
        .el-input-number--mini{
          width: 100%;
        }
        .el-form-item__label{
          width: 140px !important;
        }
        .el-form-item__content{
          margin-left: 140px !important;
        }
    }
  }
  .el-table{
    max-height: 300px;
    overflow: hidden;
  }
}

</style>
