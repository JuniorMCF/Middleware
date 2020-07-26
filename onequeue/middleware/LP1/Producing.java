/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package middleware.LP1;

import java.util.logging.Level;
import java.util.logging.Logger;
import middleware.Middleware;

/**
 *
 * @author Usuario
 */
public class Producing {
    ServidorProducing server;
    private OnMessageReceived messageListener = null;
    
    public Producing(OnMessageReceived listener){
        this.messageListener = listener;
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        server = new ServidorProducing(new ServidorProducing.OnQueuing() {
                            @Override
                            public void Queuing(String message) {
                                synchronized(this){
                                    ponerEnCola(message);
                                }
                               
                            }
                           
                        });
                        try {
                            server.iniciar();

                        } catch (InterruptedException ex) {
                            Logger.getLogger(Middleware.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
        ).start();
    
    }
    void ponerEnCola(String message){
        messageListener.guardarEnCola(message);
    }
    
    public interface OnMessageReceived {
        public void guardarEnCola(String message);
    }
}
