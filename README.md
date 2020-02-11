# info
MVVM+Coroutine+koin实现的玩Android

# 特点
- 使用协程Coroutine
- 基于Navigation，1个Activity多个Fragment
- koin实现依赖注入

# Todo
- []
  WebView也放置Fragment中，可能会引起很明显的内存泄漏(不是WebView泄漏，而是导致的各个Fragment中SwipeRefreshLayout内存泄漏)，需要处理。万一处理不了，只能放置在Activity中
- [] Splash引导页面必须放置Activity中，因为Navigation必须要有一个startDestion，而且还要在首页中实现AppBar/DrawerLayout/BottomNavigation,
  目前不知道怎么把Splash也放置Fragment中