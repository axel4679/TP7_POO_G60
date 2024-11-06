package ar.edu.unju.escmi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cliente_id")
	private long id;
	
	@Column(name = "cliente_nombre")
	private String nombre;
	
	@Column(name = "cliente_apellido")
	private String apellido;

	@Column(name = "cliente_domicilio")
	private String domicilio;
	
	@Column(name = "cliente_dni")
	private int dni;
	
	@Column(name = "cliente_estado")
	private boolean estado;
	
	
	public Cliente(long id, String nombre, String apellido, String domicilio, int dni, boolean estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.domicilio = domicilio;
		this.dni = dni;
		this.estado = estado;
	}
	
	
	public Cliente() {
		// TODO Auto-generated constructor stub
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	
	public void mostrarDatos() {
		System.out.println("Cliente: " + id);
		System.out.println("/nNombre del cliente: " + apellido + ", " + nombre);
		System.out.println("DNI: " + dni);
		System.out.println("Domicilio: " + domicilio);
	}
}
