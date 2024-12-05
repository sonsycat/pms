<template>
  <div style="margin-left: 20px;margin-top: 20px;margin-right: 20px">
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="1">预览</el-menu-item>
      <el-menu-item index="2">查看源代码</el-menu-item>
    </el-menu>

    <div v-show="activeIndex==1">
      <div class="app-container" ref="preview">
        <el-row :gutter="20">
          <!--部门数据-->
          <el-col :span="6" :xs="24">
            <h3>search机构树</h3>
            <div class="head-container">
              <el-input
                v-model="deptName"
                placeholder="请输入部门名称"
                clearable
                size="small"
                prefix-icon="el-icon-search"
                style="margin-bottom: 20px"
              />
            </div>
            <div style="height: 600px;overflow-y: scroll">
              <el-tree
                :data="deptOptions"
                :props="defaultProps"
                :expand-on-click-node="false"
                :filter-node-method="filterNode"
                ref="tree"
                :load="getTreeSelect"
                lazy
                @node-click="handleNodeClick"
              />
            </div>
          </el-col>
          <el-col :span="18" :xs="24">
            <h3>下拉机构树</h3>
            <div class="head-container">
              <treeSelect v-model="orgCode" :options="orgOptions" placeholder="请选择机构"
                          :load-options="loadDepts"
                          style="width:500px;"/>
            </div>
            <div class="head-container">
              <h3>弹出机构树</h3>
              <el-input v-on:click.native="deptSearchDialog=true" placeholder="请选择机构" v-model="queryShowDeptName"
                        style="width:250px;" disabled/>
              <el-input type="text" style="display:none" v-model="deptId"/>
              <!-- 所属机构search-dialog -->
              <el-theme-dialog :visible.sync="deptSearchDialog" :close-on-click-modal="false" title="选择机构" append-to-body
                         width="500px">
                <div style="height: 400px;overflow-y: scroll">
                  <div class="head-container">
                    <el-input
                      v-model="deptNameDialog"
                      placeholder="请输入机构名称"
                      clearable
                      size="small"
                      prefix-icon="el-icon-search"
                      style="margin-bottom: 20px"
                    />
                  </div>
                  <div class="head-container">
                    <el-tree
                      :data="deptOptions"
                      :props="defaultProps"
                      :expand-on-click-node="false"
                      :filter-node-method="filterNode"
                      ref="treeDialog"
                      :load="getTreeSelect"
                      lazy
                      @node-click="handleDialogNodeClick"
                    />
                  </div>
                </div>
              </el-theme-dialog>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <div v-show="activeIndex==2">
      <pre style="background-color: #dadada">{{code}}</pre>
    </div>
  </div>
</template>

<script>
  import { treeselect } from '@/api/system/dept'
  import TreeSelect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'
  import { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect'

  export default {
    name: 'TreeSelectDemo',
    components: { TreeSelect },
    data() {
      return {
        // 导航栏切换index
        activeIndex: '1',
        // 查看源代码code
        code: '',
        // 部门名称
        deptName: undefined,
        // 部门树选项
        deptOptions: undefined,
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        // 下拉树code
        orgCode: null,
        // 部门树选项
        orgOptions: [],
        // 机构id
        deptId: null,
        // 用于显示查询的机构名称
        queryShowDeptName: '',
        // 表格上方机构查询dialog
        deptSearchDialog: false,
        // 部门名称
        deptNameDialog: undefined
      }
    },
    watch: {
      // 根据名称筛选部门树
      deptName(val) {
        this.$refs.tree.filter(val)
      },
      // 根据名称筛选部门树
      deptNameDialog(val) {
        this.$refs.treeDialog.filter(val)
      }
    },
    created() {
      // 初始化源代码
      this.initCode()
      // 初始化弹窗内部门数据
      this.getDepts()
    },
    methods: {
      // 获取左侧部门数据
      getTreeSelect(node, resolve) {
        let pid = ''
        if (node.level == 0) {
          pid = ''
        } else {
          pid = node.data.id
        }
        setTimeout(() => {
          treeselect({ parentId: pid }).then(response => {
            if (resolve) {
              resolve(response.data)
            } else {
              this.deptOptions = response.data
            }
          })
        }, 100)
      },
      // 初始化弹窗内部门数据
      getDepts() {
        treeselect({ parentId: "0" }).then(res => {
          this.orgOptions = res.data.map(function(obj) {
            if (obj.hasChildren) {
              obj.children = null
            }
            return obj
          })
        })
      },
      // 获取弹窗内部门数据
      loadDepts({ action, parentNode, callback }) {
        if (action === LOAD_CHILDREN_OPTIONS) {
          treeselect({ parentId: parentNode.id }).then(res => {
            parentNode.children = res.data.map(function(obj) {
              if (obj.hasChildren) {
                obj.children = null
              }
              return obj
            })
            setTimeout(() => {
              callback()
            }, 200)
          })
        }
      },
      // search筛选节点
      filterNode(value, data) {
        if (!value) return true
        return data.label.indexOf(value) !== -1
      },
      // search节点单击事件
      handleNodeClick(data) {
        this.$message({
          showClose: true,
          message: 'search树节点点击事件'
        })
      },
      // dialog中search节点单击事件
      handleDialogNodeClick(data) {
        this.deptId = data.id
        this.queryShowDeptName = data.label
        this.deptSearchDialog = false
      },
      // 导航切换
      handleSelect(key, keyPath) {
        this.activeIndex = key
      },
      // 初始化源代码
      initCode() {
        this.code =
          '<template>\n' +
          '  <div style="margin-left: 20px;margin-top: 20px;margin-right: 20px">\n' +
          '    <div class="app-container" ref="preview">\n' +
          '      <el-row :gutter="20">\n' +
          '        <!--部门数据-->\n' +
          '        <el-col :span="6" :xs="24">\n' +
          '          <h3>search机构树</h3>\n' +
          '          <div class="head-container">\n' +
          '            <el-input\n' +
          '              v-model="deptName"\n' +
          '              placeholder="请输入部门名称"\n' +
          '              clearable\n' +
          '              size="small"\n' +
          '              prefix-icon="el-icon-search"\n' +
          '              style="margin-bottom: 20px"\n' +
          '            />\n' +
          '          </div>\n' +
          '          <div style="height: 600px;overflow-y: scroll">\n' +
          '            <el-tree\n' +
          '              :data="deptOptions"\n' +
          '              :props="defaultProps"\n' +
          '              :expand-on-click-node="false"\n' +
          '              :filter-node-method="filterNode"\n' +
          '              ref="tree"\n' +
          '              :load="getTreeSelect"\n' +
          '              lazy\n' +
          '              @node-click="handleNodeClick"\n' +
          '            />\n' +
          '          </div>\n' +
          '        </el-col>\n' +
          '        <el-col :span="18" :xs="24">\n' +
          '          <h3>下拉机构树</h3>\n' +
          '          <div class="head-container">\n' +
          '            <treeSelect v-model="orgCode" :options="orgOptions" placeholder="请选择机构"\n' +
          '                        :load-options="loadDepts"\n' +
          '                        style="width:500px;"/>\n' +
          '          </div>\n' +
          '          <div class="head-container">\n' +
          '            <h3>弹出机构树</h3>\n' +
          '            <el-input v-on:click.native="deptSearchDialog=true" placeholder="请选择机构" v-model="queryShowDeptName"\n' +
          '                      style="width:250px;" disabled/>\n' +
          '            <el-input type="text" style="display:none" v-model="deptId"/>\n' +
          '            <!-- 所属机构search-dialog -->\n' +
          '            <el-theme-dialog :visible.sync="deptSearchDialog" :close-on-click-modal="false" title="选择机构" append-to-body\n' +
          '                       width="500px">\n' +
          '              <div style="height: 400px;overflow-y: scroll">\n' +
          '                <div class="head-container">\n' +
          '                  <el-input\n' +
          '                    v-model="deptNameDialog"\n' +
          '                    placeholder="请输入机构名称"\n' +
          '                    clearable\n' +
          '                    size="small"\n' +
          '                    prefix-icon="el-icon-search"\n' +
          '                    style="margin-bottom: 20px"\n' +
          '                  />\n' +
          '                </div>\n' +
          '                <div class="head-container">\n' +
          '                  <el-tree\n' +
          '                    :data="deptOptions"\n' +
          '                    :props="defaultProps"\n' +
          '                    :expand-on-click-node="false"\n' +
          '                    :filter-node-method="filterNode"\n' +
          '                    ref="treeDialog"\n' +
          '                    :load="getTreeSelect"\n' +
          '                    lazy\n' +
          '                    @node-click="handleDialogNodeClick"\n' +
          '                  />\n' +
          '                </div>\n' +
          '              </div>\n' +
          '            </el-theme-dialog>\n' +
          '          </div>\n' +
          '        </el-col>\n' +
          '      </el-row>\n' +
          '    </div>\n' +
          '  </div>\n' +
          '</template>\n' +
          '\n' +
          '<script>\n' +
          '  import { treeselect } from \'@/api/system/dept\'\n' +
          '  import TreeSelect from \'@riophae/vue-treeselect\'\n' +
          '  import \'@riophae/vue-treeselect/dist/vue-treeselect.css\'\n' +
          '  import { LOAD_CHILDREN_OPTIONS } from \'@riophae/vue-treeselect\'\n' +
          '\n' +
          '  export default {\n' +
          '    name: \'TreeSelectDemo\',\n' +
          '    components: { TreeSelect },\n' +
          '    data() {\n' +
          '      return {\n' +
          '        // 部门名称\n' +
          '        deptName: undefined,\n' +
          '        // 部门树选项\n' +
          '        deptOptions: undefined,\n' +
          '        defaultProps: {\n' +
          '          children: \'children\',\n' +
          '          label: \'label\'\n' +
          '        },\n' +
          '        // 下拉树code\n' +
          '        orgCode: null,\n' +
          '        // 部门树选项\n' +
          '        orgOptions: [],\n' +
          '        // 机构id\n' +
          '        deptId: null,\n' +
          '        // 用于显示查询的机构名称\n' +
          '        queryShowDeptName: \'\',\n' +
          '        // 表格上方机构查询dialog\n' +
          '        deptSearchDialog: false,\n' +
          '        // 部门名称\n' +
          '        deptNameDialog: undefined\n' +
          '      }\n' +
          '    },\n' +
          '    watch: {\n' +
          '      // 根据名称筛选部门树\n' +
          '      deptName(val) {\n' +
          '        this.$refs.tree.filter(val)\n' +
          '      },\n' +
          '      // 根据名称筛选部门树\n' +
          '      deptNameDialog(val) {\n' +
          '        this.$refs.treeDialog.filter(val)\n' +
          '      }\n' +
          '    },\n' +
          '    created() {\n' +
          '      // 初始化弹窗内部门数据\n' +
          '      this.getDepts()\n' +
          '    },\n' +
          '    methods: {\n' +
          '      // 获取左侧部门数据\n' +
          '      getTreeSelect(node, resolve) {\n' +
          '        let pid = \'\'\n' +
          '        if (node.level == 0) {\n' +
          '          pid = \'\'\n' +
          '        } else {\n' +
          '          pid = node.data.id\n' +
          '        }\n' +
          '        setTimeout(() => {\n' +
          '          treeselect({ parentId: pid }).then(response => {\n' +
          '            if (resolve) {\n' +
          '              resolve(response.data)\n' +
          '            } else {\n' +
          '              this.deptOptions = response.data\n' +
          '            }\n' +
          '          })\n' +
          '        }, 100)\n' +
          '      },\n' +
          '      // 初始化弹窗内部门数据\n' +
          '      getDepts() {\n' +
          '        treeselect({ parentId: "0" }).then(res => {\n' +
          '          this.orgOptions = res.data.map(function(obj) {\n' +
          '            if (obj.hasChildren) {\n' +
          '              obj.children = null\n' +
          '            }\n' +
          '            return obj\n' +
          '          })\n' +
          '        })\n' +
          '      },\n' +
          '      // 获取弹窗内部门数据\n' +
          '      loadDepts({ action, parentNode, callback }) {\n' +
          '        if (action === LOAD_CHILDREN_OPTIONS) {\n' +
          '          treeselect({ parentId: parentNode.id }).then(res => {\n' +
          '            parentNode.children = res.data.map(function(obj) {\n' +
          '              if (obj.hasChildren) {\n' +
          '                obj.children = null\n' +
          '              }\n' +
          '              return obj\n' +
          '            })\n' +
          '            setTimeout(() => {\n' +
          '              callback()\n' +
          '            }, 200)\n' +
          '          })\n' +
          '        }\n' +
          '      },\n' +
          '      // search筛选节点\n' +
          '      filterNode(value, data) {\n' +
          '        if (!value) return true\n' +
          '        return data.label.indexOf(value) !== -1\n' +
          '      },\n' +
          '      // search节点单击事件\n' +
          '      handleNodeClick(data) {\n' +
          '        this.$message({\n' +
          '          showClose: true,\n' +
          '          message: \'search树节点点击事件\'\n' +
          '        })\n' +
          '      },\n' +
          '      // dialog中search节点单击事件\n' +
          '      handleDialogNodeClick(data) {\n' +
          '        this.deptId = data.id\n' +
          '        this.queryShowDeptName = data.label\n' +
          '        this.deptSearchDialog = false\n' +
          '      }\n' +
          '    }\n' +
          '  }\n' +
          '<\/script>\n'
      }
    }
  }
</script>
