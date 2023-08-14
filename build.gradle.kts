buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
    }
}

plugins {
    id("com.google.dagger.hilt.android").version("2.44").apply(false)
    id("androidx.navigation.safeargs").version("2.4.2").apply(false)
    id("org.jetbrains.kotlin.android").version("1.8.0").apply(false)

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}