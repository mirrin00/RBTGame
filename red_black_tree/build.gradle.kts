import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    id("org.openjfx.javafxplugin") version "0.0.10"
    application
}
group = "com.github"
version = "1.2"

val tornadofx_version: String by rootProject

repositories {
    mavenCentral()
}

javafx {
    version = "12"
    modules("javafx.controls", "javafx.fxml")
    configuration = "implementation"
}

application {
    mainClassName = "summer.practice.infty.MainKt"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("no.tornado:tornadofx:$tornadofx_version")
    testImplementation(kotlin("test-junit"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    jar {
        manifest {
            attributes["Main-Class"] = "summer.practice.infty.MainKt"
        }
        from(sourceSets.main.get().output)
        dependsOn(configurations.runtimeClasspath)
        from({
            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
        })
    }
}