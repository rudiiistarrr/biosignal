/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpip_client.network;

import dependency.Channel;
import dependency.ChannelComparator;
import dependency.Data;
import dependency.Console;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rudi
 */
public class Client {

    private Socket socket;
    private int port;
    private Timer timer;

    public Client(int arg0) {
        this.port = arg0;
        timer = new Timer();
    }

    public void connectToServer() {
        try {
            socket = new Socket("localhost", port);
            Console.setMessage("Connection established");
            Console.setMessage("Set Channels: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String userInput = reader.readLine();
            int number = Integer.parseInt(userInput);
            
            OutputStream output = socket.getOutputStream();
            Data.sendNumberOfChannels(output, number);

            List<Channel> channels = new ArrayList<Channel>();
            for(int i = 0; i < number; i++){
                Console.setMessage("[Channel " + i + ":] Abtastrate: ");
                String readLine = reader.readLine();
                int samplingRate = Integer.parseInt(readLine);
                channels.add(new Channel(i, samplingRate));
            }
            
            Collections.sort(channels, new ChannelComparator());

            
            for(Channel channel: channels){
                timer.scheduleAtFixedRate(new Transfer(channel,output), 1000, (long)(((double)1/channel.getSamplingRate())*1000));
                System.out.println("");
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
