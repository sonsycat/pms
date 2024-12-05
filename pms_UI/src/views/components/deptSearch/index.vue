<template>
  <div class="deptSearch el-form-item" >
  		<el-form-item :label="label" :prop="deptName">
       	<el-input :size="size" :readonly="readonly" :clearable="clearable" @keyup.enter.native="handleQuery" v-on:click.native="handleClickInput" :placeholder="`请选择${label}`" v-model="deptParams[deptName]" @change="handleChange" style="width:100%"/>
      </el-form-item>
      <el-form-item label="" :prop="deptId" style="display:none">
	      <el-input type="text" v-model="deptParams[deptId]" @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-dialog custom-class="el-button--primary" :visible.sync="deptSearchFormDialog" id="deptDialog" :close-on-click-modal="false" :title="`选择${label}`" append-to-body
               width="500px" height="80%">
        <div>
          <div class="head-container">
            <el-row>
              <el-col :span="16" class="pr5">
                <el-input
                	@input = "deptNameForm2"
                    v-model="deptNameForm"
                    placeholder="请输入机构名称"
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
            	:lazy="lazy"
              node-key="id"
              v-loading="loading"
              :data="deptOptions"
              :load="getDeptDatas"
              :props="defaultProps"
              :filter-node-method="filterNode"
              :default-expand-all="defaultExpand"
              ref="treeForm"
              :default-expanded-keys="defaultShowNodes"
              @node-click="handleFormNodeClick"
            />
          </div>
        </div>
    </el-dialog>
  </div>
</template>

<script>
import { treeselect,treeselect2,treeselect3 } from '@/api/system/dept'
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
    clearable:{
      type: Boolean,
      default: true
    },
    readonly: {
      type: Boolean,
      default: false
    },
    deptId: {
      type: String,
      default: "deptId"
    },
    deptName: {
      type: String,
      default: "deptName"
    },
    // 需要清空类似 科室的值
    deptCode: {
      type: String,
      default: "deptCode"
    }
  },
  data() {
    return {
    	lazy:false,
      //定义点击次数,默认0次
      treeClickCount: 0,
      deptParams: this.queryParams,
      deptSearchFormDialog: false,
      deptNameForm: undefined,
      deptOptions: undefined,
      defaultShowNodes: [], 
      defaultProps: {
        children: "children",
        label: "label"
      },
      open: false,
      loading: false,
      // 默认展开所有节点
      defaultExpand: true
    };
  },
  mounted() {
       this.getTreeSelect();
  },
  watch: {
//	deptOptions: {
//			handler () {
//				this.deptOptions.forEach(item => {
//					if(item.children){
//						item.children.forEach(item2 => {
//							this.defaultShowNodes.push(item2.id)
//						})
//					}
//				})
//			},
////			deep: true,
////			immediate: true
//		},
    // 根据名称筛选部门树
//  deptNameForm(val) {
//  	treeselect({deptName:val}).then(res => {
//  		console.log(res);
//				let ress = res.data;
//				this.deptOptions = ress;
////	      this.append(data,ress);
//	    });
////  	console.log(val);
////    this.$refs.treeForm.filter(val);
//  },
    queryParams: {
      handler(val) {
        this.deptParams = val;
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
  	deptNameForm2() {
  		console.log(this.deptNameForm);
  		this.loading = true;
    	treeselect3({deptName:this.deptNameForm}).then(res => {
    		console.log(res);
				let ress = res.data;
				this.deptOptions = ress;
				this.loading = false;
	    });
    },
    // 点击input
    handleClickInput() {
      if (!this.readonly) {
        this.deptSearchFormDialog = true;
      } else {
        // this.msgError('禁止编辑')
      }
    },
    getDeptDatas(node,resolve) {
      var pId = "";
      if (node.level === 0) {
        pId = "";
      } else {
        pId = node.data.id;
      }
      setTimeout(() => {
          this.loading = true;
        treeselect({ parentId: pId }).then(res => {
        	console.log(res);
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
      treeselect2().then(response => {
      	console.log(response);
        this.deptOptions = response.data;
        this.loading = false;
      });
    },
	append(data,res) {
		if(data.children){
			data.children = [];
		}
     	for(let i=0;i<res.length;i++){
     		let newChild = { id: res[i].id, label:res[i].label, children: [] }
	      	if (!data.children) {
	          this.$set(data,'children',[]);
	       	}
        	data.children.push(newChild);  
     	}
  	},
    // form表单中search节点单击事件
    handleFormNodeClick(data) {
    	if(data.children){
    	}else{
    		delete data.children;
    	}
		treeselect({ parentId: data.id }).then(res => {
			let ress = res.data;
	      	this.append(data,ress);
	    });
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
          this.deptParams[this.deptId] = data.id;
          this.deptParams[this.deptName] = data.label;
          this.deptSearchFormDialog = false;
          this.$emit("input", this.deptParams);
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
        this.deptParams[this.deptId] = "";
        this.$emit("input", this.deptParams);
      }
    },
    // 刷新树节点
    handleRefresh() {
    	this.deptNameForm = "";
      this.getTreeSelect();
//    this.getDeptDatas();
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
