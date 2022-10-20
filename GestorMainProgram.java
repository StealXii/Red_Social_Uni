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
		LocalDate ld = LocalDate.now();
		gestor.cargarContactos("prueba2.txt");
		
		
		System.out.println("Bienvenido al gestor de Contactos. Seleccione una opcion: \n" );
		System.out.println("	1. Dar de alta contacto" );
		System.out.println("	2. Dar de baja contacto" );
		System.out.println("	3. Buscar contacto");
		System.out.println("	4. Consultar datos de contacto" );
		System.out.println("	5. Modificar contacto \n");
		System.out.println("	6. Cargar datos" );
		System.out.println("	7. Guardar datos\n" );
		System.out.println("	0. Salir\n\n" );
		
		Scanner sc = new Scanner(System.in);
	    int i = sc.nextInt();
		
		
		c = gestor.buscarContacto("GuinerveLealRivero@armyspy.com");
		if (c != null) {
			gestor.consultaContacto(c);
		}
		
	    resultado = gestor.buscarContacto("Octavia", "Ayala Marrero");
		if (resultado != null) {
			for (Contacto aux:resultado) {
				gestor.consultaContacto(aux);
			}
		}
		
		resultado = gestor.buscarContactoEdad(29);
		if (resultado != null) {
			for (Contacto aux:resultado) {
				gestor.consultaContacto(aux);
			}
		}
		
		
		
	}
	
}
