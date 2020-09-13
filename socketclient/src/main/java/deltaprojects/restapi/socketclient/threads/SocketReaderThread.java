package deltaprojects.restapi.socketclient.threads;

import deltaprojects.restapi.socketclient.service.KeyValueStoreService;
import java.io.BufferedReader;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SocketReaderThread extends Thread {

  private final static Logger logger = Logger.getLogger(SocketReaderThread.class);

  private BufferedReader reader;
  private final KeyValueStoreService keyValueStoreService;

  public SocketReaderThread(BufferedReader reader, KeyValueStoreService keyValueStoreService) {
    this.reader = reader;
    this.keyValueStoreService = keyValueStoreService;
  }

  public void run() {
    while (true) {
      try {
        String response = reader.readLine();
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(response);
        updateInternalKeyValueStore(jsonObject);
      } catch (IOException | ParseException e) {
        logger.error("Error when trying to handle message from server: " + e.getMessage());
      }
    }
  }

  private void updateInternalKeyValueStore(JSONObject jsonObject) {
    Object key = jsonObject.keySet().stream().findFirst().get();
    JSONObject value = (JSONObject) jsonObject.get(key);
    keyValueStoreService.addKeyValueToStore(key.toString(), value);
  }
}