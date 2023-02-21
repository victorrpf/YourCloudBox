package src.client.controller;

import java.io.IOException;

import src.common.Channel;

/**
 * Petición del cliente de desloguearse archivos
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class ClientLogoutController {

	private Channel channel;
	private String user;

	public ClientLogoutController(Channel channel, String user) {
		this.channel = channel;
		this.user = user;
	}
	
	public void logout() throws IOException {
		if(user != null) {
			// Formamos el mensaje que envía el cliente
			String logoutAction = "logout#" + user;

			// Conectamos el flujo de escritura con el socket.
			channel.getSal().println(logoutAction);
			channel.getEnt().readLine();
		}else {
			System.out.println("El usuario no se ha autenticado.");
		}
		System.out.println("Saliendo...");
	}
}
