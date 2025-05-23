package persistencia;

import ObjetosNegocio.*;
import ObjetosServicio.Fecha;
import excepciones.PersistenciaException;
import java.util.ArrayList;

import java.util.List;

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
    if (producto.getTipo().equals("G")) {
        // Es un producto a granel, debe ser ProductoGranel
        if (!(producto instanceof ProductoGranel)) {
            throw new PersistenciaException("El producto tipo G debe ser instancia de ProductoGranel.");
        }
        productosGranel.agregar((ProductoGranel) producto);
    } else if (producto.getTipo().equals("E")) {
        // Producto empacado
        productos.agregar(producto);
    } else {
        throw new PersistenciaException("Tipo de producto inválido.");
    }
}

public Producto consultarProductoPorClave(String clave) throws PersistenciaException {
    Producto producto = productos.consultarPorClave(clave);
    if (producto != null) {
        return producto;
    }
    producto = productosGranel.consultarPorClave(clave);
    if (producto != null) {
        return producto;
    }
    throw new PersistenciaException("Producto no encontrado con clave: " + clave);
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
    
   public void registrarCompra(String clave, double cantidad) throws PersistenciaException {
    Producto producto = consultarProductoPorClave(clave);

    if (producto == null) {
        throw new PersistenciaException("Producto no encontrado.");
    }

    if (producto instanceof ProductoGranel) {
        ProductoGranel productoGranel = (ProductoGranel) producto;
        productoGranel.setCantidad(productoGranel.getCantidad() + cantidad);

        MovimientoGranel movimiento = new MovimientoGranel("Compra-" + System.currentTimeMillis(), new Fecha(), false, productoGranel, cantidad);
        movimientosGranel.registrarCompra(movimiento);

        productosGranel.actualizar(productoGranel);

    } else {
        throw new PersistenciaException("Este método solo acepta productos a granel.");
    }
}


    public void registrarVenta(String clave, double cantidad) throws PersistenciaException {
    Producto producto = consultarProductoPorClave(clave);

    if (producto == null) {
        throw new PersistenciaException("Producto no encontrado.");
    }

    if (producto instanceof ProductoGranel) {
        ProductoGranel productoGranel = (ProductoGranel) producto;

        if (productoGranel.getCantidad() < cantidad) {
            throw new PersistenciaException("Cantidad insuficiente en el inventario.");
        }

        productoGranel.setCantidad(productoGranel.getCantidad() - cantidad);

        MovimientoGranel movimiento = new MovimientoGranel("Venta-" + System.currentTimeMillis(), new Fecha(), false, productoGranel, cantidad);
        movimientosGranel.registrarVenta(movimiento);

        productosGranel.actualizar(productoGranel);

    } else {
        throw new PersistenciaException("Este método solo acepta productos a granel.");
    }
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
        List<ProductoGranel> procesados = new ArrayList<>();

        for (MovimientoGranel mov : movimientosGranel.consultarCompras()) {
            if (!mov.isProcesado()){
                ProductoGranel producto = mov.getProductoGranel();
                producto.setCantidad(producto.getCantidad() + mov.getCantidad());
                mov.setProcesado(true);
                procesados.add(producto);
            }
        }

        return procesados;
    }

    public List<MovimientoGranel> consultarComprasPorProducto(String clave) {
    List<MovimientoGranel> comprasPorProducto = new ArrayList<>();
    for (MovimientoGranel mov : movimientosGranel.consultarCompras()) {
        if (mov.getProductoGranel().getClave().equals(clave)) {
            comprasPorProducto.add(mov);
        }
    }
    return comprasPorProducto;
}
    // Requisito 24
    public List<ProductoGranel> desinventariarVentas() throws PersistenciaException {
        List<ProductoGranel> productosAfectados = new ArrayList<>();
        
        List<MovimientoGranel> ventas = movimientosGranel.consultarVentas();
        
        for (MovimientoGranel mov : ventas) {
            if (!mov.isProcesado()) {
                ProductoGranel producto = mov.getProductoGranel();
                double cantidadActual = producto.getCantidad();
                double cantidadVender = mov.getCantidad();
                
                if(cantidadActual < cantidadVender){
                    throw new PersistenciaException("No hay suficiente stock del producto " + 
                            producto.getClave() + " para procesar la venta.");
                }
                
                producto.setCantidad(cantidadActual - cantidadVender);
                mov.setProcesado(true);
                productosAfectados.add(producto);
                
            }
             
            }
        return productosAfectados;
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

