/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cliente;

import java.util.Scanner;
import cliente.TCPCliente;


public class Cliente {
	//String ip = "192.168.0.106";
	String ip = "18.219.234.80";
    TCPCliente mTcpClient;
    Scanner sc;
    
    public static void main (String args[]){
        Cliente cliente = new Cliente();
        cliente.iniciar();
    }
    
        
	void iniciar(){
	   new Thread(
			new Runnable() {

				@Override
				public void run() {
					mTcpClient = new TCPCliente(ip,
						new TCPCliente.OnMessageReceived(){
							@Override
							public void messageReceived(String message){
								ClienteRecibe(message);
							}
						}
					);
					mTcpClient.run();                   
				}
			}
		).start();
		//---------------------------
		String salir = "n";
		sc = new Scanner(System.in);
		while( !salir.equals("q")){
			salir = sc.nextLine();
			ClienteEnvia(salir);
		}
	
	}

	void ClienteRecibe(String llego){
		if(llego != null){
			System.out.flush();
			if(llego.equals("Cola vacia")) System.out.println(llego);
			else System.out.println("Mensaje desde el servidor python a traves del middleware: " + llego);
		}
	}

	void ClienteEnvia(String envia){
		if (mTcpClient != null)
			mTcpClient.sendMessage(envia);
	}
}
