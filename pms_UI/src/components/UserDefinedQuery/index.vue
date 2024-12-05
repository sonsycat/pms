<template>
  <div :inline="true" v-show="showUserDefinedQuery" style="border:1px dashed #000;margin-bottom: 20px;">
    <el-row :gutter="24">
      <el-col :span="18">
        <div v-for="(item, index) in queryParams.queryRows" :key="index" style="margin: 10px">
          <el-row :gutter="24">
            <el-col :span="6">
              <el-select clearable size="small" v-model="item.fieldName"
                         @change="fieldChange(item,index)" placeholder="请选择查询字段">
                <el-option v-for="field in fieldOptions" :key="field.fieldName" :label="field.fieldLabel"
                           :value="field.fieldName"/>
              </el-select>
            </el-col>
            <el-col :span="6">
              <el-select clearable size="small" v-model="item.fieldConnect"
                         v-if="item.type === 'number' || item.type === 'datetime'">
                <el-option v-for="dict in fieldConnectDatetimeIntBigintOptions" :key="dict.dictValue"
                           :label="dict.dictLabel"
                           :value="dict.dictValue"/>
              </el-select>
              <el-select clearable size="small" v-model="item.fieldConnect" v-if="item.type === 'dict'">
                <el-option v-for="dict in fieldConnectCharOptions" :key="dict.dictValue" :label="dict.dictLabel"
                           :value="dict.dictValue"/>
              </el-select>
              <el-select clearable size="small" v-model="item.fieldConnect" v-if="item.type === 'dict_checkbox'">
                <el-option v-for="dict in fieldConnectCharCheckboxOptions" :key="dict.dictValue" :label="dict.dictLabel"
                           :value="dict.dictValue"/>
              </el-select>
              <el-select clearable size="small" v-model="item.fieldConnect" v-if="item.type === 'input'">
                <el-option v-for="dict in fieldConnectVarcharOptions" :key="dict.dictValue" :label="dict.dictLabel"
                           :value="dict.dictValue"/>
              </el-select>
            </el-col>
            <el-col :span="6">
              <el-select clearable size="small" v-model="item.fieldValue" placeholder="请选择值"
                         v-if="item.type === 'dict' || item.type === 'dict_checkbox' ">
                <el-option v-for="dict in item.fieldDictOptions" :key="dict.dictValue" :label="dict.dictLabel"
                           :value="dict.dictValue"/>
              </el-select>
              <el-input placeholder="请输入值" clearable size="small" v-model="item.fieldValue"
                        v-if="item.type === 'input'"></el-input>
              <!-- TODO 校验只能输入数字 -->
              <el-input placeholder="请输入数值" clearable size="small" v-model="item.fieldValue"
                        v-if="item.type === 'number'"></el-input>
              <el-date-picker type="date" v-model="item.fieldValue" value-format="yyyy-MM-dd"
                              placeholder="请选择日期" v-if="item.type === 'datetime'"></el-date-picker>
            </el-col>
            <el-col :span="6">
              <el-button icon="el-icon-plus" @click="addRow"/>
              <el-button icon="el-icon-delete" @click="delRow(index)"/>
            </el-col>
          </el-row>
        </div>
        <div style="margin: 10px">
          <el-row :gutter="24">
            <el-col :span="16">
              <a>过滤条件匹配： </a>
              <el-select clearable size="small" v-model="queryParams.filterCondition">
                <el-option v-for="dict in filterConditionOptions" :key="dict.dictValue" :label="dict.dictLabel"
                           :value="dict.dictValue"/>
              </el-select>
            </el-col>
            <el-col :span="8">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery()">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery()">重置</el-button>
              <el-button type="info" icon="el-icon-plus" size="mini" @click="openSaveUserDefinedQueryDialog">保存查询条件
              </el-button>
              <el-dialog :visible.sync="userDefinedQueryDialog" :close-on-click-modal="true" title="自定义查询名称"
                         append-to-body width="500px">
                <el-form ref="form" :model="userDefinedQueryForm" :rules="rules" label-width="150px">
                  <el-row>
                    <el-col :span="18">
                      <el-form-item label="自定义查询名称" prop="queryName">
                        <el-input v-model="userDefinedQueryForm.queryName" placeholder="请输入自定义查询名称"/>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-form>
                <div slot="footer" class="dialog-footer" style="text-align: center">
                  <el-button type="primary" @click="saveUserDefinedQuery('form')">确定</el-button>
                </div>
              </el-dialog>
            </el-col>
          </el-row>
        </div>
      </el-col>
      <el-col :span="6">
        <div style="margin: 10px">
          <el-table :data="userDefinedQueryData" style="width: 100%" @row-click="rowClick">
            <el-table-column prop="queryName" label="保存的查询" width="240px"></el-table-column>
            <el-table-column label="操作" width="60px">
              <template slot-scope="scope">
                <el-button icon="el-icon-delete" @click="delUserDefinedQuery(scope.row)"/>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {
    getUserDefinedQueryField, userDefinedQueryDataList, addUserDefinedQuery, listUserDefinedQuery, delUserDefinedQuery
  } from '@/api/system/userDefinedQuery'

  export default {
    name: 'userDefinedQuery',
    components: {},
    data() {
      return {
        // 查询参数
        queryParams: {
          tableName: this.tableName, //自定义查询表名
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          checkDelFlag: this.checkDelFlag, // 是否值查询del_flag=0的数据
          filterCondition: 'and', //过滤条件匹配
          queryRows: [{
            fieldName: '', //字段名
            fieldConnect: 'eq', //字段连接符
            fieldValue: '', //字段值
            fieldType: '', //字段类型
            type: 'input', //字符串输入框、数值输入框、下拉框、或者时间组件
            fieldDictOptions: [] //字段字典Options
          }]
        },
        // 过滤条件匹配Options
        filterConditionOptions: [],
        // 可查询字段Options
        fieldOptions: [],
        // 字段连接Options
        fieldConnectDatetimeIntBigintOptions: [],
        fieldConnectCharOptions: [],
        fieldConnectVarcharOptions: [],
        fieldConnectCharCheckboxOptions: [{ dictValue: 'like', dictLabel: '包含' }],
        // char类型字典Options
        fieldDictOptions: [],
        // field对象与queryId无用，只是为了防止代码出现波浪线
        field: { fieldName: '', fieldType: '', fieldLabel: '' },
        queryId: '',
        // 保存的查询table数据
        userDefinedQueryData: [],
        // 自定义查询保存Dialog
        userDefinedQueryDialog: false,
        // 待保存的自定义查询表单
        userDefinedQueryForm: { queryName: '', queryIndex: this.queryIndex, filterCondition: '', queryRows: '' },
        // 表单校验
        rules: {
          queryName: [
            { required: true, message: '请输入自定义查询名称', trigger: 'blur' }
          ]
        }
      }
    },
    props: {
      // 是否展示自定义查询
      showUserDefinedQuery: {
        type: Boolean,
        default: false
      },
      // 自定义查询表的表名
      tableName: {
        type: String,
        default: ''
      },
      // 自定义查询索引
      queryIndex: {
        type: String,
        default: ''
      },
      // 不需要自定义查询的字段数组
      excludeFieldArr: {
        type: Array,
        default: []
      },
      // 多选框连接符只能选择包含
      dictCheckboxArr: {
        type: Array,
        default: []
      },
      // 需要从字典中获取Options的自定义查询字段对象
      dictFieldObj: {
        type: Object,
        default: {}
      },
      // 分页参数
      pageNum: {
        type: Number,
        default: 1
      },
      pageSize: {
        type: Number,
        default: 10
      },
      // 是否添加del_flag=0作为查询条件
      checkDelFlag: {
        type: Boolean,
        default: true
      }
    },
    created() {
      // 1.查询对象实例的字段
      getUserDefinedQueryField({ tableName: this.tableName }).then(response => {
        // 排除掉fieldOptions中excludeFieldArr中的
        response.data.forEach((obj) => {
          if (this.excludeFieldArr.indexOf(obj.fieldName) === -1) {
            this.fieldOptions.push(obj)
          }
        })
      })
      // 2.查询保存的自定义查询条件
      this.listUserDefinedQuery()

      // 3.过滤条件匹配Options
      this.getDicts('filter_condition').then(response => {
        this.filterConditionOptions = response.data
      })
      // 4.字段连接条件Options
      this.getDicts('field_connect_datetime_int_bigint').then(response => {
        this.fieldConnectDatetimeIntBigintOptions = response.data
      })
      this.getDicts('field_connect_char').then(response => {
        this.fieldConnectCharOptions = response.data
      })
      this.getDicts('field_connect_varchar').then(response => {
        this.fieldConnectVarcharOptions = response.data
      })
    },
    methods: {
      // 1.增加一行
      addRow() {
        this.queryParams.queryRows.push({
          fieldName: '',
          fieldConnect: 'eq',
          fieldValue: '',
          fieldType: '',
          type: 'input',
          fieldDictOptions: []
        })
      },
      // 2.减少一行
      delRow(index) {
        this.queryParams.queryRows.splice(index, 1)
        if (this.queryParams.queryRows.length === 0) {
          this.addRow()
        }
      },
      // 3.查询字段下拉框改变
      fieldChange(item, index) {
        this.queryParams.queryRows[index].fieldValue = ''
        this.queryParams.queryRows[index].fieldDictOptions = []
        if (item.fieldName === '') {
          this.queryParams.queryRows[index].fieldType = ''
          this.queryParams.queryRows[index].type = 'input'
          this.queryParams.queryRows[index].fieldConnect = 'eq'
          return
        }
        // 查找出选中的字段所有属性
        this.fieldOptions.forEach((obj) => {
          if (obj.fieldName === item.fieldName) {
            this.queryParams.queryRows[index].fieldType = obj.fieldType
            if (obj.fieldType === 'datetime') {
              this.queryParams.queryRows[index].type = 'datetime'
            } else if (obj.fieldType === 'int' || obj.fieldType === 'bigint') {
              this.queryParams.queryRows[index].type = 'number'
            } else {
              // 使用临时变量temp来解决时间组件和下拉组件向下拉组件过渡时出现输入框
              let temp = 0
              // 从字典中获取Options的自定义查询字段数组
              for (let [key, value] of Object.entries(this.dictFieldObj)) {
                if (key === item.fieldName) {
                  temp++
                  this.getDicts(value).then(response => {
                    if (this.dictCheckboxArr.indexOf(item.fieldName) !== -1) {
                      this.queryParams.queryRows[index].fieldConnect = 'like'
                      this.queryParams.queryRows[index].type = 'dict_checkbox'
                    } else {
                      this.queryParams.queryRows[index].type = 'dict'
                    }
                    this.queryParams.queryRows[index].fieldDictOptions = response.data
                  })
                }
              }
              if (temp === 0) {
                this.queryParams.queryRows[index].type = 'input'
              }
            }
          }
        })
      },
      // 4.重置查询
      resetQuery() {
        this.queryParams.queryRows = [{
          fieldName: '',
          fieldConnect: '=',
          fieldValue: '',
          fieldType: '',
          type: 'input',
          fieldDictOptions: []
        }]
        this.handleQuery()
      },
      // 5.搜索查询-自定义查询获取业务数据
      handleQuery() {
        userDefinedQueryDataList(this.queryParams).then(response => {
          this.$emit('refreshData', response)
        })
      },
      // 6.打开自定义查询
      openSaveUserDefinedQueryDialog() {
        this.userDefinedQueryForm.queryName = ''
        this.userDefinedQueryDialog = true
      },
      // 7.保存自定义查询
      saveUserDefinedQuery(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.userDefinedQueryForm.filterCondition = this.queryParams.filterCondition
            this.userDefinedQueryForm.queryRows = JSON.stringify(this.queryParams.queryRows)
            addUserDefinedQuery(this.userDefinedQueryForm).then(response => {
              this.$message({
                message: '保存成功',
                type: 'success'
              })
              this.userDefinedQueryDialog = false
              // 保存完成之后执行查询
              this.listUserDefinedQuery()
            })
          } else {
            return false
          }
        })
      },
      // 8.查询保存的自定义查询条件
      listUserDefinedQuery() {
        listUserDefinedQuery({ queryIndex: this.queryIndex }).then(response => {
          this.userDefinedQueryData = response.data
        })
      },
      // 9.右侧自定义查询表格某一行被点击时出发 渲染左侧数据
      rowClick(row) {
        this.queryParams.filterCondition = row.filterCondition
        this.queryParams.queryRows = JSON.parse(row.queryRows)
      },
      // 10.删除自定义查询
      delUserDefinedQuery(row) {
        const queryIds = row.queryId
        this.$confirm('是否确认删除用户编号为"' + queryIds + '"的数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          return delUserDefinedQuery(queryIds)
        }).then(() => {
          // 删除完成之后执行查询
          this.listUserDefinedQuery()
          this.msgSuccess('删除成功')
        }).catch(() => {
        })
      }
    }
  }
</script>

