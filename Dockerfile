FROM openjdk:26-ea-jdk-slim
WORKDIR /app
COPY target/demo-0.0.5-SNAPSHOT.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]