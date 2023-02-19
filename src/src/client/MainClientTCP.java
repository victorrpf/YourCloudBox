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
import java.security.NoSuchAlgorithmException;

import src.client.controller.ClientDownloadController;
import src.client.controller.ClientListController;
import src.client.controller.ClientLoginController;
import src.client.controller.ClientLogoutController;
import src.client.controller.ClientRegisterController;
import src.client.controller.ClientUploadController;
import src.client.controller.DeleteFileController;
import src.client.util.Menu;
import src.common.Channel;
import src.common.security.SecurityService;
import src.server.service.FileService;

public class MainClientTCP {
	private static final int PUERTO_SERVIDOR = 5000;

	public static void main(String[] args) {
		int opcion = 0;
		Menu menu = new Menu();
		String user = null;

		try {

			SecurityService securityService = new SecurityService();
			FileService fileService = new FileService();

			// todo esto igual que siempre
			InetAddress direccionServidor;

			// 1) Conexion con el servidor
			// obtenemos la dir de la maquina a la que nos queremos conectar
			direccionServidor = InetAddress.getByName("localhost");
			// Desarrollamos el shocket:
			Socket socket = new Socket(direccionServidor, PUERTO_SERVIDOR);
			BufferedReader ent = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter sal = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
					true);

			do {

				Channel channel = new Channel(socket, ent, sal);

				opcion = menu.mostrarMenu();
				switch (opcion) {
				case 1:
					ClientRegisterController registerController = new ClientRegisterController(securityService, channel);
					registerController.register();
					break;
				case 2:
					ClientLoginController loginController = new ClientLoginController(channel, securityService);
					user = loginController.login();
					break;
				case 3:
					ClientUploadController clientUploadController = new ClientUploadController(channel, user, fileService);
					clientUploadController.upload();
					break;
				case 4:
					ClientDownloadController clientDownloadController = new ClientDownloadController(channel, user,	fileService);
					clientDownloadController.download();
					break;
				case 5:
					ClientListController clientList = new ClientListController(channel, user);
					clientList.list();
					break;
				case 6:
					DeleteFileController deleteFileController = new DeleteFileController(channel, user);
					deleteFileController.delete();
					break;
				case 7:
					ClientLogoutController clientLogout = new ClientLogoutController(channel, user);
					clientLogout.logout();
					break;
				default:
					System.out.println("Opción no disponible! Si querías salir, pulsa 8.");
					break;

				}
			} while (opcion != 8);

			// Cerramos todo
			ent.close();
			sal.close();
			socket.close();

		} catch (SocketException e) {
			System.err.println(e.getMessage());
		} catch (UnknownHostException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
		}
	}
}
