package src.client.controller;

import java.io.IOException;
import java.util.Base64;

import src.common.Channel;
import src.common.ReadKeyboardUtil;
import src.server.service.FileService;

/**
 * Petición del cliente de subir archivos
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class ClientUploadController {

	private Channel channel;
	private String user;
	private FileService fileService;

	public ClientUploadController(Channel channel, String user, FileService fileService) {
		this.channel = channel;
		this.user = user;
		this.fileService = fileService;
	}

	public void upload() throws IOException {
		if (user != null) {
			ReadKeyboardUtil readKeyboardUtil = ReadKeyboardUtil.getInstance();
			String path = readKeyboardUtil.readString("Inserte la ruta del fichero a subir");
			String[] aux = path.split("/");
			String fileName = aux[aux.length - 1];
			long totalSize = fileService.existFile(path);

			// El fichero existe en el cliente
			if (totalSize > 1) {
				// Formamos el mensaje que envía el cliente
				// #upload#user#namefile#totalsize
				String uploadAction = "upload#" + user + "#" + fileName + "#" + totalSize;

				channel.getSal().println(uploadAction);

				System.out.println("cliente espera respuesta para comenzar la subida del fichero " + path);
				String response = channel.getEnt().readLine();
				System.out.println(response);

				// Convertir el fichero a un array de byte
				byte[] arrayByteFile = fileService.readFileToBytes(path);
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
				System.out.println("El fichero no existe");
			}
		} else {
			System.out.println("El usuario no se ha autenticado.");
		}
	}
}
