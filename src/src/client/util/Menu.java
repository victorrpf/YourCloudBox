package src.client.util;

import src.common.ReadKeyboardUtil;

public class Menu {

	public int mostrarMenu() {
		int opcion = 0;
		do {
			System.out.println("\n========MENU========");
			System.out.println("1) Registro");
			System.out.println("2) Login.");
			System.out.println("3) Upload fichero");
			System.out.println("4) Download fichero");
			System.out.println("5) Listar");
			System.out.println("6) Eliminar fichero.");
			System.out.println("7) Salir.");
			System.out.println("====================");

			opcion = pedirEntero();

		} while (opcion > 8 || opcion < 1);

		return opcion;
	}

	// método para validar si se ha escrito un número entero
	private int pedirEntero() {
		ReadKeyboardUtil readKeyboardUtil =ReadKeyboardUtil.getInstance();
		boolean enteroOk = false;
		int entero = 0;
		while (!enteroOk) {
			try {
				entero = readKeyboardUtil.readInt("\nEscribe una opción (número entero): ");
				enteroOk = true;
			} catch (NumberFormatException e) {
				System.err.println("No has escrito un número entero.");
			}
		}

		return entero;
	}
}
