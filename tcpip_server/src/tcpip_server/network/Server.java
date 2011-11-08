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
import java.net.SocketException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rudi
 */
public class Server {

    private ServerSocket serverSocket;
    private Socket client;
    private final boolean OUTPUT = true;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        ScheduledThreadPoolExecutor service = null;
        try {
            client = serverSocket.accept();
            Console.setMessage("Client " + client.getInetAddress().toString() + " connected");


            InputStream input = client.getInputStream();
            int number = Data.receiveNumberOfChannels(input);

            if (number > 0) {

                service = new ScheduledThreadPoolExecutor(number);
                Channel[] channels = new Channel[number];
                
                client.setSoTimeout(5000);
                for (int i = 0; i < number; i++) {
                    channels[i] = new Channel();
                    service.schedule(new Receive(channels[i], input, OUTPUT, service), 1000, TimeUnit.MILLISECONDS);
                }

                while (!service.isTerminated()) {
                }

            }


            Console.setMessage("Client " + client.getInetAddress().toString() + " disconnected");

        } 
        catch (SocketException ex){
            service.shutdownNow();
            Console.setMessage("Client " + client.getInetAddress().toString() + " disconnected");
        }
        catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
