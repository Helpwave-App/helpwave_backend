FROM openjdk:17-jdk-alpine

COPY target/HelpWave-0.0.1-SNAPSHOT.war /app/helpwave.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/helpwave.war"]
