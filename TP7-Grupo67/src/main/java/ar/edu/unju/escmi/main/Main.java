package ar.edu.unju.escmi.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ar.edu.unju.escmi.entities.*;
import ar.edu.unju.escmi.dao.imp.*;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		boolean band=true;
		
		do {
			System.out.println("*****MENU*****");
			System.out.println("1-Alta de cliente");
			System.out.println("2-Alta de producto");
			System.out.println("3-Vender productos");
			System.out.println("4-Buscar una factura por id");
			System.out.println("5-Eliminar una factura");
			System.out.println("6-Eliminar un producto");
			System.out.println("7-Modificar datos del cliente");
			System.out.println("8-Modificar precio del producto");
			System.out.println("9-Mostrar todas las facturas");
			System.out.println("10-Mostrar todos los clientes");
			System.out.println("11-Mostrar facturas con un precio mayor a $500.000");
			System.out.println("12-Salir");
			System.out.println("***************");
			System.out.print("Ingrese una opcion: ");
			String op = sc.nextLine();
			
			switch(op) {
			case "1":
				AltaCliente(sc);
			break;
			
			case "2":
				AltaProducto(sc);
			break;
			
			case "3":
				VenderProducto(sc);
			break;
			
			case "4":
				System.out.println("/nIngrese el numero de ID de la factura: ");
				long id = sc.nextLong();
				BuscarFactura(id);
			break;
			
			case "5":
				System.out.println("/nIngrese el numero de ID de la factura para poder eliminarla: ");
				long idE = sc.nextLong();
				EliminarFactura(idE);
			break;
			
			case "6":
				System.out.println("/nIngrese el numero de ID del producto para poder eliminarlo: ");
				long idP = sc.nextLong();
				EliminarProducto(idP);
			break;
			
			case "7":
				ModificarCliente(sc);
			break;
			
			case "8":
				ModificarProducto(sc);
			break;
			
			case "9":
				MostrarClientes();
			break;
			
			case "10":
				MostrarFacturas();
			break;
			
			case "11":
				MostrarFacturasFiltradas();
			break;
			
			case "12":
				System.out.println("*****FIN DEL PROGRAMA*****");
				band=false;
			break;
			
			default:
				System.out.println("Opcion no disponible");
			}
			
		}while(band);
		
		sc.close();
		
	}
	
	
public static void AltaCliente(Scanner sc) {
		
	ClienteDaoImp clienteDaoImp = new ClienteDaoImp(); 
		
	System.out.print("Ingrese ID del Cliente: ");
	long id = sc.nextLong();
	System.out.print("Ingrese nombre: ");
	String nombre = sc.nextLine();
	System.out.print("Ingrese apellido: ");
	String apellido = sc.nextLine();
	System.out.print("Ingrese domicilio: ");
	String domicilio = sc.nextLine();
	System.out.print("Ingrese DNI: ");
	int dni = sc.nextInt();

	Cliente cliente = new Cliente(id, nombre, apellido, domicilio, dni, true);  
	clienteDaoImp.guardarCliente(cliente);
	System.out.println("Cliente registrado exitosamente.");
}

	
public static void AltaProducto(Scanner sc) {
		
	ProductoDaoImp productoDaoImp = new ProductoDaoImp();
		
	System.out.print("Ingrese el ID del producto: ");
	long id = sc.nextLong();
	System.out.print("Ingrese descripción del producto: ");
	String descripcion = sc.nextLine();
	System.out.print("Ingrese precio unitario: ");
	double precioUnitario = sc.nextDouble();

	Producto producto = new Producto(descripcion, precioUnitario);  
	productoDaoImp.guardarProducto(producto);
	System.out.println("Producto registrado exitosamente.");
		
}
	
	
	public static void VenderProducto(Scanner sc) {
		
		FacturaDaoImp facturaDaoImp = new FacturaDaoImp();
		Factura factura = new Factura();
		factura.setFecha(LocalDate.now());
        System.out.print("Ingrese el ID del cliente: ");
        Long clienteId = sc.nextLong();
        
        ClienteDaoImp clienteDaoImp = new ClienteDaoImp();
        Cliente cliente = clienteDaoImp.buscarClientePorId(clienteId);
        ProductoDaoImp ProductoDaoImp = new ProductoDaoImp();

        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            sc.close();
            return;
        }

        factura.setCliente(cliente);

        List<DetalleFactura> detalles = new ArrayList<>();
        String agregar = null;
        do {
            DetalleFactura detalle = new DetalleFactura();

            System.out.print("Ingrese el ID del producto: ");
            Long productoId = sc.nextLong();
            Producto producto = ProductoDaoImp.buscarProductoPorId(productoId);

            if (producto == null) {
                System.out.println("Producto no encontrado.");
                continue;
            }

            detalle.setProducto(producto);

            System.out.print("Ingrese la cantidad: ");
            int cantidad = sc.nextInt();
            detalle.setCantidad(cantidad);

            detalle.calcularSubtotal();
            detalle.setFactura(factura);
            detalles.add(detalle);

            System.out.print("¿Desea agregar otro producto? (s/n): ");
            agregar = sc.next();
        } while (agregar.equalsIgnoreCase("s"));

        factura.setDetalles(detalles);
        factura.calcularTotal(); 

      
        facturaDaoImp.guardarFactura(factura);

        System.out.println("Venta registrada exitosamente con ID de factura: " + factura.getId());

        sc.close();
	}
	
	
	public static void BuscarFactura(long numId) {
		FacturaDaoImp factura = new FacturaDaoImp();
		Factura fact = factura.buscarFacturaPorId(numId);
		fact.mostrarFactura();
	}
	
	
	public static void EliminarFactura(long numId) {
		FacturaDaoImp factura = new FacturaDaoImp();
		Factura fact = factura.buscarFacturaPorId(numId);
		factura.eliminarFacturaLogicamente(fact);
		fact.setEstado(false);
	}
	
	
	public static void EliminarProducto(long idProd) {
		ProductoDaoImp producto = new ProductoDaoImp();
		Producto prod = producto.buscarProductoPorId(idProd);
		producto.eliminarProductoLogicamente(prod);
		prod.setEstado(false);
	}
	
	
	public static void ModificarCliente(Scanner sc) {
		ClienteDaoImp clienteDaoImp = new ClienteDaoImp();
	    Cliente cliente = null;
	    long idCliente;

	    do {
	        System.out.print("Ingrese el ID del cliente a modificar: ");
	        idCliente = sc.nextLong();
	        cliente = clienteDaoImp.buscarClientePorId(idCliente);

	        if (cliente == null) {
	            System.out.println("Cliente no encontrado. Intente nuevamente");
	        }
	    } while (cliente == null);

	    sc.nextLine();

	    System.out.print("Ingrese el nuevo nombre: ");
	    cliente.setNombre(sc.nextLine());

	    System.out.print("Ingrese el nuevo apellido: ");
	    cliente.setApellido(sc.nextLine());

	    System.out.print("Ingrese el nuevo domicilio: ");
	    cliente.setDomicilio(sc.nextLine());

	    System.out.print("Ingrese el nuevo DNI: ");
	    cliente.setDni(sc.nextInt());

	    clienteDaoImp.modificarCliente(cliente);
	    System.out.println("Datos del cliente actualizados");
	}
	
	
	public static void ModificarProducto(Scanner sc) {
		ProductoDaoImp productoDaoImp = new ProductoDaoImp();
	    Producto producto = null;
	    long idProducto;

	    do {
	        System.out.print("Ingrese el ID del producto a modificar: ");
	        idProducto = sc.nextLong();
	        producto = productoDaoImp.buscarProductoPorId(idProducto);

	        if (producto == null) {
	            System.out.println("Producto no encontrado. Intente nuevamente");
	        }
	    } while (producto == null);

	    System.out.print("Ingrese el nuevo precio del producto: ");
	    double nuevoPrecio = sc.nextDouble();
	    producto.setPrecioUnitario(nuevoPrecio);

	    productoDaoImp.modificarPrecioProducto(producto);
	    System.out.println("Precio del producto modificado");
	}
	
	
	public static void MostrarFacturas() {
		
		 try {
			 FacturaDaoImp facturaDaoImp= new FacturaDaoImp(); 
		        System.out.println("Lista de Facturas:");
				facturaDaoImp.mostrarTodasLasFacturas();
	        } catch (Exception e) {
	            System.out.println("Error al mostrar todas las Facturas");
	            System.out.println(e.getMessage());
	        }
	    }
	
	
	public static void MostrarClientes() {
		 try {
			 	ClienteDaoImp clienteDaoImp = new ClienteDaoImp();
		        System.out.println("Lista de Clientes:");
				clienteDaoImp.mostrarTodosLosClientes();
	        } catch (Exception e) {
	            System.out.println("Error al mostrar los Clientes");
	            System.out.println(e.getMessage());
	        }
	    }
		
	
	public static void MostrarFacturasFiltradas() {
		 try {
			 	FacturaDaoImp facturaDaoImp= new FacturaDaoImp(); 
		        System.out.println("Lista de Facturas filtradas:");
				facturaDaoImp.mostrarFacturasFiltradas();
	        } catch (Exception e) {
	            System.out.println("Error al mostrar las facturas");
	            System.out.println(e.getMessage());
	        }
	    }
	}
	