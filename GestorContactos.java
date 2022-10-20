/**
* Gestor de contactos de usuarios.
*
* @author Muñoz Jimenez, Juan Pedro (p52mujij@uco.es)
* @author Sevilla Molina, Angel (i42semoa@uco.es)
*/

package practica01;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class GestorContactos {

	private static GestorContactos instance = null; //!< singleton
	private ArrayList<Contacto> contactos; //!< contactos a gestionar
	private int idDisponible; //!< identificador de usuario disponible para uso
	private String[] nombresIntereses; //!< 
	
	/**
	* Crea la agenda con los contactos.
	*/
	private GestorContactos() {
		this.contactos = new ArrayList<Contacto>();
		this.nombresIntereses = new String[10];
		nombresIntereses[0]="Deporte";
		nombresIntereses[1]="Oficina";
		nombresIntereses[2]="Turismo";
		nombresIntereses[3]="Cultura";
		nombresIntereses[4]="Videojuegos";
		nombresIntereses[5]="Cine";
		nombresIntereses[6]="Arte";
		nombresIntereses[7]="Ciencia";
		nombresIntereses[8]="Religion";
		nombresIntereses[9]="Salud";
		idDisponible = 0;
	}

	/**
    * Obtiene acceso al gestor de contactos.
    *
	* @return instance  el gestor de contactos
    */
	public static GestorContactos getInstance() {
		if (instance == null) {
			instance = new GestorContactos();
		}
		return instance;
	}
	
	public int getNumContactos() {
		return contactos.size();
	}
	
	/**
	* Carga los contactos almacenados en un fichero de texto.
	*
    * @param nombreFichero  la cadena con el nombre del fichero
	*/
	public void cargarContactos(String nombreFichero) {
		File fichero = new File(nombreFichero);
		Scanner s = null;
		int idAux= 0;
		String nameAux= null;
		String name2Aux= null;
		String emailAux= null;
		LocalDate dateAux= null;
		Boolean[] interesesAux;
		Contacto c = null;
		
		this.vaciarAgenda();
		
		try {
			s = new Scanner(fichero);
			interesesAux = new Boolean[10];
			
			String linea = s.nextLine();
			idDisponible = Integer.parseInt(linea);
			
			while (s.hasNextLine()) {
				linea = s.nextLine();
				String[] parts = linea.split(";");
				idAux = Integer.parseInt(parts[0]);
				nameAux = parts[1];
				name2Aux = parts[2];
				emailAux = parts[3];
				dateAux = LocalDate.parse(parts[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				String[] boleanosAux = parts[5].split(",");
				
				
				for(int i=0; i<boleanosAux.length; i++) {
					interesesAux[i] = Boolean.parseBoolean(boleanosAux[i]);
				}
				c = new Contacto(idAux, nameAux, name2Aux, dateAux, emailAux, interesesAux);
				contactos.add(c);
			}
			
		} catch (Exception ex){
				
		} finally {
			if (s != null)
				s.close();
		}
	}
	
	/**
	* Registra en un fichero de texto los contactos del gestor.
	*
    * @param nombreFichero  la cadena con el nombre del fichero
	*/
	public void guardarContactos(String nombreFichero) {
		Iterator<Contacto> it = contactos.iterator(); 
		FileWriter fichero = null;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String interesesAux;
		
		try {

			fichero = new FileWriter(nombreFichero);	
			
			fichero.write(this.idDisponible + "\n");
			while (it.hasNext()) { 
				Contacto c = it.next();
				interesesAux = String.valueOf(c.esInteres(0));
				for(int i =1; i<10; i++) {
					interesesAux = interesesAux + "," + String.valueOf(c.esInteres(i));
				}
				fichero.write(c.getId() + ";" + c.getNombre() + ";" + c.getApellidos() + ";" + c.getEmail() + ";" + c.getFechaNacimiento().format(df) + ";" + interesesAux + "\n");
			}

			System.out.println("Fichero guardado");
			fichero.close();

		} catch (Exception ex) {
			System.out.println("Mensaje de la excepcion: " + ex.getMessage());
		}
		
	}
	
	/**
	* Agrega un contacto en el gestor con información personal especificada.
	* El usuario debe verificar previamente que el email no esté en uso.
	*
    * @param nombre           la cadena con el nombre personal del contacto 
	* @param apellidos        la cadena con los apellidos del contacto 
	* @param fechaNacimiento  la fecha de nacimiento del contacto 
	* @param email            la cadena con el correo electronico del contacto 
	*/
	public void agregaContacto(String nombre, String apellidos, 
	                LocalDate fechaNacimiento, String email, boolean[] intereses) {
		Contacto c = null;
		c = new Contacto(idDisponible, nombre, apellidos, fechaNacimiento, email, intereses);
		contactos.add(c);
		idDisponible = idDisponible + 1;
	}
	
	/**
	* Actualiza la información de un contacto indicado por medio de su identificador.
	*
	* @param id               el identificador del contacto a modificar
    * @param nombre           la cadena con el nuevo nombre personal del contacto 
	* @param apellidos        la cadena con los nuevos apellidos del contacto 
	* @param fechaNacimiento  la nueva fecha de nacimiento del contacto 
	* @param email            la cadena con el nuevo correo electronico del contacto 
	*/
	public void actualizaContacto(int id, String nombre, String apellidos, 
	                LocalDate fechaNacimiento, String email) {
		for (Contacto c:contactos) {
			if (c.getId() == id) {
				c.setNombre(nombre);
				c.setApellidos(apellidos);
				c.setFechaNacimiento(fechaNacimiento);
				c.setEmail(email);
			}
		}
	}
	
	/**
	* Elimina un contacto del gestor
	*
    * @param id  el codigo de identificacion del contacto
	*/
	public void eliminaContacto(int id) {
		Iterator<Contacto> it = contactos.iterator(); 
		boolean found = false;
		
		while (it.hasNext() & !found) { 
			Contacto c = it.next(); 
			if (c.getId() == id) { 
				it.remove(); 
				found = true;
			}
		}
	}
	
	/**
	* Elimina todos los contactos del gestor
	*/
	public void vaciarAgenda() {
		Iterator<Contacto> it = contactos.iterator(); 
		while (it.hasNext()) { 
			it.remove(); 
		}
		idDisponible = 0;
	}
	
	/**
    * Imprime la informacion del contacto por consola.
    *
	* @param c  el contacto cuya informacion se va a mostrar.
    */
	public void consultaContacto(Contacto c) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.println("Nombre: " + c.getNombre());
		System.out.println("Apellidos: " + c.getApellidos());
		System.out.println("Fecha de nacimiento: " + c.getFechaNacimiento().format(df));
		System.out.println("Email: " + c.getEmail());
		System.out.println("Intereses: ");
		for(int i =0; i<10; i++) {
			if (c.esInteres(i)) {
				System.out.println("   - " + this.nombresIntereses[i]);
			}
		}
	}
	
	public void consultaContactos() {
		for (Contacto c:contactos) {  
			consultaContacto(c);
		}
	}
	
	/**
	* Comprueba si un correo electronico pertenece a un contacto de la agenda.
	*
	* @param email  la cadena con el correo electronico que se pretende buscar
	*/
	public boolean existeContacto(String email) {
		Contacto c = buscarContacto(email);
		return (c != null);
	}
	
	/**
	* Obtiene la informacion del contacto que tenga un email determinado. 
	*
	* @param email  la cadena con el correo electronico que se pretende buscar
	*/
	public Contacto buscarContacto(String email) {
		Contacto resultado = null;
		
		for (Contacto c:contactos) {  
			if (c.getEmail().equals(email))  {
				resultado = c;
				return resultado;
			}
		}
		
		return resultado;
	}
	
	/**
	* Obtiene la informacion de los contactos que tengan un nombre determinado 
	*
	* @param nombre     la cadena con el nombre que se pretende buscar
	* @param apellidos  la cadena con los apellidos que se pretende buscar
	*/
	public ArrayList<Contacto> buscarContacto(String nombre, String apellidos) {
		ArrayList<Contacto> resultado = new ArrayList<Contacto>();
		
		for (Contacto c:contactos) {   
			if (c.getNombre().equals(nombre) & c.getApellidos().equals(apellidos))  {
				resultado.add(c);
			}   
		}
		return resultado;
	}
	
	/**
	* Obtiene la informacion de los contactos que tengan una edad determinada.
	*
	* @param nombre  la edad de los contactos que se pretenden buscar
	*/
	public ArrayList<Contacto> buscarContactoEdad(int edad) {
		ArrayList<Contacto> resultado = new ArrayList<Contacto>();
		
		for (Contacto c:contactos) {   
			if (c.getEdad() == edad)  {
				resultado.add(c);
			}   
		}
		return resultado;
	}
}
