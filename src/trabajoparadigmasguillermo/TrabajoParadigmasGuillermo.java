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
        
        for(int i=0;i<3;i++){
            Operario operario = new Operario(i, gasolinera, 5, rand);
            operario.start();
        }
        
        for(int i=0;i<2000;i++){
            Vehiculo vehiculo = new Vehiculo(i,gasolinera);
            vehiculo.start();
            //Log vehicle creation here
            System.out.println("Creado " + vehiculo);
            try{
                Thread.sleep(500+(long)rand.nextInt(5500));
            }
            catch (InterruptedException ex) {
                System.out.println("Error in sleep after creating " + vehiculo);
                //Log error here
            }
        } 
    }
}
