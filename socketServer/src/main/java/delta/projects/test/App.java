package delta.projects.test;

import delta.projects.test.server.KeyValueServer;
import org.apache.log4j.Logger;


public class App {

  private static final Logger logger = Logger.getLogger(App.class);

  public static void main(String[] args) {
    int port;
    try {
       port = Integer.parseInt(System.getenv("SOCKET_SERVER_PORT"));
    } catch (NumberFormatException e) {
      logger.error("Could not parse port. Throwing error and exiting application");
      throw new NumberFormatException("Port was not set correctly in system environment variable. Port should be a four digit number");
    }
    KeyValueServer keyValueServer = new KeyValueServer(port);
    keyValueServer.startServer();
  }
}
