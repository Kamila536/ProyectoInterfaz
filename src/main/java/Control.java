import ObjetosNegocio.Producto;
import ObjetosNegocio.ProductoGranel;
import excepciones.PersistenciaException;
import persistencia.Productos;

/**
 *
 * @author Kamilala
 */
public class Control {
    

    public class ControlProductos {

    private Productos inventario;

    public ControlProductos() {
        this.inventario = new Productos();
    }

    /**
     * Agrega un producto al inventario. Las excepciones se propagan.
     * @throws PersistenciaException si ocurre un error de persistencia
     * @throws IllegalArgumentException si los datos son inválidos
     */
    public String agregarProducto(String nombre, String tipo, String unidad, Double cantidad)
            throws PersistenciaException, IllegalArgumentException {

        Producto producto = new Producto(nombre, tipo, unidad);

        if (tipo.equals("G")) {
            if (cantidad == null || cantidad < 0.01) {
                throw new IllegalArgumentException("La cantidad debe ser mayor o igual a 0.01 para productos a granel.");
            }
            ProductoGranel productoGranel = new ProductoGranel(producto, cantidad);
            inventario.agregar(productoGranel);
            return "Producto a granel agregado exitosamente: " + productoGranel;
        }

        inventario.agregar(producto);
        return "Producto empacado agregado exitosamente: " + producto;
    }

    public String eliminarProducto(String clave) throws PersistenciaException {
        inventario.eliminar(clave);
        return "Producto eliminado exitosamente con clave: " + clave;
    }

    public String listarProductos() {
        StringBuilder sb = new StringBuilder("Inventario:\n");
        for (Producto producto : inventario.consultarTodos()) {
            sb.append(producto).append("\n");
        }
        return sb.toString();
    }

    public String buscarProducto(String clave) {
        Producto p = inventario.consultarPorClave(clave);
        return (p != null) ? p.toString() : "Producto no encontrado con clave: " + clave;
    }
}
}