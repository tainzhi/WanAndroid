import com.tainzhi.android.buildsrc.Libs
import org.jetbrains.kotlin.konan.properties.Properties
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs.kotlin")
    id("bugly")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("plugin.serialization")
    kotlin("kapt")
}

apply {
    from("../test_dependencies.gradle")
}

val byteOut = org.apache.commons.io.output.ByteArrayOutputStream()
exec {
    commandLine = "git rev-list HEAD --first-parent --count".split(" ")
    standardOutput = byteOut
}
val verCode = String(byteOut.toByteArray()).trim().toInt()
val version = gitDescribeVersion()

android {
    signingConfigs {
        //加载资源
        val properties = Properties()
        val inputStream = project.rootProject.file("local.properties").inputStream()
        properties.load(inputStream)
        
        // 读取文件
        val releaseKeyStoreFile = properties.getProperty("release_keyStoreFile")
        // 读取字段
        val releaseKeyAlias = properties.getProperty("release_keyAlias")
        val releaseKeyPassword = properties.getProperty("release_keyPassword")
        val releaseKeyStorePassword = properties.getProperty("release_keyStorePassword")
        create("release") {
            storeFile = file(releaseKeyStoreFile)
            storePassword = releaseKeyStorePassword
            keyAlias = releaseKeyAlias
            keyPassword = releaseKeyPassword
        }
    }
    compileSdkVersion(Libs.Version.compileSdkVersion)
    buildToolsVersion(Libs.Version.buildToolsVersion)
    
    defaultConfig {
        applicationId = "com.tainzhi.android.wanandroid"
        minSdkVersion(Libs.Version.minSdkVersion)
        targetSdkVersion(Libs.Version.targetSdkVersion)
        versionCode = verCode
        versionName = version
        
        // 第三方库 AppUpdate
        // 每个应用拥有不同的authorities，防止相同在同一个手机上无法同时安装
        val _id = applicationId ?: ""
        resValue("string", "authorities", _id)
    }

    buildFeatures {
        dataBinding = true
    }

    buildTypes {
        getByName("debug") {
            // 默认值 true
            // isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
    
    // productFlavors {
    // android.applicationVariants.all { variant ->
    //     variant.outputs.all {
    //         outputFileName = "QWanAndroid_${variant.versionName}${variant.flavorName}_${variant.buildType.name}.apk"
    //     }
    // }
    // }
    
    applicationVariants.all {
        outputs.map {
            it as com.android.build.gradle.internal.api.BaseVariantOutputImpl
        }
                .forEach { output ->
                    output.outputFileName = "QWanAndroid_${defaultConfig.versionName}${defaultConfig.buildConfigFields}.apk"
                }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

}

bugly {
    appId = "25c0753a52"
    appKey = "2c72a2dc-57af-47c2-be10-c6f592cc743f"
    // debug = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    
    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.preference)
    implementation(Libs.AndroidX.constraintlayout)
    implementation(Libs.AndroidX.viewPager2)
    implementation(Libs.AndroidX.swiperefresh)
    implementation(Libs.AndroidX.Navigation.fragmentKtx)
    implementation(Libs.AndroidX.Navigation.uiKtx)
    implementation(Libs.AndroidX.Lifecycle.viewmodelKtx)
    implementation(Libs.AndroidX.Lifecycle.livedata)
    implementation(Libs.AndroidX.Lifecycle.extensions)
    implementation(Libs.AndroidX.Room.runtime)
    implementation(Libs.AndroidX.Room.ktx)
    kapt(Libs.AndroidX.Room.compiler)
    implementation(Libs.AndroidX.Paging.runtime)
    implementation(Libs.AndroidX.Paging.runtimeKtx)
    
    implementation(Libs.Google.material)
    
    implementation(Libs.Coroutines.android)
    
    implementation(Libs.Koin.scope)
    implementation(Libs.Koin.viewmodel)
    
    implementation(Libs.Retrofit.retrofit)
    implementation(Libs.Retrofit.gsonConverter)
    implementation(Libs.OkHttp.loggingInterceptor)
    implementation(Libs.cookietar)
    
    implementation(Libs.Glide.glide)
    kapt(Libs.Glide.compiler)
    
    debugImplementation(Libs.leakCanary)
    debugImplementation(Libs.DoKit.debugVersion)
    
    implementation(Libs.timber)
    implementation(Libs.baseRecyclerViewAdapterHelper)
    implementation(Libs.youthBanner)
    implementation(Libs.tencentTbssdk)
    implementation(Libs.verticalTabLayout)
    implementation(Libs.flowlayout)
    implementation(Libs.licenseDialog)
    implementation(Libs.appUpdate)
    implementation(Libs.activityOnCrash)
    implementation(Libs.buglyCrashReport)
    implementation(Libs.multiStateView)
}

task("updateReleaseApk") {
    // 升级内容以 \n 分割
    val updateDescription = "1.修改了UI逻辑\n2.fix some fatal bug\n3.添加bug上报\n4.混淆代码\n5.添加Crash页面"
    addDownloadUrl(updateDescription)
}.dependsOn("assembleRelease")

// after you run `git tag`, then you can retrieve it
fun gitDescribeVersion(): String {
    
    val stdOut = org.apache.commons.io.output.ByteArrayOutputStream()
    
    exec {
        commandLine("git", "describe", "--tags", "--long", "--always", "--match", "[0-9].[0-9]*")
        standardOutput = stdOut
        workingDir = rootDir
    }
    
    val describe = stdOut.toString().trim()
    val gitDescribeMatchRegex = """(\d+)\.(\d+)-(\d+)-.*""".toRegex()
    
    return gitDescribeMatchRegex.matchEntire(describe)
            ?.destructured
            ?.let { (major, minor, patch) ->
                "$major.$minor.$patch"
            }
            ?: throw GradleException("Cannot parse git describe '$describe'")
}

//
// // assembleRelease后会在app/build/outpus/apk/release/目录下生成apk和outpus.json
// // outpus.json已经有apk的一些信息，比如versionCode和versionNumber
// // 默认缺少打包时间和更新描述，在这里添加
// // 并添加下载路径
// // 我要把包通过github action上传到 https://gitee.com/qinmen/GithubServer/WanAndroid 方便下载
fun addDownloadUrl(updateDescription: String) {
    val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    val inputJsonPath = "app/build/outputs/apk/release/output.json"
    val inputFile = File(inputJsonPath)
    val json = groovy.json.JsonSlurper().parseText(inputFile.text)
    val apkFileName = json.elements[0].outputFile
    val versionCode = json.elements[0].versionCode
    val downloadUrl = "https://gitee.com/qinmen/GithubServer/raw/master/WanAndroid" + apkFileName
    val backupDownloadUrl = "https://github.com/tainzhi/WanAndroid/releases/download/" + gitVersionTag() + "/" + apkFileName
    // val dataMap = [ "versionCode": versionCode,
    //                 "description": updateDescription,
    //                 "url": downloadUrl,
    //                 "url_backup": backupDownloadUrl,
    //                 "time": currentTime,
    //                 "apkName": apkFileName]
    // def updateMap = [ "errorCode": 0, "data": dataMap, "errorMsg": ""]
    val outputJsonPath = "app/build/outputs/apk/release/update.json"
    // (File(outputJsonPath)).write(new JsonOutput().toJson(updateMap))
}
