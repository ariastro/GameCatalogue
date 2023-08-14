plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

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
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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

    implementation(libs.datastore)
    implementation(libs.datastore.core)

    api(libs.dots.indicator)
    api(libs.cpp)

    coreLibraryDesugaring(libs.desugar)
    api(libs.calendar)
    api(libs.progress.view)

    api(libs.sliding.up.panel)

    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)

    api(libs.paging)

    api(libs.power.spinner)
}