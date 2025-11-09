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
        versionCode = 9
        versionName = "1.343"
        // 1 = version
        // 0.1 = algun cambio pequeño o modo
        // 0.01 = bugs
        // 0.001 = lectura y balance
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false          // Activa reducción de código
            isShrinkResources = false        // Elimina recursos no usados
            isDebuggable = false            // Asegura que no incluya datos de depuración

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

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.0")

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("io.github.jan-tennert.supabase:supabase-kt:2.3.1")
    implementation("io.github.jan-tennert.supabase:gotrue-kt:2.3.1") // Auth
    implementation("io.github.jan-tennert.supabase:postgrest-kt:2.3.1") // Base de datos

    implementation("com.google.android.gms:play-services-ads:24.7.0")

}
