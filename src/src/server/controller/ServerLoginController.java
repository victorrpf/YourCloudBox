package src.server.controller;

import java.util.List;

import src.common.Channel;
import src.common.security.SecurityService;
import src.server.model.User;
import src.server.service.LoginService;
import src.server.service.UserFileService;

/**
 * Para loguear usuarios
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class ServerLoginController {

	private UserFileService userFileService;
	private SecurityService securityService;

	public ServerLoginController(UserFileService userFileService, SecurityService securityService) {
		this.userFileService = userFileService;
		this.securityService = securityService;
	}

	public void login(Channel channel, String action) throws Exception {
		List<User> listUser = userFileService.loadUser();

		// action #register#user#password
		String[] aux = action.split("#");
		String userStr = aux[1];
		String pass = securityService.generateHash(aux[2]);

		String response = "";
		boolean exist = false;
		for (User user : listUser) {
			if (user.getName().equals(userStr) && user.getPass().equals(pass)) {
				exist = true;
				break;
			}
		}

		/*
		 * login#ok login#fail
		 */

		if (exist) {
			response = "login#ok";
			
			LoginService loginService = LoginService.getinstance();
			loginService.login(userStr);

			//Creamos el espacio del usuario
			userFileService.createSpaceUser(userStr);
		} else {
			response = "login#fail";
		}

		channel.getSal().println(response);
	}
}
