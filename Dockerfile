FROM openjdk:17-jdk
LABEL maintainer="yubin"
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
