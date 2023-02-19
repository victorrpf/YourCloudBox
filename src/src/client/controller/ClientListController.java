package src.client.controller;

import java.io.IOException;

import src.common.Channel;

/**
 * Petición del cliente de listar archivos
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class ClientListController {

	private Channel channel;
	private String user;

	public ClientListController(Channel channel, String user) {
		this.channel = channel;
		this.user = user;
	}
	
	public void list() throws IOException {
		if(user != null) {
			// Formamos el mensaje que envía el cliente
			String listAction = "list#" + user;

			// Conectamos el flujo de escritura con el socket.
			channel.getSal().println(listAction);
			
			System.out.println("cliente espera respuesta listado de ficheros");
			String response = channel.getEnt().readLine();
			
			// #listar#filename1, filename2
			String[] aux = response.split("#");
			for (int i = 1; i < aux.length; i++) {
				System.out.println(aux[i]);
			}
		}else {
			System.out.println("El usuario no se ha autenticado.");
		}
	}
}
