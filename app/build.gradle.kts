plugins {
    id(Dependencies.Plugins.application)
    id(Dependencies.Plugins.kotlinAndroid)
    id(Dependencies.Plugins.kotlinKapt)
    id(Dependencies.Plugins.kotlinxSerialization)
    id(Dependencies.Plugins.daggerHilt)
}

android {
    compileSdkVersion(Sdk.compileSdkVersion)

    defaultConfig {
        applicationId = Sdk.applicationId
        minSdkVersion(Sdk.minSdkVersion)
        targetSdkVersion(Sdk.minSdkVersion)
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
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeVersion
        kotlinCompilerVersion = Versions.kotlinVersion
    }
}

dependencies {

    implementation(Dependencies.Kotlin.kotlinStdLib)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.AppCompat.appCompat)
    implementation(Dependencies.Material.material)

    //Compose
    implementation(Dependencies.Compose.uiTooling)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.ui)

    implementation(Dependencies.Compose.navCompose)

    implementation(Dependencies.Lifecycle.viewmodelKtx)
    implementation(Dependencies.Lifecycle.commonJava8)
    implementation(Dependencies.Lifecycle.extensions)

    implementation(Dependencies.DaggerHilt.hiltAndroid)
    kapt(Dependencies.DaggerHilt.androidCompiler)
    implementation(Dependencies.DaggerHilt.lifecycleViewmodel)
    kapt(Dependencies.DaggerHilt.hiltCompiler)
    implementation(Dependencies.DaggerHilt.hiltNavigation)

    implementation(Dependencies.Glide.glide)
    kapt(Dependencies.Glide.glideCompiler)

    implementation(Dependencies.Room.roomRuntime)
    implementation(Dependencies.Room.roomCompiler)
    implementation(Dependencies.Room.roomKtx)

    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.serializationConverter)

    implementation(Dependencies.Kotlin.kotlinSerializationJson)

    implementation(Dependencies.AndroidX.Datastore.preferences)

    implementation(Dependencies.accompanistInsets)

    testImplementation(Dependencies.Test.Junit.junit)
    testImplementation(Dependencies.Test.androidTestCore)
    testImplementation(Dependencies.Test.hamcrestVersion)
    testImplementation(Dependencies.Test.archCoreTesting)
    testImplementation(Dependencies.Test.roboelectric)
    testImplementation(Dependencies.Test.coroutinesTest)
    testImplementation(Dependencies.Test.googleTruth)
    testImplementation(Dependencies.Test.mockitoCore)

    androidTestImplementation(Dependencies.Test.Junit.junit)
    //TODO dexmaker
    androidTestImplementation(Dependencies.Test.coroutinesTest)
    androidTestImplementation(Dependencies.Test.archCoreTesting)
    androidTestImplementation(Dependencies.Test.googleTruth)
    androidTestImplementation(Dependencies.Test.Junit.junitExt)
    androidTestImplementation(Dependencies.Test.espressoCore)
    androidTestImplementation(Dependencies.Test.mockitoCore)
    androidTestImplementation(Dependencies.Test.dexmakerMockito)
    androidTestImplementation(Dependencies.Test.hiltAndroidTesting)
    kaptAndroidTest(Dependencies.Test.hiltAndroidCompiler)
}