package src.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import src.server.controller.SeverThreadController;

/**
 * Servidor concurrente - Run As Java Application
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class MainServidorConcurrenteTCP {

	// servidor atiende las peticiones de forma concurrente
	public static void main(String[] args) throws IOException {

		int puerto = 5000;
		ServerSocket socketServidor = null;

		try {
			socketServidor = new ServerSocket(puerto);

			while (true) {
				System.out.println("Servidor TCP concurrente esperando una petición...\n");

				Socket socketCliente = socketServidor.accept();
				System.out.println("Servidor atiende petición");
				new SeverThreadController(socketCliente);
			}
		} catch (IOException e) {
			System.out.println("Puerto en uso");
			e.printStackTrace();
		} finally {
			if (socketServidor != null) {
				socketServidor.close();
			}
		}
	}
}
