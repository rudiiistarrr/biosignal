/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_client.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rudi
 */
public class Client {

    private Socket socket;
    private int port;

    public Client(int arg0) {
        this.port = arg0;
    }

    public void connectToServer() {
        try {
            socket = new Socket("localhost", port);
            System.out.println("Connection established");
            //BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            System.out.println("Started OutputStream");
            byte x = 0xa;
            while (true) {
                
                output.writeByte(x);
                output.flush();
                System.out.println("Write: " + x + " " + Byte.valueOf(x));

            }


        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
