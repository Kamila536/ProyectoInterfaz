
package persistencia;

    
import ObjetosNegocio.MovimientoGranel;
import ObjetosServicio.Fecha;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Kamilala
 */
public class MovimientosGranel {

    private List<MovimientoGranel> movimientosCompra;
    private List<MovimientoGranel> movimientosVenta;
    
    // Constructor
    public MovimientosGranel() {
        this.movimientosCompra = new ArrayList<>();
        this.movimientosVenta = new ArrayList<>();
    }
    
    /**
     * Registra un movimiento de compra
     * @param movimiento Movimiento de compra a registrar
     * @throws PersistenciaException si la fecha es inválida o ya existe un movimiento para el mismo producto en la misma fecha
     */
    public void registrarCompra(MovimientoGranel movimiento) throws PersistenciaException {
        // Validar que el movimiento no sea nulo
        if (movimiento == null) {
            throw new PersistenciaException("El movimiento no puede ser nulo");
        }
        
        // Validar que la fecha sea válida (dentro del mes actual pero no después de la fecha actual)
        Fecha fechaActual = new Fecha();
        Fecha fechaMovimiento = movimiento.getFecha();
        
        if (fechaMovimiento.getMes() != fechaActual.getMes() || 
            fechaMovimiento.getAnho() != fechaActual.getAnho() ||
            fechaMovimiento.compareTo(fechaActual) > 0) {
            throw new PersistenciaException("La fecha del movimiento debe estar dentro del mes actual y no después de la fecha actual");
        }
        
        // Verificar que no exista otro movimiento para el mismo producto en la misma fecha
        boolean existeMovimiento = movimientosCompra.stream()
                .anyMatch(m -> m.getProductoGranel().getClave().equals(movimiento.getProductoGranel().getClave()) &&
                         m.getFecha().getDia() == fechaMovimiento.getDia() &&
                         m.getFecha().getMes() == fechaMovimiento.getMes() &&
                         m.getFecha().getAnho() == fechaMovimiento.getAnho());
                         
        if (existeMovimiento) {
            throw new PersistenciaException("Ya existe un movimiento de compra para el producto " + 
                                           movimiento.getProductoGranel().getClave() + " en la fecha " + fechaMovimiento);
        }
        
        // Asegurarse que el estatus sea falso (no procesado)
        movimiento.setProcesado(false);
        
        // Registrar el movimiento
        movimientosCompra.add(movimiento);
    }
    
    /**
     * Registra un movimiento de venta
     * @param movimiento Movimiento de venta a registrar
     * @throws PersistenciaException si la fecha es inválida
     */
    public void registrarVenta(MovimientoGranel movimiento) throws PersistenciaException {
        // Validar que el movimiento no sea nulo
        if (movimiento == null) {
            throw new PersistenciaException("El movimiento no puede ser nulo");
        }
        
        // Validar que la fecha sea válida (dentro del mes actual pero no después de la fecha actual)
        Fecha fechaActual = new Fecha();
        Fecha fechaMovimiento = movimiento.getFecha();
        
        if (fechaMovimiento.getMes() != fechaActual.getMes() || 
            fechaMovimiento.getAnho() != fechaActual.getAnho() ||
            fechaMovimiento.compareTo(fechaActual) > 0) {
            throw new PersistenciaException("La fecha del movimiento debe estar dentro del mes actual y no después de la fecha actual");
        }
        
        // Asegurarse que el estatus sea falso (no procesado)
        movimiento.setProcesado(false);
        
        // Registrar el movimiento
        movimientosVenta.add(movimiento);
    }
    
    /**
     * Consulta todos los movimientos de compra
     * @return Lista de todos los movimientos de compra
     */
    public List<MovimientoGranel> consultarCompras() {
        return new ArrayList<>(movimientosCompra);
    }
    
    /**
     * Consulta todos los movimientos de venta
     * @return Lista de todos los movimientos de venta
     */
    public List<MovimientoGranel> consultarVentas() {
        return new ArrayList<>(movimientosVenta);
    }
    
    /**
     * Consulta movimientos de compra en un periodo de fechas
     * @param fechaInicio Fecha de inicio del periodo
     * @param fechaFin Fecha de fin del periodo
     * @return Lista de movimientos de compra en el periodo
     */
    public List<MovimientoGranel> consultarComprasPorPeriodo(Fecha fechaInicio, Fecha fechaFin) {
        return movimientosCompra.stream()
                .filter(m -> m.getFecha().compareTo(fechaInicio) >= 0 && m.getFecha().compareTo(fechaFin) <= 0)
                .collect(Collectors.toList());
    }
    
    /**
     * Consulta movimientos de venta en un periodo de fechas
     * @param fechaInicio Fecha de inicio del periodo
     * @param fechaFin Fecha de fin del periodo
     * @return Lista de movimientos de venta en el periodo
     */
    public List<MovimientoGranel> consultarVentasPorPeriodo(Fecha fechaInicio, Fecha fechaFin) {
        return movimientosVenta.stream()
                .filter(m -> m.getFecha().compareTo(fechaInicio) >= 0 && m.getFecha().compareTo(fechaFin) <= 0)
                .collect(Collectors.toList());
    }

}
