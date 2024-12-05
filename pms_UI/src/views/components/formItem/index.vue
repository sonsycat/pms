<template>
	<span>
    <el-card  v-if="openDialog" class="mb20">
      <ul>当前节点：{{currActivity}}</ul>
      <el-row>
        <el-col :span="24">
		    <el-form-item label="审核意见" v-if="openDialog" prop="comment">
          <el-input v-model="comment.comment" type="textarea" placeholder="请输入审核意见" />
        </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <staffSearch v-if="openDialog2" label="下节点审核人" :rules="assigneeRules"  :staffParams.sync = "comment" formItemProp="assignee"  inputProp="assigneepName" :userInfoList = "userOptions"></staffSearch>
        <!-- <el-form-item label="下节点审核人" v-if="openDialog2"  prop="assignee" :rules="assigneeRules">
            <el-select v-model="comment.assignee" placeholder="请选择下节点审核人" style="width:100%;">
              <el-option
                v-for="dict in userOptions2"
                :key="dict.userName"
                :label="dict.nickName"
                :value="dict.userName"
              ></el-option>
            </el-select>
        </el-form-item> -->
        </el-col>
      </el-row>
    </el-card>
	</span>
</template>
<script>
import { currActivityName } from "@/api/process/model";
export default {
  name: "addEdit",
  components: {},
  props: {
    open: {
      type: Boolean,
      default: false
    },
    open2: {
      type: Boolean,
      default: false
    },
    comment: {
      type: Object,
      default: {}
    },
    userOptions: {
      type: Array,
      default: {}
    }
  },
  data() {
    return {
      openDialog: this.open,
      openDialog2: this.open2,
      userOptions2: this.userOptions,
      currActivity: null,
      proclnsId: null,
      assigneeRules: [
        {
          required: true,
          message: "下节点审核人不能为空",
          trigger: ["change", "blur"]
        }
      ]
    };
  },
  computed: {},
  watch: {
    open: {
      handler(val) {
        this.openDialog = val;
      },
      deep: true
    },
    open2: {
      handler(val) {
        this.openDialog2 = val;
      },
      deep: true
    },
    userOptions: {
      handler(val) {
        this.userOptions2 = val;
      },
      deep: true
    },
    comment: {
      handler(val) {
        console.log('ok')
        this.proclnsId = val.procedureId;
        this.getList();
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    getList() {
      if (this.proclnsId == null || this.proclnsId == "") {
      } else {
        let data = {
          procInsId: this.proclnsId
        };
        currActivityName(data).then(response => {
          console.log(response);
          this.currActivity = response.data.activityName;
        });
      }
    }
  },
  mounted() {}
};
</script>

<style>
</style>
