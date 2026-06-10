plugins {
    id("java")
}

group = "forth.practice"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":processor"))
    annotationProcessor(project(":processor"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<JavaExec> {
    systemProperty("file.encoding", "UTF-8")
}