<template>
  <div class="app-container">
    <div ref="search">    
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="区域编码" prop="areaId">
        <el-input
          v-model="queryParams.areaId"
          placeholder="请输入区域编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="父编码" prop="parentId">
        <el-input
          v-model="queryParams.parentId"
          placeholder="请输入父编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="区域名称" prop="areaName">
        <el-input
          v-model="queryParams.areaName"
          placeholder="请输入区域名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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
          v-hasPermi="['system:area:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
   </div>
      <!--default-expand-all-->
    <el-table
      v-loading="loading"
      :height="tableHeight" 
      :data="areaList"
      row-key="areaId"
      lazy
      :load="load"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="区域编码" prop="areaId" />
      <el-table-column label="父编码" align="center" prop="parentId" />
      <el-table-column label="区域名称" align="left" prop="areaName" />
      <el-table-column label="区域信息" align="left" prop="areaInfo" />
      <el-table-column label="备注" align="left" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            title="修改"
            v-hasPermi="['system:area:edit']"
          ></el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:area:add']"
              title="新增"
          ></el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            title="删除"
            v-hasPermi="['system:area:remove']"
          ></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改区域信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body  custom-class="el-button--primary">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="区域编码" prop="areaId">
          <el-input v-model="form.areaId" placeholder="请输入区域编码" />
        </el-form-item>
        <el-form-item label="父编码" prop="parentId">
          <treeselect v-model="form.parentId" :options="areaOptions" :normalizer="normalizer" placeholder="请选择父编码" />
        </el-form-item>
        <el-form-item label="区域名称" prop="areaName">
          <el-input v-model="form.areaName" placeholder="请输入区域名称" />
        </el-form-item>
        <el-form-item label="区域信息" prop="areaInfo">
          <el-input v-model="form.areaInfo" placeholder="请输入区域信息" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listArea, getArea, delArea, addArea, updateArea, exportArea } from "@/api/system/area";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Area",
  components: {
    Treeselect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 区域信息表格数据
      areaList: [],
      // 区域信息树选项
      areaOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        areaId: null,
        parentId: "0",
        areaName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        areaId: [
          { required: true, message: "区域编码不能为空", trigger: "blur" }
        ],
        parentId: [
          { required: true, message: "父编码不能为空", trigger: "blur" }
        ],
        areaName: [
          { required: true, message: "区域名称不能为空", trigger: "blur" }
        ],
      },
      tableHeight: "auto",      
    };
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
  },
  created() {
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
    this.getList();
  },
  methods: {
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let h = a  + this.$store.state.settings.tableHeight;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    load(tree, treeNode, resolve){
      console.log(tree)
      listArea({
        parentId: tree.areaId}).then(response => {
        resolve(response.data)
        // console.log(response)
      })
    },
    /** 查询区域信息列表 */
    getList() {
      this.loading = true;
      listArea(this.queryParams).then(response => {
        this.areaList = this.handleTree(response.data, "areaId", "parentId");
        this.loading = false;
      });
    },
    /** 转换区域信息数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.areaId,
        label: node.areaName,
        children: node.children
      };
    },
	/** 查询区域信息下拉树结构 */
    getTreeselect() {
      listArea().then(response => {
        this.areaOptions = [];
        const data = { areaId: 0, areaName: '顶级节点', children: [] };
        data.children = this.handleTree(response.data, "areaId", "parentId");
        this.areaOptions.push(data);
      });
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
        areaId: null,
        parentId: null,
        areaName: null,
        areaInfo: null,
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
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.areaId) {
        this.form.parentId = row.areaId;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "区域信息 / 新增";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.parentId = row.areaId;
      }
      getArea(row.id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "区域信息 / 修改";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateArea(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addArea(this.form).then(response => {
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
      this.$confirm('是否确认删除区域信息编号为"' + row.id + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delArea(row.id);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    }
  }
};
</script>
