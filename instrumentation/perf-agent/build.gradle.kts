plugins {
    id("java")
}

group = "com.otus.java.advanced"
version = "1.0.0-SNAPSHOT"

var agentClass = "com.otus.java.advanced.instrumentation.agent.PerformanceAgent"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("ch.qos.logback:logback-classic:1.2.7")

    implementation("org.javassist:javassist:3.27.0-GA")
    implementation("org.apache.commons:commons-lang3:3.14.0")
    implementation("org.owasp.esapi:esapi:2.5.3.1")
    implementation("org.ow2.asm:asm:9.4")
    implementation("org.ow2.asm:asm-util:9.5")
    implementation("org.apache.bcel:bcel:6.8.1")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.jar {
    manifest.attributes["Agent-Class"] = agentClass         /* LESSON delete it and see what happens */
    manifest.attributes["Premain-Class"] = agentClass       /* LESSON delete it and see what happens */
    manifest.attributes["Can-Redefine-Classes"] = true      /* LESSON delete it and see what happens */
    manifest.attributes["Can-Retransform-Classes"] = true   /* LESSON delete it and see what happens */
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree) // OR .map { zipTree(it) }
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.test {
    useJUnitPlatform()
}