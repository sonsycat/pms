<template>
  <div>
  	<template v-if="sideTheme === 'theme-blue' || sideTheme === 'theme-blue2' || sideTheme === 'theme-blue4'">
    	<svg-icon :icon-class="isFullscreen?'exit-fullscreen':'fullscreen'" :style = "{ color: variables.menuLightBg}" @click="click" />
		</template>
		<template v-else>
    	<svg-icon :icon-class="isFullscreen?'exit-fullscreen':'fullscreen'" @click="click" />
		</template>
  </div>
</template>

<script>
import screenfull from 'screenfull'
import variables from '@/assets/styles/variables.scss'

export default {
  name: 'Screenfull',
  data() {
    return {
      isFullscreen: false
    }
  },
  mounted() {
    this.init()
  },
  computed: {
  	variables() {
      return variables;
    },
		sideTheme() {
			console.log(this.$store.state.settings.sideTheme);
      return this.$store.state.settings.sideTheme
  	}
  },
  beforeDestroy() {
    this.destroy()
  },
  methods: {
    click() {
      if (!screenfull.isEnabled) {
        this.$message({ message: '你的浏览器不支持全屏', type: 'warning' })
        return false
      }
      screenfull.toggle()
    },
    change() {
      this.isFullscreen = screenfull.isFullscreen
    },
    init() {
      if (screenfull.isEnabled) {
        screenfull.on('change', this.change)
      }
    },
    destroy() {
      if (screenfull.isEnabled) {
        screenfull.off('change', this.change)
      }
    }
  }
}
</script>

<style scoped>
.screenfull-svg {
  display: inline-block;
  cursor: pointer;
  fill: #5a5e66;;
  width: 20px;
  height: 20px;
  vertical-align: 10px;
}
</style>
