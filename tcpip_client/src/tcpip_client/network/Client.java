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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rudi
 */
public class Client {

    private Socket socket;
    private int number;
    private Timer timer;
    private List<Channel> channels;
    private String filePath = "config.txt";

    public Client(Socket socket) {
        this.socket = socket;
        timer = new Timer();
        channels = new ArrayList<Channel>();
    }

    public void connectToServer() {
        try {
            
            boolean reconnect = false;
            OutputStream output = socket.getOutputStream();

            Console.setMessage("Connection established");

            /* Check if config file exsists */
            if (new File(filePath).exists() == true) {
                Console.setInputMessage("Abgebrochene Connection wieder aufnehmen? ");
                if (Console.getInput().equals("y")) {
                    reconnect = true;
                }
            }

            /* Starting a new connection */
            if (!reconnect) {
                Console.setInputMessage("Set Channels: ");
                number = Integer.valueOf(Console.getInput());

                /* Send: Number of Channels */
                Data.sendNumberOfChannels(output, number);

                for (int i = 0; i < number; i++) {
                    Console.setInputMessage("[Channel " + i + ":] Abtastrate: ");
                    int samplingRate = Integer.valueOf(Console.getInput());
                    channels.add(new Channel(i, samplingRate));
                }
            } else {

                /* Load configuration from a config file and reconnect */
                loadConfig();

                /* Send: Number of Channels */
                Data.sendNumberOfChannels(output, number);
            }

            saveConfig();
            
            ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(number);

            /* Sort Channels (lowest sampling rate first) */
            Collections.sort(channels, new ChannelComparator());

            for (Channel channel : channels) {
                service.scheduleWithFixedDelay(new Transfer(channel, output, service), 1000, (long) (((double) 1 / channel.getSamplingRate()) * 1000), TimeUnit.MILLISECONDS);
            }

            while (!service.isTerminated()) {
            }
            System.out.println("Connection lost");

        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveConfig() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(String.valueOf(number) + "\r\n");
            writer.flush();
            for (Channel channel : channels) {
                writer.write(String.valueOf(channel.getNumber()) + "\r\n");
                writer.flush();
                writer.write(String.valueOf(channel.getSamplingRate()) + "\r\n");
                writer.flush();
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadConfig() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            number = Integer.parseInt(reader.readLine());
            for (int i = 0; i < number; i++) {
                int channelNumber = Integer.parseInt(reader.readLine());
                int samplingRate = Integer.parseInt(reader.readLine());
                channels.add(new Channel(channelNumber, samplingRate));
            }
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
