// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.0.0")
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Gradle.gradlePlugin)
        classpath(Dependencies.Kotlin.kotlinGradlePlugin)
        classpath(Dependencies.Kotlin.kotlinSerialization)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}