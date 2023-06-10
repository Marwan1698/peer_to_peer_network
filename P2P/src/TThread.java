import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class TThread extends Thread {
	ServerThread serverThread;
	Socket socket;
	private PrintWriter printWriter;

	public TThread(Socket socket, ServerThread serverThread) {
		this.serverThread = serverThread;
		this.socket = socket;
	}

	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.printWriter = new PrintWriter(socket.getOutputStream(), true);
			while (true) {
				serverThread.sendMessage(reader.readLine());
			}
		} catch (Exception e) {
			serverThread.geTThreads().remove(this);
		}
	}

	public PrintWriter getPrintWriter() {
		return printWriter;
	}
}
