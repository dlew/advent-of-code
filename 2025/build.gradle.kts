plugins {
  kotlin("jvm") version "2.2.21"
}

group = "net.danlew"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation("com.google.guava:guava:33.5.0-jre")
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain(24)
}