package trabajoparadigmasguillermo;

import java.util.Random;

/**
 *
 * @author Guillermo Díaz García
 */
public class Operario extends Thread{
    private final int num;
    private final String nombre;
    private final Gasolinera gasolinera;
    private final int vehiculosPorDescanso;
    private final Random rand;
    private final int tiempoDescanso = 5000;
    
    public Operario (int num, Gasolinera gasolinera, int vehiculosPorDescanso, Random rand){
        this.num = num;
        this.nombre = "Operario"+num;
        this.gasolinera = gasolinera;
        this.vehiculosPorDescanso = vehiculosPorDescanso;
        this.rand = rand;
    }
    
    @Override
    public void run(){
        int contador = 0;
        while(true){
            if(contador<5){
                int surtidor = gasolinera.operarSurtidor(num);
                try{
                    long tiempoOperar = 4000 + (long)rand.nextInt(4000);
                    System.out.println(nombre + " operando surtidor " + surtidor + " durante " + (tiempoOperar/1000) + " segundos");
                    //Log here
                    sleep(tiempoOperar);
                } catch(InterruptedException ex){
                    System.out.println("Error with " + nombre + " sleeping");
                }
                gasolinera.surtidorTerminado(surtidor, nombre);
                contador++;
            }
            else{
                try{
                    System.out.println(nombre + " descansando " + (tiempoDescanso/1000) + " segundos");
                    //Log here
                    sleep(tiempoDescanso);
                } catch(InterruptedException ex){
                    System.out.println("Error with " + nombre + " sleeping");
                    //Log error here
                }
                contador = 0;
            }
        }
    }

    public int getVehiculosPorDescanso() {
        return vehiculosPorDescanso;
    }
}
