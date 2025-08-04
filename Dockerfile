FROM openjdk:17-jdk-slim

WORKDIR /app

COPY src/main/resources/Player.csv Player.csv
COPY src/main/resources/schema.sql schema.sql
COPY target/player-service-java-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
