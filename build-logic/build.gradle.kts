import org.jetbrains.kotlin.com.intellij.util.lang.UrlClassLoader
import org.jetbrains.kotlin.samWithReceiver.gradle.SamWithReceiverExtension
import org.jetbrains.kotlin.samWithReceiver.gradle.SamWithReceiverGradleSubplugin

plugins {
  `embedded-kotlin`
  id("java-gradle-plugin")
}

plugins.apply(SamWithReceiverGradleSubplugin::class.java)
extensions.configure(SamWithReceiverExtension::class.java) {
  annotations(HasImplicitReceiver::class.qualifiedName!!)
}

group = "com.example"

repositories {
  mavenCentral()
}
dependencies {
  implementation(libs.gr8)
  implementation(libs.okio)
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

