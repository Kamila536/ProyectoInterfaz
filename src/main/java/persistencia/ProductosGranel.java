package persistencia;

import ObjetosNegocio.ProductoGranel;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductosGranel {
    private List<ProductoGranel> productosGranel;

    public ProductosGranel() {
        this.productosGranel = new ArrayList<>();
    }

    public void agregar(ProductoGranel productoGranel) throws PersistenciaException {
        validarProductoGranel(productoGranel);
        if (consultarPorClave(productoGranel.getClave()) != null) {
            throw new PersistenciaException("Ya existe un producto a granel con la clave: " + productoGranel.getClave());
        }
        productosGranel.add(productoGranel);
    }

    public void actualizar(ProductoGranel productoGranel) throws PersistenciaException {
        validarProductoGranel(productoGranel);
        ProductoGranel existente = consultarPorClave(productoGranel.getClave());
        if (existente == null) {
            throw new PersistenciaException("No existe un producto a granel con la clave: " + productoGranel.getClave());
        }
        int index = productosGranel.indexOf(existente);
        productosGranel.set(index, productoGranel);

    }

    public void eliminar(String clave) throws PersistenciaException {
        ProductoGranel producto = consultarPorClave(clave);
        if (producto == null) {
            throw new PersistenciaException("No existe un producto a granel con la clave: " + clave);
        }
        productosGranel.remove(producto);
    }

    public ProductoGranel consultarPorClave(String clave) {
    if (clave == null) return null;
    String claveLimpia = clave.trim().toUpperCase();
    return productosGranel.stream()
        .filter(p -> p.getClave() != null && p.getClave().toUpperCase().equals(claveLimpia))
        .findFirst()
        .orElse(null);
}



    public List<ProductoGranel> consultarTodos() {
        return new ArrayList<>(productosGranel);
    }

    public List<ProductoGranel> consultarConFiltros(String tipo, String unidad) {
    return productosGranel.stream()
            .filter(p -> tipo == null || p.getTipo().equals(tipo))
            .filter(p -> unidad == null || p.getUnidad().equals(unidad))
            .collect(Collectors.toList());
}

    // Método privado para validar un ProductoGranel
    private void validarProductoGranel(ProductoGranel productoGranel) throws PersistenciaException {
        if (productoGranel == null) {
            throw new PersistenciaException("El producto a granel no puede ser nulo");
        }
        if (productoGranel.getCantidad() <= 0) {
            throw new PersistenciaException("La cantidad del producto debe ser mayor que 0");
        }
    }
}
