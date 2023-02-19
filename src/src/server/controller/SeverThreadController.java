package src.server.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import src.common.Channel;
import src.server.util.Dispatcher;

/**
 * Esta clase tiene la responsabilidad de atender las peticiones de los clientes de forma concurrente sin esperas.
 * La petición de cada cliente se atiende en un hilo.
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class SeverThreadController implements Runnable {

	private Socket socket;
	private Thread hilo;

	public SeverThreadController(Socket socket) {

		// almacena todos los clientes que se conecten
		this.socket = socket;

		this.hilo = new Thread(this);
		hilo.start();
	}

	public void run() {

		try {
			// Establece canal de salida
			BufferedReader ent = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter sal = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			
			Dispatcher dispatcher = new Dispatcher();
			Channel channel = new Channel(socket, ent, sal);

			while (!dispatcher.isLogout()) {
				dispatcher.dispatcher(channel);
			}

			// Cerramos la conexión con el proceso cliente.
			System.out.println("Se cierra la conexión con el cliente");
			ent.close();
			sal.close();
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
