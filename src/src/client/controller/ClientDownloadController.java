package src.client.controller;

import java.io.IOException;
import java.util.Base64;

import src.common.Channel;
import src.common.ReadKeyboardUtil;
import src.server.service.FileService;

/**
 * Petición del cliente de descargar archivos
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class ClientDownloadController {

	private Channel channel;
	private String user;
	private FileService fileService;

	public ClientDownloadController(Channel channel, String user, FileService fileService) {
		this.channel = channel;
		this.user = user;
		this.fileService = fileService;
	}

	public void download() throws IOException {
		if (user != null) {
			ReadKeyboardUtil readKeyboardUtil = ReadKeyboardUtil.getInstance();
			String pathServer = readKeyboardUtil.readString("Inserte la ruta del fichero a descargar");
			String pathClient = readKeyboardUtil.readString("Inserte la ruta donde se va a descargar el fichero");
			String downloadAction = "download#" + user + "#" + pathServer;

			channel.getSal().println(downloadAction);

			System.out.println("cliente espera respuesta para comenzar la descarga del fichero " + pathServer);
			String response = channel.getEnt().readLine();
			System.out.println(response);
			String[] aux = response.split("#");
			String fileName = aux[1];
			long totalBytes = Long.parseLong(aux[2]);

			byte[] dataCopy = new byte[(int) totalBytes];
			int g = 0;
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
				fileService.createFile(pathClient, fileName, dataCopy);
			}

		} else {
			System.out.println("El usuario no se ha autenticado.");
		}
	}
}
