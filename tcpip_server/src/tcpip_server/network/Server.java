/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_server.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import tcpip_server.console.Output;

/**
 *
 * @author Rudi
 */
public class Server {

    private ServerSocket serverSocket;
    private Socket client;
    private int port;
    private final ExecutorService pool;
    int timeout = 1000;

    public Server(int port, int poolSize) {
        this.port = port;
        pool = Executors.newFixedThreadPool(poolSize);
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            Output.setMessage("Server started");
            while (true) {
                Socket s;

                while (true) {
                    Future<?> submit = pool.submit(new ClientHandling(s = serverSocket.accept()));
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                client.close();
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
