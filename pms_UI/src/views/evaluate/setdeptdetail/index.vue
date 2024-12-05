<template>
  <div class="app-container">
	  <el-dialog :title="title" :visible.sync="openDialog" width="80%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="queryForm" :model="datas">
        <el-row>
          <el-col :span="24">
            <el-col :span="6">
              <el-form-item label="科室名称" prop="deptName">
                <el-input
                  v-model="datas.deptName"
                  placeholder="请输入科室名称"
                  disabled
                />
              </el-form-item>
            </el-col>
          </el-col>
          <div class="table-box">
            <div class="table-box-body" v-for="(item,index) in bscList" :key="index">
              <el-col :span="4">
                <el-form-item :label="item.bscName + '-总分'" :prop="item.bscCode">
                  <el-input-number
                    v-model="item.score"
                    placeholder="总分"
                    :precision="2" controls-position="right"
                  />
                </el-form-item>
              </el-col>
              <el-table border stripe v-loading="loading" :data="item.setdeptdetailList" height="88%">
                <el-table-column min-width="140px" sortable label="指标名称" align="center" prop="indexName" show-overflow-tooltip />
                <el-table-column min-width="140px" sortable label="降低量" align="center" prop="reduce">
                  <template slot-scope="scope">
                    <el-input-number :precision="2" controls-position="right" :min="0" :max="100" v-model="scope.row.reduce" class="w100"></el-input-number>
                  </template>
                </el-table-column>
                <el-table-column min-width="140px" sortable label="扣分" align="center" prop="deduct">
                  <template slot-scope="scope">
                    <el-input-number :precision="2" controls-position="right" :max="0" v-model="scope.row.deduct" class="w100"></el-input-number>
                  </template>
                </el-table-column>
                <el-table-column min-width="140px" sortable label="增加量" align="center" prop="increase">
                  <template slot-scope="scope">
                    <el-input-number :precision="2" controls-position="right" :min="0" v-model="scope.row.increase" class="w100"></el-input-number>
                  </template>
                </el-table-column>
                <el-table-column min-width="140px" sortable label="加分" align="center" prop="extra" >
                  <template slot-scope="scope">
                    <el-input-number :precision="2" controls-position="right" :min="0" v-model="scope.row.extra" class="w100"></el-input-number>
                  </template>
                </el-table-column>
                <el-table-column min-width="120px" sortable label="单位(%)" align="center" prop="unit">
                  <template slot-scope="scope">
                    <el-select v-model="scope.row.unit" filterable clearable placeholder="请选择" >
                      <el-option
                        v-for="dict in isUnitOptions"
                        :key="dict.dictValue"
                        :label="dict.dictLabel"
                        :value="dict.dictValue"
                      ></el-option>
                    </el-select>
                  </template>
                </el-table-column>
                <el-table-column min-width="140px" sortable label="目标值" align="center" prop="target">
                  <template slot-scope="scope">
                    <el-input-number :precision="2" controls-position="right" v-model="scope.row.target" class="w100"></el-input-number>
                  </template>
                </el-table-column>
                <el-table-column min-width="140px" sortable label="指标分数" align="center" prop="indexScore">
                  <template slot-scope="scope">
                    <el-input-number :precision="2" controls-position="right" :min="0" v-model="scope.row.indexScore" class="w100" @change="changeIndexScore(scope.row,scope.$index)"></el-input-number>
                  </template>
                </el-table-column>
                <el-table-column min-width="120px" sortable label="允许超分" align="center" prop="isHyper">
                  <template slot-scope="scope">
                    <el-select v-model="scope.row.isHyper" filterable clearable placeholder="请选择" >
                      <el-option
                        v-for="dict in isHyperOptions"
                        :key="dict.dictValue"
                        :label="dict.dictLabel"
                        :value="dict.dictValue"
                      ></el-option>
                    </el-select>
                  </template>
                </el-table-column>
                <el-table-column min-width="120px" sortable label="允许负分" align="center" prop="isNegative">
                  <template slot-scope="scope">
                    <el-select v-model="scope.row.isNegative" filterable clearable placeholder="请选择" >
                      <el-option
                        v-for="dict in isNegativeOptions"
                        :key="dict.dictValue"
                        :label="dict.dictLabel"
                        :value="dict.dictValue"
                      ></el-option>
                    </el-select>
                  </template>
                </el-table-column>
                <el-table-column min-width="120px" sortable label="固定分值" align="center" prop="isFixed">
                  <template slot-scope="scope">
                    <el-select v-model="scope.row.isFixed" filterable clearable placeholder="请选择" >
                      <el-option
                        v-for="dict in isFixedOptions"
                        :key="dict.dictValue"
                        :label="dict.dictLabel"
                        :value="dict.dictValue"
                      ></el-option>
                    </el-select>
                  </template>
                </el-table-column>
                <el-table-column min-width="140px" sortable label="阀值" align="center" prop="threshold">
                  <template slot-scope="scope">
                    <el-input-number :precision="2" controls-position="right" :min="1" v-model="scope.row.threshold" class="w100"></el-input-number>
                  </template>
                </el-table-column>
                <el-table-column min-width="140px" sortable label="超分限制" align="center" prop="overLimit">
                  <template slot-scope="scope">
                    <el-input-number :precision="2" controls-position="right" :min="0" :max="100" v-model="scope.row.overLimit" class="w100"></el-input-number>
                  </template>
                </el-table-column>
                <el-table-column min-width="140px" sortable label="减分限制" align="center" prop="deductionLimit">
                  <template slot-scope="scope">
                    <el-input-number :precision="2" controls-position="right" :min="0" :max="100" v-model="scope.row.deductionLimit" class="w100"></el-input-number>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm" :loading="btnLoading">保 存</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
	  </el-dialog>
  </div>
</template>

<script>
import {
  listSetdeptdetail,
  getSetdeptdetail,
  updateSetdeptdetail,
  getBscScoreById
} from '@/api/evaluate/setdeptdetail'
import { getConfigKey } from '@/api/system/config'
import { updateSetdept } from '@/api/evaluate/setdept'
import Vue from 'vue'

export default {
  name: "Setdeptdetail",
  components: {
  },
  props: {
    open: {
        type: Boolean,
        default: false
    },
    id: {
        type: [String, Number],
        default: ""
    },
    datas: {
      type: [Object],
      default: null
    },
  },
  watch: {
    id: {
      handler(val) {
          this.reset();
        if (val) {
          this.id = val;
          this.title = "单科室多指标管理-科室指标设置";
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
      },
  },
  data() {
    return {
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
      // 指标集与科室管理明细表格数据
      setdeptdetailList: [],
      selectionRow: [],
      openDialog:this.open,
      // 弹出层标题
      title: "单科室多指标管理-科室指标设置",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode: null,
        accountCode: null,
        setDeptCode: null,
        indexCode: null,
        indexName: null,
        reduce: null,
        deduct: null,
        increase: null,
        extra: null,
        unit: null,
        target: null,
        indexScore: null,
        isHyper: null,
        isNegative: null,
        isFixed: null,
        threshold: null,
        overLimit: null,
        deductionLimit: null,
        nowDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      isUnitOptions:[],
      isHyperOptions:[],
      isNegativeOptions:[],
      isFixedOptions:[],
      btnLoading:false,
      bscList:{},
      roleConfig:null,
    };
  },
  created() {
    this.getBscScoreById();
    //是否字典类型
    this.getDicts("hr_is_change").then(response => {
      this.isUnitOptions = response.data;
      this.isHyperOptions = response.data;
      this.isNegativeOptions = response.data;
      this.isFixedOptions = response.data;
    });
    this.getConfigKey();
  },
  methods: {
    /** 查询系统参数：是否允许超分 */
    getConfigKey(){
      getConfigKey("exceedScoreFlag").then(response => {
        this.roleConfig = response.msg;
      })
    },
    /** 查询该科室对应BSC分类及总分 */
    getBscScoreById(){
      getBscScoreById(this.id).then(response => {
        this.bscList = response.data
        this.getList()
      })
    },
    /** 查询指标集与科室管理明细列表 */
    getList() {
      this.loading = true;
      let params={
        compCode:this.datas.compCode,
        setDeptCode:this.datas.setDeptCode,
        nowDate:this.datas.nowDate,
      }
      this.setdeptdetailList=[]
      listSetdeptdetail(params).then(response => {
        this.loading = false;
        if(response.data.length==0){
          this.getDetailInfo();
        }else{
          response.data.forEach(item=>{
            item.reduce=item.reduce?item.reduce:0;
            item.deduct=item.deduct?item.deduct:0;
            item.increase=item.increase?item.increase:0;
            item.extra=item.extra?item.extra:0;
            item.unit=item.unit?item.unit:'0';
            item.target=item.target?item.target:0;
            item.indexScore=item.indexScore?item.indexScore:0;
            item.isHyper=item.isHyper?item.isHyper:'1';
            item.isNegative=item.isNegative?item.isNegative:'1';
            item.isFixed=item.isFixed?item.isFixed:'1';
            item.threshold=item.threshold?item.threshold:0;
            item.overLimit=item.overLimit?item.overLimit:0;
            item.deductionLimit=item.deductionLimit?item.deductionLimit:0;
            for (let bsc in this.bscList){
              if (item.bscOneCode == this.bscList[bsc].bscCode){
                this.setdeptdetailList.push(item)
                this.bscList[bsc].setdeptdetailList = this.setdeptdetailList
              }
              this.setdeptdetailList = []
            }
          })
        }
      });
    },
    getDetailInfo() {
      this.loading=true;
      let params={
        compCode:this.datas.compCode,
        setCode:this.datas.setCode,
      }
      getSetdeptdetail(params).then(response => {
        response.data.forEach(item=>{
          item.reduce=item.reduce?item.reduce:0;
          item.deduct=item.deduct?item.deduct:0;
          item.increase=item.increase?item.increase:0;
          item.extra=item.extra?item.extra:0;
          item.unit=item.unit?item.unit:'0';
          item.target=item.target?item.target:0;
          item.indexScore=item.indexScore?item.indexScore:0;
          item.isHyper=item.isHyper?item.isHyper:'1';
          item.isNegative=item.isNegative?item.isNegative:'1';
          item.isFixed=item.isFixed?item.isFixed:'1';
          item.threshold=item.threshold?item.threshold:0;
          item.overLimit=item.overLimit?item.overLimit:0;
          item.deductionLimit=item.deductionLimit?item.deductionLimit:0;
          for (let bsc in this.bscList){
            if (item.bscOneCode == this.bscList[bsc].bscCode){
              this.setdeptdetailList.push(item)
              this.bscList[bsc].setdeptdetailList = this.setdeptdetailList
            }
            this.setdeptdetailList = []
          }
        })
        this.loading=false
      });
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: null,
        accountCode: null,
        setDeptCode: null,
        indexCode: null,
        indexName: null,
        reduce: null,
        deduct: null,
        increase: null,
        extra: null,
        unit: null,
        target: null,
        indexScore: null,
        isHyper: null,
        isNegative: null,
        isFixed: null,
        threshold: null,
        overLimit: null,
        deductionLimit: null,
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
    changeIndexScore(row,index){
      if(row.bscOneCode=='0004'){
        this.four_sumGrade=0
        this.setdeptdetailList3.forEach(item=>{
          this.four_sumGrade+=item.indexScore * 1
        })
      }else if(row.bscOneCode=='0003'){
        this.third_sumGrade=0
        this.setdeptdetailList2.forEach(item=>{
          this.third_sumGrade+=item.indexScore * 1
        })
      }else if(row.bscOneCode=='0002'){
        this.seconde_sumGrade=0
        this.setdeptdetailList1.forEach(item=>{
          this.seconde_sumGrade+=item.indexScore * 1
        })
      }else if(row.bscOneCode=='0001'){
        this.first_sumGrade=0
        this.setdeptdetailList.forEach(item=>{
          this.first_sumGrade+=item.indexScore * 1
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

    /** 提交按钮 */
    submitForm() {
      this.btnLoading = true;
      let arr1 = []
      for (let i in this.bscList) {
        arr1.push(...this.bscList[i].setdeptdetailList)
      }
      let arr = arr1.map(item => {
        item.reduce = item.reduce ? item.reduce + '' : '0';
        item.deduct = item.deduct ? item.deduct + '' : '0';
        item.increase = item.increase ? item.increase + '' : '0';
        item.extra = item.extra ? item.extra + '' : '0';
        item.target = item.target ? item.target + '' : '0';
        item.indexScore = item.indexScore ? item.indexScore + '' : '0';
        item.threshold = item.threshold ? item.threshold + '' : '0';
        item.overLimit = item.overLimit ? item.overLimit + '' : '0';
        item.deductionLimit = item.deductionLimit ? item.deductionLimit + '' : '0';
        return item
      });
      // 校验指标分和总分大小关系
      let targetSum = 0;
      for (let b in this.bscList) {
        for (let a in arr) {
          if (arr[a].bscOneCode == this.bscList[b].bscCode) {
            targetSum = targetSum + (arr[a].target - 0)
            arr[a].sumScore = this.bscList[b].score
          }
        }
        if (this.roleConfig == '0') {
          if (targetSum > this.bscList[b].score || targetSum != this.bscList[b].score) {
            this.$message.error("不允许超分或同类型下指标目标值和不等于总分！");
            this.btnLoading = false;
            return
          }
        } else if (this.roleConfig == '1') {
          if (targetSum < this.bscList[b].score) {
            this.$message.error("同类型下指标目标值和小于总分！");
            this.btnLoading = false;
            return
          }
        }
        targetSum = 0;
      }
      let params = {
        setDeptCode: this.datas.setDeptCode,
        nowDate: this.datas.nowDate,
        listPms: arr,
        compCode: this.datas.compCode
      };
      for (let i in this.bscList){
        Vue.delete(this.bscList[i],"setdeptdetailList")
      }
      let paramsBsc = {
        id:this.id,
        compCode: this.datas.compCode,
        deptCode: this.datas.deptCode,
        internalScore: JSON.stringify(this.bscList)
      };
      let saveLoading = this.saveLoading()
      // 保存总分
      updateSetdept(paramsBsc).then(response => {});
      // 保存明细
      updateSetdeptdetail(params).then(response => {
        this.msgSuccess("保存成功");
        this.$emit("success", false);
        this.btnLoading = false;
        saveLoading.close()
      });
    }
  }
};
</script>
<style lang="scss" scoped>
::v-deep .el-dialog {
    height: 80%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: hidden;
      .table-box{
        height:540px;
        overflow: auto;
        margin-top: 46px;
        &::-webkit-scrollbar {
          width: 8px;
          height: 8px
        }
        &::-webkit-scrollbar-thumb  {
          background-color: #cccccc;
          border-radius: 8px;
        }
        .table-box-body{
          height: 400px;
          padding-top: 16px;
          border: 1px solid #cccccc
        }
      }
    }
  }

</style>

