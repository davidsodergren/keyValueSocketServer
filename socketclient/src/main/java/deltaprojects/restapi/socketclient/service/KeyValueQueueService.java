package deltaprojects.restapi.socketclient.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class KeyValueQueueService {

  private static final Logger logger = Logger.getLogger(KeyValueQueueService.class);

  private static final BlockingQueue<JSONObject> keyValueMessageQueue = new LinkedBlockingQueue<>(200);

  public void addKeyValueToQueue(String key, JSONObject jsonObject) throws InterruptedException {
    JSONObject newKeyValue = new JSONObject();
    newKeyValue.put(key, jsonObject);
    keyValueMessageQueue.put(newKeyValue);
    logger.info("Added new Key/Value pair to queue. Value was: " + jsonObject.toString());
  }

  public JSONObject take() throws InterruptedException {
    logger.info("Take from queue");
    return keyValueMessageQueue.take();
  }
}
