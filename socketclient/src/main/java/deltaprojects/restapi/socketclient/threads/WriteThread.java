package deltaprojects.restapi.socketclient.threads;

import deltaprojects.restapi.socketclient.service.KeyValueQueueService;
import java.io.PrintWriter;
import org.json.simple.JSONObject;

public class WriteThread extends Thread {

  private final PrintWriter printWriter;
  private final KeyValueQueueService keyValueQueueService;


  public WriteThread(PrintWriter printWriter, KeyValueQueueService keyValueQueueService) {
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
      Thread.currentThread().interrupt();
    }
  }

  private void sendMessageToServer(JSONObject jsonObject) {
    printWriter.println(jsonObject.toString());
  }
}
