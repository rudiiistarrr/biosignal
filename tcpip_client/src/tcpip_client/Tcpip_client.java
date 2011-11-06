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
    private static String ip = "localhost";
    private static int port = 5050;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(ip, port);
            Client client = new Client(socket);
            client.connectToServer();
            System.out.println("");
        } catch (UnknownHostException ex) {
            Logger.getLogger(Tcpip_client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Tcpip_client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
