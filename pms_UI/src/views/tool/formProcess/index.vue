<template>
	<div class="app-container">
		<div ref="searchs">
			<el-form :model="queryParams" ref="queryForm" :inline="true">
				<el-row>
					<template v-for="(item,i) in lists">
							<template v-if="item.fieldType=='input'">
								<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
									<el-input
										class="search_w220"
										v-model="item.val"
										clearable
										:maxlength="item.fieldLength"
										@keyup.enter.native="handleQuery"
										placeholder="请输入"
									/>
								</el-form-item>
							</template>
							<template v-else-if="item.fieldType=='select'">
									<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
										<el-select class="search_w220" filterable remote v-model="item.val">
												<el-option
													:style="{width:'300px'}"
													v-for="dict in item.options"
													:key="dict.dictValue || dict.id"
													:label="dict.dictLabel || dict.evaCont"
													:value="dict.dictValue || dict.id"

												>
												 <el-tooltip :content="dict.dictLabel || dict.evaCont" placement="top"><span class="opt_tool_span">{{dict.dictLabel || dict.evaCont}}</span></el-tooltip>
												</el-option>
											</el-select>
									</el-form-item>
							</template>
						<template v-else-if="item.fieldType=='date'">
							<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
								<!-- <el-date-picker
									v-model="item.val"
									type="daterange"
									class="search_w220"
									range-separator="至"
									start-placeholder="开始日期"
									end-placeholder="结束日期">
								</el-date-picker> -->
								<el-date-picker
									:type="item.queryType=='between'?'daterange':'date'"
									class="search_w220"
									v-model="item.val"
									value-format="yyyy-MM-dd"
									placeholder="请选择"
								></el-date-picker>
							</el-form-item>
						</template>
						<template v-else-if="item.fieldType=='time'">
							<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
								<el-date-picker
								class="search_w220"
								v-model="item.val"
								type="datetime"
								value-format="yyyy-MM-dd HH:mm:ss"
							></el-date-picker>
							</el-form-item>
						</template>
						<template v-else-if="item.fieldType=='textarea'">
							<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
								<el-input class="search_w220" :maxlength="item.fieldLength" type="textarea" v-model="item.val" placeholder="请输入"></el-input>
							</el-form-item>
						</template>
						<template v-else-if="item.fieldType=='number'">
							<el-form-item :label="item.fieldDesc" v-if="item.isQuery=='1'" :prop="item.fieldName">
								<el-input-number class="search_w220" v-model="item.val" controls-position="right" :min="item.minValue * 1" :max="item.maxValue * 1" :precision="item.decimlDigit *1"></el-input-number>
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
									class="search_w220"
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
				<el-col :span="1.5">
					<el-button
						type="primary"
						icon="el-icon-plus"
						size="mini"
						@click="handleSearch"
					>查询设置</el-button>
				</el-col>
      	<right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
			</el-row>
		</div>
    <el-table border stripe v-loading="loading" ref="multipleTable"  :max-height="tableHeight" :data="formDesignList" @selection-change="handleSelectionChange" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName" highlight-current-row >
      	<el-table-column type="selection" width="55" align="center" />
      	<template v-for="item in cols">
      		<template v-if="item.isList=='1'">
      			<template v-if="item.fieldType=='select'">
      				<el-table-column show-overflow-tooltip :align="item.align" :label="item.label" :prop="item.prop" :formatter="stateFormat" />
      			</template>
      			<template v-else>
							<el-table-column v-if="item.prop=='cs_date'" show-overflow-tooltip :align="item.align" :label="item.label" :prop="item.prop">
								<template slot-scope="scope">
									<el-link type="primary" :underline="false" v-html="scope.row.cs_date" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
								</template>
							</el-table-column>
							<el-table-column v-else-if="item.prop=='cs_score'" show-overflow-tooltip :align="item.align" :label="item.label" :prop="item.prop">
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

    <!-- <pagination
			style="position:fixed;top:90%;right:20px;width:100%"
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    /> -->

    <!-- 添加或修改自定义表单对话框 -->
    <el-dialog :title="title" custom-class="el-button--primary" :visible.sync="open" width="30%" append-to-body @close="cancel" :close-on-click-modal="false">
    	<el-form :model="form" ref="form" label-width="110px">
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
                      <el-input class="w80" :maxlength="item.fieldLength" v-model="item.val" placeholder="请输入"/>
                    </el-form-item>
                    <el-form-item
                        :label="item.fieldDesc"
                        v-else-if="item.isNotNull!='1'"
                        :prop="'lists2.' + i + '.val'"
                        >
                        <el-input class="w80" :maxlength="item.fieldLength" v-model="item.val" placeholder="请输入" />
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
                      <el-input class="w80" v-model="item.val" />
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
                      <el-select v-model="item.val" clearable filterable remote class="w80" no-data-text>
                        <el-option
                            v-for="dict in item.options"
                            :key="dict.dictValue || dict.id"
                            :label="dict.dictLabel || dict.evaCont"
                            :value="dict.dictValue || dict.id"
                          >
													<el-tooltip :content="dict.dictLabel || dict.evaCont" placement="top"><span class="opt_tool_span">{{dict.dictLabel || dict.evaCont}}</span></el-tooltip>
													</el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item
                      v-else-if="item.isNotNull!='1'"
                      :label="item.fieldDesc"
                    :prop="'lists2.' + i + '.val'"
                      >

                      <el-select clearable filterable remote v-model="item.val" class="w80" no-data-text>
                        <el-option
                            v-for="dict in item.options"
                            :key="dict.dictValue || dict.id"
                            :label="dict.dictLabel || dict.evaCont"
                            :value="dict.dictValue || dict.id"
                          >
														<el-tooltip :content="dict.dictLabel || dict.evaCont" placement="top"><span class="opt_tool_span">{{dict.dictLabel || dict.evaCont}}</span></el-tooltip>
													</el-option>
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
                        class="w80"
                        value-format="yyyy-MM-dd"
												placeholder="请选择"
                      ></el-date-picker>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-date-picker
                        v-model="item.val"
                        class="w80"
                        value-format="yyyy-MM-dd"
												placeholder="请选择"
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
                      class="w80"
                      type="datetime"
                      value-format="yyyy-MM-dd HH:mm:ss"
											placeholder="请选择"
                    ></el-date-picker>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-date-picker
                      v-model="item.val"
                      class="w80"
                      type="datetime"
                      value-format="yyyy-MM-dd HH:mm:ss"
											placeholder="请选择"
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
                      <el-input class="w90" :maxlength="item.fieldLength" type="textarea" :rows="item.textLines" v-model="item.val" show-word-limit placeholder="请输入"></el-input>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-input class="w90" :maxlength="item.fieldLength" type="textarea" :rows="item.textLines" v-model="item.val" show-word-limit placeholder="请输入"></el-input>
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
                      <el-input-number class="w80" controls-position="right" :precision="item.decimlDigit *1" :min="item.minValue * 1" :max="item.maxValue * 1" size="mini" v-model="item.val"></el-input-number>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-input-number class="w80" controls-position="right" :precision="item.decimlDigit *1" :min="item.minValue * 1" :max="item.maxValue * 1" size="mini" v-model="item.val"></el-input-number>
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
                      <el-rate v-model="item.val" class="w80"></el-rate>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-rate v-model="item.val" class="w80"></el-rate>
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
                      <el-color-picker class="w80" v-model="item.val"></el-color-picker>
                    </el-form-item>
                    <el-form-item
                      v-else
                      :label="item.fieldDesc"
                      :prop="'lists2.' + i + '.val'">
                      <el-color-picker class="w80" v-model="item.val"></el-color-picker>
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
		<!-- 查询设置对话框 -->
		<el-dialog :title="searchTitle" custom-class="el-button--primary" :visible.sync="open_search" width="50%" append-to-body :close-on-click-modal="false">
			<el-row>
				<el-col :span="24">
						<p class="lit-title" >筛选条件</p>
				</el-col>
				<el-table stripe class="search_table" border v-loading="tableLoading" max-height="250px" :data="formDataList1">
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
              <el-input v-model="scope.row.queryDefault" controls-position="right" />
            </template>
          </el-table-column>
        </el-table>
				<el-col :span="24">
          <p class="lit-title" >排序</p>
        </el-col>
				 <el-table stripe class="search_table" border v-loading="tableLoading" max-height="250px" :data="formDataList2" >
          <el-table-column label="字段名称" align="center" prop="fieldDesc" />
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
			</el-row>

			<div slot="footer" class="dialog-footer">
        	<el-button type="primary" @click="submitFormSearchSet">确 定</el-button>
        	<el-button @click="cancelSearch">取 消</el-button>
      </div>
		</el-dialog>
  	</div>
</template>

<script>
import { deployList,saveDesign,getForm,testForm,getFormDetails,addSaveForm,delForm} from '@/api/utils';
import {getDeptInfoForPms,getFormTableList,editAddFormDesign} from "@/api/formDesign/formDesign";
import { listDetail } from "@/api/quality/detail";
import moment from "moment";
import { getFormDesign } from '../../../api/formDesign/formDesign'
export default {
	data(){
        return{
					tableHeight:'auto',
					isAdd:false,
        	idd:null,
        	// 修改id
      		addEditId: "",
        	lists:[],
        	lists2:[],
        	cols:[],
          	jsonData:{
          		list:[],
          	},
          	formDesignId:"",
          	// formDesignId:this.$route.name,
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
					searchTitle:"",
	      	// 是否显示弹出层
	      	open: false,
					open_search:false,
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
					tableLoading:false,
					formDataList:[],
					formDataList1:[],
					formDataList2:[],
					mbpxTypeOptions:[],
					queryTypeOptions:[],
					isPmsGuidance:false,
					isPmsResult:false,
					isAddShow:false,
					targetValue:[],//指标数据
					itemAlign:''
        }
    },
    components:{},
    created() {
      console.log("进入信息");
				this.getDicts("pms_mbpx_type").then(response => {
					this.mbpxTypeOptions = response.data;
				});
				this.getDicts("pms_mbsx_tj").then(response => {
					this.queryTypeOptions = response.data;
				});

      console.log(".$route.path="+this.$route.path);
      getFormDesign(this.$route.name).then((response) =>{
        console.log("修改前formDesignId="+this.formDesignId);
        this.formDesignId = response.data.id;
        this.formName = response.data.formName;
        console.log("修改后formDesignId="+this.formDesignId);

      })
      console.log("created 中formDesignId="+this.formDesignId);
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
			handleClickTableName(data) {
        // 选中当前行
        this.rowClick(data, false);
        this.handleUpdate(data);
      },
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
						if(arr[i].fieldName=="cs_target"){
							return this.selectDictIndexLabel(arr[i].options,cellValue)
						}else{
							return this.selectDictLabel(arr[i].options,cellValue);
						}
					}
				}
			},
    	/** 查询自定义表单列表 */
	    getList() {
	      	// this.loading = true;
	    		let data = {
						// formDesignId:this.formDesignId,
						formDesignId:this.$route.name,
	        }
        console.log("list 中formDesignId="+this.formDesignId);
	        deployList(data).then(response => {
	        	// console.log(response);
	        	this.lists = response.data;
	        	this.lists2 = JSON.parse(JSON.stringify(this.lists))
	        	this.form = {
	        		lists2:this.lists2
						}
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
					if(this.lists[i].fieldType=="date"&&this.lists[i].queryType=="between"){
						this.$set( this.lists[i], "val", [moment().startOf('month').format('YYYY-MM-DD'),moment().endOf('month').format('YYYY-MM-DD')])
					}
						}
	        	for(let i=0;i<this.lists2.length;i++){
	        		this.$set( this.lists2[i], this.lists2[i].fieldName, null );
							this.$set( this.lists2[i], "val", this.lists2[i].defaultValue );
	        		if(this.lists2[i].fieldType=="select"){
								if(this.lists2[i].fieldName=="cs_target"){
									let params={
											indexCode:this.lists2[i].indexCode,
										  compCode:this.$store.state.user.userInfo.deptId   //必传
									}
									listDetail(params).then(response=>{
											if(response.code===200){
												this.$set( this.lists2[i], "options",response.rows )
											}
									})
								}else if(this.lists2[i].fieldName=="cs_dept"){
									let params= {
										compCode:this.$store.state.user.userInfo.deptId
									}
									getDeptInfoForPms(params).then(res=>{
											if(res.code===200){
													this.$set( this.lists2[i], "options",res.data )
											}
									})
								}else{
									this.getDicts(this.lists2[i].dictType).then(response => {
										this.$set( this.lists2[i], "options", response.data )
									});
								}
							}
	        		if(this.lists2[i].fieldType=="radio"){
	        			this.lists2[i].options = JSON.parse(this.lists2[i].options)
	        		}
	        		if(this.lists2[i].fieldType=="checkbox"){
	        			this.lists2[i].options = JSON.parse(this.lists2[i].options)
	        			this.$set( this.lists2[i], "val", [])
							}
						}
						// console.log(this.lists2,'this.lists2---')
						let list = response.data;
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
				this.handleQuery();
	        	// this.$set( this.queryParams, "formDesignId", this.formDesignId )
				// 		this.getTable();
	        })
			},
	    // 取消按钮
	    cancel() {
	      this.open = false;
				this.reset();
				this.getTable();
	    },
	    // 表单重置
	    reset() {
				this.idd = null;
				let lists2Length=this.form.lists2.filter(_=>{return _.fieldName=="dept_track"});
    		for(let i=0;i<this.form.lists2.length;i++){
						this.form.lists2[i].val = this.form.lists2[i].defaultValue;

        		if(this.form.lists2[i].fieldType=="checkbox"){
        			this.$set(this.form.lists2[i], "val", [])
						}

					}
					if(lists2Length.length!=0){
							let guidance=this.form.lists2.find((_=>{return _.fieldName=='pms_guidance'}));
							let resultPms=this.form.lists2.find((_=>{return _.fieldName=='pms_result'}));
							guidance.isEdit="0";
							resultPms.isEdit="0";
							guidance.fieldValue=null;
							resultPms.fieldValue=null;
					}
					// console.log(this.form.lists2,'reset')
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
        	this.getTable();
			},
			//表格数据
	    getTable(){
				this.loading = true;
				// this.$set( this.queryParams, "formDesignId", this.formDesignId );
				this.$set( this.queryParams, "formDesignId", this.$route.name );
				this.$set( this.queryParams, "comp_code", this.$store.state.user.userInfo.deptId );
	    	// testForm(this.queryParams,this.formDesignId).then(response => {
	    	testForm(this.queryParams,this.$route.name).then(response => {
						// console.log(response,'res');
						if(response.code==200){
							this.formDesignList = response.rows;
        			this.total = response.total;
							this.loading = false;
						}

		    });
	    },
			//查询设置-表格列表数据
      getFormTableList(){
        this.tableLoading=true;
        getFormTableList({formDesignId:this.formDesignId}).then(response=>{
          if(response.code===200){
						this.formDataList=JSON.parse(JSON.stringify(response.data));
						this.formDataList1=JSON.parse(JSON.stringify(response.data.filter((ite)=>{
							return ite.isQuery==1 && ite.isListQuery==1
						})))
           this.formDataList2=JSON.parse(JSON.stringify(response.data.filter((ite)=>{
             return ite.isSort==1
           })));
           this.tableLoading=false;
          }
        })
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
	    	// console.log(selection)
	      	this.ids = selection.map(item => item.id)
					this.single = selection.length!==1
					if(selection.length!==0&&selection[0].pms_state && selection.length==1){
						this.multiple = selection[0].pms_state!=="1"
					}else{
						this.multiple = selection.length!==1
					}
					this.selectionRow=selection
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
					this.isAdd=true;
					this.title = this.formName+"-添加";
	    },
	    /** 修改按钮操作 */
	    handleUpdate(row) {
					this.isAdd=false;
	      	this.reset();
	      	const id = row.id || this.ids
	      	this.addEditId = this.ids[0];
	      	let formDesignId = this.formDesignId;
      		this.open = true;
	      	this.title = this.formName+"-编辑";
	      	getFormDetails(formDesignId,id).then(response => {
						let lists = response.data.datas;
	      		this.form = {
	        		lists2:lists
	        	}
						this.idd = response.data.id;
						let lists2Length=this.form.lists2.filter(_=>{return _.fieldName=="dept_track"});
						// console.log(this.form.lists2,'form.lists2')
	      		for(let i=0;i<this.form.lists2.length;i++){
							this.$set(this.form.lists2[i],"val", this.form.lists2[i].fieldValue )
  					if(this.form.lists2[i].fieldType=="select"){
							//指标详情数据
							if(this.form.lists2[i].fieldName=="cs_target"){
									this.$set(this.form.lists2[i],"val", null )
								let params={
											indexCode:this.form.lists2[i].indexCode,
											compCode:this.$store.state.user.userInfo.deptId  // 必传
								};
								listDetail(params).then(response=>{
										if(response.code===200){
											this.$set( this.form.lists2[i], "options",response.rows );
											let	targetValue=response.rows.filter(_=>{
												if(_.id==this.form.lists2[i].fieldValue || _.evaCont==this.form.lists2[i].fieldValue){
													return _
												}
											});
											this.$set(this.form.lists2[i],"val", targetValue[0].evaCont )
										}
								})
							}else if(this.form.lists2[i].fieldName=="cs_dept"){
								//部门详情数据
									let params= {
											compCode:this.$store.state.user.userInfo.deptId
										}
										getDeptInfoForPms(params).then(res=>{
												if(res.code===200){
														this.$set( this.form.lists2[i], "options",res.data )
												}
										})
							}else{
								this.getDicts(this.form.lists2[i].dictType).then(response => {
			        		this.$set(this.form.lists2[i], "options", response.data )
				      	});
							}
						}
  					if(this.form.lists2[i].fieldType=="radio"){
	        			this.form.lists2[i].options = JSON.parse(this.form.lists2[i].options)
	        		}
  					if(this.form.lists2[i].fieldType=="checkbox"){
	        			this.form.lists2[i].options = JSON.parse(this.form.lists2[i].options)
	        			let arr = this.form.lists2[i].fieldValue.split(',');
	        			this.$set(this.form.lists2[i],"val",arr)
							}
							//是科室跟踪时-编辑增加状态判断
							if(lists2Length.length!=0){
									let guidance=this.form.lists2.find((_=>{return _.fieldName=='pms_guidance'}));
									let resultPms=this.form.lists2.find((_=>{return _.fieldName=='pms_result'}));
										if(this.selectionRow[0].pms_state=="1"){
												guidance.isEdit="1";
												resultPms.isEdit="0";
										}else if(this.selectionRow[0].pms_state=="2" || this.selectionRow[0].pms_state=="3"){
												guidance.isEdit="1"
												resultPms.isEdit="1"
										}else{
											guidance.isEdit="0";
											resultPms.isEdit="0"
										}
							}
						}
						// console.log(this.selectionRow[0],'row')
						// console.log(this.form.lists2,'bianji')
	        	this.open = true;
	        	this.title = "编辑"+this.formName;
	      	});
	    },
	    /** 提交按钮 */
	    submitForm() {
        try {
          let list = this.form.lists2;
          // pms_state
          // console.log(list,'list')
          for (let i = 0; i < list.length; i++) {
            this.form[list[i].fieldName] = list[i].val;
          }
          this.$set(this.form, "formDesignId", this.formDesignId)
          this.$set(this.form, "id", this.idd)
          this.$refs["form"].validate(valid => {
            if (valid) {
              let form = JSON.parse(JSON.stringify(this.form))
              delete form.lists2;
              let bool = form.hasOwnProperty('dept_track');
              if (bool && this.isAdd) {
                form.pms_state = "1";
                form.pms_result = null;
                form.pms_guidance = null;
              }
              let saveLoading = this.saveLoading()
              form.cs_score = form.cs_score ? form.cs_score * 1 : 0;
              if (this.form.id != null) {
                if (form.pms_guidance) {
                  form.pms_state = "2"
                }
                if (form.pms_guidance && form.pms_result) {
                  form.pms_state = "3"
                }
                //指标重新赋值为id
                let formTarget = this.targetValue.filter(_ => {
                  if (_.evaCont == form.cs_target || _.id == form.cs_target) {
                    return _
                  }
                })
                if(formTarget && formTarget.length > 0){
                  form.cs_target = formTarget[0].id;
                }
                addSaveForm(form, this.formDesignId).then(response => {
                  if (response.code == 200) {
                    this.msgSuccess("编辑成功");
                    saveLoading.close();
                    this.open = false;
                  }
                });
              } else {
                form.comp_code = this.$store.state.user.userInfo.deptId;
                form.cs_score = form.cs_score + "";
                // console.log(form)
                addSaveForm(form, this.formDesignId).then(response => {
                  if (response.code == 200) {
                    this.msgSuccess("新增成功");
                    saveLoading.close();
                    this.open = false;
                  }
                });
              }
            }
          });
        } catch (e) {
          this.saveLoading().close();
          this.$message({
            showClose: true,
            message: '出错了，请联系管理员',
            type: 'error',
            duration: 800
          });
        }
			},
			//查询设置保存
			submitFormSearchSet(){
				let fdcList=[];
				for(let i = 0; i < this.formDataList.length; i++) {
					let tempArr1 = this.formDataList[i]
					for(let j = 0; j < this.formDataList1.length; j++) {
						let tempArr2 = this.formDataList1[j];
						if(tempArr2.id === tempArr1.id){
							tempArr1.queryType=tempArr2.queryType;
							tempArr1.queryDefault=tempArr2.queryDefault;
							break;
						}
					}
					for(let k=0;k<this.formDataList2.length;k++){
							let tempArr2 = this.formDataList2[k];
							if(tempArr2.id === tempArr1.id){
								tempArr1.soortType=tempArr2.soortType;
								break;
						}
					}
					fdcList.push(tempArr1)
				}
				let saveLoading=this.saveLoading()
					editAddFormDesign({fdcList:fdcList}).then(response => {
						if(response.code===200){
							this.msgSuccess("保存成功");
							this.cancelSearch();
							saveLoading.close();
						}
				  })
			},
			//查询按钮操作
			handleSearch(){
	      let formDesignId = this.formDesignId;
				this.getFormTableList();
				this.searchTitle="动态表单/查询设置";
				this.open_search=true;
			},
			//关闭查询框
			cancelSearch(){
				this.open_search=false;
				this.getTable();
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

<style lang="scss" scoped>
.lit-title{
    font-size: 16px;
    padding-bottom: 8px;
		margin:20px 0;
    border-bottom: 1px dashed #cccccc;
}
::v-deep{
	.el-dialog {
		max-height: 80%;
		overflow: hidden;
		.el-dialog__body {
			height: calc(100% - 100px);
			overflow: auto;
		}
	}
	.el-table{
		overflow: hidden;
	}
	 .el-table.search_table{
			overflow: hidden;
		}
}
.opt_tool_span{
	display: block;
	max-width:360px;
	// padding-right: 20px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
</style>
