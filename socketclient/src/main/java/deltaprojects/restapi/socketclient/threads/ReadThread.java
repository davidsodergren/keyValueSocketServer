package deltaprojects.restapi.socketclient.threads;

import deltaprojects.restapi.socketclient.service.KeyValueStoreService;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadThread extends Thread {
  private BufferedReader reader;
  private final KeyValueStoreService keyValueStoreService;

  public ReadThread(BufferedReader reader, KeyValueStoreService keyValueStoreService) {
    this.reader = reader;
    this.keyValueStoreService = keyValueStoreService;
  }

  public void run() {
    while (true) {
      try {
        String response = reader.readLine();
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(response);
        updateInternalKeyValueStore(jsonObject);
      } catch (IOException | ParseException ex) {
        System.out.println("Error reading from server: " + ex.getMessage());
        ex.printStackTrace();
        break;
      }
    }
  }

  private void updateInternalKeyValueStore(JSONObject jsonObject) {
    Object key = jsonObject.keySet().stream().findFirst().get();
    JSONObject value = (JSONObject) jsonObject.get(key);
    keyValueStoreService.addKeyValueToStore(key.toString(), value);
    System.out.println("\n" + jsonObject.toString());
  }
}