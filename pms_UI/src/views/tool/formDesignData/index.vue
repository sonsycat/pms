<template>
  <div>
    <fm-generate-form :data="jsonData" ref="generateForm">

    </fm-generate-form>
    <el-button type="primary" @click="handleSubmit">提交</el-button>
  </div>
</template>

<script>
import { getFormDesign } from "@/api/formDesign/formDesign";
import { saveData } from "@/api/formDesignData/formDesignData";

export default {
  data () {
    return {
      jsonData: {"list":[{"type":"input","icon":"icon-input","options":{"width":"100%","defaultValue":"","required":false,"dataType":"string","pattern":"","placeholder":"","disabled":false,"remoteFunc":"func_1627615198000_96878"},"name":"单行文本","key":"1627615198000_96878","model":"input_1627615198000_96878","rules":[{"type":"string","message":"单行文本格式不正确"}]},{"type":"radio","icon":"icon-radio-active","options":{"inline":false,"defaultValue":"","showLabel":false,"options":[{"value":"Option 1","label":"Option 1"},{"value":"Option 2","label":"Option 2"},{"value":"Option 3","label":"Option 3"}],"required":false,"width":"","remote":false,"remoteOptions":[],"props":{"value":"value","label":"label"},"remoteFunc":"func_1627867368000_27567","disabled":false},"name":"单选框组","key":"1627867368000_27567","model":"radio_1627867368000_27567","rules":[]},{"type":"checkbox","icon":"icon-check-box","options":{"inline":false,"defaultValue":[],"showLabel":false,"options":[{"value":"Option 1"},{"value":"Option 2"},{"value":"Option 3"}],"required":false,"width":"","remote":false,"remoteOptions":[],"props":{"value":"value","label":"label"},"remoteFunc":"func_1627867370000_51787","disabled":false},"name":"多选框组","key":"1627867370000_51787","model":"checkbox_1627867370000_51787","rules":[]},{"type":"date","icon":"icon-date","options":{"defaultValue":"","readonly":false,"disabled":false,"editable":true,"clearable":true,"placeholder":"","startPlaceholder":"","endPlaceholder":"","type":"date","format":"yyyy-MM-dd","timestamp":false,"required":false,"width":"","remoteFunc":"func_1627867371000_93910"},"name":"日期选择器","key":"1627867371000_93910","model":"date_1627867371000_93910","rules":[]}],"config":{"labelWidth":100,"labelPosition":"right","size":"small","customClass":""}},
      //editData: {}
    }
  },
  created() {
    //this.setForm();
    this.setData();
  },
  methods: {
    handleSubmit () {
      const formDesignId = this.$route.query.formDesignId;
      this.$refs.generateForm.getData().then(data => {
        saveData(formDesignId, data);
      }).catch(e => {
        // data check failed
      })
    },
    setForm() {
      const formDesignId = this.$route.query.formDesignId;
      console.log('formDesignId===' + formDesignId)
      getFormDesign(formDesignId).then(response => {
        console.log(response.data.formJson)
        this.jsonData = response.data.formJson;
        this.$nextTick(() => {
          this.$refs.generateForm.refresh();
        })
      });
    },
    setData() {
      const formDesignId = this.$route.query.formDesignId;
      console.log('formDesignId===' + formDesignId)
      getFormDesign(formDesignId).then(response => {
        const formData = response.data.formData;
        console.log(formData)
        this.$refs.generateForm.setData(formData)
        //this.$refs.generateForm.reset()
      });
    }
  }
}
</script>
