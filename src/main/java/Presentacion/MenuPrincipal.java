package Presentacion;

import ObjetosNegocio.Producto;
import ObjetosNegocio.ProductoGranel;
import excepciones.PersistenciaException;
import java.util.List;
import java.util.Scanner;
import persistencia.Productos;

/**
 *
 * @author Kamilala
 */
public class MenuPrincipal{
    private Scanner scanner;
    private Productos productos;

    public  MenuPrincipal() {
        scanner = new Scanner(System.in);
    }
    
 public void mostrar() {
        
        productos = new Productos();
        int opcion;

        do {
            System.out.println("=== MENU PRINCIPAL ===");
            System.out.println("1. Agregar producto");
            System.out.println("2. Consultar producto por clave");
            System.out.println("3. Actualizar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Consultar catálogo de productos");
            System.out.println("6. Agregar compra de producto a granel");
            System.out.println("7. Agregar venta de producto a granel");
            System.out.println("8. Consultar compra por clave");
            System.out.println("9. Inventariar compras");
            System.out.println("10. Desinventariar ventas");
            System.out.println("11. Mostrar inventario de productos a granel");
            System.out.println("12. Mostrar registro de compras");
            System.out.println("13. Mostrar registro de ventas");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");

            opcion = i();

            switch (opcion) {
                case 1 -> agregarProducto();
                case 2 -> consultarProducto();
                case 3 -> actualizarProducto();
                case 4 -> eliminarProducto();
                case 5 -> consultarCatalogo();
                case 6 -> agregarCompraGranel();
                case 7 -> agregarVentaGranel();
                case 8 -> consultarCompraPorClave();
                case 9 -> inventariarCompras();
                case 10 -> desinventariarVentas();
                case 11 -> mostrarInventarioGranel();
                case 12 -> mostrarRegistroCompras();
                case 13 -> mostrarRegistroVentas();
                case 0 -> System.out.println("Gracias por usar el sistema.");
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }

        } while (opcion != 0);
    } private int i() {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Debe ingresar un número.");
            scanner.next(); 
        }
        return scanner.nextInt();
    }

    //metodos de cada opcion

    private void agregarProducto() {
    System.out.println("== Agregar nuevo producto ==");

    scanner.nextLine(); // Limpiar buffer de entrada

    String clave;
    while (true) {
        System.out.print("Ingrese la clave del producto: ");
        clave = scanner.nextLine().trim();
        if (clave.isEmpty()) {
            System.out.println("La clave no puede estar vacía.");
            continue;
        }

        // Validar si la clave ya existe
        if (productos.consultarPorClave(clave) != null) {
            System.out.println("Ya existe un producto con esa clave. No se puede agregar.");
            return;
        }
        break;
    }

    String nombre;
    while (true) {
        System.out.print("Ingrese el nombre del producto: ");
        nombre = scanner.nextLine().trim();
        if (!nombre.isEmpty()) break;
        System.out.println("El nombre no puede estar vacío.");
    }
    
    String tipo;
    while (true) {
        System.out.print("Ingrese el tipo de producto (G/E): ");
        tipo = scanner.nextLine().trim().toLowerCase();
        if (tipo.equals("g") || tipo.equals("e")) break;
        System.out.println("Tipo no válido. Solo se permite '(G)Granel' o '(E)Empacado'.");
    }
    
    String unidad;
    while (true) {
        System.out.print("Ingrese la unidad (kg/l/pz/g): ");
        unidad = scanner.nextLine().trim().toLowerCase();
        if (unidad.equals("kg") || unidad.equals("l")|| unidad.equals("pz")|| unidad.equals("g")) break;
        System.out.println("Unidad no válida. Solo se permite 'kg' , 'l' , 'pz' o 'g'.");
    }

    try {
        Producto producto;

        if (tipo.equals("granel")) {
            double cantidad = 0.0;
            while (true) {
                System.out.print("Ingrese la cantidad inicial (en " + unidad + "): ");
                String input = scanner.nextLine().trim();
                try {
                    cantidad = Double.parseDouble(input);
                    if (cantidad >= 0) break;
                    else System.out.println("La cantidad no puede ser negativa.");
                } catch (NumberFormatException e) {
                    System.out.println("Cantidad no válida. Ingrese un número.");
                }
            }
            Producto baseProducto = new Producto(clave, nombre, tipo, unidad);
            producto = new ProductoGranel(baseProducto, cantidad);
        } else {
            producto = new Producto(clave, nombre, tipo, unidad);
        }

        productos.agregar(producto);
        System.out.println("Producto agregado correctamente.");

    } catch (PersistenciaException e) {
        System.out.println("Ocurrió un error al agregar el producto: " + e.getMessage());
    }
}


    private void consultarProducto() {
        
        System.out.println("== Consultar producto por clave ==");
        System.out.println(productos.consultarTodos());
    }

    private void actualizarProducto() {
        
        System.out.println("== Actualizar producto ==");
    }

    private void eliminarProducto() {
        
        System.out.println("== Eliminar producto ==");
    }

    private void consultarCatalogo() {
    System.out.println("== Consultar catálogo de productos ==");

    List<Producto> lista = productos.consultarTodos();
    if (lista.isEmpty()) {
        System.out.println("No hay productos registrados en el catálogo.");
    } else {
        for (Producto p : lista) {
            System.out.println(p); 
        }
    }
}


    private void agregarCompraGranel() {
        
        System.out.println("== Agregar compra a granel ==");
    }

    private void agregarVentaGranel() {
        
        System.out.println("== Agregar venta a granel ==");
    }

    private void consultarCompraPorClave() {
        
        System.out.println("== Consultar compra por clave ==");
    }

    private void inventariarCompras() {
        
        System.out.println("== Inventariar compras ==");
    }

    private void desinventariarVentas() {
        
        System.out.println("== Desinventariar ventas ==");
    }

    private void mostrarInventarioGranel() {
        
        System.out.println("== Inventario de productos a granel ==");
    }

    private void mostrarRegistroCompras() {
        
        System.out.println("== Registro de compras ==");
    }

    private void mostrarRegistroVentas() {
        
        System.out.println("== Registro de ventas ==");
    }
}