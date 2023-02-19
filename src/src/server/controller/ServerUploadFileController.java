package src.server.controller;

import java.util.Base64;

import src.common.Channel;
import src.common.Constant;
import src.server.service.FileService;
import src.server.service.LoginService;

/**
 * Sube los archivos al servidor
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class ServerUploadFileController {

	private FileService fileService;

	public ServerUploadFileController(FileService fileService) {
		this.fileService = fileService;
	}

	/**
	 * 
	 * @param channel
	 * @param action
	 * @throws Exception
	 */
	public void upload(Channel channel, String action) throws Exception {
		String response = "";
		LoginService loginService = LoginService.getinstance();

		// action #upload#user#fileName#totalSize
		String[] aux = action.split("#");
		String userStr = aux[1];

		if (loginService.isLogged(userStr)) {
			String fileName = aux[2];
			long totalBytes = Long.parseLong(aux[3]);
			byte[] dataCopy = new byte[(int) totalBytes];
			int g = 0;

			// upload#namefile
			channel.getSal().println("upload#" + fileName);

			// Reconstruir el fichero
			while (totalBytes > 0) {
				response = channel.getEnt().readLine();
				// upload-content#numberBytes#bytes
				aux = response.split("#");
				String fileBase64 = aux[2];

				byte[] decoded = Base64.getDecoder().decode(fileBase64);
				for (int i = 0; i < decoded.length && g < dataCopy.length; i++) {
					dataCopy[g] = decoded[i];
					g++;
				}
				totalBytes -= decoded.length;
			}

			if (g > 0) {
				fileService.createFile(Constant.PATH_SERVER, userStr + "/" + fileName, dataCopy);
			}

		} else {
			response = "login#fail";
		}

		channel.getSal().println(response);
	}
}
