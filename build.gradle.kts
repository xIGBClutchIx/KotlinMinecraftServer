plugins {
    kotlin("jvm") version "1.3.61"
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
    implementation("com.beust:klaxon:5.0.1")
    implementation("com.github.ajalt:mordant:1.2.1")
}

application {
    mainClassName = "me.clutchy.server.AppKt"
}
