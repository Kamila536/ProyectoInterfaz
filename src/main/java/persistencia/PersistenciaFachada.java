package persistencia;

import ObjetosNegocio.*;
import ObjetosServicio.Fecha;
import excepciones.PersistenciaException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author 52644
 */

public class PersistenciaFachada {

    private final Productos productos;
    private final ProductosGranel productosGranel;
    private final MovimientosGranel movimientosGranel;

    public PersistenciaFachada() {
        this.productos = new Productos();
        this.productosGranel = new ProductosGranel();
        this.movimientosGranel = new MovimientosGranel();
    }

    // Requisito 15
    public void agregarProducto(Producto producto) throws PersistenciaException {
        productos.agregar(producto);
    }

    // Requisito 16
    public Producto consultarProductoPorClave(String clave) throws PersistenciaException {
        Producto producto = productos.consultarPorClave(clave);
        if (producto == null)
            throw new PersistenciaException("Producto no encontrado con clave: " + clave);
        return producto;
    }

    // Requisito 17
    public void actualizarProducto(Producto producto) throws PersistenciaException {
        consultarProductoPorClave(producto.getClave()); // para validar existencia
        productos.actualizar(producto);
    }

    // Requisito 18
    public void eliminarProducto(String clave) throws PersistenciaException {
        consultarProductoPorClave(clave); // para validar existencia
        productos.eliminar(clave);
    }

    // Requisito 19
    public List<Producto> consultarCatalogo(String tipo, String unidad) {
        return productos.consultarConFiltros(tipo, unidad);
    }

    // Requisito 20
    public void registrarCompra(MovimientoGranel movimiento) throws PersistenciaException {
        if (productos.consultarPorClave(movimiento.getProductoGranel().getClave()) == null)
            throw new PersistenciaException("No se puede registrar compra. El producto no está en el catálogo.");

        movimientosGranel.registrarCompra(movimiento);
    }

    // Requisito 21
    public void registrarVenta(MovimientoGranel movimiento) throws PersistenciaException {
        if (productos.consultarPorClave(movimiento.getProductoGranel().getClave()) == null)
            throw new PersistenciaException("No se puede registrar venta. El producto no está en el catálogo.");

        movimientosGranel.registrarVenta(movimiento);
    }

    // Requisito 22
    public MovimientoGranel consultarCompraPorClave(String clave) throws PersistenciaException {
        return movimientosGranel.consultarCompras().stream()
                .filter(m -> m.getProductoGranel().getClave().equals(clave))
                .findFirst()
                .orElseThrow(() -> new PersistenciaException("No existe compra registrada con esa clave."));
    }

    // Requisito 23
    public List<ProductoGranel> inventariarCompras() throws PersistenciaException {
        List<MovimientoGranel> compras = movimientosGranel.consultarCompras().stream()
                .filter(m -> !m.isProcesado())
                .collect(Collectors.toList());

        for (MovimientoGranel mov : compras) {
            ProductoGranel prod = mov.getProductoGranel();
            ProductoGranel inventario = productosGranel.consultarPorClave(prod.getClave());

            double cantidadNueva = prod.getCantidad();
            double total = inventario != null ? inventario.getCantidad() + cantidadNueva : cantidadNueva;

            String unidad = prod.getUnidad();
            if ((unidad.equals("KG") && total > 1500) || (unidad.equals("L") && total > 3000)) {
                continue;
            }

            if (inventario != null) {
                inventario.setCantidad(total);
                productosGranel.actualizar(inventario);
            } else {
                productosGranel.agregar(prod);
            }

            mov.setProcesado(true);
        }

        return productosGranel.consultarTodos();
    }

    // Requisito 24
    public List<ProductoGranel> desinventariarVentas() throws PersistenciaException {
        List<MovimientoGranel> ventas = movimientosGranel.consultarVentas().stream()
                .filter(m -> !m.isProcesado())
                .collect(Collectors.toList());

        for (MovimientoGranel mov : ventas) {
            ProductoGranel prod = mov.getProductoGranel();
            ProductoGranel inventario = productosGranel.consultarPorClave(prod.getClave());

            if (inventario == null || inventario.getCantidad() < prod.getCantidad()) {
                continue;
            }

            double nuevaCantidad = inventario.getCantidad() - prod.getCantidad();

            if (nuevaCantidad == 0) {
                productosGranel.eliminar(prod.getClave());
            } else {
                inventario.setCantidad(nuevaCantidad);
                productosGranel.actualizar(inventario);
            }

            mov.setProcesado(true);
        }

        return productosGranel.consultarTodos();
    }

    // Requisito 25
    public List<ProductoGranel> mostrarInventario() {
        return productosGranel.consultarTodos();
    }

    // Requisito 26
    public List<MovimientoGranel> mostrarCompras(Fecha inicio, Fecha fin) {
        if (inicio != null && fin != null) {
            return movimientosGranel.consultarComprasPorPeriodo(inicio, fin);
        } else {
            return movimientosGranel.consultarCompras();
        }
    }

    // Requisito 27
    public List<MovimientoGranel> mostrarVentas(Fecha inicio, Fecha fin) {
        if (inicio != null && fin != null) {
            return movimientosGranel.consultarVentasPorPeriodo(inicio, fin);
        } else {
            return movimientosGranel.consultarVentas();
        }
    }
}

