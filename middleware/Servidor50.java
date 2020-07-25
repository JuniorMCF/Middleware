package middleware;

import java.util.Scanner;

public class Servidor50 {

    TCPServer50 mTcpServer;
    Scanner sc;
    DesEncolar desEncolar = null;
    public Servidor50(DesEncolar listener) {
        this.desEncolar = listener;
    }

    void iniciar() throws InterruptedException {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        mTcpServer = new TCPServer50(
                                new TCPServer50.OnMessageReceived() {
                                    @Override
                                    public void messageReceived(String message) {
                                        synchronized (this) {
                                            ServidorRecibe(message);
                                        }

                                    }

                                }
                        );
                        mTcpServer.run();
                    }
                }
        ).start();

        Thread.sleep(5000);
        while (true) {
            Thread.sleep(2000);
            desEncolar.sacarDeCola();
        }

    }

    void ServidorRecibe(String llego) {

        try {
            System.out.println("SERVIDOR40 El mensaje:" + llego);

        } catch (NumberFormatException e) {
            // bla
        }

    }

    void ServidorEnvia(String envia) {
        if (envia != null) {
            if (mTcpServer != null) {
                mTcpServer.sendMessageTCPServer(envia);
            }
        }
    }

    public interface DesEncolar {
        public void sacarDeCola();
    }
}
