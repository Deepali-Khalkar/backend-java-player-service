# Use the OpenJDK 17 slim image as the base image
FROM openjdk:17-jdk-slim

# Maintainer information
MAINTAINER Deepali

# Set working directory inside the container
WORKDIR /app

# Copy JAR file to the container
COPY target/player-service-java-0.0.1-SNAPSHOT.jar app.jar

# Copy SQL script and other resources needed by the application (like Player.csv)
COPY src/main/resources/Player.csv Player.csv
COPY src/main/resources/schema.sql schema.sql

# Expose the relevant port
EXPOSE 8080

# Run the JAR file as the container entry point
ENTRYPOINT ["java", "-jar", "app.jar"]