package src.server.controller;

import src.common.Channel;
import src.server.service.FileService;
import src.server.service.LoginService;

public class ServerDeleteFileController {

	private FileService fileService;

	public ServerDeleteFileController(FileService fileService) {
		this.fileService = fileService;
	}

	public void delete(Channel channel, String action) throws Exception {
		String response;
		LoginService loginService = LoginService.getinstance();

		// action #delete#user
		String[] aux = action.split("#");
		String userStr = aux[1];
		String fileName = aux[2];

		if (loginService.isLogged(userStr)) {
			// delete#message,

			boolean isDelete = fileService.deleteFile(userStr, fileName);
			if (isDelete) {
				response = "delete#El fichero" + fileName + " se ha eliminado con éxito";
			} else {
				response = "delete#No existe el fichero " + fileName+", no se ha podido eliminar";
			}
		} else {
			response = "login#fail";
		}

		channel.getSal().println(response);
	}
}
