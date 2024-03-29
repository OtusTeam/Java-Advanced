plugins {
    id("java")
}

group = "com.otus.java.advanced"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}