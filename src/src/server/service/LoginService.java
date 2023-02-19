package src.server.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Servicio de login y logout
 * @author Víctor Ramón Pardilla Fernández
 */
public class LoginService {

	private Map<String, Boolean> mapLogin;
	private static LoginService loginService;

	private LoginService() {
		mapLogin = new HashMap<>();
	}

	/**
	 * Instancia única del LoginService en la aplicación, se sigue patrón singleton
	 * @return servicio LoginService
	 */
	public static LoginService getinstance() {
		if (loginService == null) {
			loginService = new LoginService();
		}

		return loginService;
	}

	/**
	 * Login usuario
	 * @param user
	 */
	public void login(String user) {
		mapLogin.put(user, true);
	}

	/**
	 * Comprueba si el usario está logueado
	 * @param user
	 * @return true or false
	 */
	public boolean isLogged(String user) {
		return mapLogin.containsKey(user) && mapLogin.get(user);
	}

	/**
	 * Desloguea al usuario
	 * @param user
	 * @return true or false
	 */
	public boolean logout(String user) {
		boolean logout = false;
		if (mapLogin.containsKey(user)) {
			mapLogin.remove(user);
			logout = true;
		}

		return logout;
	}
}
