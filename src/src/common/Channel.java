package src.common;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Encapsula los elementos usados en la comunicación entre el cliente y el servidor. 
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class Channel {
	private Socket socket;
	private BufferedReader ent;
	private PrintWriter sal;

	public Channel(Socket socket, BufferedReader ent, PrintWriter sal) {
		this.socket = socket;
		this.ent = ent;
		this.sal = sal;
	}

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

}
