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

  public BufferedReader createReaderForSocket(Socket socket) throws IOException {
    InputStream input = socket.getInputStream();
    return new BufferedReader(new InputStreamReader(input));
  }

  public PrintWriter createWriterForSocket(Socket socket) throws IOException {
    OutputStream output = socket.getOutputStream();
    return new PrintWriter(output, true);
  }
}

