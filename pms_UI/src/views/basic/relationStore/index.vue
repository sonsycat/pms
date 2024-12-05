<template>
<!-- 仓库关系设置 -->
    <div class="app-container">
      <div ref="btns">
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                type="primary"
                icon="el-icon-box"
                size="mini"
                @click="handleAdd"
                :disabled="singleRelation"
                v-hasPermi="['basic:relationStore:add']"
              >保存</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                icon="el-icon-refresh"
                size="mini"
                @click="resetQuery">重置</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="primary"
                icon="el-icon-plus"
                size="mini"
                @click="handleAddRelation"
                v-hasPermi="['basic:relationStore:add']"
              >关系分类</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getListRelation"></right-toolbar>
          </el-row>
      </div>


      <el-row :gutter="15">
        <el-col :span="12" >
          <el-card>
          <div ref="search">
            <el-form :model="queryParamsRelation" ref="queryFormRelation" :inline="true" v-show="showSearchRelation" label-width="68px">
              <el-form-item label="" prop="queryCode" >
                <el-input
                  v-model="queryParamsRelation.queryCode"
                  placeholder="输入信息（关系、单位）"
                  clearable
                  size="small"
                  @input="handleQueryRelation"
                  @keyup.enter.native="handleQueryRelation"
                />
              </el-form-item>
            </el-form>
          </div>

          <el-table v-loading="loading" :height="tableHeight"  ref="relationListRef" :data="relationList" @selection-change="handleSelectionChangeRelation" stripe :default-sort="defaultSortRelation" @sort-change="handleSortChangeRelation"  highlight-current-row @row-click="handleRowClickRelation">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="对应关系编码" align="left" key="relaCode" prop="relaCode" />
            <el-table-column label="对应关系名称" align="left" key="relaName" prop="relaName" />
            <el-table-column label="单位" align="left" key="compName" prop="compName"/>
          </el-table>
          <pagination
            v-show="totalRelation>0"
            :total="totalRelation"
            :page.sync="queryParamsRelation.pageNum"
            :limit.sync="queryParamsRelation.pageSize"
            @pagination="getListRelation"
          />
        </el-card>
        </el-col>
        <el-col :span="12" >
          <el-col :span="24" class="mb10">
            <el-card>
            <div ref="ssearch">
             <el-form :model="queryParamsStoreRelation" ref="queryFormStoreRelation" :inline="true" v-show="showSearchStoreRelation" label-width="68px" row-class-name="tableRowClassName">
              <el-col :span="24">
                <el-form-item  label="已选仓库" prop="">
                </el-form-item>
              </el-col>
              <el-form-item  label="" prop="queryCode">
                <el-input
                  v-model="queryParamsStoreRelation.queryCode"
                  placeholder="输入信息（仓库、单位）"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQueryStoreRelation"
                  @input="handleQueryStoreRelation"
                />
              </el-form-item>
            </el-form>
            </div>
            <el-table v-loading="storeListRelationLoading"  :data="storeListRelation" :height="stableHeight" ref="storeListRelationRef"  @selection-change="handleSelectionChangeStoreRelation" @select="selectionStoreRelation" stripe :default-sort="defaultSort" @sort-change="handleTopSortChange"  highlight-current-row>
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="仓库编码" align="center" prop="storeCode" />
              <el-table-column label="仓库名称" align="left" prop="storeName" show-overflow-tooltip/>
              <el-table-column label="库房分类" align="center" prop="storeType" :formatter="storeTypeFormat" />
              <el-table-column label="单位" align="left" prop="compName" show-overflow-tooltip/>
            </el-table>
            <pagination
              v-show="totalStoreRelation>0"
              :total="totalStoreRelation"
              :page.sync="queryParamsStoreRelation.pageNum"
              :limit.sync="queryParamsStoreRelation.pageSize"
              @pagination="getListStoreRelation"
            />
            </el-card>
          </el-col>
          <el-col :span="24">
            <el-card>
            <el-form :model="queryParamsStore" ref="queryFormStore" :inline="true" v-show="showSearchStore" label-width="68px" row-class-name="tableRowClassName">
              <el-col :span="24">
                <el-form-item  label="待选仓库" prop="">
                </el-form-item>
              </el-col>
              <el-form-item  label="" prop="queryCode">
                <el-input
                  v-model="queryParamsStore.queryCode"
                  placeholder="输入信息（仓库、单位）"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQueryStore"
                  @input="handleQueryStore"
                />
              </el-form-item>
            </el-form>
            <el-table  v-loading="storeListLoading" :data="storeList" :height="stableHeight" ref="storeListRef"  @selection-change="handleSelectionChangeStore" @select="selectionStore" stripe>
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column label="仓库编码" align="left" prop="storeCode" />
              <el-table-column label="仓库名称" align="left" prop="storeName" show-overflow-tooltip/>
              <el-table-column label="库房分类" align="left" prop="storeType" :formatter="storeTypeFormat" />
              <el-table-column label="单位" align="left" prop="compName" show-overflow-tooltip/>
            </el-table>
            <pagination
              v-show="totalStore>0"
              :total="totalStore"
              :page.sync="queryParamsStore.pageNum"
              :limit.sync="queryParamsStore.pageSize"
              @pagination="getListStore"
            />
          </el-card>
          </el-col>
        </el-col>

      </el-row>
      <addEdit v-if="open" :open="open" :id="addEditId"  @cancel="cancel" @success="successSubmit"></addEdit>
    </div>
</template>

<script>
import { listRelationStore, addRelationStore } from "@/api/basic/relationStore";
import { listStoreRelation } from "@/api/basic/store";
import { listRelation } from "@/api/basic/relation";
import addEdit from "@/views/basic/relation/indexAdd";
import { truncate } from 'fs';

export default {
  name: "RelationStore",
  components: {
    addEdit // 新增修改
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      storeListRelationLoading:false,
      storeListLoading:false,
      // 导出遮罩层
      exportLoading: false,
      // 关系分类选中数组
      idsRelation: [],
      // 仓库编码
      storeCode: [],
      // 仓库名称
      storeName: [],
      // 仓库分类
      storeType: [],
      // 单位编码
      compCode: [],
      // 单位
      compName: [],
      //分类关系传参ID
      addEditId: "",
      // 选择关系分类编码
      relationCodePost: [],
      // 选择关联仓库列表
      storeCodeList: [],
      // 非单个禁用
      singleRelation: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 显示关系分类搜索条件
      showSearchRelation: true,
      // 显示未关联仓库搜索条件
      showSearchStore: true,
      // 显示用户关联仓库搜索条件
      showSearchStoreRelation: true,
      // 用户总条数
      totalRelation: 0,
      // 未关联仓库总条数
      totalStore: 0,
      // 用户关联仓库总条数
      totalStoreRelation: 0,
      // 用户仓库关联表格数据
      relationStoreList: [],
      // 关系分类数据
      relationList: [],
      // 用户未关联仓库数据
      storeList: [],
      // 用户关联仓库数据
      storeListRelation: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //关系分类查询参数
      queryParamsRelation: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        relaCode: null,
        relaName: null,
        compCode: null,
        queryCode: null
      },
      //用户未关联仓库查询参数
      queryParamsStore: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: null,
        storeCode: null,
        storeName: null,
        isRelation: null,
        relaCode: null,
        queryCode: null
      },
      //用户关联关系分类查询参数
      queryParamsStoreRelation: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: null,
        storeCode: null,
        storeName: null,
        isRelation: null,
        relaCode: null,
        queryCode: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      storeTypeOptions: [],
      defaultSortRelation: { prop: "createTime", order: "descending" },
      defaultSort: { prop: "createTime", order: "descending" },
      selectionRelationRow: [],
      tableHeight: "auto",
      stableHeight: "auto",
      selectionTopRow: []
    };
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
    },
    storeListRelation(val) {
      this.totalStoreRelation = val.length;
    },
    storeList(val) {
      this.totalStore = val.length;
    }
  },
  created() {
    this.getListRelation();
    this.getDicts("basic_store_type").then(response => {
      this.storeTypeOptions = response.data;
    });
  },
  mounted() {
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  methods: {
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + this.$store.state.settings.tableHeight + 24;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
      let c = this.$refs.ssearch.offsetHeight;
      let sh = b + c + this.$store.state.settings.tableHeight + 160;
      sh = sh + "px";
      this.stableHeight = `calc((100vh - ${sh})/2)`;
    },
    tableRowClassName({ row, rowIndex }) {
      row.row_index = rowIndex;
    },
    /** 查询仓库关系设置列表 */
    getList() {
      this.loading = true;
      listRelationStore(this.queryParams).then(response => {
        if (response.code == 200) {
          this.relationStoreList = response.rows;
          this.total = response.total;
        } else {
          this.msgError(response.msg);
        }
        this.loading = false;
      });
    },
    /** 查询关系分类列表 */
    getListRelation() {
      this.loading = true;
      listRelation(this.queryParamsRelation).then(response => {
        if (response.code == 200) {
          this.relationList = response.rows;
          this.totalRelation = response.total;
        } else {
          this.msgError(response.msg);
        }
        this.loading = false;
      });
    },
    /** 查询非关系分类关联仓库列表 */
    getListStore() {
      this.queryParamsStore.isRelation = "2";
      if (this.queryParamsStore.relaCode != null) {
        this.storeListLoading = true
        listStoreRelation(this.queryParamsStore).then(response => {
          if (response.code == 200) {
            this.storeList = response.rows;
            console.log(this.storeList, "store");
            this.totalStore = response.total;
            this.storeListLoading = false            

          } else {
            this.msgError(response.msg);
          }
        });
      }
    },
    /** 查询关系分类关联仓库列表 */
    getListStoreRelation() {
      this.queryParamsStoreRelation.isRelation = "1";
      if (this.queryParamsStoreRelation.relaCode != null) {
        this.storeListRelationLoading = true
        listStoreRelation(this.queryParamsStoreRelation).then(response => {
          if (response.code == 200) {
            this.storeListRelation = response.rows;
            console.log(this.storeListRelation, "storeRelation");
            this.totalStoreRelation = response.total;
            this.storeListRelationLoading = false            
          } else {
            this.msgError(response.msg);
          }
        });
      }
    },
    successSubmit(flag) {
      this.open = flag;
      this.getListRelation();
    },

    /** 新增关系分类按钮操作 */
    handleAddRelation() {
      this.open = true; // 弹框点开
      this.addEditId = ""; // addEditId 是新增  去子组件方便区分新增编辑
    },

    // 库房分类字典翻译
    storeTypeFormat(row, column) {
      return this.selectDictLabel(this.storeTypeOptions, row.storeType);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.getListRelation();
    },

    /** 关系分类搜索按钮操作 */
    handleQueryRelation() {
      this.queryParamsRelation.pageNum = 1;
      this.getListRelation();
    },
    /** 未关联仓库搜索按钮操作 */
    handleQueryStore() {
      this.queryParamsStore.pageNum = 1;
      this.getListStore();
    },
    /** 关联仓库搜索按钮操作 */
    handleQueryStoreRelation() {
      this.queryParamsStoreRelation.pageNum = 1;
      this.getListStoreRelation();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryFormRelation");
      this.resetForm("queryFormStore");
      this.resetForm("queryFormStoreRelation");
      this.handleQuery();
    },
    handleSortChangeRelation(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
    handleRowClickRelation(row) {
      let relationListRef = this.$refs.relationListRef;
      let findRow = this.selectionRelationRow.find(c => c.id == row.id);
      if (findRow) {
        relationListRef.toggleRowSelection(row, false);
        return;
      }
      relationListRef.clearSelection(); //清空列表的选中
      relationListRef.toggleRowSelection(row, true);
    },
    // 多选框选中数据
    handleSelectionChangeRelation(selection) {
      let relationListRef = this.$refs.relationListRef;
      if (selection.length > 1) {
        relationListRef.clearSelection(); //清空列表的选中
        relationListRef.toggleRowSelection(
          selection[selection.length - 1],
          true
        ); //只显示选中最后一个 这时selection还是多选的列表(就是你选中的几个数据)
        selection = Array(selection[selection.length - 1]);
      } else if (selection.length === 1) {
        selection = Array(selection[selection.length - 1]);
      } else {
        selection = [];
      }
      // 方便点击行取消选中
      this.selectionRelationRow = selection;
      this.idsRelation = selection.map(item => item.relaCode);
      this.singleRelation = selection.length !== 1;
      this.multiple = !selection.length;

      if (selection.length !== 1) {
        this.storeList = [];
      } else {
        this.queryParamsStore.relaCode = this.idsRelation[0];
        this.relationCodePost = this.idsRelation[0];
        this.getListStore();
      }

      if (selection.length !== 1) {
        this.storeListRelation = [];
      } else {
        this.queryParamsStoreRelation.relaCode = this.idsRelation[0];
        this.getListStoreRelation();
      }
    },
    // 仓库多选框选中数据
    handleSelectionChangeStore(selection) {
      /*this.idsStore = selection.map(item => item.storeCode)*/
    },

    // 仓库多选框选中数据
    selectionStore(selection, row) {
      let storeListRef = this.$refs.storeListRef;
      if (selection.length > 1) {
        storeListRef.clearSelection(); //清空列表的选中
        storeListRef.toggleRowSelection(selection[selection.length - 1], true); //只显示选中最后一个 这时selection还是多选的列表(就是你选中的几个数据)
        selection = Array(selection[selection.length - 1]);
      } else if (selection.length === 1) {
        selection = Array(selection[selection.length - 1]);
      } else {
        selection = [];
      }
      // 方便点击行取消选中
      this.selectionRow = selection;
      console.log(this.selectionRow, "this.selectionRow");

      if (selection.length == 1) {
        let {
          storeCode,
          storeName,
          storeType,
          compName,
          compCode
        } = selection[0];
        this.storeListRelation.push({
          storeCode,
          storeName,
          storeType,
          compName,
          compCode
        });
        this.storeList = this.storeList.filter(el => el.id != selection[0].id);
        // this.storeList.splice(row.row_index, 1);
      }
    },
    // 关联关系分类仓库多选框选中数据
    handleSelectionChangeStoreRelation(selection) {
      /* this.idsStoreRelation = selection.map(item => item.storeCode)*/
    },
    handleTopSortChange() {},
    // 关联用户仓库多选框选中数据
    selectionStoreRelation(selection, row) {
      let storeListRelationRef = this.$refs.storeListRelationRef;
      if (selection.length > 1) {
        storeListRelationRef.clearSelection(); //清空列表的选中
        storeListRelationRef.toggleRowSelection(
          selection[selection.length - 1],
          true
        ); //只显示选中最后一个 这时selection还是多选的列表(就是你选中的几个数据)
        selection = Array(selection[selection.length - 1]);
      } else if (selection.length === 1) {
        selection = Array(selection[selection.length - 1]);
      } else {
        selection = [];
      }

      if (selection.length == 1) {
        let {
          storeCode,
          storeName,
          storeType,
          compName,
          compCode
        } = selection[0];
        this.storeList.push({
          storeCode,
          storeName,
          storeType,
          compName,
          compCode
        });
        this.storeListRelation = this.storeListRelation.filter(
          el => el.storeCode != selection[0].storeCode
        );

        console.log(this.storeListRelation);
      }
    },
    /** 保存按钮操作 */
    handleAdd(row) {
      const ids = this.idsRelation[0];
      this.$confirm(
        '是否确认保存关系分类编码为"' + ids + '"的关联仓库数据?',
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      ).then(() => {
        let data = {
          relationCode: this.relationCodePost,
          assetsBasicStoreList: this.storeListRelation
        };
        addRelationStore(data).then(response => {
          if (response.code == 200) {
            this.getListRelation();
            this.msgSuccess("保存成功");
          } else {
            this.msgError(response.msg);
          }
        });
      });
    }
  }
};
</script>
