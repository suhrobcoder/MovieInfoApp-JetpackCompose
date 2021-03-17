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
        const val material = "androidx.compose.material:material:${Versions.composeVersion}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeVersion}"
        const val navCompose = "androidx.navigation:navigation-compose:${Versions.navComposeVersion}"
    }
    object Lifecycle {
        const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
        const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleVersion}"
        const val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}"
    }
    object Material {
        const val material = "com.google.android.material:material:${Versions.materialVersion}"
    }
    object Gradle {
        const val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePluginVersion}"
    }
    object DaggerHilt {
        const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltAndroidVersion}"
        const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltAndroidVersion}"
        const val androidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroidVersion}"
        const val lifecycleViewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltVersion}"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltVersion}"
        const val hiltNavigation = "androidx.hilt:hilt-navigation:${Versions.hiltVersion}"
    }
    object Glide {
        const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
        const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"
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

    const val accompanistInsets = "dev.chrisbanes.accompanist:accompanist-insets:${Versions.accompanistVersion}"

    object Test {
        object Junit {
            const val junit = "junit:junit:${Versions.junitVersion}"
            const val junitExt = "androidx.test.ext:junit:${Versions.junitExtVersion}"
        }
        const val androidTestCore = "androidx.test:core:${Versions.testCoreVersion}"
        const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archCoreTestingVersion}"
        const val hamcrestVersion = "org.hamcrest:hamcrest-all:${Versions.hamcrestVersion}"
        const val roboelectric = "org.robolectric:robolectric:${Versions.roboelectricVersion}"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTestVersion}"
        const val googleTruth = "com.google.truth:truth:${Versions.googleTruthVersion}"
        const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCoreVersion}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
        const val dexmakerMockito = "com.linkedin.dexmaker:dexmaker-mockito:${Versions.dexmakerMockitoVersion}"
        const val hiltAndroidTesting = "com.google.dagger:hilt-android-testing:${Versions.hiltTestingVersion}"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltTestingVersion}"
    }
}