/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_client;

import dependency.Console;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tcpip_client.network.Client;

/**
 *
 * @author Rudi
 */
public class Tcpip_client {

    /**
     * @param args the command line arguments
     */
    private static Socket socket;
    private static boolean connected = false;
    private static String ip = "localhost";
    private static int port = 5050;
    private static final int timeout = 5000;

    public static void main(String[] args) {

        socket = null;
        
        while (true) {
            while (!connected) {
                try {
                    socket = new Socket(ip, port);
                    connected = true;
                } catch (SocketException ex) {
                    System.out.println("Server is not available... Try to reconnect in " + timeout / 1000 + " seconds");
                    try {
                        Thread.sleep(timeout);
                    } catch (InterruptedException ex1) {
                        Logger.getLogger(Tcpip_client.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } catch (UnknownHostException ex) {
                    Logger.getLogger(Tcpip_client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Tcpip_client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }


            if (connected) {
                Client client = new Client(socket);
                client.connectToServer();
                connected = false;
            }

        }

    }
}
