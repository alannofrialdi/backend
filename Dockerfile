# # Stage 1: Build the application
# FROM maven:3.9.6-eclipse-temurin-21 AS builder
# WORKDIR /build

# # Copy pom.xml and source code
# COPY pom.xml .
# COPY src src

# # Build the application
# RUN mvn clean package -DskipTests

# # Stage 2: Create the final image
# FROM eclipse-temurin:21-jdk
# WORKDIR /app

# # Copy the jar file from builder stage
# COPY --from=builder /build/target/*.jar app.jar

# # Expose the port your application runs on
# EXPOSE 8080

# # Command to run the application
# ENTRYPOINT ["java", "--enable-preview", "-jar", "app.jar"]

# Stage 1: Build
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


