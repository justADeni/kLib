plugins {
    kotlin("jvm") version "2.0.0-Beta3"
    id("java")
    `maven-publish`
    id ("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "com.zorbeytorunoglu"
version = "0.0.9"

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
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0-Beta3")
    implementation("org.jetbrains.kotlin:kotlin-serialization:2.0.0-Beta3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.6.2")
    implementation("org.apache.maven:maven-artifact:3.8.7")
    compileOnly("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")
}

tasks {

    compileKotlin {
        kotlinOptions.languageVersion = "2.0"
        kotlinOptions.jvmTarget = "17"
    }

}

tasks.shadowJar.configure {
    archiveClassifier.set("")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.zorbeytorunoglu"
            artifactId = "kLib"
            version = "0.0.9"

            from(components["java"])
        }
    }
}