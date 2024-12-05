<template>
  <div class="app-container">
    <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="科室名称" prop="deptRoomName">
        <el-input
          v-model="queryParams.deptRoomName"
          placeholder="请输入科室名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="科室编码" prop="deptRoomCode">
        <el-input
          v-model="queryParams.deptRoomCode"
          placeholder="请输入科室编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <orgSearch label="所属机构" :queryParams="queryParams" compCode="deptId" compName="deptName"></orgSearch>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:room:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:room:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:room:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-upload2"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['system:room:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    </div>
    <el-table
      ref="indexTable"
      v-loading="loading"
      :height="tableHeight"
      :data="roomList"
      :row-style="rowStyle"
      @row-click="rowClick"
      @selection-change="handleSelectionChange"> <!--@selection-change="handleSelectionChange"-->
      <el-table-column type="selection" width="55" align="center"/>
      <!--<el-table-column align="center" width="80" label="选择">
        <template slot-scope="scope">
          <el-checkbox-group v-model="checkList" class="tableCheckbox">
          	<el-checkbox :label="scope.row.deptRoomCode" @change="checkBoxChange(scope.row)">1</el-checkbox>
          </el-checkbox-group>
        </template>
      </el-table-column>-->
      <el-table-column label="科室名称" align="left" prop="deptRoomName"/>
      <el-table-column label="科室编码" align="center" prop="deptRoomCode"/>
      <el-table-column label="所属机构" align="left" prop="deptName"/>
      <el-table-column label="科室类型" align="center" prop="type" :formatter="typeFormat"/>
      <el-table-column label="负责人" align="left" prop="dutyOfficer"/>
      <el-table-column label="科室电话" align="right" prop="phone"/>
      <el-table-column label="版本" align="center" prop="version"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            title="修改"
            v-hasPermi="['system:room:edit']"
          >
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
             title="删除"
            v-hasPermi="['system:room:remove']"
          >
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 所属机构search-dialog -->
    <el-dialog :visible.sync="deptSearchDialog" :close-on-click-modal="false" title="选择机构" append-to-body width="500px" custom-class="el-button--primary">
      <div style="height: 400px;overflow-y: scroll">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入机构名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            :load="getTreeSelect"
            lazy
            @node-click="handleNodeClick"
          />
        </div>
      </div>
    </el-dialog>

    <!-- 添加或修改科室对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body custom-class="el-button--primary">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <orgSearch label="所属机构" :queryParams="form" compCode="deptId" compName="deptName"></orgSearch>
          </el-col>
          <el-col :span="12">
            <el-form-item label="科室名称" prop="deptRoomName">
              <el-input v-model="form.deptRoomName" placeholder="请输入科室名称"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="科室编码" prop="deptRoomCode">
              <el-input v-model="form.deptRoomCode" placeholder="请输入科室编码"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="科室类型" prop="type">
              <el-select v-model="form.type" placeholder="请选择科室类型" class="w100">
                <el-option
                  v-for="dict in typeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="版本" prop="version">
              <el-input v-model="form.version" placeholder="请输入版本"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="拼音码" prop="pym">
              <el-input v-model="form.pym" placeholder="请输入拼音码"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="负责人" prop="dutyOfficer">
              <el-input v-model="form.dutyOfficer" placeholder="请输入负责人"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否核算" prop="isIndependentAccounting">
              <el-select v-model="form.isIndependentAccounting" placeholder="请选择是否核算" class="w100">
                <el-option
                  v-for="dict in isChangeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否独立核算" prop="isAccounting">
              <el-select v-model="form.isAccounting" placeholder="请选择是否独立核算" class="w100">
                <el-option
                  v-for="dict in isChangeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否采购" prop="isPurchase">
              <el-select v-model="form.isPurchase" placeholder="请选择是否采购" class="w100">
                <el-option
                  v-for="dict in isChangeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否服务" prop="isService">
              <el-select v-model="form.isService" placeholder="请选择是否服务" class="w100">
                <el-option
                  v-for="dict in isChangeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否末级" prop="isLeaf">
              <el-select v-model="form.isLeaf" placeholder="请选择是否末级" class="w100">
                <el-option
                  v-for="dict in isChangeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="临床科室类别" prop="clinicType">
              <el-select v-model="form.clinicType" placeholder="请选择临床科室类别" class="w100">
                <el-option
                  v-for="dict in clinicTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="科室地址" prop="depAddress">
              <el-input type="textarea" :rows="2" v-model="form.depAddress" placeholder="请输入科室地址"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" :rows="2"  v-model="form.remark" placeholder="请输入备注"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- Form所属机构search-dialog -->
    <el-dialog :visible.sync="deptSearchFormDialog" :close-on-click-modal="false" title="选择机构" append-to-body width="500px" custom-class="el-button--primary">
      <div style="height: 400px;overflow-y: scroll">
        <div class="head-container">
          <el-input
            v-model="deptNameForm"
            placeholder="请输入机构名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="treeForm"
            :load="getTreeSelect"
            lazy
            @node-click="handleFormNodeClick"
          />
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    listDeptRoom,
    getDeptRoom,
    delDeptRoom,
    addDeptRoom,
    updateDeptRoom,
    exportDeptRoom,
    deptRoomTree,
    importTemplate

  } from '@/api/basic/deptRoom'
  import { treeselect } from '@/api/system/dept'
  import orgSearch from "@/views/components/orgSearch";

  export default {
    name: 'Room',
    components: {orgSearch},
    data() {
      return {
      	checkList:[],

        // 遮罩层
        loading: true,
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
        // 科室表格数据
        roomList: [],
        // 弹出层标题
        title: '',
        // 是否显示弹出层
        open: false,
        // 表格上方机构查询dialog
        deptSearchDialog: false,
        // 机构树选项
        deptOptions: undefined,
        // dialog中search机构名称
        deptName: undefined,
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        // Form表单机构查询dialog
        deptSearchFormDialog: false,
        // Form中search机构名称
        deptNameForm: undefined,
        tableHeight: "auto",
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 30,
          deptRoomName: null,
          deptRoomCode: null,
          deptId: this.$store.state.user.userInfo.deptId,
          deptName: this.$store.state.user.userInfo.deptName,
        },
        // 用于显示查询的机构名称
        queryShowDeptName: '',
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          deptName: [
            { required: true, message: "所属机构不能为空", trigger: "blur" }
          ],
          deptRoomName: [
            { required: true, message: "用户昵称不能为空", trigger: "blur" }
          ],
          deptRoomCode: [
            { required: true, message: "用户密码不能为空", trigger: "blur" }
          ]
        },
        selectionRow:[],
        //科室类型
        typeOptions:[],
        //是否变更字典
        isChangeOptions:[],
        //临床科室类别字典
        clinicTypeOptions:[],
      }
    },
    computed: {
      listData() {
        return this.$store.state.app.sidebar.opened;
      }
    },
    watch: {
      "$store.state.dicts": {
        handler(val) {
          // 基础字典
          let baseDicts = val.baseDicts;
          // this.assetTypeOptions = baseDicts.hrAssetType;
          // this.isMeasureOptions = baseDicts.hrIsChange;
          // this.isDepreciationOptions = baseDicts.hrIsChange;
          // this.depreciationWayOptions = baseDicts.hrAssetDepreciationPlan;
          // this.isCostOptions = baseDicts.hrIsChange;
          // this.isLastOptions = baseDicts.hrIsChange;
          this.isChangeOptions = baseDicts.hrIsChange;
          // 特殊
          // this.supplierOptions = val.supplierInfo;
          // this.userOptions = val.userOptsInfo;
        },
        deep: true,
        immediate: true
      },
      listData(val) {
      var that = this;
      setTimeout(function() {
        that.resizeHandler();
      }, 300);
    },
      // 根据名称筛选部门树
      deptName(val) {
        this.$refs.tree.filter(val)
      },
      // 根据名称筛选部门树
      deptNameForm(val) {
        this.$refs.treeForm.filter(val)
      }
    },
    created() {
      this.getList()
      this.getDicts("COST_DEPT_TYPE").then(response => {
        this.typeOptions = response.data;
      });
      this.getDicts("clinic_type").then(response => {
        this.clinicTypeOptions = response.data;
      });
    },
    mounted(){
      this.$nextTick(() => {
        this.resizeHandler();
        window.addEventListener("resize", this.resizeHandler);
      });
    },
    methods: {
      resizeHandler() {
        let a = this.$refs.search.offsetHeight;
        let h = a + 18 + 45 + 84 + 40 + 10;
        h = h + "px";
        this.tableHeight = `calc(100vh - ${h})`;
      },
    	checkBoxChange(val) { // 控制 单选
    		console.log(val.deptRoomCode);
        if(this.checkList.indexOf(val.deptRoomCode) > -1){
          this.checkList = [];
          this.checkList.push(val.deptRoomCode);
        }
        console.log(this.checkList);
			},

      // 科室类型字典翻译
      typeFormat(row, column) {
        return this.selectDictLabel(this.typeOptions, row.type);
      },

      /** 查询科室列表 */
      getList() {
        this.loading = true
        listDeptRoom(this.queryParams).then(response => {
          console.log(response);
          if (response.code == 200) {
            this.roomList = response.rows
            this.total = response.total
            this.loading = false
          } else {
            this.msgError(response.msg);
          }
        })
      },
      // 取消按钮
      cancel() {
        this.open = false
        this.reset()
      },
      // 表单重置
      reset() {
        this.form = {
          deptRoomId: null,
          parentId: null,
          deptRoomName: null,
          deptRoomCode: null,
          deptId: null,
          deptName: null,
          type: null,
          sort: null,
          grade: null,
          dutyOfficer: null,
          phone: null,
          depAddress: null,
          status: '0',
          remark: null,
          delFlag: null,
          version: null,
          pym: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          isIndependentAccounting: "1",
          isAccounting: "1",
          isPurchase: "1",
          isService: "1",
          isLeaf: "1",
          clinicType: "1",
        }
        this.resetForm('form')
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1
        this.getList()
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm('queryForm')
        this.queryShowDeptName = ''
        this.handleQuery()
      },
      rowStyle({
        row,
        rowIndex
      }) {
        Object.defineProperty(row, 'rowIndex', {
          value: rowIndex,
        })
      },
      rowClick(row, flag = true) {
        let refsElTable = this.$refs.indexTable;
        if (flag) {
          let findRow = this.selectionRow.find(c => c.rowIndex == row.rowIndex);
          if (findRow && this.selectionRow.length == 1) {
            refsElTable.toggleRowSelection(row, false);
            return;
          }
        }
        refsElTable.clearSelection();
        refsElTable.toggleRowSelection(row);
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        let requirementTable = this.$refs.indexTable;
      if (selection.length > 1) {
        requirementTable.clearSelection();
        requirementTable.toggleRowSelection(
          selection[selection.length - 1],
          true
        );
        selection = Array(selection[selection.length - 1]);
      } else if (selection.length === 1) {
        selection = Array(selection[selection.length - 1]);
      } else {
        selection = [];
      }

      this.selectionRow=selection;


        this.ids = selection.map(item => item.deptRoomId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset()
        this.open = true
        this.title = '科室 / 新增'
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset()
        const deptRoomId = row.deptRoomId || this.ids
        getDeptRoom(deptRoomId).then(response => {
          if (response.code == 200) {
            this.form = response.data
            this.open = true
            this.title = '科室 / 修改'
          } else {
            this.msgError(response.msg);
          }
        })
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            if (this.form.deptRoomId != null) {
              updateDeptRoom(this.form).then(response => {
                if (response.code == 200) {
                  this.msgSuccess('修改成功')
                  this.open = false
                  this.getList()
                } else {
                  this.msgError(response.msg);
                }
              })
            } else {
              addDeptRoom(this.form).then(response => {
                if (response.code == 200) {
                  this.msgSuccess('新增成功')
                  this.open = false
                  this.getList()
                } else {
                  this.msgError(response.msg);
                }
              })
            }
          }
        })
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const deptRoomIds = row.deptRoomId || this.ids
        this.$confirm('是否确认删除科室编号为"' + deptRoomIds + '"的数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          return delDeptRoom(deptRoomIds)
        }).then(() => {
          this.getList()
          this.msgSuccess('删除成功')
        }).catch(() => {
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams
        this.$confirm('是否确认导出所有科室数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.exportLoading = true
          return exportDeptRoom(queryParams)
        }).then(response => {
          if (response.code == 200) {
            this.download(response.msg)
            this.exportLoading = false
          } else {
            this.msgError(response.msg);
          }
        }).catch(() => {
        })
      },
      // 获取左侧部门数据
      getTreeSelect(node, resolve) {
        let pid = ''
        if (node.level == 0) {
          pid = '0'
        } else {
          pid = node.data.id
        }
        setTimeout(() => {
          treeselect({ parentId: pid }).then(response => {
            if (response.code == 200) {
              if (resolve) {
                resolve(response.data)
              } else {
                this.deptOptions = response.data
              }
            } else {
              this.msgError(response.msg);
            }
          })
        }, 100)
      },
      // search筛选节点
      filterNode(value, data) {
        if (!value) return true
        return data.label.indexOf(value) !== -1
      },
      // search节点单击事件
      handleNodeClick(data) {
        this.queryParams.deptId = data.id
        this.queryShowDeptName = data.label
        //this.getList();
        this.deptSearchDialog = false
      },
      // form表单中search节点单击事件
      handleFormNodeClick(data) {
        this.form.deptId = data.id
        this.form.deptName = data.label
        this.deptSearchFormDialog = false
      }
    }
  }
</script>
<style lang="scss">
.tableCheckbox{
  .el-checkbox__label{
    display: none;
  }
}
</style>
