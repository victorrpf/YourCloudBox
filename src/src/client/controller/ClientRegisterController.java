package src.client.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import src.common.Channel;
import src.common.ReadKeyboardUtil;
import src.common.security.SecurityService;

/**
 * Petición del cliente de registrarse archivos
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class ClientRegisterController {

	private SecurityService securityService;
	private Channel channel;

	public ClientRegisterController(SecurityService securityService, Channel channel) {
		this.securityService = securityService;
		this.channel = channel;
	}

	public void register() throws IOException, NoSuchAlgorithmException {

		ReadKeyboardUtil readKeyboardUtil = ReadKeyboardUtil.getInstance();
		String user = readKeyboardUtil.readString("Inserte el nombre del usuario");
		String pass = readKeyboardUtil.readString("Inserte la password");
		String confirmPass = readKeyboardUtil.readString("Confirmar password");
		
		pass = securityService.generateHashSalt(pass);
		confirmPass = securityService.generateHashSalt(confirmPass);

		if (pass.equals(confirmPass)) {

			// Formamos el mensaje que envía el cliente
			String registerAction = "register#" + user + "#" + pass;

			// Conectamos el flujo de escritura con el socket.
			channel.getSal().println(registerAction);

			System.out.println("cliente espera respuesta registro");
			String response = channel.getEnt().readLine();

			/*
			 * Posibles respuestas del servidor #register#useralreadyexist
			 * #register#usercreated
			 */
			String[] aux = response.split("#");
			if (aux[1].equals("useralreadyexist")) {
				System.out.println("El usuario ya existe, debe insertar un nombre que no está registrado.");
			} else if (aux[1].equals("usercreated")) {
				System.out.println("El usuario " + user + " se ha creado con éxito");
			}
		}else {
			System.out.println("Las password no coinciden, vuelva a registrarse.");
		}
	}
}
