package trabajoparadigmasguillermo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guillermo Díaz García
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random rand = new Random();
        Gasolinera gasolinera = new Gasolinera();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
        for(int i=0;i<3;i++){
            Operario operario = new Operario(i, gasolinera, 5, rand);
            Date now = new Date();
            System.out.println(formatoFecha.format(now) + " - Creado " + operario);
            operario.start();
        }
        
        for(int i=0;i<2000;i++){
            Vehiculo vehiculo = new Vehiculo(i,gasolinera);
            //Log vehicle creation here
            Date now = new Date();
            System.out.println(formatoFecha.format(now) + " - Creado " + vehiculo);
            vehiculo.start();
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
