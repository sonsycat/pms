<template>
	<div class="app-container">
		<el-table border ref="dragTable" :data="formDesignList" :cell-class-name="tableCellClassName">
          <el-table-column label="页面字段" prop="fieldDesc" align="center" />
          <el-table-column label="编辑页面" align="center">
            <template slot-scope="scope">
              <el-checkbox true-label="1" false-label="0" v-model="scope.row.isEdit"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="必填项" align="center">
            <template slot-scope="scope">
              <el-checkbox true-label="1" false-label="0" v-model="scope.row.isNotNull"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="字典类型" align="center">
          	<template slot-scope="scope">
              <el-select clearable filterable placeholder="请选择" v-model="scope.row.dictType"
                         v-on:click.native="handleClick(scope.row)">
                <el-option
                  v-for="dict in scope.row.dictOptions"
                  :key="dict.dictType"
                  :label="dict.dictName"
                  :value="dict.dictType">
                  <span style="float: left">{{ dict.dictName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ dict.dictType }}</span>
                </el-option>
              </el-select>
           	</template>
          </el-table-column>
          <el-table-column label="字段长度" align="center">
          	<template slot-scope="scope">
              <el-input type="number" min="1" v-model="scope.row.fieldLength"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="效验方式" align="center">
            <template slot-scope="scope">
              <el-select v-model="scope.row.validType" clearable>
                <el-option label="邮箱地址" value="email"/>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="列表显示" align="center">
            <template slot-scope="scope">
              <el-checkbox true-label="1" false-label="0" v-model="scope.row.isList"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="查询条件" align="center">
            <template slot-scope="scope">
              <el-checkbox true-label="1" false-label="0" v-model="scope.row.isQuery"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="查询匹配方式" align="center">
            <template slot-scope="scope">
              <el-select v-model="scope.row.queryType" clearable>
                <el-option label="等于" value="eq"/>
                <el-option label="不等于" value="ne"/>
                <el-option label="大于" value="gt"/>
                <el-option label="大于等于" value="ge"/>
                <el-option label="小于" value="lt"/>
                <el-option label="小于等于" value="le"/>
                <el-option label="LIKE" value="like"/>
              </el-select>
            </template>
          </el-table-column>
        </el-table>
        <el-form label-width="100px">
	      <el-form-item style="text-align:center;margin-left:-100px;margin-top:10px;">
	        <el-button type="primary" @click="submitForm()">提交</el-button>
	        <el-button @click="close()">返回</el-button>
	      </el-form-item>
	    </el-form>
	</div>
</template>

<script>
import { deployList ,saveDeployList} from '@/api/utils'	
import { optionselect as getDictOptionselect } from '@/api/system/dict/type'
  
export default {
	data(){
        return{
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
	      	title: "",
	      	// 是否显示弹出层
	      	open: false,
	      	// 查询参数
	      	queryParams: {
	        	pageNum: 1,
	        	pageSize: 30,
	      	},
	      	// 表单参数
	      	form: {},
	      	
        }
    },
    components:{},
    created() {
      	this.formDesignId = this.$route.query.formDesignId
      	/** 查询字典下拉列表 */
        getDictOptionselect().then(response => {
          this.dictOptions = response.data
        })
        this.getList();
    },
    methods:{
    	//字典下拉框点击事件
      	handleClick(row) {
      		// console.log(row);
        	this.$set(this.formDesignList[row.index], 'dictOptions', this.dictOptions)
      	},
    	/** 查询自定义表单列表 */
	    getList() {
	    	let data = {formDesignId:this.formDesignId}
	      	deployList(data).then(response => {
		      	console.log(response);
		        this.formDesignList = response.data;
		    })
	    },
	    /** 提交按钮 */
      	submitForm() {
        	let data = {
        		formDesignId:this.formDesignId,
        		fdcList:this.formDesignList
        	}
        	console.log(data);
        	saveDeployList(data).then(response => {
		      	console.log(response);
				this.msgSuccess("保存成功")
	            if (response.code === 200) {
	            	this.close();
	            }
		    })
      	},
      	/** 关闭按钮 */
      	close() {
        	this.$store.dispatch('tagsView/delView', this.$route)
        	this.$router.push({ path:'/tool/formDesign', query: { t: Date.now() } })
      	},
      	//利用单元格的 className 的回调方法，给行列索引赋值
      	tableCellClassName({ row, column, rowIndex, columnIndex }) {//注意这里是解构
       	 	row.index = rowIndex
        	column.index = columnIndex
      	},
      	
      	
      	
	    
    },
}

</script>

<style>
</style>