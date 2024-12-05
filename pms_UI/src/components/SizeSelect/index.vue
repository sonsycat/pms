<template>
  <el-dropdown trigger="click" @command="handleSetSize">
    <div>
    	<template v-if="sideTheme === 'theme-blue' || sideTheme === 'theme-blue2' || sideTheme === 'theme-blue4'">
      	<svg-icon class-name="size-icon" icon-class="size" :style = "{ color: variables.menuLightBg}" />
			</template>
			<template v-else>
      	<svg-icon class-name="size-icon" icon-class="size" />
			</template>
    </div>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item v-for="item of sizeOptions" :key="item.value" :disabled="size===item.value" :command="item.value">
        {{
          item.label }}
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
import variables from '@/assets/styles/variables.scss'
	
export default {
  data() {
    return {
      sizeOptions: [
        { label: 'Default', value: 'default' },
        { label: 'Medium', value: 'medium' },
        { label: 'Small', value: 'small' },
        { label: 'Mini', value: 'mini' }
      ]
    }
  },
  computed: {
  	variables() {
      return variables;
    },
		sideTheme() {
			console.log(this.$store.state.settings.sideTheme);
      return this.$store.state.settings.sideTheme
  	},
  	size() {
      return this.$store.getters.size
    }
  },
  methods: {
    handleSetSize(size) {
      this.$ELEMENT.size = size
      this.$store.dispatch('app/setSize', size)
      this.refreshView()
      this.$message({
        message: 'Switch Size Success',
        type: 'success'
      })
    },
    refreshView() {
      // In order to make the cached page re-rendered
      this.$store.dispatch('tagsView/delAllCachedViews', this.$route)

      const { fullPath } = this.$route

      this.$nextTick(() => {
        this.$router.replace({
          path: '/redirect' + fullPath
        })
      })
    }
  }

}
</script>
