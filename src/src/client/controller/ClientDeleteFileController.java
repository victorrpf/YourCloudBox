package src.client.controller;

import java.io.IOException;

import src.common.Channel;
import src.common.ReadKeyboardUtil;

/**
 * Petición del cliente de eliminar archivos
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class ClientDeleteFileController {

	private Channel channel;
	private String user;

	public ClientDeleteFileController(Channel channel, String user) {
		this.channel = channel;
		this.user = user;
	}

	public void delete() throws IOException {
		if (user != null) {

			ReadKeyboardUtil readKeyboardUtil = ReadKeyboardUtil.getInstance();
			String fileName = readKeyboardUtil.readString("Inserte el nombre del fichero a eliminar: ");

			// Formamos el mensaje que envía el cliente
			String deleteAction = "delete#" + user + "#" + fileName;

			// Conectamos el flujo de escritura con el socket.
			channel.getSal().println(deleteAction);

			System.out.println("cliente espera respuesta al eliminar el fichero "+fileName);
			String response = channel.getEnt().readLine();

			// #delete#mensaje
			String[] aux = response.split("#");
			System.out.println(aux[1]);
			
		} else {
			System.out.println("El usuario no se ha autenticado.");
		}
	}
}
