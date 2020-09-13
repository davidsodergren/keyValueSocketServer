package deltaprojects.restapi.socketclient.threads;

import deltaprojects.restapi.socketclient.service.KeyValueQueueService;
import java.io.PrintWriter;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

public class SocketWriterThread extends Thread {

  private final static Logger logger = Logger.getLogger(SocketWriterThread.class);

  private final PrintWriter printWriter;
  private final KeyValueQueueService keyValueQueueService;


  public SocketWriterThread(PrintWriter printWriter, KeyValueQueueService keyValueQueueService) {
    this.printWriter = printWriter;
    this.keyValueQueueService = keyValueQueueService;
  }

  public void run() {
    try {
      while (true) {
        JSONObject keyValue = keyValueQueueService.take();
        sendMessageToServer(keyValue);
      }
    } catch (InterruptedException e) {
      logger.error("Error when trying to send message to server: " + e.getMessage());
    }
  }

  private void sendMessageToServer(JSONObject jsonObject) {
    logger.info("Sending new key/value to server. key/value is: " + jsonObject.toString());
    printWriter.println(jsonObject.toString());
  }
}
