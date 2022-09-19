plugins {
  `embedded-kotlin`
}



group = "com.example"

repositories {
  mavenCentral()
}

tasks.named("compileKotlin").configure {
  doLast {
    println("Classes:")

    println(this::class.java.classLoader.urls())
  }
}

fun ClassLoader.urls(): List<String> {
  println("$this")
  if (this is org.gradle.internal.classloader.VisitableURLClassLoader) {
    return urLs.map { it.toString() } + this.parent?.urls().orEmpty()
  }

  return this.parent?.urls().orEmpty()
}

