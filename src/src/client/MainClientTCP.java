package src.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import src.client.controller.ClientDeleteFileController;
import src.client.controller.ClientDownloadController;
import src.client.controller.ClientListController;
import src.client.controller.ClientLoginController;
import src.client.controller.ClientLogoutController;
import src.client.controller.ClientRegisterController;
import src.client.controller.ClientUploadController;
import src.client.util.Menu;
import src.common.Channel;
import src.common.security.SecurityService;
import src.server.service.FileService;

/**
 * Cliente principal - Run As Java Application
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class MainClientTCP {
	private static final int PUERTO_SERVIDOR = 5000;

	public static void main(String[] args) {
		int opcion = 0;
		Menu menu = new Menu();
		String user = null;
		Socket socket = null;
		BufferedReader ent = null;
		PrintWriter sal = null;

		try {

			SecurityService securityService = new SecurityService();
			FileService fileService = new FileService();

			// para coger datos del localhost
			InetAddress direccionServidor;

			// 1) Conexion con el servidor
			// obtenemos la dir de la maquina a la que nos queremos conectar
			direccionServidor = InetAddress.getByName("localhost");
			// Desarrollamos el shocket:
			socket = new Socket(direccionServidor, PUERTO_SERVIDOR);
			ent = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sal = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

			do {

				try {
					Channel channel = new Channel(socket, ent, sal);

					opcion = menu.mostrarMenu();
					switch (opcion) {
					case 1:
						ClientRegisterController registerController = new ClientRegisterController(securityService,
								channel);
						registerController.register();
						break;
					case 2:
						ClientLoginController loginController = new ClientLoginController(channel, securityService);
						user = loginController.login();
						break;
					case 3:
						ClientUploadController clientUploadController = new ClientUploadController(channel, user,
								fileService);
						clientUploadController.upload();
						break;
					case 4:
						ClientDownloadController clientDownloadController = new ClientDownloadController(channel, user,
								fileService);
						clientDownloadController.download();
						break;
					case 5:
						ClientListController clientList = new ClientListController(channel, user);
						clientList.list();
						break;
					case 6:
						ClientDeleteFileController deleteFileController = new ClientDeleteFileController(channel, user);
						deleteFileController.delete();
						break;
					case 7:
						ClientLogoutController clientLogout = new ClientLogoutController(channel, user);
						clientLogout.logout();
						break;
					default:
						System.out.println("Opción no disponible! Si querías salir, pulsa 7.");
						break;

					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}

			} while (opcion != 7);

		} catch (SocketException e) {
			System.err.println(e.getMessage());
		} catch (UnknownHostException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			if (sal != null) {
				sal.close();
			}

			try {
				// Cerramos todo
				if (ent != null) {
					ent.close();
				}

				if (socket != null) {
					socket.close();
				}

			} catch (IOException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
