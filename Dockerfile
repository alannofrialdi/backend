# Gunakan base image JDK 17
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy file JAR ke container
COPY target/*.jar app.jar

# Jalankan aplikasi
CMD ["java", "-jar", "app.jar"]
