FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/ToolTool-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8010
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]