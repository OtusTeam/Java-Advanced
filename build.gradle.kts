plugins {
    id("java")
}

val currentJavaVersion = JavaVersion.VERSION_21

java {
    sourceCompatibility = currentJavaVersion
    targetCompatibility = currentJavaVersion
}

allprojects {
    group = "com.otus.java.advanced"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}
