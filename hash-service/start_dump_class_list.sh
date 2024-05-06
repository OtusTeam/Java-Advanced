time java -XX:DumpLoadedClassList=app_classes.lst \
     -Dspring.output.ansi.enabled=always \
     -Dcom.sun.management.jmxremote \
     -Dspring.jmx.enabled=true \
     -Dspring.liveBeansView.mbeanDomain \
     -Dspring.application.admin.enabled=true \
     -Dfile.encoding=UTF-8 \
     -Dsun.stdout.encoding=UTF-8 \
     -Dsun.stderr.encoding=UTF-8 \
     -classpath "/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework.boot/spring-boot-starter-web/2.7.18/dd62ea85098187b4604e78dc15a7ff87dba173d/spring-boot-starter-web-2.7.18.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework.boot/spring-boot-starter-aop/2.7.18/8669fae56877e44bd26e35f641e0233bebe9239c/spring-boot-starter-aop-2.7.18.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework.boot/spring-boot-starter-actuator/2.7.18/ac6d614d1d0503dfec26110c801902d48bd22628/spring-boot-starter-actuator-2.7.18.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/io.micrometer/micrometer-registry-prometheus/1.9.17/b939e2f73d3db968d02cdaeb7d72df21f098e262/micrometer-registry-prometheus-1.9.17.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.apache.commons/commons-lang3/3.12.0/c6842c86792ff03b9f1d1fe2aab8dc23aa6c6f0e/commons-lang3-3.12.0.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/com.google.guava/guava/33.0.0-jre/161ba27964a62f241533807a46b8711b13c1d94b/guava-33.0.0-jre.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.jetbrains/annotations/24.0.0/69b8b443c437fdeefa8d20c18d257b94836a92b9/annotations-24.0.0.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.projectlombok/lombok/1.18.30/f195ee86e6c896ea47a1d39defbe20eb59cd149d/lombok-1.18.30.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework.boot/spring-boot-starter-json/2.7.18/b6d9ed5cae0c1929a9e561bf4799a3dc93a10db1/spring-boot-starter-json-2.7.18.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework.boot/spring-boot-starter/2.7.18/e56b75105f9ace6df154fd47eeeeadc2f5791e56/spring-boot-starter-2.7.18.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework.boot/spring-boot-starter-tomcat/2.7.18/c56e50e006448e75a8bde595dbc754ba294389af/spring-boot-starter-tomcat-2.7.18.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework/spring-webmvc/5.3.31/45754d056effe8257a012f6b98ed5454cf1e8960/spring-webmvc-5.3.31.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework/spring-web/5.3.31/3bf73c385a1f2f4a0d482149d6a205e854cec497/spring-web-5.3.31.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework/spring-aop/5.3.31/3be929dbdb5f4516919ad09a3d3720d779bb65d9/spring-aop-5.3.31.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.aspectj/aspectjweaver/1.9.7/158f5c255cd3e4408e795b79f7c3fbae9b53b7ca/aspectjweaver-1.9.7.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework.boot/spring-boot-actuator-autoconfigure/2.7.18/1102948e3d7d3d63f64ad8de9864429023f2fae0/spring-boot-actuator-autoconfigure-2.7.18.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/io.micrometer/micrometer-core/1.9.17/48bf67c0c7028e5f3124d1f9a31bdcf345596e65/micrometer-core-1.9.17.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/io.prometheus/simpleclient_common/0.15.0/57bd1d8be9f4d965a38c6b1b35ee60358cc679fc/simpleclient_common-0.15.0.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/com.google.guava/failureaccess/1.0.2/c4a06a64e650562f30b7bf9aaec1bfed43aca12b/failureaccess-1.0.2.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/com.google.guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/b421526c5f297295adef1c886e5246c39d4ac629/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/com.google.code.findbugs/jsr305/3.0.2/25ea2e8b0c338a877313bd4672d3fe056ea78f0d/jsr305-3.0.2.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.checkerframework/checker-qual/3.41.0/8be6df7f1e9bccb19f8f351b3651f0bac2f5e0c/checker-qual-3.41.0.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/com.google.errorprone/error_prone_annotations/2.23.0/43a27853b6c7d54893e0b1997c2c778c347179eb/error_prone_annotations-2.23.0.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/com.google.j2objc/j2objc-annotations/2.8/c85270e307e7b822f1086b93689124b89768e273/j2objc-annotations-2.8.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/com.fasterxml.jackson.datatype/jackson-datatype-jsr310/2.13.5/8ba3b868e81d7fc6ead686bd2353859b111d9eaf/jackson-datatype-jsr310-2.13.5.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/com.fasterxml.jackson.module/jackson-module-parameter-names/2.13.5/a401a99e7a45450fd3ef76e82ba39005fd1a8c22/jackson-module-parameter-names-2.13.5.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/com.fasterxml.jackson.datatype/jackson-datatype-jdk8/2.13.5/1278f38160812811c56eb77f67213662ed1c7a2e/jackson-datatype-jdk8-2.13.5.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/com.fasterxml.jackson.core/jackson-databind/2.13.5/aa95e46dbc32454f3983221d420e78ef19ddf844/jackson-databind-2.13.5.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework.boot/spring-boot-autoconfigure/2.7.18/9cf147c6ca274c75b32556acdcba5a1de081ebcd/spring-boot-autoconfigure-2.7.18.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework.boot/spring-boot/2.7.18/f6dbdd8da7c2bded63dff9b1f48d01a4923f20a0/spring-boot-2.7.18.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework.boot/spring-boot-starter-logging/2.7.18/19f7c255ba5255116f58c3bbaf52c7b88ea6af3e/spring-boot-starter-logging-2.7.18.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/jakarta.annotation/jakarta.annotation-api/1.3.5/59eb84ee0d616332ff44aba065f3888cf002cd2d/jakarta.annotation-api-1.3.5.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework/spring-core/5.3.31/368e76f732a3c331b970f69cafec1525d27b34d3/spring-core-5.3.31.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.yaml/snakeyaml/1.30/8fde7fe2586328ac3c68db92045e1c8759125000/snakeyaml-1.30.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.apache.tomcat.embed/tomcat-embed-websocket/9.0.83/9af4b7450296bb4eff93b2ee3e52ab69d07512e4/tomcat-embed-websocket-9.0.83.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.apache.tomcat.embed/tomcat-embed-core/9.0.83/d771e4343b0515c67dab2a09fe02f5d47550153f/tomcat-embed-core-9.0.83.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.apache.tomcat.embed/tomcat-embed-el/9.0.83/b0cdada70099c25f45fceb48e1ebce60d138a5ce/tomcat-embed-el-9.0.83.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework/spring-context/5.3.31/a2d6e76507f037ad835e8c2288dfedf28981999f/spring-context-5.3.31.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework/spring-beans/5.3.31/d27258849071b3b268ecc388eca35bbfcc586448/spring-beans-5.3.31.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework/spring-expression/5.3.31/55637af1b186d1008890980c2876c5fc83599756/spring-expression-5.3.31.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework.boot/spring-boot-actuator/2.7.18/669394598d18cf908ac5086a729ac331a321c38d/spring-boot-actuator-2.7.18.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.hdrhistogram/HdrHistogram/2.1.12/6eb7552156e0d517ae80cc2247be1427c8d90452/HdrHistogram-2.1.12.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/io.prometheus/simpleclient/0.15.0/144aaf1ac9361a497d98079e0db8757a95e22fc4/simpleclient-0.15.0.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/com.fasterxml.jackson.core/jackson-annotations/2.13.5/136f77ab424f302c9e27230b4482e8000e142edf/jackson-annotations-2.13.5.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/com.fasterxml.jackson.core/jackson-core/2.13.5/d07c97d3de9ea658caf1ff1809fd9de930a286a/jackson-core-2.13.5.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/ch.qos.logback/logback-classic/1.2.12/d4dee19148dccb177a0736eb2027bd195341da78/logback-classic-1.2.12.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.apache.logging.log4j/log4j-to-slf4j/2.17.2/17dd0fae2747d9a28c67bc9534108823d2376b46/log4j-to-slf4j-2.17.2.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.slf4j/jul-to-slf4j/1.7.36/ed46d81cef9c412a88caef405b58f93a678ff2ca/jul-to-slf4j-1.7.36.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.springframework/spring-jcl/5.3.31/e7ab9ee590a195415dd6b898440d776b4c8db78c/spring-jcl-5.3.31.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/io.prometheus/simpleclient_tracer_otel/0.15.0/53770a575d13d5aeebc7e2ebd7cc714496d7ab28/simpleclient_tracer_otel-0.15.0.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/io.prometheus/simpleclient_tracer_otel_agent/0.15.0/9c2f1a317960110581857911ca5fd7379ba77e28/simpleclient_tracer_otel_agent-0.15.0.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/ch.qos.logback/logback-core/1.2.12/1d8e51a698b138065d73baefb4f94531faa323cb/logback-core-1.2.12.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.slf4j/slf4j-api/1.7.36/6c62681a2f655b49963a5983b8b0950a6120ae14/slf4j-api-1.7.36.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.apache.logging.log4j/log4j-api/2.17.2/f42d6afa111b4dec5d2aea0fe2197240749a4ea6/log4j-api-2.17.2.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/io.prometheus/simpleclient_tracer_common/0.15.0/f1bac57eaf6c04e6b72a08b44a0e6569e87974a4/simpleclient_tracer_common-0.15.0.jar:/c/Users/atsar/.gradle/caches/modules-2/files-2.1/org.latencyutils/LatencyUtils/2.0.3/769c0b82cb2421c8256300e907298a9410a2a3d3/LatencyUtils-2.0.3.jar" \
     -jar ./build/libs/hash-service-1.0.0-SNAPSHOT.jar