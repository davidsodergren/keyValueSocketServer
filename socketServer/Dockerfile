FROM openjdk:13-jdk-alpine
ENV SOCKET_SERVER_PORT=8081
EXPOSE $SOCKET_SERVER_PORT
ARG JAR_FILE=target/socketServer-1.0-SNAPSHOT-jar-with-dependencies.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]