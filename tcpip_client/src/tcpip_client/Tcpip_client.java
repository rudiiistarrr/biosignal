/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_client;

import tcpip_client.network.Client;

/**
 *
 * @author Rudi
 */
public class Tcpip_client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Client client = new Client(5050);
        client.connectToServer();
    }
}
