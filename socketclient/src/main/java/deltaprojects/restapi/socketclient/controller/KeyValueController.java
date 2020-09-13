package deltaprojects.restapi.socketclient.controller;

import deltaprojects.restapi.socketclient.service.KeyValueQueueService;
import deltaprojects.restapi.socketclient.service.KeyValueStoreService;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<?> getValueByKey(@PathVariable String key) {
    JSONObject jsonObject = keyValueStoreService.getValueByKey(key);
    if(jsonObject == null) {
      return new ResponseEntity<>(
          "Value was not found for given key: " + key,
          HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(
        jsonObject,
        HttpStatus.OK);
  }

  @PutMapping("/{key}")
  public ResponseEntity<?> updateKey(@RequestBody JSONObject jsonObject, @PathVariable String key) throws InterruptedException {
    keyValueStoreService.addKeyValueToStore(key, jsonObject);
    keyValueQueueService.addKeyValueToQueue(key, jsonObject);
    return new ResponseEntity<>("Updated key/value", HttpStatus.NO_CONTENT);
  }

}
