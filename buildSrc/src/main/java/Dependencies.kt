object Dependencies {
    object Kotlin {
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
        const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlinVersion}"
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
        const val kotlinSerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1"
    }
    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
        object AppCompat {
            const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
        }
        object Datastore {
            const val preferences = "androidx.datastore:datastore-preferences:${Versions.datastoreVersion}"
        }
        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:${Versions.activityComposeVersion}"
        }
    }
    object Compose {
        const val ui = "androidx.compose.ui:ui:${Versions.composeVersion}"
        const val material3 = "androidx.compose.material3:material3:${Versions.composeMaterial3Version}"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}"
        const val navCompose = "androidx.navigation:navigation-compose:${Versions.navComposeVersion}"
        const val composeShimmer = "com.valentinilk.shimmer:compose-shimmer:${Versions.composeShimmerVersion}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeVersion}"
        const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.composeVersion}"
    }
    object Lifecycle {
        const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
        const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleVersion}"
    }
    object Gradle {
        const val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePluginVersion}"
    }
    object DaggerHilt {
        const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltAndroidVersion}"
        const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltAndroidVersion}"
        const val androidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroidVersion}"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltVersion}"
        const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:${Versions.hiltVersion}"
    }
    object Coil {
        const val coilCompose = "io.coil-kt:coil-compose:${Versions.coilVersion}"
    }
    object Room {
        const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"
    }
    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
        const val serializationConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.serializationConverterVersion}"
    }

    object Plugins {
        const val application = "com.android.application"
        const val kotlinAndroid = "kotlin-android"
        const val kotlinKapt = "kotlin-kapt"
        const val kotlinxSerialization = "kotlinx-serialization"
        const val daggerHilt = "dagger.hilt.android.plugin"
    }

    object Test {
        object Junit {
            const val junit = "junit:junit:${Versions.junitVersion}"
            const val junitExt = "androidx.test.ext:junit:${Versions.junitExtVersion}"
        }
        const val androidTestCore = "androidx.test:core:${Versions.testCoreVersion}"
        const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archCoreTestingVersion}"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTestVersion}"
        const val googleTruth = "com.google.truth:truth:${Versions.googleTruthVersion}"
        const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCoreVersion}"
        const val dexmakerMockito = "com.linkedin.dexmaker:dexmaker-mockito:${Versions.dexmakerMockitoVersion}"
        const val hiltAndroidTesting = "com.google.dagger:hilt-android-testing:${Versions.hiltTestingVersion}"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-compiler:${Versions.hiltTestingVersion}"
    }
}