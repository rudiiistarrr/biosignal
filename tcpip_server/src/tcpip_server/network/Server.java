/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_server.network;

import dependency.Channel;
import dependency.Data;
import dependency.Console;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            Console.setMessage("Server started");
            Console.setMessage("Waiting for Clients");
            client = serverSocket.accept();
            Console.setMessage("Client " + client.getInetAddress().toString() + " connected");
            
            InputStream input = client.getInputStream();
            int number = Data.receiveNumberOfChannels(input);
            
            Channel[] channels = new Channel[number];
            
            Thread [] t = new Thread[number];
            
            for(int i = 0;i < number; i++){
                channels[i] = new Channel();
                t[i] = new Thread(new Receive(channels[i], input));
                t[i].start();
            }
            
            while(true){}

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
