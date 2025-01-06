// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.google.dagger.hilt.android") version "2.50" apply false
    kotlin("kapt") version "1.9.0"
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}