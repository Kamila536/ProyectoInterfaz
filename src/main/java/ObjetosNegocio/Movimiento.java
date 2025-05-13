package ObjetosNegocio;

import ObjetosServicio.Fecha;

public class Movimiento {
    String cveMovimiento;
    private Fecha fecha;
    private boolean procesado;

    // Constructor por defecto
    public Movimiento() {
        this.cveMovimiento = "";
        this.fecha = new Fecha();  
        this.procesado = false;
    }

    // Constructor que recibe todos los atributos
    public Movimiento(String cveMovimiento, Fecha fecha, boolean procesado) {
        this.cveMovimiento = cveMovimiento;
        this.fecha = fecha;
        this.procesado = procesado;
    }

    // Métodos de acceso (getters y setters)
    public String getCveMovimiento() {
        return cveMovimiento;
    }

    public void setCveMovimiento(String cveMovimiento) {
        this.cveMovimiento = cveMovimiento;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }

    public boolean isProcesado() {
        return procesado;
    }

    public void setProcesado(boolean procesado) {
        this.procesado = procesado;
    }

    // Método hashCode: se debe generar un valor hash basado en los atributos
    @Override
    public int hashCode() {
        return cveMovimiento.hashCode() + fecha.hashCode() + Boolean.hashCode(procesado);
    }

    // Método equals: se debe comparar los atributos de manera adecuada
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Movimiento otroMovimiento = (Movimiento) obj;
        return cveMovimiento.equals(otroMovimiento.cveMovimiento) &&
               fecha.equals(otroMovimiento.fecha) &&
               procesado == otroMovimiento.procesado;
    }

    // Método toString: devuelve una representación legible del objeto
    @Override
    public String toString() {
        return cveMovimiento + ", " + fecha + ", " + procesado;
    }
}