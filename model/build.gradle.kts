plugins {
    java
    kotlin("jvm")
    kotlin("plugin.serialization")

}

group = "me.subash"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-client-serialization:1.4.0")

    testCompile("junit", "junit", "4.12")
}
