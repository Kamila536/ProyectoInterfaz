
package control;

import ObjetosNegocio.Producto;
import ObjetosNegocio.ProductoGranel;
import ObjetosNegocio.MovimientoGranel;
import ObjetosServicio.Fecha;
import excepciones.PersistenciaException;
import persistencia.PersistenciaFachada;

import java.util.List;

/**
 * 
 * @author 52644
 */

public class Control {

    private final PersistenciaFachada fachada;

    public Control() {
        this.fachada = new PersistenciaFachada();
    }

    // --- Métodos de control para Productos (catálogo) ---

    public void agregarProducto(Producto producto) throws PersistenciaException {
        fachada.agregarProducto(producto);
    }

    public Producto consultarProducto(String clave) throws PersistenciaException {
        return fachada.consultarProductoPorClave(clave);
    }

    public void actualizarProducto(Producto producto) throws PersistenciaException {
        fachada.actualizarProducto(producto);
    }

    public void eliminarProducto(String clave) throws PersistenciaException {
        fachada.eliminarProducto(clave);
    }

    public List<Producto> consultarCatalogo(String tipo, String unidad) {
        return fachada.consultarCatalogo(tipo, unidad);
    }
    
    public List<Producto> obtenerTodosLosProductos() {
       return fachada.consultarCatalogo(null, null); // sin filtros
    }

    public Producto obtenerProducto(String clave) throws PersistenciaException {
    return fachada.consultarProductoPorClave(clave);
    }

    // --- Métodos de control para Movimientos (compras/ventas) ---

    public void registrarCompra(MovimientoGranel movimiento) throws PersistenciaException {
        fachada.registrarCompra(movimiento);
    }

    public void registrarVenta(MovimientoGranel movimiento) throws PersistenciaException {
        fachada.registrarVenta(movimiento);
    }

    public MovimientoGranel consultarCompraPorClave(String clave) throws PersistenciaException {
        return fachada.consultarCompraPorClave(clave);
    }

    public List<MovimientoGranel> mostrarCompras(Fecha inicio, Fecha fin) {
        return fachada.mostrarCompras(inicio, fin);
    }

    public List<MovimientoGranel> mostrarVentas(Fecha inicio, Fecha fin) {
        return fachada.mostrarVentas(inicio, fin);
    }

    // --- Métodos de control para Inventario ---

    public List<ProductoGranel> inventariarCompras() throws PersistenciaException {
        return fachada.inventariarCompras();
    }

    public List<ProductoGranel> desinventariarVentas() throws PersistenciaException {
        return fachada.desinventariarVentas();
    }

    public List<ProductoGranel> mostrarInventario() {
        return fachada.mostrarInventario();
    }
}



    

