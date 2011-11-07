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
    private static boolean input = false;
    private static String ip;
    private static int port;
    private static final int timeout = 5000;

    public static void main(String[] args) {
        
        while (true) {
            socket = null;
            Console.setInputMessage("Bitte geben Sie eine IP ein: ");
            ip = Console.getInput();
            Console.setInputMessage("Bitte geben Sie einen Port ein: ");
            port = Integer.valueOf(Console.getInput());
            input = true;

            while (input) {
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
                        Console.setMessage("Host wurde nicht gefunden");
                        input = false;
                        break;
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
}
