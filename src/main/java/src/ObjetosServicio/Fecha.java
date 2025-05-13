package ObjetosServicio;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;
import java.util.GregorianCalendar;

public class Fecha extends GregorianCalendar {

    public static Fecha hoy() {
    return new Fecha();
    }

    public Fecha() {
        super();
        set(HOUR_OF_DAY, 0);
        set(MINUTE, 0);
        set(SECOND, 0);
        set(MILLISECOND, 0);
    }

    public Fecha(int dia, int mes, int anho) {
        super(anho, mes - 1, dia, 0, 0, 0); // Corregido: mes - 1
        set(MILLISECOND, 0);
    }

    public Fecha(Fecha fecha) {
        super(fecha.get(YEAR), fecha.get(MONTH), fecha.get(DAY_OF_MONTH), 0, 0, 0);
        set(MILLISECOND, 0);
    }

    public int getDia() {
        return get(DAY_OF_MONTH);
    }

    public int getMes() {
        return get(MONTH) + 1; // Corregido: mes + 1 para mostrar correctamente
    }

    public int getAnho() {
        return get(YEAR);
    }

    public void setDia(int dia) {
        set(DAY_OF_MONTH, dia);
    }

    public void setMes(int mes) {
        set(MONTH, mes - 1); // Corregido: mes - 1 para manejar índices correctamente
    }

    public void setAnho(int anho) {
        set(YEAR, anho);
    }

    public void setFecha(int dia, int mes, int anho) {
        set(DAY_OF_MONTH, dia);
        set(MONTH, mes - 1); // Corregido: mes - 1
        set(YEAR, anho);
    }

    public Fecha vencimiento(int dias, int meses, int anhos) {
        Fecha nuevaFecha = new Fecha(this); // Crear una copia para no modificar la actual
        nuevaFecha.add(DAY_OF_MONTH, dias);
        nuevaFecha.add(MONTH, meses);
        nuevaFecha.add(YEAR, anhos);
        return nuevaFecha;
    }

    public Fecha vencimiento(int dias, int meses) {
        return vencimiento(dias, meses, 0);
    }

    public Fecha vencimiento(int dias) {
        return vencimiento(dias, 0, 0);
    }

    public int lapso(Fecha desde) {
        long diferencia = getTimeInMillis() - desde.getTimeInMillis();
        return (int) (diferencia / (1000 * 60 * 60 * 24)); // Convertir milisegundos a días
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", getDia(), getMes(), getAnho());
    }

    // Método para restar días a la fecha actual
    public void restarDias(int dias) {
        add(DAY_OF_MONTH, -dias); // Restar días utilizando el método add
    }

    // Método para comparar si esta fecha es mayor que otra
    public boolean esMayorQue(Fecha otraFecha) {
        return this.compareTo(otraFecha) > 0; // Compara las fechas, devuelve true si esta es mayor
    }
}