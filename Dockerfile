# FROM eclipse-temurin:17-jdk-alpine
FROM amazoncorretto:17
VOLUME /tmp
COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]