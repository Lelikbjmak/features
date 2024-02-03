FROM openjdk:17.0.2-bullseye

LABEL authors="Denis Solovey"

WORKDIR app

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]