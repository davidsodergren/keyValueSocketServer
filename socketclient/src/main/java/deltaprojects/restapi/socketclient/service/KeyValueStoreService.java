package deltaprojects.restapi.socketclient.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class KeyValueStoreService {

  private final static ConcurrentMap<String, JSONObject> keyValueStore = new ConcurrentHashMap<>();

  public void addKeyValueToStore(String key, JSONObject jsonObject) {
    keyValueStore.put(key, jsonObject);
  }

  public JSONObject getValueByKey(String key) {
    return keyValueStore.get(key);
  }

}
