<template>
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
        <el-row>
          <el-col :span="6">
            <el-form-item label="指标代码" prop="indexCode">
              <el-input
                class="search_w220"
                v-model="queryParams.indexCode"
                placeholder="请输入指标代码"
                clearable
              />
            </el-form-item>
            <el-form-item label="指标名称" prop="indexName">
              <el-input
                class="search_w220"
                v-model="queryParams.indexName"
                placeholder="请输入指标名称"
                clearable
              />
            </el-form-item>
            <el-form-item label="是否启用" prop="isStop">
              <el-select class="search_w220" v-model="queryParams.isStop" placeholder="请选择是否启用" clearable>
                <el-option
                  v-for="dict in isStopOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
<!--            <el-form-item label="BSC一级分类" prop="bscOneCode">-->
<!--              <el-select style="width:204px" clearable filterable remote v-model="queryParams.bscOneCode" placeholder="请选择BSC一级分类" @change="getBscList" >-->
<!--                <el-option-->
<!--                  v-for="dict in bscIList"-->
<!--                  :key="dict.bscCode"-->
<!--                  :label="dict.bscName"-->
<!--                  :value="dict.bscCode"-->
<!--                />-->
<!--              </el-select>-->
<!--            </el-form-item>-->
            <el-form-item label="是否表达式" prop="isExpress">
              <el-select class="search_w220" v-model="queryParams.isExpress" placeholder="请选择是否表达式">
                <el-option
                  v-for="dict in isExpressOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="是否绝对值" prop="isAbsolute">
              <el-select class="search_w220" v-model="queryParams.isAbsolute" placeholder="请选择是否绝对值">
                <el-option
                  v-for="dict in isAbsoluteOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
<!--            <el-form-item label="BSC二级分类" prop="bscSecondCode">-->
<!--              <el-select class="search_w220" clearable filterable v-model="queryParams.bscSecondCode" placeholder="请选择BSC二级分类">-->
<!--                <el-option-->
<!--                  v-for="dict in bscIIList"-->
<!--                  :key="dict.bscCode"-->
<!--                  :label="dict.bscName"-->
<!--                  :value="dict.bscCode"-->
<!--                />-->
<!--              </el-select>-->
<!--            </el-form-item>-->
            <el-form-item label="综合评价指标" prop="isComp">
              <el-select class="search_w220" v-model="queryParams.isComp" placeholder="请选择是否综合评价指标">
                <el-option
                  v-for="dict in isCompOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="是否高优指标" prop="isCouns">
              <el-select class="search_w220" v-model="queryParams.isCouns" placeholder="请选择是否高优指标">
                <el-option
                  v-for="dict in isCounsOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>

          </el-col>
          <el-col :span="6">
            <el-form-item label="部门" prop="deptType">
              <el-select class="search_w220" v-model="queryParams.deptType" placeholder="请选择部门分类" clearable >
                <el-option
                  v-for="dict in deptOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="组织" prop="orgType">
              <el-select class="search_w220" v-model="queryParams.orgType" placeholder="请选择组织类别" clearable >
                <el-option
                  v-for="dict in orgOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div ref="btns">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            :disabled="allowAdd"
            v-hasPermi="['evaluate:index:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['evaluate:index:edit']"
          >编辑</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="single"
            @click="handleDelete"
            v-hasPermi="['evaluate:index:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            :disabled="isExp"
            @click="setIndicatorExp1"
            v-hasPermi="['evaluate:index:add']"
          >指标表达式</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            :disabled="isAlgor"
            @click="handleAlg"
            v-hasPermi="['evaluate:index:add']"
          >指标算法</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>
    <el-table border stripe v-loading="loading" :data="indexList" :height="tableHeight" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="指标代码" align="center" prop="indexCode" min-width="150px">
        <template slot-scope="scope">
          <el-link type="primary" :underline="false" v-html="scope.row.indexCode" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column sortable label="BSC一级分类" align="center" prop="bscOneCode" min-width="150px" :formatter="isBounsSBCY"/>
      <el-table-column sortable label="BSC二级分类" align="left" prop="bscSecondCode" min-width="150px" :formatter="isBounsSBCE"/>
      <el-table-column sortable label="指标名称" show-overflow-tooltip align="left" prop="indexName" min-width="150px"/>
      <el-table-column sortable label="是否表达式" align="center" prop="isExpress" min-width="150px" :formatter="isExpressFormat"/>
      <el-table-column sortable label="是否启用" align="center" prop="isStop" min-width="150px" :formatter="isStopFormat"/>
      <el-table-column sortable label="是否高优指标" align="center" prop="isCouns" min-width="140px" :formatter="isCounsFormat"/>
      <el-table-column sortable label="综合评价指标" align="center" prop="isComp" min-width="140px" :formatter="isCompFormat"/>
      <el-table-column sortable label="是否绝对值" align="center" prop="isAbsolute" min-width="140px" :formatter="isAbsoluteFormat"/>
      <el-table-column sortable label="部门" align="center" prop="deptType" min-width="150px" :formatter="deptFormat"/>
      <el-table-column sortable label="组织" align="center" prop="orgType" min-width="150px" :formatter="orgFormat"/>
      <el-table-column sortable label="指标阈值比" align="center" prop="indexRatio" min-width="150px" />
      <el-table-column sortable label="指标说明" show-overflow-tooltip align="center" prop="indexContent" min-width="150px" />
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <addEdit v-if="open" :open="open" :datas="addDatas" :id="addEditId" @cancel="cancel" @success="successSubmit" @openIndExp="setIndicatorExp(arguments)" @openAlgorithm="setAlgorithm" ></addEdit>
    <algorithm v-if="openAlg" :id="addEditId" :open="openAlg" @cancel="openAlgCancel" @success="subOpenAlgFun" :addIndexName="addIndexName" @showData="showDataFun"></algorithm>
    <detailData v-if="showDetailBtn" :open="showDetailBtn" @cancel="cancelDetail" @success="subDetailData" :datas="detailData"></detailData>

    <el-dialog title="指标管理-指标表达式" class="formulaDialog" ref="formulaDialog" :visible.sync="openIndicatorExp" width="80%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @open="handleFormulaDialogOpen" @close="indicatorExpCancel">
      <el-form ref="formIn" :model="indicatorData" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="指标代码" prop="indexCode">
              <el-input class="w80" v-model="indicatorData.indexCode" placeholder="系统自动生成"  maxlength="20" show-word-limit disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="指标名称" prop="indexName">
              <el-input class="w80" v-model="indicatorData.indexName" placeholder="请输入指标名称"  maxlength="100" show-word-limit disabled />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-row :gutter="15" class="h100">
        <el-col :span="6" class="h100">
          <el-card class="box-card h100">
            <div class="h100">
              <el-form ref="searchForm" :model="searchForm">
                <el-form-item label="">
                  <el-input v-model="searchForm.bscName" placeholder="请输入搜索信息" maxlength="50" class="w100" @input="getIndicatorList">
                    <el-button slot="append" icon="el-icon-search"></el-button>
                  </el-input>
                </el-form-item>
              </el-form>

              <el-table
                class="totalTable"
                :loading="memberTableLoading"
                height="calc(100% - 44px)"
                border
                stripe
                row-key="id"
                :data="subjectList"
                ref="menmberTable"
                :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
                default-expand-all
                @row-click="rowMemberClick"
                @selection-change="handleSelectionMemberChange"
                :row-style="rowStyle1">
                <el-table-column type="selection" :selectable="selectableTota" width="55" align="center"/>
                <el-table-column sortable label="指标名称" align="left" prop="bscName" />
              </el-table>
            </div>
          </el-card>
        </el-col>
        <el-col :span="12" class="h100">
          <el-col :span="24" class="mb10" style="height:40%">
            <el-card class="box-card h100" >
              <div slot="header" class="clearfix">
                <span>公式</span>
              </div>
              <div class="h100">
                {{memberFormulaCode}}
              </div>
              <div>

              </div>
            </el-card>
          </el-col>
          <el-col :span="24" style="height:24%">
            <el-card class="box-card h100">
              <div slot="header" class="clearfix">
                <span>翻译</span>
              </div>
              <div>
                {{memberFormulaName}}
              </div>
            </el-card>
            <el-col :span="24" class="mt10">
              <el-row>
                <el-button class="btns" size="mini" @click="handleStr(7)">7</el-button>
                <el-button class="btns" size="mini" @click="handleStr(8)">8</el-button>
                <el-button class="btns" size="mini" @click="handleStr(9)">9</el-button>
                <el-button class="btns" size="mini" @click="handleExcept">/</el-button>
                <el-button class="btns" size="mini" @click="handleLeftBrackets">(</el-button>
              </el-row>
              <el-row>
                <el-button class="btns" size="mini" @click="handleStr(4)">4</el-button>
                <el-button class="btns" size="mini" @click="handleStr(5)">5</el-button>
                <el-button class="btns" size="mini" @click="handleStr(6)">6</el-button>
                <el-button class="btns" size="mini" @click="handleMultiplication">*</el-button>
                <el-button class="btns" size="mini" @click="handleRightBrackets">)</el-button>
              </el-row>
              <el-row>
                <el-button class="btns" size="mini" @click="handleStr(1)">1</el-button>
                <el-button class="btns" size="mini" @click="handleStr(2)">2</el-button>
                <el-button class="btns" size="mini" @click="handleStr(3)">3</el-button>
                <el-button class="btns" size="mini" @click="handleSubtraction">-</el-button>
                <!--<el-button class="btns" size="mini" @click="qingchu()">删除</el-button>-->
                <el-button class="btns" size="mini" @click="handleEmpty">清空</el-button></el-button>
              </el-row>
              <el-row>
                <el-button class="btns" size="mini" @click="handleStr(0)">0</el-button>
                <el-button class="btns" size="mini" @click="handleSpot">.</el-button>
                <el-button class="btns" size="mini" @click="handleAddComp">+</el-button>

              </el-row>
            </el-col>
          </el-col>
        </el-col>
        <el-col :span="6" class="h100">
          <el-card class="box-card h100">
            <div slot="header" class="clearfix">
              <span>公式函数列表</span>
            </div>
            <div class="h100">
              <el-form ref="funcForm" :model="funcForm" >
                <el-form-item label="" >
                  <el-input v-model="funcForm.name" placeholder="请输入搜索信息" maxlength="50" class="w100">
                    <el-button slot="append" icon="el-icon-search"></el-button>
                  </el-input>
                </el-form-item>
              </el-form>
              <el-table
                class="totalTable"
                :loading="memberTableLoading"
                height="calc(100% - 44px)"
                border
                stripe
                :data="funcTableList"
                ref="fucTable"
                @row-click="rowFuncClick"
                @selection-change="handleSelectionfuncChange"
                :row-style="rowStyle1">
                <el-table-column type="selection" :selectable="selectableTota" width="55" align="center"/>
                <el-table-column sortable label="公式" align="left" prop="subjectName" />
              </el-table>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormulaForm">保 存</el-button>
        <el-button @click="indicatorExpCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listBonus, getIndex, delIndex, addBonus, updateIndex, exportIndex, getBsc, getIndicatorList } from "@/api/evaluate/index";
  import addEdit from "./addBouns";
  import algorithm from './algorithm';
  import Editor from '@/components/Editor';
  import detailData from './detailData';

  export default {
    name: "Index",
    components: {
      Editor,
      addEdit,
      algorithm,
      detailData
    },
    data() {
      return {
        tableHeight: "auto",
        // 遮罩层
        loading: true,
        // 导出遮罩层
        exportLoading: false,
        openIndicatorExp:false,
        openAlg:false,
        addDatas:{},
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        isExp:true,
        isAlgor:true,
        isAdd:false,
        isEdit:false,
        allowAdd:false,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 指标管理表格数据
        indexList: [],
        selectionRow: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        addIndexName:"",
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 30,
          compCode: null,
          accountCode: null,
          bscOneCode: null,
          bscSecondCode: null,
          deptType: null,
          orgType: null,
          indexCode: null,
          indexName: null,
          isExpress: null,
          formulaCode: null,
          formulaName: null,
          isSection: null,
          indexSql: null,
          sectionStart: null,
          sectionEnd: null,
          isCouns: null,
          isStop: null,
          isComp: null,
          isAbsolute: null,
          indexType: null,
          isRegular: null,
          indexRatio: null,
          indexContent: null,
          indexSort: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {

        },
        //指标部门分类
        deptOptions:[],
        //指标组织分类
        orgOptions:[],
        //高优指标
        isCounsOptions:[],
        //是否字典
        isStopOptions:[],
        isExpressOptions:[],
        //bsc一级分类
        isBounsSBCYs:[],
        //bsc二级分类
        isBounsSBCEs:[],
        isCompOptions:[],
        isAbsoluteOptions:[],
        bscIList:[],//bsc一级列表
        bscIIList:[],//bsc二级列表
        editOrgType: '',
        indicatorData:{
          compCode:this.$store.state.user.userInfo.deptId,
          formulaCode:null,
          formulaName:null,
          indexCode:null,
          indexName:null,
        },
        indicatorLoading:false,
        // 公式型
        formulaDialog:this.open,
        memberTableLoading:true,
        searchForm:{
          bscName:null,
          compCode:this.$store.state.user.userInfo.deptId,
        },
        // 成员列表
        memberTableList:[],
        memberFormulaName:"",
        memberFormulaCode:"",
        // 函数
        funcForm:{
          name:null
        },
        selectionTotalRow:[],
        selectionMemberRow:[],
        funcTableList:[],
        settingForm:{
          subjectCode:null,
          subjectName:null,
          subjectType:null,
          subjectFormat:null,
          digit:undefined,
          isNull:null,
          compCode:this.$store.state.user.userInfo.deptId,
          formulaCode:null,
          formulaName:null
        },
        subjectList:[],
        initSubjectList:[],
        showDetailBtn:false,
        detailData:{},
      };
    },
    created() {
      this.getBscList();
      this.getList();
      //是否高优指标字典
      this.getDicts("pms_zb_sfgy").then(response=>{
        this.isCounsOptions = response.data;
      })
      //指标组织字典
      this.getDicts("pms_zb_zz").then(response=>{
        this.orgOptions = response.data;
      })
      //是否停用字典
      this.getDicts("pms_zb_ty").then(response=>{
        this.isStopOptions = response.data;
      })
      //指标部门分类字典
      this.getDicts("pms_bm_fl").then(response=>{
        this.deptOptions=response.data;
      })
      //bsc一级
      this.getDicts("PMS_JJBSC_YJFL").then(response=>{
        this.isBounsSBCYs=response.data;
      })
      //bsc二级
      this.getDicts("PMS_JJBSC_EJFL").then(response=>{
        this.isBounsSBCEs=response.data;
      })
      //是否字典类型
      this.getDicts("hr_is_change").then(response => {
        this.isExpressOptions = response.data;
        this.isCompOptions = response.data;
        this.isAbsoluteOptions = response.data;
      });
      this.$nextTick(() => {
        this.resizeHandler();
        window.addEventListener("resize", this.resizeHandler);
      });
    },
    computed: {
      listData() {
        return this.$store.state.app.sidebar.opened;
      }
    },
    watch:{
      listData(val) {
        var that = this;
        setTimeout(function() {
          that.resizeHandler();
        }, 300);
      },
      // 公式设置选中的科目
      selectionMemberRow:{
        handler(val){
          if(val.length){
            let selectedData = val[0]
            let str1 = this.memberFormulaCode
            if ((str1 == "" || str1 == null )&&str1!="(") {
              this.memberFormulaCode = `#{${selectedData.bscCode}}`
              this.memberFormulaName = `[${selectedData.bscName}]`
            } else {
              var str = str1.substr(str1.length - 1, str1.length);
              if (
                str == "+" ||
                str == "-" ||
                str == "*" ||
                str == "/" ||
                str == "(" ||
                str == ")"
              ) {
                this.memberFormulaCode += `#{${selectedData.bscCode}}`
                this.memberFormulaName += `[${selectedData.bscName}]`
              } else {
                this.msgError("当前公式最后不是运算符不能直接拼接绩效分类！");
              }
            }
          }
        },
        deep:true,
        immediate:true
      }
    },
    methods: {
      //是否高优指标字典翻译
      isCounsFormat(row, column) {
        return this.selectDictLabel(this.isCounsOptions, row.isCouns);
      },
      //指标部门分类字典翻译
      deptFormat(row,column){
        return this.selectDictLabel(this.deptOptions, row.deptType);
      },
      //指标组织分类字典翻译
      orgFormat(row,column){
        return this.selectDictLabel(this.orgOptions, row.orgType);
      },
      // 是否启用字典翻译
      isStopFormat(row, column) {
        return this.selectDictLabel(this.isStopOptions, row.isStop);
      },
      // 字典翻译
      isExpressFormat(row, column) {
        return this.selectDictLabel(this.isExpressOptions, row.isExpress);
      },
      //BSC一级
      isBounsSBCY(row, column) {
        return this.selectDictLabel(this.isBounsSBCYs, row.bscOneCode);
      },
      //BSC二级
      isBounsSBCE(row, column) {
        return this.selectDictLabel(this.isBounsSBCEs, row.bscSecondCode);
      },
      // 字典翻译
      isCompFormat(row, column) {
        return this.selectDictLabel(this.isCompOptions, row.isComp);
      },
      // 字典翻译
      isAbsoluteFormat(row, column) {
        return this.selectDictLabel(this.isAbsoluteOptions, row.isAbsolute);
      },
      /** 查询指标管理列表 */
      getList() {
        this.loading = true;
        listBonus(this.queryParams).then(response => {
          this.indexList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      //获取bsc下拉列表数据
      getBscList(val){
        let params={
          parentCode:val?val:0
        }
        if(!val){
          this.queryParams.bscSecondCode=null;
          this.bscIList=[];
          this.bscIIList=[];
        }
        getBsc(params).then(res=>{
          if(res.code==200){
            if(val){
              this.bscIIList=res.data;
            }else{
              this.bscIList=res.data;
            }
          }
        })
      },
      //超链接
      handleClickTableName(data) {
        // 选中当前行
        this.rowClick(data, false);
        this.handleUpdate(data);
      },
      //表格高度设置
      resizeHandler() {
        let a = this.$refs.search.offsetHeight;
        let b = this.$refs.btns.offsetHeight;
        let h = a + b + 18 + 45 + 84 + 40 + 20;
        h = h + "px";
        this.tableHeight = `calc(100vh - ${h})`;
      },
      // 公式型
      handleFormulaDialogOpen(){
        // 指标 获取指标数据
        this.getIndicatorList()
      },
      /** 查询指标管理列表 */
      getIndicatorList() {
        this.indicatorLoading = true;
        let params = {
          compCode: this.$store.state.user.userInfo.deptId,
          orgType:  this.editOrgType,
          isBonus: '0'
        }
        getIndicatorList(params).then(response => {
          this.initSubjectList = response.data
          response.data.forEach(item=>{
            item.id=item.bscCode
          })
          this.subjectList = this.handleTree(
            response.data,
            "bscCode",
            "parentCode"
          );
          this.indicatorLoading = false;
        });
      },
      // 只能选择 末级节点
      selectableTota(row){
        if(row.isLast == 0){
          // 末级
          if(this.indicatorData.indexCode === row.bscCode){
            //自己不可选
            return false;
          }
          return true
        }else{
          return false
        }
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
      /** 新增按钮操作 */
      handleAdd() {
        this.open = true; // 弹框点开
        this.addEditId = ""; // addEditId 是新增  去子组件方便区分新增编辑
        this.isAdd=true;
        this.isEdit=false;
      },
      //编辑按钮操作
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids[0];
        this.open = true;
        this.isAdd=false;
        this.isEdit=true;
        this.addEditId = id; // id赋值
        // console.log("addEditId---" + addEdit);
      },
      //点击指标表达式按钮操作
      setIndicatorExp(data){
        const id =this.ids[0];
        this.editOrgType = data[1];
        if(this.isAdd){
          this.addEditId="";
        }else{
          this.addEditId = id; // id赋值
        }
        if(this.addEditId !=""){
          this.memberFormulaCode = this.indicatorData.formulaCode;
          this.memberFormulaName = this.indicatorData.formulaName;
        }else{
          this.memberFormulaCode = "";
          this.memberFormulaName = "";
          this.indicatorData.indexName = data[0]

        }
        this.openIndicatorExp=true;
      },
      //列表页点击指标表达式
      setIndicatorExp1(){
        const id = this.ids[0];
        this.addEditId=id;
        this.memberFormulaCode = this.indicatorData.formulaCode;
        this.memberFormulaName = this.indicatorData.formulaName;
        this.openIndicatorExp=true;
      },
      //新增界面点击指标算法按钮
      setAlgorithm(data){
        this.addIndexName=data;
        this.openAlg=true;
      },
      //列表页点击指标算法按钮操作
      handleAlg(){
        const id = this.ids[0];
        this.addEditId=id
        this.openAlg=true;
      },
      //关闭指标表达式
      indicatorExpCancel(){
        this.openIndicatorExp=false;
        this.isExp=true;
        this.isAlgor=true;
        this.resetIndi()
        if(!this.isAdd && !this.isEdit){
          this.getList()
        }
      },
      //新增界面保存成功操作
      successSubmit(flag) {
        this.open = false;
        this.isAdd=false;
        this.isExp=true;
        this.isAlgor=true;
        this.getList();
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.isAdd=false;
        this.isEdit=false;
        this.isExp=true;
        this.isAlgor=true;
        this.reset();
        this.getList()
      },
      //关闭指标算法
      openAlgCancel(){
        this.openAlg=false;
        this.isExp=true;
        this.isAlgor=true;
        this.getList()
      },
      subOpenAlgFun(datas){
        this.openAlg=false;
        this.isExp=true;
        this.isAlgor=true;
        if(this.isAdd){
          this.addDatas.isSection=datas.isSection;
          this.addDatas.indexSql=datas.indexSql;
          this.addDatas.sectionStart=datas.sectionStart;
          this.addDatas.sectionEnd=datas.sectionEnd;
        }else{
          this.getList()
        }
        //
      },
      showDataFun(data){
        this.showDetailBtn=true;
        this.detailData=data;
      },
      cancelDetail(){
        this.showDetailBtn=false;
      },
      subDetailData(flag){
        this.showDetailBtn=flag
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          compCode: null,
          accountCode: null,
          bscOneCode: null,
          bscSecondCode: null,
          deptType: null,
          orgType: null,
          indexCode: null,
          indexName: null,
          isExpress: null,
          formulaCode: null,
          formulaName: null,
          isSection: null,
          indexSql: null,
          sectionStart: null,
          sectionEnd: null,
          isCouns: null,
          isStop: null,
          isComp: null,
          isAbsolute: null,
          indexType: null,
          isRegular: null,
          indexRatio: null,
          indexContent: null,
          indexSort: null,
          delFlag: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          remark: null
        };

        this.resetForm("form");
      },
      resetIndi(){
        this.indicatorData={
          compCode:this.$store.state.user.userInfo.deptId,
          formulaCode:null,
          formulaName:null,
          indexCode:null,
          indexName:null,
        }
        this.resetForm("formIn");
      },

      // 保存公式型
      submitFormulaForm(){
        if(this.memberFormulaCode){
          if(this.addEditId !="" && this.addEditId){
            let datas={
              id:this.addEditId,
              formulaCode:this.memberFormulaCode,
              formulaName:this.memberFormulaName,
              compCode:this.$store.state.user.userInfo.deptId,
              indexName:this.indicatorData.indexName
            }
            let saveLoading1=this.saveLoading()
            updateIndex(datas).then(response => {
              this.msgSuccess("修改成功");
              this.indicatorExpCancel();
              saveLoading1.close()
            });
          }else{
            let saveLoading1=this.saveLoading()
            this.addDatas.formulaCode=this.memberFormulaCode;
            this.addDatas.formulaName=this.memberFormulaName;
            this.indicatorExpCancel();
            saveLoading1.close()
          }
        }else{
          this.msgError("公式型不能为空")
        }
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除指标管理编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delIndex(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
      },

      // 多选框选中数据
      handleSelectionChange(selection) {
        if (selection.length > 1) {
          this.$refs.multipleTable.clearSelection();
          this.$refs.multipleTable.toggleRowSelection(selection.pop());
        }
        this.ids = selection.map(item => item.id)
        this.single = selection.length!==1
        this.multiple = !selection.length
        this.selectionRow=selection
        this.indicatorData=selection[0]?selection[0]:{}
        if(this.ids.length!==0){
          if(this.indicatorData.isExpress==1){
            this.isExp=true;
            this.isAlgor=false;
          }else{
            this.isExp=false;
            this.isAlgor=true;
          }
        }
        if(selection.length==0){
          this.allowAdd=false
        }else{
          this.editOrgType = this.selectionRow[0].orgType
          this.allowAdd=true
        }
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

      rowMemberClick(row, column, event) {
        if(this.indicatorData.indexCode === row.bscCode){
          //自己不可选
          return;
        }
        if(row.isLast == 0){
          let refsElTable = this.$refs.menmberTable;
          let findRow = this.selectionMemberRow.find(c => c.rowIndex == row.rowIndex);
          if (findRow && this.selectionMemberRow.length == 1) {
            refsElTable.toggleRowSelection(row, false);
            return;
          }
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

      // 计算
      handleStr(val){
        let regExp = /^[0-9]*$/;
        let str1 = this.memberFormulaCode;
        let str = str1.substr(str1.length - 1, str1.length);
        if (
          (str != "" && regExp.test(str)) ||
          str == "+" ||
          str == "-" ||
          str == "*" ||
          str == "/" ||
          str == "."
        ) {
          this.memberFormulaCode += String(val);
          this.memberFormulaName += String(val);
        }else {
          this.msgError("当前公式最后不是数字或运算符不能输入数字！");
        }
      },
      // +
      handleAddComp(){
        var regExp = /^[0-9]*$/;
        let str1 = this.memberFormulaCode;
        var str = str1.substr(str1.length - 1, str1.length);
        if (str != "") {
          if (str == ")" || str == "}" || regExp.test(str)) {
            this.memberFormulaCode += "+";
            this.memberFormulaName += "+";

          } else {
            this.msgError("当前公式不能使用运算符！");
          }
        } else {
          this.msgError("当前公式不能使用运算符！");
        }
      },
      // 除
      handleExcept(val){
        var regExp = /^[0-9]*$/;
        let str1 = this.memberFormulaCode;
        var str = str1.substr(str1.length - 1, str1.length);
        if (str != "") {
          if (str == ")" || str == "}" || regExp.test(str)) {
            this.memberFormulaCode += "/";
            this.memberFormulaName += "/";
          } else {
            this.msgError("当前公式不能使用运算符！");
          }
        } else {
          this.msgError("当前公式不能使用运算符！");
        }
      },
      // 点
      handleSpot(){
        var regExp = /^[0-9]*$/;
        let str1 = this.memberFormulaCode;
        var str = str1.substr(str1.length - 1, str1.length);
        if (str != "") {
          if (str == ")" || str == "}" || regExp.test(str)) {
            if (str == "" || !regExp.test(str)) {
              this.msgError("当前公式最后不是数字不能使用小数点！");
            } else {
              this.memberFormulaCode += ".";
              this.memberFormulaName += ".";
            }
          } else {
            this.msgError("当前公式不能使用运算符！");
          }
        } else {
          this.msgError("当前公式不能使用运算符！");
        }
      },
      // 减法
      handleSubtraction(){
        var regExp = /^[0-9]*$/;
        let str1 = this.memberFormulaCode;;
        var str = str1.substr(str1.length - 1, str1.length);
        if (str != "") {
          if (str == ")" || str == "}" || regExp.test(str)) {
            this.memberFormulaCode += "-";
            this.memberFormulaName += "-";
          } else {
            this.msgError("当前公式不能使用运算符！");
          }
        } else {
          this.msgError("当前公式不能使用运算符！");
        }
      },
      // 乘法
      handleMultiplication(){
        var regExp = /^[0-9]*$/;
        let str1 = this.memberFormulaCode;
        var str = str1.substr(str1.length - 1, str1.length);
        if (str != "") {
          if (str == ")" || str == "}" || regExp.test(str)) {
            this.memberFormulaCode += "*";
            this.memberFormulaName += "*";
          } else {
            this.msgError("当前公式不能使用运算符！");
          }
        } else {
          this.msgError("当前公式不能使用运算符！");
        }
      },
      // 左括号
      handleLeftBrackets(){
        var regExp = /^[0-9]*∑$/;
        let str1 = this.memberFormulaCode;
        var str = str1.substr(str1.length - 1, str1.length);
        if (str != "") {
          if (str !== "" || regExp.test(str)) {
            this.memberFormulaCode += "(";
            this.memberFormulaName += "(";
          } else {
            this.msgError("当前公式不能使用运算符！");
          }
        } else {
          this.memberFormulaCode = "(";
          this.memberFormulaName = "(";
        }
      },
      // 右括号
      handleRightBrackets(){
        var regExp = /^[0-9]*∑$/;
        let str1 = this.memberFormulaCode;
        var str = str1.substr(str1.length - 1, str1.length);
        if (str != "") {
          if (str !== "" || regExp.test(str)) {
            this.memberFormulaCode += ")";
            this.memberFormulaName += ")";
          } else {
            this.msgError("当前公式不能使用运算符！");
          }
        } else {
          this.memberFormulaCode = ")";
          this.memberFormulaName = ")";
        }
      },
      // 清空
      handleEmpty(){
        this.memberFormulaCode = "";
        this.memberFormulaName = "";
      },
      rowFuncClick(){},
      handleSelectionfuncChange(){},

    }
  };
</script>
<style lang="scss" scoped>
  .app-container{
    .el-form{
      border-bottom: 0px!important;
    }
    ::v-deep .el-input-number.is-controls-right .el-input__inner{
      text-align: left!important;
    }
  }
  .formulaDialog{
    ::v-deep .el-dialog{
      .el-form-item__content{
        margin-left: 0px!important
      }
    }
  }
  ::v-deep{
    .el-table__body-wrapper.is-scrolling-left{
      height:auto !important;
      max-height:calc(100vh - 393px) !important;
    }
    .el-table__body-wrapper.is-scrolling-middle{
      max-height:calc(100vh - 393px) !important;
      height:auto !important;
    }
    .el-table__body-wrapper.is-scrolling-right{
      height:auto !important;
      max-height:calc(100vh - 393px) !important;
    }
  }
  ::v-deep .el-dialog {
    // min-height: 20%;
    height: 80%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
      //   .totalTable{
      //     // height: calc(100% -100px)
      //   }
      &::-webkit-scrollbar {
        width: 6px;
        height: 6px
      }
      &::-webkit-scrollbar-thumb  {
        background-color: #cccccc;
        border-radius: 8px;
      }
      .mt10{
        margin-left: 50%;
        transform: translateX(-25%)
      }
    }
  }
  .h100{
    height: 100% !important;
  }
  ::v-deep .box-card{
    .el-card__body{
      height: calc(100% - 40px);
      >div{
        height: 100%!important;
      }
    }
  }
  .boxx {
    margin-top: 10px;
  }
  .btns {
    min-width: 60px;
    margin-bottom: 10px;
  }
</style>
