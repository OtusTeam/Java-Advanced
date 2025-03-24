FROM eclipse-temurin:21

RUN mkdir /opt/app
COPY ./target/demo*.jar /opt/app/demo.jar

EXPOSE 8080

CMD ["java", "-jar", "/opt/app/demo.jar"]