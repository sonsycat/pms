<template>
  <div id="app" >
    <router-view :class="{ 'setTableBorder': tableBorder }"/>
  </div>
</template>

<script>
  export default {
    name: "App",
    data() {
      return {
        tableBorder: this.$store.state.settings.sysTabeleBorder
      };
    },
    watch: {
      "$store.state.settings.sysTabeleBorder": {
        handler(val) {
          this.tableBorder = val;
        },
        deep: true
      }
    },
    metaInfo() {
      return {
        title:
          this.$store.state.settings.dynamicTitle &&
          this.$store.state.settings.title,
        titleTemplate: title => {
          return title
            ? `${title} - ${process.env.VUE_APP_TITLE}`
            : process.env.VUE_APP_TITLE;
        }
      };
    }
  };
</script>
<style lang="scss">
  // table显示边框
  .setTableBorder {
    .el-table {
      border: 1px solid #dfe6ec;
      border-bottom: none;
      th,
      td {
        border-right: 1px solid #dfe6ec;
      }
      > ::after {
        background-color: #dfe6ec;
      }
      > ::before {
        background-color: #dfe6ec;
      }
    }
  }
</style>
