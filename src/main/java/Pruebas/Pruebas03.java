
package Pruebas;

import ObjetosNegocio.MovimientoGranel;
import ObjetosNegocio.Producto;
import ObjetosNegocio.ProductoGranel;
import ObjetosServicio.Fecha;
import excepciones.PersistenciaException;
import persistencia.MovimientosGranel;

import java.util.List;

/**
 * 
 * @author 52644
 */

public class Pruebas03 {
    public static void main(String[] args) {

        Producto productoValido = null;
        Producto productoInvalido = null;
        ProductoGranel productoGranel = null;
        MovimientosGranel compras = new MovimientosGranel();
        Fecha fechaHoy = new Fecha();

        // Crear producto válido
        try {
            productoValido = new Producto("GR001", "Frijol", "G", "KG");
            System.out.println("Producto válido creado: " + productoValido);
        } catch (Exception e) {
            System.out.println("Error al crear producto válido: " + e.getMessage());
        }

        // Crear producto inválido
        try {
            productoInvalido = new Producto("GR002", "Arroz", "A", "KG");
            System.out.println("Producto inválido creado (esto no debería pasar): " + productoInvalido);
        } catch (Exception e) {
            System.out.println("Error esperado al crear producto inválido (tipo A): " + e.getMessage());
        }

        // Consultar producto por clave
        try {
            if (productoValido != null && "GR001".equals(productoValido.getClave())) {
                System.out.println("Consulta por clave exitosa: " + productoValido);
            }
        } catch (Exception e) {
            System.out.println("Error en consulta por clave: " + e.getMessage());
        }

        // Actualizar tipo a "A"
        try {
            if (productoValido != null) {
                productoValido.setTipo("A");
                System.out.println("Producto actualizado a tipo A: " + productoValido);
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar tipo: " + e.getMessage());
        }

        // Restaurar tipo a "G"
        try {
            if (productoValido != null) {
                productoValido.setTipo("G");
            }
        } catch (Exception e) {
            System.out.println("Error al restaurar tipo G: " + e.getMessage());
        }

        // Crear ProductoGranel válido
        try {
            if (productoValido != null) {
                productoGranel = new ProductoGranel(productoValido, 10.0);
                System.out.println("ProductoGranel creado correctamente: " + productoGranel);
            }
        } catch (Exception e) {
            System.out.println("Error al crear ProductoGranel: " + e.getMessage());
        }

        // Registrar compra válida
        try {
            if (productoGranel != null) {
                MovimientoGranel compra1 = new MovimientoGranel("MC001", fechaHoy, false, productoGranel,0.0);
                compras.registrarCompra(compra1);
                System.out.println("Compra 1 registrada exitosamente.");
            }
        } catch (PersistenciaException e) {
            System.out.println("Error al registrar compra 1: " + e.getMessage());
        }

        // Registrar compra duplicada (mismo producto y fecha)
        try {
            if (productoGranel != null) {
                MovimientoGranel compra2 = new MovimientoGranel("MC002", fechaHoy, false, productoGranel,0.0);
                compras.registrarCompra(compra2);
                System.out.println("Compra 2 registrada exitosamente (esto no debería pasar).");
            }
        } catch (PersistenciaException e) {
            System.out.println("Error esperado al registrar compra duplicada: " + e.getMessage());
        }

        // Mostrar lista de compras
        try {
            List<MovimientoGranel> listaCompras = compras.consultarCompras();
            System.out.println("\nLista de compras registradas:");
            for (MovimientoGranel mg : listaCompras) {
                System.out.println(" - " + mg);
            }
        } catch (Exception e) {
            System.out.println("Error al consultar compras: " + e.getMessage());
        }

        // Mostrar productos a granel
        try {
            if (productoGranel != null) {
                System.out.println("\nProductoGranel registrado:");
                ProductoGranel.desplegarProductosGranel(productoGranel);
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar productos a granel: " + e.getMessage());
        }
    }
}
