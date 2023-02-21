package src.server.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import src.common.Constant;
import src.server.model.User;

/**
 * Servicio de gestión de directorios y usuarios
 * @author Víctor Ramón Pardilla Fernández
 */
public class UserFileService {
	
	/**
	 * Crea nuevos directorios
	 * @param nameUser
	 */
	public void createSpaceUser(String nameUser) {
		File dirServer = new File(Constant.PATH_SERVER);
		
		// comprueba que exista el directorio del Servidor para crearlo o no
		if (!dirServer.exists()) {
			dirServer.mkdir();
		}
		
		String partUser = Constant.PATH_SERVER + "/" + nameUser;
		File dirUser = new File(partUser);
		// comprueba que exista el directorio del Cliente para crearlo o no
		if (!dirUser.exists()) {
			dirUser.mkdir();
		}
	}
	
	/**
	 * Busca un usuario en el fichero de usuarios registrados
	 * @return
	 * @throws Exception
	 */
	public List<User> loadUser() throws Exception{
		List<User> listUser = new ArrayList<>();
		BufferedReader fileUser = new BufferedReader(new FileReader("resources/users.txt"));
		String line;
		while ((line = fileUser.readLine()) != null) {

			String[] aux = line.split(";");
			User user = new User(aux[0], aux[1]);
			listUser.add(user);
			
		}
		fileUser.close();
		
		return listUser;
	}
	
	/**
	 * Registra un nuevo usuario en el fichero
	 * @param listUser
	 * @throws FileNotFoundException
	 */
	public void storeUser(List<User> listUser) throws FileNotFoundException {
		PrintWriter salida = new PrintWriter(new File("resources/users.txt"));
		for(User user: listUser) {
			salida.println(user.getName()+";"+user.getPass());
		}
		salida.close();
	}
	
}
