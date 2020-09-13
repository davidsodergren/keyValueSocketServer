package delta.projects.test.socket;

import delta.projects.test.server.KeyValueServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SocketCommunicationThread extends Thread {

  private static final Logger logger = Logger.getLogger(SocketCommunicationThread.class);

  private PrintWriter printWriter;
  private BufferedReader bufferedReader;

  public SocketCommunicationThread(BufferedReader bufferedReader, PrintWriter printWriter) {
    this.bufferedReader = bufferedReader;
    this.printWriter = printWriter;
  }

  @Override
  public void run() {
    try {
      while (true) {
        JSONObject jsonObject = receiveMessage();
        logger.info("Received message: " + jsonObject.toString());
        KeyValueServer.broadcast(jsonObject.toString(), this);
      }
    } catch (IOException | ParseException ex) {
      KeyValueServer.removeSocketCommunicationThread(this);
      logger.error("Error in UserThread: " + ex.getMessage());
    }
  }

  public JSONObject receiveMessage() throws IOException, ParseException {
    String clientMessage = bufferedReader.readLine();
    return (JSONObject) new JSONParser().parse(clientMessage);
  }

  public void sendMessage(String message) {
    printWriter.println(message);
  }
}