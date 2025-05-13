

package Pruebas;

import ObjetosNegocio.MovimientoEmpacado;
import static ObjetosNegocio.MovimientoEmpacado.desplegarMovimientosEmpacados;
import ObjetosNegocio.MovimientoGranel;
import static ObjetosNegocio.MovimientoGranel.desplegarMovimientosGranel;
import ObjetosNegocio.Producto;
import static ObjetosNegocio.Producto.corregirNombreProducto;
import static ObjetosNegocio.Producto.desplegarProductos;
import static ObjetosNegocio.Producto.verificarProductosDiferentes;
import static ObjetosNegocio.Producto.verificarProductosIguales;
import ObjetosNegocio.ProductoEmpacado;
import static ObjetosNegocio.ProductoEmpacado.desplegarProductosEmpacados;
import ObjetosNegocio.ProductoGranel;
import static ObjetosNegocio.ProductoGranel.desplegarProductosGranel;
import ObjetosServicio.Fecha;
import java.time.LocalDate;


public class AbarrotesPersistencia_00000252266_00000246835 {

    public static void main(String[] args) {
        
       
        
        System.out.println(" CREACION Y DESPLIEGUE DE PRODUCTOS ");
        Producto producto1 = crearProducto ("EM001", "Atun en Aceite", "E", "Pz");
        Producto producto2 = crearProducto ("EM002", "Elotitos Amarillos", "E", "Pz");
        Producto producto3 = crearProducto("EM120", "Chiles Jalapenhos", "E", "Pz");
        Producto producto4 = crearProducto("GR001", "Frijol Azufrado", "G", "Kg");
        Producto producto5 = crearProducto("GR013", "Arroz Grano Largo", "G", "Kg");

        desplegarProductos(producto1, producto2, producto3, producto4, producto5);

        System.out.println("\n OPERACIONES CON PRODUCTOS ");
        corregirNombreProducto(producto5, "Arroz Grano Corto");
        verificarProductosDiferentes(producto1, producto2);
        verificarProductosIguales(producto4, producto5);

        System.out.println("\n CREACION Y DESPLIEGUE DE PRODUCTOS EMPACADOS ");
        ProductoEmpacado productoEmpacado1 = crearProductoEmpacado(producto1, 10);
        ProductoEmpacado productoEmpacado2 = crearProductoEmpacado(producto2, 20);
        desplegarProductosEmpacados(productoEmpacado1, productoEmpacado2);

        System.out.println("\n CREACION Y DESPLIEGUE DE PRODUCTOS A GRANEL ");
        ProductoGranel productoGranel1 = crearProductoGranel (producto4, 25.0f);
        ProductoGranel productoGranel2 = crearProductoGranel(producto5, 12.5f);
        desplegarProductosGranel(productoGranel1, productoGranel2);

        System.out.println("\n MOVIMIENTOS DE PRODUCTOS EMPACADOS ");
        MovimientoEmpacado movimientoEmpacado1 = crearMovimientoEmpacado("MV001", new Fecha(), false, productoEmpacado1);
        MovimientoEmpacado movimientoEmpacado2 = crearMovimientoEmpacado("MV002", new Fecha(), false, productoEmpacado2);
        desplegarMovimientosEmpacados(movimientoEmpacado1, movimientoEmpacado2);

        System.out.println("\n MOVIMIENTOS DE PRODUCTOS A GRANEL ");
        MovimientoGranel movimientoGranel1 = crearMovimientoGranel("MV003", new Fecha(), false, productoGranel1);
        MovimientoGranel movimientoGranel2 = crearMovimientoGranel("MV004", new Fecha(), false, productoGranel2);
        desplegarMovimientosGranel(movimientoGranel1, movimientoGranel2);
        
    }

     private static Producto crearProducto (String clave, String nombre, String tipo, String unidad) {
        try {
            return new Producto (clave, nombre, tipo, unidad);
        } catch (IllegalArgumentException e) {
            System.err.println("Error al crear producto: " + e.getMessage());
            return null;
        }
    }

    private static ProductoEmpacado crearProductoEmpacado (Producto producto, double cantidad) {
        try {
            return new ProductoEmpacado(producto, cantidad);
        } catch (IllegalArgumentException e) {
            System.err.println("Error al crear producto empacado: " + e.getMessage());
            return null;
        }
    }
    
     
      private static ProductoGranel crearProductoGranel(Producto producto, float cantidad) {
        try {
            return new ProductoGranel(producto, cantidad);
        } catch (IllegalArgumentException e) {
            System.err.println("Error al crear producto a granel: " + e.getMessage());
            return null;
        }
    }
      
       private static MovimientoEmpacado crearMovimientoEmpacado(String clave, Fecha fecha, boolean procesado, ProductoEmpacado productoEmpacado) {
        try {
            return new MovimientoEmpacado(clave, fecha, procesado, productoEmpacado);
        } catch (IllegalArgumentException e) {
            System.err.println("Error al crear movimiento empacado: " + e.getMessage());
            return null;
        }
    }

    private static MovimientoGranel crearMovimientoGranel(String clave, Fecha fecha, boolean procesado, ProductoGranel productoGranel) {
        try {
            MovimientoGranel movimiento = new MovimientoGranel("AT001", LocalDate.now());
            movimiento.setCveMovimiento(clave);
            movimiento.setFecha(fecha);
            movimiento.setProcesado(procesado);
            movimiento.setProductoGranel(productoGranel);
            return movimiento;
        } catch (IllegalArgumentException e) {
            System.err.println("Error al crear movimiento granel: " + e.getMessage());
            return null;
        }
    }



   
}
