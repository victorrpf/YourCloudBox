package src.server.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import src.common.Constant;

/**
 * Servicio de gestión de ficheros
 * 
 * @author Víctor Ramón Pardilla Fernández
 */
public class FileService {
	/**
	 * Crea el fichero en el directorio del usuario
	 * 
	 * @param pathBase ruta de almacenamiento
	 * @param fileName nombre fichero
	 * @param data     contenido fichero
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void createFile(String pathBase, String fileName, byte[] data) throws FileNotFoundException, IOException {
		// Generamos el fichero
		File dir = new File(pathBase);
		if (dir.exists()) {
			try (FileOutputStream fos = new FileOutputStream(pathBase + "/" + fileName)) {
				fos.write(data);
			}
		} else {
			throw new FileNotFoundException("No existe el directorio " + pathBase);
		}
	}

	/**
	 * Convierte el fichero en un array de bytes
	 * 
	 * @param filePath ruta del fichero
	 * @return un array de bytes para el fichero indicado en la ruta que se pasa
	 *         como parámetro
	 * @throws IOException
	 */
	public byte[] readFileToBytes(String filePath) throws IOException {

		File file = new File(filePath);
		byte[] bytes = new byte[(int) file.length()];

		// funny, if can use Java 7, please uses Files.readAllBytes(path)
		try (FileInputStream fis = new FileInputStream(file)) {
			fis.read(bytes);
		}

		return bytes;
	}

	/**
	 * Mide el tamaño del fichero si existe
	 * 
	 * @param path ruta del fichero
	 * @return tamaño del fichero
	 */
	public long existFile(String path) {
		long size = -1;
		File file = new File(path);
		if (file.exists()) {
			size = file.length();
		}

		return size;
	}

	/**
	 * Lista los ficheros del usuario logueado
	 * 
	 * @param user
	 * @return lista ficheros
	 */
	public String listFileByUser(String user) {
		String files = "";
		File dirUser = new File(Constant.PATH_SERVER + "/" + user);
		File[] arrayFiles = dirUser.listFiles();
		for (File file : arrayFiles) {
			files += file.getName() + "#";
		}

		return files;
	}

	/**
	 * Comprueba si el fichero está eliminado
	 * 
	 * @param user
	 * @param fileName
	 * @return true or false
	 */
	public boolean deleteFile(String user, String fileName) {
		boolean isDelete = false;
		File path = new File(Constant.PATH_SERVER + "/" + user + "/" + fileName);
		if (path.exists()) {
			if (path.isDirectory()) {
				isDelete = deleteDirectory(path);
			} else {
				isDelete = path.delete();
			}
		}

		return isDelete;
	}

	/**
	 * Elimina el contenido del directorio y el directorio.
	 * 
	 * @param directoryToBeDeleted directorio a eliminar.
	 * @return true si se ha podido eliminar el directorio.
	 */
	boolean deleteDirectory(File directoryToBeDeleted) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteDirectory(file);
			}
		}
		return directoryToBeDeleted.delete();
	}
}
