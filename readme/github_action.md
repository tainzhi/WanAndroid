# info
- 使用github action完成自动打包签名后上传到github release assets
- 把编译的apk和相关的升级信息update.json通过上传到[gitee](https://gitee.com/qinmen/GithubServer/tree/master/WanAndroid)
  update.json

# action script详解
action路径， [git workflow](./github/workflows/release_gitee.yml)

- l1: `name:` 一些介绍信息
- l2: `env:` 全局环境变量. 使用方法为`${{ env.REPO }}`,
  value值不用`""`来包含。要注意，通过手动设置secrets调用为`secrets.GITHUB_TOKEN`
- l4: `on:`为触发条件，当`push tags` tag为任意，时触发

### checkout code
- l10: `runs-on: ubuntu-lates` 运行在最新的ubuntu系统
- l11: `step:`, 每个step通过`-`区分，包含`name: uses:`
- l13: `uses: run:`同时只能有一个
- l13: `actions/checkout@master` clone当前repo到action 主机本地，比如`.../WanAndroid/Wandroid/..

### set environemnt
- l17: 设置环境变量`env.TAG=$(shell command)`, 该命令获取到tag，eg. `v0.6.7`
- l18: 设置环境变量`env.CURRENT_DIR=""

### set up JDK 1.8
设置java环境

### assemble release apk
执行gradle命令，打包生成默认signed的apk到 `env. RELEASE_APK_DIR =
app/build/outputs/apk/release`目录下，
apk名称类似与`QWanAndroid_v0.7.7.0_release.apk`, 和`outputs.json`

- outputs.json默认有versionCode
- gradle assembleRelease执行完毕后，会执行` app.build.gradle
  下DownloadUrl()`添加打包时间和下载路径到update.json

### get apk basename
- l31:
  获取`app/build/outputs/apk/release/QWanAndroid_v0.7.7.0_release.apk`的basename
  `QWanAndroid_v0.7.7.0_release`方便与后续命名

### sign release apk
- 使用的[github aciton](https://github.com/r0adkll/sign-android-release)
- 解码`android.release.keystore`复制后添加到github该项目settings > secrets， 添加`SIGNING_KEY`的value
```bash
openssl base64 < android.release.keystore |tr -d '\n'| tee signing.key | pbcopy
```
- 同理添加`ALIAS`, `KEY_STORE_PASSOWR`, `KEY_PASSWORD`到github的secrets
- 生成的signed
  apk为`app/build/outputs/apk/release/QWanAndroid_v0.6.11.0_release-signed.apk`
- 设置了`env.SIGNED_RELEASE_FILE`指向该signed apk


### set gitee private key
- 使用的action[set ssh key](https://github.com/kielabokkie/ssh-key-and-known-hosts-action)
- 因为要把signed的apk和update.json上传到gitee仓库，所以需要配置访问gitee的ssh私钥
- 私钥已经设置到`secretes.GITEE_PRIVATE_KEY`
- 签名后的文件为`
### clone GithubServer from gitee
- siged的apk是上传到[gitee:GithubServer](https://gitee.com/qinmen/GithubServer)
- 先从`WanAndroid`目录返回上一级，拷贝signed
  apk和output.json到`GithubServer/WanAndroid`下
- 进入`GithubServer`目录，设置local user后提交

### Create release
- 使用action
  [create-release](https://github.com/actions/create-release@v1)
- 不用做任何设置

### Upload Release Asset
- 使用action
  [upload release asset](https://github.com/actions/upload-release-asset)
- asset_path为签名后的apk，因为在当前`env.REPO`目录下，直接进入`app/...`目录下
- asset_name为上传到github release asset的名称，使用了`${{ env.BASE_NAME }}
- gradle默认生成了不签名的apk， 通过action加密后，添加了后缀`-signed， 需要去除

