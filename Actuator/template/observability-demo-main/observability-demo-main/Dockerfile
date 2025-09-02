FROM bellsoft/alpaquita-linux-base AS builder

WORKDIR /opt/temp

RUN apk update && \
    apk add --no-cache bash g++ make zlib-static && \
    wget https://download.bell-sw.com/java/17.0.5+8/bellsoft-jdk17.0.5+8-linux-x64-musl-lite.apk && \
    apk add --allow-untrusted --no-cache  \
        ./bellsoft-jdk17.0.5+8-linux-x64-musl-lite.apk && \
    rm -R *

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw .
COPY pom.xml .

RUN chmod +x mvnw  && \
    ./mvnw -ntp dependency:go-offline

COPY src/ src/

RUN ./mvnw package -DskipTests

FROM bellsoft/liberica-runtime-container

WORKDIR /opt

COPY --from=builder /app/target/observability-demo-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "observability-demo-0.0.1-SNAPSHOT.jar"]
