<template>
  <div class="deptSearch el-form-item" >
    <el-form-item :label="label" :prop="compName">
      <el-input :size="size" :readonly="readonly" :clearable="!readonly" @keyup.enter.native="handleQuery" v-on:click.native="handleClickInput" :placeholder="`请选择${label}`" v-model="deptParams[compName]" @change="handleChange" style="width:100%"/>
    </el-form-item>
    <el-form-item label="" :prop="compCode" style="display:none">
      <el-input type="text" v-model="deptParams[compCode]" @keyup.enter.native="handleQuery"/>
    </el-form-item>
    <el-dialog custom-class="el-button--primary" :visible.sync="deptSearchFormDialog" id="deptDialog" :close-on-click-modal="false" :title="`选择${label}`" append-to-body
               width="500px" >
      <div>
        <div class="head-container">
          <el-row>
            <el-col :span="16" class="pr5">
              <el-input
                v-model="deptNameForm"
                placeholder="请输入编码名称"
                clearable
                size="small"
                prefix-icon="el-icon-search"
                style="margin-bottom: 20px;"
              />
            </el-col>
            <el-col :span="8">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-refresh"
                @click="handleRefresh"
              >刷新</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-arrow-down"
                @click="handleExpand(true)"
              >展开</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-arrow-up"
                @click="handleExpand(false)"
              >折叠</el-button>
            </el-col>
          </el-row>


        </div>
        <div class="head-container">
          <el-tree
            lazy
            v-loading="loading"
            node-key="id"
            :data="deptOptions"
            :load="getDeptDatas"
            :props="defaultProps"
            :filter-node-method="filterNode"
            :default-expand-all="defaultExpand"
            ref="treeForm"
            @node-click="handleFormNodeClick"
          />
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { treeselect, treeRoom } from "@/api/until";
export default {
  name: "index",
  props: {
    queryParams: Object,
    label: {
      type: String,
      default: "单位"
    },
    size: {
      type: String,
      default: "mini"
    },
    readonly: {
      type: Boolean,
      default: false
    },
    compCode: {
      type: String,
      default: "compCode"
    },
    compName: {
      type: String,
      default: "compName"
    },
    // 需要清空类似 科室的值
    deptCode: {
      type: String,
      default: "deptCode"
    }
  },
  data() {
    return {
      //定义点击次数,默认0次
      treeClickCount: 0,
      deptParams: this.queryParams,
      deptSearchFormDialog: false,
      deptNameForm: undefined,
      deptOptions: undefined,
      defaultProps: {
        children: "children",
        label: "label"
      },
      deptList: [],
      open: false,
      loading: true,
      // 默认不展开所有节点
      defaultExpand: false
    };
  },

  mounted() {
    // this.getTreeSelect();
  },
  watch: {
    // 根据名称筛选部门树
    deptNameForm(val) {
      this.$refs.treeForm.filter(val);
    },
    queryParams: {
      handler(val) {
        this.deptParams = val;
      },
      deep: true,
      immediate: true
    },
    // form表单中search节点单击事件
    handleFormNodeClick(data) {
      if (this.compCode == "compCode") {
        this.queryParams.deptCode = null;
      }
      this.deptParams[this.compCode] = data.id;
      this.deptParams[this.compName] = data.label;
      this.deptSearchFormDialog = false;
      this.$emit("click",this.deptParams)
      this.$emit("input", this.deptParams);
    }
  },
  methods: {
    // 点击input
    handleClickInput() {
      if (!this.readonly) {
        this.deptSearchFormDialog = true;
      } else {
        // this.msgError('禁止编辑')
      }
    },
    getDeptDatas(node, resolve) {
      var pId = "";
      if (node.level === 0) {
        pId = "";
      } else {
        pId = node.data.id;
      }
      setTimeout(() => {
        this.loading = true;
        treeselect({ parentId: pId }).then(res => {
          if (resolve) {
            resolve(res.data);
          } else {
            this.deptOptions = res.data;
          }
          this.loading = false;
        });
      }, 100);
    },
    //获取单位树
    getTreeSelect() {
      this.loading = true;
      treeselect().then(response => {
        this.deptOptions = response.data;
        this.loading = false;
      });
    },
    // form表单中search节点单击事件
    handleFormNodeClick(data) {
      //记录点击次数
      this.treeClickCount++;
      //单次点击次数超过2次不作处理,直接返回,也可以拓展成多击事件
      if (this.treeClickCount >= 2) {
        return;
      }
      //计时器,计算300毫秒为单位,可自行修改
      this.timer = window.setTimeout(() => {
        if (this.treeClickCount == 1) {
          //把次数归零
          this.treeClickCount = 0;
          //单击事件处理
          //					console.log("单击事件");
        } else if (this.treeClickCount > 1) {
          //把次数归零
          this.treeClickCount = 0;
          //双击事件
          this.queryParams[this.deptCode] = null;
          this.deptParams[this.compCode] = data.id;
          this.deptParams[this.compName] = data.label;
          this.deptSearchFormDialog = false;
          this.$emit("input", this.deptParams);
          this.$emit("click",this.deptParams)
        }
      }, 300);
    },
    // search筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    handleChange(value) {
      if (!value) {
        this.deptParams[this.compCode] = "";
        this.$emit("input", this.deptParams);
      }
    },
    // 刷新树节点
    handleRefresh() {
      // this.getTreeSelect();
      this.getDeptDatas();
    },
    // 展开收缩树结点
    handleExpand(flag) {
      // 将没有转换成树的原数据
      let treeList = this.deptOptions;
      for (let i = 0; i < treeList.length; i++) {
        this.$refs.treeForm.store.nodesMap[treeList[i].id].expanded = flag;
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.deptSearch {
  margin-bottom: 0 !important;
}
// 单位弹框样式更改
#deptDialog {
  ::v-deep .el-dialog {
    height: 80%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 54px);
      overflow: hidden;
      > div {
        height: 100%;
        .head-container:last-of-type {
          height: calc(100% - 54px);
          overflow-y: auto;
        }
      }
    }
  }
}
</style>
