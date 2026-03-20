# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:21-jdk-jammy

# Set the working directory in the container
WORKDIR /app

# Copy the application's jar file to the container
# This assumes the jar file is built using 'mvn package'
COPY target/high-level-design-1.0-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expose the application port
EXPOSE 8080
