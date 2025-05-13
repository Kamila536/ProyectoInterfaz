package persistencia.test;

/**
 *
 * @author Kamilala
 */

import ObjetosNegocio.MovimientoGranel;
import ObjetosNegocio.ProductoGranel;
import ObjetosServicio.Fecha;
import excepciones.PersistenciaException;
import persistencia.MovimientosGranel;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import persistencia.ProductosGranel;

public class MovimientosGranelTest {
    
    private MovimientosGranel compras;
    private MovimientosGranel ventas;
   
        fechaActual = new Fecha(); // Fecha actual
        
        
        // Crear productos
        productoGranel1 = new ProductoGranel("GR001", "Frijol", "G", "KG", 50.0);
        productoGranel2 = new ProductoGranel("GR002", "Arroz", "G", "KG", 75.0);
        
        // Crear movimientos
        movimientoCompra = new MovimientoGranel(productoGranel1, 10.0, fechaActual, false, 15.0);
        movimientoVenta = new MovimientoGranel(productoGranel2, 5.0, fechaActual, false, 20.0);
    
    
    @Test
    public void testRegistrarCompraExitoso() throws PersistenciaException {
        MovimientoGranel movimientoCompra;
        // Registrar una compra válida
        compras.registrarCompra(movimientoCompra);
        
        // Verificar que se haya registrado
        List<MovimientoGranel> compras;
        assertEquals("Debe haber 1 movimiento de compra", 1, compras.size());
        assertEquals("El producto debe coincidir", "Frijol", compras.get(0).getProductoGranel().getNombre());
        assertEquals("La cantidad debe coincidir", 10.0f, compras.get(0).getCantidad(), 0.001);
        assertFalse("El estatus debe ser falso", compras.get(0).isProcesado());
    }
    
    @Test(expected = PersistenciaException.class)
    public void testRegistrarCompraNula() throws PersistenciaException {
        // Intentar registrar una compra nula
        compras.registrarCompra(null); // Debe lanzar excepción
    }
    
    @Test(expected = PersistenciaException.class)
    public void testRegistrarCompraFechaFutura() throws PersistenciaException {
        // Crear una fecha futura (mes siguiente)
        Fecha fechaFutura = new Fecha(fechaActual.getDia(), fechaActual.getMes() + 1, fechaActual.getAnho());
        MovimientoGranel movimientoInvalido = new MovimientoGranel(productoGranel1, 10.0, fechaFutura, false, 15.0);
        
        // Intentar registrar una compra con fecha futura
        compras.registrarCompra(movimientoInvalido); // Debe lanzar excepción
    }
    
    @Test(expected = PersistenciaException.class)
    public void testRegistrarCompraProductoRepetidoMismaFecha() throws PersistenciaException {
        // Registrar una compra
        compras.registrarCompra(movimientoCompra);
        
        // Intentar registrar otra compra con el mismo producto en la misma fecha
        MovimientoGranel movimientoRepetido = new MovimientoGranel(productoGranel1, 15.0, fechaActual, false, 14.0);
        compras.registrarCompra(movimientoRepetido); // Debe lanzar excepción
    }
    
    @Test
    public void testRegistrarVentaExitoso() throws PersistenciaException {
        // Registrar una venta válida
        ventas.registrarVenta(movimientoVenta);
        
        // Verificar que se haya registrado
        List<MovimientoGranel> ventas = ventas.consultarVentas();
        assertEquals("Debe haber 1 movimiento de venta", 1, ventas.size());
        assertEquals("El producto debe coincidir", "Arroz", ventas.get(0).getProductoGranel().getNombre());
        assertEquals("La cantidad debe coincidir", 5.0f, ventas.get(0).getCantidad(), 0.001);
        assertFalse("El estatus debe ser falso", ventas.get(0).isProcesado());
    }
    
    @Test(expected = PersistenciaException.class)
    public void testRegistrarVentaNula() throws PersistenciaException {
        // Intentar registrar una venta nula
        ventas.registrarVenta(null); // Debe lanzar excepción
    }
    
    @Test(expected = PersistenciaException.class)
    public void testRegistrarVentaFechaFutura() throws PersistenciaException {
        // Crear una fecha futura (mes siguiente)
        Fecha fechaFutura = new Fecha(fechaActual.getDia(), fechaActual.getMes() + 1, fechaActual.getAnho());
        MovimientoGranel movimientoInvalido = new MovimientoGranel(productoGranel2, 5.0f, fechaFutura, false, 20.0f);
        
        // Intentar registrar una venta con fecha futura
        ventas.registrarVenta(movimientoInvalido); // Debe lanzar excepción
    }
    
    @Test
    public void testConsultarCompras() throws PersistenciaException {
        // Registrar varias compras
        compras.registrarCompra(movimientoCompra);
        
        // Crear otro producto y otra fecha (mismo mes)
        Fecha otraFecha = new Fecha(fechaActual.getDia() - 1, fechaActual.getMes(), fechaActual.getAnho());
        ProductoGranel otroProducto = new ProductoGranel("GR003", "Azúcar", "G", "KG", 30.0f);
        MovimientoGranel otraCompra = new MovimientoGranel(otroProducto, 20.0f, otraFecha, false, 12.0f);
        compras.registrarCompra(otraCompra);
        
        // Consultar todas las compras
        List<MovimientoGranel> compras = compras.consultarCompras();
        assertEquals("Deben haber 2 movimientos de compra", 2, compras.size());
    }
    
    @Test
    public void testConsultarVentas() throws PersistenciaException {
        // Registrar varias ventas
        ventas.registrarVenta(movimientoVenta);
        
        // Crear otro producto y otra fecha (mismo mes)
        Fecha otraFecha = new Fecha(fechaActual.getDia() - 1, fechaActual.getMes(), fechaActual.getAnho());
        ProductoGranel otroProducto = new ProductoGranel("GR003", "Azúcar", "G", "KG", 30.0f);
        MovimientoGranel otraVenta = new MovimientoGranel(otroProducto, 8.0f, otraFecha, false, 18.0f);
        ventas.registrarVenta(otraVenta);
        
        // Consultar todas las ventas
        List<MovimientoGranel> ventas = ventas.consultarVentas();
        assertEquals("Deben haber 2 movimientos de venta", 2, ventas.size());
    }
    
    @Test
    public void testConsultarComprasPorPeriodo() throws PersistenciaException {
        // Crear fechas para un periodo
        Fecha fechaInicio = new Fecha(1, fechaActual.getMes(), fechaActual.getAnho());
        Fecha fechaFin = new Fecha(fechaActual.getDia(), fechaActual.getMes(), fechaActual.getAnho());
        
        // Registrar compras en diferentes fechas del mismo mes
        compras.registrarCompra(movimientoCompra); // Fecha actual
        
        // Crear otra fecha (día 5 del mes actual)
        Fecha otraFecha = new Fecha(5, fechaActual.getMes(), fechaActual.getAnho());
        ProductoGranel otroProducto = new ProductoGranel("GR003", "Azúcar", "G", "KG", 30.0f);
        MovimientoGranel otraCompra = new MovimientoGranel(otroProducto, 20.0f, otraFecha, false, 12.0f);
        compras.registrarCompra(otraCompra);
        
        // Consultar compras en el periodo
        List<MovimientoGranel> compras = compras.consultarComprasPorPeriodo(fechaInicio, fechaFin);
        assertEquals("Deben haber 2 movimientos de compra en el periodo", 2, compras.size());
        
        // Consultar con un periodo más específico (solo día 5)
        Fecha inicioEspecifico = new Fecha(5, fechaActual.getMes(), fechaActual.getAnho());
        Fecha finEspecifico = new Fecha(5, fechaActual.getMes(), fechaActual.getAnho());
        List<MovimientoGranel> comprasEspecificas = compras.consultarComprasPorPeriodo(inicioEspecifico, finEspecifico);
        assertEquals("Debe haber 1 movimiento de compra en el periodo específico", 1, comprasEspecificas.size());
        assertEquals("El producto debe ser Azúcar", "Azúcar", comprasEspecificas.get(0).getProductoGranel().getNombre());
    }
    
    @Test
    public void testConsultarVentasPorPeriodo() throws PersistenciaException {
        // Crear fechas para un periodo
        Fecha fechaInicio = new Fecha(1, fechaActual.getMes(), fechaActual.getAnho());
        Fecha fechaFin = new Fecha(fechaActual.getDia(), fechaActual.getMes(), fechaActual.getAnho());
        
        // Registrar ventas en diferentes fechas del mismo mes
        ventas.registrarVenta(movimientoVenta); // Fecha actual
        
        // Crear otra fecha (día 10 del mes actual)
        Fecha otraFecha = new Fecha(10, fechaActual.getMes(), fechaActual.getAnho());
        ProductoGranel otroProducto = new ProductoGranel("GR003", "Azúcar", "G", "KG", 30.0f);
        MovimientoGranel otraVenta = new MovimientoGranel(otroProducto, 8.0f, otraFecha, false, 18.0f);
        repositorio.registrarVenta(otraVenta);
        
        // Consultar ventas en el periodo
        List<MovimientoGranel> ventas = ventas.consultarVentasPorPeriodo(fechaInicio, fechaFin);
        assertEquals("Deben haber 2 movimientos de venta en el periodo", 2, ventas.size());
        
        // Consultar con un periodo más específico (solo día 10)
        Fecha inicioEspecifico = new Fecha(10, fechaActual.getMes(), fechaActual.getAnho());
        Fecha finEspecifico = new Fecha(10, fechaActual.getMes(), fechaActual.getAnho());
        List<MovimientoGranel> ventasEspecificas = ventas.consultarVentasPorPeriodo(inicioEspecifico, finEspecifico);
        assertEquals("Debe haber 1 movimiento de venta en el periodo específico", 1, ventasEspecificas.size());
        assertEquals("El producto debe ser Azúcar", "Azúcar", ventasEspecificas.get(0).getProductoGranel().getNombre());
    }
    
    @Test
    public void testConsultarComprasPorPeriodoSinResultados() throws PersistenciaException {
        // Registrar una compra en la fecha actual
        compras.registrarCompra(movimientoCompra);
        
        // Crear un periodo futuro
        Fecha fechaInicioFutura = new Fecha(1, fechaActual.getMes() + 1, fechaActual.getAnho());
        Fecha fechaFinFutura = new Fecha(15, fechaActual.getMes() + 1, fechaActual.getAnho());
        
        // Consultar compras en un periodo donde no hay registros
        List<MovimientoGranel> compras = compras.consultarComprasPorPeriodo(fechaInicioFutura, fechaFinFutura);
        assertTrue("No debería haber compras en ese periodo", compras.isEmpty());
    }
    
    @Test
    public void testConsultarVentasPorPeriodoSinResultados() throws PersistenciaException {
        // Registrar una venta en la fecha actual
        ventas.registrarVenta(movimientoVenta);
        
        // Crear un periodo pasado
        Fecha fechaInicioPasada = new Fecha(1, fechaActual.getMes() - 1, fechaActual.getAnho());
        Fecha fechaFinPasada = new Fecha(15, fechaActual.getMes() - 1, fechaActual.getAnho());
        
        // Consultar ventas en un periodo donde no hay registros
        List<MovimientoGranel> ventas = ventas.consultarVentasPorPeriodo(fechaInicioPasada, fechaFinPasada);
        assertTrue("No debería haber ventas en ese periodo", ventas.isEmpty());
    }
}