FROM openjdk:13-jdk-alpine
ENV SOCKET_SERVER_ADDRESS="localhost"
ENV SOCKET_SERVER_PORT=8081
ENV TOMCAT_PORT=8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]