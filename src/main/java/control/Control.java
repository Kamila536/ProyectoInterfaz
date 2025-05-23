
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

   public void crearYAgregarProducto(String clave, String nombre, String tipo, String unidad) throws PersistenciaException {
    Producto producto;

    // Instanciar el objeto adecuado y establecer el tipo primero
    if (tipo.equals("G")) {
        ProductoGranel pg = new ProductoGranel();
        pg.setTipo(tipo);       // ✅ PRIMERO setTipo
        pg.setCantidad(0.01);   // cantidad inicial
        producto = pg;
    } else if (tipo.equals("E")) {
        producto = new Producto();
        producto.setTipo(tipo); // ✅ PRIMERO setTipo
    } else {
        throw new PersistenciaException("Tipo inválido.");
    }

    producto.setClave(clave);     // ✅ DESPUÉS de setTipo
    producto.setNombre(nombre);
    producto.setUnidad(unidad);

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

    public void registrarCompra(String clave, double cantidad) throws PersistenciaException {
        fachada.registrarCompra(clave,cantidad);
    }

    public void registrarVenta(String clave, double cantidad) throws PersistenciaException {
        fachada.registrarVenta(clave,cantidad);
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

    public List<ProductoGranel> consultarInventario() {
        return fachada.mostrarInventario();
    }
}



    

