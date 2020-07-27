package middleware.LP2;

import java.util.Scanner;
import middleware.Global;

public class ServidorConsuming {

    ServidorConsumingTCP mTcpServer;
    Scanner sc;
    DesEncolar desencolarListener = null;
    public ServidorConsuming(DesEncolar listener) {
        this.desencolarListener = listener;
    }

    void iniciar() throws InterruptedException {
        new Thread(
            new Runnable() {
                @Override
                public void run() {
                    mTcpServer = new ServidorConsumingTCP(
                            new ServidorConsumingTCP.OnMessageReceived() {
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
        while (Global.nrcli>=1) {
            Thread.sleep(2000);
            desencolarListener.sacarDeCola();
        }

    }

    void ServidorRecibe(String llego) {

        try {
            System.out.println("Desde los clientes" + llego);
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
