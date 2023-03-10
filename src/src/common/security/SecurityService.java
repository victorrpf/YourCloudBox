package src.common.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Encripta las contraseñas de los usuarios.
 * MD5 viene del inglés "Message Digest Algorithm 5". 
 * Es un algoritmo de codificación de 128 bits que se compone de 32 caracteres hexadecimales.
 * Sirve para encritpar archivos y contraseñas en bases de datos. 
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class SecurityService {
	private static final String MD5 = "MD5";
	private static final byte[] SALT = createSalt();

	public String generateHash(String data) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance(MD5);
		digest.reset();
		byte[] hash = digest.digest(data.getBytes());
		String md5Hex = DigestUtils.md5Hex(hash).toUpperCase();
		return md5Hex;
	}
	
	public  String generateHashSalt(String data) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance(MD5);
		digest.reset();
		digest.update(SALT);
		byte[] hash = digest.digest(data.getBytes());
		String md5Hex = DigestUtils.md5Hex(hash).toUpperCase();
		return md5Hex;
	}
	
	private static byte[] createSalt() {
		//byte[] bytes = new byte[20];
		//SecureRandom random = new SecureRandom();
		//random.nextBytes(bytes);
		return "s123ertgfesehtygjuurhshs".getBytes();
	}
	
}
