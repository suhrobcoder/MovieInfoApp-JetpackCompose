import Dependencies.AndroidX.Activity.activityCompose

plugins {
    id(Dependencies.Plugins.application)
    id(Dependencies.Plugins.kotlinAndroid)
    id(Dependencies.Plugins.kotlinKapt)
    id(Dependencies.Plugins.kotlinxSerialization)
    id("kotlin-parcelize")
}

android {
    compileSdk = Sdk.compileSdkVersion

    defaultConfig {
        applicationId = Sdk.applicationId
        minSdk = Sdk.minSdkVersion
        targetSdk = Sdk.targetSdkVersion
        versionCode = Sdk.versionCode
        versionName = Sdk.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        kotlinCompilerExtensionVersion = Versions.composeCompilerVersion
    }
}

dependencies {

    implementation(Dependencies.Kotlin.kotlinStdLib)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.AppCompat.appCompat)

    //Compose
    implementation(Dependencies.Compose.uiToolingPreview)
    implementation(Dependencies.Compose.material3)
    implementation(Dependencies.Compose.ui)

    implementation(Dependencies.AndroidX.Activity.activityCompose)

    implementation(Dependencies.Decompose.decompose)
    implementation(Dependencies.Decompose.composeExt)

    implementation(Dependencies.Compose.composeShimmer)

    implementation(Dependencies.Coil.coilCompose)

    implementation(Dependencies.Room.roomRuntime)
    kapt(Dependencies.Room.roomCompiler)
    implementation(Dependencies.Room.roomKtx)

    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.serializationConverter)

    implementation(Dependencies.Kotlin.kotlinSerializationJson)

    implementation(Dependencies.AndroidX.Datastore.preferences)

    testImplementation(Dependencies.Test.Junit.junit)
    testImplementation(Dependencies.Test.androidTestCore)
    testImplementation(Dependencies.Test.archCoreTesting)
    testImplementation(Dependencies.Test.coroutinesTest)
    testImplementation(Dependencies.Test.googleTruth)
    testImplementation(Dependencies.Test.mockitoCore)

    androidTestImplementation(Dependencies.Test.Junit.junit)
    androidTestImplementation(Dependencies.Test.coroutinesTest)
    androidTestImplementation(Dependencies.Test.archCoreTesting)
    androidTestImplementation(Dependencies.Test.googleTruth)
    androidTestImplementation(Dependencies.Test.Junit.junitExt)
    androidTestImplementation(Dependencies.Test.mockitoCore)
    androidTestImplementation(Dependencies.Test.dexmakerMockito)

    debugImplementation(Dependencies.Compose.uiTooling)
    debugImplementation(Dependencies.Compose.uiTestManifest)
}