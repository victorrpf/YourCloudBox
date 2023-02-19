package src.common;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Channel {

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public BufferedReader getEnt() {
		return ent;
	}

	public void setEnt(BufferedReader ent) {
		this.ent = ent;
	}

	public PrintWriter getSal() {
		return sal;
	}

	public void setSal(PrintWriter sal) {
		this.sal = sal;
	}

	private Socket socket;
	private BufferedReader ent;
	private PrintWriter sal;

	public Channel(Socket socket, BufferedReader ent, PrintWriter sal) {
		this.socket = socket;
		this.ent = ent;
		this.sal = sal;
	}

}
