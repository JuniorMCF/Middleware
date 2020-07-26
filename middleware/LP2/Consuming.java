/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package middleware.LP2;

import java.util.logging.Level;
import java.util.logging.Logger;
import middleware.Middleware;

public class Consuming {
	ServidorConsuming server;
	private OnMessageReceived messageListener = null;
	
	public Consuming(OnMessageReceived listener){
		this.messageListener = listener;
		new Thread(
			new Runnable() {
				@Override
				public void run() {
					server = new ServidorConsuming(new ServidorConsuming.DesEncolar() {
						@Override
						public void sacarDeCola() {
							synchronized(this){
								sacarCola();
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
	void sacarCola(){
		this.messageListener.sacarDeColaMiddleware();
	}
	public void enviarDatoDeCola(String dato){
		server.ServidorEnvia(dato);
	}
	
	public interface OnMessageReceived {
		public void sacarDeColaMiddleware();
	}
}
