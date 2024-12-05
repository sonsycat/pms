<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="70%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
    <el-form ref="form" :model="form" :inline="true" :rules="rules">
      <el-form-item label="科室分类"  prop="deptName" >
        <el-select class="search_w220" @change="getDeptQuery" :disabled ="idflag" v-model="form.deptName" placeholder="请选择科室分类">
          <el-option
            v-for="dict in deptTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="科室"  prop="deptCode" >
        <el-select class="search_w220" :loading="searchCoptLoading" v-model="form.deptCode" :disabled ="idflag" :remote="true" automatic-dropdown="" @change="ResultListDatas" :filterable="filterAble" placeholder="请选择科室">
          <el-option
            v-for="item in deptDatas"
            :key="item.dictValue"
            :label="item.dictLabel"
            :value="item.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年月" prop="nowDate">
        <el-date-picker class="search_w220" @change="ResultListDatas" :clearable="false" disabled
                        v-model="form.nowDate"
                        type="month"
                        value-format="yyyy-MM"
                        placeholder="选择年月"
        >
        </el-date-picker>
      </el-form-item>
      <el-row>
        <el-col :span="4" style="float:right;text-align:right;">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAddTable"
          >添加行</el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-table style="margin-top:20px" v-loading="loading" :data="form.costNumList" ref="costNumListTable" stripe max-height="600px">
            <el-table-column label="项目编码" align="center" prop="projectCode">
              <template slot-scope="scope">
                <orgDept :formProp="`costNumList.${scope.$index}.projectCode`" :datas="form" :compCode="form.compCode" :queryParams="scope.row"  @click="handleClickOrgPop(scope.$index,scope.row,$event)" :rules="rules.projectCode"  />
              </template>
            </el-table-column>
            <el-table-column label="项目名称" align="center" show-overflow-tooltip prop="projectName" />
            <el-table-column label="成本金额" align="center" show-overflow-tooltip prop="money">
              <template slot-scope="scope">
                <el-input-number :precision="2" controls-position="right" class="w100" :min="0" v-model="scope.row.money"  />
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
          </el-table>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm(false)" :loading="saveLoading" :disabled="isCountinus" >保存并继续添加</el-button>
      <el-button type="primary" @click="submitForm(true)" :loading="saveLoading" :disabled="isCountinus" >保存</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import { validEmail } from'@/utils/validate';
  import {getYearMonth2} from "@/utils/date";
  import orgDept from "@/views/components/orgDept";
  import { listNum,searchList ,searchPreList, getNum, delNum, addNum, updateNum, exportNum,searchForDeptType } from "@/api/project/num";
  import { setTimeout } from 'timers';
  import { getCostApport } from "@/api/system/dict/data"


  export default {
    name: "addEdit",
    props: {
      open: {
        type: Boolean,
        default: false
      },
      id: {
        type: [String, Number],
        default: ""
      },
      editData:{
        type: [Object,String],
        default: ""
      }

    },
    watch: {
      id: {
        handler(val) {
          this.reset();
          if (val) {
            this.id = val;
            this.title = "成本金额管理-编辑";
            this.idflag=true
            this.form.deptCode=this.editData.deptCode
            this.deptDatas.push({
              dictValue:this.editData.deptCode,
              dictLabel:this.editData.deptName
            })
            this.searchForDeptType()
            this.form.nowDate=this.editData.nowDate
            this.getList()
            // this.form.deptName=this.form.costNumList.deptName
          } else {
            this.id = "";
            this.idflag=false
            this.title = "成本金额管理-新增";
            this.getCostApport();
          }
        },
        deep: true,
        immediate: true
      },
      open: {
        handler(val) {
          this.openDialog = val;
        },
        deep: true
      }
    },
    components: {
      orgDept,
      getCostApport,
    },
    data() {
      return {
        title: "成本金额管理-新增",
        openDialog: this.open,
        saveLoading: false,
        idflag:'',
        // 表单参数
        form: {
          costNumList:[],
        },
        // 表单校验
        rules: {
          deptCode:[
            { required: true, message: "科室名称不能为空", trigger:"blur"}
          ],
          projectCode:[
            { required: true, message: "项目编码不能为空", trigger:["blur","change","focus"]}
          ],
        },
        filterAble:true,
        deptTypeOptions:[],
        deptDatas:[],
        isHaveSearchOption:false,
        isCountinus:null,
        isPassCheck:null,
        loading:false,
        searchCoptLoading:false,
        isInCome:true,
      };
    },
    created() {
      // this.getList();
      this.getDicts("cost_dept_type").then(response => {
        this.deptTypeOptions = response.data;
      });
    },
    methods: {
      getCostApport() {
        getCostApport(this.$store.state.user.accountInfo.deptId).then(response => {
          if (response.data) {
            let dataTime=response.data.split("-")[0]+'-'+response.data.split("-")[1]
            this.form.nowDate = dataTime
          }
        });
      },
      /** 查询成本项目管理列表 */
      getList() {
        this.loading = true;
        let searchQuery={
          deptCode: this.form.deptCode,
          nowDate:this.form.nowDate
        }
        listNum(searchQuery).then(response => {
          if(response.code===200){
            this.form.costNumList = response.rows;
            this.total = response.total;
            this.loading = false;
          }
        });
      },
      ResultListDatas(){
        this.getList()
      },
      handleAddDelete(idx) {
        this.form.costNumList.splice(idx, 1);
        // this.getList();
      },
      //科室查询科室类型
      searchForDeptType(){
        let query={
          deptCode: this.editData.deptCode,
          comeCode:this.$store.state.user.accountInfo.deptId,
        }
        searchForDeptType(query).then(response=>{
          this.form.deptName=response.msg
        })
      },
      //科室类型字典翻译
      deptTypeFormat(row, column) {
        return this.selectDictLabel(this.deptTypeOptions, row.deptType);
      },
      //科室类型查询科室获取
      getDeptQuery(value){
        this.searchCoptLoading=true
        let queryDatas={
          compCode: this.$store.state.user.accountInfo.deptId,
          type:value
        }
        searchList(queryDatas).then(response=>{
          if(response.code===200){
            this.deptDatas=response.data
            this.searchCoptLoading=false
          }
        })
      },
      handleClickOrgPop(idx,row,datas){
        let data = ["projectCode","projectName","money"];
        data.forEach(key => {
          this.$set(row, key, datas[key]);
        })
      },
      addMeth(){
        let tablCol = {
        };
        this.form.costNumList.push(tablCol);
        this.tableScrollLeft("costNumListTable")
      },
      /** 新增添加行 */
      handleAddTable() {
        let params={
          compCode: this.$store.state.user.accountInfo.deptId,
          nowDate:this.form.nowDate
        }
        searchPreList(params).then(response=>{
          if(response){
            this.isCountinus=false;
            this.$refs["form"].validate(valid => {
              if (valid) {
                let tablCol={
                  deptCode:this.form.deptCode,
                  nowDate:this.form.nowDate,
                  projectCode:null
                }
                this.form.costNumList.push(tablCol);
                this.tableScrollLeft("costNumListTable")
              }else{
                this.msgError("请输入必填项");
              }
            });
          }else{
            this.msgError("当前年月已经分摊不可以维护");
            this.isCountinus=true
          }
        })
      },
      checkList(){
        if(this.form.costNumList.length>0){
          let obj = {}
          this.form.costNumList.forEach((el,i)=>{
            if(obj.hasOwnProperty(el.projectCode)){
              this.form.costNumList.splice(i, 1);
              this.msgError('明细数据不能重复');
              this.isPassCheck=false;
            }else{
              obj[el.projectCode] = i;
              this.isPassCheck=true;
            }
          })
        }
      },
      // 弹框关闭
      handleDialogClose() {
        this.$emit("cancel", false);
      },
      // 取消
      cancel() {
        this.$emit("cancel", false);
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          compCode: null,
          accountCode: null,
          projectCode: null,
          projectName:null,
          costProperty: null,
          deptCode: null,
          deptName: null,
          nowDate: getYearMonth2(),
          money: null,
          delFlag: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          remark: null,
          orgCode:null,
          deptType:null,
          params:{},
          searchValue:null
        };
        this.resetForm("form");
      },
      /** 提交按钮 */
      submitForm(type) {
        this.$refs["form"].validate(valid => {
          if (valid) {
            let params={
              compCode: this.$store.state.user.accountInfo.deptId,
              nowDate:this.form.nowDate
            }
            searchPreList(params).then(response=>{
              if(response){
                this.saveLoading = true;
                this.checkList()
                if(!this.isPassCheck){
                  this.saveLoading=false
                  return
                }
                let costNumList=this.form.costNumList.map(ite=>{
                  if(ite.money==undefined){
                    this.msgError("请输入成本金额");
                    this.saveLoading = false;
                    return
                  }
                  return {
                    "compCode": this.$store.state.user.accountInfo.deptId,
                    "money": ite.money,
                    "projectCode": ite.projectCode
                  }
                })
                let obj={
                  "deptCode": this.form.deptCode,
                  "nowDate": this.form.nowDate,
                  "costNumList":costNumList
                }
                addNum(obj).then(response => {
                  if(this.id!==""){
                    if(type){
                      this.$emit("success", false);
                    }else{
                      this.$emit("successSubmit", true);
                      this.reset()
                    }
                    this.msgSuccess("修改成功");
                  }else{
                    if(type){
                      this.$emit("success", false);
                    }else{
                      this.$emit("successSubmit", true);
                      this.reset()
                    }
                    this.msgSuccess("新增成功");
                  }
                  this.saveLoading = false;
                });
              }else{
                this.msgError("当前年月已经分摊不可以维护");
                this.isCountinus=true
              }
            })
          }
        });
      },
    }};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    min-height: 20%;
    overflow: hidden;
    .el-form-item__content{
      margin-left: 0 !important
    }
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
      .el-input-number--mini{
        width: auto !important;
      }
    }
  }
</style>
