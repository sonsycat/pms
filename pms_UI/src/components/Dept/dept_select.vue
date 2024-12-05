<template>
  <div>
    <el-input v-on:click.native="deptSearchDialog=true" placeholder="请选择单位" v-model="queryShowDeptName"
              style="width:250px;" disabled/>
    <el-input type="text" style="display:none" v-model="deptId"/>
    <el-dialog :visible.sync="deptSearchDialog" :close-on-click-modal="false" title="选择单位" append-to-body
               width="500px">
      <deptTree @handleClose="handleDialogNodeClick"></deptTree>
    </el-dialog>
  </div>
</template>

<script>
  import deptTree from './dept_tree'

  export default {
    name: 'deptSelect',
    components:{deptTree},
    data(){
      return{
        deptId:undefined,
        queryShowDeptName:'',
        deptSearchDialog: false
      }
    },
    methods:{
      handleDialogNodeClick(data) {
        this.deptId=data.id
        this.queryShowDeptName=data.label
        this.deptSearchDialog = false
      }
    }
  }
</script>

<style scoped>

</style>
