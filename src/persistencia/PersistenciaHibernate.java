package persistencia;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import clasesZoo.Alimento;
import clasesZoo.Animal;
import clasesZoo.Especie;
import clasesZoo.Tratamiento;
import clasesZoo.Zona;

public class PersistenciaHibernate {
	Session sesion;

	public PersistenciaHibernate() {
		SessionFactory sessionFactory;
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		sesion = sessionFactory.openSession();
	}

	public boolean guardar(Object a) {
		sesion.beginTransaction();
		sesion.save(a);
		sesion.getTransaction().commit();
		return true;
	}
	public boolean borrar(Object a) {
		sesion.beginTransaction();
		sesion.delete(a);
		sesion.getTransaction().commit();
		return true;
	}
	
	public List<Animal> consultarAnimal(String nombre, Integer especie) {
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

}
