package persistencia;

import ObjetosNegocio.ProductoGranel;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Kamilala
 */
public class ProductosGranel {
    

    private List<ProductoGranel> productosGranel;
    
    // Constructor
    public ProductosGranel() {
        this.productosGranel = new ArrayList<>();
    }
    
    /**
     * Agrega un producto a granel al inventario
     * @param productoGranel Producto a granel a agregar
     * @throws PersistenciaException si la cantidad es inválida o ya existe el producto
     */
    public void agregar(ProductoGranel productoGranel) throws PersistenciaException {
        // Validar que el producto no sea nulo
        if (productoGranel == null) {
            throw new PersistenciaException("El producto a granel no puede ser nulo");
        }
        
        // Validar que la cantidad sea mayor que 0
        if (productoGranel.getCantidad() <= 0) {
            throw new PersistenciaException("La cantidad del producto debe ser mayor que 0");
        }
        
        // Verificar que no exista un producto con la misma clave
        boolean existe = productosGranel.stream()
                .anyMatch(p -> p.getClave().equals(productoGranel.getClave()));
                
        if (existe) {
            throw new PersistenciaException("Ya existe un producto a granel con la clave: " + productoGranel.getClave());
        }
        
        // Agregar el producto
        productosGranel.add(productoGranel);
    }
    
    /**
     * Elimina un producto a granel del inventario por su clave
     * @param clave Clave del producto a eliminar
     * @throws PersistenciaException si el producto no existe
     */
    public void eliminar(String clave) throws PersistenciaException {
        ProductoGranel producto = consultarPorClave(clave);
        if (producto == null) {
            throw new PersistenciaException("No existe un producto a granel con la clave: " + clave);
        }
        productosGranel.remove(producto);
    }
    
    /**
     * Actualiza un producto a granel existente
     * @param productoGranel Producto con los nuevos datos
     * @throws PersistenciaException si el producto no existe o la cantidad es inválida
     */
    public void actualizar(ProductoGranel productoGranel) throws PersistenciaException {
        // Validar que el producto no sea nulo
        if (productoGranel == null) {
            throw new PersistenciaException("El producto a granel no puede ser nulo");
        }
        
        // Validar que la cantidad sea mayor que 0
        if (productoGranel.getCantidad() <= 0) {
            throw new PersistenciaException("La cantidad del producto debe ser mayor que 0");
        }
        
        // Buscar el producto por clave
        ProductoGranel productoExistente = consultarPorClave(productoGranel.getClave());
        if (productoExistente == null) {
            throw new PersistenciaException("No existe un producto a granel con la clave: " + productoGranel.getClave());
        }
        
        // Actualizar el producto (eliminar el existente y agregar el nuevo)
        productosGranel.remove(productoExistente);
        productosGranel.add(productoGranel);
    }
    
    /**
     * Consulta un producto a granel por su clave
     * @param clave Clave del producto a consultar
     * @return El producto encontrado o null si no existe
     */
    public ProductoGranel consultarPorClave(String clave) {
        return productosGranel.stream()
                .filter(p -> p.getClave().equals(clave))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Consulta todos los productos a granel
     * @return Lista de todos los productos a granel
     */
    public List<ProductoGranel> consultarTodos() {
        return new ArrayList<>(productosGranel);
    }

}
