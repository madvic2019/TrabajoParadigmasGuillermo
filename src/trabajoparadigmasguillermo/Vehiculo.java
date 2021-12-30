package trabajoparadigmasguillermo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Guillermo Díaz García
 */
public class Vehiculo  extends Thread{
    private final String nombre;
    private final Gasolinera gasolinera;
    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public Vehiculo (int num, Gasolinera gasolinera){
        this.nombre = "Vehículo"+num;
        this.gasolinera = gasolinera;
    }
    
    @Override
    public void run(){
        int surt = -1;
        
        Date now = new Date();
        System.out.println(formatoFecha.format(now) + " - " + nombre + " entrando en gasolinera");
        //Log here
        gasolinera.entrarGasolinera(nombre);
        
        now = new Date();
        System.out.println(formatoFecha.format(now) + " - " + nombre + " saliendo de gasolinera");
    }
    
    @Override
    public String toString(){
        return this.nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Gasolinera getGasolinera() {
        return gasolinera;
    }
}
