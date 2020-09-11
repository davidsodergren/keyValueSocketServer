package deltaprojects.restapi.socketclient.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import org.springframework.stereotype.Component;

@Component
public class SocketCommunicationConfig {

  public BufferedReader createReaderForSocket(Socket socket) {
    InputStream input = null;
    try {
      input = socket.getInputStream();
    } catch (IOException e) {
      System.out.println("Error when setting up reader");
      e.printStackTrace();
    }
    return new BufferedReader(new InputStreamReader(input));
  }

  public PrintWriter createWriterForSocket(Socket socket) {
    OutputStream output = null;
    try {
      output = socket.getOutputStream();
    } catch (IOException ex) {
      System.out.println("Error getting output stream: " + ex.getMessage());
      ex.printStackTrace();
    }
    return new PrintWriter(output, true);
  }
}
