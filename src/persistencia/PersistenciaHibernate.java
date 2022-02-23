package persistencia;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import clasesZoo.Alimento;
import clasesZoo.Animal;
import clasesZoo.Empleado;
import clasesZoo.Entrada;
import clasesZoo.Especie;
import clasesZoo.Evento;
import clasesZoo.Nomina;
import clasesZoo.Tratamiento;
import clasesZoo.Zona;

public class PersistenciaHibernate implements Persistencia{
	Session sesion;
	Transaction transaccion;

	public PersistenciaHibernate() {
		SessionFactory sessionFactory;
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		sesion = sessionFactory.openSession();
	}

	public boolean guardar(Object a,String tabla) {
		sesion.beginTransaction();
		sesion.save(a);
		sesion.getTransaction().commit();
		sesion.refresh(a);
		return true;
	}
	public boolean borrar(Object a, String tabla) {
		sesion.beginTransaction();
		sesion.delete(a);
		sesion.getTransaction().commit();
		return true;
	}
	//REFRESCAR OBJETOS
	public void refresh(Object o, String tabla) throws Exception {
		sesion.beginTransaction();
		sesion.refresh(o);
		sesion.getTransaction().commit();
		
	}
	public List<Animal> consultarAnimales(String nombre, Integer especie) {
		List<Animal> la;
		Query q;
		
		if (especie==null) {
			q = sesion.createQuery("SELECT a FROM Animal a WHERE nombre LIKE '%"+nombre+"%'");
			la = q.list();
		}else {
			q = sesion.createQuery("SELECT a FROM Animal a WHERE nombre LIKE '%"+nombre+"%' AND idEspecie=?");
			
			q.setInteger(0, especie);
			la = q.list();
		}

		return la;
	}
	public Animal consultarAnimalID (Integer id) {
		
		Query q=sesion.createQuery("SELECT a FROM Animal a WHERE id=?");
		q.setInteger(0, id);
		Animal a=(Animal) q.uniqueResult();
		return a;
	}
	
	public Animal conultarAnimalUnico(String nombre, Integer especie) { 
		
		Query q = sesion.createQuery("SELECT a FROM Animal a WHERE nombre=? AND idEspecie=?");
		q.setString(0, nombre);
		q.setInteger(1, especie);
		Animal a=(Animal)q.uniqueResult();
		
		return a;
	}


	public Object consultarUnico(String clase, String desc) {
		Object e;
		Query q= sesion.createQuery("SELECT e FROM "+clase+" e WHERE descripcion=?");
		q.setString(0, desc);
		e=q.uniqueResult();
		return e;
	}

	public List<Object> consultarPorDesc(String clase, String desc) {
		List<Object> la ;
		Query q = sesion.createQuery("SELECT a FROM "+clase+" a WHERE descripcion LIKE '%"+desc+"%'");
		la = q.list();
		return la;
	}

	public Empleado consultarEmpleadoID(Integer id) {
		Query q=sesion.createQuery("SELECT e FROM Empleado e WHERE id=?");
		q.setInteger(0, id);
		Empleado e=(Empleado) q.uniqueResult();
		return e;
	}

	public Empleado consultarEmpleadoUnico(String nombre, String direccion) {
		// TODO Auto-generated method stub
		Query q = sesion.createQuery("SELECT e FROM Empleado e WHERE nombre=? AND direccion=?");
		q.setString(0, nombre);
		q.setString(1, direccion);
		Empleado e=(Empleado)q.uniqueResult();
		
		return e;
	}
	public List<Empleado> consultarEmpleados(String nombre, String direccion) {
		List<Empleado> le;
		Query q;
		
		if (direccion.equalsIgnoreCase("")) {
			q = sesion.createQuery("SELECT a FROM Empleado a WHERE nombre LIKE '%"+nombre+"%' ORDER BY nombre");
			le = q.list();
		}else {
			q = sesion.createQuery("SELECT a FROM Empleado a WHERE nombre LIKE '%"+nombre+"%' AND direccion LIKE '%"+direccion+"%' ORDER BY nombre");
			
			le = q.list();
		}

		return le;
	}

	public Evento consultarEventoID(Integer id) {
		Query q=sesion.createQuery("SELECT e FROM Evento e WHERE id=?");
		q.setInteger(0, id);
		Evento e=(Evento) q.uniqueResult();
		return e;
	}

	public Evento consultarEventoUnico(String desc) {
		// TODO Auto-generated method stub
		Query q=sesion.createQuery("SELECT e FROM Evento e WHERE descripcion=?");
		q.setString(0, desc);
		Evento e=(Evento) q.uniqueResult();
		return e;
	}

	public List<Evento> consultarEventos(String desc) {
		// TODO Auto-generated method stub
		List<Evento> le;
		Query q=sesion.createQuery("SELECT e FROM Evento e WHERE descripcion LIKE '%"+desc+"%'");
		le= q.list();
		return le;
	}

	@Override
	public List<Entrada> consultarEntradas(Integer id) throws Exception {
		List<Entrada> le;
		Query q=sesion.createQuery("SELECT e FROM Entrada e WHERE idEvento=? ORDER BY fechaHoraVenta DESC");
		q.setInteger(0, id);
		le=q.list();
		return le;
	}

	@Override
	public Entrada consultarEntradaID(Integer id) throws Exception {
		Entrada entrada;
		Query q= sesion.createQuery("SELECT e FROM Entrada e WHERE id=?");
		q.setInteger(0, id);
		entrada=(Entrada) q.uniqueResult();
		return entrada;
	}

	@Override
	public List<Nomina> consultarNominasPorEmpleado(Integer id) throws Exception {
		List<Nomina> ln;
		Query q=sesion.createQuery("SELECT e FROM Nomina e WHERE idEmpleado=? ORDER BY fechaEmision DESC");
		q.setInteger(0, id);
		ln=q.list();
		return ln;
	}

	@Override
	public Nomina consultarNominaID(Integer id) throws Exception {
		Nomina n ;
		Query q=sesion.createQuery("SELECT n FROM Nomina n WHERE id=?");
		q.setInteger(0, id);
		n=(Nomina)q.uniqueResult();
		return n;
	}

	@Override
	public void guardarSinCommit(Object o) throws Exception {
		if (transaccion==null || !transaccion.isActive()) {
			sesion.beginTransaction();
			
		}
		sesion.save(o);
		transaccion=sesion.getTransaction();
		
		
		
	}

	@Override
	public void borrarSinCommit(Object o) throws Exception {
		if (transaccion==null || !transaccion.isActive()) {
			sesion.beginTransaction();
			
		}
		sesion.delete(o);
		transaccion=sesion.getTransaction();
		
		
	}

	@Override
	public void transaccionCommit() throws Exception {
		// TODO Auto-generated method stub
		if (transaccion!=null && transaccion.isActive()) {
			transaccion.commit();
		}
		
		
	}

	@Override
	public void transaccionRollback() throws Exception {
		// TODO Auto-generated method stub
		if (transaccion!=null && transaccion.isActive()) {
			transaccion.rollback();
		}
		
		
	}
	


}
