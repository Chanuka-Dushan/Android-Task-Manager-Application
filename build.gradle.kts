// Top-level build file where you can add configuration options common to all sub-projects/modules.


// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
    }

    dependencies {
        val navVersion = "2.7.5"
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
        classpath(libs.com.google.devtools.ksp.gradle.plugin)
    }
}


plugins {
    id ("com.android.application") version "7.3.1" apply false
    id ("com.android.library") version "7.3.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.7.20" apply false

    id ("com.google.dagger.hilt.android") version "2.48.1" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13"

}