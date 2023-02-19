package src.server.controller;

import java.util.List;

import src.common.Channel;
import src.common.security.SecurityService;
import src.server.model.User;
import src.server.service.UserFileService;

/**
 * Registra los nuevos usuarios
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class ServerRegisterController {
	
	private UserFileService userFileService;
	private SecurityService securityService;
	
	public ServerRegisterController(UserFileService userFileService, SecurityService securityService) {
		this.userFileService = userFileService;
		this.securityService = securityService;
	}

	public void register(Channel channel, String action) throws Exception {
		List<User> listUser = userFileService.loadUser();
		
		// action #register#user#password
		String[] aux = action.split("#");
		String userStr = aux[1];
		String pass = securityService.generateHash(aux[2]);
		
		String response = "";
		boolean exist = false;
		for(User user: listUser) {
			if(user.getName().equals(userStr)) {
				response = "register#useralreadyexist";
				exist = true;
				break;
			}
		}
		
		if(!exist) {
			// Registrar al usuario
			User newUser = new User(userStr, pass);
			listUser.add(newUser);
			userFileService.storeUser(listUser);
			response = "#register#usercreated";
		}
		/*
		 * response
		 * #register#useralreadyexist
		 * #register#usercreated
		 */
		channel.getSal().println(response);
	}
}
