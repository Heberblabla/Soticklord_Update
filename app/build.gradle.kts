plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.waos.soticklord"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.waos.soticklord"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Networking (Retrofit + OkHttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)

    // PostgreSQL (solo si conectas desde servidor, no desde Android)
    // ⚠️ Nota: esto no funciona directamente en Android, usa Supabase REST mejor
    // implementation("org.postgresql:postgresql:42.7.3")

    // Reflexión Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.0")

    // 🔥 Supabase SDK
    implementation("io.github.jan-tennert.supabase:supabase-kt:2.3.1")
    implementation("io.github.jan-tennert.supabase:gotrue-kt:2.3.1") // Auth
    implementation("io.github.jan-tennert.supabase:postgrest-kt:2.3.1") // Base de datos
}
