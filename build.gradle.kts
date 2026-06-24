plugins {
    kotlin("jvm") version "2.3.10"
    kotlin("plugin.serialization") version "2.3.10"
    id("io.ktor.plugin") version "3.4.0"
}

group = "com.godker"
version = "0.0.1"

application {
    mainClass = "com.godker.server.ApplicationKt"
}

kotlin {
    jvmToolchain(25)
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:1.5.34")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0")
    implementation("org.postgresql:postgresql:42.7.11")
    implementation("com.zaxxer:HikariCP:7.0.2")

    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.4.0")
}
