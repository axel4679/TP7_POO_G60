package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IProductoDao;
import ar.edu.unju.escmi.entities.Producto;

public class ProductoDaoImp implements IProductoDao {
	private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
	
	@Override
	public void guardarProducto(Producto producto) {
		try {
			manager.getTransaction().begin();
			manager.merge(producto);
			manager.getTransaction().commit();
		}catch(Exception e) {
			if(manager.getTransaction() != null) {
				manager.getTransaction().rollback();
			}
			System.out.println("No se pudo guardar el producto");	
		}
	}
	
	@Override
	public Producto buscarProductoPorId(long idProducto) {
		return manager.find(Producto.class, idProducto);
	}

	@Override
	public void modificarPrecioProducto(Producto producto) {
		try {
			manager.getTransaction().begin();
			manager.merge(producto);
			manager.getTransaction().commit();
		}catch(Exception e){
			if(manager.getTransaction() != null) {
				manager.getTransaction().rollback();
			}
			System.out.println("No se pudo modificar el producto");
		}
	}

	@Override
	public void eliminarProductoLogicamente(Producto producto) {
		try {
			manager.getTransaction().begin();
			manager.merge(producto);
			manager.getTransaction().commit();
		}catch(Exception e) {
			if(manager.getTransaction() != null) {
				manager.getTransaction().rollback();
			}
			System.out.println("No se pudo eliminar el producto");
		}
	} 

	@Override
	public void mostrarTodosLosProductos() {
		TypedQuery<Producto> query = manager.createQuery("SELECT e FROM Producto e",Producto.class);
		List<Producto> productos = query.getResultList();
		for(Producto producto : productos) {
			if(producto.isEstado()) {
				producto.mostrarProducto();
			}
		}
	}
}
