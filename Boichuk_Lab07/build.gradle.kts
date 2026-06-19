plugins {
    kotlin("jvm") version "2.0.0"
    id("java")
    id("info.solidsoft.pitest") version "1.15.0"
}

group = "seventh.practice"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.mockito:mockito-core:5.14.2")
    testImplementation("org.mockito:mockito-junit-jupiter:5.14.2")
    testImplementation("org.assertj:assertj-core:3.27.7")
}

tasks.test {
    useJUnitPlatform()
}

pitest {
    junit5PluginVersion.set("1.2.1")

    targetClasses.set(setOf("seventh.practice.PatientRiskEvaluator")) // або "seventh.practice.*"
    targetTests.set(setOf("pit.*"))

    avoidCallsTo.set(setOf("kotlin.jvm.internal", "kotlinx.coroutines"))

    threads.set(Runtime.getRuntime().availableProcessors())

    outputFormats.set(setOf("HTML", "XML"))
}