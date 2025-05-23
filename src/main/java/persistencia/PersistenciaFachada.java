
package persistencia;

import ObjetosNegocio.MovimientoGranel;
import ObjetosServicio.Fecha;
import ObjetosServicio.Periodo;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author Kamilala
 */
public class PersistenciaFachada {
   

    public class PersistenciaCompras {
        private final List<MovimientoGranel> compras;

        public PersistenciaCompras() {
            this.compras = new ArrayList<>();
        }

        public void registrarCompra(MovimientoGranel movimiento) throws PersistenciaException {
            if (movimiento == null) {
                throw new PersistenciaException("El movimiento de compra no puede ser nulo.");
        }

            Fecha hoy = new Fecha();
            Fecha fechaMovimiento = movimiento.getFecha();

            if (fechaMovimiento.getMes() != hoy.getMes() || 
                fechaMovimiento.getAnho() != hoy.getAnho() || 
                fechaMovimiento.compareTo(hoy) > 0) {
                throw new PersistenciaException("La fecha del movimiento debe estar en el mes actual y no ser futura.");
        }

        boolean existe = compras.stream()
            .anyMatch(m -> m.getProductoGranel().getClave().equals(movimiento.getProductoGranel().getClave())
                        && m.getFecha().equals(fechaMovimiento));

        if (existe) {
            throw new PersistenciaException("Ya existe una compra registrada para ese producto y fecha.");
        }

        movimiento.setProcesado(false);
        compras.add(movimiento);
    }

    public List<MovimientoGranel> consultarCompras() {
        return new ArrayList<>(compras);
    }

    public List<MovimientoGranel> consultarComprasPorPeriodo(Periodo periodo) {
        return compras.stream()
                .filter(m -> periodo.contiene(m.getFecha()))
                .collect(Collectors.toList());
    }
    
    }
    public class PersistenciaVentas {
    private final List<MovimientoGranel> ventas;

    public PersistenciaVentas() {
        this.ventas = new ArrayList<>();
    }

    public void registrarVenta(MovimientoGranel movimiento) throws PersistenciaException {
        if (movimiento == null) {
            throw new PersistenciaException("El movimiento de venta no puede ser nulo.");
        }

        Fecha hoy = new Fecha();
        Fecha fechaMovimiento = movimiento.getFecha();

        if (fechaMovimiento.getMes() != hoy.getMes() ||
            fechaMovimiento.getAnho() != hoy.getAnho() ||
            fechaMovimiento.compareTo(hoy) > 0) {
            throw new PersistenciaException("La fecha del movimiento debe estar en el mes actual y no ser futura.");
        }

        movimiento.setProcesado(false);
        ventas.add(movimiento);
    }

    public List<MovimientoGranel> consultarVentas() {
        return new ArrayList<>(ventas);
    }

    public List<MovimientoGranel> consultarVentasPorPeriodo(Periodo periodo) {
        return ventas.stream()
                .filter(m -> periodo.contiene(m.getFecha()))
                .collect(Collectors.toList());
    }
}
    
}
