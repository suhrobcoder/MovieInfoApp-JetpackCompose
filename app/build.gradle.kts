plugins {
    id(Dependencies.Plugins.application)
    id(Dependencies.Plugins.kotlinAndroid)
    id(Dependencies.Plugins.ksp) version(Versions.kspVersion)
    id(Dependencies.Plugins.kotlinxSerialization)
    id(Dependencies.Plugins.kotlinParcelize)
    id(Dependencies.Plugins.ktLint) version(Versions.ktlintVersion)
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompilerVersion
    }
}

ktlint {
    android.set(true)
    ignoreFailures.set(false)
    disabledRules.set(listOf("final-newline", "no-wildcard-imports"))
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.SARIF)
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(Dependencies.Kotlin.kotlinStdLib)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.AppCompat.appCompat)

    // Compose
    implementation(Dependencies.Compose.uiToolingPreview)
    implementation(Dependencies.Compose.material3)
    implementation(Dependencies.Compose.ui)

    implementation(Dependencies.AndroidX.Activity.activityCompose)

    implementation(Dependencies.Decompose.decompose)
    implementation(Dependencies.Decompose.composeExt)

    implementation(Dependencies.Compose.composeShimmer)

    implementation(Dependencies.Coil.coilCompose)

    implementation(Dependencies.Room.roomRuntime)
    ksp(Dependencies.Room.roomCompiler)
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