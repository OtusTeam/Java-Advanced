plugins {
    id("java")
    id("org.springframework.boot") version "3.2.4"
}
apply(plugin = "io.spring.dependency-management")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation(group = "com.google.code.gson", name = "gson", version = "2.9.1")
    tasks.withType<JavaCompile> {
        options.compilerArgs.add("--enable-preview")
    }
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("--enable-preview")
}