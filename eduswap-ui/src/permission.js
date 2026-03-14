import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'
import { isPathMatch } from '@/utils/validate'
import { isRelogin } from '@/utils/request'

NProgress.configure({ showSpinner: false })

const whiteList = ['/login', '/register']

const isWhiteList = (path) => {
  return whiteList.some(pattern => isPathMatch(pattern, path))
}

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    to.meta.title && store.dispatch('settings/setTitle', to.meta.title)
    /* has token*/
    if (to.path === '/login') {
      console.log('[路由守卫] 已登录，访问登录页，重定向到 /')
      next({ path: '/' })
      NProgress.done()
    } else if (isWhiteList(to.path)) {
      next()
    } else {
      if (store.getters.roles.length === 0) {
        console.log('[路由守卫] roles 为空，开始获取用户信息...')
        isRelogin.show = true
        // 判断当前用户是否已拉取完 user_info 信息
        store.dispatch('GetInfo').then(() => {
          console.log('[路由守卫] 用户信息获取成功，roles:', store.getters.roles)
          isRelogin.show = false
          store.dispatch('GenerateRoutes').then(accessRoutes => {
            // 根据 roles 权限生成可访问的路由表
            console.log('[路由守卫] 路由生成成功，准备访问:', to.path)
            router.addRoutes(accessRoutes) // 动态添加可访问路由表
            next({ ...to, replace: true }) // hack 方法 确保 addRoutes 已完成
          })
        }).catch(err => {
            console.error('[路由守卫] 获取用户信息失败:', err)
            store.dispatch('LogOut').then(() => {
              Message.error(err)
              next({ path: '/' })
            })
          })
      } else {
        console.log('[路由守卫] roles 已存在，直接放行:', store.getters.roles)
        next()
      }
    }
  } else {
    // 没有 token
    if (isWhiteList(to.path)) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next(`/login?redirect=${encodeURIComponent(to.fullPath)}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
