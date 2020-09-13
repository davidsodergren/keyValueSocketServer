package deltaprojects.restapi.socketclient.webSocket;

import deltaprojects.restapi.socketclient.config.SocketCommunicationConfig;
import deltaprojects.restapi.socketclient.service.KeyValueQueueService;
import deltaprojects.restapi.socketclient.service.KeyValueStoreService;
import deltaprojects.restapi.socketclient.threads.SocketReaderThread;
import deltaprojects.restapi.socketclient.threads.SocketWriterThread;
import java.io.IOException;
import java.net.Socket;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class KeyValueSocketClient {

  private final static Logger logger = Logger.getLogger(KeyValueSocketClient.class);

  @Value("${socket.hostname}")
  private String hostname;
  @Value("${socket.port}")
  private int port;

  private final KeyValueQueueService keyValueQueueService;
  private final SocketCommunicationConfig socketCommunicationConfig;
  private final KeyValueStoreService keyValueStoreService;

  public KeyValueSocketClient(KeyValueQueueService keyValueQueueService, SocketCommunicationConfig socketCommunicationConfig,
                              KeyValueStoreService keyValueStoreService) {
    this.keyValueQueueService = keyValueQueueService;
    this.socketCommunicationConfig = socketCommunicationConfig;
    this.keyValueStoreService = keyValueStoreService;
  }

  public void connectToSocketServer() throws IOException {
    logger.info("Trying to connect to socket server with hostname: " + hostname + " on port: " + port);
    Socket socket = new Socket(hostname, port);
    new SocketReaderThread(socketCommunicationConfig.createReaderForSocket(socket), keyValueStoreService).start();
    new SocketWriterThread(socketCommunicationConfig.createWriterForSocket(socket), keyValueQueueService).start();
    logger.info("Connected to socket server");
  }

}
