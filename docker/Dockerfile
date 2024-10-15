FROM openjdk:17
ARG JAR_FILE=*.jar
WORKDIR /app
COPY ${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]

EXPOSE 8080