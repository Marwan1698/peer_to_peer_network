import java.io.*;
import java.net.*;
import java.util.*;

public class ServerThread extends Thread {
	ServerSocket serverSocket;
	Set<TThread> tthreads = new HashSet<TThread>();
	public ServerThread(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}
	
	public void sendMessage(String message) {
		try {
			tthreads.forEach(t-> t.getPrintWriter().println(message));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Set<TThread> geTThreads() {
		return tthreads;
	}
	
	public void run() {
		try {
			while(true) {
				TThread tthread = new TThread(serverSocket.accept(), this);
				tthreads.add(tthread);
				tthread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
