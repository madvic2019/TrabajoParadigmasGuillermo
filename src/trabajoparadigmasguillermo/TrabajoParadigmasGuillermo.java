package trabajoparadigmasguillermo;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guillermo Díaz García
 */
public class TrabajoParadigmasGuillermo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random rand = new Random();
        Gasolinera gasolinera = new Gasolinera();
        try{
            for(int i=0;i<2000;i++){
                Vehiculo vehiculo = new Vehiculo(i);
                vehiculo.start();
                Thread.sleep(500+(long)rand.nextInt(5500));
            }
        } catch (InterruptedException ex) {
            //Log error here
        } 
    }
}
