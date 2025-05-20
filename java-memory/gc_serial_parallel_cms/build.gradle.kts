plugins {
    id("java")
}

var mainClass = "com.otus.gc.StopTheWorldGc"

group = "com.otus.gc"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
}

tasks.jar {
    manifest.attributes["Main-Class"] = mainClass
}

tasks.test {
    useJUnitPlatform()
}