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
    if (clave == null) {
        throw new PersistenciaException("Clave no puede ser nula");
    }
    String claveLimpia = clave.trim().toUpperCase();

    Producto producto = productos.consultarPorClave(claveLimpia);
    if (producto != null) {
        return producto;
    }
    producto = productosGranel.consultarPorClave(claveLimpia);
    if (producto != null) {
        return producto;
    }
    throw new PersistenciaException("Producto no encontrado con clave: " + clave);
}


    
    public void actualizarProducto(Producto producto) throws PersistenciaException {
    Producto p = consultarProductoPorClave(producto.getClave());
    if (p == null) {
        System.out.println("No se encontró producto con clave: " + producto.getClave());
        throw new PersistenciaException("Producto no encontrado para actualizar");
    } else {
        System.out.println("Producto encontrado: " + p.getNombre());
    }
    // Aquí ya sabes que existe, así que llamas a actualizar
    productos.actualizar(producto);
}

    
    public void eliminarProducto(String clave) throws PersistenciaException {
    Producto producto = consultarProductoPorClave(clave);
    if (producto instanceof ProductoGranel) {
        productosGranel.eliminar(clave);
    } else {
        productos.eliminar(clave);
    }
}

    
    public List<Producto> consultarCatalogo(String tipo, String unidad) {
    List<Producto> resultado = new ArrayList<>();

    // Consulta en productos empacados (tipo "E")
    if (tipo == null || tipo.equals("E")) {
        resultado.addAll(productos.consultarConFiltros(tipo, unidad));
    }

    // Consulta en productos a granel (tipo "G")
    if (tipo == null || tipo.equals("G")) {
        resultado.addAll(productosGranel.consultarConFiltros(tipo, unidad));
    }

    return resultado;
    }

    public List<Producto> consultarTodosLosProductos() {
    List<Producto> todos = new ArrayList<>();
    todos.addAll(productos.consultarTodos());      
    todos.addAll(productosGranel.consultarTodos()); 
    return todos;
}

    
   public void registrarCompra(String clave, double cantidad) throws PersistenciaException {
    Producto producto = consultarProductoPorClave(clave);

    if (producto == null) {
        throw new PersistenciaException("Producto no encontrado.");
    }

    if (producto instanceof ProductoGranel) {
        ProductoGranel productoGranel = (ProductoGranel) producto;

        
        productoGranel.setCantidad(productoGranel.getCantidad() + cantidad);
        productosGranel.actualizar(productoGranel); 

        
        MovimientoGranel movimiento = new MovimientoGranel(
            "Compra-" + System.currentTimeMillis(), 
            new Fecha(), 
            false, 
            productoGranel, 
            cantidad
        );
        movimientosGranel.registrarCompra(movimiento);

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

        // Validar stock suficiente
        if (productoGranel.getCantidad() < cantidad) {
            throw new PersistenciaException("Cantidad insuficiente en el inventario.");
        }

        // Actualizar cantidad del producto
        productoGranel.setCantidad(productoGranel.getCantidad() - cantidad);

        // Crear movimiento de venta, aquí asumo que 'true' significa venta
        MovimientoGranel movimiento = new MovimientoGranel(
            "Venta-" + System.currentTimeMillis(), 
            new Fecha(), 
            true,  // <- Cambiado a true para indicar que es venta
            productoGranel, 
            cantidad
        );

        // Registrar el movimiento
        movimientosGranel.registrarVenta(movimiento);

        // Actualizar el producto en la colección
        productosGranel.actualizar(productoGranel);

    } else {
        throw new PersistenciaException("Este método solo acepta productos a granel.");
    }
    }

    

    
    public MovimientoGranel consultarCompraPorClave(String clave) throws PersistenciaException {
        return movimientosGranel.consultarCompras().stream()
                .filter(m -> m.getProductoGranel().getClave().equals(clave))
                .findFirst()
                .orElseThrow(() -> new PersistenciaException("No existe compra registrada con esa clave."));
    }

    
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


    public List<ProductoGranel> mostrarInventario() {
        return productosGranel.consultarTodos();
    }

    public List<MovimientoGranel> mostrarCompras(Fecha inicio, Fecha fin) {
        if (inicio != null && fin != null) {
            return movimientosGranel.consultarComprasPorPeriodo(inicio, fin);
        } else {
            return movimientosGranel.consultarCompras();
        }
    }

    public List<MovimientoGranel> mostrarVentas(Fecha inicio, Fecha fin) {
        if (inicio != null && fin != null) {
            return movimientosGranel.consultarVentasPorPeriodo(inicio, fin);
        } else {
            return movimientosGranel.consultarVentas();
        }
    }
    
}

