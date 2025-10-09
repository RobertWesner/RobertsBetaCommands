import org.yaml.snakeyaml.Yaml
import java.io.FileReader

buildscript {
    dependencies {
        classpath("org.yaml:snakeyaml:2.2")
    }
}

plugins {
    kotlin("jvm") version "2.1.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "io.wesner.robert.cb1060.robertsbetacommands"
version = Yaml().load<Map<String, Any>>(FileReader("src/main/resources/plugin.yml")).get("version").toString()

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.2.20")
    implementation(files("project-poseidon-1.1.10.jar"))
    implementation("org.yaml:snakeyaml:2.2")
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

