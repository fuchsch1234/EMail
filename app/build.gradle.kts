import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}


android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "de.fuchsch.email"
        minSdkVersion(23)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    val koinVersion = "2.0.1"

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${KotlinCompilerVersion.VERSION}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${KotlinCompilerVersion.VERSION}")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.navigation:navigation-fragment:2.1.0")
    implementation("androidx.navigation:navigation-ui:2.1.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.1.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.1.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.1.0")

    // Room
    implementation("androidx.room:room-runtime:2.2.1")
    kapt("androidx.room:room-compiler:2.2.1")
    implementation("androidx.room:room-ktx:2.2.1")
    testImplementation("androidx.room:room-testing:2.2.1")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.1.0")
    kapt("androidx.lifecycle:lifecycle-compiler:2.1.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.1.0")

    // Coroutines
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.0")

    // Koin for Android
    implementation("org.koin:koin-androidx-scope:$koinVersion")
    // Koin AndroidX ViewModel features
    implementation("org.koin:koin-androidx-viewmodel:$koinVersion")

    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
