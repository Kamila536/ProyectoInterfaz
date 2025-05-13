package persistencia.test;

/**
 *
 * @author Kamilala
 */

import ObjetosNegocio.Producto;
import ObjetosNegocio.ProductoGranel;
import excepciones.PersistenciaException;
import persistencia.ProductosGranel;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductosGranelTest {
    
    private ProductosGranel repositorio;
    
    @Before
    public void setUp() {
        repositorio = new ProductosGranel();
        Producto producto1 = new Producto("GR001", "Frijol", "G", "KG");
        ProductoGranel productoGranel1 = new ProductoGranel(producto1, 50.0);
        Producto producto2 = new Producto("GR002", "Arroz", "G", "KG");
        ProductoGranel productoGranel2 = new ProductoGranel(producto2, 75.0);
        
    }
    //Cambiar todos los productosGranel creados 
    @Test
    public void testAgregarProductoGranelExitoso() throws PersistenciaException {
        
        Producto producto1 = new Producto("GR001", "Frijol", "G", "KG");
        ProductoGranel productoGranel1 = new ProductoGranel(producto1, 50.0);
        
        // Agregar un producto a granel válido
        repositorio.agregar(productoGranel1);
        
        // Verificar que el producto se haya agregado correctamente
        ProductoGranel recuperado = repositorio.consultarPorClave("GR001");
        assertNotNull("El producto a granel debería encontrarse", recuperado);
        assertEquals("El nombre del producto debe coincidir", "Frijol", recuperado.getNombre());
        assertEquals("La cantidad del producto debe coincidir", 50.0f, recuperado.getCantidad(), 0.001);
    }
    
    @Test(expected = PersistenciaException.class)
    public void testAgregarProductoGranelNulo() throws PersistenciaException {
        // Intentar agregar un producto nulo
        repositorio.agregar(null); // Debe lanzar excepción
    }
    
    @Test(expected = PersistenciaException.class)
    public void testAgregarProductoGranelCantidadInvalida() throws PersistenciaException {
        // Intentar agregar un producto con cantidad 0
        
        Producto productoI0 = new Producto("GR003", "Azúcar", "G", "KG");
        ProductoGranel productoInvalido = new ProductoGranel(productoI0,0.0);
        repositorio.agregar(productoInvalido); // Debe lanzar excepción
    }
    
    @Test(expected = PersistenciaException.class)
    public void testAgregarProductoGranelCantidadNegativa() throws PersistenciaException {
        // Intentar agregar un producto con cantidad negativa
        Producto productoI1 = new Producto ("GR003", "Azúcar", "G", "KG");
        ProductoGranel productoInvalido = new ProductoGranel(productoI1, -10.0);
        repositorio.agregar(productoInvalido); // Debe lanzar excepción
    }
    
    @Test(expected = PersistenciaException.class)
    public void testAgregarProductoGranelClaveRepetida() throws PersistenciaException {
        
        Producto producto1 = new Producto("GR001", "Frijol", "G", "KG");
        ProductoGranel productoGranel1 = new ProductoGranel(producto1, 50.0);
        // Agregar un producto
        repositorio.agregar(productoGranel1);
        
        // Intentar agregar otro producto con la misma clave
        Producto productoR1 = new Producto("GR001", "Frijol", "G", "KG");
        ProductoGranel productoRepetido = new ProductoGranel(productoR1, 25.0);
        repositorio.agregar(productoRepetido); // Debe lanzar excepción
    }
    
    @Test
    public void testConsultarPorClaveExistente() throws PersistenciaException {
        
        Producto producto1 = new Producto("GR001", "Frijol", "G", "KG");
        ProductoGranel productoGranel1 = new ProductoGranel(producto1, 50.0);
        Producto producto2 = new Producto("GR002", "Arroz", "G", "KG");
        ProductoGranel productoGranel2 = new ProductoGranel(producto2, 75.0);
        
        // Agregar productos
        repositorio.agregar(productoGranel1);
        repositorio.agregar(productoGranel2);
        
        // Consultar por clave existente
        ProductoGranel recuperado = repositorio.consultarPorClave("GR002");
        assertNotNull("El producto a granel debería encontrarse", recuperado);
        assertEquals("El nombre del producto debe coincidir", "Arroz", recuperado.getNombre());
        assertEquals("La cantidad del producto debe coincidir", 75.0f, recuperado.getCantidad(), 0.001);
    }
    
    @Test
    public void testConsultarPorClaveInexistente() {
        // Consultar por clave que no existe
        ProductoGranel recuperado = repositorio.consultarPorClave("XX999");
        assertNull("No debería encontrarse ningún producto", recuperado);
    }
    
    @Test
    public void testEliminarProductoExitoso() throws PersistenciaException {
        
        Producto producto1 = new Producto("GR001", "Frijol", "G", "KG");
        ProductoGranel productoGranel1 = new ProductoGranel(producto1, 50.0);
        // Agregar un producto
        repositorio.agregar(productoGranel1);
        
        // Eliminar el producto
        repositorio.eliminar("GR001");
        
        // Verificar que se haya eliminado
        ProductoGranel recuperado = repositorio.consultarPorClave("GR001");
        assertNull("El producto debería haberse eliminado", recuperado);
    }
    
    @Test(expected = PersistenciaException.class)
    public void testEliminarProductoInexistente() throws PersistenciaException {
        // Intentar eliminar un producto que no existe
        repositorio.eliminar("XX999"); // Debe lanzar excepción
    }
    
    @Test
    public void testActualizarProductoExitoso() throws PersistenciaException {
        
        Producto producto1 = new Producto("GR001", "Frijol", "G", "KG");
        ProductoGranel productoGranel1 = new ProductoGranel(producto1, 50.0);
        // Agregar un producto
        repositorio.agregar(productoGranel1);
        
        // Modificar el producto
        
        Producto productoM1 = new Producto("GR001", "Frijol orgánico", "G", "KG");
        ProductoGranel productoModificado = new ProductoGranel(productoM1, 60.0);
        repositorio.actualizar(productoModificado);
        
        // Verificar que se haya actualizado
        ProductoGranel recuperado = repositorio.consultarPorClave("GR001");
        assertEquals("El nombre del producto debe haberse actualizado", "Frijol orgánico", recuperado.getNombre());
        assertEquals("La cantidad del producto debe haberse actualizado", 60.0f, recuperado.getCantidad(), 0.001);
    }
    
    @Test(expected = PersistenciaException.class)
    public void testActualizarProductoInexistente() throws PersistenciaException {
        // Intentar actualizar un producto que no existe
        Producto productoIE1 = new Producto("XX999", "No existe", "G", "KG");
        ProductoGranel productoInexistente = new ProductoGranel(productoIE1, 10.0);
        repositorio.actualizar(productoInexistente); // Debe lanzar excepción
    }
    
    @Test(expected = PersistenciaException.class)
    public void testActualizarProductoNulo() throws PersistenciaException {
        // Intentar actualizar con un producto nulo
        repositorio.actualizar(null); // Debe lanzar excepción
    }
    
    @Test(expected = PersistenciaException.class)
    
    public void testActualizarProductoCantidadInvalida() throws PersistenciaException {
        // Agregar un producto
        Producto productoIE1 = new Producto("XX999", "No existe", "G", "KG");
        ProductoGranel productoInexistente = new ProductoGranel(productoIE1, 10.0);
        repositorio.agregar(productoInexistente);
        
        // Intentar actualizar con cantidad inválida
        Producto productoI2 = new Producto("GR001", "Frijol", "G", "KG");
        ProductoGranel productoInvalido = new ProductoGranel(productoI2, 0.0);
        repositorio.actualizar(productoInvalido); // Debe lanzar excepción
    }
    
    @Test
    public void testConsultarTodos() throws PersistenciaException {
        // Verificar lista vacía
        List<ProductoGranel> productosVacios = repositorio.consultarTodos();
        assertEquals("La lista debería estar vacía", 0, productosVacios.size());
        
        
        Producto productoM1 = new Producto("GR001", "Frijol orgánico", "G", "KG");
        ProductoGranel productoGranel1 = new ProductoGranel(productoM1, 60.0);
        
        Producto producto2 = new Producto("GR002", "Arroz", "G", "KG");
        ProductoGranel productoGranel2 = new ProductoGranel(producto2, 75.0);
        
        // Agregar productos
        repositorio.agregar(productoGranel1);
        repositorio.agregar(productoGranel2);
        
        // Consultar todos los productos
        List<ProductoGranel> productos = repositorio.consultarTodos();
        assertEquals("Deben haber 2 productos", 2, productos.size());
    }
}