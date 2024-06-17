plugins {
    id("java")
    id("me.champeau.jmh") version "0.7.2"
}

group = "com.otus.java.advanced"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    jmh("com.google.guava:guava:33.0.0-jre")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

jmh {
    excludes.addAll("*")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}