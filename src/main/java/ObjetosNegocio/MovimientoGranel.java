package ObjetosNegocio;

import ObjetosServicio.Fecha;
import java.time.LocalDate;

public class MovimientoGranel extends Movimiento {
    private ProductoGranel productoGranel;

    // Constructor por defecto
    public MovimientoGranel(String aT001, LocalDate now) {
        super();
        this.productoGranel = null;
    }

    // Constructor con parámetros
    public MovimientoGranel(String cveMovimiento, Fecha fecha, boolean procesado, ProductoGranel productoGranel) {
        super(cveMovimiento, fecha, procesado); // Llama al constructor de Movimiento
        if (productoGranel == null) {
            throw new IllegalArgumentException("El producto a granel no puede ser nulo.");
        }
        this.productoGranel = productoGranel;
    }

    public static void desplegarMovimientosGranel(MovimientoGranel... movimientos) {
        for (MovimientoGranel movimiento : movimientos) {
            if (movimiento != null) {
                System.out.println(movimiento);
            }
        }
    }
    // Getter y Setter para productoGranel
    public ProductoGranel getProductoGranel() {
        return productoGranel;
    }

    public void setProductoGranel(ProductoGranel productoGranel) {
        if (productoGranel == null) {
            throw new IllegalArgumentException("El producto a granel no puede ser nulo.");
        }
        this.productoGranel = productoGranel;
    }

    // Método equals para comparar MovimientoGranel
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MovimientoGranel other = (MovimientoGranel) obj;
        return productoGranel.equals(other.productoGranel);
    }

    // Método hashCode
    @Override
    public int hashCode() {
        return super.hashCode() + productoGranel.hashCode();
    }

    // Método toString que incluye atributos de Movimiento y ProductoGranel
    @Override
    public String toString() {
        return super.toString() + ", " + productoGranel;
    }
}