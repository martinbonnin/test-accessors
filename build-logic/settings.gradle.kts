rootProject.name = "build-logic"

dependencyResolutionManagement {
  repositories {
    mavenCentral()
    google()
  }

  versionCatalogs {
    create("libs") {
      from(files("../gradle/libs.versions.toml"))
    }
  }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")