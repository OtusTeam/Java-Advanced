plugins {
    id("java")
    id("org.springframework.boot") version "3.2.4"
}
apply(plugin = "io.spring.dependency-management")

group = "com.otus.java.advanced"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation(group = "com.google.code.gson", name = "gson", version = "2.7")
    tasks.withType<JavaCompile> {
        options.compilerArgs.add("--enable-preview")
    }
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("--enable-preview")
}