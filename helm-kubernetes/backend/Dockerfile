FROM eclipse-temurin:21
VOLUME /tmp
COPY target/backend-1.0.0-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java -jar /app.jar --debug