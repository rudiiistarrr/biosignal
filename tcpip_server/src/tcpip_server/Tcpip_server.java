/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_server;

import dependency.Console;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import tcpip_server.network.Server;

/**
 *
 * @author Rudi
 */
public class Tcpip_server {

    private static ServerSocket serverSocket;
    private static int port = 5050;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(port);
            Server connection = new Server(serverSocket);

            Console.setMessage("Server started");
            Console.setMessage("Waiting for Clients");

            while (true) {
                connection.startServer();
            }
        } catch (IOException ex) {
            Logger.getLogger(Tcpip_server.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Tcpip_server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
