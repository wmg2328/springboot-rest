FROM openjdk:8-jdk-alpine
MAINTAINER Wiraj Gunasinghe (wwiraj@gmail.com)
VOLUME /tmp
ARG JAR_FILE=build/libs/springboot-rest-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} springboot-rest.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/springboot-rest.jar"]
EXPOSE 8080