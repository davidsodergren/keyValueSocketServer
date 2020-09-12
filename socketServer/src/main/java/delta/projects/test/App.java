package delta.projects.test;

import delta.projects.test.server.KeyValueServer;
import delta.projects.test.socket.SocketCommunicationThread;
import org.apache.log4j.Logger;


public class App {

  private final static Logger logger = Logger.getLogger(App.class);

  public static void main(String[] args) throws Exception {
    int port;
    try {
       port = Integer.parseInt(System.getenv("SOCKET_SERVER_PORT"));
    } catch (NumberFormatException e) {
      logger.error("Could not parse port. Throwing error and exiting application");
      throw new Exception("Port was not set correctly in system environment variable. Port should be a four digit number");
    }
    KeyValueServer keyValueServer = new KeyValueServer(port);
    keyValueServer.startServer();
  }
}
