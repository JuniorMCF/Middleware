package middleware;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import middleware.TCPClient50;

public class Cliente50 {

    TCPClient50 mTcpClient;
    Scanner sc;
    private EnColar enColar = null;  
    
    public Cliente50(EnColar listener) {
        this.enColar = listener;
    }

    void iniciar() {

        new Thread(
                new Runnable() {

                    @Override
                    public void run() {
                        mTcpClient = new TCPClient50("192.168.1.12",
                                new TCPClient50.OnMessageReceived() {
                                    @Override
                                    public void messageReceived(String message) {
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
        while (!salir.equals("q")) {
            salir = sc.nextLine();
            ClienteEnvia(salir);
        }

    }

    void ClienteRecibe(String llego) {
        enColar.ponerEnCola(llego);

		//System.out.println(queue.peek());
        //queue.poll();
        if (llego != null) {
            System.out.flush();
        }
    }

    void ClienteEnvia(String envia) {
        if (mTcpClient != null) {
            mTcpClient.sendMessage(envia);
        }
    }

    public interface EnColar {
        public void ponerEnCola(String message);
    }
}
