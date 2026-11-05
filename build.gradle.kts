plugins {
    kotlin("jvm") version "2.3.10"
    id("com.gradleup.shadow") version "9.2.2"
}

group = "io.wesner.robert.cb1060.robertsbetacommands"
version = "0.3.0"

repositories {
    mavenCentral()
    maven("https://maven.robert.wesner.io/repository/maven-public/")
    maven("https://maven.robert.wesner.io/repository/johnymuffin-maven-public/")
}

dependencies {
    compileOnly("com.legacyminecraft.poseidon:poseidon-craftbukkit:1.+")
    compileOnly("org.betamc:kotlin-libs:1.0.0-kt2.3.0")
    implementation("com.google.code.gson:gson:2.15.0")
}

kotlin {
    jvmToolchain(8)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand(project.properties)
    }
}

tasks.shadowJar {
    archiveClassifier.set("")

    dependencies {
        include(dependency("com.google.code.gson:gson:.*"))
    }

    relocate(
        "com.google.gson",
        "io.wesner.robert.cb1060.robertsbetacommands.libs.gson"
    )
}

tasks.build {
    dependsOn(tasks.shadowJar)
}
