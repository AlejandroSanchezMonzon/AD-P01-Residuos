import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
    id("org.jetbrains.dokka") version "1.7.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(
            listOf(
                "compileJava",
                "compileKotlin",
                "processResources"
            )
        ) // We need this for Gradle optimization to work
        archiveClassifier.set("standalone") // Naming the jar
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to application.mainClass)) } // Provided we set it up in the application plugin configuration
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output
        from(contents)
    }
    build {
        dependsOn(fatJar) // Trigger fat jar creation during build
    }
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

    // LetsPlots en Kotlin
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin:4.1.0")
    implementation("org.jetbrains.lets-plot:lets-plot-image-export:2.5.0")

    // Loggers para Kotlin
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.0")
    implementation("ch.qos.logback:logback-classic:1.4.3")
    implementation("ch.qos.logback:logback-core:1.4.3")

    //Dokka para sustituir a JDOC y KDOC
    implementation("org.jetbrains.dokka:kotlin-as-java-plugin:1.7.20")

    // JUnit para tests
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("src/main/kotlin/Main.kt")
}