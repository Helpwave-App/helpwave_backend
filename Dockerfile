# Usa una imagen Maven para compilar
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Usa una imagen más liviana para ejecutar el .war
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.war app.war
EXPOSE 8080
ENV PORT=8080
CMD ["java", "-jar", "app.war"]