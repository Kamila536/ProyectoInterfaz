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
    public void testAgregarProductoExitoso() {
        try {
            repositorio.agregar(producto1);
            Producto recuperado = repositorio.consultarPorClave("EM001");

            assertNotNull(recuperado);
            assertEquals("Arroz", recuperado.getNombre());
            System.out.println("testAgregarProductoExitoso: Correcto");
        } catch (PersistenciaException e) {
            System.out.println("testAgregarProductoExitoso: Error - " + e.getMessage());
        }
    }

    @Test
    public void testAgregarProductoClaveRepetida() {
        try {
            repositorio.agregar(producto1);
            Producto productoRepetido = new Producto("EM001", "Azúcar", "E", "KG");
            repositorio.agregar(productoRepetido);
            System.out.println("testAgregarProductoClaveRepetida: Error - No lanzó excepción");
        } catch (PersistenciaException e) {
            System.out.println("testAgregarProductoClaveRepetida: Correcto - " + e.getMessage());
        }
    }

    @Test
    public void testAgregarProductoSinNombre() {
        try {
            Producto productoInvalido = new Producto("EM002", "", "E", "KG");
            repositorio.agregar(productoInvalido);
            System.out.println("testAgregarProductoSinNombre: Error - No lanzó excepción");
        } catch (PersistenciaException e) {
            System.out.println("testAgregarProductoSinNombre: Correcto - " + e.getMessage());
        }
    }

    @Test
    public void testAgregarProductoUnidadInvalida() {
        try {
            Producto productoInvalido = new Producto("EM002", "Azúcar", "E", "g");
            repositorio.agregar(productoInvalido);
            System.out.println("testAgregarProductoUnidadInvalida: Error - No lanzó excepción");
        } catch (PersistenciaException e) {
            System.out.println("testAgregarProductoUnidadInvalida: Correcto - " + e.getMessage());
        }
    }

    @Test
    public void testAgregarProductoTipoInvalido() {
        try {
            Producto productoInvalido = new Producto("EM002", "Azúcar", "X", "KG");
            repositorio.agregar(productoInvalido);
            System.out.println("testAgregarProductoTipoInvalido: Error - No lanzó excepción");
        } catch (PersistenciaException e) {
            System.out.println("testAgregarProductoTipoInvalido: Correcto - " + e.getMessage());
        }
    }

    @Test
    public void testConsultarPorClaveExistente() {
        try {
            repositorio.agregar(producto1);
            repositorio.agregar(producto2);

            Producto recuperado = repositorio.consultarPorClave("GR001");
            assertNotNull(recuperado);
            assertEquals("Frijol", recuperado.getNombre());
            System.out.println("testConsultarPorClaveExistente: Correcto");
        } catch (PersistenciaException e) {
            System.out.println("testConsultarPorClaveExistente: Error - " + e.getMessage());
        }
    }

    @Test
    public void testConsultarPorClaveInexistente() {
        Producto recuperado = repositorio.consultarPorClave("XX999");
        if (recuperado == null) {
            System.out.println("testConsultarPorClaveInexistente: Correcto");
        } else {
            System.out.println("testConsultarPorClaveInexistente: Error - Se encontró un producto inexistente");
        }
    }

    @Test
    public void testActualizarProductoExitoso() {
        try {
            repositorio.agregar(producto1);
            Producto productoModificado = new Producto("EM001", "Arroz Premium", "E", "KG");
            repositorio.actualizar(productoModificado);

            Producto recuperado = repositorio.consultarPorClave("EM001");
            assertEquals("Arroz Premium", recuperado.getNombre());
            System.out.println("testActualizarProductoExitoso: Correcto");
        } catch (PersistenciaException e) {
            System.out.println("testActualizarProductoExitoso: Error - " + e.getMessage());
        }
    }

    @Test
    public void testActualizarProductoInexistente() {
        try {
            Producto productoInexistente = new Producto("XX999", "No existe", "E", "KG");
            repositorio.actualizar(productoInexistente);
            System.out.println("testActualizarProductoInexistente: Error - No lanzó excepción");
        } catch (PersistenciaException e) {
            System.out.println("testActualizarProductoInexistente: Correcto - " + e.getMessage());
        }
    }

    @Test
    public void testEliminarProductoExitoso() {
        try {
            repositorio.agregar(producto1);
            repositorio.eliminar("EM001");

            Producto recuperado = repositorio.consultarPorClave("EM001");
            assertNull(recuperado);
            System.out.println("testEliminarProductoExitoso: Correcto");
        } catch (PersistenciaException e) {
            System.out.println("testEliminarProductoExitoso: Error - " + e.getMessage());
        }
    }

    @Test
    public void testEliminarProductoInexistente() {
        try {
            repositorio.eliminar("XX999");
            System.out.println("testEliminarProductoInexistente: Error - No lanzó excepción");
        } catch (PersistenciaException e) {
            System.out.println("testEliminarProductoInexistente: Correcto - " + e.getMessage());
        }
    }

    @Test
    public void testConsultarTodos() {
        try {
            repositorio.agregar(producto1);
            repositorio.agregar(producto2);

            List<Producto> productos = repositorio.consultarTodos();
            assertEquals(2, productos.size());
            System.out.println("testConsultarTodos: Correcto");
        } catch (PersistenciaException e) {
            System.out.println("testConsultarTodos: Error - " + e.getMessage());
        }
    }

    @Test
    public void testConsultarConFiltroTipo() {
        try {
            repositorio.agregar(producto1);
            repositorio.agregar(producto2);

            List<Producto> productos = repositorio.consultarConFiltros("E", null);
            assertEquals(1, productos.size());
            assertEquals("Arroz", productos.get(0).getNombre());
            System.out.println("testConsultarConFiltroTipo: Correcto");
        } catch (PersistenciaException e) {
            System.out.println("testConsultarConFiltroTipo: Error - " + e.getMessage());
        }
    }

    @Test
    public void testConsultarConFiltroUnidad() {
        try {
            repositorio.agregar(producto1);
            repositorio.agregar(producto2);
            Producto producto3 = new Producto("EM002", "Leche", "E", "L");
            repositorio.agregar(producto3);

            List<Producto> productos = repositorio.consultarConFiltros(null, "KG");
            assertEquals(2, productos.size());
            System.out.println("testConsultarConFiltroUnidad: Correcto");
        } catch (PersistenciaException e) {
            System.out.println("testConsultarConFiltroUnidad: Error - " + e.getMessage());
        }
    }

    @Test
    public void testConsultarConAmbosFiltros() {
        try {
            repositorio.agregar(producto1);
            repositorio.agregar(producto2);
            Producto producto3 = new Producto("EM002", "Leche", "E", "L");
            repositorio.agregar(producto3);

            List<Producto> productos = repositorio.consultarConFiltros("E", "KG");
            assertEquals(1, productos.size());
            assertEquals("Arroz", productos.get(0).getNombre());
            System.out.println("testConsultarConAmbosFiltros: Correcto");
        } catch (PersistenciaException e) {
            System.out.println("testConsultarConAmbosFiltros: Error - " + e.getMessage());
        }
    }
}