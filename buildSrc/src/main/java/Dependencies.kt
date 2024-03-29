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
        const val composeShimmer = "com.valentinilk.shimmer:compose-shimmer:${Versions.composeShimmerVersion}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeVersion}"
        const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.composeVersion}"
    }
    object Decompose {
        const val decompose = "com.arkivanov.decompose:decompose:${Versions.decomposeVersion}"
        const val composeExt = "com.arkivanov.decompose:extensions-compose-jetpack:${Versions.decomposeVersion}"
    }
    object Gradle {
        const val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePluginVersion}"
    }
    object Coil {
        const val coilCompose = "io.coil-kt:coil-compose:${Versions.coilVersion}"
    }
    object Room {
        const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"
    }

    object Ktor {
        const val core = "io.ktor:ktor-client-core:${Versions.ktorVersion}"
        const val androidClient = "io.ktor:ktor-client-android:${Versions.ktorVersion}"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktorVersion}"
        const val serializationJson = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktorVersion}"
    }

    object Plugins {
        const val application = "com.android.application"
        const val kotlinAndroid = "kotlin-android"
        const val ksp = "com.google.devtools.ksp"
        const val kotlinxSerialization = "kotlinx-serialization"
        const val kotlinParcelize = "kotlin-parcelize"
        const val ktLint = "org.jlleitschuh.gradle.ktlint"
        const val ktorfit = "de.jensklingenberg.ktorfit"
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
    }
}