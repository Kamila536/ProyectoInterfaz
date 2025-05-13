package persistencia.test;


/**
 *
 * @author Kamilala
 */

import ObjetosNegocio.Producto;
import excepciones.PersistenciaException;
import persistencia.Productos;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductosTest {
    
private Productos repositorio;
    private Producto producto1;
    private Producto producto2;
    
    @Before
    public void setUp() {
        repositorio = new Productos();
        producto1 = new Producto("EM001", "Arroz", "E", "KG");
        producto2 = new Producto("GR001", "Frijol", "G", "KG");
    }
    
    @Test
    public void testAgregarProductoExitoso() throws PersistenciaException {
        // Agregar un producto válido
        repositorio.agregar(producto1);
        
        // Verificar que el producto se haya agregado correctamente
        Producto recuperado = repositorio.consultarPorClave("EM001");
        assertNotNull("El producto debería encontrarse", recuperado);
        assertEquals("El nombre del producto debe coincidir", "Arroz", recuperado.getNombre());
    }
    
    @Test(expected = PersistenciaException.class)
    public void testAgregarProductoClaveRepetida() throws PersistenciaException {
        // Agregar un producto
        repositorio.agregar(producto1);
        
        // Intentar agregar otro producto con la misma clave
        Producto productoRepetido = new Producto("EM001", "Azúcar", "E", "KG");
        repositorio.agregar(productoRepetido); // Debe lanzar excepción
    }
    
    @Test(expected = PersistenciaException.class)
    public void testAgregarProductoSinNombre() throws PersistenciaException {
        // Intentar agregar un producto sin nombre
        Producto productoInvalido = new Producto("EM002", "", "E", "KG");
        repositorio.agregar(productoInvalido); // Debe lanzar excepción
    }
    
    @Test(expected = PersistenciaException.class)
    public void testAgregarProductoUnidadInvalida() throws PersistenciaException {
        // Intentar agregar un producto con unidad inválida
        Producto productoInvalido = new Producto("EM002", "Azúcar", "E", "g");
        repositorio.agregar(productoInvalido); // Debe lanzar excepción
    }
    
    @Test(expected = PersistenciaException.class)
    public void testAgregarProductoTipoInvalido() throws PersistenciaException {
        // Intentar agregar un producto con tipo inválido
        Producto productoInvalido = new Producto("EM002", "Azúcar", "X", "KG");
        repositorio.agregar(productoInvalido); // Debe lanzar excepción
    }
    
    @Test
    public void testConsultarPorClaveExistente() throws PersistenciaException {
        // Agregar productos
        repositorio.agregar(producto1);
        repositorio.agregar(producto2);
        
        // Consultar por clave existente
        Producto recuperado = repositorio.consultarPorClave("GR001");
        assertNotNull("El producto debería encontrarse", recuperado);
        assertEquals("El nombre del producto debe coincidir", "Frijol", recuperado.getNombre());
    }
    
    @Test
    public void testConsultarPorClaveInexistente() {
        // Consultar por clave que no existe
        Producto recuperado = repositorio.consultarPorClave("XX999");
        assertNull("No debería encontrarse ningún producto", recuperado);
    }
    
    @Test
    public void testActualizarProductoExitoso() throws PersistenciaException {
        // Agregar un producto
        repositorio.agregar(producto1);
        
        // Modificar el producto
        Producto productoModificado = new Producto("EM001", "Arroz Premium", "E", "KG");
        repositorio.actualizar(productoModificado);
        
        // Verificar que se haya actualizado
        Producto recuperado = repositorio.consultarPorClave("EM001");
        assertEquals("El nombre del producto debe haberse actualizado", "Arroz Premium", recuperado.getNombre());
    }
    
    @Test(expected = PersistenciaException.class)
    public void testActualizarProductoInexistente() throws PersistenciaException {
        // Intentar actualizar un producto que no existe
        Producto productoInexistente = new Producto("XX999", "No existe", "E", "KG");
        repositorio.actualizar(productoInexistente); // Debe lanzar excepción
    }
    
    @Test
    public void testEliminarProductoExitoso() throws PersistenciaException {
        // Agregar un producto
        repositorio.agregar(producto1);
        
        // Eliminar el producto
        repositorio.eliminar("EM001");
        
        // Verificar que se haya eliminado
        Producto recuperado = repositorio.consultarPorClave("EM001");
        assertNull("El producto debería haberse eliminado", recuperado);
    }
    
    @Test(expected = PersistenciaException.class)
    public void testEliminarProductoInexistente() throws PersistenciaException {
        // Intentar eliminar un producto que no existe
        repositorio.eliminar("XX999"); // Debe lanzar excepción
    }
    
    @Test
    public void testConsultarTodos() throws PersistenciaException {
        // Agregar productos
        repositorio.agregar(producto1);
        repositorio.agregar(producto2);
        
        // Consultar todos los productos
        List<Producto> productos = repositorio.consultarTodos();
        assertEquals("Deben haber 2 productos", 2, productos.size());
    }
    
    @Test
    public void testConsultarConFiltroTipo() throws PersistenciaException {
        // Agregar productos
        repositorio.agregar(producto1);
        repositorio.agregar(producto2);
        
        // Consultar productos de tipo E
        List<Producto> productos = repositorio.consultarConFiltros("E", null);
        assertEquals("Debe haber 1 producto de tipo E", 1, productos.size());
        assertEquals("Debe ser el producto 'Arroz'", "Arroz", productos.get(0).getNombre());
    }
    
    @Test
    public void testConsultarConFiltroUnidad() throws PersistenciaException {
        // Agregar productos
        repositorio.agregar(producto1);
        repositorio.agregar(producto2);
        Producto producto3 = new Producto("EM002", "Leche", "E", "L");
        repositorio.agregar(producto3);
        
        // Consultar productos de unidad KG
        List<Producto> productos = repositorio.consultarConFiltros(null, "KG");
        assertEquals("Deben haber 2 productos de unidad KG", 2, productos.size());
    }
    
    @Test
    public void testConsultarConAmbosFiltros() throws PersistenciaException {
        // Agregar productos
        repositorio.agregar(producto1);
        repositorio.agregar(producto2);
        Producto producto3 = new Producto("EM002", "Leche", "E", "L");
        repositorio.agregar(producto3);
        
        // Consultar productos de tipo E y unidad KG
        List<Producto> productos = repositorio.consultarConFiltros("E", "KG");
        assertEquals("Debe haber 1 producto de tipo E y unidad KG", 1, productos.size());
        assertEquals("Debe ser el producto 'Arroz'", "Arroz", productos.get(0).getNombre());
    }
}