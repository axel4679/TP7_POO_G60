package ar.edu.unju.escmi.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;


@Entity
@Table(name = "productos")
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prod_id")
	private long id;
	
	@Column(name = "prod_descripcion")
	private String descripcion;
	
	@Column(name = "prod_precio_unitario", nullable = false)
	private double precioUnitario;
	
	@Column(name = "prod_estado")
	private boolean estado;
	
	public Producto(String descripcion, double precioUnitario) {
		super();
		this.descripcion = descripcion;
		this.precioUnitario = precioUnitario;
		this.estado = true;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	
	public void mostrarProducto() {
		System.out.println("\nID: "+id);
		System.out.println("Descripcion: "+descripcion);
		System.out.println("Precio: "+precioUnitario);
	}
	
}
