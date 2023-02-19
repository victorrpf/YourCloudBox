package src.server.model;

/**
 * Representa un usuario
 * @author Víctor Ramón Pardilla Fernández
 */
public class User {

	private String name;
	private String pass;

	/**
	 * Constructor de usuarios
	 * @param name
	 * @param pass
	 */
	public User(String name, String pass) {
		this.name = name;
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
