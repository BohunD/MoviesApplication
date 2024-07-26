plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.apps.moviesapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.apps.moviesapplication"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField ("String", "TMDB_ACCESS_TOKEN", "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxOWY2YzIwYmYyNDIwMGIyY2JhOTkyYjBjODJmMWQzYSIsIm5iZiI6MTcyMTc0MDI4Ny4yNzE5Miwic3ViIjoiNjY5ZmFhOWMyYmFkYTBlN2E4NmU3ZjJhIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.Xzj9GsFlzl1dd_53EsbVde6elW1MMvV5pDtktgyQaO0\"")
        buildConfigField ("String", "TMDB_API_KEY", "\"19f6c20bf24200b2cba992b0c82f1d3a\"")
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
        buildConfig = true

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
    implementation(libs.androidx.navigation.testing)
    implementation(libs.androidx.ui.test.junit4.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation (libs.navigation.compose)
    implementation (libs.androidx.hilt.navigation.fragment)

    testImplementation (libs.mockito.core)
    testImplementation (libs.mockito.inline)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.turbine)


    testImplementation (libs.androidx.core.testing)
    testImplementation (libs.mockwebserver)
    testImplementation (libs.androidx.paging.testing)

    implementation (libs.moshi)
    implementation(libs.moshi.kotlin)
    kapt (libs.moshi.kotlin.codegen)
    implementation (libs.converter.moshi)
    testImplementation (libs.robolectric)





    implementation(libs.hilt.android)
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    kapt("com.google.dagger:hilt-android-compiler:2.50")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation ("androidx.paging:paging-runtime:3.3.0")
    implementation ("androidx.paging:paging-compose:3.3.0")

    implementation("io.coil-kt:coil-compose:2.7.0")

}