package ObjetosNegocio;

import ObjetosServicio.Fecha;

public class MovimientoEmpacado extends Movimiento {
    private ProductoEmpacado productoEmpacado;

    // Constructor
    public MovimientoEmpacado(String cveMovimiento, Fecha fecha, boolean procesado, ProductoEmpacado productoEmpacado) {
        super(cveMovimiento, fecha, procesado); // Llama al constructor de Movimiento
        try {
            setProductoEmpacado(productoEmpacado); // Usa el setter para validar
        } catch (IllegalArgumentException e) {
            System.out.println("Error al inicializar MovimientoEmpacado: " + e.getMessage());
        }
    }
    
    public static void desplegarMovimientosEmpacados(MovimientoEmpacado... movimientos) {
        for (MovimientoEmpacado movimiento : movimientos) {
            if (movimiento != null) {
                System.out.println(movimiento);
            }
        }
    }

    // Getter y Setter para productoEmpacado
    public ProductoEmpacado getProductoEmpacado() {
        return productoEmpacado;
    }

    public void setProductoEmpacado(ProductoEmpacado productoEmpacado) {
        if (productoEmpacado == null) {
            throw new IllegalArgumentException("El producto empacado no puede ser nulo.");
        }
        this.productoEmpacado = productoEmpacado;
    }

    // toString() incluyendo atributos de Movimiento
    @Override
    public String toString() {
        return super.toString() + "," + productoEmpacado;
    }

    // Método equals para comparar MovimientoEmpacado
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
        MovimientoEmpacado other = (MovimientoEmpacado) obj;
        return productoEmpacado.equals(other.productoEmpacado);
    }

    // hashCode para MovimientoEmpacado
    @Override
    public int hashCode() {
        return super.hashCode() + productoEmpacado.hashCode();
    }
}