/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajoparadigmasguillermo;

/**
 *
 * @author Guillermo Díaz García
 */
public class Vehiculo  extends Thread{
    public String nombre;
    public Vehiculo (int num){
        this.nombre = "Vehículo"+Integer.toString(num);
    }
}
