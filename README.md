<!-- vim-markdown-toc GFM -->

+ [info](#info)
+ [Demo](#demo)
+ [特点](#特点)
+ [构建和使用](#构建和使用)
+ [Todo](#todo)
    * [feat](#feat)
    * [fix bug](#fix-bug)
+ [致谢](#致谢)

<!-- vim-markdown-toc -->
# info
MVVM(Databinding/VideModel/LifeCycle/Navigation/room)+Coroutine+koin实现的[玩Android](https://wanandroid.com/blog/show/2)

# Demo
<img src="./images/demo.gif" alt="项目demo" height="300">

# 特点
- 使用协程Coroutine
- 基于Navigation，1个Activity多个Fragment(except SplashActivity)
- [koin](https://github.com/InsertKoinIO/koin)实现依赖注入
- buildSrc + kotlin管理第三库依赖
- gradle + git 动态管理版本号和tag
- DrawerLayout + NavigationView + TabLayout
- Material Desing风格
- 暗黑主题
- room实现查询记录/浏览记录
- unit test with Room/koin

# 构建和使用
- 1. 需求：必须要Android Studio 4.0 以上



# Todo
## feat
- [] 参考[android developer/testing](https://developer.android.com/training/testing)添加各种测试
- [] 升级youthbanner
- [] 升级[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/releases)
- [] 历史记录分页查找`select * from table_name limit 10 offset 0`
- [] 参考[android developer/performance](https://developer.android.com/topic/performance)优化app

## fix bug
- [] [添加用户信息详细查询显示和修改](https://www.wanandroid.com/blog/show/2)
- [] NavigationFragment中的VerticalLayout和RecyclerView滑动联动不够完善
- [] WebView也放置Fragment中，可能会引起很明显的内存泄漏(不是WebView泄漏，而是导致的各个Fragment中SwipeRefreshLayout内存泄漏)，需要处理。万一处理不了，只能放置在Activity中
- [] Splash引导页面必须放置Activity中，因为Navigation必须要有一个startDestion，而且还要在首页中实现AppBar/DrawerLayout/BottomNavigation,
  目前不知道怎么把Splash也放置Fragment中

# 致谢
学习了[Lulululbj Wanandroid](https://github.com/lulululbj/wanandroid/)大神很多，也像众多其他大佬学习了很多。感谢

