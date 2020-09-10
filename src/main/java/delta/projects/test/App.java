package delta.projects.test;

import delta.projects.test.server.KeyValueServer;

/**
 * Hello world!
 */
public class App {
  public static void main(String[] args) {
    int port = 8081;
    KeyValueServer keyValueServer = new KeyValueServer(port);
    keyValueServer.startServer();
  }
}
