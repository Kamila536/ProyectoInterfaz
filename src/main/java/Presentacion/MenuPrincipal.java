package Presentacion;

import ObjetosNegocio.Producto;
import ObjetosNegocio.ProductoGranel;
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

    scanner.nextLine(); // limpiar buffer

    System.out.print("Ingrese la clave del producto: ");
    String clave = scanner.nextLine().trim();

    // Validar si ya existe un producto con esa clave
//    if ((clave) != null) {
//        System.out.println("Ya existe un producto con esa clave. No se puede agregar.");
//        return;
//    }

    System.out.print("Ingrese el nombre del producto: ");
    String nombre = scanner.nextLine().trim();

    String unidad;
    while (true) {
        System.out.print("Ingrese la unidad (kg/l): ");
        unidad = scanner.nextLine().trim().toLowerCase();
        if (unidad.equals("kg") || unidad.equals("l")) break;
        System.out.println("Unidad no válida. Solo se permite 'kg' o 'l'.");
    }

    String tipo;
    while (true) {
        System.out.print("Ingrese el tipo de producto (Granel/Empaque): ");
        tipo = scanner.nextLine().trim().toLowerCase();
        if (tipo.equals("granel") || tipo.equals("empaque")) break;
        System.out.println("Tipo no válido. Solo se permite 'Granel' o 'Empaque'.");
    }

    try {
        
    Producto producto;

    if (tipo.equalsIgnoreCase("granel")) {
        Producto baseProducto = new Producto(clave, nombre, tipo, unidad);
        producto = new ProductoGranel(baseProducto, 0.0);//cambiar 0.0 por cantidad
    } else {
        producto = new Producto(clave, nombre, tipo, unidad);
    }


    //Falta Agregar producto (me falta modificar codigo xd)
    productos.agregar(producto);
    System.out.println("Producto agregado correctamente.");

    } catch (Exception e) {
    System.out.println("Ocurrió un error al agregar el producto: " + e.getMessage());
    }

}


    private void consultarProducto() {
        
        System.out.println("== Consultar producto por clave ==");
    }

    private void actualizarProducto() {
        
        System.out.println("== Actualizar producto ==");
    }

    private void eliminarProducto() {
        
        System.out.println("== Eliminar producto ==");
    }

    private void consultarCatalogo() {
        
        System.out.println("== Consultar catálogo de productos ==");
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