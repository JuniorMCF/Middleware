package middleware.LP1;

import java.util.Scanner;


public class ServidorProducing {

    ServidorProducingTCP mTcpServer;
    Scanner sc;
    OnQueuing encolarListener = null;
    public ServidorProducing(OnQueuing listener) {
        this.encolarListener = listener;
    }

    void iniciar() throws InterruptedException {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        mTcpServer = new ServidorProducingTCP(
                                new ServidorProducingTCP.OnMessageReceived() {
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

    }

    void ServidorRecibe(String llego) {
        System.out.println("Middleware: Se recibio desde el servidor:" + llego);
        this.encolarListener.Queuing(llego);

    }

    void ServidorEnvia(String envia) {
        if (envia != null) {
            if (mTcpServer != null) {
                mTcpServer.sendMessageTCPServer(envia);
            }
        }
    }

    public interface OnQueuing {
        public void Queuing(String message);
    }
}
