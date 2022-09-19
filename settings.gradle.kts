rootProject.name = "test-accessors"

includeBuild("build-logic")
dependencyResolutionManagement {
  repositories {
    mavenCentral()
    google()
  }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")