FROM openjdk:19-jdk-slim-buster
COPY target/*.jar app.jar
EXPOSE 8080
ENV SPRINGPROFILES=prod,actuator
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRINGPROFILES}","-jar", "app.jar"]