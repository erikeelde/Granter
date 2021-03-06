// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.3.2")
        classpath(embeddedKotlin("gradle-plugin"))
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.21.0"
    id("digital.wup.android-maven-publish") version "3.6.2"
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
