/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_server;

import tcpip_server.network.Server;



/**
 *
 * @author Rudi
 */
public class Tcpip_server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Server connection = new Server(5050,5);
        connection.startServer();
    }
}
