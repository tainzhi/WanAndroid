
kotlin版本的AOP

## 功能特点

## 用法
Project的build.gradle添加
```gradle
buildScrit {
    dependencies {
        classpath Libs.Kotlin.gradlePlugin
        classpath Libs.AspectJX.hujiangAspectJX
    }
}
```
app的build.gradle添加

```
apply plugin: 'com.android.application'

apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
apply plugin: 'android-aspectjx'

android {
  aspectjx {
        exclude 'org.jetbrains.kotlin'
        exclude 'org.jetbrains.kotlinx'
        exclude 'com.android.support'
    }
}
```
