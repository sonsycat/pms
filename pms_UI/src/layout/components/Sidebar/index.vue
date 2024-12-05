<template>
    <div :class="{'has-logo':showLogo}" :style="{ backgroundColor: settings.sideTheme === 'theme-dark' ? variables.menuBg : variables.menuLightBg }">
        <logo v-if="showLogo" :collapse="isCollapse" />
        <el-scrollbar :class="settings.sideTheme" wrap-class="scrollbar-wrapper">
            <el-menu
                :default-active="activeMenu"
                :collapse="isCollapse"
                :background-color="getColor()"
                :text-color="getColor2()"
                :unique-opened="true"
                :collapse-transition="false"
                mode="vertical"
            	:active-text-color="getColor3()"
            >
                <sidebar-item
                    v-for="(route, index) in sidebarRouters"
                    :key="route.path  + index"
                    :item="route"
                    :base-path="route.path"
                />
            </el-menu>
        </el-scrollbar>
    </div>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import Logo from "./Logo";
import SidebarItem from "./SidebarItem";
import variables from "@/assets/styles/variables.scss";

export default {
    components: { SidebarItem, Logo },
    computed: {
        ...mapState(["settings"]),
        ...mapGetters(["sidebarRouters", "sidebar"]),
        activeMenu() {
            const route = this.$route;
            const { meta, path } = route;
            // if set path, the sidebar will highlight the path you set
            if (meta.activeMenu) {
                return meta.activeMenu;
            }
            return path;
        },
        showLogo() {
            return this.$store.state.settings.sidebarLogo;
        },
        variables() {
            return variables;
        },
        isCollapse() {
            return !this.sidebar.opened;
        }
    },
    methods: {
    	getColor(){
	  		if(this.$store.state.settings.sideTheme === 'theme-dark'){
	  			return variables.menuBg
	  		}else if(this.$store.state.settings.sideTheme === 'theme-light' || this.$store.state.settings.sideTheme === 'theme-blue2' || this.$store.state.settings.sideTheme === 'theme-blue4'){
	  			return variables.menuLightBg
	  		}else if(this.$store.state.settings.sideTheme === 'theme-blue'){
	  			return variables.menuBg
	  		}else if((this.$store.state.settings.sideTheme === 'theme-blue3'||this.$store.state.settings.sideTheme === 'theme-blue5') && (this.$store.state.settings.theme === '#1890FF')){
	  			return variables.menuBlue
	  		}else if((this.$store.state.settings.sideTheme === 'theme-blue3'||this.$store.state.settings.sideTheme === 'theme-blue5') && (this.$store.state.settings.theme != '#1890FF')){
	  			return this.$store.state.settings.theme
	  		}
	  	},
	  	getColor2(){
	  		if(this.$store.state.settings.sideTheme === 'theme-dark' || this.$store.state.settings.sideTheme === 'theme-blue3'||this.$store.state.settings.sideTheme === 'theme-blue5'){
	  			return variables.menuText
	  		}else if(this.$store.state.settings.sideTheme === 'theme-light' || this.$store.state.settings.sideTheme === 'theme-blue2' || this.$store.state.settings.sideTheme === 'theme-blue4'){
	  			return 'rgba(0,0,0,.65)'
	  		}else if(this.$store.state.settings.sideTheme === 'theme-blue'){
	  			return variables.menuText
	  		}
	  	},
	  	getColor3(){
	  		if(this.$store.state.settings.sideTheme === 'theme-blue3' ||this.$store.state.settings.sideTheme === 'theme-blue5'){
	  			return '#ff9292'
	  		}else{
	  			return this.$store.state.settings.theme
	  		}
	  	},
    },
};
</script>
