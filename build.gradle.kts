import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.1.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "io.wesner.robert.cb1060.robertsbetacommands"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.2.20")
    implementation(files("project-poseidon-1.1.10.jar"))
}

tasks.test {
    useJUnitPlatform()
}
tasks.shadowJar {
    mergeServiceFiles()
}
kotlin {
    jvmToolchain(8)
}

