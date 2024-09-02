plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.satdroid.wkap"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.satdroid.wkap"
        minSdk = 29
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {


    implementation (libs.retrofit)
    implementation (libs.convertergson)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)



    implementation ("io.reactivex.rxjava2:rxjava:2.1.9")
    implementation("io.reactivex.rxjava2:rxandroid:2.0.1")

}