package src.client.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import src.common.Channel;
import src.common.ReadKeyboardUtil;
import src.common.security.SecurityService;

public class ClientLoginController {

	private Channel channel;
	private SecurityService securityService;

	public ClientLoginController(Channel channel, SecurityService securityService) {
		this.channel = channel;
		this.securityService = securityService;
	}

	public String login() throws IOException, NoSuchAlgorithmException {

		String userLogged = null;
		ReadKeyboardUtil readKeyboardUtil = ReadKeyboardUtil.getInstance();
		String user = readKeyboardUtil.readString("Inserte el nombre del usuario");
		String pass = readKeyboardUtil.readString("Inserte la password");
		pass = securityService.generateHashSalt(pass);

		// Formamos el mensaje que envía el cliente
		String loginAction = "login#" + user + "#" + pass;

		// Conectamos el flujo de escritura con el socket.
		channel.getSal().println(loginAction);

		System.out.println("cliente espera respuesta login");
		String response = channel.getEnt().readLine();

		/*
		 * Posibles respuestas del servidor #login#ok, #login#fail
		 */
		String[] aux = response.split("#");
		if (aux[1].equals("ok")) {
			System.out.println("El usuario " + user + " se ha autenticado con éxito.");
			userLogged = user;
		} else if (aux[1].equals("fail")) {
			System.out.println("Usuario/password incorrectos.");
		}
		
		return userLogged;
	}
}
