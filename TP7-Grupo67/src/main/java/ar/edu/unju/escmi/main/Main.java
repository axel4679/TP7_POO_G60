package ar.edu.unju.escmi.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ar.edu.unju.escmi.entities.*;
import ar.edu.unju.escmi.dao.imp.*;

public class Main {

	private static ProductoDaoImp productoDaoImp = new ProductoDaoImp();
	private static ClienteDaoImp clienteDaoImp = new ClienteDaoImp();
	private static FacturaDaoImp facturaDaoImp = new FacturaDaoImp();
	
	private static long idProductoAuto = 1;
	private static long idClienteAuto = 1;
	private static long idFacturaAuto = 1;
	private static long idDetalleAuto = 1;
	
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
				altaCliente(sc);
			break;
			
			case "2":
				altaProducto(sc);
			break;
			
			case "3":
				venderProducto(sc);
			break;
			
			case "4":
				System.out.print("\nIngrese el numero de ID de la factura: ");
				long id = sc.nextLong();
				buscarFactura(id);
			break;
			
			case "5":
				System.out.print("\nIngrese el numero de ID de la factura para poder eliminarla: ");
				long idE = sc.nextLong();
				eliminarFactura(idE);
			break;
			
			case "6":
				System.out.print("\nIngrese el numero de ID del producto para poder eliminarlo: ");
				long idP = sc.nextLong();
				eliminarProducto(idP);
			break;
			
			case "7":
				modificarCliente(sc);
			break;
			
			case "8":
				modificarProducto(sc);
			break;
			
			case "9":
				mostrarFacturas();
			break;
			
			case "10":
				mostrarClientes();
			break;
			
			case "11":
				mostrarFacturasFiltradas();
			break;
			
			case "12":
				System.out.println("\n*****FIN DEL PROGRAMA*****\n");
				band=false;
			break;
			
			default:
				System.out.println("\nOpcion no disponible\n");
			}
			
		}while(band);
		
		sc.close();
		
	}
	
	
	public static void altaCliente(Scanner sc) {
			
		System.out.print("\nIngrese nombre: ");
		String nombre = sc.nextLine();
		System.out.print("Ingrese apellido: ");
		String apellido = sc.nextLine();
		System.out.print("Ingrese domicilio: ");
		String domicilio = sc.nextLine();
		
		boolean datoInvalido=false;
		int dni=0;
		do {
			try {
				datoInvalido=false;
				System.out.print("Ingrese DNI: ");
				dni = sc.nextInt();
			}
			catch(Exception e) {
				System.out.println("\nDato no valido, vuelva a ingresar el DNI");
				datoInvalido=true;
				sc.nextLine();
			}
		}while(datoInvalido);
	
		List<Factura> facturas = null;
		Cliente cliente = new Cliente(idClienteAuto, nombre, apellido, domicilio, dni, facturas);  
		idClienteAuto++;
		
		clienteDaoImp.guardarCliente(cliente);
		System.out.println("\nCliente registrado exitosamente.\n");
		sc.nextLine();
	}

	
	public static void altaProducto(Scanner sc) {
			
		System.out.print("\nIngrese descripción del producto: ");
		String descripcion = sc.nextLine();
		
		boolean datoInvalido=false;
		int precioUnitario=0;
		do {
			try {
				datoInvalido=false;
				System.out.print("Ingrese precio unitario: ");
				precioUnitario = sc.nextInt();
			}
			catch(Exception e) {
				System.out.println("\nDato no valido, vuelva a ingresar el precio");
				datoInvalido=true;
				sc.nextLine();
			}
		}while(datoInvalido);
	
		Producto producto = new Producto(idProductoAuto, descripcion, precioUnitario);  
		idProductoAuto++;
		
		productoDaoImp.guardarProducto(producto);
		System.out.println("\nProducto registrado exitosamente.\n");
		sc.nextLine();
		
	}
	
	
	public static void venderProducto(Scanner sc) {
		        
		clienteDaoImp.mostrarTodosLosClientes();
		
		Cliente cliente = null;
	    long idCliente=0;
	    boolean datoInvalido=true;
	    do {
	    	try {
	    		System.out.print("Ingrese el ID del Cliente: ");
		        idCliente = sc.nextLong();
		        
		        cliente = clienteDaoImp.buscarClientePorId(idCliente);

		        if (cliente == null) {
		            System.out.println("Cliente no encontrado. Intente nuevamente");
		        }
		        else datoInvalido=false;
	    	}
	    	catch(Exception e) {
	    		System.out.println("\nDato no valido, ingrese nuevamente el ID");
	    		sc.nextLine();
	    	}
	    } while (datoInvalido);
        
        productoDaoImp.mostrarTodosLosProductos();
        
        Factura factura = new Factura();
        factura.setId(idFacturaAuto);
        idFacturaAuto++;
        
        List<DetalleFactura> detalles = new ArrayList<>();
        String agregar = null;
        do {
            Producto producto = null;
    	    long idProducto;
    	    datoInvalido=true;
    	    do {
    	    	try {
    	    		System.out.print("\nIngrese el ID del Producto: ");
    		        idProducto = sc.nextLong();
    		        producto = productoDaoImp.buscarProductoPorId(idProducto);

    		        if (producto == null) {
    		            System.out.println("\nProducto no encontrado. Intente nuevamente");
    		        }
    		        else datoInvalido=false;
    	    	}
    	    	catch(Exception e) {
    	    		System.out.println("\nDato no valido, ingrese nuevamente el ID");
    	    		sc.nextLine();
    	    	}
    	        
    	    } while (datoInvalido);

            int cantidad=0;
    		do {
    			try {
    				datoInvalido=false;
    				System.out.print("\nIngrese la cantidad: ");
    				cantidad = sc.nextInt();
    			}
    			catch(Exception e) {
    				System.out.println("\nDato no valido, vuelva a ingresar la cantidad");
    				datoInvalido=true;
    				sc.nextLine();
    			}
    		}while(datoInvalido);
            
    		DetalleFactura detalle = new DetalleFactura(idDetalleAuto, producto, cantidad, factura);
    		idDetalleAuto++;
    		
            detalle.calcularSubtotal();
            detalles.add(detalle);

            System.out.print("\n¿Desea agregar otro producto? (s/n): ");
            agregar = sc.next();
        } while (agregar.equalsIgnoreCase("s"));

        factura.setDetalles(detalles);
        factura.setCliente(cliente);
        factura.setCliente(cliente);
        factura.calcularTotal(); 
      
        facturaDaoImp.guardarFactura(factura);

        System.out.println("\nVenta registrada exitosamente con ID de factura: " + factura.getId());

        sc.nextLine();
	}
	
	
	public static void buscarFactura(long numId) {
		FacturaDaoImp factura = new FacturaDaoImp();
		Factura fact = factura.buscarFacturaPorId(numId);
		fact.mostrarFactura();
	}
	
	
	public static void eliminarFactura(long numId) {
		Factura fact = facturaDaoImp.buscarFacturaPorId(numId);
		fact.setEstado(false);
		facturaDaoImp.eliminarFacturaLogicamente(fact);
	}
	
	
	public static void eliminarProducto(long idProd) {
		Producto prod = productoDaoImp.buscarProductoPorId(idProd);
		prod.setEstado(false);
		productoDaoImp.eliminarProductoLogicamente(prod);
	}
	
	
	public static void modificarCliente(Scanner sc) {
	    
		clienteDaoImp.mostrarTodosLosClientes();
		
		Cliente cliente = null;
	    long idCliente=0;
	    boolean datoInvalido=true;
	    do {
	    	try {
	    		System.out.print("\nIngrese el ID del cliente a modificar: ");
		        idCliente = sc.nextLong();
		        
		        cliente = clienteDaoImp.buscarClientePorId(idCliente);

		        if (cliente == null) {
		            System.out.println("Cliente no encontrado. Intente nuevamente");
		        }
		        else datoInvalido=false;
	    	}
	    	catch(Exception e) {
	    		System.out.println("\nDato no valido, ingrese nuevamente el ID");
	    		sc.nextLine();
	    	}
	    } while (datoInvalido);

	    sc.nextLine();

	    System.out.print("Ingrese el nuevo nombre: ");
	    cliente.setNombre(sc.nextLine());

	    System.out.print("Ingrese el nuevo apellido: ");
	    cliente.setApellido(sc.nextLine());

	    System.out.print("Ingrese el nuevo domicilio: ");
	    cliente.setDomicilio(sc.nextLine());

		do {
			try {
				datoInvalido=false;
				System.out.print("Ingrese el nuevo DNI: ");
				cliente.setDni(sc.nextInt());
			}
			catch(Exception e) {
				System.out.println("\nDato no valido, vuelva a ingresar el DNI");
				datoInvalido=true;
				sc.nextLine();
			}
		}while(datoInvalido);
		
	    clienteDaoImp.modificarCliente(cliente);
	    System.out.println("\nDatos del cliente actualizados.\n");
	}
	
	
	public static void modificarProducto(Scanner sc) {

		productoDaoImp.mostrarTodosLosProductos();
		
		Producto producto = null;
	    long idProducto;
	    boolean datoInvalido=true;
	    do {
	    	try {
	    		System.out.print("\nIngrese el ID del producto a modificar: ");
		        idProducto = sc.nextLong();
		        producto = productoDaoImp.buscarProductoPorId(idProducto);

		        if (producto == null) {
		            System.out.println("\nProducto no encontrado. Intente nuevamente");
		        }
		        else datoInvalido=false;
	    	}
	    	catch(Exception e) {
	    		System.out.println("\nDato no valido, ingrese nuevamente el ID");
	    		sc.nextLine();
	    	}
	        
	    } while (datoInvalido);

	    System.out.print("Ingrese nueva descripcion del producto: ");
	    producto.setDescripcion(sc.nextLine());
	    
		do {
			try {
				datoInvalido=false;
				System.out.print("Ingrese nuevo precio unitario del producto: ");
				producto.setPrecioUnitario(sc.nextDouble());
			}
			catch(Exception e) {
				System.out.println("\nDato no valido, vuelva a ingresar el precio");
				datoInvalido=true;
				sc.nextLine();
			}
		}while(datoInvalido);

	    productoDaoImp.modificarPrecioProducto(producto);
	    System.out.println("\nPrecio del producto modificado.\n");
	}
	
	
	public static void mostrarFacturas() {
		
		 try {
		        System.out.println("Lista de Facturas:");
				facturaDaoImp.mostrarTodasLasFacturas();
	        } catch (Exception e) {
	            System.out.println("Error al mostrar todas las Facturas");
	            System.out.println(e.getMessage());
	        }
	    }
	
	 
	public static void mostrarClientes() {
		 try {
		        System.out.println("Lista de Clientes:");
				clienteDaoImp.mostrarTodosLosClientes();
	        } catch (Exception e) {
	            System.out.println("Error al mostrar los Clientes");
	            System.out.println(e.getMessage());
	        }
	    }
		
	
	public static void mostrarFacturasFiltradas() {
		 try {
		        System.out.println("Lista de Facturas filtradas:");
				facturaDaoImp.mostrarFacturasFiltradas();
	        } catch (Exception e) {
	            System.out.println("Error al mostrar las facturas");
	            System.out.println(e.getMessage());
	        }
	    }
	}
	 
