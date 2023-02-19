package src.server.controller;

import src.common.Channel;
import src.server.service.FileService;
import src.server.service.LoginService;

public class ServerListController {

	private FileService fileService;

	public ServerListController(FileService fileService) {
		this.fileService = fileService;
	}

	public void list(Channel channel, String action) throws Exception {
		String response;
		LoginService loginService = LoginService.getinstance();

		// action #list#user
		String[] aux = action.split("#");
		String userStr = aux[1];

		// Si el usuario está logado
		if (loginService.isLogged(userStr)) {
			// list#filename1, filename2… login#fail
			response = "list#" + fileService.listFileByUser(userStr);

		} else {
			response = "login#fail";
		}
		channel.getSal().println(response);
	}
}
