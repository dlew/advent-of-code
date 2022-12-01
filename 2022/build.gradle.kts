plugins {
    kotlin("jvm") version "1.7.22"
}

repositories {
    mavenCentral()
}
dependencies {
    implementation("junit:junit:4.13.2")
    implementation("com.google.guava:guava:31.1-jre")
}

tasks {
    wrapper {
        gradleVersion = "7.6"
    }
}
