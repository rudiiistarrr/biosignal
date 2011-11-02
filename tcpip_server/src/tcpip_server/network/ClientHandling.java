/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_server.network;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import tcpip_server.console.Output;

/**
 *
 * @author Rudi
 */
public class ClientHandling implements Runnable {

    private Socket client;
    private static int clients = 0;
    private DataInputStream input = null;
    private int timeout = 5000;

    public ClientHandling(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {

        try {
            clients++;
            Output.setMessage("Client " + clients + " " + client.getInetAddress().toString() + " connected");
            input = new DataInputStream(client.getInputStream());
            byte x;
            while (true) {
                
                
                System.out.println("Waiting for Client " + input.available());
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
