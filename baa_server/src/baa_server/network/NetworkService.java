/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package baa_server.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author Rudi
 */
public class NetworkService {

    private ServerSocket serverSocket;
    private int port;
    private JTextArea log;
    private String receiveMessage;

    public NetworkService(int arg0, JTextArea arg1) {
        this.port = arg0;
        this.log = arg1;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            Socket socket = waitForClient();
            if(socket != null){
                while(receiveMessage == null){
                   receiveMessage = receiveMessage(socket); 
                }
                System.out.println(receiveMessage);
                System.out.println("OK");
            }
        } catch (IOException ex) {
            Logger.getLogger(NetworkService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Socket waitForClient() {
        try {
            return serverSocket.accept();
        } catch (IOException ex) {
            Logger.getLogger(NetworkService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private String receiveMessage(Socket socket){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            char[] buffer = new char[4];
            int number = reader.read(buffer,0,4);
            String message = new String(buffer,0,number);
            return message;
            
        } catch (IOException ex) {
            Logger.getLogger(NetworkService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
