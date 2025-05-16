package persistencia.test;

import ObjetosNegocio.MovimientoGranel;
import ObjetosNegocio.Producto;
import ObjetosNegocio.ProductoGranel;
import ObjetosServicio.Fecha;
import excepciones.PersistenciaException;
import org.junit.Before;
import org.junit.Test;
import persistencia.MovimientosGranel;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 
 * @author 52644
 */

public class MovimientosGranelTest {

    private MovimientosGranel compras;
    private MovimientosGranel ventas;

    private ProductoGranel productoGranel1;
    private ProductoGranel productoGranel2;

    private MovimientoGranel movimientoCompra;
    private MovimientoGranel movimientoVenta;

    private Fecha fechaActual;

    @Before
    public void setUp() {
        compras = new MovimientosGranel();
        ventas = new MovimientosGranel();

        fechaActual = new Fecha();

        Producto baseProducto1 = new Producto("GR001", "Frijol", "G", "KG");
        productoGranel1 = new ProductoGranel(baseProducto1, 50.0);

        Producto baseProducto2 = new Producto("GR002", "Arroz", "G", "KG");
        productoGranel2 = new ProductoGranel(baseProducto2, 75.0);

        movimientoCompra = new MovimientoGranel("MC001", fechaActual, false, productoGranel1);
        movimientoVenta = new MovimientoGranel("MV001", fechaActual, false, productoGranel2);
    }

    @Test
    public void testRegistrarCompraExitoso() {
        try {
            compras.registrarCompra(movimientoCompra);
            List<MovimientoGranel> listaCompras = compras.consultarCompras();

            assertEquals(1, listaCompras.size());
            assertEquals("Frijol", listaCompras.get(0).getProductoGranel().getNombre());
            assertFalse(listaCompras.get(0).isProcesado());
            System.out.println("testRegistrarCompraExitoso: Correcto");
        } catch (PersistenciaException e) {
            System.out.println("testRegistrarCompraExitoso: Error - " + e.getMessage());
        }
    }

    @Test
    public void testRegistrarCompraNula() {
        try {
            compras.registrarCompra(null);
            fail("testRegistrarCompraNula: Error - Debió lanzar PersistenciaException");
        } catch (PersistenciaException e) {
            System.out.println("testRegistrarCompraNula: Correcto - " + e.getMessage());
        }
    }

    @Test
    public void testRegistrarCompraFechaFutura() {
        try {
            Fecha fechaFutura = new Fecha(fechaActual.getDia(), fechaActual.getMes() + 1, fechaActual.getAnho());
            MovimientoGranel movimientoInvalido = new MovimientoGranel("MC002", fechaFutura, false, productoGranel1);
            compras.registrarCompra(movimientoInvalido);
            fail("testRegistrarCompraFechaFutura: Error - Debió lanzar PersistenciaException");
        } catch (PersistenciaException e) {
            System.out.println("testRegistrarCompraFechaFutura: Correcto - " + e.getMessage());
        }
    }

    @Test
    public void testRegistrarCompraProductoRepetidoMismaFecha() {
        try {
            compras.registrarCompra(movimientoCompra);
            MovimientoGranel repetido = new MovimientoGranel("MC003", fechaActual, false, productoGranel1);
            compras.registrarCompra(repetido);
            fail("testRegistrarCompraProductoRepetidoMismaFecha: Error - Debió lanzar PersistenciaException");
        } catch (PersistenciaException e) {
            System.out.println("testRegistrarCompraProductoRepetidoMismaFecha: Correcto - " + e.getMessage());
        }
    }

    @Test
    public void testRegistrarVentaExitoso() {
        try {
            ventas.registrarVenta(movimientoVenta);
            List<MovimientoGranel> listaVentas = ventas.consultarVentas();

            assertEquals(1, listaVentas.size());
            assertEquals("Arroz", listaVentas.get(0).getProductoGranel().getNombre());
            assertFalse(listaVentas.get(0).isProcesado());
            System.out.println("testRegistrarVentaExitoso: Correcto");
        } catch (PersistenciaException e) {
            System.out.println("testRegistrarVentaExitoso: Error - " + e.getMessage());
        }
    }

    @Test
    public void testRegistrarVentaNula() {
        try {
            ventas.registrarVenta(null);
            fail("testRegistrarVentaNula: Error - Debió lanzar PersistenciaException");
        } catch (PersistenciaException e) {
            System.out.println("testRegistrarVentaNula: Correcto - " + e.getMessage());
        }
    }

    @Test
    public void testRegistrarVentaFechaFutura() {
        try {
            Fecha fechaFutura = new Fecha(fechaActual.getDia(), fechaActual.getMes() + 1, fechaActual.getAnho());
            MovimientoGranel movimientoInvalido = new MovimientoGranel("MV002", fechaFutura, false, productoGranel2);
            ventas.registrarVenta(movimientoInvalido);
            fail("testRegistrarVentaFechaFutura: Error - Debió lanzar PersistenciaException");
        } catch (PersistenciaException e) {
            System.out.println("testRegistrarVentaFechaFutura: Correcto - " + e.getMessage());
        }
    }

    @Test
    public void testConsultarCompras() {
        try {
            compras.registrarCompra(movimientoCompra);

            Fecha otraFecha = new Fecha(fechaActual.getDia() - 1, fechaActual.getMes(), fechaActual.getAnho());
            Producto baseExtra = new Producto("GR003", "Azúcar", "G", "KG");
            ProductoGranel productoExtra = new ProductoGranel(baseExtra, 30.0);
            MovimientoGranel otraCompra = new MovimientoGranel("MC004", otraFecha, false, productoExtra);
            compras.registrarCompra(otraCompra);

            List<MovimientoGranel> listaCompras = compras.consultarCompras();
            assertEquals(2, listaCompras.size());
            System.out.println("testConsultarCompras: Correcto");
        } catch (PersistenciaException e) {
            System.out.println("testConsultarCompras: Error - " + e.getMessage());
        }
    }

    @Test
    public void testConsultarVentas() {
        try {
            ventas.registrarVenta(movimientoVenta);

            Fecha otraFecha = new Fecha(fechaActual.getDia() - 1, fechaActual.getMes(), fechaActual.getAnho());
            Producto baseExtra = new Producto("GR003", "Azúcar", "G", "KG");
            ProductoGranel productoExtra = new ProductoGranel(baseExtra, 30.0);
            MovimientoGranel otraVenta = new MovimientoGranel("MV003", otraFecha, false, productoExtra);
            ventas.registrarVenta(otraVenta);

            List<MovimientoGranel> listaVentas = ventas.consultarVentas();
            assertEquals(2, listaVentas.size());
            System.out.println("testConsultarVentas: Correcto");
        } catch (PersistenciaException e) {
            System.out.println("testConsultarVentas: Error - " + e.getMessage());
        }
    }

    @Test
    public void testConsultarComprasPorPeriodo() {
        try {
            compras.registrarCompra(movimientoCompra);

            Fecha otraFecha = new Fecha(5, fechaActual.getMes(), fechaActual.getAnho());
            Producto baseExtra = new Producto("GR003", "Azúcar", "G", "KG");
            ProductoGranel productoExtra = new ProductoGranel(baseExtra, 30.0);
            MovimientoGranel otraCompra = new MovimientoGranel("MC005", otraFecha, false, productoExtra);
            compras.registrarCompra(otraCompra);

            Fecha inicio = new Fecha(1, fechaActual.getMes(), fechaActual.getAnho());
            Fecha fin = new Fecha(fechaActual.getDia(), fechaActual.getMes(), fechaActual.getAnho());

            List<MovimientoGranel> lista = compras.consultarComprasPorPeriodo(inicio, fin);
            assertEquals(2, lista.size());

            List<MovimientoGranel> listaEspecifica = compras.consultarComprasPorPeriodo(otraFecha, otraFecha);
            assertEquals(1, listaEspecifica.size());
            assertEquals("Azúcar", listaEspecifica.get(0).getProductoGranel().getNombre());

            System.out.println("testConsultarComprasPorPeriodo: Correcto");
        } catch (PersistenciaException e) {
            System.out.println("testConsultarComprasPorPeriodo: Error - " + e.getMessage());
        }
    }

    @Test
    public void testConsultarVentasPorPeriodo() {
        try {
            ventas.registrarVenta(movimientoVenta);

            Fecha otraFecha = new Fecha(10, fechaActual.getMes(), fechaActual.getAnho());
            Producto baseExtra = new Producto("GR003", "Azúcar", "G", "KG");
            ProductoGranel productoExtra = new ProductoGranel(baseExtra, 30.0);
            MovimientoGranel otraVenta = new MovimientoGranel("MV004", otraFecha, false, productoExtra);
            ventas.registrarVenta(otraVenta);

            Fecha inicio = new Fecha(1, fechaActual.getMes(), fechaActual.getAnho());
            Fecha fin = new Fecha(fechaActual.getDia(), fechaActual.getMes(), fechaActual.getAnho());

            List<MovimientoGranel> lista = ventas.consultarVentasPorPeriodo(inicio, fin);
            assertEquals(2, lista.size());

            List<MovimientoGranel> listaEspecifica = ventas.consultarVentasPorPeriodo(otraFecha, otraFecha);
            assertEquals(1, listaEspecifica.size());
            assertEquals("Azúcar", listaEspecifica.get(0).getProductoGranel().getNombre());

            System.out.println("testConsultarVentasPorPeriodo: Correcto");
        } catch (PersistenciaException e) {
            System.out.println("testConsultarVentasPorPeriodo: Error - " + e.getMessage());
        }
    }

    @Test
    public void testConsultarComprasPorPeriodoSinResultados() {
        try {
            compras.registrarCompra(movimientoCompra);

            Fecha inicioFuturo = new Fecha(1, fechaActual.getMes() + 1, fechaActual.getAnho());
            Fecha finFuturo = new Fecha(15, fechaActual.getMes() + 1, fechaActual.getAnho());

            List<MovimientoGranel> lista = compras.consultarComprasPorPeriodo(inicioFuturo, finFuturo);
            assertTrue(lista.isEmpty());
            System.out.println("testConsultarComprasPorPeriodoSinResultados: Correcto");
        } catch (PersistenciaException e) {
            System.out.println("testConsultarComprasPorPeriodoSinResultados: Error - " + e.getMessage());
        }
    }

    @Test
    public void testConsultarVentasPorPeriodoSinResultados() {
        try {
            ventas.registrarVenta(movimientoVenta);

            Fecha inicioFuturo = new Fecha(1, fechaActual.getMes() + 1, fechaActual.getAnho());
            Fecha finFuturo = new Fecha(15, fechaActual.getMes() + 1, fechaActual.getAnho());

            List<MovimientoGranel> lista = ventas.consultarVentasPorPeriodo(inicioFuturo, finFuturo);
            assertTrue(lista.isEmpty());
            System.out.println("testConsultarVentasPorPeriodoSinResultados: Correcto");
        } catch (PersistenciaException e) {
            System.out.println("testConsultarVentasPorPeriodoSinResultados: Error - " + e.getMessage());
        }
    }
}
