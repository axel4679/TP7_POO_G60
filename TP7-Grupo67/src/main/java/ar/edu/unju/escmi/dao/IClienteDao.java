package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.entities.Cliente;

public interface IClienteDao {
	public void guardarCliente(Cliente cliente);
	public void modificarCliente(Cliente cliente);
	public Cliente buscarClientePorId(long idCliente);
	public void mostrarTodosLosClientes();
}
 