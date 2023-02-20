package src.server.controller;

import java.util.Base64;

import src.common.Channel;
import src.common.Constant;
import src.server.service.FileService;
import src.server.service.LoginService;

/**
 * Descarga los archivos del servidor
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class ServerDownloadFileController {

	private FileService fileService;

	public ServerDownloadFileController(FileService fileService) {
		this.fileService = fileService;
	}

	public void download(Channel channel, String action) throws Exception {
		String response = "";
		LoginService loginService = LoginService.getinstance();

		// action #download#user#fileName
		String[] aux = action.split("#");
		String userStr = aux[1];
		String pathServer = aux[2];

		if (loginService.isLogged(userStr)) {
			String pathComplete = Constant.PATH_SERVER + "/" + userStr + "/" + pathServer;
			long totalSize = fileService.existFile(pathComplete);
			aux = pathServer.split("/");
			String fileName = aux[aux.length - 1];
			// dowload#namefile
			channel.getSal().println("dowload#" + fileName + "#" + totalSize);

			// Convertir el fichero a un array de byte
			byte[] arrayByteFile = fileService.readFileToBytes(pathComplete);
			int pendientes = arrayByteFile.length;
			boolean hayb = true;
			int g = 0;
			int sizePackage = 512;

			// Realizar la subida del fichero
			while (hayb) {
				byte[] buffer = null;
				if (pendientes >= sizePackage) {
					buffer = new byte[sizePackage];
				} else {
					buffer = new byte[pendientes];
				}

				int i;
				int bytesSend = 0;
				for (i = 0; g < arrayByteFile.length; i++) {
					buffer[i] = arrayByteFile[g];
					bytesSend++;
					g++;
				}
				pendientes -= bytesSend;

				// #upload-content#numberBytes#bytes
				String encoded = "upload-content#" + bytesSend + "#" + Base64.getEncoder().encodeToString(buffer);

				// Enviamos un trozo del fichero
				channel.getSal().println(encoded);
				if (g >= arrayByteFile.length) {
					hayb = false;
				}
			}

		} else {
			response = "login#fail";
			channel.getSal().println(response);
		}

	}
}
