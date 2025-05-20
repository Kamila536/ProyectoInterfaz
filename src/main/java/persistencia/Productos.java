package persistencia;

import ObjetosNegocio.Producto;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Kamilala
 */

public class Productos {
    private List<Producto> productos;
    
    // Constructor
    public Productos() {
        this.productos = new ArrayList<>();
    }
    
    /**
     * Agrega un nuevo producto a la lista
     * @param producto Producto a agregar
     * @throws PersistenciaException si ya existe un producto con la misma clave o si el producto no cumple con las validaciones
     */
    public void agregar(Producto producto) throws PersistenciaException {
        // Validar que el producto no sea nulo
        if (producto == null) {
            throw new PersistenciaException("El producto no puede ser nulo");
        }
        
        // Validar que la clave sea correcta (2 caracteres, 3 dígitos)
        String clave = producto.getClave();
        if (clave == null || clave.length() != 5 || !clave.substring(2).matches("\\d{3}")) {
            throw new PersistenciaException("La clave del producto debe componerse de dos caracteres y tres dígitos");
        }
        
        // Validar que no exista un producto con la misma clave
        if (productos.stream().anyMatch(p -> p.getClave().equals(producto.getClave()))) {
            throw new PersistenciaException("Ya existe un producto con la clave: " + producto.getClave());
        }
        
        // Validar que tenga nombre y tipo
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty() ||
            producto.getTipo() == null || producto.getTipo().trim().isEmpty()) {
            throw new PersistenciaException("El producto debe tener nombre y tipo");
        }
        
        // Validar que la unidad sea KG, L o PZ
        String unidad = producto.getUnidad();
        if (unidad == null || (!unidad.equals("KG") && !unidad.equals("L") && !unidad.equals("PZ"))) {
            throw new PersistenciaException("La unidad del producto debe ser KG, L o PZ");
        }
        
        // Validar que el tipo sea E o G
        String tipo = producto.getTipo();
        if (!tipo.equals("E") && !tipo.equals("G")) {
            throw new PersistenciaException("El tipo del producto debe ser E o G");
        }
        
        // Agregar el producto
        productos.add(producto);
    }
    
    /**
     * Consulta un producto por su clave
     * @param clave Clave del producto a consultar
     * @return El producto encontrado o null si no existe
     */
    public Producto consultarPorClave(String clave) {
        return productos.stream()
                .filter(p -> p.getClave().equals(clave))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Actualiza un producto existente
     * @param producto Producto con los nuevos datos
     * @throws PersistenciaException si el producto no existe o no cumple con las validaciones
     */
    public void actualizar(Producto producto) throws PersistenciaException {
        // Validar que el producto no sea nulo
        if (producto == null) {
            throw new PersistenciaException("El producto no puede ser nulo");
        }
        
        // Buscar el producto por clave
        Producto productoExistente = consultarPorClave(producto.getClave());
        if (productoExistente == null) {
            throw new PersistenciaException("No existe un producto con la clave: " + producto.getClave());
        }
        
        // Validar que tenga nombre y tipo
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty() ||
            producto.getTipo() == null || producto.getTipo().trim().isEmpty()) {
            throw new PersistenciaException("El producto debe tener nombre y tipo");
        }
        
        // Validar que la unidad sea KG, L o PZ
        String unidad = producto.getUnidad();
        if (unidad == null || (!unidad.equals("KG") && !unidad.equals("L") && !unidad.equals("PZ"))) {
            throw new PersistenciaException("La unidad del producto debe ser KG, L o PZ");
        }
        
        // Validar que el tipo sea E o G
        String tipo = producto.getTipo();
        if (!tipo.equals("E") && !tipo.equals("G")) {
            throw new PersistenciaException("El tipo del producto debe ser E o G");
        }
        
        // Actualizar el producto (eliminar el existente y agregar el nuevo)
        productos.remove(productoExistente);
        productos.add(producto);
    }
    
    /**
     * Elimina un producto por su clave
     * @param clave Clave del producto a eliminar
     * @throws PersistenciaException si el producto no existe
     */
    public void eliminar(String clave) throws PersistenciaException {
        Producto producto = consultarPorClave(clave);
        if (producto == null) {
            throw new PersistenciaException("No existe un producto con la clave: " + clave);
        }
        productos.remove(producto);
    }
    
    /**
     * Consulta todos los productos
     * @return Lista de todos los productos
     */
    public List<Producto> consultarTodos() {
        return new ArrayList<>(productos);
    }
    
    /**
     * Consulta productos con filtros opcionales
     * @param tipo Tipo de producto (E o G) o null para no filtrar por tipo
     * @param unidad Unidad del producto (KG, L, PZ) o null para no filtrar por unidad
     * @return Lista de productos que cumplen con los filtros
     */
    public List<Producto> consultarConFiltros(String tipo, String unidad) {
        return productos.stream()
                .filter(p -> tipo == null || p.getTipo().equals(tipo))
                .filter(p -> unidad == null || p.getUnidad().equals(unidad))
                .collect(Collectors.toList());
    }
}
