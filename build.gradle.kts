import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.0"
    id("org.jetbrains.compose") version "0.1.0-m1-build62"
    kotlin("plugin.serialization") version "1.4.10"
    application
}

group = "me.subash"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
    implementation(compose.desktop.all)
    implementation(compose.materialIconsExtended)

    implementation("com.google.code.gson:gson:2.8.6")

//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation("io.ktor:ktor-client-core:1.4.0")
    implementation("io.ktor:ktor-client-okhttp:1.4.0")
    implementation("io.ktor:ktor-client-json:1.4.0")

    implementation("io.ktor:ktor-client-serialization:1.4.0")

    implementation ("io.ktor:ktor-client-logging-jvm:1.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.0")

    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.google.code.gson:gson:2.8.6")

    implementation(project(":model"))


}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "MainKt"
}