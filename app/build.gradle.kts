import com.tainzhi.android.buildsrc.Libs
import java.io.ByteArrayOutputStream

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
    // kotlin("plugin.serialization")
    id("androidx.navigation.safeargs.kotlin")
    // id("bugly")
    id("io.wusa.semver-git-plugin").version("2.3.7")
    id("com.tainzhi.android.plugin.autoupload")
}
// apply(plugin = "com.tainzhi.android.plugin.upload")

val byteOut = ByteArrayOutputStream()
exec {
    commandLine = "git rev-list HEAD --first-parent --count".split(" ")
    standardOutput = byteOut
}

android {
    // signingConfigs {
    //     getByName("debug") {
    //         // debug版本默认不签名
    //         // storeFile = file("../android.keystore")
    //     }
    //
    //     create("release") {
    //         storeFile = file("../android.keystore")
    //         // #签名密码
    //         storePassword = "123456"
    //         // #签名别名
    //         keyAlias = "android"
    //         // #签名别名密码
    //         keyPassword = "tainzhi"
    //     }
    // }
    compileSdkVersion(Libs.Configs.compileSdkVersion)
    buildToolsVersion(Libs.Configs.buildToolsVersion)

    defaultConfig {
        applicationId = "com.tainzhi.android.wanandroid"
        minSdkVersion(Libs.Configs.minSdkVersion)
        targetSdkVersion(Libs.Configs.targetSdkVersion)
        versionCode = semver.info.count
        versionName = semver.info.lastTag
        flavorDimensions("1")

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
            applicationIdSuffix = ".debug"
            // signingConfigs["debug"]
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            // signingConfigs["release"]
        }
    }

    productFlavors {
        create("pgy") {
            // dimension = ""
        }
    }

    applicationVariants.all {
        // val outputFileName = getOutputFileName(
        //         defaultConfig,
        //         buildType = buildTypes.getByName(name)
        // )
        //
        outputs.forEach { output ->
            check(output is com.android.build.gradle.internal.api.ApkVariantOutputImpl)
            if (output is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                if (buildType.name == "debug") {
                    output.outputFileName =
                        "QWanAndroid_${flavorName}_${versionName}_${buildType.name}.apk"
                } else if (buildType.name == "release") {
                    output.outputFileName = "QWanAndroid_${flavorName}_${versionName}.apk"
                }
            }
            // output.outputFileName = outputFileName
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        useIR = true
    }

    adbOptions {
        timeOutInMs = 20 * 60 * 1000  // 20 minutes
        installOptions("-d", "-t")
    }

}
autoUpload {
    apiKey = "99d9f637f9ca00b7ef97cdb2cdabd8ac"
    flavor = "pgy"
    buildType = "release"
    updateDescription =
    """
        1. 使用自定义plugin上传
        2. 修改相关依赖
    """.trimIndent()
}

// bugly {
//     appId = "25c0753a52"
//     appKey = "2c72a2dc-57af-47c2-be10-c6f592cc743f"
//     // debug = true
// }

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(":common"))
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
    // debugImplementation(Libs.DoKit.debugVersion)
    // releaseImplementation(Libs.DoKit.releaseVersion)

    implementation(Libs.timber)
    implementation(Libs.baseRecyclerViewAdapterHelper)
    implementation(Libs.youthBanner)
    implementation(Libs.tencentTbssdk)
    implementation(Libs.verticalTabLayout)
    implementation(Libs.flowlayout)
    implementation(Libs.licenseDialog)
    implementation(Libs.appUpdate)
    implementation(Libs.activityOnCrash)
    // implementation(Libs.buglyCrashReport)
    implementation(Libs.multiStateView)

    ///////////////////////////////////////////////////////////////////////////
    // 以下是 unit test 依赖
    ///////////////////////////////////////////////////////////////////////////
    testImplementation(Libs.junit)
    testImplementation(Libs.Mockito.core)
    testImplementation(Libs.Koin.test)
    testImplementation(Libs.Coroutines.test)
    testImplementation(Libs.AndroidX.Test.core)
    testImplementation(Libs.AndroidX.archCoreTesting)
    testImplementation(Libs.Google.truth)

    ///////////////////////////////////////////////////////////////////////////
    // 以下是 android test 依赖
    ///////////////////////////////////////////////////////////////////////////
    // Koin for Unit test and instrumented test
    androidTestImplementation(Libs.Koin.test)
    androidTestImplementation(Libs.Coroutines.test)

    androidTestImplementation(Libs.AndroidX.archCoreTesting)
    // Core Library
    androidTestImplementation(Libs.AndroidX.Test.core)
    // Required for instrumen(ed tests, AndroidJUnitRunner and JUnit Rules
    androidTestImplementation(Libs.AndroidX.Test.runner)
    androidTestImplementation(Libs.AndroidX.Test.rules)
    androidTestImplementation(Libs.AndroidX.Test.Ext.junit)
    // assertion
    androidTestImplementation(Libs.AndroidX.Test.Ext.truth)
    androidTestImplementation(Libs.Google.truth)
    // espresso
    androidTestImplementation(Libs.AndroidX.Test.Espresso.core)
    androidTestImplementation(Libs.AndroidX.Test.Espresso.contrib)
    androidTestImplementation(Libs.AndroidX.Test.Espresso.intents)

}

// task("updateReleaseApk") {
//     // 升级内容以 \n 分割
//     val updateDescription = "1.修改了UI逻辑\n2.fix some fatal bug\n3.添加bug上报\n4.混淆代码\n5.添加Crash页面"
//     addDownloadUrl(updateDescription)
// }.dependsOn("assembleRelease")

fun getOutputFileName(
    productFlavor: com.android.builder.model.ProductFlavor,
    buildType: com.android.build.gradle.internal.dsl.BuildType
): String {
    return productFlavor.applicationId + buildType.applicationIdSuffix +
            "-" + productFlavor.versionName +
            // "-" + productFlavor.versionCode +
            ".apk"
}

// assembleRelease后会在app/build/outpus/apk/release/目录下生成apk和outpus.json
// outpus.json已经有apk的一些信息，比如versionCode和versionNumber
// 默认缺少打包时间和更新描述，在这里添加
// 并添加下载路径
// 我要把包通过github action上传到 https://gitee.com/qinmen/GithubServer/WanAndroid 方便下载
fun addDownloadUrl(updateDescription: String) {
    // val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    // val targetFile = file("app/build/outputs/apk/release/output.json")
    // val packageJson = com.google.gson.JsonParser().parse(targetFile.readText()).asJsonObject
    // val gson = com.google.gson.Gson()
    // packageJson.apply {
    //     val apkFileName = get("outputFile").asString
    //     val versionCode = get("versionCode").asString
    //     val downloadUrl = "https://gitee.com/qinmen/GithubServer/raw/master/WanAndroid$apkFileName"
    //     // val backupDownloadUrl = "https://github.com/tainzhi/WanAndroid/releases/download/" + gitVersionTag() + "/" + apkFileName
    //     // val dataMap = [ "versionCode": versionCode,
    //     //                 "description": updateDescription,
    //     //                 "url": downloadUrl,
    //     //                 "url_backup": backupDownloadUrl,
    //     //                 "time": currentTime,
    //     //                 "apkName": apkFileName]
    // }
    // // val dataMap = [ "versionCode": versionCode,
    // //                 "description": updateDescription,
    // //                 "url": downloadUrl,
    // //                 "url_backup": backupDownloadUrl,
    // //                 "time": currentTime,
    // //                 "apkName": apkFileName]
    // // def updateMap = [ "errorCode": 0, "data": dataMap, "errorMsg": ""]
    // val outputJsonPath = "app/build/outputs/apk/release/update.json"
    // // (File(outputJsonPath)).write(new JsonOutput().toJson(updateMap))
}
