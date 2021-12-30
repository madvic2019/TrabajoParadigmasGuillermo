package trabajoparadigmasguillermo;

import java.util.Random;

/**
 *
 * @author Guillermo Díaz García
 */
public class Vehiculo  extends Thread{
    private final String nombre;
    private final Gasolinera gasolinera;
    
    public Vehiculo (int num, Gasolinera gasolinera){
        this.nombre = "Vehículo"+num;
        this.gasolinera = gasolinera;
    }
    
    @Override
    public void run(){
        int surt = -1;
        System.out.println(nombre + " entrando en gasolinera");
        //Log here
        while(surt == -1){
            surt = gasolinera.entrarGasolinera(nombre);
        }

        System.out.println(nombre + "entrando a surtidor");
        //Log here
        gasolinera.accederSurtidor(nombre, surt);

        System.out.println(nombre + " saliendo de gasolinera");
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

    public Random getRand() {
        return rand;
    }
}
