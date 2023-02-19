package src.server.controller;

import src.common.Channel;
import src.server.service.LoginService;

/**
 * Para desloguear
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class ServerLogoutController {

	public boolean logout(Channel channel, String action) throws Exception {

		// action #logout#user
		String[] aux = action.split("#");
		String userStr = aux[1];

		LoginService loginService = LoginService.getinstance();

		return loginService.logout(userStr);
	}
}
