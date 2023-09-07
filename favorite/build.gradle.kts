plugins {
    alias(libs.plugins.com.android.dynamic.feature)
    alias(libs.plugins.kotlin.android)
}
android {
    namespace = "io.astronout.favorite"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    implementation(project(":app"))
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.android.test.junit4)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(libs.annotation)
}