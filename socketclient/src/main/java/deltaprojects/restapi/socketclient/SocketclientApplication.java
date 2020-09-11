package deltaprojects.restapi.socketclient;

import deltaprojects.restapi.socketclient.webSocket.KeyValueClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
@SpringBootApplication
public class SocketclientApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(SocketclientApplication.class, args);
		KeyValueClient keyValueClient = app.getBean(KeyValueClient.class);
		new Thread(() -> {
			while (true) {
				try {
					keyValueClient.connectToSocketServer();
					break;
				} catch (Exception e) {
					System.out.println("Failed to connect with socket server. Sleeping in 10 seconds and trying again");
					try {
						Thread.sleep(10000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}
		}).start();
	}

}
