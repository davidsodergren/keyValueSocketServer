package deltaprojects.restapi.socketclient.controller;

import deltaprojects.restapi.socketclient.service.KeyValueQueueService;
import deltaprojects.restapi.socketclient.service.KeyValueStoreService;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/kvstore/values/")
public class KeyValueController {

  private final KeyValueQueueService keyValueQueueService;
  private final KeyValueStoreService keyValueStoreService;

  public KeyValueController(KeyValueQueueService keyValueQueueService, KeyValueStoreService keyValueStoreService) {
    this.keyValueQueueService = keyValueQueueService;
    this.keyValueStoreService = keyValueStoreService;
  }

  @GetMapping("/{key}")
  public JSONObject getValueByKey(@PathVariable String key) {
    return keyValueStoreService.getValueByKey(key);
  }

  @PutMapping("/{key}")
  public void updateKey(@RequestBody JSONObject jsonObject, @PathVariable String key) throws InterruptedException {
    keyValueStoreService.addKeyValueToStore(key, jsonObject);
    keyValueQueueService.addKeyValueToQueue(key, jsonObject);
  }

}
