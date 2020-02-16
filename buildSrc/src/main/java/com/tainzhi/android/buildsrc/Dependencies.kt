package com.tainzhi.android.buildsrc

object Versions {
    const val ktlint = "0.35.0"
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:4.0.0-alpha08"

    const val junit = "junit:junit:4.13"

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.1"

    object Google {
        const val material = "com.google.android.material:material:1.2.0-alpha03"
        const val firebaseCore = "com.google.firebase:firebase-core:17.2.2"
        const val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.10.1"
        const val gmsGoogleServices = "com.google.gms:google-services:4.3.3"
        const val fabricPlugin = "io.fabric.tools:gradle:1.31.2"

        const val openSourceLicensesPlugin = "com.google.android.gms:oss-licenses-plugin:0.10.1"
        const val openSourceLicensesLibrary = "com.google.android.gms:play-services-oss-licenses:17.0.0"
    }

    object Kotlin {
        private const val version = "1.3.61"

        const val stdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.3.2"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val rx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.1.0"
        const val browser = "androidx.browser:browser:1.0.0"
        const val coreKtx = "androidx.core:core-ktx:1.2.0-rc01"
        const val collection = "androidx.collection:collection-ktx:1.1.0"
        const val palette = "androidx.palette:palette:1.0.0"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
        const val emoji = "androidx.emoji:emoji:1.0.0"
        const val swiperefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0-alpha03"
        const val viewPager2 = "androidx.viewpager2:viewpager2:1.0.0"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta4"

        const val archCoreTesting = "androidx.arch.core:core-testing:2.1.0"

        const val preference = "androidx.preference:preference:1.1.0"

        object Navigation {
            private const val version = "2.3.0-alpha01"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
            const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        object Fragment {
            private const val version = "1.2.0-rc01"
            const val fragment = "androidx.fragment:fragment:$version"
            const val fragmentKtx = "androidx.fragment:fragment-ktx:$version"
        }

        object Test {
            private const val version = "1.2.0"
            const val core = "androidx.test:core:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        }

        object Paging {
            private const val version = "2.1.1"
            const val common = "androidx.paging:paging-common-ktx:$version"
            const val runtime = "androidx.paging:paging-runtime-ktx:$version"
        }

        object Lifecycle {
            private const val version = "2.2.0"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
        }

        object Room {
            private const val version = "2.2.3"
            const val common = "androidx.room:room-common:$version"
            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val testing = "androidx.room:room-testing:$version"
        }

        object Work {
            private const val version = "2.3.0"
            const val runtimeKtx = "androidx.work:work-runtime-ktx:$version"
        }
    }

    object RxJava {
        const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.11"
        const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    }

    object Dagger {
        private const val version = "2.25.4"
        const val dagger = "com.google.dagger:dagger:$version"
        const val androidSupport = "com.google.dagger:dagger-android-support:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"
        const val androidProcessor = "com.google.dagger:dagger-android-processor:$version"
    }

    object Retrofit {
        private const val version = "2.7.1"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val retrofit_rxjava_adapter = "com.squareup.retrofit2:adapter-rxjava2:$version"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$version"
    }

    object OkHttp {
        private const val version = "4.3.1"
        const val okhttp = "com.squareup.okhttp3:okhttp:$version"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Koin {
        private const val version = "2.0.1"
        const val android = "org.koin:koin-android:$version"
        // or Koin for Lifecycle scoping
        const val scope = "org.koin:koin-androidx-scope:2.0.1"
        // or Koin for Android Architecture ViewModel
        const val viewmodel = "org.koin:koin-androidx-viewmodel:2.0.1"
    }

    object Glide {
        private const val version = "4.10.0"
        const val glide = "com.github.bumptech.glide:glide:4.10.0"
        const val compiler = "com.github.bumptech.glide:compiler:4.10.0"
    }

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    const val cookietar = "com.github.franmontiel:PersistentCookieJar:v1.0.1"

    const val baseRecyclerViewAdapterHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.50"

    const val youthBanner = "com.youth.banner:banner:1.4.10"

    const val tencentTbssdk = "com.tencent.tbs.tbssdk:sdk:43697"

    const val vertialTabLayout = "q.rorbin:VerticalTabLayout:1.2.5"

    const val flowlayout = "com.hyman:flowlayout-lib:1.1.2"

    const val licenseDialog = "de.psdev.licensesdialog:licensesdialog:2.1.0"

    const val materialDialog = "com.afollestad.material-dialogs:core:3.1.1"
}
