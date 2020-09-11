package delta.projects.test.socket;

import delta.projects.test.server.KeyValueServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MessageReceiverThread extends Thread {
  private PrintWriter printWriter;
  private BufferedReader bufferedReader;

  public MessageReceiverThread(BufferedReader bufferedReader, PrintWriter printWriter) {
    this.bufferedReader = bufferedReader;
    this.printWriter = printWriter;
  }

  public void run() {
    try {
      while (true) {
        JSONObject jsonObject = receiveMessage();
        System.out.println("Received message: " + jsonObject.toString());
        KeyValueServer.Broadcast(jsonObject.toString(), this);
      }
    } catch (IOException | ParseException ex) {
      System.out.println("Error in UserThread: " + ex.getMessage());
      ex.printStackTrace();
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