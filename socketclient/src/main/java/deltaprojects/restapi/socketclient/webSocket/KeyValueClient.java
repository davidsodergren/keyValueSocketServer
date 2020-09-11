package deltaprojects.restapi.socketclient.webSocket;

import deltaprojects.restapi.socketclient.config.SocketCommunicationConfig;
import deltaprojects.restapi.socketclient.service.KeyValueQueueService;
import deltaprojects.restapi.socketclient.service.KeyValueStoreService;
import deltaprojects.restapi.socketclient.threads.ReadThread;
import deltaprojects.restapi.socketclient.threads.WriteThread;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class KeyValueClient {

  @Value("${socket.hostname}")
  private String hostname;
  @Value("${socket.port}")
  private int port;

  private final KeyValueQueueService keyValueQueueService;
  private final SocketCommunicationConfig socketCommunicationConfig;
  private final KeyValueStoreService keyValueStoreService;

  public KeyValueClient(KeyValueQueueService keyValueQueueService, SocketCommunicationConfig socketCommunicationConfig,
                        KeyValueStoreService keyValueStoreService) {
    this.keyValueQueueService = keyValueQueueService;
    this.socketCommunicationConfig = socketCommunicationConfig;
    this.keyValueStoreService = keyValueStoreService;
  }

  public void connectToSocketServer() throws IOException {
    System.out.println("Trying to connect to host: " + hostname + " on port: " +port);
      Socket socket = new Socket(hostname, port);
      new ReadThread(socketCommunicationConfig.createReaderForSocket(socket), keyValueStoreService).start();
      new WriteThread(socketCommunicationConfig.createWriterForSocket(socket), keyValueQueueService).start();
      System.out.println("Connected to socket server");
  }

}
