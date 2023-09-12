buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
        classpath("com.google.gms:google-services:${libs.versions.google.services.get()}")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.google.dagger.hilt.android").version("2.44").apply(false)
    id("androidx.navigation.safeargs").version("2.4.2").apply(false)
    alias(libs.plugins.com.android.dynamic.feature) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.crashlytics) apply false

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}