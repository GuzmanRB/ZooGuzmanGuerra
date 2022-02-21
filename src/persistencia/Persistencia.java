package persistencia;

import java.util.List;
import java.util.concurrent.ExecutionException;

import clasesZoo.Animal;
import clasesZoo.Empleado;
import clasesZoo.Entrada;
import clasesZoo.Evento;

public interface Persistencia {
//	public void
	
	
	public boolean guardar(Object a) throws Exception; //Guarda cualquier objeto
	public boolean borrar(Object a) throws Exception; //Borrar cualquier objeto
	
	//METODSOS DE ANIMALES
	public List<Animal> consultarAnimales(String nombre, Integer especie) throws Exception;
	public Animal consultarAnimalID (Integer id) throws Exception;
	public Animal conultarAnimalUnico(String nombre, Integer especie) throws Exception;
	
	public Object consultarUnico(String clase, String desc) throws Exception; // Consulta un valor pasandole la clase y la descripcion 
	public List<Object> consultarPorDesc(String clase, String desc) throws Exception; //Consulta un conjunto de valores pasandoles la clase y la descripcion
	
	//METODOS DE EMPLEADO
	public Empleado consultarEmpleadoID(Integer id) throws Exception;
	public Empleado consultarEmpleadoUnico(String nombre, String direccion) throws Exception;
	public List<Empleado> consultarEmpleados(String nombre, String direccion) throws Exception;
	
	
	//METODOS DE EVENTOS
	public Evento consultarEventoID(Integer id) throws Exception;
	public Evento consultarEventoUnico(String desc) throws Exception;
	public List<Evento> consultarEventos(String desc) throws Exception;
	
	//METODOS DE ENTRADAS
	public List<Entrada> consultarEntradas(Integer id) throws Exception;

}
