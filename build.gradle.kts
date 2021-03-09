// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Gradle.gradlePlugin)
        classpath(Dependencies.Kotlin.kotlinGradlePlugin)
        classpath(Dependencies.Kotlin.kotlinSerialization)
        classpath(Dependencies.DaggerHilt.hiltPlugin)
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