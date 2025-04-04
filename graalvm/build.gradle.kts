plugins {
    id("java")
    id("org.springframework.boot") version "3.3.4"
    id("org.graalvm.buildtools.native") version "0.10.3" //graalvm
}

group = "com.otus.java.advanced"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

apply(plugin = "io.spring.dependency-management")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.projectlombok:lombok")

    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.bootJar {
    manifest {
        attributes("Start-Class" to "com.otus.java.advanced.Main")
    }
}

graalvmNative {
    testSupport = false
    metadataRepository {
        enabled.set(true)
    }

    binaries.all {
//        javaLauncher.set(javaToolchains.launcherFor {
//            languageVersion.set(JavaLanguageVersion.of(21))
//            vendor.set(JvmVendorSpec.GRAAL_VM)
//        })
//        buildArgs.add("-H:IncludeResources")
    }
}