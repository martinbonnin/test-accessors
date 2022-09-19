buildscript {
  repositories {
    mavenCentral()
    google()
  }
  dependencies {
    classpath("com.example:build-logic")
  }
}

plugins {
  id("org.jetbrains.kotlin.jvm").version("1.7.10")
}

repositories {
  mavenCentral()
}
tasks.register("foo") {
  doLast {
    println(com.example.hello)
  }
}
projects