plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    alias(libs.plugins.ksp)
    alias(libs.plugins.google.dagger.hilt)

    //Existing plugins
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.dflch.dashboardemc"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dflch.dashboardemc"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Dagger - Hilt
    implementation(libs.google.dagger.hilt)

    ksp(libs.google.dagger.hilt.compiler)
    kspAndroidTest(libs.google.dagger.hilt.compiler)

    // Coil
    implementation(libs.coil)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.compose)

    // Compose Charts
    implementation (libs.compose.charts)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation (libs.logging.interceptor)

    implementation (libs.androidx.runtime.livedata)

    implementation(libs.gson)

    implementation(libs.kotlinx.datetime)

    implementation(libs.androidx.material.icons.extended)

}


