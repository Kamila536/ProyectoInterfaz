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

        fechaActual = new Fecha(); // Fecha de hoy

        Producto baseProducto1 = new Producto("GR001", "Frijol", "G", "KG");
        productoGranel1 = new ProductoGranel(baseProducto1, 50.0);

        Producto baseProducto2 = new Producto("GR002", "Arroz", "G", "KG");
        productoGranel2 = new ProductoGranel(baseProducto2, 75.0);

        movimientoCompra = new MovimientoGranel("MC001", fechaActual, false, productoGranel1);
        movimientoVenta = new MovimientoGranel("MV001", fechaActual, false, productoGranel2);
    }

    @Test
    public void testRegistrarCompraExitoso() throws PersistenciaException {
        compras.registrarCompra(movimientoCompra);
        List<MovimientoGranel> listaCompras = compras.consultarCompras();

        assertEquals(1, listaCompras.size());
        assertEquals("Frijol", listaCompras.get(0).getProductoGranel().getNombre());
        assertFalse(listaCompras.get(0).isProcesado());
    }

    @Test(expected = PersistenciaException.class)
    public void testRegistrarCompraNula() throws PersistenciaException {
        compras.registrarCompra(null);
    }

    @Test(expected = PersistenciaException.class)
    public void testRegistrarCompraFechaFutura() throws PersistenciaException {
        Fecha fechaFutura = new Fecha(fechaActual.getDia(), fechaActual.getMes() + 1, fechaActual.getAnho());
        MovimientoGranel movimientoInvalido = new MovimientoGranel("MC002", fechaFutura, false, productoGranel1);
        compras.registrarCompra(movimientoInvalido);
    }

    @Test(expected = PersistenciaException.class)
    public void testRegistrarCompraProductoRepetidoMismaFecha() throws PersistenciaException {
        compras.registrarCompra(movimientoCompra);
        MovimientoGranel repetido = new MovimientoGranel("MC003", fechaActual, false, productoGranel1);
        compras.registrarCompra(repetido);
    }

    @Test
    public void testRegistrarVentaExitoso() throws PersistenciaException {
        ventas.registrarVenta(movimientoVenta);
        List<MovimientoGranel> listaVentas = ventas.consultarVentas();

        assertEquals(1, listaVentas.size());
        assertEquals("Arroz", listaVentas.get(0).getProductoGranel().getNombre());
        assertFalse(listaVentas.get(0).isProcesado());
    }

    @Test(expected = PersistenciaException.class)
    public void testRegistrarVentaNula() throws PersistenciaException {
        ventas.registrarVenta(null);
    }

    @Test(expected = PersistenciaException.class)
    public void testRegistrarVentaFechaFutura() throws PersistenciaException {
        Fecha fechaFutura = new Fecha(fechaActual.getDia(), fechaActual.getMes() + 1, fechaActual.getAnho());
        MovimientoGranel movimientoInvalido = new MovimientoGranel("MV002", fechaFutura, false, productoGranel2);
        ventas.registrarVenta(movimientoInvalido);
    }

    @Test
    public void testConsultarCompras() throws PersistenciaException {
        compras.registrarCompra(movimientoCompra);

        Fecha otraFecha = new Fecha(fechaActual.getDia() - 1, fechaActual.getMes(), fechaActual.getAnho());
        Producto baseExtra = new Producto("GR003", "Azúcar", "G", "KG");
        ProductoGranel productoExtra = new ProductoGranel(baseExtra, 30.0);
        MovimientoGranel otraCompra = new MovimientoGranel("MC004", otraFecha, false, productoExtra);
        compras.registrarCompra(otraCompra);

        List<MovimientoGranel> listaCompras = compras.consultarCompras();
        assertEquals(2, listaCompras.size());
    }

    @Test
    public void testConsultarVentas() throws PersistenciaException {
        ventas.registrarVenta(movimientoVenta);

        Fecha otraFecha = new Fecha(fechaActual.getDia() - 1, fechaActual.getMes(), fechaActual.getAnho());
        Producto baseExtra = new Producto("GR003", "Azúcar", "G", "KG");
        ProductoGranel productoExtra = new ProductoGranel(baseExtra, 30.0);
        MovimientoGranel otraVenta = new MovimientoGranel("MV003", otraFecha, false, productoExtra);
        ventas.registrarVenta(otraVenta);

        List<MovimientoGranel> listaVentas = ventas.consultarVentas();
        assertEquals(2, listaVentas.size());
    }

    @Test
    public void testConsultarComprasPorPeriodo() throws PersistenciaException {
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
    }

    @Test
    public void testConsultarVentasPorPeriodo() throws PersistenciaException {
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
    }

    @Test
    public void testConsultarComprasPorPeriodoSinResultados() throws PersistenciaException {
        compras.registrarCompra(movimientoCompra);

        Fecha inicioFuturo = new Fecha(1, fechaActual.getMes() + 1, fechaActual.getAnho());
        Fecha finFuturo = new Fecha(15, fechaActual.getMes() + 1, fechaActual.getAnho());

        List<MovimientoGranel> lista = compras.consultarComprasPorPeriodo(inicioFuturo, finFuturo);
        assertTrue(lista.isEmpty());
    }

    @Test
    public void testConsultarVentasPorPeriodoSinResultados() throws PersistenciaException {
        ventas.registrarVenta(movimientoVenta);

    }
    
}
