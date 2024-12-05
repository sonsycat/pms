<template>
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="比例编码" prop="ratioCode">
          <el-input
            class="search_w220"
            v-model="queryParams.ratioCode"
            placeholder="请输入比例编码"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="比例名称" prop="ratioName">
          <el-input
            class="search_w220"
            v-model="queryParams.ratioName"
            placeholder="请输入比例名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
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
            v-hasPermi="['apportionment:dictionary:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['apportionment:dictionary:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            :disabled="single"
            @click="submitCs"
            v-hasPermi="['apportionment:dictionary:edit']"
          >查看详情</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['apportionment:dictionary:remove']"
          >删除</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <el-table :height="tableHeight" border stripe v-loading="loading" :data="dictionaryList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="比例编码" align="center" prop="ratioCode" />
      <el-table-column sortable label="比例名称" align="left" prop="ratioName">
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" v-html="scope.row.ratioName" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column sortable label="比例类型" align="center" prop="ratioType" :formatter="ratioTypeFormat" />
      <el-table-column sortable label="备注" align="left" prop="remark" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>

    <el-dialog :title="title1" class="dialog" :visible.sync="openDialog1" width="50%" append-to-body :close-on-click-modal="false"  custom-class="el-button--primary"  @close="cancel1">
      <div>
        <el-row>
          <!-- 一级分摊方案 -->
          <el-tabs v-model="activeName1">
            <el-tab-pane label="一级分摊方案" name="sql1List">
              <el-form  ref="form" :model="form">
                <el-table border class="mt20" max-height="500px" v-loading="sql1Loading" :data="form.costApportionmentDictionaryDetailList1" ref="cardListRef" stripe>
                  <el-table-column label="比例编码" align="center" prop="ratioCode" show-overflow-tooltip/>
                  <el-table-column label="科室编码" align="center" prop="deptCode" show-overflow-tooltip/>
                  <el-table-column label="科室名称" align="left" prop="deptName" show-overflow-tooltip/>
                  <el-table-column label="使用人数" align="center" prop="num" show-overflow-tooltip/>
                  <el-table-column label="使用占比" align="center" prop="useRatio" show-overflow-tooltip/>
                  <el-table-column label="年月" align="center" prop="ny" show-overflow-tooltip/>
                </el-table>
              </el-form>
            </el-tab-pane>
            <!-- 二级分摊方案 -->
            <el-tab-pane label="二级分摊方案" name="sql2List">
              <el-form  ref="form" :model="form">
                <el-table border class="mt20" max-height="500px" v-loading="sql2Loading" :data="form.costApportionmentDictionaryDetailList2" ref="cardListRef" stripe>
                  <el-table-column label="比例编码" align="center" prop="ratioCode" show-overflow-tooltip/>
                  <el-table-column label="科室编码" align="center" prop="deptCode" show-overflow-tooltip/>
                  <el-table-column label="科室名称" align="left" prop="deptName" show-overflow-tooltip/>
                  <el-table-column label="使用人数" align="center" prop="num" show-overflow-tooltip/>
                  <el-table-column label="使用占比" align="center" prop="useRatio" show-overflow-tooltip/>
                  <el-table-column label="年月" align="center" prop="ny" show-overflow-tooltip/>
                </el-table>
              </el-form>
            </el-tab-pane>
            <!-- 三级分摊方案 -->
            <el-tab-pane label="三级分摊方案" name="sql3List">
              <el-form  ref="form" :model="form">
                <el-table border class="mt20" max-height="500px" v-loading="sql3Loading" :data="form.costApportionmentDictionaryDetailList3" ref="cardListRef" stripe>
                  <el-table-column label="比例编码" align="center" prop="ratioCode" show-overflow-tooltip/>
                  <el-table-column label="科室编码" align="center" prop="deptCode" show-overflow-tooltip/>
                  <el-table-column label="科室名称" align="left" prop="deptName" show-overflow-tooltip/>
                  <el-table-column label="使用人数" align="center" prop="num" show-overflow-tooltip/>
                  <el-table-column label="使用占比" align="center" prop="useRatio" show-overflow-tooltip/>
                  <el-table-column label="年月" align="center" prop="ny" show-overflow-tooltip/>
                </el-table>
              </el-form>
            </el-tab-pane>
          </el-tabs>
        </el-row>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel1" class="m20">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="title1" class="dialog" :visible.sync="openDialog2" width="50%" append-to-body :close-on-click-modal="false"  custom-class="el-button--primary"  @close="cancel2">
      <div>
        <el-row>
          <!-- 计算计入成本分摊方案 -->
          <el-tabs v-model="activeName2">
            <el-tab-pane label="计算计入成本分摊方案" name="sql4List">
              <el-form  ref="form" :model="form">
                <el-table border class="mt20" max-height="500px" v-loading="sql4Loading" :data="form.costApportionmentDictionaryDetailList4" ref="cardListRef" stripe>
                  <el-table-column label="比例编码" align="center" prop="ratioCode" show-overflow-tooltip/>
                  <el-table-column label="科室编码" align="center" prop="deptCode" show-overflow-tooltip/>
                  <el-table-column label="科室名称" align="left" prop="deptName" show-overflow-tooltip/>
                  <el-table-column label="使用人数" align="center" prop="num" show-overflow-tooltip/>
                  <el-table-column label="使用占比" align="center" prop="useRatio" show-overflow-tooltip/>
                  <el-table-column label="年月" align="center" prop="ny" show-overflow-tooltip/>
                </el-table>
              </el-form>
            </el-tab-pane>
          </el-tabs>
        </el-row>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel2" class="m20">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import { listDictionary,listDictionarys, getDictionary, delDictionary, addDictionary, updateDictionary, exportDictionary } from "@/api/apportionment/dictionary";
  import addEdit from "./addEdit";

  export default {
    name: "Dictionary",
    components: {
      addEdit
    },
    data() {
      return {
        // 遮罩层
        loading: true,
        tableHeight: "auto",
        // 导出遮罩层
        exportLoading: false,
        // 选中数组
        ids: [],
        ratioTypes: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 分摊比例字典管理表格数据
        dictionaryList: [],
        selectionRow: [],
        // 弹出层标题
        title: "",
        title1: "分摊比例字典管理/ 查看详情",
        activeName1: "sql1List",
        activeName2: "sql4List",
        sql1Loading: false,
        // 使用科室明细
        sql2Loading: false,
        // 资产文档
        sql3Loading: false,
        sql4Loading: false,
        // 是否显示弹出层
        open: false,
        openDialog1: false,
        openDialog2: false,
        // 比例类型字典
        ratioTypeOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 30,
          compCode: this.$store.state.user.userInfo.deptId,
          accountCode: null,
          ratioCode: null,
          ratioName: null,
          ratioType: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
        }
      };
    },
    created() {
      if(this.$route.query.id){
        this.handleUpdate(this.$route.query)
      }
      this.getList();
      this.getDicts("cost_scale_type").then(response => {
        this.ratioTypeOptions = response.data;
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
    watch: {
      listData(val) {
        var that = this;
        setTimeout(function() {
          that.resizeHandler();
        }, 300);
      }
    },
    methods: {
      handleClickTableName(data) {
        // 选中当前行
        this.rowClick(data, false);
        this.submitCs(data);
      },
      resizeHandler() {
        let a = this.$refs.search.offsetHeight;
        let b = this.$refs.btns.offsetHeight;
        let h = a + b + 18 + 45 + 84 + 40 + 20;
        h = h + "px";
        this.tableHeight = `calc(100vh - ${h})`;
      },
      /** 查询分摊比例字典管理列表 */
      getList() {
        this.loading = true;
        this.queryParams.ratioType ='2';
        listDictionary(this.queryParams).then(response => {
          this.dictionaryList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 比例类型字典翻译
      ratioTypeFormat(row, column) {
        return this.selectDictLabel(this.ratioTypeOptions, row.ratioType);
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
      cancel1() {
        this.openDialog1 = false;
      },
      cancel2() {
        this.openDialog2 = false;
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          compCode: this.$store.state.user.userInfo.deptId,
          accountCode: null,
          ratioCode: null,
          ratioName: null,
          ratioType: null,
          countSql: null,
          firstLevelSql: null,
          secondLevelSql: null,
          thirdLevelSql: null,
          delFlag: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          remark: null
        };
        this.resetForm("form");
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
        this.ratioTypes = selection.map(item => item.ratioType)
        this.single = selection.length!==1
        this.multiple = !selection.length
        this.selectionRow=selection
      },
      rowClick(row, column, event) {
        let refsElTable = this.$refs.multipleTable;
        let findRow = this.selectionRow.find(c => c.rowIndex == row.rowIndex);
        if (findRow && this.selectionRow.length == 1) {
          refsElTable.toggleRowSelection(row, false);
          return;
        }
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
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids[0];
        this.open = true;
        this.addEditId = id; // id赋值
        console.log("addEditId---" + addEdit);
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateDictionary(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addDictionary(this.form).then(response => {
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
        this.$confirm('是否确认删除分摊比例字典管理编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDictionary(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有分摊比例字典管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportDictionary(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
      },
      submitCs(row){
        const id = row.id || this.ids[0];
        getDictionary(id).then(response => {
          this.form = response.data;
        }).then(()=>{
          let saveLoading = this.saveLoading();
          let ratioType = row.ratioType || this.ratioTypes[0];
          if (ratioType == "1") {
            this.openDialog1 = true; // 弹框点开sql1
            saveLoading.close();
          } else if (ratioType == "2") {
            this.openDialog2 = true; // 弹框点开
            saveLoading.close();
          }
        })
      },

    }
  };
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    min-height: 20%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
