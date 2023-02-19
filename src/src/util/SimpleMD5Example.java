package src.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//pruebas de cifrado de password, actualmente no se usan en el proyecto presentado
public class SimpleMD5Example {
	public static void main(String[] args) {
		String passwordToHash = "password";
		String generatedPassword = null;

		// 5f4dcc3b5aa765d61d8327deb882cf99
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// Add password bytes to digest
			md.update(passwordToHash.getBytes());

			// Get the hash's bytes
			byte[] bytes = md.digest();

			// This bytes[] has bytes in decimal format. Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println(generatedPassword);
	}
}
