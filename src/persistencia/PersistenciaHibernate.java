package persistencia;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import clasesZoo.Animal;
import clasesZoo.Especie;
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

	public boolean guardarAnimal(Animal a) {
		sesion.beginTransaction();
		sesion.save(a);
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
	
	public Animal conultarAnimal(String nombre, Integer especie) { 
		
		Query q = sesion.createQuery("SELECT a FROM Animal a WHERE nombre=? AND idEspecie=?");
		q.setString(0, nombre);
		q.setInteger(1, especie);
		Animal a=(Animal)q.uniqueResult();
		
		return a;
	}

	public List<Zona> consultarZonas() {
		List<Zona> lz;
		Query q = sesion.createQuery("SELECT z FROM Zona z");
		lz = q.list();

		return lz;
	}

	public List<Especie> consultarEspecie() {
		List<Especie> le;
		Query q = sesion.createQuery("SELECT e FROM Especie e");
		le = q.list();
		return le;
	}


	public Especie consultarEspecieUnica(String desc) {
		Especie e;
		Query q= sesion.createQuery("SELECT e FROM Especie e WHERE descripcion=?");
		q.setString(0, desc);
		e=(Especie)q.uniqueResult();
		return e;
	}

}
