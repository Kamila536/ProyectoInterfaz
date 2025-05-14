
package Pruebas;

/**
 *
 * @author 52644
 */
import ObjetosNegocio.MovimientoGranel;
import ObjetosNegocio.Producto;
import ObjetosNegocio.ProductoGranel;
import ObjetosServicio.Fecha;
import excepciones.PersistenciaException;
import persistencia.MovimientosGranel;

import java.util.List;


public class Pruebas03 {
    public static void main(String[] args) {
        try {
            // Crear un producto válido 
            Producto productoValido = new Producto("GR001", "Frijol", "G", "KG");
            System.out.println("Producto válido creado: " + productoValido);

            // Crear un producto inválido (tipo A)
            Producto productoInvalido = new Producto("GR002", "Arroz", "A", "KG");
            System.out.println("Producto inválido creado (tipo A): " + productoInvalido);

            // Consultar el producto válido por clave
            if ("GR001".equals(productoValido.getClave())) {
                System.out.println("Consulta por clave exitosa: " + productoValido);
            }

            // Actualizar el tipo del producto válido a "A"
            productoValido.setTipo("A");
            System.out.println("Producto actualizado (ahora tipo A): " + productoValido);

            // Restaurar tipo a "G" para crear ProductoGranel
            productoValido.setTipo("G");

            // Crear un ProductoGranel válido
            ProductoGranel productoGranel = new ProductoGranel(productoValido, 10.0);
            System.out.println("ProductoGranel creado correctamente: " + productoGranel);

            // Crear gestor de compras
            MovimientosGranel compras = new MovimientosGranel();

            // Crear fecha actual
            Fecha fechaHoy = new Fecha();

            // Crear compra válida
            MovimientoGranel compra1 = new MovimientoGranel("MC001", fechaHoy, false, productoGranel);
            try {
                compras.registrarCompra(compra1);
                System.out.println("Compra 1 registrada exitosamente.");
            } catch (PersistenciaException e) {
                System.out.println("Error al registrar compra 1: " + e.getMessage());
            }

            // Crear una compra duplicada (mismo producto y misma fecha)
            MovimientoGranel compra2 = new MovimientoGranel("MC002", fechaHoy, false, productoGranel);
            try {
                compras.registrarCompra(compra2);
                System.out.println("Compra 2 registrada exitosamente (esto no debería pasar).");
            } catch (PersistenciaException e) {
                System.out.println("Error al registrar compra 2 (esperado): " + e.getMessage());
            }

            // Mostrar lista de compras registradas
            List<MovimientoGranel> listaCompras = compras.consultarCompras();
            System.out.println("\nLista de compras registradas:");
            for (MovimientoGranel mg : listaCompras) {
                System.out.println(mg);
            }

            // Mostrar productos a granel
            System.out.println("\nProductosGranel registrados:");
            ProductoGranel.desplegarProductosGranel(productoGranel);

        } catch (Exception e) {
            System.out.println("Error general en la ejecución: " + e.getMessage());
        }
    }
}