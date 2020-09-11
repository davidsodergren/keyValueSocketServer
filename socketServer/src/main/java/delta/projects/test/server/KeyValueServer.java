package delta.projects.test.server;

import delta.projects.test.config.SocketCommunicationConfig;
import delta.projects.test.socket.MessageReceiverThread;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class KeyValueServer {
  private int port;
  private final static Set<MessageReceiverThread> MESSAGE_RECEIVER_THREADS = new HashSet<>();

  public KeyValueServer(int port) {
    this.port = port;
  }

  public void startServer() {
    try (ServerSocket serverSocket = new ServerSocket(port)) {

      System.out.println("Socket server is listening on port " + port);
      System.out.println("Server started: " + serverSocket);

      while (true) {
        Socket socket = serverSocket.accept();
        BufferedReader bufferedReader = SocketCommunicationConfig.CreateReaderForSocket(socket);
        PrintWriter printWriter = SocketCommunicationConfig.CreateWriterForSocket(socket);

        MessageReceiverThread messageReceiverThread = new MessageReceiverThread(bufferedReader, printWriter);
        System.out.println("New node connected");
        MESSAGE_RECEIVER_THREADS.add(messageReceiverThread);
        messageReceiverThread.start();
      }
    } catch (IOException ex) {
      System.out.println("Error in the server: " + ex.getMessage());
      ex.printStackTrace();
    }
  }

  public static void Broadcast(String keyValue, MessageReceiverThread excludeUser) {
    for (MessageReceiverThread aUser : MESSAGE_RECEIVER_THREADS) {
      if (aUser != excludeUser) {
        aUser.sendMessage(keyValue);
      }
    }
  }
}
