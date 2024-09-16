# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the application's JAR file to the container
COPY target/mail-service.jar /app/app.jar

# Expose the port the application runs on
EXPOSE 8083

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

#172.17.0.3