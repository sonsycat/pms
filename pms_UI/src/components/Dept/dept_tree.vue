<template>
  <el-tree
    :data="deptOptions"
    :props="defaultProps"
    :expand-on-click-node="false"
    ref="tree"
    :load="getDeptDatas"
    lazy
    :indent=6
    @node-click="handleNodeClick"
  />
</template>

<script>
  import Treeselect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'
  import { treeselect } from '@/api/system/dept'

  export default {
    name: 'deptTree',
    components: { Treeselect },
    data() {
      return {
        deptOptions: undefined,
        defaultProps: {
          children: 'children',
          label: 'label'
        },
      }
    },
    methods: {
      getDeptDatas(node, resolve) {
        var pId = ''
        if (node.level === 0) {
          pId = '0'
        } else {
          pId = node.data.id
        }
        setTimeout(() => {
          treeselect({ parentId: pId }).then(res => {
            if (resolve) {
              resolve(res.data)
            } else {
              this.deptOptions = res.data
            }
          })
        }, 100)
      },
      handleNodeClick(data){
        this.$emit('handleClose',data);
      }
    }
  }
</script>

<style scoped>

</style>
