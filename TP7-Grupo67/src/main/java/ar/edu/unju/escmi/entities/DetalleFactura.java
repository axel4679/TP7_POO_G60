package ar.edu.unju.escmi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "Detalle_Facturas")
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalle_fact_id")
    private Long id;

    @JoinColumn(name = "detalle_fact_producto")
    private Producto producto;

    @Column(name = "detalle_fact_cantidad")
    private int cantidad;

    @Column(name = "detalle_fact_subtotal")
    private double subtotal;

    public DetalleFactura() {}

    public DetalleFactura(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        calcularSubtotal();  
    }
    
    
    
    public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	
	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public void calcularSubtotal() {
        if (this.producto != null && this.producto.getPrecioUnitario() > 0) {
            this.subtotal = this.cantidad * this.producto.getPrecioUnitario();
        } else {
            this.subtotal = 0.0;
        }
    }

	public void setFactura(Factura factura) {
		// TODO Auto-generated method stub
		
	}
}