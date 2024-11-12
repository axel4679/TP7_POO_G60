package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.entities.Factura;

public interface IFacturaDao {
	public void guardarFactura(Factura factura);
	public Factura buscarFacturaPorId(long idFactura);
	public void eliminarFacturaLogicamente(Factura factura);
	public void mostrarTodasLasFacturas();
	public void mostrarFacturasFiltradas();
}
