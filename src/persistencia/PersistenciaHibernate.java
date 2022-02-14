package persistencia;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import clasesZoo.Animal;

public class PersistenciaHibernate {
	Session sesion;
	
    public PersistenciaHibernate() {
		SessionFactory sessionFactory;
	    Configuration configuration = new Configuration();
	    configuration.configure();
	    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    sesion=sessionFactory.openSession();
	}
    
    public boolean guardarAnimal(Animal a) {
    	sesion.beginTransaction();
    	sesion.save(a);
    	sesion.getTransaction().commit();
    	return true;
    }
    
    public List<Animal> consultarAnimal( String nombre, int especie){
    	
    	Query q=sesion.createQuery("SELECT a FROM animal a WHERE nombre LIKE '%:nombre%' AND especie=:especie");
    	q.setParameter(1, nombre);
    	q.setParameter(2, especie);
    	List<Animal> la=q.list();
		return la;
    }
    public List<Animal> consultarAnimal( String nombre){
    	
    	Query q=sesion.createQuery("SELECT a FROM animal a WHERE nombre LIKE '%:nombre%' ");
    	q.setParameter(1, nombre);
    	List<Animal> la=q.list();
		return la;
    }
    
}
