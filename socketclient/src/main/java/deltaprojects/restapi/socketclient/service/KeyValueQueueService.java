package deltaprojects.restapi.socketclient.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class KeyValueQueueService {

  private static final BlockingQueue<JSONObject> queue = new LinkedBlockingQueue<>(200);

  public void addKeyValueToQueue(String key, JSONObject jsonObject) throws InterruptedException {
    JSONObject newKeyValue = new JSONObject();
    newKeyValue.put(key, jsonObject);
    queue.put(newKeyValue);
  }

  public JSONObject take() throws InterruptedException {
    return queue.take();
  }
}
