package delta.projects.test.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketCommunicationConfig {

  private SocketCommunicationConfig() {
  }

  public static BufferedReader createReaderForSocket(Socket socket) throws IOException {
    InputStream input = socket.getInputStream();
    return new BufferedReader(new InputStreamReader(input));
  }

  public static PrintWriter createWriterForSocket(Socket socket) throws IOException {
    OutputStream output = socket.getOutputStream();
    return new PrintWriter(output, true);
  }
}
