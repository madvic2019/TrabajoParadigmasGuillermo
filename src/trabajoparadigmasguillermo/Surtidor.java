package trabajoparadigmasguillermo;

/**
 *
 * @author Guillermo Díaz García
 */
public class Surtidor {
    private boolean libre;
    private Operario operario;
    private Vehiculo vehiculo;
    private final int numero;
    
    public Surtidor (int numero){
        this.numero = numero;
    }

    public boolean isLibre() {
        return libre;
    }

    public void setLibre(boolean libre) {
        this.libre = libre;
    }

    public Operario getOperario() {
        return operario;
    }

    public void setOperario(Operario operario) {
        this.operario = operario;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public int getNumero() {
        return numero;
    }
}
