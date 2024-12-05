<template>
     <div class="app-container">
            <div ref="search">
                <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
                    <el-form-item :label="label" prop="nowDate">
                        <el-date-picker
                        :clearable="false"
                        class="search_w220"
                        v-model="queryParams.nowDate"
                        :type="type"
                        :value-format="forMat"
                        placeholder="请选择">
                        </el-date-picker>
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
                    type="primary"
                    icon="el-icon-box"
                    size="mini"
                    @click="handleSave"
                    >保存</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                    size="mini"
                    type="primary"
                    icon="el-icon-refresh"
                    @click="handleRefresh"
                    >刷新</el-button>
                </el-col>

                <right-toolbar :showSearch.sync="showSearch" @queryTable="getOneDeptListFun(selectionMemberRow)"></right-toolbar>
                </el-row>
	        </div>
                <el-row :gutter="20">
                    <el-col :span="6">
                        <el-card>
                        <el-table
                            ref="menmberTable"
                            style="margin-top:10px"
                            v-loading="evaTreeListLoading"
                            :data="evaTreeList"
                            :height="leftHeight"
                            row-key="id"
                            :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
                             @row-click="rowMemberClick"
                            @selection-change="handleSelectionMemberChange"
                            :row-style="rowStyle1"
                        >
                            <el-table-column type="selection" :selectable="selectableTota" width="55" align="center" />
                            <el-table-column label="指标分类树" align="left" prop="bscName" show-overflow-tooltip/>
                        </el-table>
                        </el-card>
                    </el-col>
                    <el-col :span="18">
                     <el-table border stripe v-loading="loading" :height="tableHeight" :data="setdeptdetailList">
                            <el-table-column min-width="140px" sortable label="科室名称" align="center" prop="deptName" show-overflow-tooltip />
                            <el-table-column min-width="140px" sortable label="科室编码" align="center" prop="deptCode" />
                            <el-table-column min-width="140px" sortable label="降低量" align="center" prop="reduce">
                            <template slot-scope="scope">
                                <el-input-number :precision="2" controls-position="right" :min="0" v-model="scope.row.reduce" class="w100"></el-input-number>
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
                            <el-table-column min-width="140px" sortable label="指标分数" align="center" prop="indexScore">
                            <template slot-scope="scope">
                                <el-input-number :precision="2" controls-position="right" :min="0" v-model="scope.row.indexScore" class="w100"></el-input-number>
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
                                    <el-input-number :precision="2" controls-position="right" :min="0" v-model="scope.row.threshold" class="w100"></el-input-number>
                                </template>
                            </el-table-column>
                            <el-table-column min-width="140px" sortable label="超分限制" align="center" prop="overLimit">
                                <template slot-scope="scope">
                                    <el-input-number :precision="2" controls-position="right" v-model="scope.row.overLimit" class="w100"></el-input-number>
                                </template>
                            </el-table-column>
                            <el-table-column min-width="140px" sortable label="减分限制" align="center" prop="deductionLimit">
                                <template slot-scope="scope">
                                    <el-input-number :precision="2" controls-position="right" v-model="scope.row.deductionLimit" class="w100"></el-input-number>
                                </template>
                            </el-table-column>
                            <el-table-column v-if="isYear" min-width="140px" sortable label="1月目标值" align="center" prop="oneTarget">
                                <template slot-scope="scope">
                                    <el-input type="number" v-model="scope.row.oneTarget" class="w100"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column v-if="isYear" min-width="140px" sortable label="2月目标值" align="center" prop="twoTarget">
                                <template slot-scope="scope">
                                    <el-input type="number" v-model="scope.row.twoTarget" class="w100"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column v-if="isYear" min-width="140px" sortable label="3月目标值" align="center" prop="threeTarget">
                                <template slot-scope="scope">
                                    <el-input type="number" v-model="scope.row.threeTarget" class="w100"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column v-if="isYear" min-width="140px" sortable label="4月目标值" align="center" prop="fourTarget">
                                <template slot-scope="scope">
                                    <el-input type="number" v-model="scope.row.fourTarget" class="w100"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column v-if="isYear" min-width="140px" sortable label="5月目标值" align="center" prop="fiveTarget">
                                <template slot-scope="scope">
                                    <el-input type="number" v-model="scope.row.fiveTarget" class="w100"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column v-if="isYear" min-width="140px" sortable label="6月目标值" align="center" prop="sixTarget">
                                <template slot-scope="scope">
                                    <el-input type="number" v-model="scope.row.sixTarget" class="w100"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column v-if="isYear" min-width="140px" sortable label="7月目标值" align="center" prop="sevenTarget">
                                <template slot-scope="scope">
                                    <el-input type="number" v-model="scope.row.sevenTarget" class="w100"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column v-if="isYear" min-width="140px" sortable label="8月目标值" align="center" prop="eightTarget">
                                <template slot-scope="scope">
                                    <el-input type="number" v-model="scope.row.eightTarget" class="w100"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column v-if="isYear" min-width="140px" sortable label="9月目标值" align="center" prop="nineTarget">
                                <template slot-scope="scope">
                                    <el-input type="number" v-model="scope.row.nineTarget" class="w100"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column v-if="isYear" min-width="140px" sortable label="10月目标值" align="center" prop="tenTarget">
                                <template slot-scope="scope">
                                    <el-input type="number" v-model="scope.row.tenTarget" class="w100"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column v-if="isYear" min-width="140px" sortable label="11月目标值" align="center" prop="elevenTarget">
                                <template slot-scope="scope">
                                    <el-input type="number" v-model="scope.row.elevenTarget" class="w100"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column v-if="isYear" min-width="140px" sortable label="12月目标值" align="center" prop="twelveTarget">
                                <template slot-scope="scope">
                                    <el-input type="number" v-model="scope.row.twelveTarget" class="w100"></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column v-if="!isYear" min-width="140px" sortable label="目标值" align="center" prop="target">
                                <template slot-scope="scope">
                                    <el-input type="number" v-model="scope.row.target" class="w100"></el-input>
                                </template>
                            </el-table-column>

                        </el-table>
                        <pagination
                            v-show="total>0"
                            :total="total"
                            :page.sync="queryParams.pageNum"
                            :limit.sync="queryParams.pageSize"
                            @pagination="getOneDeptListFun(selectionMemberRow)"
                        />
                    </el-col>
                </el-row>

    </div>
</template>

<script>
import {getIndicatorList } from "@/api/evaluate/index";
import { getOneDeptList, saveOneDept, getOneDeptMonthList, saveOneMonthDept } from "@/api/evaluate/setdept";
import {getNowYear,getYearMonth2} from "@/utils/date";


export default {
    name: 'index',
    data() {
        return {
            tableHeight:'auto',
            leftHeight:'auto',
            loading:false,
            isYear:null,
            label:'',
            forMat:'',
            type:'',
            form:{},
            rules:{},
            showSearch:true,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 30,
                compCode: this.$store.state.user.userInfo.deptId,
                nowDate:null,
            },
             //指标分类树
            evaTreeList:[],
            isUnitOptions:[],
            isHyperOptions:[],
            isNegativeOptions:[],
            isFixedOptions:[],
            setdeptdetailList:[],
            total:0,
            selectionMemberRow:[],
            setType: this.$route.query.m
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
          console.log("rrttt"+this.$route.query.m)
            return this.$store.state.app.sidebar.opened;
        }
    },
    created(){
        this.getConfigKey("DKSDZB").then(response => {
           if(response.msg=='N'){
               this.isYear=true;
           }else if(response.msg=='Y'){
               this.isYear=false;
           }
           if(this.isYear){
               this.label="年";
               this.forMat="yyyy";
               this.type='year';
               this.queryParams.nowDate=getNowYear()
           }else{
               this.label="年月";
               this.forMat="yyyy-MM";
               this.type='month';
               this.queryParams.nowDate=getYearMonth2()
           }
        });

        this.getList();
        //是否字典类型
        this.getDicts("hr_is_change").then(response => {
            this.isUnitOptions = response.data;
            this.isHyperOptions = response.data;
            this.isNegativeOptions = response.data;
            this.isFixedOptions = response.data;
        });
        this.$nextTick(() => {
            this.resizeHandler();
            window.addEventListener("resize", this.resizeHandler);
        });
    },
    mounted() {

    },

    methods: {
        resizeHandler() {
            let a = this.$refs.search.offsetHeight;
            let b = this.$refs.btns.offsetHeight;
            let h = a + b + 18 + 45 + 84 + 40 + 20;
            let lh=h + 47
            h = h + "px";
            lh=lh + "px";
            this.tableHeight = `calc(100vh - ${h})`;
            this.leftHeight = `calc(100vh - ${lh})`;
        },
        /** 搜索按钮操作 */
        handleQuery() {
            this.queryParams.pageNum = 1;
            this.getOneDeptListFun(this.selectionMemberRow);
        },
        /** 重置按钮操作 */
        resetQuery() {
            this.queryParams.nowDate=this.isYear?getNowYear():getYearMonth2()
            this.handleQuery();
            this.setdeptdetailList=[]
        },
        //指标分类树
        getList(){
            let data={compCode:this.queryParams.compCode}
            this.evaTreeListLoading=true;
            getIndicatorList(data).then(response => {
                response.data.forEach(item=>{
                    item.id=item.bscCode
                })
                this.evaTreeList = this.handleTree(
                response.data,
                "bscCode",
                "parentCode"
                );
            this.evaTreeListLoading = false;
            this.setdeptdetailList=[]
        });
        },
        //获取右侧指标关联科室数据
        getOneDeptListFun(data){
            if(data.length!==0){
                this.setdeptdetailList=[]
                let params={
                    compCode:this.queryParams.compCode,
                    indexCode:data[0].bscCode,
                    nowDate:this.queryParams.nowDate + '-01' + '-01'
                }
                this.loading=true;
                if(this.isYear){
                    getOneDeptList(params).then(response=>{
                      if(response.code==200){
                          this.setdeptdetailList=response.data.map(item=>{
                              item.target=item.target?item.target:null
                              return item
                          });
                          this.total=response.data.length;
                      }
                      this.loading=false;
                  })
                }else{
                    params.nowDate = this.queryParams.nowDate + '-01';
                    getOneDeptMonthList(params).then(response=>{
                        if(response.code==200){
                            this.setdeptdetailList=response.data.map(item=>{
                                item.target=item.target?item.target:null
                            return item
                        });
                            this.total=response.data.length;
                        }
                        this.loading=false;
                    })
                }

            }

        },
        /** 刷新按钮操作 */
        handleRefresh() {
          this.$confirm('确定刷新当前数据吗，未保存数据即将清空！', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
            }).then(() => {
            let saveLoading = this.saveLoading();
                this.getOneDeptListFun(this.selectionMemberRow)
                this.msgSuccess("刷新成功");
                saveLoading.close();
            }).catch(() => {});
        },
        //保存按钮
        handleSave(){
            this.setdeptdetailList.forEach(item=>{
                if(this.isYear){
                    item.oneDate=item.oneDate?item.oneDate:this.queryParams.nowDate + '-01' + '-01';
                    item.twoDate=item.twoDate?item.twoDate:this.queryParams.nowDate + '-02' + '-01';
                    item.threeDate=item.threeDate?item.threeDate:this.queryParams.nowDate + '-03' + '-01';
                    item.fourDate=item.fourDate?item.fourDate:this.queryParams.nowDate + '-04' + '-01';
                    item.fiveDate=item.fiveDate?item.fiveDate:this.queryParams.nowDate + '-05' + '-01';
                    item.sixDate=item.sixDate?item.sixDate:this.queryParams.nowDate + '-06' + '-01';
                    item.sevenDate=item.sevenDate?item.sevenDate:this.queryParams.nowDate + '-07' + '-01';
                    item.eightDate=item.eightDate?item.eightDate:this.queryParams.nowDate + '-08' + '-01';
                    item.nineDate=item.nineDate?item.nineDate:this.queryParams.nowDate + '-09' + '-01';
                    item.tenDate=item.tenDate?item.tenDate:this.queryParams.nowDate + '-10' + '-01';
                    item.elevenDate=item.elevenDate?item.elevenDate:this.queryParams.nowDate + '-11' + '-01';
                    item.twelveDate=item.twelveDate?item.twelveDate:this.queryParams.nowDate + '-12' + '-01';
                }
            })
            let data={
                pmsList:this.setdeptdetailList,
                compCode:this.queryParams.compCode
            };
            let saveLoading=this.saveLoading();
            if(this.isYear){
                saveOneDept(data).then(response=>{
                    if(response.code==200){
                        this.msgSuccess("保存成功");
                        this.getOneDeptListFun(this.selectionMemberRow);
                        saveLoading.close()
                    }
                })
            }else{
                saveOneMonthDept(data).then(response=>{
                    if(response.code==200){
                        this.msgSuccess("保存成功");
                        this.getOneDeptListFun(this.selectionMemberRow);
                        saveLoading.close()
                    }
                })
            }

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
            true
            );
            selection = Array(selection[selection.length - 1]);
        } else if (selection.length === 1) {
            selection = Array(selection[selection.length - 1]);
        } else {
            selection = [];
        }
        this.selectionMemberRow=selection
        if(this.selectionMemberRow.length!=0){
            this.getOneDeptListFun(this.selectionMemberRow)
        }else{
            this.setdeptdetailList=[]
        }
    },
    rowStyle1({
        row,
        rowIndex
        }) {
          Object.defineProperty(row, 'rowIndex', {
              value: rowIndex,
              writable: true,
              enumerable: false
          })
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
    },
};
</script>

<style lang="scss" scoped>

</style>
