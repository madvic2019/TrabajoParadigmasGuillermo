/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajoparadigmasguillermo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;
import javax.swing.JTextField;

/**
 *
 * @author Guillermo
 */
public class MainFrame extends javax.swing.JFrame {
    
    private final Gasolinera gasolinera;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        gasolinera = new Gasolinera();
        initThreads();
    }
    // <editor-fold defaultstate="collapsed" desc="Deprecated class">
    public class Gasolinera {
        private final Queue<String> colaEntrada = new LinkedList<>();
        private final Queue<Integer> esperandoOperario = new LinkedList<>();
        private final Surtidor[] surtidores = new Surtidor[8];
        private final Semaphore semEntrada = new Semaphore(8,true);
        private final Semaphore semSurtVehiculos0 = new Semaphore(1);
        private final Semaphore semSurtVehiculos1 = new Semaphore(1);
        private final Semaphore semSurtVehiculos2 = new Semaphore(1);
        private final Semaphore semSurtVehiculos3 = new Semaphore(1);
        private final Semaphore semSurtVehiculos4 = new Semaphore(1);
        private final Semaphore semSurtVehiculos5 = new Semaphore(1);
        private final Semaphore semSurtVehiculos6 = new Semaphore(1);
        private final Semaphore semSurtVehiculos7 = new Semaphore(1);
        private final Semaphore semOperarios = new Semaphore(1);
        private final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public Gasolinera (){
            //this.campoCola = campoCola;
            for(int i=0;i<8;i++){
                Surtidor surtidor = new Surtidor(i);
                surtidores[i] = surtidor;
            }
        }

        public void entrarGasolinera(String vehiculo){
            int surt = -1;
            try{
                colaEntrada.add(vehiculo);
                semEntrada.acquire();
                actualizarCola();
                surt = surtidorLibre();
            } catch(Exception ex){
                Date now = new Date();
                System.out.println(formatoFecha.format(now) + " - Error ENTRADA COLA awaiting " + vehiculo + " while waiting at entrance");
                //Log here
            } try{
                Date now = new Date();
                System.out.println(formatoFecha.format(now) + " - " + vehiculo + " entrando a surtidor " + (surt+1));
                //Log here
                switch(surt){
                    case 0:
                        jCampoVeh1.setText(vehiculo);
                        semSurtVehiculos0.acquire();
                        break;
                    case 1:
                        jCampoVeh2.setText(vehiculo);
                        semSurtVehiculos1.acquire();
                        break;
                    case 2:
                        jCampoVeh3.setText(vehiculo);
                        semSurtVehiculos2.acquire();
                        break;
                    case 3:
                        jCampoVeh4.setText(vehiculo);
                        semSurtVehiculos3.acquire();
                        break;
                    case 4:
                        jCampoVeh5.setText(vehiculo);
                        semSurtVehiculos4.acquire();
                        break;
                    case 5:
                        jCampoVeh6.setText(vehiculo);
                        semSurtVehiculos5.acquire();
                        break;
                    case 6:
                        jCampoVeh7.setText(vehiculo);
                        semSurtVehiculos6.acquire();
                        break;
                    case 7:
                        jCampoVeh8.setText(vehiculo);
                        semSurtVehiculos7.acquire();
                        break;
                                      
                }
                if (surt != -1) {
                    colaEntrada.remove(vehiculo);
                    actualizarCola();
                    surtidores[surt].setVehiculo(vehiculo);
                    surtidores[surt].setLibre(false);
                    esperandoOperario.add(surt);
                }
                
                
                
            } catch(Exception ex){
                Date now = new Date();
                System.out.println(formatoFecha.format(now) + " - Error ENTRADA SURTIDOR while waiting for operator in surt " + surt);
                //Log error here
            } finally {
                semEntrada.release();
            }
        }

        public int operarSurtidor(int operario){
            int surt = -1;
            try{
                semOperarios.acquire();
                surt = surtidorEsperandoOperario();
                while(surt == -1){
                    Thread.sleep(100);
                    surt = surtidorEsperandoOperario();
                }
                surtidores[surt].setOperario(operario);
                switch(surt){
                    case 0:
                        jCampoOper1.setText("Operario" + operario);
                        break;
                    case 1:
                        jCampoOper2.setText("Operario" + operario);
                        break;
                    case 2:
                        jCampoOper3.setText("Operario" + operario);
                        break;
                    case 3:
                        jCampoOper4.setText("Operario" + operario);
                        break;
                    case 4:
                        jCampoOper5.setText("Operario" + operario);
                        break;
                    case 5:
                        jCampoOper6.setText("Operario" + operario);
                        break;
                    case 6:
                        jCampoOper7.setText("Operario" + operario);
                        break;
                    case 7:
                        jCampoOper8.setText("Operario" + operario);
                        break;
                }
            } catch(Exception ex){
                Date now = new Date();
                System.out.println(formatoFecha.format(now) + " - Error while operating surt " + surt);
                //Log error here
            } finally {
                semOperarios.release();
            }
            return surt;
        }

        public void surtidorTerminado(int surt){
            try{
                semOperarios.acquire();
                surtidores[surt].setVehiculo(null);
                surtidores[surt].setOperario(-1);
                surtidores[surt].setLibre(true);
                switch(surt){
                    case 0:
                        jCampoOper1.setText("");
                        jCampoVeh1.setText("");
                        semSurtVehiculos0.release();
                        break;
                    case 1:
                        jCampoOper2.setText("");
                        jCampoVeh2.setText("");
                        semSurtVehiculos1.release();
                        break;
                    case 2:
                        jCampoOper3.setText("");
                        jCampoVeh3.setText("");
                        semSurtVehiculos2.release();
                        break;
                    case 3:
                        jCampoOper4.setText("");
                        jCampoVeh4.setText("");
                        semSurtVehiculos3.release();
                        break;
                    case 4:
                        jCampoOper5.setText("");
                        jCampoVeh5.setText("");
                        semSurtVehiculos4.release();
                        break;
                    case 5:
                        jCampoOper6.setText("");
                        jCampoVeh6.setText("");
                        semSurtVehiculos5.release();
                        break;
                    case 6:
                        jCampoOper7.setText("");
                        jCampoVeh7.setText("");
                        semSurtVehiculos6.release();
                        break;
                    case 7:
                        jCampoOper8.setText("");
                        jCampoVeh8.setText("");
                        semSurtVehiculos7.release();
                        break;
                }
            } catch (Exception ex){
                Date now = new Date();
                System.out.println(formatoFecha.format(now) + " - Error while finishing surt " + surt);
                //Log error here
            } finally {
                semOperarios.release();
            }
        }
        
        private void actualizarCola(){
            String textoCola = "";
            for(String nav : colaEntrada){
                textoCola += nav + ", ";
            }
            jCampoCola.setText(textoCola);
        }

        private int surtidorLibre(){
            for(Surtidor nav : surtidores){
                if(nav.isLibre()) return nav.getNumero();
            }
            return -1;
        }
        private int surtidorEsperandoOperario(){
            if(!esperandoOperario.isEmpty()) return esperandoOperario.remove();
            else return -1;
        }
    }
    // </editor-fold>  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelCola = new javax.swing.JLabel();
        jCampoCola = new javax.swing.JTextField();
        jPanelSurt5 = new javax.swing.JPanel();
        jLabelVeh5 = new javax.swing.JLabel();
        jCampoVeh5 = new javax.swing.JTextField();
        jCampoOper5 = new javax.swing.JTextField();
        jLabelOper5 = new javax.swing.JLabel();
        jLabelSurt5 = new javax.swing.JLabel();
        jPanelSurt1 = new javax.swing.JPanel();
        jLabelVeh1 = new javax.swing.JLabel();
        jCampoVeh1 = new javax.swing.JTextField();
        jCampoOper1 = new javax.swing.JTextField();
        jLabelOper1 = new javax.swing.JLabel();
        jLabelSurt1 = new javax.swing.JLabel();
        jPanelSurt2 = new javax.swing.JPanel();
        jLabelVeh2 = new javax.swing.JLabel();
        jCampoVeh2 = new javax.swing.JTextField();
        jCampoOper2 = new javax.swing.JTextField();
        jLabelOper2 = new javax.swing.JLabel();
        jLabelSurt2 = new javax.swing.JLabel();
        jPanelSurt6 = new javax.swing.JPanel();
        jLabelVeh6 = new javax.swing.JLabel();
        jCampoVeh6 = new javax.swing.JTextField();
        jCampoOper6 = new javax.swing.JTextField();
        jLabelOper6 = new javax.swing.JLabel();
        jLabelSurt6 = new javax.swing.JLabel();
        jPanelSurt3 = new javax.swing.JPanel();
        jLabelVeh3 = new javax.swing.JLabel();
        jCampoVeh3 = new javax.swing.JTextField();
        jCampoOper3 = new javax.swing.JTextField();
        jLabelOper3 = new javax.swing.JLabel();
        jLabelSurt3 = new javax.swing.JLabel();
        jPanelSurt7 = new javax.swing.JPanel();
        jLabelVeh7 = new javax.swing.JLabel();
        jCampoVeh7 = new javax.swing.JTextField();
        jCampoOper7 = new javax.swing.JTextField();
        jLabelOper7 = new javax.swing.JLabel();
        jLabelSurt7 = new javax.swing.JLabel();
        jPanelSurt8 = new javax.swing.JPanel();
        jLabelVeh8 = new javax.swing.JLabel();
        jCampoVeh8 = new javax.swing.JTextField();
        jCampoOper8 = new javax.swing.JTextField();
        jLabelOper8 = new javax.swing.JLabel();
        jLabelSurt8 = new javax.swing.JLabel();
        jPanelSurt4 = new javax.swing.JPanel();
        jLabelVeh4 = new javax.swing.JLabel();
        jCampoVeh4 = new javax.swing.JTextField();
        jCampoOper4 = new javax.swing.JTextField();
        jLabelOper4 = new javax.swing.JLabel();
        jLabelSurt4 = new javax.swing.JLabel();
        jBotonParar = new javax.swing.JToggleButton();
        jBotonReanudar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(250, 400));
        setMaximumSize(new java.awt.Dimension(809, 416));
        setMinimumSize(new java.awt.Dimension(809, 416));

        jLabelCola.setText("Vehículos esperando entrar a Gasolinera:");

        jCampoCola.setEditable(false);
        jCampoCola.setBackground(new java.awt.Color(255, 255, 255));
        jCampoCola.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCampoColaActionPerformed(evt);
            }
        });

        jLabelVeh5.setText("Vehículo:");

        jCampoVeh5.setEditable(false);
        jCampoVeh5.setBackground(new java.awt.Color(255, 255, 255));

        jCampoOper5.setEditable(false);
        jCampoOper5.setBackground(new java.awt.Color(255, 255, 255));

        jLabelOper5.setText("Operario:");

        jLabelSurt5.setText("Surtidor 5");

        javax.swing.GroupLayout jPanelSurt5Layout = new javax.swing.GroupLayout(jPanelSurt5);
        jPanelSurt5.setLayout(jPanelSurt5Layout);
        jPanelSurt5Layout.setHorizontalGroup(
            jPanelSurt5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSurt5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSurt5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelVeh5)
                    .addComponent(jLabelOper5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSurt5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSurt5)
                    .addGroup(jPanelSurt5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jCampoOper5, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                        .addComponent(jCampoVeh5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelSurt5Layout.setVerticalGroup(
            jPanelSurt5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSurt5Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addComponent(jLabelSurt5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSurt5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVeh5)
                    .addComponent(jCampoVeh5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelSurt5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCampoOper5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelOper5))
                .addContainerGap())
        );

        jLabelVeh1.setText("Vehículo:");

        jCampoVeh1.setEditable(false);
        jCampoVeh1.setBackground(new java.awt.Color(255, 255, 255));

        jCampoOper1.setEditable(false);
        jCampoOper1.setBackground(new java.awt.Color(255, 255, 255));

        jLabelOper1.setText("Operario:");

        jLabelSurt1.setText("Surtidor 1");

        javax.swing.GroupLayout jPanelSurt1Layout = new javax.swing.GroupLayout(jPanelSurt1);
        jPanelSurt1.setLayout(jPanelSurt1Layout);
        jPanelSurt1Layout.setHorizontalGroup(
            jPanelSurt1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSurt1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSurt1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelVeh1)
                    .addComponent(jLabelOper1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSurt1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSurt1)
                    .addGroup(jPanelSurt1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jCampoOper1, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                        .addComponent(jCampoVeh1)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanelSurt1Layout.setVerticalGroup(
            jPanelSurt1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSurt1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelSurt1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSurt1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVeh1)
                    .addComponent(jCampoVeh1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelSurt1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCampoOper1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelOper1))
                .addContainerGap())
        );

        jPanelSurt2.setPreferredSize(new java.awt.Dimension(191, 105));

        jLabelVeh2.setText("Vehículo:");

        jCampoVeh2.setEditable(false);
        jCampoVeh2.setBackground(new java.awt.Color(255, 255, 255));

        jCampoOper2.setEditable(false);
        jCampoOper2.setBackground(new java.awt.Color(255, 255, 255));

        jLabelOper2.setText("Operario:");

        jLabelSurt2.setText("Surtidor 2");

        javax.swing.GroupLayout jPanelSurt2Layout = new javax.swing.GroupLayout(jPanelSurt2);
        jPanelSurt2.setLayout(jPanelSurt2Layout);
        jPanelSurt2Layout.setHorizontalGroup(
            jPanelSurt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSurt2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSurt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelVeh2)
                    .addComponent(jLabelOper2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSurt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSurt2)
                    .addGroup(jPanelSurt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jCampoOper2, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                        .addComponent(jCampoVeh2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelSurt2Layout.setVerticalGroup(
            jPanelSurt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSurt2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelSurt2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSurt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVeh2)
                    .addComponent(jCampoVeh2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelSurt2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCampoOper2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelOper2))
                .addContainerGap())
        );

        jPanelSurt6.setPreferredSize(new java.awt.Dimension(191, 105));

        jLabelVeh6.setText("Vehículo:");

        jCampoVeh6.setEditable(false);
        jCampoVeh6.setBackground(new java.awt.Color(255, 255, 255));

        jCampoOper6.setEditable(false);
        jCampoOper6.setBackground(new java.awt.Color(255, 255, 255));

        jLabelOper6.setText("Operario:");

        jLabelSurt6.setText("Surtidor 6");

        javax.swing.GroupLayout jPanelSurt6Layout = new javax.swing.GroupLayout(jPanelSurt6);
        jPanelSurt6.setLayout(jPanelSurt6Layout);
        jPanelSurt6Layout.setHorizontalGroup(
            jPanelSurt6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSurt6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSurt6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelVeh6)
                    .addComponent(jLabelOper6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSurt6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSurt6)
                    .addGroup(jPanelSurt6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jCampoOper6, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                        .addComponent(jCampoVeh6)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanelSurt6Layout.setVerticalGroup(
            jPanelSurt6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSurt6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelSurt6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSurt6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVeh6)
                    .addComponent(jCampoVeh6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelSurt6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCampoOper6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelOper6))
                .addContainerGap())
        );

        jPanelSurt3.setPreferredSize(new java.awt.Dimension(191, 105));

        jLabelVeh3.setText("Vehículo:");

        jCampoVeh3.setEditable(false);
        jCampoVeh3.setBackground(new java.awt.Color(255, 255, 255));

        jCampoOper3.setEditable(false);
        jCampoOper3.setBackground(new java.awt.Color(255, 255, 255));

        jLabelOper3.setText("Operario:");

        jLabelSurt3.setText("Surtidor 3");

        javax.swing.GroupLayout jPanelSurt3Layout = new javax.swing.GroupLayout(jPanelSurt3);
        jPanelSurt3.setLayout(jPanelSurt3Layout);
        jPanelSurt3Layout.setHorizontalGroup(
            jPanelSurt3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSurt3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSurt3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelVeh3)
                    .addComponent(jLabelOper3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSurt3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSurt3)
                    .addGroup(jPanelSurt3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jCampoOper3, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                        .addComponent(jCampoVeh3)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanelSurt3Layout.setVerticalGroup(
            jPanelSurt3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSurt3Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(jLabelSurt3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSurt3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVeh3)
                    .addComponent(jCampoVeh3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelSurt3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCampoOper3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelOper3))
                .addContainerGap())
        );

        jLabelVeh7.setText("Vehículo:");

        jCampoVeh7.setEditable(false);
        jCampoVeh7.setBackground(new java.awt.Color(255, 255, 255));

        jCampoOper7.setEditable(false);
        jCampoOper7.setBackground(new java.awt.Color(255, 255, 255));

        jLabelOper7.setText("Operario:");

        jLabelSurt7.setText("Surtidor 7");

        javax.swing.GroupLayout jPanelSurt7Layout = new javax.swing.GroupLayout(jPanelSurt7);
        jPanelSurt7.setLayout(jPanelSurt7Layout);
        jPanelSurt7Layout.setHorizontalGroup(
            jPanelSurt7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSurt7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSurt7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelVeh7)
                    .addComponent(jLabelOper7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSurt7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSurt7)
                    .addGroup(jPanelSurt7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jCampoOper7, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                        .addComponent(jCampoVeh7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelSurt7Layout.setVerticalGroup(
            jPanelSurt7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSurt7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelSurt7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSurt7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVeh7)
                    .addComponent(jCampoVeh7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelSurt7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCampoOper7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelOper7))
                .addContainerGap())
        );

        jLabelVeh8.setText("Vehículo:");

        jCampoVeh8.setEditable(false);
        jCampoVeh8.setBackground(new java.awt.Color(255, 255, 255));

        jCampoOper8.setEditable(false);
        jCampoOper8.setBackground(new java.awt.Color(255, 255, 255));

        jLabelOper8.setText("Operario:");

        jLabelSurt8.setText("Surtidor 8");

        javax.swing.GroupLayout jPanelSurt8Layout = new javax.swing.GroupLayout(jPanelSurt8);
        jPanelSurt8.setLayout(jPanelSurt8Layout);
        jPanelSurt8Layout.setHorizontalGroup(
            jPanelSurt8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSurt8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSurt8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelVeh8)
                    .addComponent(jLabelOper8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSurt8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSurt8)
                    .addGroup(jPanelSurt8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jCampoOper8, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                        .addComponent(jCampoVeh8)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanelSurt8Layout.setVerticalGroup(
            jPanelSurt8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSurt8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelSurt8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSurt8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVeh8)
                    .addComponent(jCampoVeh8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelSurt8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCampoOper8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelOper8))
                .addContainerGap())
        );

        jPanelSurt4.setPreferredSize(new java.awt.Dimension(191, 127));

        jLabelVeh4.setText("Vehículo:");

        jCampoVeh4.setEditable(false);
        jCampoVeh4.setBackground(new java.awt.Color(255, 255, 255));

        jCampoOper4.setEditable(false);
        jCampoOper4.setBackground(new java.awt.Color(255, 255, 255));

        jLabelOper4.setText("Operario:");

        jLabelSurt4.setText("Surtidor 4");

        javax.swing.GroupLayout jPanelSurt4Layout = new javax.swing.GroupLayout(jPanelSurt4);
        jPanelSurt4.setLayout(jPanelSurt4Layout);
        jPanelSurt4Layout.setHorizontalGroup(
            jPanelSurt4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSurt4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSurt4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelVeh4)
                    .addComponent(jLabelOper4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSurt4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSurt4)
                    .addGroup(jPanelSurt4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jCampoOper4, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                        .addComponent(jCampoVeh4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelSurt4Layout.setVerticalGroup(
            jPanelSurt4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSurt4Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(jLabelSurt4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelSurt4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVeh4)
                    .addComponent(jCampoVeh4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelSurt4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCampoOper4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelOper4))
                .addContainerGap())
        );

        jBotonParar.setText("Parar");

        jBotonReanudar.setText("Reanudar");
        jBotonReanudar.setMaximumSize(new java.awt.Dimension(59, 23));
        jBotonReanudar.setMinimumSize(new java.awt.Dimension(59, 23));
        jBotonReanudar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBotonReanudarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanelSurt5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelSurt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanelSurt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelSurt6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanelSurt7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelSurt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelSurt4, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                            .addComponent(jPanelSurt8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelCola)
                            .addComponent(jCampoCola, javax.swing.GroupLayout.PREFERRED_SIZE, 782, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(268, 268, 268)
                                .addComponent(jBotonParar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(jBotonReanudar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(7, 7, 7)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelCola)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCampoCola, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanelSurt2, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(jPanelSurt1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelSurt3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanelSurt8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelSurt7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelSurt6, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                            .addComponent(jPanelSurt5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanelSurt4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBotonReanudar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBotonParar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCampoColaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCampoColaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCampoColaActionPerformed

    private void jBotonReanudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBotonReanudarActionPerformed
        //TODO here
    }//GEN-LAST:event_jBotonReanudarActionPerformed

    private void initThreads(){
        ThreadStarter threadStarter = new ThreadStarter(gasolinera);
        threadStarter.start();
    }
    
//    public void actualizarCola(String textoCola){
//        jCampoCola.setText(textoCola);
//    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton jBotonParar;
    private javax.swing.JButton jBotonReanudar;
    private javax.swing.JTextField jCampoCola;
    private javax.swing.JTextField jCampoOper1;
    private javax.swing.JTextField jCampoOper2;
    private javax.swing.JTextField jCampoOper3;
    private javax.swing.JTextField jCampoOper4;
    private javax.swing.JTextField jCampoOper5;
    private javax.swing.JTextField jCampoOper6;
    private javax.swing.JTextField jCampoOper7;
    private javax.swing.JTextField jCampoOper8;
    private javax.swing.JTextField jCampoVeh1;
    private javax.swing.JTextField jCampoVeh2;
    private javax.swing.JTextField jCampoVeh3;
    private javax.swing.JTextField jCampoVeh4;
    private javax.swing.JTextField jCampoVeh5;
    private javax.swing.JTextField jCampoVeh6;
    private javax.swing.JTextField jCampoVeh7;
    private javax.swing.JTextField jCampoVeh8;
    private javax.swing.JLabel jLabelCola;
    private javax.swing.JLabel jLabelOper1;
    private javax.swing.JLabel jLabelOper2;
    private javax.swing.JLabel jLabelOper3;
    private javax.swing.JLabel jLabelOper4;
    private javax.swing.JLabel jLabelOper5;
    private javax.swing.JLabel jLabelOper6;
    private javax.swing.JLabel jLabelOper7;
    private javax.swing.JLabel jLabelOper8;
    private javax.swing.JLabel jLabelSurt1;
    private javax.swing.JLabel jLabelSurt2;
    private javax.swing.JLabel jLabelSurt3;
    private javax.swing.JLabel jLabelSurt4;
    private javax.swing.JLabel jLabelSurt5;
    private javax.swing.JLabel jLabelSurt6;
    private javax.swing.JLabel jLabelSurt7;
    private javax.swing.JLabel jLabelSurt8;
    private javax.swing.JLabel jLabelVeh1;
    private javax.swing.JLabel jLabelVeh2;
    private javax.swing.JLabel jLabelVeh3;
    private javax.swing.JLabel jLabelVeh4;
    private javax.swing.JLabel jLabelVeh5;
    private javax.swing.JLabel jLabelVeh6;
    private javax.swing.JLabel jLabelVeh7;
    private javax.swing.JLabel jLabelVeh8;
    private javax.swing.JPanel jPanelSurt1;
    private javax.swing.JPanel jPanelSurt2;
    private javax.swing.JPanel jPanelSurt3;
    private javax.swing.JPanel jPanelSurt4;
    private javax.swing.JPanel jPanelSurt5;
    private javax.swing.JPanel jPanelSurt6;
    private javax.swing.JPanel jPanelSurt7;
    private javax.swing.JPanel jPanelSurt8;
    // End of variables declaration//GEN-END:variables
}
