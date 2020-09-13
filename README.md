# keyValueSocketServer

This is my solution to a distributed key/value store

The reason why I chose Java for this project was because it is a very good and stable language for creating backend services of this type. Java has good support for creating and using web sockets which is a technique/tool that was specifically important for the solution which I chose for this task. The project also required to create a restful api which could handle GET/PUT requests, this can easily be done with Spring Boot which I used on top of Java. Another reason why I chose Java is due to the fact that it is the language I feel most confident working with. In addition to Java I used Maven to be able to build my project and handle dependencies in a smooth way. I use docker to make it easy for anyone to test and try out the application on their own. I also use docker compose which is configured to start 3 different nodes which suits the requirement for this task.

High level description of my intended solution: 

I have created two applications. The first one acts as a “socketserver” which is a very simple application that simply runs in a while loop and waits for new sockets to connect. For every new socket that Is connected the server creates a new thread. In each thread the application listens for new messages. When a new message is received, the server “broadcasts” that message to all connected sockets.

The second one acts as a “socketclient” and a rest api. When the application starts it will try to connect to the socketserver. The application will then create a writerthread(responsible for writing to the socketserver) and a readerthread(responsible for reading from the server)

The application has a static hashmap in one of its classes which acts as the key/value store for the application. When a PUT request is received it does two things: 

1.	It updates the internal key/value store of the application
2.	Sends a message to the socket server to broadcast this message to all other connected nodes
