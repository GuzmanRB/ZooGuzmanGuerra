package clasesZoo;
// Generated 14-feb-2022 13:47:46 by Hibernate Tools 4.0.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Animal generated by hbm2java
 */
public class Animal implements java.io.Serializable {

	private Integer id;
	private Especie especie;
	private Zona zona;
	private String nombre;
	private Date fechaNac;
	private Set animaltratamientos = new HashSet(0);
	private Set consumes = new HashSet(0);

	public Animal() {
	}

	public Animal(Especie especie, Zona zona, String nombre) {
		this.especie = especie;
		this.zona = zona;
		this.nombre = nombre;
	}

	public Animal(Especie especie, Zona zona, String nombre, Date fechaNac, Set animaltratamientos, Set consumes) {
		this.especie = especie;
		this.zona = zona;
		this.nombre = nombre;
		this.fechaNac = fechaNac;
		this.animaltratamientos = animaltratamientos;
		this.consumes = consumes;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Especie getEspecie() {
		return this.especie;
	}

	public void setEspecie(Especie especie) {
		this.especie = especie;
	}

	public Zona getZona() {
		return this.zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaNac() {
		return this.fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public Set getAnimaltratamientos() {
		return this.animaltratamientos;
	}

	public void setAnimaltratamientos(Set animaltratamientos) {
		this.animaltratamientos = animaltratamientos;
	}

	public Set getConsumes() {
		return this.consumes;
	}

	public void setConsumes(Set consumes) {
		this.consumes = consumes;
	}

}