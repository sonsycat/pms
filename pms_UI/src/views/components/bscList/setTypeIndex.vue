<template>
  <!-- 物资分类 -->
  <el-form-item :label="label" :prop="forItemProp">
    <treeselect
      :placeholder="placeholder"
      no-options-text="暂无数据"
      v-model="form[forItemProp]"
      :normalizer="normalizer"
      :show-count="true"
      :options="bscOptions"
      @select="handleTreeSelect"
      :disable-branch-nodes="disableBranch"
      :disabled = "disabled"
    />
  </el-form-item>
</template>

<script>
  import { spdMclist } from "@/api/settype/settype";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  export default {
    name: "index",
    components: { Treeselect },
    props: {
      label: {
        type: String,
        default: "指标集分类"
      },
      forItemProp: {
        type: String,
        default: "bscCode"
      },
      placeholder: {
        type: String,
        default:"请选择指标集分类"
      },
      form: {
        type: [Object, Array]
      },
      disabled: {
        type: Boolean,
        default: false
      },
      multiple: {
        type: Boolean,
        default: false
      },
      size: {
        type: String,
        default: "mini"
      },
      // 判断是否是用户关联的仓库接口
      userStore: {
        type: Boolean,
        default: false
      },
      refresh: {
        type: Boolean,
        default: false
      },
      disableBranch:{
        type: Boolean,
        default: true
      }
    },
    data() {
      return {
        bscOptions: []
      };
    },
    watch:{
      //监听是否需要刷新树结构数据
      refresh:{
        handler(){
          this.getData()
        },
        immediate:true
      }
    },
    mounted() {
      this.getData()
    },

    methods: {
      normalizer(node) {
        return {
          id: node.bscCode,
          label: node.bscName,
          children: node.children
        };
      },
      // 搜索数据
      getData() {
        // let data = Object.assign(
        //   // {
        //   //   queryParam:this.queryParams[this.setProp]
        //   // }
        // );
        spdMclist().then(response => {
          this.bscOptions = this.handleTree(
            response.data,
            "bscCode",
            "parentCode"
          );
        });
      },
      handleTreeSelect(val) {
        this.$emit("selectTree", val);
      }
    }
  };
</script>

<style scoped>
  ::v-deep .vue-treeselect__value-container,
  ::v-deep .vue-treeselect__control {
    height: 27px;
    line-height: 26px;
  }
  ::v-deep .vue-treeselect__placeholder,
  ::v-deep .vue-treeselect__single-value {
    line-height: 26px;
  }
  ::v-deep .vue-treeselect__input-container {
    font-size: 13px;
  }
</style>
