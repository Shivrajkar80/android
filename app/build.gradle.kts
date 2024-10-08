plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.e_commerce"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.e_commerce"
        minSdk = 24
        targetSdk = 34
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
    implementation(platform(libs.androidx.compose.bom.v20240600))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.ui.android)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.play.services.analytics.impl)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.billing.ktx)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.firebase.storage.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom.v20240600))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)


    // Retrofit for networking
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // Coil for image loading
    implementation (libs.coil.compose.v260)

    implementation (libs.androidx.navigation.compose)
    implementation (libs.material3)

        implementation (libs.androidx.foundation) // Check for the latest version


        implementation (libs.material3)
        implementation ("androidx.compose.material3:material3-window-size-class:1.2.1")

    implementation (libs.ui)

    // Accompanist permissions
    implementation (libs.accompanist.permissions)

    // Coil for image loading
    implementation (libs.coil.compose.v260)

        // Firebase libraries
    implementation (platform(libs.firebase.bom.v3020))
    implementation (libs.firebase.auth)
    implementation (libs.firebase.firestore)
    implementation (libs.firebase.storage)

    // Other Jetpack Compose dependencies
    implementation (libs.androidx.material3.v110)
    implementation (libs.androidx.activity.compose.v151)

    implementation (libs.androidx.runtime.livedata)





}


