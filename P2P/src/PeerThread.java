import java.io.*;
import java.net.*;
import javax.json.*;

public class PeerThread extends Thread {
	BufferedReader reader;

	public PeerThread(Socket socket) throws IOException {
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void run() {
		boolean f = true;
		while (f) {
			JsonObject jsonobject = Json.createReader(reader).readObject();
			if (jsonobject.containsKey("username")) {
				System.out.println(jsonobject.getString("username") + ": " + jsonobject.getString("message"));
			} else {
				f = false;
				System.out.println("[" + jsonobject.getString("exitname") + jsonobject.getString("message"));
				interrupt();
				break;
			}
		}
	}
}
