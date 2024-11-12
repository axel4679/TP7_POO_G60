package ar.edu.unju.escmi.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Facturas")
public class Factura {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fact_id")
	private long id; 
	
	@Column(name = "fact_fecha")
	private LocalDate fecha = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente; 
	
	@Column(name = "fact_total")
	private double total;
	
	@Column(name = "fact_estado")
	private boolean estado=true;
	
	@OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<DetalleFactura> detalles;
	
	public Factura(long id, Cliente cliente, List<DetalleFactura> detalles) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.detalles = detalles;
		this.estado = true;
	}
		
	public void setId(long id) {
		this.id = id;
	}

	public List<DetalleFactura> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleFactura> detalles) {
		this.detalles = detalles;
	}
	
	public Factura() {
		// TODO Auto-generated constructor stub
	}


	public long getId() {
		return id;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	
	public void mostrarFactura() {
		System.out.println("\nID: "+id);
		System.out.println("Fecha: "+fecha);
		System.out.println("Cliente: "+cliente.getNombre()+" "+cliente.getApellido());
		System.out.println("Domicilio del cliente: "+cliente.getDomicilio());
		System.out.println("Monto total: "+total);
		
		for(DetalleFactura detalle : detalles) {
			System.out.println("\nProducto: "+detalle.getProducto().getDescripcion());
			System.out.println("Precio: "+detalle.getProducto().getPrecioUnitario());
		}
	}


	public void calcularTotal() {
        this.total = 0.0;
        
        if (detalles != null) {
            for (DetalleFactura detalle : detalles) {
                if (detalle != null) {
                    detalle.calcularSubtotal(); 
                    this.total += detalle.getSubtotal();
                }
            }
        }
    }
}
