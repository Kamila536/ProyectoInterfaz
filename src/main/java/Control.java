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
     * Agrega un producto al inventario (empacado o a granel)
     * @param nombre Nombre del producto
     * @param tipo Tipo del producto: "E" (empacado) o "G" (granel)
     * @param unidad Unidad de medida: KG, L, PZ
     * @param cantidad Cantidad para productos a granel; se ignora para empacados
     * @return Mensaje para mostrar en la GUI
         * @throws excepciones.PersistenciaException
     */
    public String agregarProducto(String nombre, String tipo, String unidad, Double cantidad) throws PersistenciaException {
        try {
            // Crear objeto base
            Producto producto = new Producto(nombre, tipo, unidad);

            // Si es de tipo granel, validar cantidad y crear ProductoGranel
            if (tipo.equals("G")) {
                if (cantidad == null || cantidad < 0.01) {
                    return "La cantidad debe ser mayor o igual a 0.01 para productos a granel.";
                }

                ProductoGranel productoGranel = new ProductoGranel(producto, cantidad);
                inventario.agregar(productoGranel); // Usa Producto como tipo base

                return "Producto a granel agregado exitosamente: " + productoGranel;
            }

            // Si es empacado, no necesita cantidad
            inventario.agregar(producto);
            return "Producto empacado agregado exitosamente: " + producto;

        } catch (IllegalArgumentException e) {
            return "Error al agregar producto: " + e.getMessage();
        }
    }
    
    public String eliminarProducto(String clave) {
        try {
            inventario.eliminar(clave);
            return "Producto eliminado exitosamente con clave: " + clave;
        } catch (PersistenciaException e) {
            return "Error al eliminar producto: " + e.getMessage();
        }
    }

    /**
     * Lista todos los productos
     * @return Cadena con los productos para mostrar
     */
    public String listarProductos() {
        StringBuilder sb = new StringBuilder("Inventario:\n");
        for (Producto producto : inventario.consultarTodos()) {
            sb.append(producto).append("\n");
        }
        return sb.toString();
    }

    /**
     * Busca un producto por clave
     * @param clave Clave a buscar
     * @return Cadena representando el producto o mensaje si no se encuentra
     */
    public String buscarProducto(String clave) {
        Producto p = inventario.consultarPorClave(clave);
        return (p != null) ? p.toString() : "Producto no encontrado con clave: " + clave;
    }
    }
}
