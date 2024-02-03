FROM openjdk:17-alpine

LABEL authors="Denis Solovey"

WORKDIR app

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]