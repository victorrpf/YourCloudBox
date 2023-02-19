package src.server.util;

import src.common.Channel;
import src.common.security.SecurityService;
import src.server.controller.ServerDeleteFileController;
import src.server.controller.ServerDownloadFileController;
import src.server.controller.ServerListController;
import src.server.controller.ServerLoginController;
import src.server.controller.ServerLogoutController;
import src.server.controller.ServerRegisterController;
import src.server.controller.ServerUploadFileController;
import src.server.service.FileService;
import src.server.service.UserFileService;

public class Dispatcher {

	private ServerLoginController serverLoginController;
	private ServerRegisterController serverRegisterController;
	private ServerListController serverListController;
	private ServerLogoutController serverLogoutController;
	private ServerDeleteFileController serverDeleteFileController;
	private ServerUploadFileController serverUploadFileController;
	private ServerDownloadFileController serverDownloadFileController;

	private boolean logout;

	public Dispatcher() {
		SecurityService securityService = new SecurityService();
		UserFileService userFileService = new UserFileService();
		FileService fileService = new FileService();

		serverLoginController = new ServerLoginController(userFileService, securityService);
		serverRegisterController = new ServerRegisterController(userFileService, securityService);
		serverListController = new ServerListController(fileService);
		serverLogoutController = new ServerLogoutController();
		serverDeleteFileController = new ServerDeleteFileController(fileService);
		serverUploadFileController = new ServerUploadFileController(fileService);
		serverDownloadFileController = new ServerDownloadFileController(fileService);

		logout = false;
	}

	public void dispatcher(Channel channel) throws Exception {
		System.out.println("Servidor espera petición");
		String request = channel.getEnt().readLine();

		System.out.println("\n\nPetición: " + request + "\n\n");
		String[] partes = request.split("#");

		// Se recupera la acción
		String action = partes[0].toLowerCase();

		if (action.equals("register")) {
			serverRegisterController.register(channel, request);
		} else if (action.equals("login")) {
			serverLoginController.login(channel, request);
		} else if (action.equals("list")) {
			serverListController.list(channel, request);
		} else if (action.equals("delete")) {
			serverDeleteFileController.delete(channel, request);
		} else if (action.equals("upload")) {
			serverUploadFileController.upload(channel, request);
		} else if (action.equals("download")) {
			serverDownloadFileController.download(channel, request);
		} else if (action.equals("logout")) {
			logout = serverLogoutController.logout(channel, request);
		}
	}

	public boolean isLogout() {
		return logout;
	}
}
