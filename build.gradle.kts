import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // Serialization para JSON
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")

    // Serialization para XML
    implementation("io.github.pdvrieze.xmlutil:core-jvm:0.84.3")
    implementation("io.github.pdvrieze.xmlutil:serialization-jvm:0.84.3")

    // DataFrames de Kotlin
    implementation("org.jetbrains.kotlinx:dataframe:0.8.1")

    //DSL para HTML: https://ktor.io/docs/html-dsl.html , https://kotlinlang.org/docs/typesafe-html-dsl.html
    //implementation("io.ktor:ktor-server-html-builder:2.1.2")
    //implementation("io.ktor:ktor-server-netty:2.1.2")
    //implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.8.0")

    // LetsPlots en Kotlin
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin:4.1.0")
    implementation("org.jetbrains.lets-plot:lets-plot-image-export:2.5.0")

    // Loggers para Kotlin
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.0")
    implementation("ch.qos.logback:logback-classic:1.4.3")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}