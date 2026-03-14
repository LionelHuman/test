<template>
    <div :class="{'has-logo':showLogo}" :style="{ backgroundColor: settings.sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground }">
        <logo v-if="showLogo" :collapse="isCollapse" />
        <el-scrollbar :class="settings.sideTheme" wrap-class="scrollbar-wrapper">
            <el-menu
                :default-active="activeMenu"
                :collapse="isCollapse"
                :background-color="settings.sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground"
                :text-color="settings.sideTheme === 'theme-dark' ? variables.menuColor : variables.menuLightColor"
                :unique-opened="true"
                :active-text-color="settings.theme"
                :collapse-transition="false"
                mode="vertical"
            >
                <sidebar-item
                    v-for="(route, index) in filteredSidebarRouters"
                    :key="route.path  + index"
                    :item="route"
                    :base-path="route.path"
                />
            </el-menu>
        </el-scrollbar>
    </div>
</template>

<script>
import { mapGetters, mapState } from "vuex"
import Logo from "./Logo"
import SidebarItem from "./SidebarItem"
import variables from "@/assets/styles/variables.scss"

export default {
    components: { SidebarItem, Logo },
    computed: {
        ...mapState(["settings"]),
        ...mapGetters(["sidebarRouters", "sidebar", "roles"]),
        // 过滤后的菜单路由（排除前台商城首页、若依官网、商品详情、重复的后台首页）
        filteredSidebarRouters() {
            return this.sidebarRouters.filter(route => {
                // 排除前台商城首页菜单（路径 /front/index）
                if (route.path === '/front/index' || (route.meta && route.meta.title === '购物首页')) {
                    return false
                }
                // 排除若依官网菜单
                if (route.path === '/tool/gen' || (route.meta && route.meta.title === '若依官网')) {
                    return false
                }
                // 排除商品详情菜单
                if (route.path === '/product/:id' || (route.meta && route.meta.title === '商品详情')) {
                    return false
                }
                // 排除重复的后台首页菜单（保留一个即可）
                if (route.path === '/index' && (route.meta && route.meta.title === '后台首页')) {
                    return false
                }
                return true
            })
        },
        activeMenu() {
            const route = this.$route
            const { meta, path } = route
            // if set path, the sidebar will highlight the path you set
            if (meta.activeMenu) {
                return meta.activeMenu
            }
            return path
        },
        showLogo() {
            return this.$store.state.settings.sidebarLogo
        },
        variables() {
            return variables
        },
        isCollapse() {
            return !this.sidebar.opened
        }
    },
    methods: {
    }
}
</script>
