package delta.projects.test;

import delta.projects.test.server.KeyValueServer;

/**
 * Hello world!
 */
public class App {
  public static void main(String[] args) throws Exception {
    int port;
    try {
       port = Integer.parseInt(System.getenv("SOCKET_SERVER_PORT"));
    } catch (NumberFormatException e) {
      throw new Exception("Port was not set correctly in system environment variable. Port should be a four digit number");
    }
    KeyValueServer keyValueServer = new KeyValueServer(port);
    keyValueServer.startServer();
  }
}
