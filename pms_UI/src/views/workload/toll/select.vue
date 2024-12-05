<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body
             :close-on-click-modal="false" @close="handleDialogClose"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="收费项目:" prop="hisName">
            <el-input v-model="form.hisName" placeholder="请输入"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-card>
            <el-col :span="12">
              <span style="font-size:16px">待选收费项目</span>
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
              <el-table-column label="收费项目" align="left" prop="roleName" show-overflow-tooltip/>
            </el-table>
          </el-card>
        </el-col>
        <el-col :span="12" class="mb10">
          <el-card>
            <el-col :span="12">
              <span style="font-size:16px">已选收费项目</span>
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
              <el-table-column label="收费项目" align="left" prop="roleName" show-overflow-tooltip/>
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
import { listAll, save } from '@/api/workload/toll'

export default {
  name: 'select',
  props: {

    openSelect: {
      type: Boolean,
      default: false
    },
    projectCode: {
      type: String,
      default: null
    }
  },
  data() {
    return {
      title: '设置收费项目',
      openDialog: this.openSelect,
      roleSelectedListLoading: false,
      indexListLoading: false,
      // 表单参数
      form: { hisName: '' },
      // 表单校验
      rules: {},
      //待选角色
      queryParamsIndex: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: this.$store.state.user.userInfo.deptId,
        roleKey: null,
        roleName: null
      },
      //已选角色
      queryParamsIndexSelected: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: this.$store.state.user.userInfo.deptId,
        roleKey: null,
        roleName: null,
        setCode: null
      },
      // 待选角色数据
      roleList: [],
      // 已选角色数据
      roleSelectedList: []
    }
  },
  created() {
    this.getSelectedRoleList()
    this.getList(this.form.hisName)

  },
  mounted() {
  },
  methods: {
    resetQuery() {
      this.form.hisName = ''
      this.handleQuery()
    },
    handleQuery() {
      let exitHisCode = this.roleSelectedList.map(item => {
        return item.roleKey
      })
      this.getList(this.form.hisName, exitHisCode)
    },
    tableRowClassName({ row, rowIndex }) {
      row.index = rowIndex
    },
    // 待选科室多选框选中数据
    selectionProject(selection, row) {
      this.rowClickWaitClass(selection[0])
    },
    //待选物资类型 点击行
    rowClickWaitClass(selection) {
      let {
        roleKey,
        roleName,
        indexName
      } = selection
      this.roleSelectedList.push({
        roleKey,
        roleName,
        indexName
      })
      this.roleList.splice(selection.index, 1)
    },
    // 已选科室多选框选中数据
    selectionProjectDept(selection, row) {
      this.rowClickHasClass(selection[0])
    },
    // 已经选择点击行
    rowClickHasClass(selection) {
      let {
        roleKey,
        roleName,
        indexName
      } = selection
      this.roleList.push({
        roleKey,
        roleName,
        indexName
      })
      this.roleSelectedList.splice(selection.index, 1)
    },
    /** 查询角色列表 */
    getList(hisName, existCode) {
      listAll({ projectCode: this.projectCode, type: '2', hisName: hisName, existCode: existCode }).then(response => {
        console.log(response.data)
        this.roleList = response.data.map(item => {
          item.roleName = item.hisName
          item.roleKey = item.hisCode
          return item
        })
      })
    },
    //已选角色列表
    getSelectedRoleList(hisName) {
      listAll({ projectCode: this.projectCode, type: '1' }).then(response => {
        this.roleSelectedList = response.data.map(item => {
          item.roleName = item.hisName
          item.roleKey = item.hisCode
          return item
        })
      })
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit('cancelSelect', false)
    },
    // 取消
    cancel() {
      this.$emit('cancelSelect', false)
    },
    /** 提交按钮 */
    submitForm() {
      this.roleSelectedList.forEach(item => {
        item.hisCode = item.roleKey
        item.hisName = item.roleName
        return item
      })
      let data = {
        projectCode: this.projectCode,
        list: this.roleSelectedList
      }
      const saveLoading = this.saveLoading()
      save(data).then(response => {
        if (response.code === 200) {
          this.msgSuccess('设置成功')
          this.$emit('cancelSelect', false)
          saveLoading.close()
        }
      })
    }
  }
}
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
