package middleware.LP1;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class ServidorProducingTCP {
    private String message;
    
    int nrcli = 0;

    public static final int SERVERPORT = 9999;
    private OnMessageReceived messageListener = null;
    private boolean running = false;
    ServidorProducingTCPThread[] sendclis = new ServidorProducingTCPThread[10];

    PrintWriter mOut;
    BufferedReader in;
    
    ServerSocket serverSocket;
    //BattleCity bc = new BattleCity(nrcli);

    //el constructor pide una interface OnMessageReceived
    public ServidorProducingTCP(OnMessageReceived messageListener) {
        this.messageListener = messageListener;
    }
    
    public OnMessageReceived getMessageListener(){
        return this.messageListener;
    }
    
    public void sendMessageTCPServer(String message){
        for (int i = 1; i <= nrcli; i++) {
            sendclis[i].sendMessage(message);
        }
    }
    
    
    public void run(){
        running = true;
        try{
            System.out.println("Middleware Producing"+"S : Connecting...");
            serverSocket = new ServerSocket(SERVERPORT);
            
            while(running){
                Socket client = serverSocket.accept();
                System.out.println("Middleware Producing"+"S: Receiving...");
                nrcli++;
                System.out.println("Conexion establecida con el PRODUCTOR " + nrcli);
                sendclis[nrcli] = new ServidorProducingTCPThread(client,this,nrcli,sendclis);
                Thread t = new Thread(sendclis[nrcli]);
                t.start();
                System.out.println("Nuevo conectado:"+ nrcli+"");
                //bc.nrclient++;
                //bc.run()boolean condition = true;
              
            }
            
        }catch( Exception e){
            System.out.println("Error"+e.getMessage());
        }finally{

        }
    }
    public  ServidorProducingTCPThread[] getClients(){
        return sendclis;
    } 

    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}
