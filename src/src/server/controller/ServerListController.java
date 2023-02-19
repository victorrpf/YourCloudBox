package src.server.controller;

import src.common.Channel;
import src.server.service.FileService;
import src.server.service.LoginService;

/**
 * Lista los archivos del usario logueado
 * @author Víctor Ramón Pardilla Fernández
 *
 */
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
			// list#filename1, filename2 
			response = "list#" + fileService.listFileByUser(userStr);

		} else {
			// login#fail
			response = "login#fail";
		}
		channel.getSal().println(response);
	}
}
