plugins {
    id("java")
    id("org.springframework.boot") version "3.2.4"
    kotlin("jvm") version "2.0.0"
}

apply(plugin = "io.spring.dependency-management")

sourceSets.main {
    java.srcDirs("src/main/java", "src/main/kotlin")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    implementation("org.projectlombok:lombok")
    implementation("javax.persistence:javax.persistence-api:2.2")

    annotationProcessor("org.projectlombok:lombok")

    implementation("javax.persistence:javax.persistence-api:2.2")
    implementation("net.bytebuddy:byte-buddy:1.14.1")
    implementation("net.bytebuddy:byte-buddy-agent:1.14.1")
    implementation("org.ow2.asm:asm:9.4")
    implementation("org.ow2.asm:asm-util:9.4")
    implementation("org.reflections:reflections:0.9.11")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
