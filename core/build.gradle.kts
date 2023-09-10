import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

val properties = gradleLocalProperties(rootDir)

android {
    namespace = "io.astronout.core"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            buildConfigField("String", "BASE_URL", "\"https://api.rawg.io/api/\"")
            buildConfigField("String", "API_KEY", properties.getProperty("API_KEY"))
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            buildConfigField("String", "BASE_URL", "\"https://api.rawg.io/api/\"")
            buildConfigField("String", "API_KEY", properties.getProperty("API_KEY"))
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(libs.core.ktx)
    api(libs.appcompat)
    api(libs.material)
    api(libs.constraintlayout)
    api(libs.recyclerview)
    api(libs.swiperefreshlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.android.test.junit4)
    androidTestImplementation(libs.espresso)

    api(libs.bundles.navigation)

    api(libs.activity.ktx)
    api(libs.fragment.ktx)

    api(libs.bundles.room)
    kapt(libs.room.compiler)

    api(libs.bundles.lifecycle)

    api(libs.bundles.networking)

    api(libs.bundles.moshi)
    kapt(libs.moshi.codegen)

    api(libs.coroutines)
    api(libs.coroutines.android)
    testImplementation(libs.coroutines.test)

    api(libs.glide)
    kapt(libs.glide.compiler)

    api(libs.timber)

    api(libs.lottie.animation)

    api(libs.dagger.hilt)
    kapt(libs.dagger.hilt.android.compiler)
    kapt(libs.dagger.hilt.compiler)

    api(libs.multistateview)

    api(libs.shimmer)

    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)

    api(libs.paging)

    debugImplementation(libs.leak.canary)
    api(libs.sql.clipper)
    api(libs.sqlite.ktx)
}