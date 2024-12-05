<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="50%"  custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="20">
            <el-col :span="24">
                <el-form-item label="指标集名称" prop="setName">
                    <el-input class="w80" v-model="form.setName" placeholder="请输入指标集名称" disabled />
                </el-form-item>
            </el-col>
          <el-col :span="12">
            <el-card>
                <el-col :span="12">
                   <span style="font-size:16px">待选角色</span>
                </el-col>
              <el-table
                style="margin-top:10px"   
                v-loading="indexListLoading"
                :data="roleList"
                height="500px" 
                :row-class-name="tableRowClassName"
                @select="selectionProject"
                @row-click="rowClickWaitClass"
              >
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="角色名称" align="left" prop="roleName" show-overflow-tooltip/>
              </el-table>
            </el-card>
          </el-col>
          <el-col :span="12" class="mb10">
            <el-card>
              <el-col :span="12">
                   <span style="font-size:16px">已选角色</span>
                </el-col>
              <el-table
                style="margin-top:10px"
                v-loading="roleSelectedListLoading"
                :data="roleSelectedList"
                height="500px"
                :row-class-name="tableRowClassName"
                @row-click="rowClickHasClass"
                @select="selectionProjectDept"
              >
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="角色名称" align="left" prop="roleName" show-overflow-tooltip/>
              </el-table>
            </el-card>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import {  getRoleSeledTree, subIndexRole } from "@/api/evaluate/set";
import {
  listRole,
} from "@/api/system/role";

export default {
  name: "addEdit",
  props: {
    open: {
      type: Boolean,
      default: false
    },
    datas:{
      type:Array,
      default:[]
    }
  },
  watch: {
    open: {
      handler(val) {
        this.openDialog = val;
      },
      deep: true
    },
  },
  components: {
  },
  data() {
    return {
      title: "指标集管理-指标权限",
      openDialog: this.open,
      roleSelectedListLoading:false,
      indexListLoading:false,
      // 表单参数
      form: {},
	  	// 表单校验
      rules: {
      },
      //待选角色
      queryParamsIndex: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: this.$store.state.user.userInfo.deptId,
        roleKey: null,
        roleName: null,
      },
      //已选角色
      queryParamsIndexSelected: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: this.$store.state.user.userInfo.deptId,
        roleKey: null,
        roleName: null,
        setCode:null,
      },
      // 待选角色数据
      roleList: [],
      // 已选角色数据
      roleSelectedList: [],
    };
  },
  created() {
    if(this.datas.length!==0){
      this.form.setName=this.datas[0].setName;
    }
    this.getSelectedRoleList()
    this.getList();

  },
  mounted() {
  },
  methods: {
    tableRowClassName({ row, rowIndex }) {
      row.index = rowIndex;
    },
    // 待选科室多选框选中数据
    selectionProject(selection, row) {
      this.rowClickWaitClass(selection[0])
    },
    //待选物资类型 点击行
    rowClickWaitClass(selection){
      let {
        roleKey,
        roleName,
        indexName
      } = selection;
      this.roleSelectedList.push({
        roleKey,
        roleName,
        indexName
      });
      this.roleList.splice(selection.index, 1);
    },
    // 已选科室多选框选中数据
    selectionProjectDept(selection, row) {
      this.rowClickHasClass(selection[0])
    },
    // 已经选择点击行
    rowClickHasClass(selection){
      let {
        roleKey,
        roleName,
        indexName
      } = selection;
      this.roleList.push({
        roleKey,
        roleName,
        indexName
      });
      this.roleSelectedList.splice(selection.index, 1);
    },
    /** 查询角色列表 */
    getList() {
      this.indexListLoading = true;
      listRole().then(
        response => {
          this.roleList = response.rows;
          for(let i in this.roleSelectedList){
              for(let k in this.roleList){
                  if(this.roleSelectedList[i].roleName == this.roleList[k].roleName){
                      this.roleList.splice(k,1)
                  }
              }
          }
          this.indexListLoading = false;
        }
      );
    },
    //已选角色列表
    getSelectedRoleList(){
        this.queryParamsIndexSelected.setCode=this.datas[0].setCode
      getRoleSeledTree(this.queryParamsIndexSelected).then(response=>{
        this.roleSelectedList=response.rows.map(item=>{
          item.roleName=item.indexN;
          item.roleKey=item.indexName;
          return item
        });
      })
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit("cancel", false);
    },
    // 取消
    cancel() {
      this.$emit("cancel", false);
    },
    /** 提交按钮 */
    submitForm() {
      let data={
        compCode:this.$store.state.user.userInfo.deptId,
        setCode:this.datas[0].setCode,
        listPms:this.roleSelectedList.map(item=>{
          item.indexName=item.roleKey;
          return item
        })
      };
      const saveLoading=this.saveLoading()
      subIndexRole(data).then(response=>{
          if(response.code==200){
              this.msgSuccess("设置成功");
              this.$emit("success", false);
              saveLoading.close()
          }
      })
    },
}};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    max-height: 86%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
