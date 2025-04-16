import com.github.jengelman.gradle.plugins.shadow.transformers.ServiceFileTransformer
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.gradle.api.file.DuplicatesStrategy

plugins {
    kotlin("jvm") version "2.1.0"
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1" apply true
    id("maven-publish")
}

group = "com.zorbeytorunoglu"
version = "0.1.1"

repositories {
    mavenCentral()
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")
    implementation("org.jetbrains.kotlin:kotlin-serialization:2.0.21")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.7.3")
    implementation("org.apache.maven:maven-artifact:4.0.0-rc-3")
    compileOnly("org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT")
}

tasks {
    compileKotlin {
        compilerOptions.languageVersion.set(KotlinVersion.KOTLIN_2_1)
        compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
    }
}

tasks.shadowJar {
    archiveClassifier.set("")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "kLib"
            version = project.version.toString()

            from(components["java"])
        }
    }
}
