# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the application's JAR file to the container
COPY target/pro-groww-eureka-server.jar /app/app.jar

# Expose the port the application runs on
EXPOSE 8761

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
