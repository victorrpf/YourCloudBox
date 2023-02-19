package src.common;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Lectura de teclado
 * @author Víctor Ramón Pardilla Fernández
 *
 */
public class ReadKeyboardUtil {

	private Scanner scanner;
	private static ReadKeyboardUtil readKeyboardUtil;

	private ReadKeyboardUtil() {
		this.scanner = new Scanner(System.in);
	}

	public static ReadKeyboardUtil getInstance() {

		if (readKeyboardUtil == null) {
			readKeyboardUtil = new ReadKeyboardUtil();
		}

		return readKeyboardUtil;
	}

	public void close() {
		this.scanner.close();
	}

	public String readString(String message) {
		System.out.println(message);
		String string = scanner.next();

		return string;
	}

	public int readInt(String message) {
		if (message != null && !message.equals("")) {
			System.out.println(message);
		}
		boolean prim = true;
		int num = 0;

		while (prim && num == 0) {

			try {
				num = scanner.nextInt();
				prim = false;
			} catch (InputMismatchException e) {
				if (message != null && !message.equals("")) {
					System.out.println(message);
				}
				prim = true;
				scanner.next();
			}
		}

		return num;
	}

	public float readFloat(String message) {
		System.out.println(message);
		float real = scanner.nextFloat();

		return real;
	}

	public double readDouble(String message) {
		System.out.println(message);
		double real = scanner.nextDouble();

		return real;
	}

}
