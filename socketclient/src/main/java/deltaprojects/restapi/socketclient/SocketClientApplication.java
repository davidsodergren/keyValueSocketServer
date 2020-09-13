package deltaprojects.restapi.socketclient;

import deltaprojects.restapi.socketclient.webSocket.KeyValueSocketClient;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
@SpringBootApplication
public class SocketClientApplication {

	private static final Logger logger = Logger.getLogger(SocketClientApplication.class);

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(SocketClientApplication.class, args);
		KeyValueSocketClient keyValueSocketClient = app.getBean(KeyValueSocketClient.class);
		new Thread(() -> {
			while (true) {
				try {
					keyValueSocketClient.connectToSocketServer();
					break;
				} catch (Exception e) {
					logger.error("Failed to connect with socket server. Sleeping in 10 seconds and trying again");
					try {
						Thread.sleep(10000);
					} catch (InterruptedException ex) {
						logger.error("Failed to sleep thread: " + Thread.currentThread().getName() + ex.getMessage());
					}
				}
			}
		}).start();
	}

}
