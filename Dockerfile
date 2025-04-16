# Stage 1: Build the application
FROM openjdk:21-jdk-slim AS builder

# Set the working directory
WORKDIR /app

# Copy the Maven build file
COPY pom.xml .

# Install dependencies (this is for Maven-based projects)
RUN apt-get update && apt-get install -y maven && mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Create a smaller runtime image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/e-commerce-final-0.0.1-SNAPSHOT.jar /app/e-commerce-final.jar

# Expose the port the app will run on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "e-commerce-final.jar"]
