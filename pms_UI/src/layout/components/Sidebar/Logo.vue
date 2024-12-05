<template>
	<div>
	<template v-if="sideTheme === 'theme-dark'">
  	<div class="sidebar-logo-container" :class="{'collapse':collapse}" :style="{ backgroundColor: variables.menuBg }">
	    <transition name="sidebarLogoFade">
	      <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
	        <img v-if="logo" :src="logo" class="sidebar-logo">
	        <h1 v-else class="sidebar-title" :style="{ color: variables.sidebarTitle}">{{ title }} </h1>
	      </router-link>
	      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
	        <h1 class="sidebar-title" :style="{ color: variables.sidebarTitle}">{{ title }} </h1>
	      </router-link>
	    </transition>
    </div>
	</template>
	<template v-else-if="sideTheme === 'theme-light' || sideTheme === 'theme-blue4'">
		<div class="sidebar-logo-container" :class="{'collapse':collapse}" :style="{ backgroundColor: variables.menuLightBg }">
	    <transition name="sidebarLogoFade">
	      <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
	        <img v-if="logo" :src="logo" class="sidebar-logo">
	        <h1 v-else class="sidebar-title" :style="{ color: variables.sidebarLightTitle}">{{ title }} </h1>
	      </router-link>
	      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
	        <h1 class="sidebar-title" :style="{ color: variables.sidebarLightTitle}">{{ title }} </h1>
	      </router-link>
	    </transition>
    </div>
	</template>
	<template v-else-if="(sideTheme === 'theme-blue' || sideTheme === 'theme-blue2' || sideTheme === 'theme-blue3'|| sideTheme === 'theme-blue5') && (theme=== '#1890FF')">
		<div class="sidebar-logo-container" :class="{'collapse':collapse}" :style="{ backgroundColor: variables.menuBlue }">
		    <transition name="sidebarLogoFade">
		      <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
		        <img v-if="logo" :src="logo" class="sidebar-logo">
		        <h1 v-else class="sidebar-title" :style="{ color: variables.sidebarTitle}">{{ title }} </h1>
		      </router-link>
		      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
		        <h1 class="sidebar-title" :style="{ color: variables.sidebarTitle}">{{ title }} </h1>
		      </router-link>
		    </transition>
    	</div>
	</template>
	<template v-else-if="(sideTheme === 'theme-blue' || sideTheme === 'theme-blue2' || sideTheme === 'theme-blue3' || sideTheme === 'theme-blue5') && (theme!= '#1890FF')">
		<div class="sidebar-logo-container" :class="{'collapse':collapse}" :style="{ backgroundColor: theme }">
		    <transition name="sidebarLogoFade">
		      <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
		        <img v-if="logo" :src="logo" class="sidebar-logo">
		        <h1 v-else class="sidebar-title" :style="{ color: variables.sidebarTitle}">{{ title }} </h1>
		      </router-link>
		      <router-link v-else key="expand" class="sidebar-logo-link" to="/">
		        <h1 class="sidebar-title" :style="{ color: variables.sidebarTitle}">{{ title }} </h1>
		      </router-link>
		    </transition>
    	</div>
	</template>


	</div>
</template>

<script>
import logoImg from '@/assets/logo/logo.png'
import variables from '@/assets/styles/variables.scss'

export default {
  name: 'SidebarLogo',
  props: {
    collapse: {
      type: Boolean,
      required: true
    }
  },
  computed: {
    variables() {
      return variables;
    },
	sideTheme() {
      return this.$store.state.settings.sideTheme
   },
	theme(){
      return this.$store.state.settings.theme
	}

  },
  data() {
    return {
      title: '绩效管理',
      logo: logoImg
    }
  }
}
</script>

<style lang="scss" scoped>
.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
}

.sidebar-logo-container {
  position: relative;
  width: 100%;
  height: 50px;
  line-height: 50px;
  background: #2b2f3a;
  text-align: center;
  overflow: hidden;

  & .sidebar-logo-link {
    height: 100%;
    width: 100%;

    & .sidebar-logo {
      width: 32px;
      height: 32px;
      vertical-align: middle;
      margin-right: 12px;
    }

    & .sidebar-title {
      display: inline-block;
      margin: 0;
      color: #fff;
      font-weight: 600;
      line-height: 50px;
      font-size: 20px;
      font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
      vertical-align: middle;
    }
  }

  &.collapse {
    .sidebar-logo {
      margin-right: 0px;
    }
  }
}
</style>
