<template>
    <div>
      <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
        <div ref="btns">
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                type="primary"
                icon="el-icon-search"
                size="mini"
                :disabled="single"
                @click="handleDetail"
                v-hasPermi="['quality:quality:edit']"
              >查看</el-button>
            </el-col>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getDetailInfo"></right-toolbar>
          </el-row>
        </div>
        <el-table border stripe v-loading="loading" height="93%" :data="qualityList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="质量名称(模板名称)" align="center" prop="tempName">
            <template slot-scope="scope">
              <el-link type="primary" :underline="false" v-html="scope.row.tempName" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
            </template>
          </el-table-column>
          <el-table-column label="项目分值" align="center" prop="indexScore" />
          <el-table-column label="本月得分" align="center" prop="actualScore" />
        </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancel">关 闭</el-button>
        </div>
      </el-dialog>

      <!-- 得分明细 -->
      <el-dialog :title="detailTitle" :visible.sync="openDetailDialog" width="50%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDetailDialogClose">
        <div ref="btns">
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                type="success"
                icon="el-icon-search"
                size="mini"
                :disabled="singleItem"
                @click="handleDetailItem"
                v-hasPermi="['quality:quality:edit']"
              >详情</el-button>
            </el-col>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getQualityDetail"></right-toolbar>
          </el-row>
        </div>
        <el-table border stripe v-loading="loading1" height="86%" :data="qualityList1" @selection-change="handleSelectionChange1" ref="multipleTable1" @row-click="rowClick1" :row-style="rowStyle">
          <el-table-column type="selection" width="55" align="center" />
          <template v-for="item in cols">
      		<template v-if="item.isList=='1'">
      			<template v-if="item.fieldType=='select'">
      				<el-table-column show-overflow-tooltip :align="item.align" :label="item.label" :prop="item.prop" :formatter="stateFormat" />
      			</template>
      			<template v-else>
							<el-table-column v-if="item.prop=='cs_date'" show-overflow-tooltip :align="item.align" :label="item.label" :prop="item.prop" width="150px">
								<template slot-scope="scope">
									<el-link type="primary" :underline="false" v-html="scope.row.cs_date" @click.stop="handleClickTableNameDetail(scope.row)" class="displayBlock text-ellipsis "></el-link>
								</template>
							</el-table-column>
							<el-table-column v-else-if="item.prop=='cs_score'" show-overflow-tooltip :align="item.align" :label="item.label" :prop="item.prop" width="150px">
								<template slot-scope="scope">
									<span>{{(scope.row.cs_score * 1).toFixed(item.decimlDigit * 1)}}</span>
								</template>
							</el-table-column>
      				<el-table-column v-else show-overflow-tooltip :align="item.align" :label="item.label" :prop="item.prop">
      				</el-table-column>
      			</template>
      		</template>
      	</template>
        </el-table>
        <pagination
          v-show="total1>0"
          :total="total1"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getQualityDetail"
        />
        <div slot="footer" class="dialog-footer">
          <el-button @click="detailCancel">关 闭</el-button>
        </div>
      </el-dialog>
      
      <detailInfo v-if="openInfo" :open="openInfo" :id="formDesignId" :ids="idsInfo" @cancel="cancelDetailInfo"></detailInfo>
    </div>
</template>

<script>
import { validEmail } from'@/utils/validate'
import { getQuality,getQualityDetail } from "@/api/quality/quality";
import { deployList} from '@/api/utils';
import { listDetail } from "@/api/quality/detail";
import {getDeptInfoForPms} from "@/api/formDesign/formDesign";
import detailInfo from './detailInfo';

export default {
  name: "addEdit",
  props: {
    open: {
      type: Boolean,
      default: false
    },
    datas: {
      type: Object,
      default: null,
    }
  },
  watch: {
    datas: {
      handler(val) {
        this.reset();
        if (val) {
          this.paramsData = val;
          this.title = "查看详情 / 质量得分";
          this.getDetailInfo();
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
    detailInfo
  },
  created() {
    this.getDetailInfo();
  },
  data() {
    return {
      title: "查看详情 / 质量得分",
      openDialog: this.open,
      paramsData:null,
      detailTitle:"查看详情 / 质量得分 / 得分明细",
      openDetailDialog:false,
      saveLoading: false,
      openInfo:false,
      single:true,
      singleItem:true,
      // 显示搜索条件
      showSearch: true,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      queryParams:{
        pageNum:1,
        pageSize:30,
      },
      qualityList:[],
      total:0,
      qualityList1:[],
      total1:0,
      loading:false,
      loading1:false,
      selectionRow:[],
      selectionRowItem:[],
      cols:[],
      lists:[],
      formDesignId:'',
      ids:'',
      idsInfo:'',
    };
  },

  methods: {
    handleClickTableName(data){
      // 选中当前行
        this.rowClick(data, false);
        this.handleDetail();
    },
    handleClickTableNameDetail(data){
      this.rowClick1(data, false);
      this.handleDetailItem(data)
    },
  	getDetailInfo() {
      if(this.datas){
        let params={
        deptCode:this.datas.deptCode,
        compCode:this.$store.state.user.userInfo.deptId,
        qTime:this.datas.occTime + '-01'
      }
      // console.log(params)
      this.loading=true;
      getQuality(params).then(response => {
        if(response.code==200){
          this.qualityList=response.rows;
          // console.log(this.qualityList)
          this.total=response.total;
          this.loading=false;
        }
      });
      }
      
    },
    stateFormat(row, column, cellValue) {
				let arr = this.lists;
    		for(let i=0;i<arr.length;i++){
    			if(arr[i].fieldName==column.property){
						if(arr[i].fieldName=="cs_target"){
							return this.selectDictIndexLabel(arr[i].options,cellValue)
						}else{
							return this.selectDictLabel(arr[i].options,cellValue);
						}
					}
				}
			},
    getQualityDetail(data){
      this.formDesignId=data.tempCode
      let obj={
        formDesignId:data.tempCode,
      }
      deployList(obj).then(res=>{
        this.lists = res.data;
        for(let i=0;i<this.lists.length;i++){
	        		this.$set( this.lists[i], this.lists[i].fieldName, null )
							this.$set( this.lists[i], "val", null )
							if(this.lists[i].fieldType=="select"){
								if(this.lists[i].fieldName=="cs_target"){
										let params={
												indexCode:this.lists[i].indexCode,
												compCode:this.$store.state.user.userInfo.deptId  // 必传
										}
										listDetail(params).then(response=>{
												if(response.code===200){
													this.$set( this.lists[i], "options",response.rows );
													this.targetValue=JSON.parse(JSON.stringify(response.rows))
												}
										})
								}else if(this.lists[i].fieldName=="cs_dept"){
									let params= {
										compCode:this.$store.state.user.userInfo.deptId
									}
									getDeptInfoForPms(params).then(res=>{
											if(res.code===200){
													this.$set( this.lists[i], "options",res.data )
											}
									})
								}else{
									this.getDicts(this.lists[i].dictType).then(response => {
										this.$set( this.lists[i], "options", response.data )
									});
								}
							}
	        		if(this.lists[i].fieldType=="radio"){
	        			this.lists[i].options = JSON.parse(this.lists[i].options)
	        		}
	        		if(this.lists[i].fieldType=="checkbox"){
	        			this.lists[i].options = JSON.parse(this.lists[i].options)
	        			this.$set( this.lists[i], "val", [])
	        		}
						}
         let list = res.data;
						// console.log(list,'list')
	        	let arr = [];
	        	for(let i=0;i<list.length;i++){
							let obj = {};
							if(list[i].fieldName!="pms_state"){
								if(list[i].fieldName!="dept_track"){
									obj.label = list[i].fieldDesc;
									obj.prop = list[i].fieldName;
									obj.isList = list[i].isList;
									obj.fieldType = list[i].fieldType;
									if(list[i].fieldName=='cs_score'){
										obj.decimlDigit = list[i].decimlDigit
									}
									if(obj.prop=='cs_date'){
										obj.align='center';
									}else if(obj.prop=='cs_score'){
										obj.align='right';
									}else{
										obj.align='left';
									}
									arr.push(obj)
								}
								
							}
						}
            this.cols = arr;
            this.getTable(data)
      })
    },
    //表格数据
    getTable(data){
      let params={
        formDesignId:data.tempCode, 
        deptCode:data.deptCode, 
        qDate:this.datas.occTime + '-01',
      };
      getQualityDetail(params).then(response=>{
        this.qualityList1=response.rows;
        this.total1=response.total;
      })
    },
   
    //查看详情
    handleDetail(){ 
      this.getQualityDetail(this.selectionRow[0]);        
      this.openDetailDialog=true;
    },
    //查看详细信息
    handleDetailItem(data){
      this.idsInfo=this.selectionRowItem[0].id;
      this.openInfo=true;
    },
    cancelDetailInfo(){
      this.openInfo=false;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.selectionRow=selection;
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
    // 多选框选中数据
    handleSelectionChange1(selection) {
      // this.ids = selection.map(item => item.id)
      this.singleItem = selection.length!==1
      this.selectionRowItem=selection
    },
    rowClick1(row, column, event) {
      let refsElTable = this.$refs.multipleTable1;
      let findRow = this.selectionRowItem.find(c => c.rowIndex == row.rowIndex);
      // if (findRow && this.selectionRowItem.length == 1) {
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
    //详情弹框关闭
    handleDetailDialogClose(){
      this.openDetailDialog=false;
    },
    detailCancel(){
      this.openDetailDialog=false;
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
        deptCode: null,
        indexCode: null,
        tempCode: null,
        indexScore: null,
        occureTime: null,
        actualScore: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
  }
};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    height: 80%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>