package delta.projects.test.server;

import delta.projects.test.config.SocketCommunicationConfig;
import delta.projects.test.socket.SocketCommunicationThread;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;

public class KeyValueServer {

  private final static Logger logger = Logger.getLogger(KeyValueServer.class);

  private final int port;
  private final static Set<SocketCommunicationThread> SOCKET_COMMUNICATION_THREADS = new HashSet<>();

  public KeyValueServer(int port) {
    this.port = port;
  }

  public void startServer() {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      logger.info("Server started. Listening on: " + serverSocket);

      while (true) {
        Socket socket = serverSocket.accept();
        BufferedReader bufferedReader = SocketCommunicationConfig.CreateReaderForSocket(socket);
        PrintWriter printWriter = SocketCommunicationConfig.CreateWriterForSocket(socket);

        SocketCommunicationThread socketCommunicationThread = new SocketCommunicationThread(bufferedReader, printWriter);
        logger.info("New node connected");
        SOCKET_COMMUNICATION_THREADS.add(socketCommunicationThread);
        socketCommunicationThread.start();
      }
    } catch (IOException e) {
      logger.error("Error when trying to connect with new node: " + e.getMessage());
    }
  }

  public static void Broadcast(String keyValue, SocketCommunicationThread excludeUser) {
    logger.info("Sending new key/value pair to all sockets");
    SOCKET_COMMUNICATION_THREADS
        .stream()
        .filter(socket -> socket != excludeUser)
        .forEach(socket -> socket.sendMessage(keyValue));
  }

  public static void RemoveSocketCommunicationThread(SocketCommunicationThread removeUser) {
    logger.info("Removing socket from list of all sockets. Socket that was removed: " + removeUser.getName());
    SOCKET_COMMUNICATION_THREADS.remove(removeUser);
  }
}
