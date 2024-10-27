// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") } // Add this line for JitPack
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.5.2") // Ensure this matches your AGP version
    }
}