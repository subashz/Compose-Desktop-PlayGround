//import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
//    id("org.jetbrains.compose") version "0.1.0-m1-build62"
    id("com.android.library")
    id("kotlin-android-extensions")
    kotlin("plugin.serialization") version "1.4.10"

}

group = "me.subash"
version = "1.0-SNAPSHOT"

repositories {
    google()
}

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
//                api(compose.runtime)
//                api(compose.foundation)
//                api(compose.material)

                // KTOR
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
                implementation("io.ktor:ktor-client-core:1.4.0")
                implementation("io.ktor:ktor-client-serialization:1.4.0")
                implementation("io.ktor:ktor-client-okhttp:1.4.0")
                implementation("io.ktor:ktor-client-json:1.4.0")
                implementation ("io.ktor:ktor-client-logging-jvm:1.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.0")



            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.2.0")
                api("androidx.core:core-ktx:1.3.1")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13")
            }
        }
        val desktopMain by getting
        val desktopTest by getting
    }
}

android {
    compileSdkVersion(29)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(29)
    }
}