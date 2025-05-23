package persistencia;

import ObjetosNegocio.Producto;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Productos {
    private List<Producto> productos;

    public Productos() {
        this.productos = new ArrayList<>();
    }

    public void agregar(Producto producto) throws PersistenciaException {
        validarProducto(producto);
        if (consultarPorClave(producto.getClave()) != null) {
            throw new PersistenciaException("Ya existe un producto con la clave: " + producto.getClave());
        }
        productos.add(producto);
    }

    public void actualizar(Producto producto) throws PersistenciaException {
    validarProducto(producto);
    boolean actualizado = false;
    for (int i = 0; i < productos.size(); i++) {
        Producto p = productos.get(i);
        if (p.getClave().equals(producto.getClave())) {
            productos.set(i, producto);
            actualizado = true;
            break;
        }
    }
    if (!actualizado) {
        throw new PersistenciaException("No existe un producto con la clave: " + producto.getClave());
    }
}


    public void eliminar(String clave) throws PersistenciaException {
        Producto producto = consultarPorClave(clave);
        if (producto == null) {
            throw new PersistenciaException("No existe un producto con la clave: " + clave);
        }
        productos.remove(producto);
    }

    public Producto consultarPorClave(String clave) {
    if (clave == null) return null;
    String claveLimpia = clave.trim().toUpperCase();
    return productos.stream()
        .filter(p -> p.getClave() != null && p.getClave().toUpperCase().equals(claveLimpia))
        .findFirst()
        .orElse(null);
}




    public List<Producto> consultarTodos() {
        return new ArrayList<>(productos);
    }

    public List<Producto> consultarConFiltros(String tipo, String unidad) {
        return productos.stream()
                .filter(p -> tipo == null || p.getTipo().equals(tipo))
                .filter(p -> unidad == null || p.getUnidad().equals(unidad))
                .collect(Collectors.toList());
    }

    // Método privado para validar un producto
    private void validarProducto(Producto producto) throws PersistenciaException {
        if (producto == null) {
            throw new PersistenciaException("El producto no puede ser nulo");
        }
        String clave = producto.getClave();
        if (clave == null || clave.length() != 5 || !clave.substring(2).matches("\\d{3}")) {
            throw new PersistenciaException("Clave inválida (debe tener 2 letras y 3 dígitos)");
        }
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new PersistenciaException("El producto debe tener nombre");
        }
        if (!producto.getUnidad().matches("KG|L|PZ")) {
            throw new PersistenciaException("Unidad inválida (debe ser KG, L o PZ)");
        }
        if (!producto.getTipo().matches("E|G")) {
            throw new PersistenciaException("Tipo inválido (debe ser E o G)");
        }
    }
}
