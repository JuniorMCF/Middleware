/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

import middleware.LP1.*;
import middleware.LP2.*;
import java.util.LinkedList;
import java.util.Queue;


public class Middleware {

    //static Cliente50 cliente;  //conexion hacia el servidor python
    Producing productor;
    Consuming consumidor;

    public Queue<String> queue = new LinkedList<>();

    public static void main(String args[]) throws InterruptedException {
       Middleware middleware = new Middleware();
       middleware.iniciar();
    }

    void iniciar() {
        productor = new Producing(new Producing.OnMessageReceived() {
            @Override
            public void guardarEnCola(String message) {
                guardarElemento(message);
            }
        });
        consumidor = new Consuming(new Consuming.OnMessageReceived() {

            @Override
            public void sacarDeColaMiddleware() {
                
                if(queue.size()>0){
                    //sacamos un elemento de la cola y lo enviamos desde el servidor
                    String dato = sacarCola();
                    consumidor.enviarDatoDeCola(dato);
                    
                }else{
                    consumidor.enviarDatoDeCola("Cola vacia");
                }
 
            }
        });
    }

    void guardarElemento(String element) {
        this.queue.add(element);
    }

    String sacarCola() {
        String data = this.queue.peek();
        this.queue.poll();
        return data;
    }

}
