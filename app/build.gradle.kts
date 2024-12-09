plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin) // Necesario para Room
}

android {
    namespace = "com.example.deliverycliente"
    compileSdk = 34 // Cambiar a 34 para mantener consistencia con el targetSdk en el config

    defaultConfig {
        applicationId = "com.example.deliverycliente"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            isMinifyEnabled = true // Habilitar para APK optimizado
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packagingOptions {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding {
        enable = true
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true// Habilitar si planeas usar Data Binding
    }
}

dependencies {
    // AndroidX Core y Material Design
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Navigation Component para manejo de navegación segura
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.2")

    // Retrofit y OkHttp para consumo de APIs
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // Room para manejo de almacenamiento local
    implementation("androidx.room:room-runtime:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")

    // Coroutines para programación asincrónica
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Picasso para manejo de imágenes
    implementation("com.squareup.picasso:picasso:2.8")

    // Google Maps para funcionalidades de mapas
    implementation("com.google.android.gms:play-services-maps:18.1.0")

    // Dependencias de prueba
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
