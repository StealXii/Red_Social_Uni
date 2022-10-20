package practica01;

import java.time.LocalDate; 
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorMainProgram {

	public static void main(String[] args) {
		
		GestorContactos gestor = GestorContactos.getInstance();
		Contacto c = null;
		ArrayList<Contacto> resultado = null;
		Scanner sc = new Scanner(System.in);
		String nombre = "";
		String apellidos = "";
		String email = "";
		String strAux = "";
		LocalDate fechaNacimiento = LocalDate.now();
		LocalDate ldactual = LocalDate.now();
		
		
		int option;
		
		do {
			// Imprime menu
			System.out.println("MENU DE OPCIONES:");
			System.out.println("\t1. Añadir contacto.");
			System.out.println("\t2. Modificar contacto.");
			System.out.println("\t3. Eliminar contacto.");
			System.out.println("\t4. Mostrar contactos.");
			System.out.println("\t5. Guardar cambios.");
			System.out.println("\t6. Cargar cambios.");
			System.out.println("\t0. Salir del programa");
			
			// Se pide la opcion al usuario
			System.out.print("Seleccione opción: ");
			option =  Integer.parseInt(sc.nextLine());
			
			// Añadir contacto
			if (option == 1) {
				System.out.println("AÑADIR CONTACTO:");
				
				System.out.print("\tCorreo electronico: ");
			    email = sc.nextLine();
			    
			    // Comprueba que el correo electronico esté disponible
				if (!gestor.existeContacto(email)) {
					System.out.print("\tNombre: ");
				    nombre = sc.nextLine();
				    
				    System.out.print("\tApellidos: ");
				    apellidos = sc.nextLine();
				    
				    System.out.print("\tFecha de nacimiento (dd/MM/yyyy): ");
				    strAux = sc.nextLine();
				    fechaNacimiento = LocalDate.parse(strAux, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				    
				    gestor.agregaContacto(nombre, apellidos, fechaNacimiento, email);
				} else {
					System.out.println("Error. Correo electrónico en uso.");
				}
				
				System.out.print("Pulse ENTER para continuar.");
				sc.nextLine();
			} 
			// Modificar contacto
			else if (option == 2) {
				System.out.print("Pulse ENTER para continuar.");
				sc.nextLine();
				sc.nextLine();
			}
			// Eliminar contacto
			else if (option == 3) {
				if (gestor.getNumContactos() > 0) {
					System.out.println("ELIMINAR CONTACTO:");
					System.out.print("Correo electronico del contacto a eliminar: ");
					email = sc.nextLine();
					
					c = gestor.buscarContacto(email);
					if (c != null) {
						System.out.println("Contacto encontrado: ");
						gestor.consultaContacto(c);
						System.out.print("¿Desea eliminarlo de la agenda? (S/N): ");
						strAux = sc.nextLine();
						if (strAux.equals("S") || strAux.equals("s")) {
							gestor.eliminaContacto(c.getId());
							System.out.print("Contacto eliminado correctamente. ");
						}
					} 
					else {
						System.out.print("No se ha encontrado el contacto. ");
					}
				}
				else {
					System.out.print("La agenda de contactos esta vacia. ");
				}
				
				System.out.print("Pulse ENTER para continuar.");
				sc.nextLine();
			}
			// Mostrar contactos
			else if (option == 4) {
				
				gestor.consultaContactos();
				
				System.out.print("Pulse ENTER para continuar.");
				sc.nextLine();
			}
			// Guardar cambios
			else if (option == 5) {
				System.out.print("Pulse ENTER para continuar.");
				sc.nextLine();
			}
			// Cargar cambios
			else if (option == 6) {
				
				fechaNacimiento = LocalDate.parse("16/10/1990", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				gestor.agregaContacto("Dulce", "Valenzuela Peres", fechaNacimiento, "DulceValenzuelaPeres@armyspy.com");
				fechaNacimiento = LocalDate.parse("22/12/1945", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				gestor.agregaContacto("Guinerve", "Leal Rivero", fechaNacimiento, "GuinerveLealRivero@armyspy.com");
				fechaNacimiento = LocalDate.parse("11/03/1991", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				gestor.agregaContacto("Octavia", "Ayala Marrero", fechaNacimiento, "OctaviaAyalaMarrero@teleworm.us");
				
				System.out.print("Pulse ENTER para continuar.");
				sc.nextLine();
			}
		} while (option != 0);
	}
}
