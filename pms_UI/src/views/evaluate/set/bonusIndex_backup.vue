<template>
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
        <el-form-item label="科室类别" prop="deptTypeForBonuse" >
          <el-select class="w100" v-model="deptType" placeholder="请选择科室类别" clearable  >
            <el-option
              v-for="dict in deptTypeDict"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="指标集代码" prop="setCode">
          <el-input
            class="search_w220"
            v-model="queryParams.setCode"
            placeholder="请输入指标集代码"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="指标集名称" prop="setName">
          <el-input
            class="search_w220"
            v-model="queryParams.setName"
            placeholder="请输入指标集名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="适用对象" prop="orgType">
          <el-select class="search_w220" v-model="queryParams.orgType" placeholder="请选择适用对象" clearable>
            <el-option
              v-for="dict in orgTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="适用年份" prop="partYear">
          <el-select class="search_w220" v-model="queryParams.partYear" placeholder="请选择适用年份" clearable>
            <el-option
              v-for="dict in partYearOptions"
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
            v-hasPermi="['evaluate:set:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['evaluate:set:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['evaluate:set:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="setEvaSelect"
            :disabled="selectionRow.length!=1"
            v-hasPermi="['evaluate:set:edit']"
          >指标选择</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="setEvaPremission"
            :disabled="selectionRow.length!=1"
            v-hasPermi="['evaluate:set:edit']"
          >指标权限</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-document-copy"
            size="mini"
            @click="copyEva"
            :disabled="selectionRow.length!=1"
            v-hasPermi="['evaluate:set:edit']"
          >复制</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <el-table border stripe v-loading="loading" :height="tableHeight" :data="setList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="指标集名称" align="left" prop="setName">
        <template slot-scope="scope">
          <el-link type="primary" :underline="false" v-html="scope.row.setName" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column sortable label="指标集代码" align="center" prop="setCode" />
      <el-table-column sortable label="适用对象" align="center" prop="orgType" :formatter="orgTypeFormat" />
      <el-table-column sortable label="适用年份" align="center" prop="partYear" :formatter="partYearFormat" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>
    <eva-premission v-if="openEvaPremission" :open="openEvaPremission" @cancel="cancelEvaPre" :datas="selectionRow" @success="subSelEvaPre"></eva-premission>
    <evaSelect v-if="openEvaSelectDialog" :open="openEvaSelectDialog" @cancel="cancelEvaSel" :datas="selectionRow" @success="subSelEva"></evaSelect>

    <el-dialog title="指标集管理-复制" v-if="openCopyEva" class="copyEvaDialog" ref="copyEvaDialog" :visible.sync="openCopyEva" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="copyCancel">
      <el-form ref="copyForm" :model="copyForm" :rules="rules">
        <el-row>
          <el-col :span="24">
            <el-form-item label="指标集名称" prop="setName">
              <el-input class="w90" v-model="copyForm.setName" placeholder="请输入指标集名称"  maxlength="100" show-word-limit  />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitcopyEvaForm">保 存</el-button>
        <el-button @click="copyCancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import { listBonus, getSet, delSet, addBonus, updateSet, exportSet, indexCopy } from "@/api/evaluate/set";
  import addEdit from "./addBonus";
  import evaPremission from "./evaPremission";
  import evaSelect from "./bonusEvaSelect"

  export default {
    name: "Set",
    components: {
      addEdit,
      evaPremission,
      evaSelect
    },
    data() {
      return {
        tableHeight:'auto',
        // 遮罩层
        loading: true,
        // 导出遮罩层
        exportLoading: false,
        // 选中数组
        ids: [],
        addEditId:'',
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 指标集管理表格数据
        setList: [],
        selectionRow: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 组织类别(适用对象)字典
        orgTypeOptions: [],
        // 适用年份字典
        partYearOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 30,
          compCode:this.$store.state.user.userInfo.deptId,
          accountCode: null,
          setCode: null,
          setName: null,
          oneCode: null,
          secondCode: null,
          deptType: null,
          orgType: null,
          partYear: null,
          deptTypeForBonuse: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          setName:[
            {required:true,message:'指标集名称不能为空',trigger:'blur'}
          ]
        },
        openCopyEva:false,
        copyForm:{},
        openEvaSelectDialog:false,
        openEvaPremission:false,
        dictType: null,
        deptTypeDict:[],
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
        return this.$store.state.app.sidebar.opened;
      }
    },
    created() {
      this.getList();
      //组织分类字典
      this.getDicts("pms_zb_zz").then(response => {
        this.orgTypeOptions = response.data;
      });
      //适用年份字典
      this.getDicts("pms_zbj_nf").then(response => {
        this.partYearOptions = response.data;
      });
      this.$nextTick(() => {
        this.resizeHandler();
        window.addEventListener("resize", this.resizeHandler);
      });
      this.getDicts("JXGL_JJ_KSLB").then(response=>{
        this.deptTypeDict = response.data;
        this.deptType = this.deptTypeDict[0].dictValue;
      });
    },
    methods: {
      //超链接
      handleClickTableName(data) {
        // 选中当前行
        this.rowClick(data, false);
        this.handleUpdate(data);
      },
      resizeHandler() {
        let a = this.$refs.search.offsetHeight;
        let b = this.$refs.btns.offsetHeight;
        let h = a + b + 18 + 45 + 84 + 40 + 20;
        h = h + "px";
        this.tableHeight = `calc(100vh - ${h})`;
      },
      /** 查询指标集管理列表 */
      getList() {
        this.loading = true;
        listBonus(this.queryParams).then(response => {
          this.setList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 组织类别(适用对象)字典翻译
      orgTypeFormat(row, column) {
        return this.selectDictLabel(this.orgTypeOptions, row.orgType);
      },
      // 适用年份字典翻译
      partYearFormat(row, column) {
        return this.selectDictLabel(this.partYearOptions, row.partYear);
      },
      successSubmit(flag) {
        this.open = flag;
        this.getList();
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          compCode: null,
          accountCode: null,
          setCode: null,
          setName: null,
          oneCode: null,
          secondCode: null,
          deptType: null,
          orgType: null,
          partYear: null,
          delFlag: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          remark: null
        };
        this.resetForm("form");
      },
      resetCopy(){
        this.copyForm={
          setName: null,
        }
        this.resetForm("copyForm");
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
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length!==1
        this.multiple = !selection.length
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
        this.open = true; // 弹框点开
        this.addEditId = ""; // addEditId 是新增  去子组件方便区分新增编辑
      },
      //编辑按钮操作
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids[0];
        this.open = true;
        this.addEditId = id; // id赋值
        console.log("addEditId---" + addEdit);
      },
      //指标权限按钮操作
      setEvaPremission(){
        this.openEvaPremission=true;
      },
      cancelEvaPre(){
        this.openEvaPremission=false;
      },
      subSelEvaPre(flag){
        this.openEvaPremission=flag;
        this.getList()
      },
      //指标选择按钮操作
      setEvaSelect(){
        this.openEvaSelectDialog=true;
      },
      cancelEvaSel(){
        this.openEvaSelectDialog=false;
      },
      subSelEva(flag){
        this.openEvaSelectDialog=flag;
        this.getList()
      },
      //复制按钮操作
      copyEva(){
        this.openCopyEva=true;
        this.resetCopy();
      },
      copyCancel(){
        this.resetCopy();
        this.openCopyEva=false;
      },
      subCopy(flag){
        this.resetCopy();
        this.openCopyEva=flag;
        this.getList();
      },
      //复制指标集提交按钮
      submitcopyEvaForm(){
        this.$refs["copyForm"].validate(valid => {
          if(valid){
            let data={
              compCode:this.$store.state.user.userInfo.deptId,
              setCode:this.selectionRow[0].setCode,
              setName:this.copyForm.setName
            }
            const saveLoading=this.saveLoading()
            indexCopy(data).then(response=>{
              if(response.code==200){
                this.msgSuccess("复制成功");
                this.subCopy(false);
                saveLoading.close()
              }
            })
          }
        })
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateSet(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addBonus(this.form).then(response => {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除指标集管理编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delSet(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
      },

    }
  };
</script>
