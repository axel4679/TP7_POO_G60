package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IFacturaDao;
import ar.edu.unju.escmi.entities.Factura;

public class FacturaDaoImp implements IFacturaDao {

	private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
	
	@Override
	public void guardarFactura(Factura factura) {
		try {
			manager.getTransaction().begin();
			manager.merge(factura);
			manager.getTransaction().commit();
		}catch(Exception e) {
			if(manager.getTransaction() != null) {
				manager.getTransaction().rollback();
			}
			System.out.println("No se pudo guardar la factura");	
		}
	}

	@Override
	public Factura buscarFacturaPorId(long idFactura) {
		return manager.find(Factura.class, idFactura);
	}

	@Override
	public void eliminarFacturaLogicamente(Factura factura) {
		try {
			manager.getTransaction().begin();
			manager.merge(factura);
			manager.getTransaction().commit();
		}catch(Exception e) {
			if(manager.getTransaction() != null) {
				manager.getTransaction().rollback();
			}
			System.out.println("No se pudo eliminar la factura");
		}
	}

	@Override
	public void mostrarTodasLasFacturas() {
		TypedQuery<Factura> query = manager.createQuery("SELECT e FROM Factura e",Factura.class);
		List<Factura> facturas = query.getResultList();
		for(Factura factura : facturas) {
			if(factura.isEstado()) {
				factura.mostrarFactura();
			}
		}
	}

	@Override
	public void mostrarFacturasFiltradas() {
		TypedQuery<Factura> query = manager.createQuery("SELECT e FROM Factura e",Factura.class);
		List<Factura> facturas = query.getResultList();
		for(Factura factura : facturas) {
			if(factura.getTotal()>=500000 && factura.isEstado()) {
				factura.mostrarFactura();
			}
		}
	}

}
