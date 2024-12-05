<template>
	<span>
		<el-button v-if="openDialog" type="primary" icon="el-icon-circle-check" @click="examineSubmitForm1">审核通过</el-button>
	    <el-button v-if="openDialog" type="danger" icon="el-icon-circle-check"  @click="examineSubmitForm2">审核驳回</el-button>
	    <el-button v-if="openDialog" type="danger" icon="el-icon-circle-check" @click="examineSubmitForm3">审核终止</el-button>
	</span>
</template>

<script>
import { audit } from "@/api/skill/monograph";
export default {
  name: "addEdit",
  components: {},
  props: {
    id: {
      type: [String, Number],
      default: ""
    },
    proclnsIds: {
      type: [String, Number],
      default: ""
    },
    comment: {
      type: [String, Number],
      default: ""
    },
    assignee: {
      type: [String, Number],
      default: ""
    },
    open: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      openDialog: this.open,
      id2: this.id,
      proclnsIds2: this.proclnsIds,
      comment2: this.comment,
      assignee2: this.assignee
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
    id: {
      handler(val) {
        this.id2 = val;
      },
      deep: true
    },
    proclnsIds: {
      handler(val) {
        this.proclnsIds2 = val;
      },
      deep: true
    },
    comment: {
      handler(val) {
        this.comment2 = val;
      },
      deep: true
    },
    assignee: {
      handler(val) {
        this.assignee2 = val;
      },
      deep: true
    }
  },
  mounted() {},
  methods: {
    // 审核通过
    examineSubmitForm1() {
      let data = {
        id: this.id2,
        procedureId: this.proclnsIds2,
        comment: this.comment2,
        assignee: this.assignee2,
        auditState: "2"
      };
      console.log(data);
      let saveLoading = this.saveLoading("审核中...")
      audit(data).then(response => {
        if (response.code == 200) {
          console.log(response);
          this.msgSuccess("审核成功");
          this.$emit("success");
        } else {
          this.msgError(response.msg);
        }
        saveLoading.close()
      }).catch(()=>{
        saveLoading.close()
      })
    },
    // 审核驳回
    examineSubmitForm2() {
      let data = {
        id: this.id2,
        procedureId: this.proclnsIds2,
        comment: this.comment2,
        auditState: "3"
      };
      let saveLoading = this.saveLoading("审核中...")
      audit(data).then(response => {
        console.log(response);
        if (response.code == 200) {
          this.msgSuccess("驳回成功");
          this.$emit("success");
        } else {
          this.msgError(response.msg);
        }
        saveLoading.close()
      }).catch(()=>{
        saveLoading.close()
      })
    },
    // 审核终止
    examineSubmitForm3() {
      let data = {
        id: this.id2,
        procedureId: this.proclnsIds2,
        comment: this.comment2,
        auditState: "4"
      };
      let saveLoading = this.saveLoading("审核中...")
      audit(data).then(response => {
        if (response.code == 200) {
          console.log(response);
          this.msgSuccess("终止成功");
          this.$emit("success");
        } else {
          this.msgError(response.msg);
        }
        saveLoading.close()
      }).catch(()=>{
        saveLoading.close()
      })
    }
  }
};
</script>

<style>
</style>
