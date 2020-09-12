package deltaprojects.restapi.socketclient.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class KeyValueStoreService {

  private final static Logger logger = Logger.getLogger(KeyValueStoreService.class);

  private final static ConcurrentMap<String, JSONObject> keyValueStore = new ConcurrentHashMap<>();

  public void addKeyValueToStore(String key, JSONObject jsonObject) {
    keyValueStore.put(key, jsonObject);
    logger.info("Added new Key/Value pair to internal store. Value was: " + key + " : " + jsonObject.toString());
  }

  public JSONObject getValueByKey(String key) {
    logger.info("Trying to get value from internal key/value store with key: " + key);
    return keyValueStore.get(key);
  }

}
