import java.io.*;
import java.net.*;
import javax.json.*;

public class Main {
	static Database database;
	public static void main(String[] args) throws IOException {
		database = new Database();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter IP-username-port number > ");
		String[] IPnamePortnum = reader.readLine().split("-");
		String IP = IPnamePortnum[0].trim();
		String username = IPnamePortnum[1].trim();
		int portNum = Integer.parseInt(IPnamePortnum[2].trim());
		database.insert(IP, username, portNum);
		database.selectAll(username);
		ServerThread serverThread = new ServerThread(portNum);
		serverThread.start();
		new Main().update(reader, username, serverThread);
	}

	public void update(BufferedReader reader, String username, ServerThread serverThread) throws IOException {
		System.out.println("Enter username for each peer");
		String[] peers = reader.readLine().split("/");
		for (int i = 0; i < peers.length; i++) {
			//String[] data = peers[i].trim().split(":");
			String IP = database.selectIP(peers[i]);
			int portNum = database.selectPort(peers[i]);
			Socket socket = new Socket(IP, portNum);
			new PeerThread(socket).start();
		}
		communicate(reader, username, serverThread);
	}

	public void communicate(BufferedReader reader, String username, ServerThread serverThread) throws IOException {
		System.out.println("Communication is Started!");
		System.out.println("To go out enter 'exit'\nTo change peers enter 'change'");
		boolean f = true;
		while (f) {
			String message = reader.readLine();
			if (message.equals("exit")) {
				f = false;
				database.logout(username);
				String leave = " left the group]";
				StringWriter writer = new StringWriter();
				Json.createWriter(writer).writeObject(
						Json.createObjectBuilder().add("exitname", username).add("message", leave).build());
				serverThread.sendMessage(writer.toString());
				break;
			} else if (message.equals("change")) {
				update(reader, username, serverThread);
			} else {
				StringWriter writer = new StringWriter();
				Json.createWriter(writer).writeObject(
						Json.createObjectBuilder().add("username", username).add("message", message).build());
				serverThread.sendMessage(writer.toString());
			}
		}
		serverThread.interrupt();
	}

}
