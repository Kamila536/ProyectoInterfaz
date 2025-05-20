package ObjetosServicio;

public class Periodo {
    private Fecha desde;
    private Fecha hasta;

    public Periodo(Fecha desde, Fecha hasta) {
        this.desde = desde;
        this.hasta = hasta;
    }

    public Fecha getDesde() {
        return desde;
    }

    public void setDesde(Fecha desde) {
        this.desde = desde;
    }

    public Fecha getHasta() {
        return hasta;
    }

    public void setHasta(Fecha hasta) {
        this.hasta = hasta;
    }

    public boolean contiene(Fecha fecha) {
        return fecha.after(desde) && fecha.before(hasta);
    }

    @Override
    public String toString() {
        return desde.toString() + " a " + hasta.toString();
    }
  
    }