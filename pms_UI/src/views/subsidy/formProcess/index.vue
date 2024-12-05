<template>
	<div class="app-container">
		<div ref="searchs">
			<el-form :model="queryParams" ref="queryForm" :inline="true" >
				<template v-for="(item,i) in lists">
					<template v-if="item.fieldType=='input'">
							<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
								<el-input
									v-model="item.val"
									clearable
									class="search_w220"
									:maxlength="item.fieldLength"
									@keyup.enter.native="handleQuery"
								/>
							</el-form-item>
						</template>
						<template v-else-if="item.fieldType=='select'">
							<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
								<el-select v-model="item.val" class="search_w220">
										<el-option
											v-for="dict in item.options"
											:key="dict.dictValue"
											:label="dict.dictLabel"
											:value="dict.dictValue"
										></el-option>
									</el-select>
							</el-form-item>
						</template>
						<template v-else-if="item.fieldType=='date'">
							<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
								<el-date-picker
									v-model="item.val"
									class="search_w220"
									value-format="yyyy-MM-dd"
								></el-date-picker>
							</el-form-item>
						</template>
						<template v-else-if="item.fieldType=='time'">
							<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
								<el-date-picker
								v-model="item.val"
								class="search_w220"
								type="datetime"
								value-format="yyyy-MM-dd HH:mm:ss"
							></el-date-picker>
							</el-form-item>
						</template>
						<template v-else-if="item.fieldType=='textarea'">
							<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
								<el-input :maxlength="item.fieldLength" type="textarea" v-model="item.val"></el-input>
							</el-form-item>
						</template>
						<template v-else-if="item.fieldType=='number'">
							<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
								<el-input-number class="search_w220" v-model="item.val" controls-position="right" :min="0"></el-input-number>
							</el-form-item>
						</template>
						<template v-else-if="item.fieldType=='rate'">
							<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
								<el-rate v-model="item.val"></el-rate>
							</el-form-item>
						</template>
						<template v-else-if="item.fieldType=='color'">
							<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
								<el-color-picker v-model="item.val"></el-color-picker>
							</el-form-item>
						</template>
						<template v-else-if="item.fieldType=='switch'">
							<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
								<el-switch v-model="item.val" active-value="打开" inactive-value="关闭"></el-switch>
							</el-form-item>
						</template>
						<template v-else-if="item.fieldType=='radio'">
							<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
								<el-radio-group v-model="item.val">
									<template v-for="(ite,ii) in item.options">
								<el-radio :label="ite.label">{{ite.value}}</el-radio>
								</template>
							</el-radio-group>
							</el-form-item>
						</template>
						<template v-else-if="item.fieldType=='checkbox'">
							<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
							<el-checkbox-group v-model="item.val">
								<template v-for="(ite,ii) in item.options">
								<el-checkbox :label="ite.value"></el-checkbox>
								</template>
							</el-checkbox-group>
							</el-form-item>
						</template>
						<template v-else>
							<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
								<el-input
									v-model="item.val"
									clearable

									:maxlength="item.fieldLength"
									@keyup.enter.native="handleQuery"
								/>
							</el-form-item>
						</template>
					</template>
					<el-form-item>
						<el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
						<el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
					</el-form-item>
			</el-form>
		</div>
		<div ref="btns">
			<el-row :gutter="10" class="mb8">
				<!-- v-hasPermi="['formDesign:formDesign:add']" -->
				<el-col :span="1.5">
					<el-button
						type="primary"
						icon="el-icon-plus"
						size="mini"
						@click="handleAdd"

					>新增</el-button>
				</el-col>
				<el-col :span="1.5">
					<el-button
						type="success"
						icon="el-icon-edit"
						size="mini"
						:disabled="single"
						@click="handleUpdate"
					>修改</el-button>
				</el-col>
				<el-col :span="1.5">
					<el-button
						type="danger"
						icon="el-icon-delete"
						size="mini"
						:disabled="multiple"
						@click="handleDelete"
					>删除</el-button>
				</el-col>
			</el-row>
		</div>
    <el-table v-loading="loading" :data="formDesignList" :max-height="tableHeight" ref="multipleTable" @selection-change="handleSelectionChange"  @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName" highlight-current-row >
      	<el-table-column type="selection" width="55" align="center" />
      	<!-- <el-table-column <el-table-column label="操作" fixed="right" align="center" class-name="small-padding fixed-width">
	        <template slot-scope="scope">
	          <el-button
	            size="mini"
	            type="text"
	            icon="el-icon-edit"
	            @click="handleUpdate(scope.row)"
	            v-hasPermi="['formDesign:formDesign:edit']" title="修改"
	          ></el-button>
	          <el-button
	            size="mini"
	            type="text"
	            icon="el-icon-delete"
	            @click="handleDelete(scope.row)"
	            v-hasPermi="['formDesign:formDesign:remove']" title="删除"
	          ></el-button>
	        </template>
      	</el-table-column> -->
      	<template v-for="item in cols">
      		<template v-if="item.isList=='1'">
      			<template v-if="item.fieldType=='select'">
      				<el-table-column  align="center" :label="item.label" :prop="item.prop" :formatter="stateFormat">
      				</el-table-column>
      			</template>
      			<template v-else>
      				<el-table-column  align="center" :label="item.label" :prop="item.prop">
      				</el-table-column>
      			</template>
      		</template>
      	</template>
    </el-table>

    <!-- <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    /> -->

    <!-- 添加或修改自定义表单对话框 -->
    <!--<addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>-->
    <el-dialog :title="title" custom-class="el-button--primary" :visible.sync="open" width="700px" append-to-body>
    	<el-form :model="form" ref="form" label-width="100px">
      		<el-row>
	    		<template v-for="(item,i) in form.lists2">
		      		<template v-if="item.fieldType=='input'">
	      				<template v-if="item.isEdit=='1'">
	      					<template v-if="item.validType==null">
                    <el-col :span="item.span">
                      <el-form-item
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'"
                      v-if="item.isNotNull=='1'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-input class="w90" :maxlength="item.fieldLength" v-model="item.val" />
                    </el-form-item>
                    <el-form-item
                        :label="item.fieldDesc"
                        v-else
                      :prop="'lists2.' + i + '.val'"
                        >
                        <el-input class="w90" :maxlength="item.fieldLength" v-model="item.val" />
                      </el-form-item>
                    </el-col>
	      					</template>
	      					<template v-else-if="item.validType=='email'">
                    <el-col :span="item.span">
                      <el-form-item
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'"
                      :rules="[
                        { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                        { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
                      ]"
                  >
                      <el-input class="w90" v-model="item.val" />
                    </el-form-item>
                    </el-col>
				      		</template>
			      		</template>
		      		</template>

		      		<template v-else-if="item.fieldType=='select'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-select class="w90" v-model="item.val">
                        <el-option
                            v-for="dict in item.options"
                            :key="dict.dictValue"
                            :label="dict.dictLabel"
                            :value="dict.dictValue"
                          ></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                    :prop="'lists2.' + i + '.val'"
                      >
                      <el-select class="w90" v-model="item.val">
                        <el-option
                            v-for="dict in item.options"
                            :key="dict.dictValue"
                            :label="dict.dictLabel"
                            :value="dict.dictValue"
                          ></el-option>
                        </el-select>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='date'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-date-picker
                        v-model="item.val"
												class="w90"
                        value-format="yyyy-MM-dd"
                      ></el-date-picker>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-date-picker
                        v-model="item.val"
                      	class="w90"
                        value-format="yyyy-MM-dd"
                      ></el-date-picker>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='time'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-date-picker
                      v-model="item.val"
											class="w90"
                      type="datetime"
                      value-format="yyyy-MM-dd HH:mm:ss"
                    ></el-date-picker>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-date-picker
                      v-model="item.val"
											class="w90"
                      type="datetime"
                      value-format="yyyy-MM-dd HH:mm:ss"
                    ></el-date-picker>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='textarea'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-input class="w90" :maxlength="item.fieldLength" type="textarea" v-model="item.val"></el-input>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-input class="w90" :maxlength="item.fieldLength" type="textarea" v-model="item.val"></el-input>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='number'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-input-number class="w90" controls-position="right" :min="0" size="mini" v-model="item.val"></el-input-number>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-input-number class="w90" controls-position="right" :min="0" size="mini" v-model="item.val"></el-input-number>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='rate'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-rate v-model="item.val"></el-rate>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-rate v-model="item.val"></el-rate>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='color'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-color-picker style="width: 100%;" v-model="item.val"></el-color-picker>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-color-picker style="width: 100%;" v-model="item.val"></el-color-picker>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='switch'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-switch v-model="item.val" active-value="打开" inactive-value="关闭"></el-switch>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-switch v-model="item.val" active-value="打开" inactive-value="关闭"></el-switch>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='radio'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                      <el-radio-group v-model="item.val">
                        <template v-for="(ite,ii) in item.options">
                      <el-radio :label="ite.label">{{ite.value}}</el-radio>
                      </template>
                    </el-radio-group>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-radio-group v-model="item.val">
                        <template v-for="(ite,ii) in item.options">
                      <el-radio :label="ite.label">{{ite.value}}</el-radio>
                      </template>
                    </el-radio-group>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else-if="item.fieldType=='checkbox'">
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      v-if="item.isNotNull=='1'"
                      :prop="'lists2.' + i + '.val'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}">
                    <el-checkbox-group v-model="item.val">
                      <template v-for="(ite,ii) in item.options">
                      <el-checkbox :label="ite.value"></el-checkbox>
                      </template>
                    </el-checkbox-group>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-checkbox-group v-model="item.val">
                      <template v-for="(ite,ii) in item.options">
                      <el-checkbox :label="ite.value"></el-checkbox>
                      </template>
                    </el-checkbox-group>
                    </el-form-item>
                  </el-col>
			      		</template>
			      	</template>

			      	<template v-else>
			      		<template v-if="item.isEdit=='1'">
                  <el-col :span="item.span">
                    <el-form-item
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'"
                      v-if="item.isNotNull=='1'"
                      :rules="{required: true, message:`${item.fieldDesc}不能为空`, trigger: 'change'}"
                    >
                      <el-input :maxlength="item.fieldLength" v-model="item.val" />
                    </el-form-item>
                    <el-form-item
                        :label="item.fieldDesc"
                        v-else
                      :prop="'lists2.' + i + '.val'"
                        >
                        <el-input :maxlength="item.fieldLength" v-model="item.val" />
                      </el-form-item>
                  </el-col>
			      		</template>
			      	</template>
	      		</template>
    		</el-row>
		</el-form>
      	<div slot="footer" class="dialog-footer">
        	<el-button type="primary" @click="submitForm">确 定</el-button>
        	<el-button @click="cancel">取 消</el-button>
      	</div>
    </el-dialog>
  	</div>
</template>

<script>
import { deployList,saveDesign,getForm,testForm,getFormDetails,addSaveForm,delForm} from '@/api/subsidy/formProcess';
import { getFormDesign } from '../../../api/formDesign/formDesign'

export default {
	data(){
        return{
					tableHeight:'auto',
        	idd:null,
        	// 修改id
      		addEditId: "",
        	lists:[],
        	lists2:[],
        	cols:[],
          	jsonData:{
          		list:[],
          	},
          	// formDesignId:"",
          	formDesignId:this.$route.name,
          	formName:"",
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
	      	form: {lists2:[]},
	      	// 表单校验
					rules: {},
					selectionRow:[],
        }
    },
    components:{},
    created() {
      console.log("自定义表单");
      console.log(".$route.path="+this.$route.path);
      getFormDesign(this.$route.name).then((response) =>{
        this.formDesignId = response.data.id;
        this.formName = response.data.formName;
      })
      	// this.formDesignId = this.$route.query.formDesignId
      	// this.formName = this.$route.query.formName
        // console.log("formDesignId____"+this.formDesignId);
        // console.log("formName____"+this.formName);
				this.getList();
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
		watch: {
			listData(val) {
				var that = this;
				setTimeout(function() {
					that.resizeHandler();
				}, 300);
			}
		},
    methods:{
			resizeHandler() {
				let a = this.$refs.btns.offsetHeight;
				let b = this.$refs.searchs.offsetHeight;
				let h =a + b + 18 + 45 + 84 + 36;
				h = h ;
				// `calc(100vh - ${h})`;
				this.tableHeight = document.documentElement.clientHeight - h + "px";
				// console.log(this.tableHeight)

			},
    	stateFormat(row, column, cellValue) {
    		let arr = this.lists;
    		for(let i=0;i<arr.length;i++){
    			if(arr[i].fieldName==column.property){
      				return this.selectDictLabel(arr[i].options,cellValue);
    			}
    		}
	    },
    	/** 查询自定义表单列表 */
	    getList() {
	      	this.loading = true;
	    	let data = {
	        	formDesignId:this.formDesignId
	        }
	        deployList(data).then(response => {
	        	console.log(response);
	        	this.lists = response.data;
	        	this.lists2 = JSON.parse(JSON.stringify(this.lists))
	        	this.form = {
	        		lists2:this.lists2
	        	}
	        	for(let i=0;i<this.lists.length;i++){
	        		this.$set( this.lists[i], this.lists[i].fieldName, null )
	        		this.$set( this.lists[i], "val", null )
	        		if(this.lists[i].fieldType=="select"){
	        			this.getDicts(this.lists[i].dictType).then(response => {
				        	this.$set( this.lists[i], "options", response.data )
				      	});
	        		}
	        		if(this.lists[i].fieldType=="radio"){
	        			this.lists[i].options = JSON.parse(this.lists[i].options)
	        		}
	        		if(this.lists[i].fieldType=="checkbox"){
	        			this.lists[i].options = JSON.parse(this.lists[i].options)
	        			this.$set( this.lists[i], "val", [])
	        		}
	        	}
	        	for(let i=0;i<this.lists2.length;i++){
	        		this.$set( this.lists2[i], this.lists2[i].fieldName, null )
	        		this.$set( this.lists2[i], "val", null )
	        		if(this.lists2[i].fieldType=="select"){
	        			this.getDicts(this.lists2[i].dictType).then(response => {
				        	this.$set( this.lists2[i], "options", response.data )
				      	});
	        		}
	        		if(this.lists2[i].fieldType=="radio"){
	        			this.lists2[i].options = JSON.parse(this.lists2[i].options)
	        		}
	        		if(this.lists2[i].fieldType=="checkbox"){
	        			this.lists2[i].options = JSON.parse(this.lists2[i].options)
	        			this.$set( this.lists2[i], "val", [])
	        		}
	        	}
	        	let list = response.data;
	        	let arr = [];
	        	for(let i=0;i<list.length;i++){
	        		let obj = {};
	        		obj.label = list[i].fieldDesc;
	        		obj.prop = list[i].fieldName;
	        		obj.isList = list[i].isList;
	        		obj.fieldType = list[i].fieldType;
	        		arr.push(obj)
	        	}
	        	this.cols = arr;
	        	this.loading = false;
	        	this.$set( this.queryParams, "formDesignId", this.formDesignId )
				this.getTable();
	        })
	    },
	    // 取消按钮
	    cancel() {
	      this.open = false;
	      this.reset();
	    },
	    // 表单重置
	    reset() {
    		this.idd = null;
    		for(let i=0;i<this.form.lists2.length;i++){
        		this.form.lists2[i].val = null;
        		if(this.form.lists2[i].fieldType=="checkbox"){
        			this.$set(this.form.lists2[i], "val", [])
	        	}
	        }
    		this.resetForm("form");
	    },
	    /** 搜索按钮操作 */
	    handleQuery() {
	    	let list = this.lists;
	    	let data = {};
        	for(let i=0;i<list.length;i++){
    			this.queryParams[list[i].fieldName] = list[i].val;
        	}
        	this.$set( this.queryParams, "formDesignId", this.formDesignId )
	      	this.queryParams.pageNum = 1;
        	this.queryParams.pageSize = 30;
        	console.log(this.queryParams)
        	this.getTable();
	    },
	    getTable(){
	    	console.log(this.queryParams);
	    	this.$set( this.queryParams, "formDesignId", this.formDesignId )
	    	testForm(this.queryParams,this.formDesignId).then(response => {
        		console.log(response);
	        	this.formDesignList = response.rows;
        		this.total = response.total;
		    });
	    },
	    /** 重置按钮操作 */
	    resetQuery() {
	    	let list = this.lists;
        	for(let i=0;i<list.length;i++){
        		list[i].val = null;
        		if(list[i].fieldType=="checkbox"){
        			this.$set(list[i], "val", [])
	        	}
        	}
	      	this.handleQuery();
	    },
	    // 多选框选中数据
	    handleSelectionChange(selection) {
	    	console.log(selection)
	      	this.ids = selection.map(item => item.id)
	      	this.single = selection.length!==1
	      	this.multiple = selection.length!==1
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

	    /** 新增按钮操作 */
	    handleAdd() {
	      	this.reset();
	      	this.open = true;
	      	this.addEditId = "";
	      	this.title = "添加"+this.formName;
	    },
	    /** 修改按钮操作 */
	    handleUpdate(row) {
	      	this.reset();
	      	const id = row.id || this.ids
	      	this.addEditId = this.ids[0];
	      	let formDesignId = this.formDesignId;
      		this.open = true;
	      	this.title = "编辑"+this.formName;
	      	getFormDetails(formDesignId,id).then(response => {
	      		let lists = response.data.datas
	      		this.form = {
	        		lists2:lists
	        	}
	      		this.idd = response.data.id;
	      		for(let i=0;i<this.form.lists2.length;i++){
	        		this.$set(this.form.lists2[i],"val", this.form.lists2[i].fieldValue )
  					if(this.form.lists2[i].fieldType=="select"){
	        			this.getDicts(this.form.lists2[i].dictType).then(response => {
			        		this.$set(this.form.lists2[i], "options", response.data )
				      	});
	        		}
  					if(this.form.lists2[i].fieldType=="radio"){
	        			this.form.lists2[i].options = JSON.parse(this.form.lists2[i].options)
	        		}
  					if(this.form.lists2[i].fieldType=="checkbox"){
	        			this.form.lists2[i].options = JSON.parse(this.form.lists2[i].options)
	        			let arr = this.form.lists2[i].fieldValue.split(',');
	        			this.$set(this.form.lists2[i],"val",arr)
	        		}
	      		}
	      		console.log(this.form.lists2);
	        	this.open = true;
	        	this.title = "编辑"+this.formName;
	      	});
	    },
	    /** 提交按钮 */
	    submitForm() {
	    	let list = this.form.lists2;
        	for(let i=0;i<list.length;i++){
        		this.form[list[i].fieldName] = list[i].val;
        	}
        	this.$set( this.form, "formDesignId", this.formDesignId )
        	this.$set( this.form, "id", this.idd )
        	this.$refs["form"].validate(valid => {
		        if (valid) {
					let form = JSON.parse(JSON.stringify(this.form))
					console.log(form);
					delete form.lists2;
		        	if (this.form.id != null) {
		        		addSaveForm(form,this.formDesignId).then(response => {
			        		if(response.code==200){
				            	this.msgSuccess("编辑成功");
			              		this.open = false;
			              		this.getTable();
			        		}
			           	});
		          	} else {
			            addSaveForm(form,this.formDesignId).then(response => {
			        		if(response.code==200){
				            	this.msgSuccess("新增成功");
			              		this.open = false;
			              		this.getTable();
			        		}
			           	});
		          	}
	          	}
	      	});
	    },
	    /** 删除按钮操作 */
	    handleDelete(row) {
	      const ids = row.id || this.ids;
	      let formDesignId = this.formDesignId;
	      this.$confirm('是否确认删除自定义表单编号为"' + ids + '"的数据项?', "警告", {
	        confirmButtonText: "确定",
	        cancelButtonText: "取消",
	        type: "warning"
	      }).then(function() {
	        return delForm(formDesignId,ids);
	      }).then(() => {
	        this.getTable();
	        this.msgSuccess("删除成功");
	      }).catch(() => {});
	    },
    },
}
</script>

<style scoped>

</style>
