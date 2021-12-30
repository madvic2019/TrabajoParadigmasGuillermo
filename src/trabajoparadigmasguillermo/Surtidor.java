package trabajoparadigmasguillermo;

/**
 *
 * @author Guillermo Díaz García
 */
public class Surtidor {
    private boolean libre = true;
    private int operario = -1;
    private String vehiculo = null;
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

    public int getOperario() {
        return operario;
    }

    public void setOperario(int operario) {
        this.operario = operario;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public int getNumero() {
        return numero;
    }
}
